package com.dbchain.keymanager;

import irita.sdk.exception.IritaSDKException;
import irita.sdk.key.AlgoEnum;
import irita.sdk.key.KeyInfo;
import irita.sdk.key.KeyManager;
import irita.sdk.util.Bech32Utils;
import irita.sdk.util.Bip44Utils;
import irita.sdk.util.SecP256K1Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.ethereum.crypto.ECKey;

import java.math.BigInteger;


public class DBChainKeyManager extends KeyManager {
    private String keyPath = "m/44'/60'/0'/0/0";
    private String hrp = "dbc";
    public DBChainKeyManager() {
    }

    public void recover(BigInteger privKey) {
        byte[] notCompressPubBytes = ECKey.publicKeyFromPrivate(privKey,false);
        ECPoint publicKey = SecP256K1Utils.getPublicKeyFromPrivkey(privKey);
        byte[] addressBytes = ECKey.computeAddress(notCompressPubBytes);
        byte[] pre20 = new byte[20];
        System.arraycopy(addressBytes, 0, pre20, 0, 20);
        String address = Bech32Utils.toBech32(this.getHrp(), pre20);
        this.setDefaultKeyDao(privKey, publicKey, address);
    }

    public String export(String password) {
        return this.export(password, this.getCurrentKeyInfo().getPrivKey().toByteArray());
    }

    public String export(String name, String password) {
        if (!this.keyDAO.has(name)) {
            throw new IritaSDKException(String.format("name %s hasn't existed", name));
        } else {
            KeyInfo keyInfo = this.keyDAO.read(name, password);
            byte[] privKey = keyInfo.getPrivKey().toByteArray();
            return super.export(password, privKey);
        }
    }

    public AlgoEnum getAlgo() {
        return AlgoEnum.SECP256K1;
    }

    public void recover(String mnemonic) {
        byte[] seed = Bip44Utils.getSeed(mnemonic);
        DeterministicKey dk = Bip44Utils.getDeterministicKey(mnemonic, seed, this.getKeyPath());
        BigInteger privKey = dk.getPrivKey();
        this.recover(privKey);
    }

    public String getKeyPath() {
        return this.keyPath;
    }

    public String getHrp() {
        return this.hrp;
    }
}
