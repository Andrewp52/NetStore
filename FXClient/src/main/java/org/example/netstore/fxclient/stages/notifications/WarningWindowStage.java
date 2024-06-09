package org.example.netstore.fxclient.stages.notifications;

import org.example.netstore.fxclient.stages.NotificationType;

import java.io.IOException;

public class WarningWindowStage extends NotificationStage{
    public WarningWindowStage(String title, String message) throws IOException {
        super(NotificationType.WARNING, title, message);
    }
}
