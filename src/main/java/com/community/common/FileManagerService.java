package com.community.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {

	// 실제 업로드가 된 이미지가 저장될 서버의 경로
	public static final String FILE_UPLOAD_PATH = "C:\\조성수\\6_Spring_project\\community\\community_workspace\\images/"; // 집
	// public static final String FILE_UPLOAD_PATH = "D:\\조성수\\6_spring_project\\community\\workspace\\images/"; //학원

	// input: MultipartFile, userId
	// output: String(이미지 경로)
	public String uploadFile(MultipartFile files, String loginId) {
		// 폴더(디렉토리) 생성
		// 예:bbbb_5642132132
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";

		// 폴더 생성
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			// 폴더 생성 시 실패라면 경로를 null로 리턴
			return null;
		}

		// 파일 업로드
		try {
			byte[] bytes = files.getBytes();
			Path path = Paths.get(filePath + files.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 실패시 경로 null
		}

		// 파일업로드가 성공하면 이미지 url path를 리턴
		return "/images/" + directoryName + "/" + files.getOriginalFilename();
	}

	// 파일 삭제
	// input: Image output:X
	public void deleteFile(String imagePath) {
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));

		// 삭제할 이미지가 존재하는가?
		if (Files.exists(path)) {
			// 이미지 삭제
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[FileManagerService 파일삭제] 삭제 실패. path:{}", path.toString());
				return;
			}

			// 폴더(디렉토리) 삭제
			path = path.getParent();
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[FileManagerService 파일삭제] 디렉토리 삭제 실패 path:{}", path.toString());
				}
			}
		}
	}
}
