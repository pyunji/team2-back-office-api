package com.mycompany.webapp.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
	
	private final AmazonS3 amazonS3;

	/* aws.properties에 정의된 S3 버킷 이름 */
	@Value("${cloud.aws.s3.bucket}") 
	public String bucket;

	
	public String uploadFile(MultipartFile multipartFile, String dirPath) {
		String fileName = dirPath + createFileName(multipartFile.getOriginalFilename());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getSize());
		objectMetadata.setContentType(multipartFile.getContentType());
		
		// close가 필요한 stream은 try 뒤의 statement에 선언하면 finally에서 close하지 않아도 자동으로 닫힌다고 함
		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3.putObject(
					new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
		}
		return amazonS3.getUrl(bucket, fileName).toString();
	}

	/* 이건 휴지통에서 영구삭제할 때 쓸거임 최대한 delete 메서드는 사용 자제 ! */
	public void deleteFile(String fileName) {
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
	}

	/* 파일명 난수화 */
	private String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	/* 파일명에 확장자가 없으면 에러를 발생시키는 메서드 */
	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
		}
	}
	/* 로컬에 저장했다가 삭제하고 AWS에 올리는 방식인데 혹시 몰라서 남겨놓음 */
//	public String upload(MultipartFile multipartFile, String dirName) throws IOException {
//		File uploadFile = convert(multipartFile) // 파일 변환할 수 없으면 에러
//				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
//
//		return upload(uploadFile, dirName);
//	}
//
//	// S3로 파일 업로드하기
//	private String upload(File uploadFile, String dirName) {
//		String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName(); // S3에 저장된 파일 이름
//		String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
//		removeNewFile(uploadFile);
//		return uploadImageUrl;
//	}
//
//	// S3로 업로드
//	private String putS3(File uploadFile, String fileName) {
//		amazonS3Client.putObject(
//				new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//		return amazonS3Client.getUrl(bucket, fileName).toString();
//	}
//
//	// 로컬에 저장된 이미지 지우기
//	private void removeNewFile(File targetFile) {
//		if (targetFile.delete()) {
//			log.info("File delete success");
//			return;
//		}
//		log.info("File delete fail");
//	}
//
//	// 로컬에 파일 업로드 하기
//	private Optional<File> convert(MultipartFile file) throws IOException {
//		File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
//		if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
//			try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기
//																				// 위함
//				fos.write(file.getBytes());
//			}
//			return Optional.of(convertFile);
//		}
//
//		return Optional.empty();
//	}
}
