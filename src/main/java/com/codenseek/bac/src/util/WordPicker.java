package com.codenseek.bac.src.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 단어 무작위 선택 기능 제공 클래스
 */
@Slf4j
public class WordPicker {
    private static final List<String> words = new ArrayList<>();
    private static final SecureRandom secureRandom;

    static {
        SecureRandom tempRandom = null;
        try {
            tempRandom = SecureRandom.getInstanceStrong(); // 강력한 SecureRandom 인스턴스 얻기
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to create random number");
            tempRandom = new SecureRandom(); // 실패 시 기본 SecureRandom 사용
        }
        secureRandom = tempRandom;

        // 리소스 파일을 읽어 단어 리스트에 저장
        try(InputStream inputStream = WordPicker.class.getResourceAsStream("/words.txt")) {
            assert inputStream != null;
            try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while((line = br.readLine()) != null) {
                    words.add(line);    // 한 줄씩 읽어 리스트에 추가
                }
            }
        } catch(IOException e) {
            log.error("Failed to open file");
        }
    }

    /**
     * 정적 단어 리스트에서 무작위로 단어를 선택하여 반환하는 메서드
     *
     * @return 무작위로 선택된 단어 / null
     */
    public static String getRandomWord() {
        if (!words.isEmpty()) {
            int randomIndex = secureRandom.nextInt(words.size()); // 0부터 words.size() - 1까지의 난수 생성
            return words.get(randomIndex); // 해당 인덱스의 단어 가져오기
        } else {
            return null; // 리스트가 비어 있을 경우 null 반환
        }
    }
}
