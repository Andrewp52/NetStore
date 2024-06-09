package org.example.netstore.nettyserver.database;

import org.example.netstore.nettyserver.database.connectors.ConnectorType;

public record ConnectionProperties(ConnectorType type, String url, String username, String password) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ConnectorType type;
        private String url;
        private String username;
        private String password;

        public Builder connectorType(ConnectorType type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public ConnectionProperties build() {
            return new ConnectionProperties(this.type, this.url, this.username, this.password);
        }
    }
}
