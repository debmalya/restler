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

import java.util.Date;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

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

	@Override
	protected void doInit() throws ResourceException {
		dayStamp = (Date) getRequest().getAttributes().get("dayStamp");
		activity = (String) getRequest().getAttributes().get("activity");
		limit = (String) getRequest().getAttributes().get("limit");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.resource.Resource#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String name) {
		return (String) getRequest().getAttributes().get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.resource.Resource#handle()
	 */
	@Override
	public Representation handle() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.resource.Resource#setAttribute(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub

	}

}
