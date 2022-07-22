package kero.s3put;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3UploadTest {

	@Test
	void test1() {
		

        S3Client s3 = S3Client.builder()
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        
        
        //Path input = Paths.get("G:/test/A.txt");
        
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("TestData/S3Upload/Test1_File1/test1.txt").getFile());
        
        Path input = file.toPath();
        
		S3Upload.putS3Object(s3, "u10.jp", "sample/test1.txt", input);
	}
}
