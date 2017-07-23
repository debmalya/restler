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
package dao.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * @author debmalyajash
 *
 */
public class DateDao {
	private DataSource dataSource;
	
	public DateDao(DataSource applicationDataSource) {
		dataSource = applicationDataSource;
	}
	
	public boolean create(Date dateId,String activityDescription) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("insert into test.daily values(?,?)");
		preparedStatement.setDate(1, dateId);
		preparedStatement.setString(2, activityDescription);
		return preparedStatement.execute();
		 
	}
	
}
