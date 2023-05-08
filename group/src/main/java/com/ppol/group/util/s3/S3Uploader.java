package com.ppol.group.util.s3;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ppol.group.exception.exception.S3Exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 	아마존 S3 서버에 파일을 업로드, 삭제 기능을 하는 Util Class
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 *	S3 서버에 파일을 업로드하고 해당 파일의 경로(url)을 리턴하는 메서드
	 */
	public String upload(MultipartFile uploadFile) {
		String ext = getExt(uploadFile);

		// 업로드 파일 형식 체크 및 예외처리
		// TODO image 파일 뿐 아니라 3D 에셋의 경우는?
		if (uploadFile.getContentType() == null || !uploadFile.getContentType().contains("image")) {
			throw new S3Exception("유효하지 않은 파일입니다.");
		}

		String fileName = UUID.randomUUID() + "." + ext;
		return putS3(uploadFile, fileName);
	}

	// S3로 업로드
	private String putS3(MultipartFile uploadFile, String fileName) {
		String result = null;

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(uploadFile.getSize());

		try {
			amazonS3Client.putObject(
				new PutObjectRequest(bucket, fileName, uploadFile.getInputStream(), objectMetadata).withCannedAcl(
					CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new S3Exception("파일 업로드 실패");
		}

		URL url = amazonS3Client.getUrl(bucket, fileName);
		if (url != null) {
			result = url.toString();
		}

		return result;
	}

	//파일 삭제
	public void deleteFile(String fileUrl) {
		int pos = fileUrl.lastIndexOf("/");
		String key = fileUrl.substring(pos + 1);

		try {
			amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));

			log.info("{} 삭제 성공", key);

		} catch (Exception exception) {
			throw new S3Exception("파일 삭제 실패");
		}
	}

	public String getExt(MultipartFile imgFile) {
		//사진 확장자
		int pos = Objects.requireNonNull(imgFile.getOriginalFilename()).lastIndexOf(".");
		return imgFile.getOriginalFilename().substring(pos + 1);
	}
}


