import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("provider")
public class Provider {
	
	@GET
	public String hello() {
		return "Hello World!";
	}
	
}
