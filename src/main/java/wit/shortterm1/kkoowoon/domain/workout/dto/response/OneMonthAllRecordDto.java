package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneMonthAllRecordDto {

    private final List<OneMonthRecordWithDateDto> oneMonthRecordDtoList = new ArrayList<>();

    public static OneMonthAllRecordDto createDto() {
        return new OneMonthAllRecordDto();
    }

    public void addOneMonthRecordDto(OneMonthRecordWithDateDto dto) {
        oneMonthRecordDtoList.add(dto);
    }

}
