package brooks.api.utility.interfaces;

import java.io.ByteArrayOutputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * Utility service for Amazon s3
 * <br>
 * Credit to: https://grokonez.com/aws/amazon-s3/amazon-s3-springboot-restapis-upload-download-file-image-to-s3
 * @author Brendan Brooks
 *
 */
public interface s3UtilityInterface {
	public ByteArrayOutputStream downloadFile(String keyName);
	public void uploadFile(String keyName, MultipartFile file);
}
