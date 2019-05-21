package com.cafe24.fileupload.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileuploadService {
	
	private static final String SAVE_PATH = "/mysite-uploads";
	private static final String URL = "/images"; //가상 url

	public String restore(MultipartFile multipartFile) {
		String url = "";
		try {
		if(multipartFile.isEmpty()) {
			return url;
		}
		
		String originalFilename = multipartFile.getOriginalFilename(); //다른 사용자와 이름이 겺칠 수 있기 때문에 다른이름으로 저장한다.
		String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);//확장자
		String saveFileName = generateSaveFileName(extName);
		long fileSize = multipartFile.getSize();
		
		System.out.println("############"+originalFilename);
		System.out.println("############"+extName);
		System.out.println("############"+saveFileName);
		System.out.println("############"+fileSize);
		
		
		byte[] fileData = multipartFile.getBytes();
		OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFileName);
		os.write(fileData);
		os.close();
		
		url = URL + "/" + saveFileName;
		
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error:" + e);
		}
		
		return url;
	}

	private String generateSaveFileName(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("."+extName);
		
//		String s = "";
//		for문을 돌리면 안된다. String 객체가 계속 생성되기 때문
//		s += "hello" => new StringBuffer(s).append("hello");
		
		return filename;
	}

}
