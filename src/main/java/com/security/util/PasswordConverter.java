package com.security.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

@Slf4j
@Converter
@RequiredArgsConstructor
public class PasswordConverter implements AttributeConverter<String, String> {

  private String secretKey = "_chris1234";


  /**
   * DB에 데이터 저장, 확인시 자동 컨버트
   *
   * @param attribute
   * @return
   */
  @Override
  public String convertToDatabaseColumn(String attribute) {
    if (!StringUtils.hasText(attribute)) {
      return new String();
    }

    return new BCryptPasswordEncoder().encode(attribute + secretKey);
  }


  /**
   * 데이터 조회시 자동 컨버트
   *
   * @param dbData
   * @return
   */
  @Override
  public String convertToEntityAttribute(String dbData) {
    return dbData;
  }
}
