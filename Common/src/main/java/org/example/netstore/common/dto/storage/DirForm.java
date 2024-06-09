package org.example.netstore.common.dto.storage;

import java.io.Serializable;

public record DirForm(String path, boolean recursive) implements Serializable {
}
