package wit.shortterm1.kkoowoon.domain.confirm.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Time;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.ConfirmCreateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.*;
import wit.shortterm1.kkoowoon.domain.confirm.exception.AlreadyConfirmedException;
import wit.shortterm1.kkoowoon.domain.confirm.exception.NoSuchConfirmationException;
import wit.shortterm1.kkoowoon.domain.confirm.exception.WrongConfirmOwnerException;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Comment;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;
import wit.shortterm1.kkoowoon.domain.confirm.repository.CommentRepository;
import wit.shortterm1.kkoowoon.domain.confirm.repository.ConfirmRepository;
import wit.shortterm1.kkoowoon.domain.etc.service.ImageService;
import wit.shortterm1.kkoowoon.domain.race.exception.NoSuchParticipateException;
import wit.shortterm1.kkoowoon.domain.race.persist.Participate;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.race.repository.ParticipateRepository;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.service.AccountService;
import wit.shortterm1.kkoowoon.domain.workout.persist.*;
import wit.shortterm1.kkoowoon.domain.workout.service.WorkoutRecordService;
import wit.shortterm1.kkoowoon.global.common.jwt.JwtProvider;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;
import wit.shortterm1.kkoowoon.global.error.exception.NoSuchImageException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmService {

    private final RaceService raceService;
    private final WorkoutRecordService workoutRecordService;
    private final ConfirmRepository confirmRepository;
    private final ParticipateRepository participateRepository;
    private final CommentRepository commentRepository;
    private final ImageService imageService;
    private final JwtProvider jwtProvider;
    private final AccountService accountService;

    private final Path IMAGE_DIR = Path.of("/Users/jeong-uijae/Documents/KKooWoon/images");

    public ConfirmInfoDto getConfirmation(Long confirmId) {
        Confirm confirm = getConfirm(confirmId);
        CommentInfoListDto commentInfoListDto = CommentInfoListDto.createDto();
        commentRepository.findListById(confirm.getId())
                .forEach(comment -> commentInfoListDto.addCommentInfoDto(CommentInfoDto.createDto(comment)));
        return ConfirmInfoDto.createDto(confirm, commentInfoListDto);
    }

    public ImageDto getImages(Long confirmId) {
        Confirm confirm = getConfirm(confirmId);
        byte[] byteArray = imageService.download(confirm.getPhotoUrl1());
        String contentType = null;
        try {
            contentType = Files.probeContentType(Path.of(confirm.getPhotoUrl1()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        HttpHeaders headers = getHttpHeaders(contentType, confirm.getPhotoUrl1());
        return ImageDto.createDto(byteArray, contentType, headers);
    }

    private HttpHeaders getHttpHeaders(String contentType, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=\"" + fileName + "\""));
        return headers;
    }

    @Transactional
    public ConfirmCreateResultDto createConfirmation(HttpServletRequest request, Long recordId, MultipartFile imageFile, ConfirmCreateDto dto) {
        WorkoutRecord workoutRecord = workoutRecordService.getWorkoutRecord(recordId);
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = accountService.getAccountByKakaoId(kakaoId);
        checkUserInRace(account.getId(), workoutRecord.getRace().getId());   // 레이스에 참여중인 사용자인지 체크
        checkAlreadyConfirmed(workoutRecord,  workoutRecord.getRace()); // 이미 인증했는지 체크

//        String fileName = storeImageFile(imageFile);
        String fileName = imageService.upload(imageFile);
        Confirm savedConfirm = confirmRepository
                .save(Confirm.of(fileName, dto.getPhotoUrl2(), dto.getPhotoUrl3(), dto.getDescription(), workoutRecord));
        workoutRecord.confirmWorkout();
        Participate participate = participateRepository.findByAccountAndRace(account.getId(), workoutRecord.getRace().getId())
                .orElseThrow(() -> new NoSuchParticipateException(ErrorCode.NO_SUCH_PARTICIPATE));
        participate.updateFromConfirm(workoutRecord.getRecordDate());
        return ConfirmCreateResultDto.createDto(true, savedConfirm);
    }

    private String storeImageFile(MultipartFile imageFile) {
        String[] s = imageFile.getOriginalFilename().split("\\.");
        String fileName = UUID.randomUUID() + "_" + LocalTime.now().toSecondOfDay() + "." + s[s.length - 1];
        Path targetLocation = IMAGE_DIR.resolve(fileName);
        try {
            System.out.println(fileName);
            Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fileName;
    }

    @Transactional
    public ConfirmDeleteResultDto deleteConfirmation(HttpServletRequest request, Long confirmId) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = accountService.getAccountByKakaoId(kakaoId);
        Confirm confirm = getConfirm(confirmId);
        checkConfirmOwner(account.getId(), confirm);

        WorkoutRecord workoutRecord = confirm.getWorkoutRecord();
        confirmRepository.delete(confirm);
        workoutRecord.revertConfirmation();
        return ConfirmDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    private void checkConfirmOwner(Long accountId, Confirm confirm) {
        if (!confirm.getWorkoutRecord().getAccount().getId().equals(accountId)) {
            throw new WrongConfirmOwnerException(ErrorCode.WRONG_CONFIRM_OWNER);
        }
    }

    public Confirm getConfirm(Long confirmId) {
        return confirmRepository.findById(confirmId)
                .orElseThrow(() -> new NoSuchConfirmationException(ErrorCode.NO_SUCH_CONFIRMATION));
    }

    public ConfirmCheckResultDto isConfirmed(Long recordId) {
        WorkoutRecord workoutRecord = workoutRecordService.getWorkoutRecord(recordId);
        AtomicReference<ConfirmCheckResultDto> confirmCheckResultDto = new AtomicReference<>();
        confirmRepository
                .findByRecord(workoutRecord).ifPresentOrElse(
                (confirmWorkout) -> confirmCheckResultDto.set(ConfirmCheckResultDto.createDto(true, confirmWorkout.getId()))
                , () -> confirmCheckResultDto.set(ConfirmCheckResultDto.createDto(false, null)));
        return confirmCheckResultDto.get();
    }

    private void checkUserInRace(Long accountId, Long raceId) {
        if (!participateRepository.existsByAccountAndRace(accountId, raceId)) {
                throw new NoSuchParticipateException(ErrorCode.NO_SUCH_PARTICIPATE);
        }
    }

    private void checkAlreadyConfirmed(WorkoutRecord workoutRecord, Race race) {
        if (confirmRepository.findByRecord(workoutRecord).isPresent()) {
            throw new AlreadyConfirmedException(ErrorCode.ALREADY_CONFIRMED);
        }
    }

    private Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = IMAGE_DIR.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            }else {
                throw new NoSuchImageException(fileName + " 파일을 찾을 수 없습니다.", ErrorCode.NO_SUCH_IMAGE);
            }
        } catch(MalformedURLException e) {
            throw new NoSuchImageException(fileName + " 파일을 찾을 수 없습니다.", ErrorCode.NO_SUCH_IMAGE);
        }
    }
}