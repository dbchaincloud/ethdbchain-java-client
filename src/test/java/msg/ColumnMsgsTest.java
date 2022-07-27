package msg;

import com.dbchain.client.DBChainOpbClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.query.Querier;
import com.dbchain.tx.DBChainTxService;
import com.dbchain.utils.CryptoUtil;
import com.google.protobuf.GeneratedMessageV3;
import dbchain.msgs.MsgsColumn;
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

public class ColumnMsgsTest {
    private static final Logger logger = LoggerFactory.getLogger(ColumnMsgsTest.class);


    @Test
    public void addColumn() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgAddColumn msg = MsgsColumn.MsgAddColumn.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setField("ttt") //新增字段
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
            Thread.sleep(5000);
            String appCode = "5Q5FJ3UGAM";
            String tableName = "testtb";
            String resp = Querier.queryTableInfo(buildRESTClient(), appCode, tableName);
            logger.info("table info : "+resp);//可以看到ttt字段已加入表里
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dropColumn() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgDropColumn msg = MsgsColumn.MsgDropColumn.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setField("ttt")
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
            Thread.sleep(5000);
            String appCode = "5Q5FJ3UGAM";
            String tableName = "testtb";
            String resp = Querier.queryTableInfo(buildRESTClient(), appCode, tableName);
            logger.info("table info : "+resp);//可以看到ttt字段已被移除
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void renameColumn() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgRenameColumn msg =  MsgsColumn.MsgRenameColumn.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setOldField("name")
                .setNewField("name_modify")
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
            Thread.sleep(5000);
            String appCode = "5Q5FJ3UGAM";
            String tableName = "testtb";
            String resp = Querier.queryTableInfo(buildRESTClient(), appCode, tableName);
            logger.info("table info : "+resp);//可以看到name字段已被修改
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addColumnMemo() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgSetColumnMemo msg =  MsgsColumn.MsgSetColumnMemo.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setFieldName("name_modify")
                .setMemo(CryptoUtil.encodeDataToBase64String("test_memo"))//设置字段的备注
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
            Thread.sleep(5000);
            String appCode = "5Q5FJ3UGAM";
            String tableName = "testtb";
            String resp = Querier.queryTableInfo(buildRESTClient(), appCode, tableName);
            logger.info("table info : "+resp);//可以看到memo
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createIndex() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgCreateIndex msg =  MsgsColumn.MsgCreateIndex.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setField("name_modify")
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

    @Test
    public void dropIndex() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgDropIndex msg =  MsgsColumn.MsgDropIndex.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setField("name_modify")
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

    @Test
    public void setColumnDataType() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgSetColumnDataType msg =   MsgsColumn.MsgSetColumnDataType.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setFieldName("age")
                .setDataType("int")//设置字段数据类型
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

    @Test
    public void modifyColumnOption() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsColumn.MsgModifyColumnOption msg =    MsgsColumn.MsgModifyColumnOption.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setTableName("testtb")
                .setFieldName("age")
                .setAction("add")//drop or add
                .setOption("not-null")//设置字段属性
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
