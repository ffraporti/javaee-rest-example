import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

@Path("/provider")
@ApplicationPath("/api")
public class Challenge_01 extends Application{

	@GET
	@Path("/{id}")
	public String getHelloMessage() {
		return "Hello World";
	}
	
}
