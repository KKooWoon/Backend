package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageDto {

    private byte[] resource;

    private String contentType;

    private HttpHeaders headers;

    private ImageDto(byte[] resource, String contentType, HttpHeaders headers) {
        this.resource = resource;
        this.contentType = contentType;
        this.headers = headers;
    }

    public static ImageDto createDto(byte[] resource, String contentType, HttpHeaders headers) {
        return new ImageDto(resource, contentType, headers);
    }
}
