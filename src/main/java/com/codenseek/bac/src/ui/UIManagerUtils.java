package com.codenseek.bac.src.ui;

import com.codenseek.bac.src.util.Constants;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.*;

/**
 * UI 컴포넌트의 테마 및 스타일 속성을 설정하기 위한 유틸리티 클래스
 */
public class UIManagerUtils {
    /**
     * 버튼 스타일 속성 초기화
     *
     * @param button 버튼
     */
    public static void initButtonProperties(JButton button) {
        button.setText("");                  // 텍스트 제거
        button.setContentAreaFilled(false);  // 배경 채우기 제거
        button.setBorderPainted(false);      // 테두리 제거
        button.setFocusPainted(false);       // 포커스 표시 제거
    }

    /**
     * 버튼의 스타일 속성 설정
     */
    public static void setButtonProperties() {
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("Button.rollover", Boolean.FALSE);
        UIManager.put("Button.background", Constants.THEME_COLOR_YELLOW);
    }

    /**
     * 옵션 창의 스타일 속성 설정
     */
    public static void setOptionPaneProperties() {
        UIManager.put("OptionPane.messageForeground", Constants.THEME_COLOR_YELLOW);
        UIManager.put("Panel.background", Constants.THEME_COLOR_NAVY);
        UIManager.put("OptionPane.background", Color.WHITE);
    }

    /**
     * 레이블의 스타일 속성 설정
     */
    public static void setLabelProperties() {
        UIManager.put("Label.foreground", Constants.THEME_COLOR_YELLOW);
    }


    /**
     * 프로젝트 전체에 적용될 UI 테마 설정
     */
    public static void setTheme() {
        setButtonProperties();
        setOptionPaneProperties();
        setLabelProperties();
    }
}
