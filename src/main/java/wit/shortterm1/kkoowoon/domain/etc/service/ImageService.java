package wit.shortterm1.kkoowoon.domain.etc.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.ImageDto;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;
import wit.shortterm1.kkoowoon.domain.confirm.service.ConfirmService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String IMAGE_DIR;

    private final AmazonS3Client s3Client;

    public String upload(MultipartFile imageFile) {
        String[] s = imageFile.getOriginalFilename().split("\\.");
        String fileName = UUID.randomUUID() + "_" + LocalTime.now().toSecondOfDay() + "." + s[s.length - 1];
        String s3FileName = IMAGE_DIR + "/" + fileName;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(imageFile.getSize());
        try {
            s3Client.putObject(bucket, s3FileName, imageFile.getInputStream(), objMeta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        return s3Client.getUrl(bucket, IMAGE_DIR + s3FileName).toString();
        return fileName;
    }

    public byte[] download(String fileName) {
        S3Object object = s3Client.getObject(bucket, IMAGE_DIR + "/" + fileName);
        S3ObjectInputStream objectContent = object.getObjectContent();
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(objectContent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bytes;
    }

    private HttpHeaders getHttpHeaders(String contentType, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=\"" + fileName + "\""));
        return headers;
    }

}
