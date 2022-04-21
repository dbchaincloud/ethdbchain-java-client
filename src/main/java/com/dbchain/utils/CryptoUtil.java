package com.dbchain.utils;

import com.alibaba.fastjson.JSON;
import com.dbchain.client.RESTClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.google.protobuf.ByteString;
import irita.sdk.key.KeyManager;
import irita.sdk.util.ByteUtils;
import org.bitcoinj.core.Base58;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.HashUtil;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Map;

public class CryptoUtil {
    public static ByteString transferToBytes(Map<String, String> map) {
        return ByteString.copyFrom(JSON.toJSONString(map).getBytes());
    }

    public static String encodeDataToBase64String(String data) {
        return new String(Base64.getEncoder().encode(data.getBytes()));
    }

    public static String encodeDataToBase58String(byte[] data) {
        return Base58.encode(data);
    }

    public static String makeAccessToken(RESTClient restClient) {
        KeyManager km = restClient.getKm();
        long currentTimeMillis = System.currentTimeMillis();
        BigInteger privKey = km.getCurrentKeyInfo().getPrivKey();
        byte[] publicKeyCompressed = ECKey.publicKeyFromPrivate(privKey,true);
        byte[] sigBytes = sign(privKey, (currentTimeMillis+"").getBytes());
        String encodedPubKey = encodeDataToBase58String(publicKeyCompressed);
        byte[] ethSigBytes = new byte[sigBytes.length+1];
        for (int i = 0;i < sigBytes.length;i++){
            ethSigBytes[i]=sigBytes[i];
        }
        ethSigBytes[sigBytes.length] = 1;
        String encodedSig = encodeDataToBase58String(ethSigBytes);
        return encodedPubKey+":"+currentTimeMillis+":"+encodedSig;

    }

    public static byte[] sign(BigInteger privkey, byte[] signdoc) {
        BigInteger pubKey = Sign.publicKeyFromPrivate(privkey);
        ECKeyPair keyPair = new ECKeyPair(privkey, pubKey);
        Sign.SignatureData signature = Sign.signMessage(HashUtil.sha3(signdoc), keyPair, false);
        return ByteUtils.addAll(signature.getR(), signature.getS());
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
