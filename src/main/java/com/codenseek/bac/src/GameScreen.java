package com.codenseek.bac.src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * 숫자야구 게임의 실제 게임 진행 화면 구현 클래스
 * - 게임의 입력 필드, 힌트 버튼, 과거 입력 내역 관리
 * - 게임의 시작/초기화 기능 제공
 *
 * TODO: 정답 판별 및 처리 구현
 */
public class GameScreen extends JPanel {
    private int wordLength; // 게임에서 사용할 단어의 자릿수
    private final JPanel inputPanel;    // 사용자 입력 패널
    private JTextField[] inputFields;   // 사용자 입력 필드 배열
    private final DefaultTableModel historyTableModel;  // 과거 입력 내역 테이블 모델
    private String[] columnNames;   // 테이블의 열 이름 배열
    private final JButton startResetButton; // 게임 시작/초기화 버튼
    private boolean isGameStarted = false;  // 게임 시작 여부


    /**
     * 게임에서 사용할 단어의 자릿수를 설정하고, 화면을 업데이트
     *
     * @param wordLength 게임에서 사용할 단어의 자릿수
     */
    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
        setColumnNames();
        updateGameScreen();
    }

    /**
     * 테이블의 열 이름 설정
     */
    public void setColumnNames() {
        // 컬럼 이름 설정
        columnNames = new String[wordLength + 1];
        for(int i = 0; i < wordLength; i++) {
            columnNames[i] = "input" + (i + 1); // 입력 필드 이름 설정
        }
        columnNames[wordLength] = "result"; // 결과 열 이름 설정

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

        /**
         * 상단 패널 설정
         */
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(UIConstants.COMMON_EMPTY_BORDER);

        // 제목
        JLabel label = new JLabel("BULLS AND COWS", JLabel.CENTER);
        label.setFont(new Font("Times", Font.BOLD, 24));
        label.setBorder(UIConstants.COMMON_EMPTY_BORDER);
        topPanel.add(label, BorderLayout.NORTH);

        JPanel managePanel = new JPanel(new BorderLayout());
        managePanel.setBorder(new EmptyBorder(20, 10, 10, 10));

        // 힌트 버튼
        JButton hintButton = new JButton("힌트");
        managePanel.add(hintButton, BorderLayout.WEST);

        // 전광판
        JLabel boardLabel = new JLabel("");
        managePanel.add(boardLabel, BorderLayout.CENTER);

        // 시작/초기화 버튼
        startResetButton = new JButton("시작");
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
        JButton checkButton = new JButton("확인");
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
        JTable historyTable = new JTable(historyTableModel);
        historyTable.setTableHeader(null);

        // 열 너비 설정
        TableColumnModel columnModel = historyTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(1);
        }

        // 과거 입력 내역 테이블을 스크롤 가능하도록 설정
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setPreferredSize(new Dimension(100, 250));

        historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.SOUTH);
    }

    /**
     * 게임 시작
     * - 화면 업데이트 및 시작/초기화 버튼의 텍스트 변경
     */
    private void startGame() {
        updateGameScreen();
        startResetButton.setText("초기화");
        isGameStarted = true;
    }

    /**
     * 게임 초기화
     * - 입력 필드와 과거 입력 내역을 초기 상태로 되돌림
     */
    private void resetGame() {
        historyTableModel.setRowCount(0);   // 과거 입력 내역 초기화
        for(JTextField inputField : inputFields) {
            inputField.setText(""); // 입력 필드 초기화
        }
        startResetButton.setText("시작");
        isGameStarted = false;
    }

    /**
     * 게임 화면 업데이트
     * - 사용자 입력 필드 재설정 및 과거 입력 내역 초기화
     */
    public void updateGameScreen() {
        // 입력칸 갱신
        inputPanel.removeAll();
        inputFields = new JTextField[wordLength];
        for (int i = 0; i < wordLength; i++) {
            JTextField input = new JTextField(2);
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
            JOptionPane.showMessageDialog(
                    this,
                    "입력 칸이 초기화되지 않았습니다.",
                    "오류",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 사용자 입력 값 수집
        String[] inputs = new String[wordLength];
        for(int i = 0; i < wordLength; i++) {
            inputs[i] = inputFields[i].getText().trim();
            inputFields[i].setText(""); // 입력칸 초기화
        }

        // TODO: 전광판 내용 적용 필요
        String result = "1S 1B";

        // 새로운 입력 내역을 과거 입력 내역에 추가
        String[] historyEntry = new String[wordLength + 1];
        System.arraycopy(inputs, 0, historyEntry, 0, wordLength);
        historyEntry[wordLength] = result;
        historyTableModel.addRow(historyEntry);
    }
}
