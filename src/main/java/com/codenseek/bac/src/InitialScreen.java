package com.codenseek.bac.src;

import com.codenseek.bac.src.util.*;

import static com.codenseek.bac.src.util.MessageUtils.*;
import static com.codenseek.bac.src.util.GbcUtils.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
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
        setLayout(new GridBagLayout());

        // 기본 GridBagConstraints 설정
        GridBagConstraints gbc = createDefaultGbc();

        /**
         * 게임 종류 지정
         */
        // 레이블
        JLabel gameKindLabel = new JLabel("게임 종류를 선택하세요.", JLabel.CENTER);
        gbc.gridy = 0;
        gbc.insets.bottom = 2;
        add(gameKindLabel, gbc);

        // 콤보박스(숫자/영어단어)
        gameKindCombo = new JComboBox<>(GameKind.values());
        gameKindCombo.setPreferredSize(new Dimension(80, 25));  // 크기 조절
        // 텍스트 가운데 정렬 설정
        ((JLabel)gameKindCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets.top = 2;
        gbc.insets.bottom = getDefaultPadding();
        add(gameKindCombo, gbc);

        /**
         * 자릿수 지정
         */
        // 레이블
        JLabel wordLengthLabel = new JLabel("자릿수를 입력하세요.(3 ~ 7)", JLabel.CENTER);
        gbc.gridy = 2;
        gbc.insets.top = getDefaultPadding();
        gbc.insets.bottom = 2;
        add(wordLengthLabel, gbc);

        // 자릿수를 입력받는 텍스트 필드
        wordLengthField = new JTextField(3);
        gbc.gridy = 3;
        // 텍스트 가운데 정렬 설정
        wordLengthField.setHorizontalAlignment(JTextField.CENTER);
        // 한 자릿수만 입력 가능하도록 필터 설정
        ((AbstractDocument) wordLengthField.getDocument()).setDocumentFilter(new InputFilters.PlaceValueFilter());
        gbc.insets.top = 2;
        gbc.insets.bottom = getDefaultPadding();
        add(wordLengthField, gbc);

        /**
         * 확인 버튼
         */
        JButton confirmButton = new JButton(Constants.CHECK);
        gbc.gridy = 4;
        gbc.insets.top = 20;
        gbc.insets.bottom = getDefaultPadding();
        add(confirmButton, gbc);

        confirmButton.addActionListener(e -> {
            try {
                // 게임 종류
                GameKind gameKind = (GameKind) gameKindCombo.getSelectedItem();

                if (Objects.isNull(gameKind)) {
                    showErrorMessage(this, Messages.SELECT_GAME_KIND);
                    return; // 게임 종류가 선택되지 않은 경우 메서드 종료
                }

                // 자릿수 입력 여부
                String wordLengthText = wordLengthField.getText();
                if(wordLengthText.trim().isEmpty()) {
                    showErrorMessage(this, Messages.ERROR_EMPTY_LENGTH);
                    return;
                }

                // 자릿수 판단
                int wordLength = Integer.parseInt(wordLengthText);
                if(wordLength < 3 || wordLength > 7) {
                    showErrorMessage(this, Messages.INVALID_WORD_LENGTH);
                } else {
                    frame.startGame(gameKind, wordLength);
                }
            } catch (NumberFormatException ex) {
                // 숫자가 아닌 값을 입력한 경우 경고 메시지를 출력
                showErrorMessage(this, Messages.ERROR_ONLY_NUMBERS);
            }
        });
    }
}
