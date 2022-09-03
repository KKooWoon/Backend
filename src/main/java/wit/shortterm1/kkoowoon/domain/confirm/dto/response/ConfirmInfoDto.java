package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmInfoDto {

    @ApiModelProperty(value = "레이스 인증 id", required = true, example = "3")
    private Long confirmId;

    @ApiModelProperty(value = "인증 내용 글", required = true, example = "오늘도 득근했습니다!")
    private String description;

    @ApiModelProperty(value = "인증 시점", required = true, example = "2022-07-18")
    private LocalDate confirmedAt;

    @ApiModelProperty(value = "댓글 리스트 DTO", required = true, example = "[ {댓글 정보1}, {댓글 정보2}, ...")
    private CommentInfoListDto commentInfoListDto;

    public ConfirmInfoDto(Long confirmId, String description, LocalDate confirmedAt,
                          CommentInfoListDto commentInfoListDto) {
        this.confirmId = confirmId;
        this.description = description;
        this.confirmedAt = confirmedAt;
        this.commentInfoListDto = commentInfoListDto;
    }

    public static ConfirmInfoDto createDto(Confirm confirm, CommentInfoListDto commentInfoListDto) {
        return new ConfirmInfoDto(confirm.getId(), confirm.getDescription(), confirm.getConfirmedAt(), commentInfoListDto);
    }
}
