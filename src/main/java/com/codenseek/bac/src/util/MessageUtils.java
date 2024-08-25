package com.codenseek.bac.src.util;

import javax.swing.*;
import java.awt.*;

public class MessageUtils {
    /**
     * 에러 메시지를 화면에 보여주는 유틸리티 메소드
     *
     * @param parentComponent 메시지를 표시할 부모 컴포넌트
     * @param message 표시할 메시지 내용
     */
    public static void showErrorMessage(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(
                parentComponent,
                message,
                Constants.ERROR,
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * 알림 메시지를 화면에 보여주는 유틸리티 메소드
     *
     * @param parentComponent 메시지를 표시할 부모 컴포넌트
     * @param message 표시할 메시지 내용
     */
    public static void showNotificationMessage(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(
                parentComponent,
                message,
                Constants.NOTIFICATION,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
