package wit.shortterm1.kkoowoon.domain.confirm.dto.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmCreateDto {

    @ApiModelProperty(value = "인증 글 내용", required = true, example = "2022-07-17")
    @NotNull
    private String description;

    @ApiModelProperty(value = "인증 사진 URL_1", required = true, example = "photo-url.com/1")
    @NotNull
    private String photoUrl1;

    @ApiModelProperty(value = "인증 사진 URL_2", required = true, example = "photo-url.com/2")
    @Nullable
    private String photoUrl2;

    @ApiModelProperty(value = "인증 사진 URL_3", required = true, example = "photo-url.com/3")
    @Nullable
    private String photoUrl3;

}
