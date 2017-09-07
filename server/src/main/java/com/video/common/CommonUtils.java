package com.video.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ihar Zharykau
 */
public final class CommonUtils {

    public static Map RESULT_SUCCESS = mapOf("result", "success");
    public static Map RESULT_ERROR = mapOf("result", "error");

    @SuppressWarnings("unchecked")
    public static Map mapOf(Object... args){
        Map result = new HashMap();
        for ( int i = 0; i < args.length - 1; i+=2){
            result.put(args[i], args[i + 1]);
        }
        return result;
    }
}
