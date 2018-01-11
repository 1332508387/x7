/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package x7.core.repository;

import java.util.List;
import java.util.Map;

import x7.core.bean.Criteria;
import x7.core.web.Pagination;


/**
 * 
 * 持久化<br>
 * 未提供业务层事务管理，建议用其他方式解决事务，例如乐观所, 各种最终一致<br>
 * 适合电商领域，不适合企业级开发<br>
 * @author Sim
 *
 */
public interface IRepository {
	
	/**
	 * 更新缓存
	 * @param clz
	 */
	<T> void refreshCache(Class<T> clz);

	/**
	 * 创建
	 * @param obj
	 * @return
	 */
	long create(Object obj);

	/**
	 * 更新, 支持局部更新
	 * @param obj
	 */
	boolean refresh(Object obj);
	
	/**
	 * 带条件支持局部更新
	 * @param obj
	 * @param conditionMap
	 * @return
	 */
	boolean refresh(Object obj, Map<String, Object> conditionMap);

	/**
	 * 删除
	 * @param obj
	 */
	boolean remove(Object obj);

	
	/**
	 * 根据主键查出单条
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
	 * @param conditionObj
	 * @return
	 */
	<T> T getOne(T conditionObj);
	/**
	 * 根据对象查一条记录
	 * @param conditionObj
	 * @param orderBy
	 * @param sc "DESC", "ASC"
	 * @return
	 */
	<T> T getOne(T conditionObj, String orderBy, String sc);

	/**
	 * 根据对象内容查询<br>
	 * 
	 * @param conditionObj
	 * @param extConditionMap
	 *            可以拼接的条件
	 * 
	 */
	<T> Pagination<T> list(Criteria criteria, Pagination<T> pagination);

	/**
	 * loadAll
	 * @param clz
	 * 
	 */
	<T> List<T> list(Class<T> clz);
	
	/**
	 * @param clz
	 * @param inList
	 */
	<T> List<T> in(Class<T> clz, List<? extends Object> inList);
	
	/**
	 * 支持单一的指定property的in查询, 包括主键
	 * @param clz
	 * @param inProperty
	 * @param inList
	 */
	<T> List<T> in(Class<T> clz, String inProperty, List<? extends Object> inList);

	/**
	 * 单主键，最大值
	 * @param clz
	 * 
	 */
	<T> long getMaxId(Class<T> clz);
	
	/**
	 * 最大值
	 * @param clz
	 * 
	 */
	long getMaxId(Object conditionObj);
	
	/**
	 * 查总条数
	 * @param obj
	 * 
	 */
	long getCount(Object obj);
	
	/**
	 * 查累计数
	 * @param conditionObj
	 * @param sumProperty
	 * 
	 */
	Object getSum(Object conditionObj, String sumProperty);
	
	/**
	 * 条件查询累计
	 * @param conditionObj
	 * @param sumProperty
	 * @param criterion
	 * 
	 */
	Object getSum(String sumProperty, Criteria criterion);
	/**
	 * 条件查询计数
	 * @param conditionObj
	 * @param countProperty
	 * @param criteria
	 * 
	 */
	Object getCount(String countProperty, Criteria criteria);
	
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

	boolean createBatch(List<? extends Object> objList);
}