package com.yj.springboot.service.dao.base.impl;

import com.yj.springboot.entity.base.*;
import com.yj.springboot.entity.search.*;
import com.yj.springboot.service.dao.base.BaseDao;
import com.yj.springboot.service.exception.BusinessException;
import com.yj.springboot.service.utils.EnumUtils;
import com.yj.springboot.service.utils.SequentialUuidHexGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
@Transactional(readOnly = true)
public class BaseDaoImpl<T extends Persistable & Serializable, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);

	protected final Class<T> domainClass;
	protected final EntityManager entityManager;

	public BaseDaoImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.domainClass = domainClass;
		this.entityManager = em;
	}
	/**
	 * 正则表达式
	 * \\b  表示 限定单词边界  比如  select 不通过   1select则是可以的
	 **/
//    private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
//            + "(\\b(select|update|union|and|or|delete|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
	private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)";
	private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

	protected boolean isValid(String str) {
		if (sqlPattern.matcher(str).find()) {
			LOGGER.error("未能通过防SQL注入拦截器：str=" + str);
			return false;
		}
		return true;
	}



	/**
	 * 持久化实体对象
	 *
	 * @param entity
	 * @param <S>
	 * @return
	 */
	@Override
	@Transactional
	public <S extends T> S save(S entity) {
		boolean isNew = preSave(entity);
		if (isNew) {
			entityManager.persist(entity);
		} else {
			entity = entityManager.merge(entity);
		}
		return entity;
	}

	@Override
	@Transactional
	public void save(Collection<T> entities) {
		if (entities != null && entities.size() > 0) {
			int i = 0;
			for (T entity : entities) {
				i++;
				boolean isNew = preSave(entity);
				if (isNew) {
					entityManager.persist(entity);
				} else {
					entityManager.merge(entity);
				}
				if (i % 50 == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
		}
	}

	protected boolean preSave(T entity) {
		Assert.notNull(entity, "持久化实体对象不能为空。");

		boolean isNew;
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			ID id = (ID) baseEntity.getId();
			//id是否有值
			if (Objects.isNull(id)) {
				isNew = true;
				baseEntity.setId(SequentialUuidHexGenerator.generate());
			} else {
//                T origin = findOne(id);
//                isNew = Objects.isNull(origin);
				if (!existsById(id)) {
					throw new BusinessException(MessageFormat.format("id：{0}不存在",id));
				} else {
					isNew = false;
				}
			}
		} else {
			isNew = Objects.isNull(entity.getId());
		}
		//是否含有业务审计属性实体
		if (entity instanceof IAuditable) {
			Date now = new Date();
			String userId = "0";
			String userAccount = "匿名";
			String userName = "匿名";
			IAuditable auditableEntity = (IAuditable) entity;
			if (isNew) {
				//创建
				auditableEntity.setCreatorId(userId);
				auditableEntity.setCreatorName(userName);
				auditableEntity.setCreatorAccount(userAccount);
				auditableEntity.setCreatedDate(now);
			}
			auditableEntity.setLastEditorId(userId);
			auditableEntity.setLastEditorName(userName);
			auditableEntity.setLastEditorAccount(userAccount);
			auditableEntity.setLastEditedDate(now);
		}
		//是否是租户实体(只是在租户代码为空时设置)
		if (entity instanceof ITenant && StringUtils.isBlank(((ITenant) entity).getTenantCode())) {
			//从上下文中获取租户代码
			ITenant tenant = (ITenant) entity;
			tenant.setTenantCode("tenantCode");
		}
		return isNew;
	}

	/**
	 * 基于主键集合查询集合数据对象
	 */
	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		Sort sort = Sort.unsorted();
		if (IRank.class.isAssignableFrom(domainClass)) {
			sort = Sort.by(Sort.Direction.ASC, IRank.RANK);
		}

		Specification<T> spec = (root, query, builder) -> {
			Predicate predicate = null;
//			//软删除
//			if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//				predicate = builder.and(builder.equal(root.get(ISoftDelete.DELETED), 0));
//			}
			//租户
			if (ITenant.class.isAssignableFrom(domainClass)) {
				if (Objects.isNull(predicate)) {
					predicate = builder.and(builder.equal(root.get(ITenant.TENANT_CODE), "tenantCode"));  // 配置全局tenantCode
				} else {
					predicate = builder.and(builder.equal(root.get(ITenant.TENANT_CODE), "tenantCode"), predicate); // 配置全局tenantCode
				}
			}
			return predicate;
		};
		return super.findAll(spec, sort);
	}

	@Override
	public T findOne(ID id) {
		return findById(id).orElse(null);
	}


	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(ID id) {
		T entity = null;
		if (Objects.nonNull(id)) {
			entity = super.findById(id).orElse(null);
			if (Objects.nonNull(entity) && ITenant.class.isAssignableFrom(domainClass)) {
				ITenant tenantEntity = (ITenant) entity;
				if (!StringUtils.equals("tenantCode", tenantEntity.getTenantCode())) { // 这里可以配置一个全局的租户代码
					return Optional.empty();
				}
			}
		}
		return Optional.ofNullable(entity);
	}

	@Override
	public void delete(ID id) {
//		//软删除
//		if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//			T entity = findOne(id);
//			if (Objects.nonNull(entity)) {
//				ISoftDelete softDelete = (ISoftDelete) entity;
//				//标记删除当前时间戳
//				softDelete.setDeleted(System.currentTimeMillis());
//				//持久化
//				save(entity);
//			}
//		} else {
//			deleteById(id);
//		}
		deleteById(id);
	}

	@Override
	public void delete(Collection<ID> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}

		List<T> entities = findAllById(ids);
//		//软删除
//		if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//			for (T entity : entities) {
//				ISoftDelete softDelete = (ISoftDelete) entity;
//				//标记删除当前时间戳
//				softDelete.setDeleted(System.currentTimeMillis());
//			}
//			// 持久化
//			save(entities);
//		} else {
//			deleteInBatch(entities);
//		}
		deleteInBatch(entities);
	}

	@Override
	public List<T> findAllUnfrozen() {
		Sort sort = Sort.unsorted();
		if (IRank.class.isAssignableFrom(domainClass)) {
			sort = Sort.by(Sort.Direction.ASC, IRank.RANK);
		}

		Specification<T> spec = (root, query, builder) -> {
			Predicate predicate = null;
//			//软删除
//			if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//				predicate = builder.and(builder.equal(root.get(ISoftDelete.DELETED), 0));
//			}
			//冻结
			if (IFrozen.class.isAssignableFrom(domainClass)) {
				if (Objects.isNull(predicate)) {
					predicate = builder.and(builder.equal(root.get(IFrozen.FROZEN), false));
				} else {
					predicate = builder.and(builder.equal(root.get(IFrozen.FROZEN), false), predicate);
				}
			}
			//租户
			if (ITenant.class.isAssignableFrom(domainClass)) {
				if (Objects.isNull(predicate)) {
					predicate = builder.and(builder.equal(root.get(ITenant.TENANT_CODE),"tenantCode"));
				} else {
					predicate = builder.and(builder.equal(root.get(ITenant.TENANT_CODE),"tenantCode"), predicate);
				}
			}
			return predicate;
		};
		return super.findAll(spec, sort);
	}

	@Override
	public List<T> findListByProperty(String property, Object value) {
		Specification<T> spec = (root, query, builder) -> {
			SearchFilter searchFilter = new SearchFilter(property, value, SearchFilter.Operator.EQ, null);
			Predicate predicate = buildPredicate(property, searchFilter, root, query, builder, false);
//			// 软删除
//			if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//				predicate = builder.and(builder.equal(root.get(ISoftDelete.DELETED), 0), predicate);
//			}
			//  租户隔离
			if (ITenant.class.isAssignableFrom(domainClass)) {
				return builder.and(builder.equal(root.get(ITenant.TENANT_CODE),"tenantCode"), predicate);
			}
			return predicate;
		};

		Sort sort = Sort.unsorted();
		if (IRank.class.isAssignableFrom(domainClass)) {
			sort = Sort.by(Sort.Direction.ASC, IRank.RANK);
		}

		return findAll(spec, sort);
	}

	@Override
	public T findByProperty(String property, Object value) {
		List<T> entities = findListByProperty(property, value);
		if (CollectionUtils.isEmpty(entities)) {
			return null;
		} else {
			Assert.isTrue(entities.size() == 1, "查询到多条数据");
			return entities.get(0);
		}
	}

	@Override
	public T findFirstByProperty(String property, Object value) {
		Sort sort = Sort.unsorted();
		if (IRank.class.isAssignableFrom(domainClass)) {
			sort = Sort.by(Sort.Direction.ASC, IRank.RANK);
		}
		Pageable pageable = PageRequest.of(0, 1, sort);

		SearchFilter searchFilter = new SearchFilter(property, value, SearchFilter.Operator.EQ, null);

		Specification<T> spec = (root, query, builder) -> {
			Predicate predicate = buildPredicate(property, searchFilter, root, query, builder, false);
			if (ITenant.class.isAssignableFrom(domainClass)) {
				Predicate tenantCodePredicate = builder.equal(root.get(ITenant.TENANT_CODE), "tenantCode");
				predicate = builder.and(predicate, tenantCodePredicate);
			}
//			if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//				predicate = builder.and(predicate, builder.equal(root.get(ISoftDelete.DELETED), 0));
//			}
			return predicate;
		};
		Page<T> page = findAll(spec, pageable);
		T result = null;
		List<T> list = page.getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
		}
		return result;
	}

	@Override
	public boolean isExistsByProperty(String property, Object value) {
		return Objects.nonNull(findFirstByProperty(property, value));
	}

	@Override
	public List<T> findByFilter(SearchFilter searchFilter) {
		List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
		searchFilters.add(searchFilter);
//		if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//			searchFilters.add(new SearchFilter(ISoftDelete.DELETED, 0));
//		}
		Search searchConfig = new Search(searchFilters, null, null);
		Specification<T> spec = buildSpecification(searchConfig);

		Sort sort = Sort.unsorted();
		if (IRank.class.isAssignableFrom(domainClass)) {
			sort = Sort.by(Sort.Direction.ASC, IRank.RANK);
		}

		return findAll(spec, sort);
	}

	@Override
	public long count(Search searchConfig) {
//		if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//			searchConfig.addFilter(new SearchFilter(ISoftDelete.DELETED, 0));
//		}
		Specification<T> spec = buildSpecification(searchConfig);
		return count(spec);
	}

	@Override
	public T findOneByFilters(Search searchConfig) {
//		if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//			searchConfig.addFilter(new SearchFilter(ISoftDelete.DELETED, 0));
//		}
		Specification<T> spec = buildSpecification(searchConfig);
		Optional<T> optional = findOne(spec);
		return optional.orElse(null);
	}

	@Override
	public List<T> findByFilters(Search searchConfig) {
		Sort sort = buildSort(searchConfig);
//		if (ISoftDelete.class.isAssignableFrom(domainClass)) {
//			searchConfig.addFilter(new SearchFilter(ISoftDelete.DELETED, 0));
//		}
		Specification<T> spec = buildSpecification(searchConfig);
		return findAll(spec, sort);
	}

	@Override
	public PageResult<T> findByPage(Search searchConfig) {
		PageInfo pageInfo = searchConfig.getPageInfo();
		//Assert.isTrue(pageInfo != null, "无分页参数。");
		if (Objects.isNull(pageInfo)) {
			pageInfo = new PageInfo();
			LOGGER.warn("无分页参数，将使用默认[{}]。", pageInfo.toString());
		}

		Sort sort = buildSort(searchConfig);
		Pageable pageable = PageRequest.of(pageInfo.getPage() - 1, pageInfo.getRows(), sort);
//		if (ISoftDelete.class.isAssignableFrom(domainClass)) {  // 软删除暂时不用
//			searchConfig.addFilter(new SearchFilter(ISoftDelete.DELETED, 0));
//		}
		Specification<T> specifications = buildSpecification(searchConfig);

		Page<T> page = findAll(specifications, pageable);
		PageResult<T> pageResult = new PageResult<T>();
		pageResult.setPage(page.getNumber() + 1);
		pageResult.setRecords(page.getTotalElements());
		pageResult.setTotal(page.getTotalPages());
		pageResult.setRows(page.getContent());
		return pageResult;
	}

	private Sort buildSort(Search searchConfig) {
		Sort sort = Sort.unsorted();
		if (searchConfig != null) {
			List<SearchOrder> sortOrders = searchConfig.getSortOrders();
			if (sortOrders != null) {
				List<Sort.Order> orders = new LinkedList<>();
				for (SearchOrder sortOrder : sortOrders) {
					if (SearchOrder.Direction.DESC.equals(sortOrder.getDirection())) {
						orders.add(new Sort.Order(Sort.Direction.DESC, sortOrder.getProperty()));
					} else {
						orders.add(new Sort.Order(Sort.Direction.ASC, sortOrder.getProperty()));
					}
				}
				// 暂时不用排序
//				if (IRank.class.isAssignableFrom(domainClass)) {
//					orders.add(new Sort.Order(Sort.Direction.ASC, IRank.RANK));
//				}
				sort = Sort.by(orders);
			}
		}
		return sort;
	}

	@SuppressWarnings({"rawtypes", "ConstantConditions"})
	private <X> Predicate buildPredicate(String propertyName, SearchFilter filter, Root<X> root, CriteriaQuery<?> query,
										 CriteriaBuilder builder, Boolean having) {
		Object matchValue = filter.getValue();
		if ((matchValue == null)
				|| (having && !propertyName.contains("("))
				|| (!having && propertyName.contains("("))) {
			return null;
		}
		if (matchValue instanceof String) {
			if (StringUtils.isBlank(String.valueOf(matchValue))) {
				return null;
			}
		} else if (matchValue.getClass().isArray()) {
			if (((Object[]) matchValue).length == 0) {
				return null;
			}
		} else if (matchValue instanceof Collection) {
			if (((Collection) matchValue).size() == 0) {
				return null;
			}
		}

		Predicate predicate = null;
		Expression expression = buildExpression(root, builder, propertyName, null);
		// 如果对字段属性为枚举类型，并且比较值为字符串，处理枚举值
		if (expression.getJavaType().isEnum()) {
			if (matchValue instanceof String) {
				// 将查询字符串转换为枚举值
				matchValue = EnumUtils.getEnum(expression.getJavaType(), (String) matchValue);
			}
			// 如果是LIST，则循环处理
			else if (matchValue instanceof Collection) {
				Set enumValues = new HashSet();
				((Collection) matchValue).forEach(m -> {
					if (m instanceof String) {
						// 将查询字符串转换为枚举值
						Object enumValue = EnumUtils.getEnum(expression.getJavaType(), (String) m);
						if (Objects.nonNull(enumValue)) {
							enumValues.add(enumValue);
						}
					}
				});
				if (CollectionUtils.isNotEmpty(enumValues)) {
					matchValue = enumValues;
				}
			}
			// 如果是数据
			else if (matchValue.getClass().isArray()) {
				Object[] matchValueArr = (Object[]) matchValue;
				Set enumValues = new HashSet();
				for (Object m : matchValueArr) {
					if (m instanceof String) {
						// 将查询字符串转换为枚举值
						Object enumValue = EnumUtils.getEnum(expression.getJavaType(), (String) m);
						if (Objects.nonNull(enumValue)) {
							enumValues.add(enumValue);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(enumValues)) {
					//noinspection ToArrayCallWithZeroLengthArrayArgument
					matchValue = enumValues.toArray(new Object[enumValues.size()]);
				}
			}
		}
		if (SearchFilter.NULL_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
			predicate = expression.isNull();
		} else if (SearchFilter.EMPTY_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
			predicate = builder.or(builder.isNull(expression), builder.equal(expression, ""));
		} else if (SearchFilter.NO_NULL_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
			predicate = expression.isNotNull();
		} else if (SearchFilter.NO_EMPTY_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
			predicate = builder.and(builder.isNotNull(expression), builder.notEqual(expression, ""));
		} else {
			// logic operator
			switch (filter.getOperator()) {
				case EQ:
					// 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
					// 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
					if (matchValue instanceof Date) {
						DateTime dateTime = new DateTime(((Date) matchValue).getTime());
						if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
							predicate = builder.and(builder.greaterThanOrEqualTo(expression, dateTime.toDate()),
									builder.lessThan(expression, dateTime.plusDays(1).toDate()));
						}
					}
					if (predicate == null) {
						predicate = builder.equal(expression, matchValue);
					}
					break;
				case NE:
					// 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
					// 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
					if (matchValue instanceof Date) {
						DateTime dateTime = new DateTime(((Date) matchValue).getTime());
						if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
							predicate = builder.or(builder.lessThan(expression, dateTime.toDate()),
									builder.greaterThan(expression, dateTime.plusDays(1).toDate()));
						}
					}
					if (predicate == null) {
						predicate = builder.notEqual(expression, matchValue);
					}
					break;
				//IS NULL OR ==''
				case BK:
					predicate = builder.or(builder.isNull(expression), builder.equal(expression, ""));
					break;
				//IS NOT NULL AND !=''
				case NB:
					predicate = builder.and(builder.isNotNull(expression), builder.notEqual(expression, ""));
					break;
				//IS NULL
				case NU:
					if (matchValue instanceof Boolean && !((Boolean) matchValue)) {
						predicate = builder.isNotNull(expression);
					} else {
						predicate = builder.isNull(expression);
					}
					break;
				//IS NOT NULL
				case NN:
					if (matchValue instanceof Boolean && !((Boolean) matchValue)) {
						predicate = builder.isNull(expression);
					} else {
						predicate = builder.isNotNull(expression);
					}
					break;
				//LIKE %abc%
				case LK:
					predicate = builder.like(expression, "%" + matchValue + "%");
					break;
				//NOT LIKE %abc%
				case NC:
					predicate = builder.notLike(expression, "%" + matchValue + "%");
					break;
				//LIKE abc%
				case LLK:
					predicate = builder.like(expression, matchValue + "%");
					break;
				//NOT LIKE abc%
				case BN:
					predicate = builder.notLike(expression, matchValue + "%");
					break;
				//LIKE %abc
				case RLK:
					predicate = builder.like(expression, "%" + matchValue);
					break;
				//NOT LIKE %abc
				case EN:
					predicate = builder.notLike(expression, "%" + matchValue);
					break;
				//BETWEEN 1 AND 2
				case BT:
					Assert.isTrue(matchValue.getClass().isArray(), "Match value must be array");
					Object[] matchValues = (Object[]) matchValue;
					Assert.isTrue(matchValues.length == 2, "Match value must have two value");
					// 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
					// 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
					if (matchValues[0] instanceof Date) {
						DateTime dateFrom = new DateTime(((Date) matchValues[0]).getTime());
						DateTime dateTo = new DateTime(((Date) matchValues[1]).getTime());
						if (dateFrom.getHourOfDay() == 0 && dateFrom.getMinuteOfHour() == 0 && dateFrom.getSecondOfMinute() == 0) {
							predicate = builder.and(builder.greaterThanOrEqualTo(expression, dateFrom.toDate()),
									builder.lessThan(expression, dateTo.plusDays(1).toDate()));
						} else {
							predicate = builder.equal(expression, matchValue);
						}
					} else {
						predicate = builder.between(expression, (Comparable) matchValues[0], (Comparable) matchValues[1]);
					}
					break;
				//>
				case GT:
					predicate = builder.greaterThan(expression, (Comparable) matchValue);
					break;
				//>=
				case GE:
					predicate = builder.greaterThanOrEqualTo(expression, (Comparable) matchValue);
					break;
				//<
				case LT:
					// 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
					// 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
					if (matchValue instanceof Date) {
						DateTime dateTime = new DateTime(((Date) matchValue).getTime());
						if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
							predicate = builder.lessThan(expression, dateTime.toDate());
						}
					}
					if (predicate == null && matchValue instanceof Comparable) {
						predicate = builder.lessThan(expression, (Comparable) matchValue);
					}
					break;
				//<=
				case LE:
					// 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
					// 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
					if (matchValue instanceof Date) {
						DateTime dateTime = new DateTime(((Date) matchValue).getTime());
						if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
							predicate = builder.lessThan(expression, dateTime.plusDays(1).toDate());
						}
					}
					if (predicate == null && matchValue instanceof Comparable) {
						predicate = builder.lessThanOrEqualTo(expression, (Comparable) matchValue);
					}
					break;
				case IN:
					if (matchValue.getClass().isArray()) {
						predicate = expression.in((Object[]) matchValue);
					} else if (matchValue instanceof Collection) {
						predicate = expression.in((Collection) matchValue);
					} else {
						predicate = builder.equal(expression, matchValue);
					}
					break;
				//Property Less Equal: <
				case PLT:
					Expression expressionPLT = buildExpression(root, builder, (String) matchValue, null);
					predicate = builder.lessThan(expression, expressionPLT);
					break;
				//Property Less Than: <=
				case PLE:
					Expression expressionPLE = buildExpression(root, builder, (String) matchValue, null);
					predicate = builder.lessThanOrEqualTo(expression, expressionPLE);
					break;
				case NOTIN:
					if (matchValue.getClass().isArray()) {
						predicate = expression.in((Object[]) matchValue);
					} else if (matchValue instanceof Collection) {
						predicate = expression.in((Collection) matchValue);
					} else {
						predicate = builder.equal(expression, matchValue);
					}
					predicate = builder.not(predicate);
					break;
				default:
					break;
			}
		}

		Assert.notNull(predicate, "Undefined match type: " + filter.getOperator());
		return predicate;
	}

	/**
	 * 根据条件集合对象组装JPA规范条件查询集合对象，基类默认实现进行条件封装组合
	 * 子类可以调用此方法在返回的List<Predicate>额外追加其他PropertyFilter不易表单的条件如exist条件处理等
	 */
	private <X> List<Predicate> buildPredicatesFromFilters(final Collection<SearchFilter> filters, Root<X> root, CriteriaQuery<?> query,
														   CriteriaBuilder builder, Boolean having) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (CollectionUtils.isNotEmpty(filters)) {
			for (SearchFilter filter : filters) {
				Predicate predicate = buildPredicate(filter.getFieldName(), filter, root, query, builder, having);
				if (predicate != null) {
					predicates.add(predicate);
				}
			}
		}
		return predicates;
	}

	private <X extends Persistable> Specification<X> buildSpecification(final Search searchConfig) {
		return (root, query, builder) -> {
			if (searchConfig != null) {
				return buildPredicatesFromFilters(searchConfig, root, query, builder);
			} else {
				return null;
			}
		};
	}

	protected Predicate buildPredicatesFromFilters(Search searchConfig, Root root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		// 暂时不要租户
//		if (ITenant.class.isAssignableFrom(domainClass)) {
//			SearchFilter filter = new SearchFilter(ITenant.TENANT_CODE, ContextUtil.getTenantCode(), SearchFilter.Operator.EQ);
//			List<SearchFilter> filters = searchConfig.getFilters();
//			if (filters == null || filters.size() == 0 || filters.stream().noneMatch(filter::equals)) {
//				//追加租户代码
//				searchConfig.addFilter(filter);
//			}
//		}
		return buildPredicatesFromFilters(searchConfig, root, query, builder, false);
	}

	private Predicate buildPredicatesFromFilters(Search searchConfig, Root root, CriteriaQuery<?> query, CriteriaBuilder builder, Boolean having) {
		if (searchConfig == null) {
			return null;
		}
		Predicate predicate = null;
		List<Predicate> predicates = new ArrayList<Predicate>();
		//快速搜索
		String quickSearchValue = searchConfig.getQuickSearchValue();
		if (StringUtils.isNotBlank(quickSearchValue)) {
			//拦截特殊字符 ' " 换行符
			if (!isValid(quickSearchValue)) {
				//return null;
				throw new BusinessException("未能通过防SQL注入拦截器: " + quickSearchValue);
			}
			Collection<String> quickSearchProperties = searchConfig.getQuickSearchProperties();
			if (CollectionUtils.isNotEmpty(quickSearchProperties)) {
				List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
				for (String property : quickSearchProperties) {
					searchFilters.add(new SearchFilter(property, quickSearchValue, SearchFilter.Operator.LK));
				}
				List<Predicate> quickSearchPredicates = buildPredicatesFromFilters(searchFilters, root, query, builder, having);

				if (CollectionUtils.isNotEmpty(quickSearchPredicates)) {
					//若有多个，存在or分组
					if (quickSearchPredicates.size() == 1) {
						predicate = builder.and(quickSearchPredicates.get(0));
					} else {
						//分组
						predicate = builder.or(quickSearchPredicates.toArray(new Predicate[0]));
					}
					if (predicate != null) {
						predicates.add(predicate);
					}
				}
			}
		}
		//追加普通查询条件（and）
		List<Predicate> andPredicates = buildPredicatesFromFilters(searchConfig.getFilters(), root, query, builder, having);
		if (CollectionUtils.isNotEmpty(andPredicates)) {
			predicates.addAll(andPredicates);
		}
		if (predicates.size() > 0) {
			predicate = builder.and(predicates.toArray(new Predicate[0]));
		}
		return predicate;
	}

	private Expression parseExpr(Root<?> root, CriteriaBuilder criteriaBuilder, String expr, Map<String, Expression<?>> parsedExprMap) {
		if (parsedExprMap == null) {
			parsedExprMap = new HashMap<String, Expression<?>>();
		}
		Expression<?> expression = null;
		if (expr.contains("(")) {
			int left = 0;
			char[] chars = expr.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] == '(') {
					left = i;
				}
			}
			String leftStr = expr.substring(0, left);
			String op = null;
			char[] leftStrs = leftStr.toCharArray();
			for (int i = leftStrs.length - 1; i > 0; i--) {
				if (leftStrs[i] == '(' || leftStrs[i] == ')' || leftStrs[i] == ',') {
					op = leftStr.substring(i + 1);
					break;
				}
			}
			if (op == null) {
				op = leftStr;
			}
			String rightStr = expr.substring(left + 1);
			String arg = StringUtils.substringBefore(rightStr, ")");
			String[] args = arg.split(",");
			//LOGGER.debug("op={},arg={}", op, arg);
			if ("case".equalsIgnoreCase(op)) {
				CriteriaBuilder.Case selectCase = criteriaBuilder.selectCase();

				Expression caseWhen = parsedExprMap.get(args[0]);

				String whenResultExpr = args[1];
				Object whenResult = parsedExprMap.get(whenResultExpr);
				if (whenResult == null) {
					CriteriaBuilder.Case<Long> whenCase = selectCase.when(caseWhen, new BigDecimal(whenResultExpr));
					selectCase = whenCase;
				} else {
					CriteriaBuilder.Case<Expression<?>> whenCase = selectCase.when(caseWhen, whenResult);
					selectCase = whenCase;
				}
				String otherwiseResultExpr = args[2];
				Object otherwiseResult = parsedExprMap.get(otherwiseResultExpr);
				if (otherwiseResult == null) {
					expression = selectCase.otherwise(new BigDecimal(otherwiseResultExpr));
				} else {
					expression = selectCase.otherwise((Expression<?>) otherwiseResult);
				}
			} else {
				Object[] subExpressions = new Object[args.length];
				for (int i = 0; i < args.length; i++) {
					subExpressions[i] = parsedExprMap.get(args[i]);
					if (subExpressions[i] == null) {
						String name = args[i];
						try {
							Path<?> item;
							if (name.contains(".")) {
								String[] props = StringUtils.split(name, ".");
								item = root.get(props[0]);
								for (int j = 1; j < props.length; j++) {
									item = item.get(props[j]);
								}
							} else {
								item = root.get(name);
							}
							subExpressions[i] = item;
						} catch (Exception e) {
							subExpressions[i] = new BigDecimal(name);
						}
					}
				}
				try {
					//criteriaBuilder.quot();
					expression = (Expression) MethodUtils.invokeMethod(criteriaBuilder, op, subExpressions);
				} catch (Exception e) {
					LOGGER.error("Error for aggregate  setting ", e);
				}
			}

			String exprPart = op + "(" + arg + ")";
			String exprPartConvert = exprPart.replace(op + "(", op + "_").replace(arg + ")", arg + "_").replace(",", "_");
			expr = expr.replace(exprPart, exprPartConvert);
			parsedExprMap.put(exprPartConvert, expression);

			if (expr.contains("(")) {
				expression = parseExpr(root, criteriaBuilder, expr, parsedExprMap);
			}
		} else {
			Path<?> item;
			if (expr.contains(".")) {
				String[] props = StringUtils.split(expr, ".");
				item = root.get(props[0]);
				for (int j = 1; j < props.length; j++) {
					item = item.get(props[j]);
				}
			} else {
				item = root.get(expr);
			}
			expression = item;
		}
		return expression;
	}

	private Expression<?> buildExpression(Root<?> root, CriteriaBuilder criteriaBuilder, String name, String alias) {
		Expression<?> expr = parseExpr(root, criteriaBuilder, name, null);
		if (alias != null) {
			expr.alias(alias);
		}
		return expr;
	}
}
