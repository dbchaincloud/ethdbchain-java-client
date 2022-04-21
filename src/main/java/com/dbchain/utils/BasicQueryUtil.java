package com.dbchain.utils;

import com.alibaba.fastjson.JSON;
import com.dbchain.client.DBChainOpbClient;
import com.dbchain.client.RESTClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.tx.DBChainTxService;
import com.dbchain.wallet.DBChainWallet;
import com.google.protobuf.GeneratedMessageV3;
import dbchain.msgs.MsgsColumn;
import org.bitcoinj.core.Base58;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicQueryUtil {

    //select * from tableName where field operator value;
    public static String basicWhereQuery(String tableName, String field, String operator, String value) {
        Map tableMap = new HashMap<String, String>();
        tableMap.put("method", "table");
        tableMap.put("table", tableName);

        Map conditionMap = new HashMap<String, String>();
        conditionMap.put("method", "where");
        conditionMap.put("field", field);
        conditionMap.put("operator", operator);
        conditionMap.put("value", value);

        Map<String, String>[] maps = new Map[2];
        maps[0] = tableMap;
        maps[1] = conditionMap;

        String toJSONString = JSON.toJSONString(maps);
        return CryptoUtil.encodeDataToBase58String(toJSONString.getBytes());

    }

}
