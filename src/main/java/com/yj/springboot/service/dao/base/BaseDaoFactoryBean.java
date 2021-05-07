package com.yj.springboot.service.dao.base;


import com.yj.springboot.service.dao.base.impl.DaoImplMapper;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static org.springframework.data.querydsl.QuerydslUtils.QUERY_DSL_PRESENT;

/**
 * 实现功能:
 * 分配关系业务实体业务逻辑实现基类
 *
 * @param <T> Persistable的子类
 * @param <R> JpaRepository的子类
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/5/8 15:48
 */
@SuppressWarnings("unchecked")
public class BaseDaoFactoryBean<R extends JpaRepository<T, Serializable>, T extends Persistable>
        extends JpaRepositoryFactoryBean<R, T, Serializable> {

    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public BaseDaoFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager) {
            @Override
            protected SimpleJpaRepository<T, Serializable> getTargetRepository(
                    RepositoryInformation information, EntityManager entityManager) {
                Class<T> domainClass = (Class<T>) information.getDomainType();
//                //树形
//                if (TreeEntity.class.isAssignableFrom(domainClass)) {
//                    return new BaseTreeDaoImpl(domainClass, entityManager);
//                }
//                //业务实体基类
//                else if (BaseEntity.class.isAssignableFrom(domainClass)) {
//                    return new BaseEntityDaoImpl(domainClass, entityManager);
//                }
//                //持久化实体基类
//                else if (AbstractEntity.class.isAssignableFrom(domainClass)) {
//                    return new BaseDaoImpl(domainClass, entityManager);
//                }
//                //默认
//                else {
//                    return new SimpleJpaRepository(domainClass, entityManager);
//                }
                return new DaoImplMapper<T>().getTargetRepository(domainClass, entityManager);
            }

            @Override
            protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
                if (isQueryDslExecutor(metadata.getRepositoryInterface())) {
                    return QuerydslJpaRepository.class;
                } else {
//                    return SimpleJpaRepository.class;
                    Class clazz = metadata.getDomainType();

//                //一般树形
//                if (TreeEntity.class.isAssignableFrom(clazz)) {
//                    return BaseTreeDaoImpl.class;
//                }
//                //业务实体基类
//                else if (BaseEntity.class.isAssignableFrom(clazz)) {
//                    return BaseEntityDaoImpl.class;
//                }
//                //持久化实体基类
//                else if (AbstractEntity.class.isAssignableFrom(clazz)) {
//                    return BaseDaoImpl.class;
//                }
//                //默认
//                else {
//                    return SimpleJpaRepository.class;
//                }
                    return new DaoImplMapper<T>().getRepositoryBaseClass(clazz);
                }
            }

            /**
             * Returns whether the given repository interface requires a QueryDsl specific implementation to be chosen.
             *
             * @param repositoryInterface
             * @return
             */
            private boolean isQueryDslExecutor(Class<?> repositoryInterface) {
                return QUERY_DSL_PRESENT && QuerydslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
            }
        };
    }
}
