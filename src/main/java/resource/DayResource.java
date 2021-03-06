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
package resource;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.google.gson.JsonArray;

import config.CommonConfig;
import dao.sql.DateDao;

/**
 * @author debmalyajash
 * 
 * 
 *         This represents a day, and attribute is activities. What are the
 *         activities on that day.
 */
public class DayResource extends ServerResource {

	private Date dayStamp;
	private String activity;
	private String limit;
	private String format;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void doInit() throws ResourceException {
		String passedDate = (String) getRequest().getAttributes().get("dayStamp");
		System.out.println("Passed Date :" + passedDate);
		try {
			if (passedDate != null) {
				dayStamp = sdf1.parse(passedDate);
			}
		} catch (ParseException e) {
			if (passedDate != null) {
				try {
					dayStamp = sdf.parse(passedDate);
				} catch (ParseException e1) {

				}
			}
		}
		activity = (String) getRequest().getAttributes().get("activity");
		limit = (String) getRequest().getAttributes().get("limit");
		format = (String) getRequest().getAttributes().get("format");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.resource.ServerResource#get()
	 */
	@Override
	protected Representation get() throws ResourceException {
		StringRepresentation representation = null;

		DateDao dateDao = new DateDao(CommonConfig.getDataSource());
		try {
			System.out.println("dayStamp :" + dayStamp);
			if (dayStamp == null) {
				JsonArray dateArray = dateDao.retrieve(limit);
				representation = new StringRepresentation(dateArray.toString());
				representation.setMediaType(MediaType.APPLICATION_JSON);
			} else {
				JsonArray dateArray = dateDao.retrieve(new java.sql.Date(dayStamp.getTime()), limit);
				representation = new StringRepresentation(dateArray.toString());
				representation.setMediaType(MediaType.APPLICATION_JSON);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Support for CORS
		Series<Header> responseHeaders = (Series<Header>) getResponseAttributes()
				.get(HeaderConstants.ATTRIBUTE_HEADERS);
		if (responseHeaders == null) {
			responseHeaders = new Series<Header>(Header.class);
			getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		return representation;
	}

	@Override
	protected Representation post(Representation entity) throws ResourceException {
		StringRepresentation str = null;
		Form eventDefForm = new Form(entity);
		System.out.println("Event Def post :" + eventDefForm.toString());
		String date = eventDefForm.getFirst("dayStamp").getValue();
		String activity = eventDefForm.getFirst("activity").getValue();
		DateDao dateDao = new DateDao(CommonConfig.getDataSource());
		System.out.println("dateDao initialized");
		try {
			Date parsedDate = sdf1.parse(date);
			dateDao.create(new java.sql.Date(parsedDate.getTime()), activity);
			System.out.println("dateDao created");
		} catch (Throwable th) {
			str = new StringRepresentation("ERROR");
			return str;
		} finally {
			entity.release();
		}
		return new StringRepresentation("SUCCESS");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.resource.ServerResource#put(org.restlet.representation.
	 * Representation)
	 */
	@Override
	protected Representation put(Representation entity) throws ResourceException {
		StringRepresentation str = null;
		Form eventDefForm = new Form(entity);
		System.out.println("Event Def put :" + eventDefForm.toString());
		String date = eventDefForm.getFirst("dayStamp").getValue();
		String activity = eventDefForm.getFirst("activity").getValue();
		DateDao dateDao = new DateDao(CommonConfig.getDataSource());

		try {
			Date parsedDate = sdf1.parse(date);
			dateDao.update(new java.sql.Date(parsedDate.getTime()), activity);
		} catch (Throwable th) {
			str = new StringRepresentation("ERROR");
			return str;
		}

		return new StringRepresentation("SUCCESS");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.resource.ServerResource#delete()
	 */
	@Override
	protected Representation delete() throws ResourceException {
		StringRepresentation str = new StringRepresentation(dayStamp + " not found");

		if (dayStamp != null) {
			System.out.println("Passed dayStamp :" + dayStamp);
			try {
				DateDao dateDao = new DateDao(CommonConfig.getDataSource());
				int deleteCount = dateDao.delete(new java.sql.Date(dayStamp.getTime()));
				str.setText(dayStamp + " deleted successfully, number of rows affected " + deleteCount);
			} catch (Throwable th) {
				str.setText("ERROR");
			}

		}
		return str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.resource.Resource#doRelease()
	 */
	@Override
	protected void doRelease() throws ResourceException {
		try {
			CommonConfig.getDataSource().getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
