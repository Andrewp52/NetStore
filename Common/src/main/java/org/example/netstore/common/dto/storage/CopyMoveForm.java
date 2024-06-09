package org.example.netstore.common.dto.storage;

import java.io.Serializable;

public record CopyMoveForm(String from, String to) implements Serializable {
}
