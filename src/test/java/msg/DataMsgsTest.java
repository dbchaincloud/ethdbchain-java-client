package msg;

import com.dbchain.client.DBChainOpbClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.query.Querier;
import com.dbchain.tx.DBChainTxService;
import com.dbchain.utils.BasicQueryUtil;
import com.dbchain.utils.CryptoUtil;
import com.google.protobuf.GeneratedMessageV3;
import dbchain.msgs.MsgsData;

import irita.sdk.exception.IritaSDKException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static client.BuildClient.buildDBChainOpbClient;
import static client.BuildClient.buildRESTClient;

public class DataMsgsTest {
    private static final Logger logger = LoggerFactory.getLogger(DataMsgsTest.class);

    @Test
    public void insertRow() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        Map<String,String> map = new HashMap<String,String>();
        map.put("name","zhangsan");
        map.put("age","15");
        MsgsData.MsgInsertRow msg =  MsgsData.MsgInsertRow.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setFields(CryptoUtil.transferToBytes(map))
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);

        try {
            logger.info("tx hash :" + DBChainTxService.buildAndSendTx(client, msgs));
            Thread.sleep(5000);
            String queryStr = BasicQueryUtil.basicWhereQuery("testtb", "id", ">=", "1");
            logger.info("table data :" + Querier.querierDataByCondition(buildRESTClient(),"5Q5FJ3UGAM",queryStr));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void frozenRow() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsData.MsgFreezeRow msg =  MsgsData.MsgFreezeRow.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setId(3)//通过id来冻结一行
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
            Thread.sleep(5000);
            String queryStr = BasicQueryUtil.basicWhereQuery("testtb", "id", ">=", "1");
            logger.info("table data :" + Querier.querierDataByCondition(buildRESTClient(),"5Q5FJ3UGAM",queryStr));//id = 2的数据已经被冻结
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
