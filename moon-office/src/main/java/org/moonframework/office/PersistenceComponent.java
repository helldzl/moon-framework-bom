package org.moonframework.office;

/**
 * 持久化组件, 该组件通常和业务模块的service配置使用, 以保证事务性
 * 
 * @author quzile
 *
 * @param <T>
 */
public interface PersistenceComponent<T> {

	/**
	 * 持久化实体
	 * 
	 * @param entity
	 * @throws Exception
	 */
	void persist(T entity) throws Exception;

}
