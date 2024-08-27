package com.codenseek.bac.src;

import com.codenseek.bac.src.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 숫자야구 게임의 실제 게임 진행 화면 구현 클래스
 * - 게임의 입력 필드, 힌트 버튼, 과거 입력 내역 관리
 * - 게임의 시작/초기화 기능 제공
 *
 * TODO: 뒤로가기, 남은 시도 횟수, 사용자 입력시 한개씩만 입력가능하도록 이벤트 핸들러 설정, 기록내역 오름차순 출력
 */
public class GameScreen extends JPanel {
    private GameKind gameKind;    // 게임 진행 종류(숫자/영어단어)
    private int wordLength; // 게임에서 사용할 단어의 자릿수
    private String answer;  // 정답
    private final JLabel boardLabel;  // 전광판
    private final JPanel inputPanel;    // 사용자 입력 패널
    private JTextField[] inputFields;   // 사용자 입력 필드 배열
    private final DefaultTableModel historyTableModel;  // 과거 입력 내역 테이블 모델
    private String[] columnNames;   // 테이블의 열 이름 배열
    private final JButton startResetButton; // 게임 시작/초기화 버튼
    private final JButton checkButton; // 확인 버튼
    private boolean isGameStarted = false;  // 게임 시작 여부
    private int attemptCount = 0;   // 시도 횟수
    private static final int MAX_ATTEMPT_COUNT = 9;  // 최대 시도 횟수
    private final JButton hintButton;   // 힌트 버튼
    private int hintCount;  // 힌트 갯수
    private static final int DEFAULT_HINT_COUNT = 1;    // 기본 힌트 갯수


    /**
     * 게임 초기 셋팅 및 화면 업데이트
     *
     * @param gameKind 게임 종류
     * @param wordLength 게임에서 사용할 단어의 자릿수
     */
    public void initGameSetting(GameKind gameKind, int wordLength) {
        this.gameKind = gameKind;
        this.wordLength = wordLength;
        this.answer = AnswerPicker.getRandomAnswer(gameKind, wordLength);
        attemptCount = 0;
        //hintCount = DEFAULT_HINT_COUNT;
        setColumnNames();
        updateGameScreen();
    }

    /**
     * 테이블의 열 이름 설정
     */
    private void setColumnNames() {
        // 컬럼 이름 설정
        columnNames = new String[wordLength + 2];
        columnNames[0] = "입력횟수";
        for(int i = 1; i <= wordLength; i++) {
            columnNames[i] = ""; // 입력 필드 이름 설정
        }
        columnNames[wordLength + 1] = "결과"; // 결과 열 이름 설정

        historyTableModel.setColumnIdentifiers(columnNames); // 테이블 열 이름 업데이트
    }

    /**
     * 게임 화면의 레이아웃과 구성 요소를 초기화
     * - 게임의 제목, 입력 필드, 버튼, 과거 입력 내역 테이블 설정
     *
     * @param frame EntryFrame 객체
     */
    public GameScreen(EntryFrame frame) {
        setLayout(new BorderLayout());

        // 버튼 공통 크기 설정
        Dimension buttonSize = new Dimension(80, 30); // 원하는 크기로 설정

        /**
         * 상단 패널 설정
         */
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(Constants.COMMON_EMPTY_BORDER);

        // 제목
        JLabel label = new JLabel(Constants.TITLE, JLabel.CENTER);
        label.setFont(new Font("Times", Font.BOLD, 24));
        label.setBorder(Constants.COMMON_EMPTY_BORDER);
        topPanel.add(label, BorderLayout.NORTH);

        JPanel managePanel = new JPanel(new BorderLayout());
        managePanel.setBorder(new EmptyBorder(20, 10, 10, 10));

        // 힌트 버튼
        hintButton = new JButton(getHintButtonName());
        hintButton.setEnabled(false);
        hintButton.setPreferredSize(new Dimension(120, 30));
        managePanel.add(hintButton, BorderLayout.WEST);

        hintButton.addActionListener(e -> provideHint());

        // 전광판
        boardLabel = new JLabel("");
        managePanel.add(boardLabel, BorderLayout.CENTER);

        // 시작/초기화 버튼
        startResetButton = new JButton(Constants.START);
        startResetButton.setPreferredSize(buttonSize);
        managePanel.add(startResetButton, BorderLayout.EAST);

        startResetButton.addActionListener(e -> {
            if(isGameStarted) {
                resetGame();
            } else {
                startGame();
            }
        });

        topPanel.add(managePanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        /**
         * 입력
         */
        JPanel submitPanel = new JPanel(new BorderLayout());
        submitPanel.setBorder(new EmptyBorder(0, 10, 20, 20));

        // 입력칸
        inputPanel = new JPanel(new FlowLayout());
        submitPanel.add(inputPanel, BorderLayout.CENTER);

        // 확인 버튼
        checkButton = new JButton(Constants.CHECK);
        checkButton.setEnabled(false);
        checkButton.setPreferredSize(buttonSize);  // 동일한 크기 적용
        submitPanel.add(checkButton, BorderLayout.EAST);

        checkButton.addActionListener(e -> handleUserInput());

        add(submitPanel, BorderLayout.CENTER);

        /**
         *  과거 입력 내역
         */
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // 과거 입력 내역 테이블
        historyTableModel = new DefaultTableModel(columnNames, 0);
        JTable historyTable = getTable();
        historyPanel.add(historyTable.getTableHeader(), BorderLayout.NORTH);
        historyPanel.add(historyTable, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.SOUTH);
    }

    /**
     * 힌트 버튼 이름 가져오기
     *
     * @return 현재 남은 힌트 개수를 포함한 힌트 버튼 이름
     */
    private String getHintButtonName() {
        return "남은 힌트 : " + hintCount;
    }

    /**
     * 힌트 갯수 설정
     */
    private void setHintCount() {
        //hintCount = wordLength > 5 ? 3 : wordLength > 3 ? 2 : 1;
        hintCount = DEFAULT_HINT_COUNT;
    }

    /**
     * 사용자에게 힌트를 제공하는 메서드
     * - 힌트 메시지를 생성하여 알림으로 표시하고, 남은 힌트 개수를 업데이트
     * - 힌트가 모두 소진되면 버튼을 비활성화
     */
    private void provideHint() {
        String msg = "힌트 : 같은 " + gameKind.getValue() + "이/가 존재";
        Set<String> answers = new HashSet<>();
        for(char c : answer.toCharArray()) {
            answers.add(String.valueOf(c));
        }
        msg += (answer.length() != answers.size()) ? "합니다." : "하지 않습니다.";
        MessageUtils.showNotificationMessage(this, msg);

        hintCount--;
        hintButton.setText(getHintButtonName());

        if(hintCount == 0) {
            hintButton.setEnabled(false);
        }
    }

    /**
     * 과거 입력 내역을 보여주는 JTable 객체를 생성하여 반환
     * - 테이블의 열 너비 설정 및 각 열의 내용 가운데 정렬
     * - 테이블의 크기 및 스크롤 설정
     *
     * @return 과거 입력 내역을 보여주는 JTable 객체
     */
    private JTable getTable() {
        JTable historyTable = new JTable(historyTableModel);

        // 열 너비 설정 및 가운데 정렬 적용
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        historyTable.setDefaultRenderer(Object.class, centerRenderer);

        TableColumnModel columnModel = historyTable.getColumnModel();
        for(int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(50);
            column.setCellRenderer(centerRenderer);
        }

        // 테이블 크기 설정 (행당 높이 * 최대 입력 횟수, 열당 너비 합계)
        int rowHeight = historyTable.getRowHeight(); // 기본 행 높이
        int tableWidth = historyTable.getPreferredSize().width; // 열 너비 합계
        int tableHeight = rowHeight * MAX_ATTEMPT_COUNT;

        // 과거 입력 내역 테이블을 스크롤 가능하도록 설정
        historyTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
        historyTable.setFillsViewportHeight(true); // 테이블이 패널을 꽉 채우도록 설정

        return historyTable;
    }

    /**
     * 게임 시작
     * - 화면 업데이트 및 시작/초기화 버튼의 텍스트 변경
     */
    private void startGame() {
        attemptCount = 0;
        boardLabel.setText("");
        updateGameScreen();
        startResetButton.setText(Constants.RESTART);
        isGameStarted = true;

        setFieldsEnabled(true);
        setHintCount(); // hint 갯수 초기화
        checkButton.setEnabled(true);
        hintButton.setText(getHintButtonName());
        hintButton.setEnabled(true);
    }

    /**
     * 게임 종료
     * - 화면 업데이트 및 시작/초기화 버튼의 텍스트 변경
     */
    private void endGame() {
        startResetButton.setText(Constants.START);  // 시작/초기화 버튼 텍스트를 "시작"으로 변경
        isGameStarted = false;  // 게임 시작 여부를 false로 설정

        setFieldsEnabled(false);  // 입력 필드 비활성화
        checkButton.setEnabled(false);  // 확인 버튼 비활성화
        hintButton.setEnabled(false);   // 힌트 버튼 비활성화
    }

    /**
     * 게임 재시작/종료
     * - 입력 필드와 과거 입력 내역을 초기 상태로 되돌림
     */
    private void resetGame() {
        startResetButton.setText(Constants.START);
        isGameStarted = false;

        setFieldsEnabled(false);
        checkButton.setEnabled(false);
        hintButton.setEnabled(false);

        initGameSetting(gameKind, wordLength);
        startGame();
    }

    /**
     * 입력 필드의 활성화/비활성화 설정
     *
     * @param enabled 입력 필드 활성화 여부
     */
    private void setFieldsEnabled(boolean enabled) {
        if (inputFields != null) {
            for (JTextField inputField : inputFields) {
                inputField.setEnabled(enabled);
            }
        }
    }

    /**
     * 게임 화면 업데이트
     * - 사용자 입력 필드 재설정 및 과거 입력 내역 초기화
     */
    public void updateGameScreen() {
        // 입력칸 갱신
        inputPanel.removeAll();
        inputFields = new JTextField[wordLength];
        for(int i = 0; i < wordLength; i++) {
            JTextField input = new JTextField(2);
            input.setEnabled(false);
            inputFields[i] = input;

            inputPanel.add(input);
        }
        inputPanel.revalidate();    // 입력 패널 갱신
        inputPanel.repaint();   // 입력 패널 다시 그리기

        // 과거 입력 내역 테이블 초기화
        historyTableModel.setRowCount(0);
    }

    /**
     * 사용자가 입력한 값 처리
     * - 입력 값이 올바른지 확인하고, 결과를 과거 입력 내역에 추가
     */
    private void handleUserInput() {
        if(inputFields == null || inputFields.length != wordLength) {
            MessageUtils.showErrorMessage(this, Messages.ERROR_INPUT_NOT_INITIALIZED);
            return;
        }

        // 모든 입력 필드가 비어있지 않은지 확인
        for(JTextField inputField : inputFields) {
            if(inputField.getText().trim().isEmpty()) {
                MessageUtils.showErrorMessage(this, Messages.ERROR_EMPTY_FIELDS);
                return;
            }
        }

        // 사용자 입력 값 수집
        String[] inputs = new String[wordLength];
        for(int i = 0; i < wordLength; i++) {
            String input = inputFields[i].getText().trim();

            // 입력이 숫자 게임의 경우 숫자인지, 영어 게임의 경우 알파벳인지 확인
            if (Constants.NUMBER.equals(gameKind.name()) && !input.matches("[0-9]+")) {
                MessageUtils.showErrorMessage(this, Messages.ERROR_ONLY_NUMBERS);
                return;
            } else if (Constants.WORD.equals(gameKind.name()) && !input.matches("[a-zA-Z]+")) {
                MessageUtils.showErrorMessage(this, Messages.ERROR_ONLY_LETTERS);
                return;
            }

            inputs[i] = input;  // 검증 통과한 입력을 배열에 저장
            inputFields[i].setText(""); // 입력칸 초기화
        }

        // 정답 체크
        String result = checkAnswer(inputs);

        // 시도 횟수 증가 및 체크
        attemptCount++;
        if (attemptCount >= MAX_ATTEMPT_COUNT) {
            MessageUtils.showNotificationMessage(this, Messages.GAME_OVER);
            boardLabel.setText(Constants.CORRECT_ANSWER + ": " + answer);
            endGame();
            return;
        }

        if (result.equals(Constants.CORRECT_ANSWER)) {
            MessageUtils.showNotificationMessage(this, Messages.CORRECT_ANSWER);
            endGame();
        } else {
            boardLabel.setText(result);
        }

        // 새로운 입력 내역을 과거 입력 내역에 추가
        String[] historyEntry = new String[wordLength + 2];
        historyEntry[0] = attemptCount + "회"; // 첫 번째 요소에 attemptCount 값 추가
        System.arraycopy(inputs, 0, historyEntry, 1, wordLength);
        historyEntry[wordLength + 1] = result;
        historyTableModel.addRow(historyEntry);
    }

    /**
     * 사용자가 입력한 값 처리
     * - 입력 값이 올바른지 확인하고, 결과를 과거 입력 내역에 추가
     *
     * @param inputWord 사용자 입력 단어
     * @return 정답 비교 결과
     */
    private String checkAnswer(String[] inputWord) {
        int sCnt = 0;
        int bCnt = 0;
        int answerLength = answer.length();

        boolean[] isStrike = new boolean[answerLength];
        boolean[] isBallChecked = new boolean[answerLength]; // 볼로 이미 체크된 자리 추적

        // strike 계산
        for(int i = 0; i < answerLength; i++) {
            if(inputWord[i].equals(String.valueOf(answer.charAt(i)))) {
                sCnt++;
                isStrike[i] = true;
            }
        }

        // ball 계산
        for(int i = 0; i < answerLength; i++) {
            if (isStrike[i]) {
                continue; // 이미 스트라이크로 처리된 자리는 건너뜀
            }

            for(int j = 0; j < answerLength; j++) {
                if (!isStrike[j] && !isBallChecked[j]
                        && inputWord[i].equals(String.valueOf(answer.charAt(j)))) {
                    bCnt++;
                    isBallChecked[j] = true; // 이 자리도 더 이상 볼로 계산되지 않도록 마킹
                    break; // 일치하는 자리를 찾았으므로 내부 루프 종료
                }
            }
        }
        // 정답인 경우
        if(sCnt == answerLength) {
            return Constants.CORRECT_ANSWER;
        }
        return sCnt + "S " + bCnt + "B";
    }
}
