package com.codenseek.bac.src.util;

import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * UI 공통 사용 상수 모음 클래스
 */
public class Constants {
    /**
     * UI 요소들 간 여백
     */
    public static final EmptyBorder COMMON_EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);

    /**
     * 버튼
     */
    public static final Dimension buttonSize = new Dimension(80, 30); // 원하는 크기로 설정

    /**
     * 테마 색상
     */
    public static final Color THEME_COLOR_NAVY = new Color(46, 51, 67);

    public static final Color THEME_COLOR_YELLOW = new Color(253, 227, 87);

    /**
     * 아이콘
     */
    public static final int ICON_WIDTH = 20;

    public static final int ICON_HEIGHT = 20;

    /**
     * FONT
     */
    public static final String FONT_PATH = "/fonts/Binggrae.ttf";

    /**
     * 화면 이름
     */
    public static final String MAIN_MENU_SCREEN = "mainMenuScreen";

    public static final String SETTING_SCREEN = "settingScreen";

    public static final String GAME_SCREEN = "gameScreen";

    /**
     * 그 외
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
