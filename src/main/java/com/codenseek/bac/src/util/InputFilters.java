package com.codenseek.bac.src.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * JTextField 컴포넌트에 대한 입력 필터를 포함하는 유틸리티 클래스
 */
public final class InputFilters {
    /**
     * 이 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자
     *
     * @throws UnsupportedOperationException 인스턴스화가 시도될 경우 발생
     */
    private InputFilters() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 정규 표현식 패턴에 기반하여 JTextField 입력을 제한하는 필터를 생성하기 위한 추상 클래스
     */
    private static abstract class AbstractPatternFilter extends DocumentFilter {
        private final String regex;

        /**
         * 지정된 정규 표현식 패턴으로 AbstractPatternFilter를 생성
         *
         * @param regex 입력이 일치해야 하는 정규 표현식 패턴
         */
        protected AbstractPatternFilter(String regex) {
            this.regex = regex;
        }

        /**
         * 문자열이 Document에 삽입될 때 입력 필터링
         *
         * @param fb     Document를 수정하는 데 사용할 수 있는 FilterBypass 객체
         * @param offset 내용을 삽입할 Document 내의 위치
         * @param string 삽입할 문자열
         * @param attr   삽입된 내용과 연결할 속성
         * @throws BadLocationException 삽입이 잘못된 위치에서 시도될 경우 발생
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if ((fb.getDocument().getLength() + string.length()) <= 1 && string.matches(regex)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        /**
         * Document의 문자열이 교체될 때 입력 필터링
         *
         * @param fb     Document를 수정하는 데 사용할 수 있는 FilterBypass 객체
         * @param offset 내용을 교체할 Document 내의 위치
         * @param length 교체할 텍스트의 길이
         * @param text   현재 내용을 교체할 텍스트
         * @param attrs  교체된 내용과 연결할 속성
         * @throws BadLocationException 교체가 잘못된 위치에서 시도될 경우 발생
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if ((fb.getDocument().getLength() + text.length() - length) <= 1 && text.matches(regex)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    /**
     * 3에서 7 사이의 숫자만 삽입을 허용하는 필터(단어 자릿수 설정)
     */
    public static class PlaceValueFilter extends AbstractPatternFilter {
        private static final String PLACE_VALUE_REGEX = "[3-7]";

        public PlaceValueFilter() {
            super(PLACE_VALUE_REGEX);
        }
    }

    /**
     * 0에서 9 사이의 한 자릿수 숫자만 삽입을 허용하는 필터
     */
    public static class OneDigitFilter extends AbstractPatternFilter {
        private static final String ONE_DIGIT_REGEX = "[0-9]";

        public OneDigitFilter() {
            super(ONE_DIGIT_REGEX);
        }
    }

    /**
     * 하나의 문자(대문자 또는 소문자)만 삽입을 허용하는 필터
     */
    public static class OneWordFilter extends AbstractPatternFilter {
        private static final String ONE_WORD_REGEX = "[a-zA-Z]";

        public OneWordFilter() {
            super(ONE_WORD_REGEX);
        }
    }
}
