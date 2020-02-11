package cbx.issuer.coin;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.util.Arrays;

import org.bitcoinj.core.Sha256Hash;

import cbx.issuer.coin.ripple.XRPBase58;
import cbx.issuer.helper.Util;

public class XRP {
	
	public static String pubToAddr(String pubKey) {
		String result = "";

		byte[] byteSb = parseHexBinary(pubKey);
		byte[] hashedPublicKey = Util.hash160(byteSb);
		
		StringBuilder address = new StringBuilder("00");
		address.append(printHexBinary(hashedPublicKey));
		
		byte[] byteSum = parseHexBinary(address.toString());
		byte[] byteSHASum1 = Sha256Hash.hash(byteSum);
//		System.out.println("1:" + printHexBinary(byteSHASum1));
		byte[] byteSHASum2 = Sha256Hash.hash(byteSHASum1);
//		System.out.println("2:" + printHexBinary(byteSHASum2));
		byte[] checkSum = Arrays.copyOfRange(byteSHASum2, 0, 4);
//		System.out.println(printHexBinary(checkSum));
		
		address.append(printHexBinary(checkSum));
//		System.out.println(address.toString());
		result = XRPBase58.encode(parseHexBinary(address.toString()));
		return result;
	}
}
