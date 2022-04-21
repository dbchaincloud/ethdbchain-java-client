package com.dbchain.query;

import com.dbchain.client.RESTClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.utils.BasicQueryUtil;
import com.dbchain.utils.RESTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dbchain.utils.CryptoUtil.makeAccessToken;

public class Querier {
    private static final Logger logger = LoggerFactory.getLogger(Querier.class);
    private static final String DBCHAIN = "dbchain";
    private static final String APPLICATION = "own-application";
    private static final String AUTH = "auth";
    private static final String Bank = "bank";
    private static final String ACCOUNTS = "accounts";
    private static final String BALANCES = "balances";
    private static final String TXS = "cosmos/tx/v1beta1/txs";
    private static final String QUERIER = DBCHAIN + "/querier";
    private static final String QUERYTABLES = DBCHAIN + "/tables";
    private static final String CUSTOMQUERIER = DBCHAIN + "/call-custom-querier";
    private static final String CONVERTADDRESS = DBCHAIN + "/convert-address";

    /**
     * 获取account
     */

    //@GET("auth/accounts/{address}")
    public static String getAccount(RESTClient restClient, String address) {
        String url = restClient.getUrl();
        String[] params = new String[3];
        params[0] = AUTH;
        params[1] = ACCOUNTS;
        params[2] = address;
        String resp = RESTUtil.get(url, params);
        return resp;
    }

    /**
     * 获取account balances
     */

    //@GET("/bank/balances/{address}")
    public static String getAccountBalances(RESTClient restClient, String address) {
        String url = restClient.getUrl();
        String[] params = new String[3];
        params[0] = Bank;
        params[1] = BALANCES;
        params[2] = address;
        String resp = RESTUtil.get(url, params);
        return resp;
    }

    /**
     * 获取交易详情
     */
    //@GET("cosmos/tx/v1beta1/txs/{txHash}")
    public static String getTxDetailByHash(RESTClient restClient, String txHash) {
        String url = restClient.getUrl();
        String[] params = new String[2];
        params[0] = TXS;
        params[1] = txHash;
        String resp = RESTUtil.get(url, params);
        return resp;
    }


    /**
     * 根据条件查询表数据
     */

    //@GET("dbchain/querier/{token}/{appCode}/{query}")
    public static String querierDataByCondition(RESTClient restClient, String appCode, String queryCondition) {
        String url = restClient.getUrl();
        String accessToken = makeAccessToken(restClient);
        String[] params = new String[4];
        params[0] = QUERIER;
        params[1] = accessToken;
        params[2] = appCode;
        params[3] = queryCondition;
        String resp = RESTUtil.get(url, params);
        return resp;
    }

    /**
     * 查询表信息
     */

    //@GET("dbchain/tables/{token}/{appCode}/{tableName}")
    public static String queryTableInfo(RESTClient restClient, String appCode, String tableName) {
        String url = restClient.getUrl();
        String accessToken = makeAccessToken(restClient);
        String[] params = new String[4];
        params[0] = QUERYTABLES;
        params[1] = accessToken;
        params[2] = appCode;
        params[3] = tableName;
        String resp = RESTUtil.get(url, params);
        return resp;
    }

    /**
     * 查询库里的表
     */

    //@GET("dbchain/tables/{token}/{appCode}/{tableName}")
    public static String queryTables(RESTClient restClient, String appCode) {
        String url = restClient.getUrl();
        String accessToken = makeAccessToken(restClient);
        String[] params = new String[3];
        params[0] = QUERYTABLES;
        params[1] = accessToken;
        params[2] = appCode;
        String resp = RESTUtil.get(url, params);
        return resp;
    }

    /**
     * 自定义的查询函数
     */
    //@GET("dbchain/call-custom-querier/{token}/{appCode}/{querierName}/{params}")
    public static String querierFunction(RESTClient restClient, String appCode, String querierName, String[] funcParams) {
        String baseuUrl = restClient.getUrl();
        StringBuffer sb = new StringBuffer(baseuUrl);
        String accessToken = makeAccessToken(restClient);
        String[] params = new String[4];
        params[0] = CUSTOMQUERIER;
        params[1] = accessToken;
        params[2] = appCode;
        params[3] = querierName;
        for (int i = 0; i < params.length; i++) {
            sb.append("/" + params[i]);
        }

        String url = sb.toString();
        for (int i = 0; i < funcParams.length; i++) {
            funcParams[i] = "params="+funcParams[i];
        }

        String resp = RESTUtil.getWithParams(url, funcParams);
        return resp;
    }


    //@GET("dbchain/owner-application/{token}")
    public static String queryApplication(RESTClient restClient) {
        String url = restClient.getUrl();
        String accessToken = makeAccessToken(restClient);
        String[] params = new String[3];
        params[0] = DBCHAIN;
        params[1] = APPLICATION;
        params[2] = accessToken;
        String resp = RESTUtil.get(url, params);
        return resp;
    }

    /**
     * 查询以太坊地址和链账户地址的对应关系
     */
    //@GET("dbchain/convert-address/{address}")
    public static String queryAddressConvert(RESTClient restClient,String address) {
        String url = restClient.getUrl();

        String[] params = new String[2];
        params[0] = CONVERTADDRESS;
        params[1] = address;

        String resp = RESTUtil.get(url,params);
        return resp;
    }
}
