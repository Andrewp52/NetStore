package org.example.netstore.fxclient.stages;

public enum NotificationType {
    ERROR("icons/error.png"),
    INFO("icons/info.png"),
    WARNING("icons/warn.png");

    private final String iconPath;

    NotificationType(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }
}
