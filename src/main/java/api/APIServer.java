package api;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class APIServer {

	

	public static void main(String[] args) throws Exception {
		// Create a new Component.
		Component component = new Component();

		if (args.length < 3) {
			System.out.println(
					"Missing Parameters. Usage java -jar APIServer.jar <URL> <Driver Class> <Username> <Password>");
			System.exit(0);
		}


		// Add a new HTTP server listening on port 8111.
		int port = 8111;
		String sPort = args[0];
		try {
			port = Integer.parseInt(sPort);
		} catch (NumberFormatException nfe) {
			System.err.println("Provided port number'" + args[0] +"' is not a valid port number. Using default port 8111");
		}

		component.getServers().add(Protocol.HTTP, port);

		component.getDefaultHost().attach("/api/v1", new APIApplication());

		// Start the component.
		component.start();
	}
}
