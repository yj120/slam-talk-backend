package sync.slamtalk.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sync.slamtalk.common.ApiResponse;
import sync.slamtalk.user.dto.request.UserChangePasswordReq;
import sync.slamtalk.user.dto.request.UserLoginReq;
import sync.slamtalk.user.dto.request.UserSignUpReq;
import sync.slamtalk.user.dto.request.UserTmpPasswordReq;
import sync.slamtalk.user.service.AuthService;

/**
 * 이 컨트롤러는 인증과 관련된 기능을 다루는 클래스입니다.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "로그인/회원가입")
public class AuthController {

    @Value("${jwt.access.header}")
    public String authorizationHeader;
    @Value("${jwt.refresh.header}")
    public String refreshAuthorizationHeader;
    private final AuthService authService;

    /**
     * 로그인 api
     *
     * @param userLoginReqDto
     * @param response
     * @return jwtTokenResponseDto
     */
    @PostMapping("/login")
    @Operation(
            summary = "자체 로그인 기능",
            description = "자체 로그인 기능입니다."
    )
    public ApiResponse<Void> authorize(
            @Valid @RequestBody UserLoginReq userLoginReqDto,
            HttpServletResponse response
    ) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        authService.login(userLoginReqDto, response);

        return ApiResponse.ok();
    }

    /**
     * 회원 가입 api
     *
     * @param userSignUpReqDto
     * @return 회원가입 성공
     */
    @PostMapping("/sign-up")
    @Operation(
            summary = "자체 회원가입 기능",
            description = "자체 회원가입 기능입니다."
    )
    public ApiResponse<Void> signUp(
            @Valid @RequestBody UserSignUpReq userSignUpReqDto,
            HttpServletResponse response) {
        authService.signUp(userSignUpReqDto, response);
        return ApiResponse.ok();
    }

    /**
     * 회원 가입 api
     *
     * @param userSignUpReqDto
     * @return 회원가입 성공
     */
    @PostMapping("/test/sign-up")
    @Operation(
            summary = "자체 회원가입 기능",
            description = "자체 회원가입 기능입니다."
    )
    public ApiResponse<Void> testSignUp(
            @RequestBody UserSignUpReq userSignUpReqDto,
            HttpServletResponse response) {
        authService.testSignUp(userSignUpReqDto, response);
        return ApiResponse.ok();
    }

    /**
     * 리프래쉬 토큰 재발급 api
     *
     * @param request
     * @param response
     * @return JwtTokenResponseDto
     */
    @PostMapping("/tokens/refresh")
    @Operation(
            summary = "엑세스 및 리프레쉬 토큰 재발급",
            description = "리프레쉬 토큰은 httpOnly secure 쿠키로 보내주고 엑세스 토큰은 헤더와 파라미터에 넣어 보내줍니다."
    )
    public ApiResponse<Void> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.refreshToken(request, response);
        return ApiResponse.ok();
    }

    @PatchMapping("/user/temporary-passwords")
    @Operation(
            summary = "임시 비밀번호 발급",
            description = "사용자가 원하는 이메일로 임시 비밀번호를 재발급 해준다."

    )
    public ApiResponse<Void> sendTemporaryPasswordViaEmail(@Valid @RequestBody UserTmpPasswordReq userTmpPasswordReq){
        authService.issuanceOfTemporaryPassword(userTmpPasswordReq.getEmail());
        return ApiResponse.ok();
    }
    @PatchMapping("/user/password")
    @Operation(
            summary = "유저 비밀번호 변경하기",
            description = "이메일인증을 한 유저의 비밀번호는 특정 비밀번호로 변경이 가능하다."
    )
    public ApiResponse<Void> userChangePassword(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody UserChangePasswordReq userChangePasswordReq
    ) {
        authService.userChangePassword(userId, userChangePasswordReq.getPassword());
        return ApiResponse.ok();
    }

    @DeleteMapping("/user")
    @Operation(
            summary = "회원 탈퇴",
            description = "회원 탈퇴 시 모든 유저의 정보가 사라진다"
    )
    public ApiResponse<Void> userWithdrawal(
            HttpServletRequest request,
            HttpServletResponse response,
            @AuthenticationPrincipal Long userId
    ) {
        authService.userWithdrawal(request, response, userId);
        return ApiResponse.ok();
    }

}
