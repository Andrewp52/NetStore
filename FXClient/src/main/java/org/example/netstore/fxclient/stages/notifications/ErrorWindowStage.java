package org.example.netstore.fxclient.stages.notifications;

import org.example.netstore.fxclient.stages.NotificationType;

import java.io.IOException;

public class ErrorWindowStage extends NotificationStage {
    public ErrorWindowStage(String title, String message) throws IOException {
        super(NotificationType.ERROR, title, message);
    }
}
