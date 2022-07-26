package com.dbchain.tx;

import com.dbchain.client.DBChainBaseClient;
import com.dbchain.client.DBChainOpbClient;

import com.dbchain.keymanager.DBChainKeyManager;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import ethermint.types.v1.DBChainAccount;
import irita.sdk.client.GrpcFactory;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.constant.enums.BroadcastMode;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.key.KeyInfo;
import irita.sdk.model.*;
import irita.sdk.tx.TxEngine;
import irita.sdk.util.HashUtils;
import proto.cosmos.auth.v1beta1.Auth;
import proto.cosmos.auth.v1beta1.QueryGrpc;
import proto.cosmos.auth.v1beta1.QueryOuterClass;
import proto.cosmos.tx.v1beta1.TxOuterClass;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.dbchain.utils.CryptoUtil.bytesToHexString;

public class DBChainTxService {

    public static String buildAndSendTx(DBChainOpbClient client, List<GeneratedMessageV3> msgs) throws IOException ,IritaSDKException{
        DBChainBaseClient baseClient = client.getDBChainBaseClient();
        TxEngine txEngine = baseClient.getTxEngine();
        TxOuterClass.TxBody txBody = txEngine.buildTxBody(msgs);
        BaseTx simulateBaseTx = new BaseTx(200000, new Fee("200000", "adbctoken"), BroadcastMode.Commit);
        int gasWanted = computeGasWanted(baseClient.simulateTx(msgs,simulateBaseTx,baseClient.queryAccount(simulateBaseTx)));
        String fee = computeFee(gasWanted,baseClient.getGasPrice());
        BaseTx baseTx = new BaseTx(gasWanted, new Fee(fee, "adbctoken"), BroadcastMode.Commit);
        TxOuterClass.Tx tx = txEngine.signTx(txBody, baseTx, baseClient.queryAccount(baseTx));
        byte[] txBz = tx.toByteArray();
        System.out.println("local hash :");
        System.out.println(computeTxHash(txBz));
        ResultTx resultTx = baseClient.getRpcClient().broadcastTx(txBz, BroadcastMode.Commit);
        return resultTx.getResult().getHash();
    }


    public static void buildAndSendTxForTest(DBChainOpbClient client, List<GeneratedMessageV3> msgs) throws Exception {
        DBChainBaseClient baseClient = client.getDBChainBaseClient();
        TxEngine txEngine = baseClient.getTxEngine();
        TxOuterClass.TxBody txBody = txEngine.buildTxBody(msgs);
        BaseTx simulateBaseTx = new BaseTx(200000, new Fee("200000", "adbctoken"), BroadcastMode.Async);
        int gasWanted = computeGasWanted(baseClient.simulateTx(msgs,simulateBaseTx,baseClient.queryAccount(simulateBaseTx)));
        String fee = computeFee(gasWanted,baseClient.getGasPrice());
        BaseTx baseTx = new BaseTx(gasWanted, new Fee(fee, "adbctoken"), BroadcastMode.Async);
        KeyInfo keyInfo = baseClient.getKeyManager().getKeyDAO().read(baseTx.getFrom(), baseTx.getPassword());

        long seq = 0;

        for(long i = 0; i < 100; i++) {
            Account account = queryAccount(baseClient, keyInfo.getAddress());
            if (i == 0){
                seq = account.getSequence();
                System.out.println("first query seq :" + account.getSequence());
            }else {
                account.setSequence(seq + i);
                System.out.println("next seq :" + account.getSequence());
            }
            TxOuterClass.Tx tx = txEngine.signTx(txBody, baseTx, account);
            byte[] txBz = tx.toByteArray();
            System.out.println("local hash :");
            System.out.println(computeTxHash(txBz));
            ResultTx resultTx = baseClient.getRpcClient().broadcastTx(txBz, BroadcastMode.Async);
        }
    }

    public static Account queryAccount(DBChainBaseClient baseclient, String address) {
        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(address).build();
        QueryOuterClass.QueryAccountResponse resp = QueryGrpc.newBlockingStub(baseclient.getGrpcClient()).account(req);
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


    public static int computeGasWanted(GasInfo gasInfo){
        return (int) (Double.parseDouble(gasInfo.getGasUsed())*1.3);
    }

    public static String computeFee(int gasWant, String gasPriceStr){
        double gasPrice = Double.parseDouble(gasPriceStr);
        return new BigDecimal(Double.toString(gasPrice * gasWant)).toPlainString();
    }

    public static String computeTxHash(byte[] txBz){
        return  bytesToHexString(HashUtils.sha256(txBz)).toUpperCase();
    }

}
