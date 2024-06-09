package org.example.netstore.nettyserver.factories;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.nettyserver.User;
import org.example.netstore.nettyserver.services.store.SimpleStorageService;
import org.example.netstore.nettyserver.services.store.StorageService;

import java.io.IOException;

public abstract class StorageServices {
    public static StorageService getStorageService(UserDto user) throws IOException {
        return new SimpleStorageService(user);
    }
}
