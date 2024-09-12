package com.codenseek.bac.src.config;

import com.codenseek.bac.src.util.GameKind;
import lombok.*;

/**
 * 게임 설정을 관리하는 클래스
 * - 게임 종류와 입력 자릿수를 저장하고, 해당 설정을 유지하는 역할 수행
 * - 싱글톤 패턴을 이용해 설정 저장 및 로드
 */
@Getter
@NoArgsConstructor
public class GameSettings {
    private GameKind gameKind;  // 게임 종류 (숫자, 영어 단어 등)
    private int inputLength;    // 입력 자릿수
    private static GameSettings savedSettings;  // 저장된 게임 설정

    /**
     * 주어진 게임 종류와 입력 자릿수를 이용하여 GameSettings 인스턴스를 생성
     * 생성과 동시에 해당 설정을 저장
     *
     * @param gameKind   설정할 게임 종류
     * @param inputLength 설정할 입력 자릿수
     */
    @Builder
    public GameSettings(GameKind gameKind, int inputLength) {
        this.gameKind = gameKind;
        this.inputLength = inputLength;
        save();
    }

    /**
     * 저장된 게임 설정 반환
     *
     * @return 저장된 GameSettings 인스턴스 또는 설정이 없으면 null을 반환
     */
    public static GameSettings getSavedSettings() {
        // 저장된 인스턴스를 반환, 없으면 null 반환
        return savedSettings;
    }

    /**
     * 현재 GameSettings 인스턴스 저장
     * - 싱글톤 패턴을 사용하여 설정을 유지
     */
    public void save() {
        // 현재 설정을 싱글톤 인스턴스로 저장
        savedSettings = this;
    }
}
