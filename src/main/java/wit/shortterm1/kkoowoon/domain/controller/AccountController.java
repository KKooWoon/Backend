package wit.shortterm1.kkoowoon.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.dto.request.SignUpRequestDto;
import wit.shortterm1.kkoowoon.domain.dto.response.LoginResponseDto;
import wit.shortterm1.kkoowoon.domain.dto.response.TempResponse;
import wit.shortterm1.kkoowoon.domain.dto.response.UserInfoResponseDto;
import wit.shortterm1.kkoowoon.domain.service.AccountService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseEntity<TempResponse> signUp(@RequestBody SignUpRequestDto signUpReqDto) {
        return new ResponseEntity<>(accountService.signUp(signUpReqDto), HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto) {
//        return new ResponseEntity<>(accountService.login(loginDto.getEmail(), loginDto.getPassword()), HttpStatus.OK);
//    }

    @GetMapping("/re-issue")
    public ResponseEntity<LoginResponseDto> reIssue(@RequestParam("nickname") String nickname, @RequestParam("refreshToken") String refreshToken) {
        return new ResponseEntity<>(accountService.reIssueAccessToken(nickname, refreshToken), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<TempResponse> logout(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        return new ResponseEntity<>(accountService.logout(accessToken), HttpStatus.OK);
    }

    @GetMapping("/oauth/kakao")
    public ResponseEntity<LoginResponseDto> newLogin(@RequestParam("code") String code) {
        return new ResponseEntity<>(accountService.newLogin(code), HttpStatus.OK);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoResponseDto> userInfo(@RequestParam("nickname") String nickname) {
        return new ResponseEntity<>(accountService.getUserInfo(nickname), HttpStatus.OK);
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
