package com.codenseek.bac.src.util;

import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * UI 공통 사용 상수 모음 클래스
 */
public class Constants {
    /**
     * UI 요소들 간의 일정한 공백을 유지하기 위한 여백(Border)
     */
    public static final EmptyBorder COMMON_EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);

    /**
     * 버튼 공통 크기 설정
     */
    public static final Dimension buttonSize = new Dimension(80, 30); // 원하는 크기로 설정

    /**
     * 버튼 공통 색상 설정
     */
    public static final Color THEME_COLOR_NAVY = new Color(46, 51, 67);

    public static final Color THEME_COLOR_YELLOW = new Color(253, 227, 87);

    /**
     * 아이콘 크기 설정
     */
    public static final int ICON_WIDTH = 20;

    public static final int ICON_HEIGHT = 20;

    /**
     * TEXT 모음
     */
    public static final String TITLE = "BULLS AND COWS";

    public static final String NUMBER = "숫자";

    public static final String WORD = "영어단어";

    public static final String NOTIFICATION = "알림";

    public static final String CHECK = "확인";

    public static final String CORRECT_ANSWER = "정답";

    public static final String HINT = "힌트";

    public static final String START = "시작";

    public static final String RESTART = "재시작";

    public static final String EXIT = "종료";

    public static final String ERROR = "ERROR";
}
