package api;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import resource.DayResource;

public class APIApplication extends Application {
	
	

	/**
	 * 
	 */
	public APIApplication() {
		super();
		setName("Day activity application");
		setDescription("Maintains day and activity in that day");
		setOwner("Debmalya Jash");
		setAuthor("Debmalya Jash");
	}

	/**
	 * @param context
	 */
	public APIApplication(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Restlet createInboundRoot() {
		// Create a router Restlet that defines routes.
		Router router = new Router(getContext());

		// Defines a route for the resource "list of days,activities"
		 router.attach( "/", DayResource.class );
		 router.attach( "/day", DayResource.class );
		 router.attach( "/day/{dayStamp}", DayResource.class );
		 router.attach( "/day/{dayStamp}/{format}", DayResource.class );
		 router.attach( "/day/{dayStamp}/{activity}", DayResource.class );
		

		return router;
	}
}
