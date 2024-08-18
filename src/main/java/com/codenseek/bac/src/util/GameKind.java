package com.codenseek.bac.src.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 게임 종류를 나타내는 열거형 클래스
 */
@RequiredArgsConstructor
@Getter
public enum GameKind {
    NUMBER("숫자"),
    WORD("영어단어");

    /**
     * 게임 종류를 설명하는 문자열 값
     */
    private final String value;

    /**
     * 열거형 상수의 문자열 표현을 반환
     *
     * @return 열거형 상수에 해당하는 문자열 값
     */
    @Override
    public String toString() {
        return value;
    }
}
