package com.codenseek.bac.src.message;

/**
 * 게임에서 사용되는 다양한 문자열 메시지를 상수로 포함하는 클래스
 * - 게임 선택에 대한 프롬프트, 오류 알림, 결과 메시지 포함
 */
public class Messages {
    public static final String SELECT_GAME_KIND = "게임 종류를 선택해 주세요.";
    public static final String ERROR_EMPTY_LENGTH = "값을 입력해주세요.";
    public static final String INVALID_WORD_LENGTH = "자릿수는 3~7 사이여야 합니다.";
    public static final String ERROR_ONLY_NUMBERS = "숫자만 입력가능합니다.";
    public static final String ERROR_ONLY_LETTERS = "영어 단어만 입력할 수 있습니다.";
    public static final String ERROR_INPUT_NOT_INITIALIZED = "입력 칸이 초기화되지 않았습니다.";
    public static final String ERROR_EMPTY_FIELDS = "모든 입력 칸에 값을 입력해야 합니다.";
    public static final String CORRECT_ANSWER = "정답입니다!";
    public static final String GAME_OVER = "정답 찾기에 실패했습니다.";
}
