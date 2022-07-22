package kero.s3put;

import java.io.File;
import java.nio.file.Path;

public abstract class TestBase {

	public Path getTestDataPath(String testData) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("TestData/S3Upload/Test1_File1/test1.txt").getFile());
        
        return file.toPath();
	}
}
