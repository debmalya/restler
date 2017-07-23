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
package reslter;

import org.restlet.Component;
import org.restlet.data.Protocol;

/**
 * @author debmalyajash
 *
 */
public class MyServer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		 // Create a new Restlet component and add a HTTP server connector to it
		int port = 8182;
	    Component component = new Component();
	    if (args != null && args.length > 0) {
	    	try {
	    		port = Integer.parseInt(args[0]);
	    	}catch(NumberFormatException ignore) {
	    		// Not specified a numeric value for port.
	    	}
	    }
	    component.getServers().add(Protocol.HTTP, port);

	    // Then attach it to the local host
	    component.getDefaultHost().attach("/number", NumericServer.class);

	    // Now, let's start the component!
	    // Note that the HTTP server connector is also automatically started.
	    component.start();

	}

}
