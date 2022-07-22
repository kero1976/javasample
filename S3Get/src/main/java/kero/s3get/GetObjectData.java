package kero.s3get;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
// snippet-end:[s3.java2.getobjectdata.import]

/**
 * Before running this Java V2 code example, set up your development environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class GetObjectData {

	private static Logger log = LoggerFactory.getLogger(GetObjectData.class);
	
    public static void main(String[] args) {
    	MDC.put("pid", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
     final String usage = "\n" +
                "Usage:\n" +
                "    <bucketName> <keyName> <path>\n\n" +
                "Where:\n" +
                "    bucketName - The Amazon S3 bucket name. \n\n"+
                "    keyName - The key name. \n\n"+
                "    path - The path where the file is written to. \n\n";

        if (args.length != 3) {
            System.out.println(usage);
            System.exit(1);
        }

        String bucketName = args[0];
        String keyName = args[1];
        String path = args[2];

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.AP_NORTHEAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();

        getObjectBytes(s3,bucketName,keyName, path);
        s3.close();
    }

    // snippet-start:[s3.java2.getobjectdata.main]
    public static void getObjectBytes (S3Client s3, String bucketName, String keyName, String path ) {

    	log.debug("START – bucketName:{}, keyName:{}, path:{}", bucketName, keyName, path);
    	
        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
            byte[] data = objectBytes.asByteArray();

            // Write the data to a local file.
            File myFile = new File(path );
            OutputStream os = new FileOutputStream(myFile);
            os.write(data);
            System.out.println("Successfully obtained bytes from an S3 object");
            os.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (S3Exception e) {
          System.err.println(e.awsErrorDetails().errorMessage());
           System.exit(1);
        }
        
        
    }
    
    
    public static void listBucketObjects(S3Client s3, String bucketName ) {

    	log.debug("START – bucketName:{}", bucketName);
        try {
             ListObjectsRequest listObjects = ListObjectsRequest
                     .builder()
                     .bucket(bucketName)
                     .build();

             ListObjectsResponse res = s3.listObjects(listObjects);
             List<S3Object> objects = res.contents();

            for (S3Object myValue : objects) {
                System.out.print("\n The name of the key is " + myValue.key());
                System.out.print("\n The object is " + calKb(myValue.size()) + " KBs");
                System.out.print("\n The owner is " + myValue.owner());
            }

         } catch (S3Exception e) {
             System.err.println(e.awsErrorDetails().errorMessage());
             System.exit(1);
         }
     }
     //convert bytes to kbs
     private static long calKb(Long val) {
         return val/1024;
     }
    // snippet-end:[s3.java2.getobjectdata.main]
}