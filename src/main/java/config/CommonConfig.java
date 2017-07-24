/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package config;

import java.util.concurrent.locks.ReentrantLock;

import dao.sql.DataSource;

/**
 * @author debmalyajash
 *
 */
public class CommonConfig {

	private static DataSource dataSource;

	private static ReentrantLock lock = new ReentrantLock();
	
	private static int initCount = 0;

	

	private void init(String url, String userName, String password, String driverClass) {
		if (!lock.isLocked()) {
			dataSource = new DataSource(driverClass, userName, password, url);
			System.out.println("Initialized..." + (initCount++) + Thread.currentThread().getName()) ;
		}

	}

	public CommonConfig(final String url, final String userName, final String password, final String driverClass) {
		if (dataSource == null) {
			init(url, userName, password, driverClass);
		} else {
			System.out.println("Already initialized..." + Thread.currentThread().getName()) ;
		}
	}

	/**
	 * 
	 * @return
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

}
