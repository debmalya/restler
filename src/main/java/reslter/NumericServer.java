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

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * @author debmalyajash
 *
 */
public class NumericServer extends ServerResource{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// default port
		int port = 8182;

		// configured port
		if (args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException ignore) {
				System.err.println("'" + args[0] + "' is not valid port");
			}
		}
		
		 new Server(Protocol.HTTP, port, NumericServer.class).start();
		 System.out.println("Server running");
	}

	@Get("txt")
	public String toString() {
		return "hello, world";
	}

}
