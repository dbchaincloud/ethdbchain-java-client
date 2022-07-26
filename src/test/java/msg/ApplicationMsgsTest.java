package msg;

import com.dbchain.client.DBChainOpbClient;
import com.dbchain.client.RESTClient;
import com.dbchain.keymanager.DBChainKeyManager;

import com.dbchain.query.Querier;
import com.dbchain.tx.DBChainTxService;

import com.dbchain.utils.QueryUtil;
import com.google.protobuf.GeneratedMessageV3;
import dbchain.msgs.MsgsApplication;
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
import static client.BuildClient.buildRESTClient;
import static com.dbchain.tx.DBChainTxService.buildAndSendTxForTest;

public class ApplicationMsgsTest {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationMsgsTest.class);

    @Test
    public void createApplication() throws Exception {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();
        MsgsApplication.MsgCreateApplication msg = MsgsApplication.MsgCreateApplication.newBuilder()
                .setName("test")//设置数据库名称
                .setOwner(km.getCurrentKeyInfo().getAddress())//设置数据库拥有者
                .setDescription("testdesc")//设置数据库描述
                .setPermissionRequired(false)//设置数据库是否公开
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
            String resp = Querier.queryApplication(buildRESTClient());
            logger.info("appcode : " + QueryUtil.getLastData(resp));//appcode是数据库唯一标识,取得当前创建的数据库的appcode
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void createApplicationForTest() throws Exception {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();
        MsgsApplication.MsgCreateApplication msg = MsgsApplication.MsgCreateApplication.newBuilder()
                .setName("test")//设置数据库名称
                .setOwner(km.getCurrentKeyInfo().getAddress())//设置数据库拥有者
                .setDescription("testdesc")//设置数据库描述
                .setPermissionRequired(false)//设置数据库是否公开
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        buildAndSendTxForTest(client, msgs);
    }

}
