package com.flaremars.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by FlareMars on 2015/11/9
 */
public enum  StringUtils {
    INSTANCE;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public String getCurrentTime() {
        return DATE_FORMAT.format(new Date());
    }
}
