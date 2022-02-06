package com.security.model;

import com.security.util.PasswordConverter;
import java.time.LocalDateTime;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Table(name = "USER")
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;

  @Convert(converter = PasswordConverter.class)
  private String password;

  private String email;

  private String role;

  private String provider;

  private String providerId;

  @CreationTimestamp
  private LocalDateTime createAt;

  @Builder
  public User(String username, String password, String email, String role, String provider, String providerId) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
    this.provider = provider;
    this.providerId = providerId;
  }
}
