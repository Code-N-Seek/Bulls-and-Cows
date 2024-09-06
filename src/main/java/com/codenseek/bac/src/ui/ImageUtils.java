package com.codenseek.bac.src.ui;

import com.codenseek.bac.src.config.PropertyManager;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Properties;

/**
 * 이미지 처리 유틸리티 클래스
 */
@Slf4j
public class ImageUtils {
    private static final String BASE_PATH_KEY = "base.path";  // 기본 경로 키

    /**
     * 주어진 키에 해당하는 이미지 파일 경로를 생성하는 메서드
     *
     * @param key 이미지 경로에 해당하는 키 (예: "logo")
     * @return 기본 경로와 파일명을 결합한 이미지 파일 경로
     *         (예: "/images/logo.png")
     */
    public static String getImagePath(String key) {
        Properties properties = PropertyManager.getProperties("image");
        assert properties != null;
        String basePath = properties.getProperty(BASE_PATH_KEY, "/images"); // 기본 경로 설정
        String imagePath = basePath + "/" + properties.getProperty(key);
        return imagePath.startsWith("/") ? imagePath : "/" + imagePath;
    }

    /**
     * 주어진 경로의 이미지를 로드하고, 지정된 크기로 조정
     *
     * @param imagePath 이미지 경로 (예: "/images/bulls_and_cows.png")
     * @param width     원하는 가로 크기, {@code null}일 경우 원본 가로 크기 사용
     * @param height    원하는 세로 크기, {@code null}일 경우 원본 세로 크기 사용
     * @return 크기 조정된 이미지의 {@code ImageIcon} 객체
     * @throws NullPointerException 지정된 경로에서 이미지를 찾을 수 없는 경우 발생
     */
    public static ImageIcon loadAndScaleImage(String imagePath, Integer width, Integer height) {
        // 이미지 로드
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(ImageUtils.class.getResource(imagePath),
                "이미지 경로를 찾을 수 없습니다: " + imagePath));

        Image originalImage = originalIcon.getImage();

        // 원본 크기 사용 여부 결정
        int imageWidth = (Objects.nonNull(width)) ? width : originalIcon.getIconWidth();
        int imageHeight = (Objects.nonNull(height)) ? height : originalIcon.getIconHeight();

        // 이미지 크기 조정
        Image scaledImage = originalImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
