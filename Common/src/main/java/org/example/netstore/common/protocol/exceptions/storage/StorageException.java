package org.example.netstore.common.protocol.exceptions.storage;

import org.example.netstore.common.protocol.exceptions.ServiceException;

import java.util.UUID;

public class StorageException extends ServiceException {

    public StorageException(String message, UUID requestUUID) {
        super(message, requestUUID);
    }


}
