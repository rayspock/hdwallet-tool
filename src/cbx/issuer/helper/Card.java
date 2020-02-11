package cbx.issuer.helper;

import java.util.ArrayList;



import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;

import cbx.issuer.coin.BCH;
import cbx.issuer.coin.BTC;
import cbx.issuer.coin.ETH;
import cbx.issuer.coin.LTC;
import cbx.issuer.coin.XRP;
import cbx.issuer.config.Coin;
import cbx.issuer.model.XpubDO;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static java.lang.String.format;

public class Card {

	public static final String COIN = "BTC,BCH,ETH,XRP";
	
	public static ArrayList<XpubDO> getAccountChain(String seedHex) throws Exception {
		System.out.println("seedHex:" + seedHex);

		String[] strCoin = COIN.split(",");
		ArrayList<XpubDO> pubKey = new ArrayList<XpubDO>();

		DeterministicKey privateMasterKey = HDKeyDerivation.createMasterPrivateKey(parseHexBinary(seedHex));
		System.out.println("privateMasterKey:" + printHexBinary(privateMasterKey.getSecretBytes())
				+ printHexBinary(privateMasterKey.getChainCode()));
		DeterministicKey key_m_44h = HDKeyDerivation.deriveChildKey(privateMasterKey,
				new ChildNumber(44 | ChildNumber.HARDENED_BIT));
		for (int i = 0; i < strCoin.length; i++) {
			Coin coin = Coin.valueOf(strCoin[i]);
			int coinType = coin.getValue();
			System.out.println(format("coinType:%s(%s)", strCoin[i], coinType));
			DeterministicKey key_m_44h_coinh = HDKeyDerivation.deriveChildKey(key_m_44h,
					new ChildNumber(coinType, true));
			DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(key_m_44h_coinh);

			DeterministicKey key_m_44h_coinh_0h = deterministicHierarchy.deriveChild(key_m_44h_coinh.getPath(), false,
					false, new ChildNumber(0, true));
			System.out.println("key_m_44h_coinh_0h:" + key_m_44h_coinh_0h.toString());

			XpubDO xpub = new XpubDO(coin, printHexBinary(key_m_44h_coinh_0h.getPubKey()),
					printHexBinary(key_m_44h_coinh_0h.getChainCode()));
			System.out.println("PubKey:" + xpub);
			pubKey.add(xpub);
		}
		return pubKey;
	}
	
	public static ArrayList<String> getAddressFromMasterPub(String pubKey, String chainCode) {
		ArrayList<String> address = new ArrayList<String>();

		DeterministicKey key_m_44h = HDKeyDerivation.createMasterPubKeyFromBytes(
				parseHexBinary(pubKey), parseHexBinary(chainCode));
		
		String[] strCoin = COIN.split(",");
		for (String item : strCoin) {
			Coin coin = Coin.valueOf(item);
			int coinType = coin.getValue();
			
			DeterministicKey key_m_44h_coinh = HDKeyDerivation.deriveChildKey(key_m_44h,
					new ChildNumber(coinType, false));
			
			DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(key_m_44h_coinh);
			
			DeterministicKey key_m_44h_coinh_0h = deterministicHierarchy.deriveChild(key_m_44h_coinh.getPath(), false,
					false, new ChildNumber(0, false));

			DeterministicKey changeKey = HDKeyDerivation.deriveChildKey(key_m_44h_coinh_0h, new ChildNumber(0, false));
			System.out.println("changeKey:" + changeKey);
			DeterministicKey addressKey = HDKeyDerivation.deriveChildKey(changeKey, new ChildNumber(0, false));

			System.out.println("addressKey:" + addressKey);

			String strPubKey = printHexBinary(addressKey.getPubKey());
			System.out.println("PubKey:" + strPubKey);
			address.add(pubToAddr(coin, strPubKey));
		}
		return address;
		
	}

	public static ArrayList<String> getAddress(ArrayList<XpubDO> accountPubKey) {
		ArrayList<String> address = new ArrayList<String>();

		for (XpubDO item : accountPubKey) {
			DeterministicKey key_m_44h_coinh_0h = HDKeyDerivation.createMasterPubKeyFromBytes(
					parseHexBinary(item.getPublicKey()), parseHexBinary(item.getChainCode()));

			DeterministicKey changeKey = HDKeyDerivation.deriveChildKey(key_m_44h_coinh_0h, new ChildNumber(0, false));
//			System.out.println("changeKey:" + changeKey);
			for(int i = 0; i < 20; i++) {
				System.out.println("index:" + i);
				DeterministicKey addressKey = HDKeyDerivation.deriveChildKey(changeKey, new ChildNumber(i, false));
		
//				System.out.println("addressKey:" + addressKey);
	
				String strPubKey = printHexBinary(addressKey.getPubKey());
//				System.out.println("PubKey:" + strPubKey);
				address.add(pubToAddr(item.getCoinType(), strPubKey));
			}
		}
		return address;
	}

	public static String pubToAddr(Coin coin, String pubKey) {
		System.out.println(format("%s pubToAddr", coin.name()));
		String result = "";
		switch (coin) {
		case BTC:
			result = BTC.pubToAddr(pubKey);
			break;
		case LTC:
			result = LTC.pubToAddr(pubKey);
			break;
		case BCH:
			result = BCH.pubToAddr(pubKey);
			break;
		case ETH:
			result = ETH.pubToAddr(pubKey);
			break;
		case XRP:
			result = XRP.pubToAddr(pubKey);
			break;
		default:
			break;
		}
		System.out.println(format("[%s]Address:%s", coin.name() ,result));
		return result;
	}
}
