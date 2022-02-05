package com.security.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

  // getAttributes;
  private Map<String, Object> attributes;

  public KakaoUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getProviderId() {
    return attributes.get("id") + "";
  }

  @Override
  public String getProvider() {
    return "kakao";
  }

  @Override
  public String getEmail() {
    // kakao는 kakao_account에 유저정보가 있다. (email)
    Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
    return (String) kakaoAccount.get("email");
  }

  @Override
  public String getName() {
    // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
    Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
    return (String) kakaoProfile.get("nickname");
  }
}
