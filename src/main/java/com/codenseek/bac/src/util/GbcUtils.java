package com.codenseek.bac.src.util;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * GridBagConstraints를 기본 설정과 패딩으로 구성하기 위한 유틸리티 클래스
 */
public final class GbcUtils {
    /**
     * 기본 패딩 값(초기값 : 10)
     */
    @Getter
    @Setter
    private static int defaultPadding = 10;

    /**
     * 이 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자
     */
    private GbcUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 기본 설정이 완료된 GridBagConstraints 객체를 생성하는 메서드
     *
     * @return 기본 설정이 완료된 GridBagConstraints 객체
     */
    public static GridBagConstraints createDefaultGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        return gbc;
    }

    /**
     * 주어진 GridBagConstraints 객체의 insets(여백) 설정
     * - 모든 면(상단, 좌측, 하단, 우측)에 지정된 값으로 패딩 설정
     *
     * @param gbc     설정할 GridBagConstraints 객체
     * @param padding 여백으로 설정할 패딩 값
     */
    public static void configureGbcWithPadding(GridBagConstraints gbc, int padding) {
        gbc.insets = new Insets(padding, padding, padding, padding);
    }
}
