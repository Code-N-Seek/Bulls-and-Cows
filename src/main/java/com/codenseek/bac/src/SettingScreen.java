package com.codenseek.bac.src;

import com.codenseek.bac.src.config.GameSettings;
import com.codenseek.bac.src.util.InputFilters;
import com.codenseek.bac.src.message.Messages;
import com.codenseek.bac.src.util.*;

import static com.codenseek.bac.src.message.MessageUtils.*;
import static com.codenseek.bac.src.ui.GbcUtils.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.Objects;

/**
 * 숫자야구 게임의 초기 화면 구현 클래스
 * - 사용자가 게임에서 사용할 단어의 자릿수를 입력하고 이를 확인하는 기능 제공
 */
public class SettingScreen extends JPanel {
    private JComboBox<GameKind> gameKindCombo = null;  // 게임 종류를 선택하는 콤보 박스
    private JTextField inputLengthField = null;   // 자릿수를 입력받는 텍스트 필드
    private final GridBagConstraints gbc; // GridBagConstraints 객체
    private GameKind gameKind;  // 게임 종류
    private int inputLength; // 단어 길이


    /**
     * 초기 화면 설정 및 자릿수 입력 필드와 확인 버튼 배치
     * - 확인 버튼 클릭 시 입력된 자릿수를 확인하고, 유효한 입력일 경우 게임 시작
     *
     * @param frame EntryFrame 객체
     */
    public SettingScreen(EntryFrame frame) {
        setLayout(new GridBagLayout());

        // 기본 GridBagConstraints 설정
        gbc = createDefaultGbc();

        // 기존 입력 설정 가져오기
        GameSettings savedSettings = GameSettings.getSavedSettings();
        if(savedSettings != null) {
            gameKindCombo.setSelectedItem(savedSettings.getGameKind());
            inputLengthField.setText(String.valueOf(savedSettings.getInputLength()));
        }

        /**
         * 게임 종류 지정
         */
        // 레이블
        JLabel gameKindLabel = createGameKindLabel();
        add(gameKindLabel, gbc);

        // 콤보박스(숫자/영어단어)
        gameKindCombo = createGameKindCombo();
        add(gameKindCombo, gbc);

        /**
         * 자릿수 지정
         */
        // 레이블
        JLabel inputLengthLabel = createWordLengthLabel();
        add(inputLengthLabel, gbc);

        // 자릿수를 입력받는 텍스트 필드
        inputLengthField = createInputLengthField();
        add(inputLengthField, gbc);

        /**
         * 확인 버튼
         */
        JButton confirmButton = createConfirmButton(frame);
        confirmButton.addActionListener(e -> {
            if (isInputValid()) {
                GameSettings.builder()
                        .gameKind(gameKind)
                        .inputLength(inputLength);
                frame.startGame(gameKind, inputLength);
            }
        });
        add(confirmButton, gbc);
    }

    /**
     * 게임 종류 선택 레이블 생성
     *
     * @return 게임 종류 선택을 알리는 JLabel
     */
    private JLabel createGameKindLabel() {
        setGbcConstraints(gbc, 0, 2, 0, null);

        return new JLabel("게임 종류를 선택하세요.", JLabel.CENTER);
    }

    /**
     * 게임 종류 선택 콤보박스 생성
     *
     * @return GameKind를 선택할 수 있는 JComboBox
     */
    private JComboBox<GameKind> createGameKindCombo() {
        setGbcConstraints(gbc, 1, 2, null, null);

        JComboBox<GameKind> comboBox = new JComboBox<>(GameKind.values());
        comboBox.setPreferredSize(new Dimension(80, 25));
        comboBox.setBackground(Color.WHITE);
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);  // 텍스트 가운데 정렬
        return comboBox;
    }

    /**
     * 자릿수 입력 레이블 생성
     *
     * @return 자릿수를 입력하도록 안내하는 JLabel
     */
    private JLabel createWordLengthLabel() {
        setGbcConstraints(gbc, 2, null, 2, null);
        return new JLabel("자릿수를 입력하세요.(3 ~ 7)", JLabel.CENTER);
    }

    /**
     * 자릿수 입력 텍스트 필드 생성
     *
     * @return 자릿수를 입력받을 JTextField
     */
    private JTextField createInputLengthField() {
        setGbcConstraints(gbc, 3, 2, null, null);

        JTextField textField = new JTextField(3);
        textField.setHorizontalAlignment(JTextField.CENTER);  // 텍스트 가운데 정렬
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new InputFilters.PlaceValueFilter());  // 필터 설정
        return textField;
    }

    /**
     * 확인 버튼 생성
     *
     * @param frame EntryFrame 객체
     * @return 게임 시작을 위한 확인 JButton
     */
    private JButton createConfirmButton(EntryFrame frame) {
        setGbcConstraints(gbc, 4, 20, null, null);

        return new JButton(Constants.CHECK);
    }

    /**
     * 게임 종류와 자릿수 입력 필드 유효성 검사하는 메서드
     *
     * @return 모든 입력이 유효하면 true, 그렇지 않으면 false
     */
    private boolean isInputValid() {
        try {
            if (!isGameKindSelected() || !isInputLengthValid()) {
                return false;
            }
        } catch (NumberFormatException ex) {
            showErrorMessage(this, Messages.ERROR_ONLY_NUMBERS);
            return false;
        }

        return true;
    }

    /**
     * 콤보 박스에서 게임 종류가 선택되었는지 확인하는 메서드
     *
     * @return 게임 종류가 선택되었으면 true, 그렇지 않으면 false
     */
    private boolean isGameKindSelected() {
        gameKind = (GameKind) gameKindCombo.getSelectedItem();

        if (Objects.isNull(gameKind)) {
            showErrorMessage(this, Messages.SELECT_GAME_KIND);
            return false;
        }
        return true;
    }

    /**
     * 자릿수 입력 필드 유효성 검사 메서드
     *
     * @return 자릿수가 유효하면 true, 그렇지 않으면 false
     */
    private boolean isInputLengthValid() {
        // 입력받은 단어 길이 텍스트
        String inputLengthText = inputLengthField.getText();
        if (inputLengthText.trim().isEmpty()) {
            showErrorMessage(this, Messages.ERROR_EMPTY_LENGTH);
            return false;
        }

        inputLength = Integer.parseInt(inputLengthText);
        if (inputLength < 3 || inputLength > 7) {
            showErrorMessage(this, Messages.INVALID_WORD_LENGTH);
            return false;
        }

        return true;
    }
}
