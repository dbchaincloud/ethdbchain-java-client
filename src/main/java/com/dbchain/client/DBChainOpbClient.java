package com.dbchain.client;

import irita.sdk.client.OpbClient;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.key.KeyManager;


public class DBChainOpbClient extends OpbClient {
    private DBChainBaseClient dbchainBaseClient;


    public DBChainOpbClient(ClientConfig clientConfig, OpbConfig opbConfig, KeyManager km) {
        super(clientConfig, opbConfig, km);
        DBChainBaseClient baseClient = new DBChainBaseClient(clientConfig, opbConfig, km);
        this.dbchainBaseClient = baseClient;
    }


    public DBChainBaseClient getDBChainBaseClient() {
        return this.dbchainBaseClient;
    }
}
