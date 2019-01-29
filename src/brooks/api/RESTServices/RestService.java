package brooks.api.RESTServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import brooks.api.models.RestResponse;
import brooks.api.utility.interfaces.s3UtilityInterface;

/**
 * For testing purposes only
 * @author Brendan Brooks
 */
@Path("/test")
@Service
public class RestService {
	
	private static s3UtilityInterface s3;
	
	public RestService() {}
	
	@GET
	@Path("/data")
	@Produces("application/json")
	public List<String> getData() {
		List<String> list = new ArrayList<String>();
		list.add("Test1");
		list.add("test 10000");
		
		return list;
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public RestResponse<Boolean> uploadFile(@FormDataParam("file") MultipartFile file, @FormDataParam("gameID") int gameID, @FormDataParam("itemID") int itemID, @FormDataParam("userID") int userID) 
	{
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		System.out.println("Received Data: " + file.getOriginalFilename() + " ID's: " + gameID + ", " + itemID + " , " + userID );
		s3.uploadFile("test/file_" + new Random().nextInt(10000) + extension, file);
		return response;
	}
	
	@Autowired
	public void setS3Utility(s3UtilityInterface s3) {
		this.s3 = s3;
	}
}
