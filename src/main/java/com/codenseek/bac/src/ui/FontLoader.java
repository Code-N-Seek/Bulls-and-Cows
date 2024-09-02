package com.codenseek.bac.src.ui;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 사용자 지정 폰트 로드 및 등록 클래스
 */
@Getter
@Slf4j
public class FontLoader {
    private Font customFont;

    /**
     * 주어진 폰트 파일 경로와 폰트 크기를 사용하여 FontLoader 객체 초기화
     *
     * @param fontPath 로드할 폰트 파일의 경로를 나타내는 문자열
     *                 (리소스 폴더 내에서의 상대 경로)
     * @param fontSize 사용할 폰트의 크기를 나타내는 부동 소수점 숫자
     * @throws RuntimeException 폰트 파일을 찾을 수 없거나 로드할 수 없는 경우 발생
     */
    public FontLoader(String fontPath, float fontSize) {
        try {
            InputStream is = getClass().getResourceAsStream(fontPath);
            if(Objects.isNull(is)) {
                throw new RuntimeException("Font file not found: " + fontPath);
            }

            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 모든 UI 컴포넌트에 대한 기본 폰트 설정
     *
     * @param fontUIResource 새로운 기본 폰트를 지정하는 {@code FontUIResource} 객체
     */
    public static void setUIFont(FontUIResource fontUIResource) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource original) {
                Font font = new Font(fontUIResource.getFontName(), original.getStyle(), fontUIResource.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
}
