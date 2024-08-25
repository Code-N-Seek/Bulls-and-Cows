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
public class AnswerPicker {
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
        try(InputStream inputStream = AnswerPicker.class.getResourceAsStream("/words.txt")) {
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
     * 지정된 게임 종류와 길이에 따라 무작위로 단어 또는 숫자를 반환
     *
     * @param gameKind 게임 종류
     * @param length 생성할 단어의 길이 또는 숫자의 자릿수
     * @return 무작위 단어 또는 숫자 반환
     * @throws IllegalArgumentException 단어/숫자의 길이가 0 이하이거나 지원하지 않는 게임 종류가 지정된 경우 발생
     */
    public static String getRandomAnswer(GameKind gameKind, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("The length must be greater than 0.");
        }

        return switch (gameKind) {
            case WORD -> getRandomWord(length);
            case NUMBER -> String.valueOf(getRandomNumber(length));
            default -> throw new IllegalArgumentException("Unsupported gameKind: " + gameKind);
        };
    }

    /**
     * 정적 단어 리스트에서 무작위로 단어를 선택하여 반환하는 메서드
     *
     * @param wordLength 선택할 단어의 길이
     * @return 길이가 wordLength인 무작위로 선택된 단어 / null
     */
    private static String getRandomWord(int wordLength) {
        List<String> filteredWords = words.stream()
                                            .filter(word -> word.length() == wordLength)
                                            .toList();

        if (!filteredWords.isEmpty()) {
            int randomIndex = generateRandomNumber(filteredWords.size());
            return filteredWords.get(randomIndex);
        } else {
            return null; // 리스트가 비어 있을 경우 null 반환
        }
    }

    /**
     * 지정된 자릿수 범위 내에서 무작위 난수를 생성하여 반환하는 메서드
     *
     * @param digits 자릿수 범위
     * @return 지정된 자릿수 내에서 생성된 난수
     */
    private static int getRandomNumber(int digits) {
        int lowerBound = (int) Math.pow(10, digits - 1);
        int upperBound = (int) Math.pow(10, digits) - 1;
        return generateRandomNumber(lowerBound, upperBound);
    }

    /**
     * 리스트 크기를 기반으로 난수 생성 (0부터 upperBound-1까지)
     *
     * @param upperBound 난수 생성 범위의 상한
     * @return 생성된 난수
     */
    private static int generateRandomNumber(int upperBound) {
        return secureRandom.nextInt(upperBound);
    }

    /**
     * 주어진 범위 내에서 무작위 난수를 생성하여 반환하는 메서드
     *
     * @param lowerBound 난수의 하한
     * @param upperBound 난수의 상한
     * @return 주어진 범위 내의 난수
     */
    private static int generateRandomNumber(int lowerBound, int upperBound) {
        return secureRandom.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }
}
