package cbx.issuer.coin;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;


import cbx.issuer.coin.bitcoincash.BitcoinCashAddressFormatter;
import cbx.issuer.coin.bitcoincash.BitcoinCashAddressType;
import cbx.issuer.coin.bitcoincash.MoneyNetwork;
import cbx.issuer.helper.Util;

public class BCH {
	
	public static String pubToAddr(String pubKey) {
		String result = "";
		byte[] byteSb = parseHexBinary(pubKey);
		byte[] hashedPublicKey = Util.hash160(byteSb);
		
		System.out.println("BCH pubKeyHash160:" + printHexBinary(hashedPublicKey));

		result = BitcoinCashAddressFormatter.toCashAddress(BitcoinCashAddressType.P2PKH, hashedPublicKey, MoneyNetwork.MAIN);
		
		return result;
	}
}
