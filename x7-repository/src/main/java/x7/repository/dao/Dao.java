package x7.repository.dao;

import java.util.List;
import java.util.Map;

import x7.core.bean.Criteria;
import x7.core.web.Pagination;


/**
 * 
 * @author sim
 *
 */
public interface Dao {

	long create(Object obj);

	boolean createBatch(List<Object> objList);
	/**
	 * 直接更新，不需要查出对象再更新<BR>
	 * 对于可能重置为0的数字，或Boolean类型，不能使用JAVA基本类型
	 * @param obj
	 */
	boolean refresh(Object obj);
	
	boolean refresh(Object obj, Map<String,Object> conditionMap);

	boolean remove(Object obj);
	
	/**
	 * 适合单主键
	 * @param clz
	 * @param idOne
	 * @return
	 */
	<T> T get(Class<T> clz, long idOne);

	
	/**
	 * 根据对象内容查询<br>
	 * 
	 * @param conditionObj
	 * 
	 */
	<T> List<T> list(Object conditionObj);
	
	/**
	 * 
	 * @param criterion
	 * @param pagination
	 * 
	 */
	<T> Pagination<T> list(Criteria criterion, Pagination<T> pagination);
	/**
	 * SQL语句查询
	 * 
	 * @param clz
	 * @param sql
	 * 
	 */
	List<Map<String,Object>>  list(Class clz, String sql,
			List<Object> conditionList);

	/**
	 * 
	 * 不要通过WEB传来的参数调用此接口, 因为没有分页限制
	 *
	 * 
	 */
	<T> List<T> list(Class<T> clz);
	
	<T> T getOne(T conditionObj, String orderBy, String sc);

	<T> long getMaxId(Class<T> clz);
	
	long getMaxId(Object obj);
	
	long getCount(Object obj);
	
	@Deprecated
	boolean execute(Object obj, String sql);
	
	Object getSum(Object conditionObj, String sumProperty);
	
	Object getSum(String sumProperty, Criteria criteria);
	//20160122 add by cl
	Object getCount(String countProperty, Criteria criteria);
	
	<T> List<T> in(Class<T> clz, List<? extends Object> inList);
	
	<T> List<T> in(Class<T> clz, String inProperty, List<? extends Object> inList);
	
	/**
	 * 连表查询，标准化拼接
	 * 尽量避免在互联网业务系统中使用<br>
	 * 不支持缓存<br>
	 * @param criterionJoinable
	 * @param pagination
	 * 
	 */
	Pagination<Map<String,Object>> list(Criteria.Fetch criterionJoinable, Pagination<Map<String,Object>> pagination);
	
	/**
	 * 
	 * 不要通过WEB传来的参数调用此接口, 因为没有分页限制
	 * @param criterionJoinable
	 * 
	 */
	List<Map<String,Object>> list(Criteria.Fetch criteriaJoinable);
}