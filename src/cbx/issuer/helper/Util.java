package cbx.issuer.helper;

import static java.lang.String.format;

import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.spongycastle.crypto.digests.RIPEMD160Digest;

public class Util {
	public static String leftPadWithZeroes(String originalString, int length) {
		String paddedString = originalString;
		while (paddedString.length() < length) {
			paddedString = "0" + paddedString;
		}
		return paddedString;
	}
	
	public static String padLeft(String str, int n, char addedData) {
		return format("%1$" + n + "s", str).replace(' ', addedData);
	}
	
	public static byte[] hash160(byte[] byteSb) {
		byte[] byteSHA = Sha256Hash.hash(byteSb);
		RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
		ripemd160Digest.update(byteSHA, 0, byteSHA.length);
		byte[] hashedPublicKey = new byte[20];
		ripemd160Digest.doFinal(hashedPublicKey, 0);
		
		return hashedPublicKey;
	}
	
	public static byte[] keccak256(byte[] byteSb) {
		Keccak.Digest256 digest = new Keccak.Digest256();
		digest.update(byteSb,0,byteSb.length);
		return digest.digest();
	}

}
