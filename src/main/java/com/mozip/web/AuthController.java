package com.mozip.web;

import com.mozip.domain.member.Member;
import com.mozip.dto.req.FindEmailDto;
import com.mozip.dto.req.LoginDto;
import com.mozip.dto.req.JoinMemberDto;
import com.mozip.handler.ex.CustomValidationException;
import com.mozip.service.AuthService;
import com.mozip.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public String join(@Valid @ModelAttribute JoinMemberDto joinMemberDto, BindingResult bindingResult) {
        authService.joinUser(joinMemberDto);
        return "redirect:/auth/login";
    }

    // login 페이지
    @GetMapping("/auth/login")
    public String loginForm() {
        return "auth/login";
    }

    // login 처리
    @PostMapping("/auth/login")
    public String login(@Valid @ModelAttribute LoginDto dto,
                        BindingResult bindingResult,
                        HttpServletRequest req) {
        Member loginMember = authService.login(dto);
        if (loginMember != null) {
            HttpSession session = req.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        }
        // HTTP Session 이용
        return "redirect:/";
    }

    // logout
    @PostMapping("/auth/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();

        return "redirect:/";
    }

    // 아이디 찾기
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

