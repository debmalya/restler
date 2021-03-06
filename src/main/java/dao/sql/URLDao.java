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
import java.text.SimpleDateFormat;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

/**
 * @author debmalyajash
 *
 */
public class URLDao {
	private DataSource dataSource;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public URLDao(DataSource applicationDataSource) {
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
	public boolean create(String actualURL, String shortCode, String alias) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("insert into test.tinyURL values(?,?,?,?)");
			preparedStatement.setString(1, actualURL);
			preparedStatement.setString(2, shortCode);
			preparedStatement.setString(3, alias);
			preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
			boolean returnResult = preparedStatement.execute();
			connection.commit();
			return returnResult;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

	}

	/**
	 * 
	 * @param limit
	 *            - query fetch limit.
	 * @return JsonArray with date and corresponding activity on that day.
	 * @throws SQLException
	 */
	public JsonArray retrieve(String limit) throws SQLException {
		JsonArray urlArray = new JsonArray();
		Connection connection = dataSource.getConnection();

		PreparedStatement preparedStatement = null;
		if (limit == null || limit.equalsIgnoreCase("All")) {
			preparedStatement = connection.prepareStatement("select * from test.tinyURL");
		} else {
			int size = 100;
			try {
				size = Integer.parseInt(limit);
			} catch (NumberFormatException ignore) {

			}
			preparedStatement = connection.prepareStatement("select * from test.tinyURL limit " + size);
		}
		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			while (resultSet.next()) {
				// JsonObject eachRow = new JsonObject();
				JsonArray eachRowAsArray = new JsonArray();
				for (int i = 0; i < colCount; i++) {
					// String columnLabel = metaData.getColumnLabel(i + 1);

					int colType = metaData.getColumnType(i + 1);
					switch (colType) {
					case Types.DATE:
						// eachRow.add("v", new
						// JsonPrimitive(resultSet.getDate(i + 1).toString()));
						eachRowAsArray.add(new JsonPrimitive(resultSet.getDate(i + 1).toString()));
						break;
					case Types.VARCHAR:
						// eachRow.add("v", new
						// JsonPrimitive(resultSet.getString(i + 1)));
						eachRowAsArray.add(new JsonPrimitive(resultSet.getString(i + 1)));
						break;
					default:
						break;
					}
				}
				// JsonObject eachRowObject = new JsonObject();
				// eachRowObject.add("c", eachRowAsArray);
				urlArray.add(eachRowAsArray);
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
		return urlArray;
	}

	/**
	 * 
	 * @param shortCode
	 *            - shortCode for whom actual URL will be retrieved.
	 * @return JsonArray with date and corresponding activity on that day.
	 * @throws SQLException
	 */
	public JsonArray retrieveActualURL(String shortCode) throws SQLException {
		JsonArray urlArray = new JsonArray();
		Connection connection = dataSource.getConnection();

		PreparedStatement preparedStatement = null;
		if (shortCode != null) {
			preparedStatement = connection.prepareStatement("select actualURL from test.tinyURL where shortCode = ?");

			ResultSet resultSet = null;
			try {
				preparedStatement.setString(1, shortCode);
				resultSet = preparedStatement.executeQuery();
				ResultSetMetaData metaData = resultSet.getMetaData();
				int colCount = metaData.getColumnCount();
				while (resultSet.next()) {
					// JsonObject eachRow = new JsonObject();
					JsonArray eachRowAsArray = new JsonArray();
					for (int i = 0; i < colCount; i++) {
						// String columnLabel = metaData.getColumnLabel(i + 1);

						int colType = metaData.getColumnType(i + 1);
						switch (colType) {
						case Types.DATE:
							// eachRow.add("v", new
							// JsonPrimitive(resultSet.getDate(i +
							// 1).toString()));
							eachRowAsArray.add(new JsonPrimitive(resultSet.getDate(i + 1).toString()));
							break;
						case Types.VARCHAR:
							// eachRow.add("v", new
							// JsonPrimitive(resultSet.getString(i + 1)));
							eachRowAsArray.add(new JsonPrimitive(resultSet.getString(i + 1)));
							break;
						default:
							break;
						}
					}
					// JsonObject eachRowObject = new JsonObject();
					// eachRowObject.add("c", eachRowAsArray);
					urlArray.add(eachRowAsArray);
				}
			} finally {
				if (resultSet != null) {
					resultSet.close();
				}

				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
		}
		return urlArray;
	}

	/**
	 * 
	 * @param limit
	 *            - query fetch limit.
	 * @return JsonArray with date and corresponding activity on that day.
	 * @throws SQLException
	 */
	public JsonArray retrieve(Date dayStamp, String limit) throws SQLException {
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
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			while (resultSet.next()) {
				JsonArray eachRow = new JsonArray();
				for (int i = 0; i < colCount; i++) {
					int colType = metaData.getColumnType(i + 1);
					switch (colType) {
					case Types.DATE:
						eachRow.add(new JsonPrimitive(resultSet.getDate(i + 1).toString()));
						break;
					case Types.VARCHAR:
						eachRow.add(new JsonPrimitive(resultSet.getString(i + 1)));
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

			if (connection != null) {
				connection.close();
			}
		}
		return dateArray;
	}

	/**
	 * To delete a date related entry
	 * 
	 * @param dateToBeDeleted
	 *            - specific date whose entry will be deleted.
	 * @return true if deletion successful, false otherwise.
	 * @throws SQLException
	 */
	public int delete(Date dateToBeDeleted) throws SQLException {
		if (dateToBeDeleted != null) {
			PreparedStatement psmt = null;
			try {
				String dateParameter = sdf.format(dateToBeDeleted);
				Connection databaseConnection = dataSource.getConnection();
				psmt = databaseConnection.prepareStatement("delete from test.daily where theday = ?");
				psmt.setString(1, dateParameter);
				System.out.println("dateParameter :" + dateParameter);
				int updateCount = psmt.executeUpdate();
				System.out.println("Rows deleted :" + updateCount);
				return updateCount;
			} finally {
				if (psmt != null) {
					psmt.close();
				}
			}
		}
		return -1;
	}

	/**
	 * To update a date related entry
	 * 
	 * @param dateToBeDeleted
	 *            - specific date whose entry will be deleted.
	 * @return true if deletion successful, false otherwise.
	 * @throws SQLException
	 */
	public int update(Date dateToBeDeleted, String activity) throws SQLException {
		if (dateToBeDeleted != null) {
			PreparedStatement psmt = null;
			try {
				String dateParameter = sdf.format(dateToBeDeleted);
				Connection databaseConnection = dataSource.getConnection();
				psmt = databaseConnection.prepareStatement("update test.daily set activity = ? where theday = ?");
				psmt.setString(1, activity);
				psmt.setString(2, dateParameter);
				int updateCount = psmt.executeUpdate();
				System.out.println("Rows updated :" + updateCount);
				return updateCount;
			} finally {
				if (psmt != null) {
					psmt.close();
				}
			}
		}
		return -1;
	}
}
