package com.security.domain.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {

  @GetMapping("/")
  public String index(){
    return "index";
  }

  @GetMapping("/admin")
  public String user(){
    return "admin";
  }

  @GetMapping("/manager")
  public String manager(){
    return "manager";
  }

  @GetMapping("/login")
  @ResponseBody
  public String login(){
    return "login";
  }

  @GetMapping("/join")
  public String join(){
    return "join";
  }

  @GetMapping("/join-proc")
  @ResponseBody
  public String joinProc(){
    return "회원가입 완료";
  }


}
