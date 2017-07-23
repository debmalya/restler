package api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import resource.DayResource;

public class APIApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		// Create a router Restlet that defines routes.
		Router router = new Router(getContext());

		// Defines a route for the resource "list of days"
		 router.attach( "/day", DayResource.class );
		// Defines a route for the resource "item"

		return router;
	}
}
