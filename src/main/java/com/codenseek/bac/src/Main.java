package com.codenseek.bac.src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        // 프레임 셋팅
        frame.setTitle("Bulls and Cows");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 500);
        frame.setLocationRelativeTo(null);

        // 컨텐트 팬 설정
        Container contentPane = frame.getContentPane();

        // 여백 설정
        EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);

        /**
         * 상단(제목, 힌트 버튼, 전광판, 시작/초기화 버튼)
         */
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(emptyBorder);

        // 제목
        JLabel label = new JLabel("BULLS AND COWS", JLabel.CENTER);
        label.setFont(new Font("Times", Font.BOLD, 24));
        label.setBorder(emptyBorder);
        topPanel.add(label, BorderLayout.NORTH);

        JPanel managePanel = new JPanel(new BorderLayout());
        managePanel.setBorder(new EmptyBorder(20, 10, 10, 10));

        // 힌트 버튼
        JButton hintButton = new JButton("HINT");
        managePanel.add(hintButton, BorderLayout.WEST);

        // 전광판
        JLabel boardLabel = new JLabel("");
        managePanel.add(boardLabel, BorderLayout.CENTER);

        // 시작/초기화 버튼
        JButton startResetButton = new JButton("START");
        managePanel.add(startResetButton, BorderLayout.EAST);

        topPanel.add(managePanel, BorderLayout.SOUTH);

        contentPane.add(topPanel, BorderLayout.NORTH);

        /**
         * 입력
         */
        JPanel submitPanel = new JPanel(new BorderLayout());
        submitPanel.setBorder(new EmptyBorder(0, 10, 20, 20));

        // 입력칸
        JPanel inputPanel = new JPanel(new FlowLayout());
        for(int i = 0; i < 5; i++) {
            JTextField input = new JTextField(2);
            inputPanel.add(input);
        }
        submitPanel.add(inputPanel, BorderLayout.CENTER);

        // 확인버튼
        JButton checkButton = new JButton("CHECK");
        submitPanel.add(checkButton, BorderLayout.EAST);

        contentPane.add(submitPanel, BorderLayout.CENTER);

        /**
         * 과거 입력 내역
         */
        String[] columnNames = {
                "input1",
                "input2",
                "input3",
                "input4",
                "input5",
                "result"
        };
        String[][] histories = {
                {"A", "A", "A", "A", "A", "1S 1B"},
                {"B", "B", "B", "B", "B", "1S 1B"},
                {"C", "C", "C", "C", "C", "1S 1B"},
                {"D", "D", "D", "D", "D", "1S 1B"},
                {"E", "E", "E", "E", "E", "1S 1B"}
        };
        JTable historyTable = new JTable(histories, columnNames);
        historyTable.setTableHeader(null);

        TableColumnModel columnModel = historyTable.getColumnModel();
        for(int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(1);
        }

        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setPreferredSize(new Dimension(100, 250)); // 스크롤팬의 크기 설정

        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        contentPane.add(historyPanel, BorderLayout.SOUTH);

        frame.setVisible(true);   // 화면에 프레임 출력
    }
}