package com.codenseek.bac.src;

import com.codenseek.bac.src.ui.ImageUtils;
import com.codenseek.bac.src.util.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

import static com.codenseek.bac.src.ui.GbcUtils.configureGbcWithPadding;
import static com.codenseek.bac.src.ui.GbcUtils.createDefaultGbc;


/**
 * 숫자야구 게임의 메인 메뉴 화면 클래스
 * - 게임의 타이틀 표시 및 게임 시작 버튼 제공
 */
@Slf4j
public class MainMenuScreen extends JPanel {
    private final GridBagConstraints gbc;
    /**
     * 메인 메뉴 화면 설정 및 게임 시작 버튼 배치
     * - 버튼을 클릭하면 초기 화면으로 전환
     *
     * @param frame EntryFrame 객체
     */
    public MainMenuScreen(EntryFrame frame) {
        setLayout(new GridBagLayout());
        gbc = createDefaultGbc();
        configureGbcWithPadding(gbc, 15);
        gbc.weightx = 1.0;

        // 타이틀 레이블
        JLabel titleLabel = createTitleLog();
        gbc.gridy = 0;  // 중앙 위쪽 배치
        add(titleLabel, gbc);

        // 게임 시작 버튼
        JButton startButton = createStartButton();
        startButton.addActionListener(e -> frame.showScreen(Constants.SETTING_SCREEN));
        gbc.gridy = 1;  // 타이틀 바로 아래 배치
        add(startButton, gbc);
    }

    /**
     * 로고를 설정한 JLabel을 생성하는 메서드
     *
     * @return 크기 조정된 로고 아이콘이 설정된 JLabel
     */
    private JLabel createTitleLog() {
        JLabel titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String logoImagePath = ImageUtils.getImagePath("logo");
        ImageIcon titleIcon = ImageUtils.loadAndScaleImage(logoImagePath, null, null);
        titleLabel.setIcon(titleIcon); // 크기 조정된 이미지를 JLabel에 설정
        return titleLabel;
    }

    /**
     * "게임 시작" 버튼을 생성하는 메서드
     *
     * @return "게임 시작" 텍스트와 크기가 설정된 JButton
     */
    private JButton createStartButton() {
        JButton startButton = new JButton("게임 시작");
        startButton.setPreferredSize(new Dimension(90, 30));
        return startButton;
    }
}
