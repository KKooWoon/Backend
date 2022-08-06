package wit.shortterm1.kkoowoon.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TempResponse {

    private String message;

    private HttpStatus status;

}
