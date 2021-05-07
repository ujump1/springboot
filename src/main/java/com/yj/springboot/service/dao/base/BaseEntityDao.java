package com.yj.springboot.service.dao.base;


import com.yj.springboot.entity.base.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @param <T> BaseEntity的子类
 * @author <a href="mailto:chao2.ma@changhong.com">马超(Vision.Mac)</a>
 * @version 1.0.1 2017/5/4 15:03
 */
@NoRepositoryBean //  // 这个注解标识不会被实例化
public interface BaseEntityDao<T extends BaseEntity> extends BaseDao<T, String> {
}
