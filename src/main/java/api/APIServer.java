package api;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.util.Series;

import config.CommonConfig;

public class APIServer {

	

	public static void main(String[] args) throws Exception {
		// Create a new Component.
		Component component = new Component();

		if (args.length < 5) {
			System.out.println(
					"Missing Parameters. Usage java -jar APIServer.jar <port> <Driver Class> <Username> <Password> <URL>");
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
		
		// for https
		Server server = component.getServers().add(Protocol.HTTPS,8113);
		Series<Parameter> parameters = server.getContext().getParameters();
		parameters.add("keystorePath","/Users/debmalyajash/git/restler/serverKey.jks");
		parameters.add("keystorePassword","Raju007%");
		parameters.add("keystoreType","JKS");
		parameters.add("keyPassword","Raju007%");
		
		component.getDefaultHost().attach("/api/v1", new APIApplication());
		new CommonConfig(args[1], args[2], args[3], args[4]);

		// Start the component.
		component.start();
	}
}
