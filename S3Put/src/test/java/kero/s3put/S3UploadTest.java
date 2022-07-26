package kero.s3put;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class S3UploadTest extends TestBase{

	@Test
	void test1() {
		S3Upload s3 = new S3Upload();
		s3.upload("u10.jp", "sample/test1.txt", getTestDataPath("Test1_File1/test1.txt"));
	}

	@Test
	void test2() {
		S3Upload s3 = new S3Upload();
		s3.upload("u10.jp", "sample/test2.txt", getTestDataPath("Test2_File2/test2.txt"));
		s3.upload("u10.jp", "sample/test3.txt", getTestDataPath("Test2_File2/test3.txt"));
	}
	
	@Override
	public String getName() {
		return "S3Upload";
	}
	
	@Mock
	S3Client s3client;
	
	@InjectMocks
	S3Upload s3upload;
	
	@BeforeEach
	private void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void test3() {
		when(s3client.putObject((PutObjectRequest)any(), (RequestBody)any())).thenReturn(mock(PutObjectResponse.class));
		
		s3upload.upload("aaa", "bbb", getTestDataPath("Test1_File1/test1.txt"));
	}
	
	@Test
	void test4() {
		when(s3client.putObject((PutObjectRequest)any(), (RequestBody)any())).thenThrow(new RuntimeException());
		s3upload.upload("aaa", "bbb", getTestDataPath("Test1_File1/test1.txt"));
	}
}
