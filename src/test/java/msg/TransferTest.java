package msg;

import bank.v1beta1.Tx;
import com.dbchain.client.DBChainOpbClient;
import com.dbchain.keymanager.DBChainKeyManager;
import com.dbchain.tx.DBChainTxService;
import com.google.protobuf.GeneratedMessageV3;
import cosmos.base.v1beta1.CoinOuterClass;

import irita.sdk.exception.IritaSDKException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static client.BuildClient.buildDBChainOpbClient;

public class TransferTest {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationMsgsTest.class);

    @Test
    public void Transfer() {

        DBChainOpbClient client = buildDBChainOpbClient();
        DBChainKeyManager km = (DBChainKeyManager) client.getDBChainBaseClient().getKeyManager();

        Tx.MsgSend msg = Tx.MsgSend.newBuilder()
                .addAmount(CoinOuterClass.Coin.newBuilder().setAmount("1000").setDenom("adbctoken").build())
//                .setFromAddress("dbc14ppnu5c4whxvl8sej7yqp4zjsp79u0my8mhqk0")
                .setFromAddress(km.getCurrentKeyInfo().getAddress())
                .setToAddress("dbc165a7euqtwahp3xxhxntduhkl4vmtmgdaamlatn")
                .build();
        //addAmount(CoinOuterClass.Coin.newBuilder().setAmount(amount).setDenom(denom).build()).setFromAddress(account.getAddress()).setToAddress(toAddress).build();
        List<GeneratedMessageV3> msgs = Collections.singletonList(msg);
        try {
            logger.info("tx hash : " + DBChainTxService.buildAndSendTx(client, msgs));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IritaSDKException e){
            logger.info(e.getMessage());
        }
    }
}
