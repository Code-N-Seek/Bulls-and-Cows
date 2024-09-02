package com.codenseek.bac.src;

import com.codenseek.bac.src.config.ConfigLoader;
import com.codenseek.bac.src.ui.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.codenseek.bac.src.ui.GbcUtils.configureGbcWithPadding;
import static com.codenseek.bac.src.ui.GbcUtils.createDefaultGbc;


/**
 * 숫자야구 게임의 메인 메뉴 화면 클래스
 * - 게임의 타이틀 표시 및 게임 시작 버튼 제공
 */
public class MainMenuScreen extends JPanel {
    /**
     * 메인 메뉴 화면 설정 및 게임 시작 버튼 배치
     * - 버튼을 클릭하면 초기 화면으로 전환
     *
     * @param frame EntryFrame 객체
     */
    public MainMenuScreen(EntryFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGbc();
        configureGbcWithPadding(gbc, 15);
        gbc.weightx = 1.0;

        // 게임 타이틀 설정(로고)
        JLabel titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String imagePath = Objects.requireNonNull(ConfigLoader.loadProperties("message")).getProperty("image.path");
        ImageIcon titleIcon = ImageUtils.loadAndScaleImage(imagePath, null, null);
        titleLabel.setIcon(titleIcon); // 크기 조정된 이미지를 JLabel에 설정

        // 타이틀 레이블을 중앙 위쪽에 배치
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // 게임 시작 버튼 설정
        JButton startButton = new JButton("게임 시작");
        startButton.setPreferredSize(new Dimension(90, 30));

        // 게임 시작 버튼을 타이틀 바로 아래에 배치
        gbc.gridy = 1;
        add(startButton, gbc);

        startButton.addActionListener(e -> frame.showScreen("initialScreen"));
    }
}
