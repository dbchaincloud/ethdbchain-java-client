package query;

import com.dbchain.client.RESTClient;

import com.dbchain.query.Querier;

import com.dbchain.utils.BasicQueryUtil;
import irita.sdk.util.Bech32Utils;
import org.bitcoinj.core.Bech32;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static client.BuildClient.buildRESTClient;
import static com.dbchain.utils.CryptoUtil.encodeDataToBase58String;


public class QueryTest {
    private static final Logger logger = LoggerFactory.getLogger(QueryTest.class);

    @Test
    public void queryAccount() {
        RESTClient restClient = buildRESTClient();
        String resp = Querier.getAccount(restClient, "dbc14ppnu5c4whxvl8sej7yqp4zjsp79u0my8mhqk0");
       // String resp = Querier.getAccount(restClient, "dbc1l5u7efmwj46zpwh6fhgkehqj2e7wxtg552hugh");
        logger.info(resp);
    }

    @Test
    public void queryAccountBalances() {
        RESTClient restClient = buildRESTClient();
        String resp = Querier.getAccountBalances(restClient, "dbc14ppnu5c4whxvl8sej7yqp4zjsp79u0my8mhqk0");
//      String resp = Querier.getAccountBalances(restClient, "dbc165a7euqtwahp3xxhxntduhkl4vmtmgdaamlatn");
        logger.info(resp);
    }

    @Test
    public void queryApplication() {
        RESTClient restClient = buildRESTClient();
        String resp = Querier.queryApplication(restClient);//查询用户创建的数据库
        logger.info(resp);
    }

    @Test
    public void queryTx() {
        RESTClient restClient = buildRESTClient();
        String txHash = "5EB7D214E58B6081C90ABC24BEE5D942766B2AEEA26F8620D3FB9FD4919ED6F8";
        String resp = Querier.getTxDetailByHash(restClient, txHash);//根据交易哈希查询交易详情
        logger.info(resp);
    }

    @Test
    public void queryDataByCondition() {
        RESTClient restClient = buildRESTClient();
        String appCode = "5Q5FJ3UGAM";
        //String queryStr = BasicQueryUtil.basicWhereQuery("testtb", "id", ">=", "1");
        String queryStr = BasicQueryUtil.basicWhereQuery("testtb", "name_modify", "=", "lisi");//查询FunctionMsgsTest 中的callFunction插入的那条数据
        String resp = Querier.querierDataByCondition(restClient, appCode, queryStr);
        logger.info(resp);
    }

    @Test
    public void queryTableInfo() {
        RESTClient restClient = buildRESTClient();
        String appCode = "5Q5FJ3UGAM";
        String tableName = "testtb";
        String resp = Querier.queryTableInfo(restClient, appCode, tableName);//查询表的信息
        logger.info(resp);
    }

    @Test
    public void queryTables() {
        RESTClient restClient = buildRESTClient();
        String appCode = "5Q5FJ3UGAM";
        String resp = Querier.queryTables(restClient, appCode);//查询数据库里的表
        logger.info(resp);
    }

    @Test
    public void customQuerier() {
        RESTClient restClient = buildRESTClient();
        String appCode = "5Q5FJ3UGAM";
        String funcName = "queryData";//查询函数名

        String[] funcParams = new String[2];
        String tableName = "testtb";
        String id = "1";
        funcParams[0] = encodeDataToBase58String(tableName.getBytes());
        funcParams[1] = encodeDataToBase58String(id.getBytes());

        String resp = Querier.querierFunction(restClient, appCode, funcName, funcParams);
        logger.info(resp);
    }

    @Test
    public void QueryAddressConvert() {
        RESTClient restClient = buildRESTClient();

       // String address = "dbc14ppnu5c4whxvl8sej7yqp4zjsp79u0my8mhqk0";
        //String address = "0x80F94815a36bB3E4Fb4623BbB64A0227a6Eb143c";
        String address = "dbc165a7euqtwahp3xxhxntduhkl4vmtmgdaamlatn";

        String resp = Querier.queryAddressConvert(restClient,address);//以太坊地址与链账户地址相互转换
        logger.info(resp);
    }


}


