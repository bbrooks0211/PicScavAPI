package brooks.api.RESTServices;

import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import brooks.api.business.interfaces.GameItemsServiceInterface;
import brooks.api.models.FoundItemModel;
import brooks.api.models.RestResponse;
import brooks.api.utility.interfaces.s3UtilityInterface;

@RestController
@RequestMapping("/file/")
public class FileUploadRestService {
	
	private static s3UtilityInterface s3;
	private static GameItemsServiceInterface itemsService;
	
	@PostMapping(value = "/gameImageUpload", consumes = "multipart/form-data")
	public RestResponse<Boolean> imageUpload(@RequestParam(value="file") MultipartFile file, @RequestParam(value="gameID") int gameID, @RequestParam(value="itemID") int itemID, @RequestParam(value="userID") int userID) 
	{
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		FoundItemModel item = new FoundItemModel();
		item.setGameID(gameID);
		item.setItemID(itemID);
		item.setUserID(userID);
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		System.out.println("Received Data: " + file.getOriginalFilename() + " ID's: " + gameID + ", " + itemID + " , " + userID );
		String filePathAndName = "game/game_" + gameID + "/item_" + itemID + "/user_" + userID + "/image_" + new Random().nextInt(10000000) + "-" + new Random().nextInt(100000) +  "." + extension;
		item.setImageURL(filePathAndName);
		s3.uploadFile(filePathAndName, file);
		
		boolean status = itemsService.addFoundItem(item);
		
		if(status)
		{
			return new RestResponse<Boolean>(1, "OK", Boolean.valueOf(true)); 
		}
		
		return response;
	}

	@Autowired
	public void setS3Utility(s3UtilityInterface s3) {
		this.s3 = s3;
	}
	
	@Autowired
	private void setItemsService(GameItemsServiceInterface service) {
		this.itemsService = service;
	}
}
