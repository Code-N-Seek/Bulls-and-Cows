package com.codenseek.bac.src;

import com.codenseek.bac.src.util.Constants;
import com.codenseek.bac.src.util.GameKind;

import javax.swing.*;
import java.awt.*;


/**
 * 숫자야구 게임 메인 프레임 클래스
 * - 여러 화면(메인 메뉴, 초기 화면, 게임 화면) 관리 및 게임 시작, 화면 전환 기능 제공
 */
public class EntryFrame extends JFrame {
    private final CardLayout cardLayout;    // 여러 화면을 전환할 수 있는 카드 레이아웃
    private final Container contentPane;    // 화면 콘텐츠를 담을 컨테이너
    private final GameScreen gameScreen;    // 게임 화면을 관리하는 GameScreen 객체

    /**
     * 메인 프레임 설정
     * - 다양한 화면 패널을 contentPane에 추가
     * - 초기 화면 크기와 위치 설정
     * - 화면 전환을 위한 레이아웃 구성
     */
    public EntryFrame() {
        setTitle(Constants.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 클릭 시 애플리케이션 종료
        setSize(350, 500);
        setLocationRelativeTo(null);    // 프레임을 화면 중앙에 위치시킴

        cardLayout = new CardLayout();
        contentPane = getContentPane();
        contentPane.setLayout(cardLayout);

        // 화면 패널을 contentPane에 추가
        contentPane.add(
                new MainMenuScreen(this),
                "mainMenuScreen"
        );
        contentPane.add(
                new InitialScreen(this),
                "initialScreen"
        );
        gameScreen = new GameScreen(this);
        contentPane.add(
                gameScreen,
                "gameScreen"
        );
    }

    /**
     * 주어진 화면 이름에 해당하는 화면을 표시하는 메서드
     *
     * @param screenName 표시할 화면의 이름
     */
    public void showScreen(String screenName) {
        cardLayout.show(contentPane, screenName);
    }

    /**
     * 게임 시작 메서드
     * - 주어진 단어 길이를 게임 화면에 설정하고,
     * 게임 화면을 업데이트한 후, 게임 화면으로 전환
     *
     * @param gameKind 게임 종류
     * @param wordLength 게임에 사용될 단어의 길이
     */
    public void startGame(GameKind gameKind, int wordLength) {
        gameScreen.initGameSetting(gameKind, wordLength);
        gameScreen.updateGameScreen();
        showScreen("gameScreen");
    }

    public static void main(String[] args) {
        EntryFrame frame = new EntryFrame();
        frame.setVisible(true);
    }
}