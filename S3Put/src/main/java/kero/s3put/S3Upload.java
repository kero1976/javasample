package kero.s3put;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public static void main(String[] args) {
        final String usage = "\n" +
                "Usage:\n" +
                "  <bucketName> <objectKey> <objectPath> \n\n" +
                "Where:\n" +
                "  bucketName - The Amazon S3 bucket to upload an object into.\n" +
                "  objectKey - The object to upload (for example, book.pdf).\n" +
                "  objectPath - The path where the file is located (for example, C:/AWS/book2.pdf). \n\n" ;

        if (args.length != 3) {
            System.out.println(usage);
            System.exit(1);
        }

        String bucketName =args[0];
        String objectKey = args[1];
        String objectPath = args[2];
        System.out.println("Putting object " + objectKey +" into bucket "+bucketName);
        System.out.println("  in bucket: " + bucketName);


        S3Client s3 = S3Client.builder()
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        String result = putS3Object(s3, bucketName, objectKey, objectPath);
        System.out.println("Tag information: "+result);
        s3.close();
    }

    // snippet-start:[s3.java2.s3_object_upload.main]
    public static String putS3Object(S3Client s3,
                                     String bucketName,
                                     String objectKey,
                                     String objectPath) {
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
        	log.error(e.getMessage(), e);
            System.exit(1);
        }
        return "";
    }

    // Return a byte array.
    private static byte[] getObjectFile(String filePath) throws IOException {
    	log.debug("START (Path:{})", filePath);
    	return  Files.readAllBytes(Paths.get(filePath));
    }
    // snippet-end:[s3.java2.s3_object_upload.main]
}
