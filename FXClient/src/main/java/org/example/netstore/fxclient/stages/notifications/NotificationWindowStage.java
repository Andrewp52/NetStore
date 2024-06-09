package org.example.netstore.fxclient.stages.notifications;

import org.example.netstore.fxclient.stages.NotificationType;

import java.io.IOException;

public class NotificationWindowStage extends NotificationStage{
    public NotificationWindowStage(String title, String message) throws IOException {
        super(NotificationType.INFO, title, message);
    }
}
