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


import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Response;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

import resource.DayResource;

/**
 * @author debmalyajash
 *
 */
public class ClientApplication {
	public static void main(String[] args) {
		Client client = new Client(new Context(),Protocol.HTTPS);
		ClientResource resource = new ClientResource("http://localhost:8113/api/v1/day");
		Response response = resource.getResponse();
	
		System.out.println(response + " " + resource.getRequest());
		
		Client secureClient = new Client(new Context(),Protocol.HTTPS);
		Series<Parameter> parameters = secureClient.getContext().getParameters();
		parameters.add("truststorePath","/Users/debmalyajash/git/restler/serverKey.jks");
		parameters.add("truststorePassword","Raju007%");
		parameters.add("truststoretype","JKS");
		resource = new ClientResource("https://localhost:8113/api/v1/day");
		DayResource dayResource = resource.wrap(DayResource.class);
		response = resource.getResponse();
	
		System.out.println(response + " " + resource.getRequest());
	}
}
