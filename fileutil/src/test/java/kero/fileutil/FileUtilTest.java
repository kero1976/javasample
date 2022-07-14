package kero.fileutil;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;


public class FileUtilTest {

	@Test
	void testCreate() {
		Path path = Paths.get("./a/b/c.txt");
		byte[] data = "あいうえお".getBytes(StandardCharsets.UTF_8);
		FileUtil.create(path, data);
		assertThat(Files.exists(path)).isTrue();
		assertThat(path.toFile()).exists();
	}
}
