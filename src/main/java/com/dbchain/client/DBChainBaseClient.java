package com.dbchain.client;

import com.dbchain.tx.EthSecp256k1TxEngine;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import ethermint.types.v1.DBChainAccount;
import irita.sdk.client.BaseClient;
import irita.sdk.client.RpcClient;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.key.KeyManager;
import irita.sdk.model.Account;
import irita.sdk.model.BaseTx;
import irita.sdk.model.GasInfo;
import irita.sdk.tx.TxEngine;
import proto.cosmos.auth.v1beta1.QueryGrpc;
import proto.cosmos.auth.v1beta1.QueryOuterClass;

import java.io.IOException;
import java.util.List;

public class DBChainBaseClient extends BaseClient {
    private String gasPrice;
    private TxEngine txEngine;
    private RpcClient rpcClient;
    private KeyManager km;
    private final String GASPRICE = "1000000000";

    public DBChainBaseClient(ClientConfig clientConfig, OpbConfig opbConfig, KeyManager keyManager) {
        super(clientConfig,opbConfig,keyManager);
        this.txEngine = new EthSecp256k1TxEngine(keyManager, clientConfig.getChainID());
        this.rpcClient = new DBChainRPCClient(clientConfig,opbConfig);
        this.gasPrice = GASPRICE;
        this.km = keyManager;
    }

    public synchronized GasInfo simulateTx(List<GeneratedMessageV3> msgs, BaseTx baseTx, Account account) throws IOException {
        if (account == null) {
            account = this.queryAccount(baseTx);
        }

        TxEngine txEngine = this.getTxEngine();
        byte[] txBytes = txEngine.buildAndSign(msgs, baseTx, account);
        return this.rpcClient.simulateTx(txBytes);
    }

    public Account queryAccount(String address) {
        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(address).build();
        QueryOuterClass.QueryAccountResponse resp = QueryGrpc.newBlockingStub(super.getGrpcClient()).account(req);
        DBChainAccount.EthAccount ethAccount = null;
        try {
            ethAccount = resp.getAccount().unpack(DBChainAccount.EthAccount.class);
        } catch (InvalidProtocolBufferException var6) {
            throw new IritaSDKException("account:\t" + address + "is not exist", var6);
        }
        Account account = new Account();
        account.setAddress(ethAccount.getBaseAccount().getAddress());
        account.setAccountNumber(ethAccount.getBaseAccount().getAccountNumber());
        account.setSequence(ethAccount.getBaseAccount().getSequence());
        return account;
    }

    public TxEngine getTxEngine() {
        return this.txEngine;
    }

    public RpcClient getRpcClient() {
        return this.rpcClient;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public KeyManager getKeyManager() {
        return this.km;
    }

}
