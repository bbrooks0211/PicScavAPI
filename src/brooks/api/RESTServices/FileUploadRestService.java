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

/**
 * Spring Rest Controller for game image uploading
 * @author Brendan Brooks
 *
 */
@RestController
@RequestMapping("/file/")
public class FileUploadRestService {
	
	private static s3UtilityInterface s3;
	private static GameItemsServiceInterface itemsService;
	
	/**
	 * Uploads a found item with an image
	 * @param file
	 * @param gameID
	 * @param itemID
	 * @param userID
	 * @return RestResponse with Boolean value
	 */
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

/*
Copyright 2019, Brendan Brooks.  

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