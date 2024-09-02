package com.codenseek.bac.src.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;


/**
 * 애플리케이션 설정을 로드하기 위한 유틸리티 클래스
 */
@Slf4j
public class ConfigLoader {
    static Properties properties = new Properties();

    /**
     * 생성자를 private으로 선언하여 클래스의 인스턴스화 방지
     */
    private ConfigLoader() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * 주어진 파일 이름에 해당하는 properties 파일 로드
     * - 파일 이름에 ".properties" 확장자 자동 추가
     *
     * @param fileName 로드할 properties 파일의 이름(확장자 제외)
     * @return 로드된 {@code Properties} 객체, 파일을 찾지 못한 경우 null을 반환
     * @throws IOException 파일을 읽는 동안 발생한 예외
     */
    public static Properties loadProperties(String fileName) {
        fileName += ".properties";
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (Objects.isNull(input)) {
                System.out.println("Sorry, unable to find " + fileName);
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return properties;
    }
}
