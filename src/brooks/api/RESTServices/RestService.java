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
import javax.ws.rs.QueryParam;
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
	
	
	@Autowired
	public void setS3Utility(s3UtilityInterface s3) {
		this.s3 = s3;
	}
}

/*
This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/