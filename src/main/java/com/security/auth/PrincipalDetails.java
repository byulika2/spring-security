package com.security.auth;

// 시큐티가 /login 요청시 로그인을 진행해줌
// 로그인 완료시 시큐리티 세션을 만들어줌(Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User정보가 있어야함
// User 오젝트타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails

import com.security.model.User;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalDetails implements UserDetails {

  private com.security.model.User user; //콤포지션

  public PrincipalDetails(User user) {
    this.user = user;
  }

  // 해당 User의 권한을 리턴
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collection = new ArrayList<>();
    collection.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {
        return user.getRole();
      }
    });
    return collection;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    // 휴면걔정 처리
    // 현재시간 - 로긴 시간 => 1년 초과시 return false;

    return true;
  }
}
