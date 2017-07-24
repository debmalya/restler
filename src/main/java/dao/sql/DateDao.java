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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * @author debmalyajash
 *
 */
public class DateDao {
	private DataSource dataSource;

	public DateDao(DataSource applicationDataSource) {
		dataSource = applicationDataSource;
	}

	/**
	 * C - Create operation.
	 * 
	 * @param dateId
	 *            - date for which activity will be entered.
	 * @param activityDescription
	 *            - description of the activity.
	 * @return true if activity created successfully.
	 * @throws SQLException
	 */
	public boolean create(Date dateId, String activityDescription) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("insert into test.daily values(?,?)");
			preparedStatement.setDate(1, dateId);
			preparedStatement.setString(2, activityDescription);
			return preparedStatement.execute();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

	}

	/**
	 * 
	 * @param limit - query fetch limit.
	 * @return JsonArray with date and corresponding activity on that day.
	 * @throws SQLException
	 */
	public JsonArray retrieve(String limit) throws SQLException {
		JsonArray dateArray = new JsonArray();
		Connection connection = dataSource.getConnection();

		PreparedStatement preparedStatement = null;
		if (limit == null || limit.equalsIgnoreCase("All")) {
			preparedStatement = connection.prepareStatement("select * from test.daily");
		} else {
			int size = 100;
			try {
				size = Integer.parseInt(limit);
			} catch (NumberFormatException ignore) {

			}
			preparedStatement = connection.prepareStatement("select * from test.daily limit " + size);
		}
		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			while (resultSet.next()) {
				JsonObject eachRow = new JsonObject();
				for (int i = 0; i < colCount; i++) {
					String columnLabel = metaData.getColumnLabel(i + 1);
					int colType = metaData.getColumnType(i + 1);
					switch (colType) {
					case Types.DATE:
						eachRow.add(columnLabel, new JsonPrimitive(resultSet.getDate(i + 1).toString()));
						break;
					case Types.VARCHAR:
						eachRow.add(columnLabel, new JsonPrimitive(resultSet.getString(i + 1)));
						break;
					default:
						break;
					}
				}
				dateArray.add(eachRow);
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
		return dateArray;
	}

	/**
	 * 
	 * @param limit - query fetch limit.
	 * @return JsonArray with date and corresponding activity on that day.
	 * @throws SQLException
	 */
	public JsonArray retrieve(Date dayStamp,String limit) throws SQLException {
		JsonArray dateArray = new JsonArray();
		Connection connection = dataSource.getConnection();

		PreparedStatement preparedStatement = null;
		if (limit == null || limit.equalsIgnoreCase("All")) {
			preparedStatement = connection.prepareStatement("select * from test.daily where theday = ?");
		} else {
			int size = 100;
			try {
				size = Integer.parseInt(limit);
			} catch (NumberFormatException ignore) {

			}
			preparedStatement = connection.prepareStatement("select * from test.daily where theday = ? limit " + size);
		}
		ResultSet resultSet = null;
		try {
			preparedStatement.setDate(1, dayStamp);
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			while (resultSet.next()) {
				JsonObject eachRow = new JsonObject();
				for (int i = 0; i < colCount; i++) {
					String columnLabel = metaData.getColumnLabel(i + 1);
					int colType = metaData.getColumnType(i + 1);
					switch (colType) {
					case Types.DATE:
						eachRow.add(columnLabel, new JsonPrimitive(resultSet.getDate(i + 1).toString()));
						break;
					case Types.VARCHAR:
						eachRow.add(columnLabel, new JsonPrimitive(resultSet.getString(i + 1)));
						break;
					default:
						break;
					}
				}
				dateArray.add(eachRow);
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
		return dateArray;
	}

}
