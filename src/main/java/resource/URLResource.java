/**
 * Copyright 2015-2017 Debmalya Jash
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
import java.text.SimpleDateFormat;

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
import dao.sql.URLDao;

/**
 * @author debmalyajash
 *
 */
public class URLResource extends ServerResource {

	private String actualURL;
	private String shortenURL;
	private String hashCode;
	private String alias;

	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void doInit() throws ResourceException {
		actualURL = (String) getRequest().getAttributes().get("actualURL");
		shortenURL = (String) getRequest().getAttributes().get("shortenURL");
		hashCode = (String) getRequest().getAttributes().get("hashCode");
		alias = (String) getRequest().getAttributes().get("alias");
	}

	@Override
	protected Representation post(Representation entity) throws ResourceException {
		StringRepresentation str = null;
		Form urlDefFrom = new Form(entity);
		int shortCode = 0;
		String finalShortCode = "";
		System.out.println("Event Def post :" + urlDefFrom.toString());
		String actualURL = urlDefFrom.getFirst("actualURL").getValue();
		String alias = urlDefFrom.getFirst("alias").getValue();
		if (alias == null) {
			alias = "";
		}
		URLDao urlDao = new URLDao(CommonConfig.getDataSource());
		System.out.println("URLDao initialized");
		try {

			shortCode = Math.abs(actualURL.hashCode());
			// UUID
			finalShortCode = Integer.toHexString(shortCode);
			urlDao.create(actualURL, finalShortCode, alias);
			System.out.println("URLDao created");
		} catch (Throwable th) {
			th.printStackTrace();
			str = new StringRepresentation("ERROR :" + th.getMessage());
			System.out.println("Error return :" + str);
			return str;
		} finally {
			entity.release();
		}
		return new StringRepresentation(finalShortCode);
	}

	@Override
	protected Representation get() throws ResourceException {
		StringRepresentation representation = null;

		URLDao urlDao = new URLDao(CommonConfig.getDataSource());
		try {

			JsonArray urlArray = urlDao.retrieve(null);
			representation = new StringRepresentation(urlArray.toString());
			representation.setMediaType(MediaType.APPLICATION_JSON);

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

}
