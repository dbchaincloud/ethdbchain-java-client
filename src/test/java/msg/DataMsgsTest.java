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

//        Map<String,String> map = new HashMap<String,String>();
//        map.put("name","zhangsan");
//        map.put("age","15");
//        MsgsData.MsgInsertRow msg =  MsgsData.MsgInsertRow.newBuilder()
//                .setOwner(km.getCurrentKeyInfo().getAddress())
//                .setAppCode("5Q5FJ3UGAM")
//                .setTableName("testtb")
//                .setFields(CryptoUtil.transferToBytes(map))
//                .build();
//        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        List<GeneratedMessageV3> msgs =new ArrayList<GeneratedMessageV3>();

        for(int i=0;i < 20; i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("contract_address","0xe6F4390c27C55379610eB5614628Aa7fAD862592");
            map.put("desc","eyJkZXNjIjoiREJDaGFpbiByZXByZXNlbnRzIHlvdSBsZWdlbmRhcnkgcm9jayBiYW5kIGFuZCBHcmFtbXkgTGlmZSBUaW1lIEFjaGlldmVtZW50IEF3YXJkIHdpbm5lciBRdWVlbiBpbiBEQkMgTkZUIHdpdGggTXlzdGVyeSBCb3ggYW5kIG1vcmUsIHN1Y2ggYXMgRmlyc3QgUmVsZWFzZSBvZiAzIG5ld2x5LWNyZWF0ZWQgbXVzaWMsIHdlbGwtZGVzaWduZWQgdmlzdWFscyBhbmQgc29tZSB2YWx1YWJsZSBtZW1vcmFiaWxpYS4iLCJuYW1lIjoiREJDaGFpbiBYIFF1ZWVuIiwibmZ0X21ldGFkYXRhcyI6W3siaW1hZ2UiOiJodHRwczovL3NjdzIwMjEwNjIxLm9zcy1jbi1zaGVuemhlbi5hbGl5dW5jcy5jb20vbXlzdGVyeV9ib3gvYmxpbmQucG5nIiwibmFtZSI6IkRCQ2hhaW4gWCBRdWVlbiIsImRlc2MiOiJEQkNoYWluIHJlcHJlc2VudHMgeW91IGxlZ2VuZGFyeSByb2NrIGJhbmQgYW5kIEdyYW1teSBMaWZlIFRpbWUgQWNoaWV2ZW1lbnQgQXdhcmQgd2lubmVyIFF1ZWVuIGluIERCQyBORlQgd2l0aCBNeXN0ZXJ5IEJveCBhbmQgbW9yZSwgc3VjaCBhcyBGaXJzdCBSZWxlYXNlIG9mIDMgbmV3bHktY3JlYXRlZCBtdXNpYywgd2VsbC1kZXNpZ25lZCB2aXN1YWxzIGFuZCBzb21lIHZhbHVhYmxlIG1lbW9yYWJpbGlhLiIsImF0dHJpYnV0ZXMiOm51bGx9LHsiaW1hZ2UiOiJodHRwczovL3NjdzIwMjEwNjIxLm9zcy1jbi1zaGVuemhlbi5hbGl5dW5jcy5jb20vbXlzdGVyeV9ib3gvUi5wbmciLCJuYW1lIjoiUXVlZW4gLSBIYXBwaW5lc3MiLCJkZXNjIjoiREJDaGFpbiByZXByZXNlbnRzIHlvdSBsZWdlbmRhcnkgcm9jayBiYW5kIGFuZCBHcmFtbXkgTGlmZSBUaW1lIEFjaGlldmVtZW50IEF3YXJkIHdpbm5lciBRdWVlbiBpbiBEQkMgTkZUIHdpdGggTXlzdGVyeSBCb3ggYW5kIG1vcmUsIHN1Y2ggYXMgRmlyc3QgUmVsZWFzZSBvZiAzIG5ld2x5LWNyZWF0ZWQgbXVzaWMsIHdlbGwtZGVzaWduZWQgdmlzdWFscyBhbmQgc29tZSB2YWx1YWJsZSBtZW1vcmFiaWxpYS4iLCJhdHRyaWJ1dGVzIjpbeyJsZXZlbCI6IlIiLCJwcm9iYWJpbGl0eSI6IjcwJSJ9XX0seyJpbWFnZSI6Imh0dHBzOi8vc2N3MjAyMTA2MjEub3NzLWNuLXNoZW56aGVuLmFsaXl1bmNzLmNvbS9teXN0ZXJ5X2JveC9TUi5wbmciLCJuYW1lIjoiUXVlZW4gLSBSb2NrIHNvdWwiLCJkZXNjIjoiREJDaGFpbiByZXByZXNlbnRzIHlvdSBsZWdlbmRhcnkgcm9jayBiYW5kIGFuZCBHcmFtbXkgTGlmZSBUaW1lIEFjaGlldmVtZW50IEF3YXJkIHdpbm5lciBRdWVlbiBpbiBEQkMgTkZUIHdpdGggTXlzdGVyeSBCb3ggYW5kIG1vcmUsIHN1Y2ggYXMgRmlyc3QgUmVsZWFzZSBvZiAzIG5ld2x5LWNyZWF0ZWQgbXVzaWMsIHdlbGwtZGVzaWduZWQgdmlzdWFscyBhbmQgc29tZSB2YWx1YWJsZSBtZW1vcmFiaWxpYS4iLCJhdHRyaWJ1dGVzIjpbeyJsZXZlbCI6IlNSIiwicHJvYmFiaWxpdHkiOiIyNSUifV19LHsiaW1hZ2UiOiJodHRwczovL3NjdzIwMjEwNjIxLm9zcy1jbi1zaGVuemhlbi5hbGl5dW5jcy5jb20vbXlzdGVyeV9ib3gvU1NSLnBuZyIsIm5hbWUiOiJRdWVlbiAtIExvbmcgbGl2ZSB0aGUgUXVlZW4iLCJkZXNjIjoiREJDaGFpbiByZXByZXNlbnRzIHlvdSBsZWdlbmRhcnkgcm9jayBiYW5kIGFuZCBHcmFtbXkgTGlmZSBUaW1lIEFjaGlldmVtZW50IEF3YXJkIHdpbm5lciBRdWVlbiBpbiBEQkMgTkZUIHdpdGggTXlzdGVyeSBCb3ggYW5kIG1vcmUsIHN1Y2ggYXMgRmlyc3QgUmVsZWFzZSBvZiAzIG5ld2x5LWNyZWF0ZWQgbXVzaWMsIHdlbGwtZGVzaWduZWQgdmlzdWFscyBhbmQgc29tZSB2YWx1YWJsZSBtZW1vcmFiaWxpYS4iLCJhdHRyaWJ1dGVzIjpbeyJsZXZlbCI6IlNTUiIsInByb2JhYmlsaXR5IjoiNSUifV19XX0=");
            map.put("price","0.1");
            map.put("release_time","1650016800");
            map.put("release_number","40");
            MsgsData.MsgInsertRow msg =  MsgsData.MsgInsertRow.newBuilder()
                .setOwner(km.getCurrentKeyInfo().getAddress())
                .setAppCode("DYJGN3ZTBY")//库名
                .setTableName("contracts")//表名
                .setFields(CryptoUtil.transferToBytes(map))//插入的数据
                .build();
            msgs.add(msg);
        }

        try {
            logger.info("tx hash :" + DBChainTxService.buildAndSendTx(client, msgs));
//            String queryStr = BasicQueryUtil.basicWhereQuery("testtb", "id", ">=", "1");
//            logger.info("table data :" + Querier.querierDataByCondition(buildRESTClient(),"5Q5FJ3UGAM",queryStr));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
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
            String queryStr = BasicQueryUtil.basicWhereQuery("testtb", "id", ">=", "1");
            logger.info("table data :" + Querier.querierDataByCondition(buildRESTClient(),"5Q5FJ3UGAM",queryStr));//id = 2的数据已经被冻结
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }


}
