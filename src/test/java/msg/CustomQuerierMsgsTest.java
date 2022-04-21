package msg;

import com.dbchain.client.DBChainOpbClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.tx.DBChainTxService;
import com.google.protobuf.GeneratedMessageV3;
import dbchain.msgs.MsgsCustomQuerier;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static client.BuildClient.buildDBChainOpbClient;
import static com.dbchain.utils.CryptoUtil.encodeDataToBase64String;

public class CustomQuerierMsgsTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomQuerierMsgsTest.class);

    @Test
    public void addCustomQuerier() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsCustomQuerier.MsgAddCustomQuerier msg =  MsgsCustomQuerier.MsgAddCustomQuerier.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setQuerierName("queryData")//设置查询函数名
                .setDescription(encodeDataToBase64String("query a row by id"))
                .setBody(encodeDataToBase64String("function queryData(tableName,Id)\n" +
                        "    res = findRow(tableName, Id)\n" +
                        "    return res:data()\n" +
                        "end"))//通过id去查找一行
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));//添加成功后通过QueryTest的customQuerier方法来调用查询函数
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void dropCustomQuerier() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsCustomQuerier.MsgDropCustomQuerier msg =   MsgsCustomQuerier.MsgDropCustomQuerier.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setQuerierName("queryData")
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }
}
