package kero.fileutil;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * ファイルを作成.
	 * 
	 * 親フォルダが無ければ作成する。
	 * ファイルが存在する場合は上書きする。
	 * @param path
	 * @param data
	 */
	public static void create(Path path, byte[] data) {
		log.error("START – path:{}", path);
		try {

			FileUtils.writeByteArrayToFile(path.toFile(), data);

		} catch (IOException e) {
			log.error("err",e);
		}
	}

}
