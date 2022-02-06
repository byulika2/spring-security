package com.security.config.oauth;

import com.security.auth.PrincipalDetails;
import com.security.config.oauth.provider.FaceBookUserInfo;
import com.security.config.oauth.provider.GoogleUserInfo;
import com.security.config.oauth.provider.KakaoUserInfo;
import com.security.config.oauth.provider.NaverUserInfo;
import com.security.config.oauth.provider.OAuth2UserInfo;
import com.security.model.User;
import com.security.repository.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  private final String secretKey = "chris1234";

  // 구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("getClientRegistration: {}", userRequest.getClientRegistration());
    log.info("getAccessToken: {}", userRequest.getAccessToken().getTokenValue());

    OAuth2User oAuth2User = super.loadUser(userRequest);
    // userRequest 정보: 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 -> code 리턴(Oauth-Client 라이브러리) -> AccessToken 요청
    // userRequest 정보 -> loadUser() -> 회원프로필
    log.info("getAttributes: {}", oAuth2User.getAttributes());

    OAuth2UserInfo oAuth2UserInfo = null;
    switch (userRequest.getClientRegistration().getRegistrationId()) {
      case "google":
        oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        log.info("구글 로그인 요청");
        break;
      case "facebook":
        oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
        log.info("페이스북 로그인 요청");
        break;

      case "kakao":
        oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        log.info("카카오 로그인 요청");
        break;

      case "naver":

        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> response = (Map<String, Object>)oAuth2User.getAttributes().get("response");

        oAuth2UserInfo = new NaverUserInfo(response);
        log.info("네이버 로그인 요청");
        break;

      default:
        log.error("지원하지 않는 소셜 로그인!!");
    }

    String provider = oAuth2UserInfo.getProvider();
    String providerId = oAuth2UserInfo.getProviderId();
    String username = provider + "_" + providerId; // google_108123987123021
    String password = username + "_" + secretKey;
    String email = oAuth2UserInfo.getEmail();
    String role = "ROLE_USER";

    User user = userRepository.findByUsername(username).orElse(null);

    if (user == null) {
      user = userRepository.save(User
          .builder()
          .username(username)
          .email(email)
          .password(password)
          .provider(provider)
          .providerId(providerId)
          .role(role)
          .build());
    }

    return new PrincipalDetails(user, oAuth2User.getAttributes());
  }
}
