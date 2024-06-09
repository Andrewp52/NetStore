package org.example.netstore.nettyserver.channelhandlers.requestservers;

import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.responses.Response;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Contains children's functions mapping.
 * Collecting methods that return Response from children classes
 * and map them to Request subtype as key.
 */
public abstract class AbstractRequestServer implements RequestServer{
    protected final Map<Class<? extends Request>, Function<Request, Response>> functionMap = new HashMap<>();

    {
        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getReturnType().equals(Response.class)
                        && !method.getParameterTypes()[0].equals(Request.class)
                )
                .forEach(method -> {
                    Class param = method.getParameterTypes()[0];
                    functionMap.put(param, request -> {
                        try {
                            return (Response) method.invoke(this, request);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
    }


}
