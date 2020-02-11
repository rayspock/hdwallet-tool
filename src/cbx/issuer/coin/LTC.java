package cbx.issuer.coin;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.util.Arrays;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

import cbx.issuer.helper.Util;

public class LTC {
	
	public static String pubToAddr(String pubKey) {
		String result = "";

		byte[] byteSb = parseHexBinary(pubKey);
		byte[] hashedPublicKey = Util.hash160(byteSb);
		
		
		StringBuilder redeemScript = new StringBuilder("0014");
		redeemScript.append(printHexBinary(hashedPublicKey));
		//System.out.println("redeemScript:" + redeemScript.toString());
		String redeemScriptHash160 = printHexBinary(Util.hash160(parseHexBinary(redeemScript.toString())));
		
		// Prefix
		// 00	P2PKH	1	1AKDDsfTh8uY4X3ppy1m7jw1fVMBSMkzjP
		// 05	P2SH	3	34nSkinWC9rDDJiUY438qQN1JHmGqBHGW7
		// 32   P2SH    M   MTf4tP1TCNBn8dNkyxeBVoPrFCcVzxJvvh
		// 30   P2PKH   L   LM2WMpR1Rp6j3Sa59cMXMs1SPzj9eXpGc1
		
		StringBuilder address = new StringBuilder("05");
		address.append(redeemScriptHash160);
		
		byte[] byteSum = parseHexBinary(address.toString());
		byte[] byteSHASum1 = Sha256Hash.hash(byteSum);
		//System.out.println("1:" + printHexBinary(byteSHASum1));
		byte[] byteSHASum2 = Sha256Hash.hash(byteSHASum1);
		//System.out.println("2:" + printHexBinary(byteSHASum2));
		byte[] checkSum = Arrays.copyOfRange(byteSHASum2, 0, 4);
		//System.out.println(printHexBinary(checkSum));
		
		address.append(printHexBinary(checkSum));
		//System.out.println(address.toString());
		result = Base58.encode(parseHexBinary(address.toString()));
		return result;
	}

}
