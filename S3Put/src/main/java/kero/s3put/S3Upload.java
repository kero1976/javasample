package kero.s3put;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class S3Upload {

	private static Logger log = LoggerFactory.getLogger(S3Upload.class);

	private S3Client s3;
	
	public S3Upload() {
		log.debug("通常コンストラクタ");
		s3 = S3Client.builder()
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
	}
	public S3Upload(S3Client s3) {
		log.debug("テスト用コンストラクタ");
		this.s3 = s3;
	}
    public void upload(String bucketName,
            String objectKey,
            Path objectPath) {

        String result = putS3Object(s3, bucketName, objectKey, objectPath);
        log.info("result:{}", result);
        //s3.close();
    }
    // snippet-start:[s3.java2.s3_object_upload.main]
    public static String putS3Object(S3Client s3,
                                     String bucketName,
                                     String objectKey,
                                     Path objectPath) {
    	log.debug("START");

        try {

            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();

            PutObjectResponse response = s3.putObject(putOb,
                    RequestBody.fromBytes(getObjectFile(objectPath)));

            log.debug("END");
           return response.eTag();

        } catch (Exception e) {
        	log.error(e.getMessage());
        }
        return "";
    }

    // Return a byte array.
    private static byte[] getObjectFile(Path filePath) throws IOException {
    	log.debug("START (Path:{})", filePath);
    	return  Files.readAllBytes(filePath);
    }
    // snippet-end:[s3.java2.s3_object_upload.main]
}
