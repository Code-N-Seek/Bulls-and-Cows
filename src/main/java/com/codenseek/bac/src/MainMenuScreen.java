package com.codenseek.bac.src;

import com.codenseek.bac.src.util.UIConstants;

import javax.swing.*;
import java.awt.*;


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
        setLayout(new BorderLayout());

        // 게임 타이틀을 표시하는 레이블
        JLabel titleLabel = new JLabel("BULLS AND COWS", JLabel.CENTER);
        titleLabel.setFont(new Font("Times", Font.BOLD, 24));
        titleLabel.setBorder(UIConstants.COMMON_EMPTY_BORDER);
        add(titleLabel, BorderLayout.NORTH);

        // 게임 시작 버튼
        JButton startButton = new JButton("게임 시작");
        startButton.setBorder(UIConstants.COMMON_EMPTY_BORDER);
        add(startButton, BorderLayout.CENTER);

        startButton.addActionListener(e -> frame.showScreen("initialScreen"));
    }
}
