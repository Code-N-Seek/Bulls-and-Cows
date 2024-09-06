package com.codenseek.bac.src.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 프로퍼티 파일을 로드하고 관리하는 클래스
 */
@Slf4j
public class PropertyManager {
    // 캐시된 프로퍼티 파일을 관리하는 맵
    private static final Map<String, Properties> propertiesCache = new HashMap<>();

    // 프로퍼티 기본 경로
    private static final String PROPERTIES_PATH = "/properties";

    /**
     * 주어진 파일 이름에 해당하는 프로퍼티 파일을 로드하는 메서드
     *
     * @param fileName 로드할 프로퍼티 파일의 이름 (확장자 ".properties"는 제외)
     * @return 로드된 {@link Properties} 객체, 파일이 없거나 로드에 실패하면 null 반환
     * @throws RuntimeException 프로퍼티 파일을 로드하는 동안 발생하는 I/O 오류
     */
    public static Properties getProperties(String fileName) {
        // 캐시에서 먼저 확인
        if(propertiesCache.containsKey(fileName)) {
            return propertiesCache.get(fileName);
        }

        // 캐시에 없으면 새로 로드
        Properties properties = new Properties();
        String filePath = PROPERTIES_PATH + "/" + fileName + ".properties";

        try(InputStream input = PropertyManager.class.getResourceAsStream(filePath)) {
            if(Objects.isNull(input)) {
                log.error("파일을 찾을 수 없습니다: {}", filePath);
                // 파일을 찾을 수 없을 때도 캐시에 null을 저장하여 중복 로드를 방지
                propertiesCache.put(fileName, null);
                return null;  // null을 반환
            }
            properties.load(input);
            // 캐시에 저장
            propertiesCache.put(fileName, properties);
        } catch (IOException e) {
            throw new RuntimeException("프로퍼티 파일을 로드하는 중 오류 발생: " + filePath, e);
        }

        return properties;
    }

    /**
     * 주어진 파일에서 특정 키에 해당하는 값을 반환하는 메서드
     *
     * @param fileName 값을 가져올 프로퍼티 파일의 이름 (확장자 ".properties"는 제외)
     * @param key 값을 가져올 프로퍼티 키
     * @return 키에 해당하는 값, 존재하지 않으면 null 반환
     */
    public static String getProperty(String fileName, String key) {
        Properties properties = getProperties(fileName);
        return Objects.nonNull(properties) ? properties.getProperty(key) : null;
    }
}
