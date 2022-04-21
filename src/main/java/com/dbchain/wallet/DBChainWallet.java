package com.dbchain.wallet;

import irita.sdk.util.Bech32Utils;
import irita.sdk.util.Bip44Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.ethereum.crypto.ECKey;
import org.web3j.crypto.MnemonicUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DBChainWallet {
    private static final String keyPath = "m/44'/60'/0'/0/0";
    private static final String hrp = "dbc";

    public static String createMnemonic(){
        byte[] initialEntropy = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initialEntropy);
        return MnemonicUtils.generateMnemonic(initialEntropy);
    }

    public static String getPrivkeyByMnemonic(String mnemonic){
        byte[] seed = Bip44Utils.getSeed(mnemonic);
        DeterministicKey dk = Bip44Utils.getDeterministicKey(mnemonic, seed, keyPath);
        BigInteger privKey = dk.getPrivKey();
        return privKey.toString(16);
    }

    public static BigInteger getBigIntTypePrivkeyByMnemonic(String mnemonic){
        byte[] seed = Bip44Utils.getSeed(mnemonic);
        DeterministicKey dk = Bip44Utils.getDeterministicKey(mnemonic, seed, keyPath);
        return dk.getPrivKey();
    }

    public static String getAddressByMnemonic(String mnemonic){
        BigInteger privKey = getBigIntTypePrivkeyByMnemonic(mnemonic);
        byte[] notCompressPubBytes = ECKey.publicKeyFromPrivate(privKey,false);
        byte[] addressBytes = ECKey.computeAddress(notCompressPubBytes);
        byte[] pre20 = new byte[20];
        System.arraycopy(addressBytes, 0, pre20, 0, 20);
        String address = Bech32Utils.toBech32(hrp, pre20);
        return address;
    }

}
