package com.dbchain.tx;

import com.dbchain.client.DBChainBaseClient;
import com.dbchain.client.DBChainOpbClient;

import com.google.protobuf.GeneratedMessageV3;
import irita.sdk.constant.enums.BroadcastMode;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.model.BaseTx;
import irita.sdk.model.Fee;
import irita.sdk.model.GasInfo;
import irita.sdk.model.ResultTx;
import irita.sdk.tx.TxEngine;
import irita.sdk.util.HashUtils;
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
        BaseTx simulateBaseTx = new BaseTx(200000, new Fee("200000", "adbctoken"), BroadcastMode.Async);
        int gasWanted = computeGasWanted(baseClient.simulateTx(msgs,simulateBaseTx,baseClient.queryAccount(simulateBaseTx)));
        String fee = computeFee(gasWanted,baseClient.getGasPrice());
        BaseTx baseTx = new BaseTx(gasWanted, new Fee(fee, "adbctoken"), BroadcastMode.Async);
        TxOuterClass.Tx tx = txEngine.signTx(txBody, baseTx, baseClient.queryAccount(baseTx));
        byte[] txBz = tx.toByteArray();
        System.out.println("local hash :");
        System.out.println(computeTxHash(txBz));
        ResultTx resultTx = baseClient.getRpcClient().broadcastTx(txBz, BroadcastMode.Async);
        return resultTx.getResult().getHash();
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
