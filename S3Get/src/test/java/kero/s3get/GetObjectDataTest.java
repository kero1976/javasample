package kero.s3get;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class GetObjectDataTest {

	@Test
	void testOK1() {
		
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.AP_NORTHEAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
        
        
		GetObjectData.getObjectBytes(s3,"www.u10.jp","sample/A.txt","A.txt");
		GetObjectData.listBucketObjects(s3,"www.u10.jp");
	}
}
