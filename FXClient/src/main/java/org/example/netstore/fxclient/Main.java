package org.example.netstore.fxclient;

import org.example.netstore.common.protocol.requests.storage.DirRequest;
import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.fxclient.netclient.ConnectionSettings;
import org.example.netstore.fxclient.netclient.NetClient;
import org.example.netstore.fxclient.netclient.NetClientFactory;
import org.example.netstore.fxclient.stages.MainWindowStage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        String rx = "^\\/{1,}";
        String s = "//asd/fggg/ggg.ggg";
        System.out.println(s.replaceFirst(rx, ""));

                MainWindowStage.main(args);
    }

}