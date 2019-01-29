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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import brooks.api.models.RestResponse;
import brooks.api.utility.interfaces.s3UtilityInterface;

/**
 * For testing purposes only
 * @author Brendan Brooks
 */
@RestController
@RequestMapping("/test2")
public class RestService2 {
	
	private static s3UtilityInterface s3;
	
	public RestService2() {}
	
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public RestResponse<Boolean> uploadFile(@RequestParam(value="file") MultipartFile file, @RequestParam(value="gameID") int gameID, @RequestParam(value="itemID") int itemID, @RequestParam(value="userID") int userID) 
	{
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		System.out.println("Received Data: " + file.getOriginalFilename() + " ID's: " + gameID + ", " + itemID + " , " + userID );
		s3.uploadFile("test/file_" + new Random().nextInt(10000) + "." + extension, file);
		return response;
	}
	
	@Autowired
	public void setS3Utility(s3UtilityInterface s3) {
		this.s3 = s3;
	}
}
