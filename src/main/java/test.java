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
        //testThread t1 = new testThread("speak universe hurry method human mule detail shuffle run squirrel exhibit account","t1");
        //testThread t2 = new testThread("alter payment curtain rate fashion mimic parrot bamboo shuffle youth token come brick debris wall ostrich champion salon wide easily rely razor sell differ", "t2");
        testThread t3 = new testThread("lunar spy clerk race family develop asthma flee safe unable aisle equal desert biology viable advice oxygen annual fan debate chef real atom orchard", "t3");
        testThread t4 = new testThread("owner erode dwarf average fruit venture okay senior carpet hundred minimum below", "t4");
        testThread t5 = new testThread("shoe gift globe link model flag sorry cook above faculty remove divert canyon come refuse excite route cram walnut solid spare point casino mistake", "t5");
        testThread t6 = new testThread("miracle jeans parrot recall fault canvas alert purpose notable civil indoor mountain", "t6");
        testThread t7 = new testThread("lady unfold member maximum dolphin clay together tree torch brisk ketchup reason", "t7");
        testThread t8 = new testThread("solar robust because depart evoke boat mesh purchase insane front before dinner", "t8");
        testThread t9 = new testThread("hello multiply inquiry sea future odor burger boil vacant major combine cave", "t9");
        testThread t10 = new testThread("source olive poverty void alone wage extend speed fish voyage average more", "t10");
        testThread t11 = new testThread("thunder salad planet business slender dog market level tell furnace nephew struggle", "t11");
        testThread t12 = new testThread("ordinary unaware seed screen deal laundry ramp shine salad resemble merry sort", "t12");
        testThread t13 = new testThread("video vocal dynamic mind blossom shoot clerk idle drink match dish bench", "t13");
        testThread t14 = new testThread("fat muscle finger march crystal luxury mix body cloth spray motor shrug", "t14");
        testThread t15 = new testThread("drop furnace adapt sibling omit gravity gather possible ready point insect agent", "t15");

        testThread t16 = new testThread("public jar agent treat select price song spot tunnel worry party spray", "t16");
        testThread t17 = new testThread("grape hub undo slight clarify narrow pause margin scorpion domain glance still", "t17");
        testThread t18 = new testThread("unfold doll powder weekend chalk apart captain scheme smooth social tomato wage", "t18");
        testThread t19 = new testThread("fragile outdoor number gossip cause arrow cargo idea alpha zebra tortoise strong", "t19");
        testThread t20 = new testThread("palm infant exotic tilt portion grain immense change develop burst company merit", "t20");

        testThread t21 = new testThread("method humor random shadow token dash near entry health scene harsh elephant", "t21");
        testThread t22 = new testThread("cat pistol sight weapon together true fossil illness orchard mail render ride", "t22");
        testThread t23 = new testThread("execute deliver pair worth riot tool accident race pigeon faculty toward trash", "t23");
        testThread t24 = new testThread("beach tortoise category memory sibling afford physical differ talk burst embark scheme", "t24");
        testThread t25 = new testThread("maximum degree beyond coffee dutch hockey insect rescue large arctic lady october", "t25");

        testThread t26 = new testThread("practice obvious paper toilet exhaust social unveil try animal video sing priority", "t25");
        testThread t27 = new testThread("trap pave plastic cereal decline fog spend tortoise bounce stamp glass answer", "t25");
        testThread t28 = new testThread("drastic figure nothing unknown clump fashion tenant unit lottery access select more", "t25");
        testThread t29 = new testThread("meat actress supply cruel middle enact tragic arena asset ramp reduce merge", "t25");
        testThread t30 = new testThread("chaos mutual raise hood arrow learn seek divert coyote celery cloud fire", "t25");


        //testThread t6 = new testThread("share relax profit judge woman kit keep lucky teach state alien alien", "t6");
        //new Thread(t1).start();
        //new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        new Thread(t5).start();
        new Thread(t6).start();
        new Thread(t7).start();
        new Thread(t8).start();
        new Thread(t9).start();
        new Thread(t10).start();
        new Thread(t11).start();
        new Thread(t12).start();
        new Thread(t13).start();
        new Thread(t14).start();
        new Thread(t15).start();
        new Thread(t16).start();
        new Thread(t17).start();
        new Thread(t18).start();
        new Thread(t19).start();
        new Thread(t20).start();

        new Thread(t21).start();
        new Thread(t22).start();
        new Thread(t23).start();
        new Thread(t24).start();
        new Thread(t25).start();


        //new Thread(t6).start();
//        t1.start();
//        t2.start();
    }
}

