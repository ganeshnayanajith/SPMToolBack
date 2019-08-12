package com.af.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.af.project.model.AssignmentSub;

@RequestMapping("/assignment")
@RestController
public class AssignmentSubController {
	
	ComplexityController complexityController=new ComplexityController();

	private static String UPLOAD_DIR = "upload";
	String line = null;
	
	@CrossOrigin("http://localhost:3000")
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file,@RequestParam("filePath") String filePath,HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename().toString();
			System.out.println(fileName);
			String savePath = request.getServletContext().getRealPath("")+UPLOAD_DIR+File.separator+fileName;
			System.out.println(savePath);
			
			saveFile(file.getInputStream(),savePath);
			complexityController.basic(savePath, fileName, filePath);
			
			

			return "http://localhost:8080/upload/"+fileName;
		}catch(Exception e) {
			return e.getMessage().toString();
		}
	}

	private void saveFile(InputStream inputStream,String path) {
		try {
			OutputStream outputStream= new FileOutputStream(new File(path));
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes))!= -1) {
				outputStream.write(bytes,0,read);
			}
			outputStream.flush();
			outputStream.close();
					
		}catch(Exception e) {
			
		}
	}
	
	
	
}
