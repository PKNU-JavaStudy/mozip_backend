package com.mozip.web;

import com.mozip.dto.req.project.FindEmailDto;
import com.mozip.dto.req.member.JoinMemberDto;
import com.mozip.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 로그인, 회원가입, 아이디찾기, 비밀번호 찾기 URL 이 해당 컨트롤러로 옴
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    // find_id 페이지
    @GetMapping("/auth/findId")
    public String findIdForm() {
        return "auth/find_id";
    }

    // find_password 페이지
    @GetMapping("/auth/findPw")
    public String findPwForm() {
        return "auth/find_password";
    }

    // join 페이지
    @GetMapping("/auth/join")
    public String joinForm() {
        return "auth/join";
    }

    // 회원가입 처리
    @PostMapping("/auth/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinMemberDto joinMemberDto, BindingResult bindingResult) {
            authService.joinUser(joinMemberDto);
            return ResponseEntity.ok().body("{\"success\":true}");

    }


    // login 페이지
    @GetMapping("/auth/login")
    public String loginForm() {
        return "auth/login";
    }



    // TODO : 아이디 찾기 로직 수정해라 김동건;;
    @PostMapping("/auth/findId")
    @ResponseBody
    public String findId(@Valid @ModelAttribute FindEmailDto findEmailDto,
                         BindingResult bindingResult) {

        String email = authService.findMemberEmail(findEmailDto);

        if (email != null) {
            return "<script>alert('찾으시는 이메일은 " + email + " 입니다.'); window.location='/auth/login';</script>";
        } else {
            return "<script>alert('일치하는 정보가 없습니다.'); window.location='/auth/findId';</script>";
        }
    }
}

