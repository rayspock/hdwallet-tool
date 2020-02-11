

import java.util.ArrayList;

import cbx.issuer.config.Coin;
import cbx.issuer.helper.Card;
import cbx.issuer.model.XpubDO;

public class Main {

	public static void main(String[] args) {
//		String pubKey = "034A306E3CB1F3BCB05A491F4E54C12C692E1C06E566DE7FBF49322CA356A633E6";
//		String chainCode = "D7D47DE8C031846391AEEB93AA55AC3307682927F269BC84E714981BD2FF53D2";
//		Card.getAddressFromMasterPub(pubKey, chainCode);
		
//		XpubDO xpub = new XpubDO();
//		xpub.setChainCode("4dfeeb9fa8de3c40b26b939255fedf48672ec1e8d6589409c2e1cacc382f5403");
//		xpub.setCoinType(Coin.BTC);
//		xpub.setPublicKey("03f100efa6e15054e977acb20d1d0485dc75c6f896b3deb083cda1ef5d3504d45f");
		
		//86
		XpubDO xpub = new XpubDO();
		xpub.setChainCode("9da5bcf011fac8b840bc2ce4c8a08a9250eaf7493ff836374bc4bfbd12dba033");
		xpub.setCoinType(Coin.BTC);
		xpub.setPublicKey("03ba6a5f2cd87650e4c9703e5ab5fc398db19ccea67469b559489841930b0cf3d2");
		
		
		XpubDO xpubltc = new XpubDO();
		xpubltc.setChainCode("30cc11dda1f67c2a7239d3f5347341a6a5f155daf30e8ad1ffb53cb97ca66066");
		xpubltc.setCoinType(Coin.LTC);
		xpubltc.setPublicKey("034fd9f560a0fb9ad686de071439aeeeabc79c9d026780f967d39dc49db5afbc0d");
		
		XpubDO xpubbch = new XpubDO();
		xpubbch.setChainCode("4d88b146056a73128f7c69c18d3b57dc19c03f2748cb0659e318e9c17ce28c11");
		xpubbch.setCoinType(Coin.BCH);
		xpubbch.setPublicKey("037328446978bd5164f1aee6c9f4ea79b265724cf6bed8cccfef937f34296a3f55");
		
		XpubDO xrp = new XpubDO();
		xrp.setChainCode("722f74fd7204869b99409b4d903df2c41c2cbe4ff919743180756468458b876f");
		xrp.setCoinType(Coin.XRP);
		xrp.setPublicKey("03cace1656ca039caa7e66358803432cd70a9e47f1837522736e988973761cb754");
		
		ArrayList<XpubDO> arr = new ArrayList<XpubDO>();
		arr.add(xpub);
		arr.add(xpubltc);
		arr.add(xpubbch);
		arr.add(xrp);
		Card.getAddress(arr);
	}

}
