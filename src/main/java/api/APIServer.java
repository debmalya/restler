package api;

import org.restlet.Component;
import org.restlet.data.Protocol;

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
		
//		CorsService corsService = new CorsService();         
//		corsService.setAllowedOrigins(new HashSet<String>(Arrays.asList("*")));
//		corsService.setAllowedCredentials(true);

		component.getServers().add(Protocol.HTTP, port);
//		component.getServices().add(corsService);

		component.getDefaultHost().attach("/api/v1", new APIApplication());
		new CommonConfig(args[1], args[2], args[3], args[4]);

		// Start the component.
		component.start();
	}
}
