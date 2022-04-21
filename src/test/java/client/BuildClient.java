package client;

import com.dbchain.client.DBChainOpbClient;
import com.dbchain.client.RESTClient;
import com.dbchain.keymanager.DBChainKeyManager;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;

public class BuildClient {
    public static RESTClient buildRESTClient() {
        //String mnemonic = "lunar spy clerk race family develop asthma flee safe unable aisle equal desert biology viable advice oxygen annual fan debate chef real atom orchard";
        //String mnemonic = "alter payment curtain rate fashion mimic parrot bamboo shuffle youth token come brick debris wall ostrich champion salon wide easily rely razor sell differ";//0xd53BECF00B776e1898d734d6DE5eDfAb36Bda1bD
        String mnemonic = "speak universe hurry method human mule detail shuffle run squirrel exhibit account";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        DBChainKeyManager km = new DBChainKeyManager();
        km.recover(mnemonic);
        String url = "http://localhost:1317";
        // String url = "http://192.168.0.58:1317";
//        String url = "http://192.168.0.19/relay";
       // String url = "https://opbtest.bsngate.com:18602/api/110d46138bde4743bef4538734a29184/rest";
        RESTClient restClient = new RESTClient(url, km);
        return restClient;
    }

    public static DBChainOpbClient buildDBChainOpbClient() {
       // String mnemonic = "lunar spy clerk race family develop asthma flee safe unable aisle equal desert biology viable advice oxygen annual fan debate chef real atom orchard";//0xa8433e531575CCCf9E19978800D452807C5E3F64 hqk0
        //String mnemonic = "shoe gift globe link model flag sorry cook above faculty remove divert canyon come refuse excite route cram walnut solid spare point casino mistake";//admin
        //String mnemonic = "alter payment curtain rate fashion mimic parrot bamboo shuffle youth token come brick debris wall ostrich champion salon wide easily rely razor sell differ";
        String mnemonic = "speak universe hurry method human mule detail shuffle run squirrel exhibit account";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        DBChainKeyManager km = new DBChainKeyManager();
        km.recover(mnemonic);

        String nodeUri = "http://localhost:26657";
        String grpcAddr = "http://localhost:9090";

       // String nodeUri = "http://192.168.1.164:26657";
//        String grpcAddr = "http://192.168.0.19:9190";
//        String nodeUri = "http://192.168.0.19:36657";
//        String nodeUri = "https://opbtest.bsngate.com:18602/api/110d46138bde4743bef4538734a29184/rpc";
//        String grpcAddr = "opbtest.bsngate.com:18603";
        String chainId = "ethdbchain_9000-1";
        ClientConfig clientConfig = new ClientConfig(nodeUri, grpcAddr, chainId);
       //OpbConfig opbConfig = new OpbConfig("110d46138bde4743bef4538734a29184","","");
       //opbConfig.setEnableTLS(true);
        OpbConfig opbConfig = null;
        DBChainOpbClient client = new DBChainOpbClient(clientConfig, opbConfig, km);
        return client;
    }
}
