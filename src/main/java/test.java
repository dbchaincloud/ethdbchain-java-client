import bank.v1beta1.Tx;
import com.dbchain.client.DBChainOpbClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.tx.DBChainTxService;
import com.dbchain.utils.CryptoUtil;
import com.google.protobuf.GeneratedMessageV3;
import cosmos.base.v1beta1.CoinOuterClass;
import dbchain.msgs.MsgsApplication;
import dbchain.msgs.MsgsData;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.dbchain.tx.DBChainTxService.buildAndSendTxForTest;

public class test {

    public static void main(String[] args) {
//        Properties properties = new Properties();
//        InputStream in = test.class.getClassLoader()
//                .getResourceAsStream("config.properties");
//        try {
//            properties.load(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        int times = Integer.parseInt(properties.getProperty("times"));
//        String rpcUrl  = properties.getProperty("rpc_uri");
//        String grpcUrl  = properties.getProperty("grpc_uri");
//
//        String mnemonic = "speak universe hurry method human mule detail shuffle run squirrel exhibit account";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
//        DBChainKeyManager km = new DBChainKeyManager();
//        km.recover(mnemonic);
//
////        String nodeUri = "http://192.168.0.58:3001/rpc_relay/";
////        String grpcAddr = "http://192.168.0.58:9090";
//        String nodeUri = rpcUrl;
//        String grpcAddr = grpcUrl;
//        String chainId = "ethdbchain_9000-1";
//        ClientConfig clientConfig = new ClientConfig(nodeUri, grpcAddr, chainId);
//        //OpbConfig opbConfig = new OpbConfig("110d46138bde4743bef4538734a29184","","");
//        //opbConfig.setEnableTLS(true);
//        OpbConfig opbConfig = null;
//        DBChainOpbClient client = new DBChainOpbClient(clientConfig, opbConfig, km);
//
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("name","zhangsan");
//        map.put("age","15");
//        MsgsData.MsgInsertRow msg =  MsgsData.MsgInsertRow.newBuilder()
//                .setOwner(km.getCurrentKeyInfo().getAddress())
//                .setAppCode("64MNBPQE8O")
//                .setTableName("testtb")
//                .setFields(CryptoUtil.transferToBytes(map))
//                .build();
//        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
////        Tx.MsgSend msg = Tx.MsgSend.newBuilder()
////                .addAmount(CoinOuterClass.Coin.newBuilder().setAmount("10").setDenom("adbctoken").build())
//////                .setFromAddress("dbc14ppnu5c4whxvl8sej7yqp4zjsp79u0my8mhqk0")
////                .setFromAddress(km.getCurrentKeyInfo().getAddress())
////                .setToAddress("dbc165a7euqtwahp3xxhxntduhkl4vmtmgdaamlatn")
////                .build();
////        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
////        MsgsApplication.MsgCreateApplication msg = MsgsApplication.MsgCreateApplication.newBuilder()
////                .setName("test")//设置数据库名称
////                .setOwner(km.getCurrentKeyInfo().getAddress())//设置数据库拥有者
////                .setDescription("testdesc")//设置数据库描述
////                .setPermissionRequired(false)//设置数据库是否公开
////                .build();
////        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
//        try {
//            buildAndSendTxForTest(client, msgs, times);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        testThread t1 = new testThread("speak universe hurry method human mule detail shuffle run squirrel exhibit account","t1");
        testThread t2 = new testThread("alter payment curtain rate fashion mimic parrot bamboo shuffle youth token come brick debris wall ostrich champion salon wide easily rely razor sell differ", "t2");
        testThread t3 = new testThread("lunar spy clerk race family develop asthma flee safe unable aisle equal desert biology viable advice oxygen annual fan debate chef real atom orchard", "t3");
        testThread t4 = new testThread("owner erode dwarf average fruit venture okay senior carpet hundred minimum below", "t4");
        testThread t5 = new testThread("shoe gift globe link model flag sorry cook above faculty remove divert canyon come refuse excite route cram walnut solid spare point casino mistake", "t5");
        //testThread t6 = new testThread("share relax profit judge woman kit keep lucky teach state alien alien", "t6");
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        new Thread(t5).start();
        //new Thread(t6).start();
//        t1.start();
//        t2.start();
    }
}

