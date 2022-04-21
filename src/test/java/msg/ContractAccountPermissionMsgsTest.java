package msg;

import com.dbchain.client.DBChainOpbClient;
import com.dbchain.keymanager.DBChainKeyManager;

import com.dbchain.tx.DBChainTxService;
import com.google.protobuf.GeneratedMessageV3;
import dbchain.msgs.MsgsContractAccount;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.util.Bech32Utils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static client.BuildClient.buildDBChainOpbClient;

public class ContractAccountPermissionMsgsTest {
    private static final Logger logger = LoggerFactory.getLogger(ContractAccountPermissionMsgsTest.class);


    @Test
    public void blockAccount() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();
        logger.info(km.getCurrentKeyInfo().getAddress());
        String account = "dbc165a7euqtwahp3xxhxntduhkl4vmtmgdaamlatn";

        MsgsContractAccount.MsgBlockAccount msg = MsgsContractAccount.MsgBlockAccount.newBuilder()
                .setAddress(account)
                .setOperator(km.getCurrentKeyInfo().getAddress())
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void unblockAccount() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();
        logger.info(km.getCurrentKeyInfo().getAddress());
        String account = "dbc165a7euqtwahp3xxhxntduhkl4vmtmgdaamlatn";
        MsgsContractAccount.MsgUnblockAccount msg = MsgsContractAccount.MsgUnblockAccount.newBuilder()
                .setAddress(account)
                .setOperator(km.getCurrentKeyInfo().getAddress())
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void blockContract() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();
        logger.info(km.getCurrentKeyInfo().getAddress());
        String contractAddress = "0x8A6A8C3Bc94dc9B4dc811476CEA84F2B95A2029c";
        MsgsContractAccount.MsgBlockContract msg =  MsgsContractAccount.MsgBlockContract.newBuilder()
                .setContractAddress(contractAddress)
                .setOperator(km.getCurrentKeyInfo().getAddress())
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void unblockContract() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();
        logger.info(km.getCurrentKeyInfo().getAddress());
        String contractAddress = "0x8A6A8C3Bc94dc9B4dc811476CEA84F2B95A2029c";
        MsgsContractAccount.MsgUnblockContract msg =  MsgsContractAccount.MsgUnblockContract.newBuilder()
                .setContractAddress(contractAddress)
                .setOperator(km.getCurrentKeyInfo().getAddress())
                //.setOperator("ethm1pvxewh2d7eawxs8n4w2nj92x54pa5q0389ytec")
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void destroyContract() {
        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();
        logger.info(km.getCurrentKeyInfo().getAddress());
        String contractAddress = "0x75c2fe92E2b172826328Ef90D7942bD6b6E1edB4";
        MsgsContractAccount.MsgDestroyContract msg =  MsgsContractAccount.MsgDestroyContract.newBuilder()
                .setContractAddress(contractAddress)
                .setOperator(km.getCurrentKeyInfo().getAddress())
                .build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void QueryLocalAddressConvert() {
        String address = "dbc1l5u7efmwj46zpwh6fhgkehqj2e7wxtg552hugh";
        //String address = "0xFD39ECA76E957420BAFA4DD16CDC12567CE32D14";
        String resp = "";
        try {
            resp = localConvertAddress(address);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        logger.info("address : "+resp);
    }

    public String localConvertAddress(String address) throws Exception{
        if (!address.startsWith("0x") && !address.startsWith("dbc")){
            throw new Exception(" not a valid address");
        }

        if (address.startsWith("0x")){
            return Bech32Utils.toBech32("dbc",toByteArray(address));
        }else {
            return "0x"+ Bech32Utils.bech32ToHex(address);
        }
    }

    public static byte[] toByteArray(String hexString) {
        hexString = hexString.replaceAll("0x", "");
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}
