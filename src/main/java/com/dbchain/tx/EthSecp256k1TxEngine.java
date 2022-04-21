package com.dbchain.tx;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import ethermint.crypto.v1.ethsecp256k1.Keys;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.key.KeyInfo;
import irita.sdk.key.KeyManager;
import irita.sdk.model.Account;
import irita.sdk.model.BaseTx;
import irita.sdk.tx.TxEngine;
import irita.sdk.util.ByteUtils;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.HashUtil;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import proto.cosmos.base.v1beta1.CoinOuterClass;
import proto.cosmos.tx.signing.v1beta1.Signing;
import proto.cosmos.tx.v1beta1.TxOuterClass;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EthSecp256k1TxEngine implements TxEngine {
    private String gasPrice;
    private final KeyManager km;
    private final String chainID;

    public EthSecp256k1TxEngine(KeyManager km, String chainID) {
        this.km = km;
        this.chainID = chainID;
    }

    public TxOuterClass.TxBody buildTxBody(List<GeneratedMessageV3> msgs) {
        return this.buildTxBodyWithMemo(msgs, (String)null);
    }

    public TxOuterClass.TxBody buildTxBodyWithMemo(List<GeneratedMessageV3> msgs, String memo) {
        if (msgs.size() == 0) {
            throw new IritaSDKException("size of msgs should larger than 0");
        } else {
            TxOuterClass.TxBody.Builder builder = TxOuterClass.TxBody.newBuilder();
            msgs.forEach((msg) -> {
                builder.addMessages(Any.pack(msg, "/"));
            });
            return builder.setMemo((String) Optional.ofNullable(memo).orElse("")).setTimeoutHeight(0L).build();
        }
    }

    @Override
    public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, BaseTx baseTx, Account account) {
        Objects.requireNonNull(baseTx, "baseTx not be null");
        KeyInfo keyInfo = this.km.getKeyDAO().read(baseTx.getFrom(), baseTx.getPassword());
        BigInteger privKey = keyInfo.getPrivKey();
        byte[] publicKeyEncoded = ECKey.publicKeyFromPrivate(privKey,true);
        long sequence = baseTx.getSequence() != 0L ? baseTx.getSequence() : account.getSequence();
        long accountNumber = baseTx.getAccountNumber() != 0L ? baseTx.getAccountNumber() : account.getAccountNumber();

        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder().addSignerInfos(TxOuterClass.SignerInfo.newBuilder().setPublicKey(Any.pack(Keys.PubKey.newBuilder().setKey(ByteString.copyFrom(publicKeyEncoded)).build(), "/")).setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT))).setSequence(sequence)).setFee(TxOuterClass.Fee.newBuilder().setGasLimit((long)baseTx.getGas()).addAmount(CoinOuterClass.Coin.newBuilder().setAmount(baseTx.getFee().getAmount()).setDenom(baseTx.getFee().getDenom()))).build();
        TxOuterClass.SignDoc signDoc = TxOuterClass.SignDoc.newBuilder().setBodyBytes(txBody.toByteString()).setAuthInfoBytes(ai.toByteString()).setAccountNumber(accountNumber).setChainId(this.chainID).build();

        byte[] sigBytes = sign(privKey, signDoc.toByteArray());
        byte[] ethSigBytes = new byte[sigBytes.length+1];
        for (int i = 0;i < sigBytes.length;i++){
            ethSigBytes[i]=sigBytes[i];
        }
        ethSigBytes[sigBytes.length] = 1;

        return TxOuterClass.Tx.newBuilder().setBody(txBody).setAuthInfo(ai).addSignatures(ByteString.copyFrom(ethSigBytes)).build();
    }

    public static byte[] sign(BigInteger privkey, byte[] signdoc) {
        BigInteger pubKey = Sign.publicKeyFromPrivate(privkey);
        ECKeyPair keyPair = new ECKeyPair(privkey, pubKey);
        Sign.SignatureData signature = Sign.signMessage(HashUtil.sha3(signdoc), keyPair, false);
        return ByteUtils.addAll(signature.getR(), signature.getS());
    }

}