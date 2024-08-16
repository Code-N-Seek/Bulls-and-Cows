package com.codenseek.bac.src;

import javax.swing.*;
import java.awt.*;

/**
 * 숫자야구 게임의 초기 화면 구현 클래스
 * - 사용자가 게임에서 사용할 단어의 자릿수를 입력하고 이를 확인하는 기능 제공
 *
 * TODO: 영어/숫자 선택 기능 구현
 */
public class InitialScreen extends JPanel {
    private final JTextField wordLengthField;   // 자릿수를 입력받는 텍스트 필드

    /**
     * 초기 화면 설정 및 자릿수 입력 필드와 확인 버튼 배치
     * - 확인 버튼 클릭 시 입력된 자릿수를 확인하고, 유효한 입력일 경우 게임 시작
     *
     * @param frame EntryFrame 객체
     */
    public InitialScreen(EntryFrame frame) {
        setLayout(new BorderLayout());

        // 자릿수 레이블
        JLabel wordLengthLabel = new JLabel("자릿수를 입력하세요.(3~7)", JLabel.CENTER);
        add(wordLengthLabel, BorderLayout.NORTH);

        // 자릿수를 입력받는 텍스트 필드
        wordLengthField = new JTextField(1);
        wordLengthField.setPreferredSize(new Dimension(10, 10));
        add(wordLengthField, BorderLayout.CENTER);

        // 확인 버튼
        JButton confirmButton = new JButton("확인");
        add(confirmButton, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> {
            try {
                int wordLength = Integer.parseInt(wordLengthField.getText());

                if(wordLength < 3 || wordLength > 7) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "숫자/영어의 자릿수는 3~7 사이여야 합니다.",
                            "입력 오류",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    frame.startGame(wordLength);
                }
            } catch (NumberFormatException ex) {
                // 숫자가 아닌 값을 입력한 경우 경고 메시지를 출력
                JOptionPane.showMessageDialog(
                        frame,
                        "유효한 숫자를 입력하세요.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
