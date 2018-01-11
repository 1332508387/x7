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
package x7.repository;

import java.util.List;

import x7.core.bean.IQuantity;
import x7.repository.redis.JedisConnector_Persistence;

/**
 * 
 * 分布式系统的异步数据操作<br>
 * 
 * @author Sim
 *
 */
public class RepositoryX {

	private static RepositoryX instance;
	public static RepositoryX getInstance() {
		if (instance == null){
			instance = new RepositoryX();
		}
		return instance;
	}
	private RepositoryX (){
		
	}
	
	/**
	 * 基于Redis的, 用于单线程增长累计数, 单线程模式无需在启动时初始化Redis数据
	 * 
	 * @param obj
	 * @param offset
	 * @return current quantity
	 */
	public int increaseBySingleThreadModel(IQuantity obj, int offset) {
		if (offset < 1) {
			throw new RuntimeException("increasing quantity must > 0");
		}

		String mapKey = obj.getClass().getName();

		int quantity = (int) JedisConnector_Persistence.getInstance().hincrBy(mapKey, obj.getKey(), offset);

		/*
		 * 初始化
		 */
		if (quantity <= offset) {
			obj.setQuantity(0);
			List<IQuantity> list = Repositories.getInstance().list(obj);

			if (! list.isEmpty()) {
				IQuantity q = list.get(0);
				quantity = q.getQuantity() + offset;
				JedisConnector_Persistence.getInstance().hset(mapKey, obj.getKey(), String.valueOf(quantity));
			}
		}

		obj.setQuantity(quantity);

		return quantity;
	}

}
