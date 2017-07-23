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

import java.io.IOException;
import java.io.PrintWriter;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

/**
 * @author debmalyajash
 *
 */
public class App {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ResourceException
	 */
	public static void main(String[] args) throws ResourceException, IOException {
		if (args != null && args.length > 0) {
			try (PrintWriter writer = new PrintWriter(System.currentTimeMillis() + ".txt")) {
				Representation representation = new ClientResource(args[0]).get();
				representation.write(writer);
			}
		} else {
			System.err.println("Usage: java restler.App <URL>");
		}
	}

}
