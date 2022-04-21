package msg;

import com.alibaba.fastjson.JSON;
import com.dbchain.client.DBChainOpbClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.query.Querier;
import com.dbchain.tx.DBChainTxService;
import com.dbchain.utils.BasicQueryUtil;
import com.dbchain.utils.QueryUtil;
import com.google.protobuf.GeneratedMessageV3;
import dbchain.msgs.MsgsFunction;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.BuildClient.buildDBChainOpbClient;
import static client.BuildClient.buildRESTClient;
import static com.dbchain.utils.CryptoUtil.encodeDataToBase64String;

public class FunctionMsgsTest {
    private static final Logger logger = LoggerFactory.getLogger(FunctionMsgsTest.class);


    @Test
    public void addFunction() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        MsgsFunction.MsgAddFunction msg = MsgsFunction.MsgAddFunction.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setFunctionName("funcInsert")
                .setDescription(encodeDataToBase64String("insert a row"))
                .setBody(encodeDataToBase64String("function funcInsert(t1, v1) \n" +
                        "\tfields = jsonStringToMap(v1)\n" +
                        "\tid, err = InsertRow(t1, fields)\n" +
                        "\tif err ~= \"\" then\n" +
                        "        return err\n" +
                        "        end\n" +
                        "        return \"\"\n" +
                        "end"))
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));//
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void callFunction() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        String[] argmentArray = new String[2];
        Map<String,String> map = new HashMap<String,String>();
        map.put("name_modify","lisi");
        map.put("age","1015");
        String data = JSON.toJSONString(map);

        argmentArray[0] = "testtb";
        argmentArray[1] = data;

        String argment = JSON.toJSONString(argmentArray);

        MsgsFunction.MsgCallFunction msg =  MsgsFunction.MsgCallFunction.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("5Q5FJ3UGAM")
                .setFunctionName("funcInsert")
                .setArgument(argment)
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
