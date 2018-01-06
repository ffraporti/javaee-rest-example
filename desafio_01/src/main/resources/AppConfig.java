
//http://localhost:8080/desafio_01/v1/provider

//import java.util.HashMap;
//import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("v1")
public class AppConfig extends Application{
	
	@SuppressWarnings("unchecked")
	private Set<Class<?>> resources = new HashMap<>();
	
	public AppConfig() {
		System.out.println("Application Started!!!");
		resources.add(Provider.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return resources;
	}

}
