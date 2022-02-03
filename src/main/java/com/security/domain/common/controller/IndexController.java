package com.security.domain.common.controller;

import com.security.model.User;
import com.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/admin")
  public String user() {
    return "admin";
  }

  @GetMapping("/manager")
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
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return "redirect:/login-form";
  }

  @Secured("ROLE_ADMIN") // 단일권한
  @GetMapping("/info")
  @ResponseBody
  public String info(){
    return "개인정보";
  }

//  @PostAuthorize()
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 권한 여러개일경우
  @GetMapping("/data")
  @ResponseBody
  public String data(){
    return "데이터 정보";
  }
}
