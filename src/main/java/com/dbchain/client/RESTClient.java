package com.dbchain.client;

import com.dbchain.keymanager.DBChainKeyManager;
import irita.sdk.client.RpcClient;

public class RESTClient {
    private  String url;
    private DBChainKeyManager km;

    public RESTClient(String url, DBChainKeyManager km) {
        this.url = url;
        this.km = km;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DBChainKeyManager getKm() {
        return km;
    }

    public void setKm(DBChainKeyManager km) {
        this.km = km;
    }
}
