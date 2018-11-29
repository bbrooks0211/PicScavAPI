package brooks.api.RESTServices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
/**
 * For testing purposes only
 * @author Brendan Brooks
 */
@Path("/test")
public class RestService {
	private List<String> list = new ArrayList<String>();
	
	@GET
	@Path("/data")
	@Produces("application/json")
	public List<String> getData() {
		list.add("Test1");
		list.add("test 10000");
		
		return list;
	}
}
