package com.dthealth.util;

import java.util.HashMap;
import java.util.Map;

import ua.naiksoftware.stomp.Stomp;


public class StompClient {

    private static volatile ua.naiksoftware.stomp.StompClient stompClientApi = null;

    public static synchronized ua.naiksoftware.stomp.StompClient getInstance() {
        if (stompClientApi == null) {
            Map<String, String> map = new HashMap<String, String>() {{
                put("token", "5d53b73492f6e331bc118715");
            }};
            stompClientApi = Stomp.over(Stomp.ConnectionProvider.OKHTTP,
                    PropertiesUtil.getProperties().getProperty("WEB_SOCKET_URL"), map);
        }
        return stompClientApi;
    }

}
