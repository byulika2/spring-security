package com.security.domain.common.controller;

import com.security.auth.PrincipalDetails;
import com.security.model.User;
import com.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

  private final UserRepository userRepository;

  @GetMapping("/test/login")
  @ResponseBody
  public String loginTest(Authentication authentication,
      @AuthenticationPrincipal PrincipalDetails userDetails) {
    log.info("test/login ==================");

    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

    log.info("authentication: {}", principalDetails.getUser());
    log.info("userDetails: {}", userDetails.getUser());
    return "세션";
  }

  @GetMapping("/test/oauth/login")
  @ResponseBody
  public String testOauthLogin(Authentication authentication,
      @AuthenticationPrincipal OAuth2User oauth) {
    log.info("/test/oauth/login ==================");

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    log.info("authentication: {}", oAuth2User.getAttributes());
    log.info("oauth2User: {}", oauth.getAttributes());

    return "Oauth2 세션";
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/user")
  @ResponseBody
  public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    log.info("principalDetails {}", principalDetails.getAttributes());
    return "user";
  }

  @GetMapping("/admin")
  @ResponseBody
  public String admin() {
    return "admin";
  }

  @GetMapping("/manager")
  @ResponseBody
  public String manager() {
    return "manager";
  }

  @GetMapping("/login-form")
  public String loginForm() {
    return "login_form";
  }

  @GetMapping("/join-form")
  public String joinForm() {
    return "join_form";
  }

  @PostMapping("/join")
  public String join(User user) {
    user.setRole("ROLE_USER");
    user.setPassword(user.getPassword());
    userRepository.save(user);
    return "redirect:/login-form";
  }

  @Secured("ROLE_ADMIN") // 단일권한
  @GetMapping("/info")
  @ResponseBody
  public String info() {
    return "개인정보";
  }

  //  @PostAuthorize()
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 권한 여러개일경우
  @GetMapping("/data")
  @ResponseBody
  public String data() {
    return "데이터 정보";
  }
}
