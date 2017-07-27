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

import java.sql.Date;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;

import dao.sql.DateDao;

/**
 * @author debmalyajash
 *
 */
public class CommonConfigTest {

	/**
	 * Test method for
	 * {@link config.CommonConfig#CommonConfig(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	// @Test(threadPoolSize=100,invocationCount=100,timeOut=5000)
	@Test
	public void testCommonConfig() {
		new CommonConfig("jdbc:mysql://localhost/mysql", "root", "passw0rd", "com.mysql.jdbc.Driver");
		Assert.assertNotNull(CommonConfig.getDataSource());
		DateDao dateDao = new DateDao(CommonConfig.getDataSource());
		Date dateId = new Date(System.currentTimeMillis());
		try {
			dateDao.create(dateId, "Testing date entry creation " + System.currentTimeMillis());
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertFalse(true, "ERR :" + e.getMessage());
		}

		try {
			JsonArray dayActivities = dateDao.retrieve(null);
			Assert.assertTrue(dayActivities.size() > 1);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertFalse(true, "ERR :" + e.getMessage());
		}
		
		try {
			Assert.assertTrue(dateDao.update(new Date(System.currentTimeMillis()),"Updated" ) == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertFalse(true, "ERR :" + e.getMessage());
		}

		try {
			Assert.assertTrue(dateDao.delete(new Date(System.currentTimeMillis()) ) == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertFalse(true, "ERR :" + e.getMessage());
		}
	}

}
