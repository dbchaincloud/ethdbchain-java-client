package com.dbchain.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.Map;

public class QueryUtil {
    public static String getLastData(String resp){
        Map map = (Map) JSON.parse(resp);
        JSONArray jsonarray = JSON.parseArray(JSON.toJSONString(map.get("result")));
        if (jsonarray.size() == 0){
            return "result is empty";
        }
        String[] appCodes = jsonarray.toArray(new String[jsonarray.size()]);
        return appCodes[appCodes.length-1];
    }
}
