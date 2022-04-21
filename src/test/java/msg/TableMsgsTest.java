package msg;

import com.dbchain.client.DBChainOpbClient;
import com.dbchain.client.RESTClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.query.Querier;
import com.dbchain.tx.DBChainTxService;
import com.google.protobuf.GeneratedMessageV3;

import dbchain.msgs.MsgsTable;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static client.BuildClient.buildDBChainOpbClient;
import static client.BuildClient.buildRESTClient;

public class TableMsgsTest {
    private static final Logger logger = LoggerFactory.getLogger(TableMsgsTest.class);

    @Test
    public void createTable() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        List fields = new ArrayList<String>();//为表添加字段名
        fields.add("name");
        fields.add("age");

        MsgsTable.MsgCreateTable msg = MsgsTable.MsgCreateTable.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")//指定在对应的数据库下建表
                .addAllFields(fields)
                .setTableName("testtb")//设置表名
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
            String appCode = "5Q5FJ3UGAM";
            String tableName = "testtb";
            String resp = Querier.queryTableInfo(buildRESTClient(), appCode, tableName);
            logger.info("table info : "+resp);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

}
