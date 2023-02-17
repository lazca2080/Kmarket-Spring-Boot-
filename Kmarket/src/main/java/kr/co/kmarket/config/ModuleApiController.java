package kr.co.kmarket.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@CrossOrigin("*")

@RestController
public class ModuleApiController {
	
	@Value("${resource_path}")
	private String resource_path;
	
	@GetMapping("/prodImg/{prodCate1}/{prodCate2}/{img}")
	public ResponseEntity<Resource> showImage(@PathVariable("prodCate1") String prodCate1, @PathVariable("prodCate2") String prodCate2, @PathVariable("img") String img){
		String os = System.getProperty("os.name").toLowerCase();
		
		String imageRoot = "";
		
		if(os.contains("win")) {
			imageRoot = resource_path+prodCate1+"/"+prodCate2+"/";
		}else if(os.contains("linux")) {
			imageRoot = "/home/Img/"+prodCate1+"/"+prodCate2+"/";
		}
		
		imageRoot = imageRoot + img;
		
		Resource resource = new FileSystemResource(imageRoot);
		
		if(!resource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		
		try {
			filePath = Paths.get(imageRoot);
			header.add("Content-Type", Files.probeContentType(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
}
