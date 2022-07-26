package kero.s3put;

import java.io.File;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TestBase {

	private static final String BASE_DIR = "TestData/";
	private static Logger log = LoggerFactory.getLogger(TestBase.class);
	
	public Path getTestDataPath(String testData) {
		String path = BASE_DIR + getName() + "/" + testData;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        
        return file.toPath();
	}
	
	public abstract String getName();
}
