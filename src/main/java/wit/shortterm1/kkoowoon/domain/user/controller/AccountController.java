package wit.shortterm1.kkoowoon.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wit.shortterm1.kkoowoon.domain.user.dto.request.SignUpRequestDto;
import wit.shortterm1.kkoowoon.domain.user.dto.response.*;
import wit.shortterm1.kkoowoon.domain.user.service.AccountService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저 관련 API")
@RequestMapping("/api/v1/user")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/oauth/kakao")
    @ApiOperation(value = "카카오 로그인", notes = "카카오에서 발급해준 Authorization Code를 통해 로그인 시도를 하는 API." +
            "만약 회원가입되어 있는 유저라면 리프레시 토큰과 액세스 토큰, 유저 정보를 리턴해주고, 신규회원이라면 DB에 존재하지 않으므로 404 Exception을 터트림.")
    public ResponseEntity<LoginResponseDto> login(
            @ApiParam(value = "카카오에서 발급해준 Authorization Code", required = true, example = "2183u21u90321")
            @RequestParam("code") String code) {
        return new ResponseEntity<>(accountService.login(code), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @ApiOperation(value = "회원가입하기", notes = "회원가입 DTO를 통해 회원가입을 진행하는 API")
    public ResponseEntity<SignUpResultDto> signUp(
            @ApiParam(value = "회원가입 DTO", required = true, example = "회원가입 DTO")
            @RequestBody SignUpRequestDto signUpReqDto) {
        return new ResponseEntity<>(accountService.signUp(signUpReqDto), HttpStatus.CREATED);
    }
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto) {
//        return new ResponseEntity<>(accountService.login(loginDto.getEmail(), loginDto.getPassword()), HttpStatus.OK);

//    }

    @GetMapping("/re-issue")
    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰을 이용해 해당 아이디의 토큰을 재발급받는 API")
    public ResponseEntity<LoginResponseDto> reIssue(
            @ApiParam(value = "본인 ID", required = true, example = "2")
            @RequestParam("accountId") Long accountId,
            @ApiParam(value = "리프레시 토큰", required = true, example = "토큰")
            @RequestParam("refreshToken") String refreshToken) {
        return new ResponseEntity<>(accountService.reIssueAccessToken(accountId, refreshToken), HttpStatus.OK);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "헤더에 있는 Authorization 정보로 로그아웃을 하는 API")
    public ResponseEntity<LogoutResultDto> logout(
            HttpServletRequest request) {
        return new ResponseEntity<>(accountService.logout(request), HttpStatus.OK);
    }

    @GetMapping("/info")
    @ApiOperation(value = "유저 정보 가져오기", notes = "회원ID 값으로 유저 정보 가져오는 API")
    public ResponseEntity<UserInfoDto> userInfo(
            @ApiParam(value = "회원 ID", required = true, example = "2")
            @RequestParam("accountId") Long accountId) {
        return new ResponseEntity<>(accountService.getUserInfo(accountId), HttpStatus.OK);
    }

    @GetMapping("/user-list")
    @ApiOperation(value = "닉네임으로 친구 찾기", notes = "이름값으로 해당하는 사용자 리스트 가져오는 API")
    public ResponseEntity<UserInfoWithFollowListDto> findUserListByNickname(
            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
            @RequestParam("nickname") String nickname,
            HttpServletRequest request) {
        return new ResponseEntity<>(accountService.findUserListByNickname(request, nickname), HttpStatus.OK);
    }

    @GetMapping("/main-page")
    @ApiOperation(value = "메인 페이지 전체 정보 로딩", notes = "로그인 후 메인페이지 로딩을 위해 먼저 호출하는 API")
    public ResponseEntity<MainPageInfoDto> mainPage(
            @ApiParam(value = "본인 ID", required = true, example = "2")
            @RequestParam("accountId") Long accountId) {
        return new ResponseEntity<>(accountService.getMainPageInfo(accountId), HttpStatus.OK);
    }

    @GetMapping("/duplicate")
    @ApiOperation(value = "닉네임 중복 여부를 체크", notes = "닉네임 값을 닉네임 중복 여부를 json으로 리턴해주는 API")
    public ResponseEntity<NicknameDuplicateDto> checkNicknameDuplicate(
            @ApiParam(value = "체크할 닉네임", required = true, example = "꾸운닉123")
            @RequestParam("nickname") String nickname) {
        return new ResponseEntity<>(accountService.isDuplicateNickname(nickname), HttpStatus.OK);
    }

//    @GetMapping("/oauth/kakao")
//    @ResponseBody
//    public String kakaoLoginByCode(@RequestParam("code") String code) {
//        accountService.login(code)
//        String kakaoAccessToken = oauthService.getKakaoAccessToken(code);
//        System.out.println(kakaoAccessToken);
//        int id = oauthService.findKakaoUser(kakaoAccessToken);
//        if (id == -1) {
//
//        }
//        return code;
//    }
}
