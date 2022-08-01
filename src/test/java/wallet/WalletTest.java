package wallet;

import com.dbchain.wallet.DBChainWallet;

import irita.sdk.util.Bech32Utils;

import org.ethereum.crypto.ECKey;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.jce.ECNamedCurveTable;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.BigIntegers;

import java.math.BigInteger;
import java.util.Formatter;

import static com.dbchain.wallet.DBChainWallet.getBigIntTypePrivkeyByMnemonic;
import static com.google.common.primitives.Bytes.concat;


public class WalletTest {
    private static final Logger logger = LoggerFactory.getLogger(WalletTest.class);

    @Test
    public void createMnemonic() {
        for(int i = 0; i < 5; i ++){
            String mnemonic = DBChainWallet.createMnemonic();
            logger.info(mnemonic);
        }
    }

    @Test
    public void getPrivkeyByMnemonic() {
        //String mnemonic = DBChainWallet.createMnemonic();
        String mnemonic = "lunar spy clerk race family develop asthma flee safe unable aisle equal desert biology viable advice oxygen annual fan debate chef real atom orchard";
        //String mnemonic = "alter payment curtain rate fashion mimic parrot bamboo shuffle youth token come brick debris wall ostrich champion salon wide easily rely razor sell differ";//latn
        String privateKey = DBChainWallet.getPrivkeyByMnemonic(mnemonic);
        logger.info(privateKey);
    }

    @Test
    public void getAddressByMnemonic() {
        //String mnemonic = DBChainWallet.createMnemonic();
        //String mnemonic = "lunar spy clerk race family develop asthma flee safe unable aisle equal desert biology viable advice oxygen annual fan debate chef real atom orchard";//test account hqk0
        //String mnemonic = "shoe gift globe link model flag sorry cook above faculty remove divert canyon come refuse excite route cram walnut solid spare point casino mistake";//admin
        //String mnemonic = "alter payment curtain rate fashion mimic parrot bamboo shuffle youth token come brick debris wall ostrich champion salon wide easily rely razor sell differ";//latn
        //String mnemonic = "owner erode dwarf average fruit venture okay senior carpet hundred minimum below";//7akf
       // String mnemonic = "speak universe hurry method human mule detail shuffle run squirrel exhibit account";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        //String mnemonic = "miracle jeans parrot recall fault canvas alert purpose notable civil indoor mountain";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        //String mnemonic = "lady unfold member maximum dolphin clay together tree torch brisk ketchup reason";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        //String mnemonic = "solar robust because depart evoke boat mesh purchase insane front before dinner";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
       // String mnemonic = "hello multiply inquiry sea future odor burger boil vacant major combine cave";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        //String mnemonic = "source olive poverty void alone wage extend speed fish voyage average more";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        //String mnemonic = "thunder salad planet business slender dog market level tell furnace nephew struggle";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7


        String mnemonic1 = "practice obvious paper toilet exhaust social unveil try animal video sing priority";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        String mnemonic2 = "trap pave plastic cereal decline fog spend tortoise bounce stamp glass answer";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        String mnemonic3 = "drastic figure nothing unknown clump fashion tenant unit lottery access select more";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        String mnemonic4 = "meat actress supply cruel middle enact tragic arena asset ramp reduce merge";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        String mnemonic5 = "chaos mutual raise hood arrow learn seek divert coyote celery cloud fire";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7



        //        method humor random shadow token dash near entry health scene harsh elephant
//        cat pistol sight weapon together true fossil illness orchard mail render ride
//        execute deliver pair worth riot tool accident race pigeon faculty toward trash
//        beach tortoise category memory sibling afford physical differ talk burst embark scheme
//        maximum degree beyond coffee dutch hockey insect rescue large arctic lady october


//        String mnemonic = "public jar agent treat select price song spot tunnel worry party spray";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
//        String mnemonic = "grape hub undo slight clarify narrow pause margin scorpion domain glance still";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
//        String mnemonic = "unfold doll powder weekend chalk apart captain scheme smooth social tomato wage";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
//        String mnemonic = "fragile outdoor number gossip cause arrow cargo idea alpha zebra tortoise strong";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
//        String mnemonic = "palm infant exotic tilt portion grain immense change develop burst company merit";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        String address1 = DBChainWallet.getAddressByMnemonic(mnemonic1);
        String address2 = DBChainWallet.getAddressByMnemonic(mnemonic2);
        String address3 = DBChainWallet.getAddressByMnemonic(mnemonic3);
        String address4 = DBChainWallet.getAddressByMnemonic(mnemonic4);
        String address5 = DBChainWallet.getAddressByMnemonic(mnemonic5);
        logger.info(address1);
        logger.info(address2);
        logger.info(address3);
        logger.info(address4);
        logger.info(address5);
    }

    @Test
    public void getCompressedPubkeyMnemonic() {
        //String mnemonic = DBChainWallet.createMnemonic();
        //String mnemonic = "lunar spy clerk race family develop asthma flee safe unable aisle equal desert biology viable advice oxygen annual fan debate chef real atom orchard";//test account hqk0
        //String mnemonic = "shoe gift globe link model flag sorry cook above faculty remove divert canyon come refuse excite route cram walnut solid spare point casino mistake";//admin
        //String mnemonic = "alter payment curtain rate fashion mimic parrot bamboo shuffle youth token come brick debris wall ostrich champion salon wide easily rely razor sell differ";//latn
        //String mnemonic = "owner erode dwarf average fruit venture okay senior carpet hundred minimum below";//7akf
        //String mnemonic = "speak universe hurry method human mule detail shuffle run squirrel exhibit account";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        String mnemonic = "miracle jeans parrot recall fault canvas alert purpose notable civil indoor mountain";//dbc1j9fuggrk45j8q6tc47s4sc42wscl0hzf7h87z7
        BigInteger privKey = getBigIntTypePrivkeyByMnemonic(mnemonic);
        byte[] compressPubBytes = ECKey.publicKeyFromPrivate(privKey,true);
        String pubkey =  encodeHexString(compressPubBytes);
        logger.info(pubkey);
    }


    //03b7b0727b41d357ec33c6ef8250d99d7af7a58ea79d0e557bf870a02d4fa24ed9
    @Test
    public void getAddressByPubkey() {
        String pubkey= "03b7b0727b41d357ec33c6ef8250d99d7af7a58ea79d0e557bf870a02d4fa24ed9";
        byte[] notCompressPubBytes = compressedToUncompressed(toByteArray(pubkey));
        byte[] addressBytes = ECKey.computeAddress(notCompressPubBytes);
        byte[] pre20 = new byte[20];
        System.arraycopy(addressBytes, 0, pre20, 0, 20);
        String address = Bech32Utils.toBech32("dbc", pre20);
        logger.info(address);
    }

    public static byte[] toByteArray(String hexString) {
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

    static ECParameterSpec SPEC = ECNamedCurveTable.getParameterSpec("secp256k1");

    static byte[] compressedToUncompressed(byte[] compKey) {
        ECPoint point = SPEC.getCurve().decodePoint(compKey);
        byte[] x = point.getXCoord().getEncoded();
        byte[] y = point.getYCoord().getEncoded();
        // concat 0x04, x, and y, make sure x and y has 32-bytes:
        return concat(new byte[] {0x04}, x, y);
    }

    public static String encodeHexString(byte[] data) {
        Formatter formatter = new Formatter();
        for (byte b : data) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
