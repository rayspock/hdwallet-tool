package cbx.issuer.coin;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.nio.charset.StandardCharsets;


import org.bitcoinj.core.ECKey;

import cbx.issuer.helper.Util;

public class ETH {
	

	public static String pubToAddr(String pubKey) {
		String result = "";

		ECKey ecKey = ECKey
				.fromPublicOnly(parseHexBinary(pubKey));

		String pubkey = Util.leftPadWithZeroes(ecKey.getPubKeyPoint().getAffineXCoord().toString(), 64)
				+ Util.leftPadWithZeroes(ecKey.getPubKeyPoint().getAffineYCoord().toString(), 64);

		byte[] byteSb = parseHexBinary(pubkey);
		byte[] shas3 = Util.keccak256(byteSb);
		
		String hexString = printHexBinary(shas3);
		System.out.println("hexString:" + hexString);
		result = "0x" + hexString.substring(hexString.length() - 40, hexString.length());
		
		return toChecksumAddress(result);
	}
	
	/**
     * Checksum address encoding as per
     * <a href="https://github.com/ethereum/EIPs/blob/master/EIPS/eip-55.md">EIP-55</a>.
     *
     * @param address a valid hex encoded address
     * @return hex encoded checksum address
     */
    public static String toChecksumAddress(String address) {
        String lowercaseAddress = address.replace("0x", "").toLowerCase();
        String addressHash = printHexBinary(Util.keccak256(lowercaseAddress.getBytes(StandardCharsets.UTF_8)));

        StringBuilder result = new StringBuilder(lowercaseAddress.length() + 2);

        result.append("0x");

        for (int i = 0; i < lowercaseAddress.length(); i++) {
            if (Integer.parseInt(String.valueOf(addressHash.charAt(i)), 16) >= 8) {
                result.append(String.valueOf(lowercaseAddress.charAt(i)).toUpperCase());
            } else {
                result.append(lowercaseAddress.charAt(i));
            }
        }

        return result.toString();
    }
}
