package com.codenseek.bac.src;

import com.codenseek.bac.src.util.GameKind;
import com.codenseek.bac.src.util.Constants;
import com.codenseek.bac.src.util.MessageUtils;
import com.codenseek.bac.src.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * 숫자야구 게임의 초기 화면 구현 클래스
 * - 사용자가 게임에서 사용할 단어의 자릿수를 입력하고 이를 확인하는 기능 제공
 */
public class InitialScreen extends JPanel {
    private final JComboBox<GameKind> gameKindCombo;  // 게임 종류를 선택하는 콤보 박스
    private final JTextField wordLengthField;   // 자릿수를 입력받는 텍스트 필드

    /**
     * 초기 화면 설정 및 자릿수 입력 필드와 확인 버튼 배치
     * - 확인 버튼 클릭 시 입력된 자릿수를 확인하고, 유효한 입력일 경우 게임 시작
     *
     * @param frame EntryFrame 객체
     */
    public InitialScreen(EntryFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /**
         * 게임 종류 지정
         */
        // 패널
        JPanel gameKindPanel = new JPanel();
        gameKindPanel.setLayout(new BoxLayout(gameKindPanel, BoxLayout.Y_AXIS));
        gameKindPanel.setBorder(Constants.COMMON_EMPTY_BORDER);

        // 레이블
        JLabel gameKindLabel = new JLabel("게임 종류를 선택하세요.", JLabel.CENTER);
        gameKindLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameKindPanel.add(gameKindLabel);

        // 콤보박스(숫자/영어단어)
        gameKindCombo = new JComboBox<>(GameKind.values());
        gameKindCombo.setPreferredSize(new Dimension(80, 25));  // 크기 조절
        gameKindCombo.setMaximumSize(gameKindCombo.getPreferredSize());
        gameKindCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameKindPanel.add(gameKindCombo);

        add(gameKindPanel);

        /**
         * 자릿수 지정
         */
        // 패널
        JPanel wordLengthPanel = new JPanel();
        wordLengthPanel.setLayout(new BoxLayout(wordLengthPanel, BoxLayout.Y_AXIS));
        gameKindPanel.setBorder(Constants.COMMON_EMPTY_BORDER);

        // 레이블
        JLabel wordLengthLabel = new JLabel("자릿수를 입력하세요.(3~7)", JLabel.CENTER);
        wordLengthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordLengthPanel.add(wordLengthLabel);

        // 자릿수를 입력받는 텍스트 필드
        wordLengthField = new JTextField(2);
        wordLengthField.setMaximumSize(wordLengthField.getPreferredSize());  // 최대 크기를 선호 크기로 제한
        wordLengthField.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordLengthPanel.add(wordLengthField);

        add(wordLengthPanel);

        /**
         * 확인 버튼
         */
        // 패널
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.Y_AXIS));
        confirmPanel.setBorder(Constants.COMMON_EMPTY_BORDER);

        // 확인 버튼
        JButton confirmButton = new JButton(Constants.CHECK);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(confirmButton);

        confirmButton.addActionListener(e -> {
            try {
                // 게임 종류
                GameKind gameKind = (GameKind) gameKindCombo.getSelectedItem();

                if (Objects.isNull(gameKind)) {
                    MessageUtils.showErrorMessage(this, Messages.SELECT_GAME_KIND);
                    return; // 게임 종류가 선택되지 않은 경우 메서드 종료
                }

                // 자릿수 입력 여부
                String wordLengthText = wordLengthField.getText();
                if(wordLengthText.trim().isEmpty()) {
                    MessageUtils.showErrorMessage(this, Messages.ERROR_EMPTY_LENGTH);
                    return;
                }

                // 자릿수 판단
                int wordLength = Integer.parseInt(wordLengthText);
                if(wordLength < 3 || wordLength > 7) {
                    MessageUtils.showErrorMessage(this, Messages.INVALID_WORD_LENGTH);
                } else {
                    frame.startGame(gameKind, wordLength);
                }
            } catch (NumberFormatException ex) {
                // 숫자가 아닌 값을 입력한 경우 경고 메시지를 출력
                MessageUtils.showErrorMessage(this, Messages.ERROR_ONLY_NUMBERS);
            }
        });
    }
}
