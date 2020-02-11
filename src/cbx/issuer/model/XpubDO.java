package cbx.issuer.model;

import javax.xml.bind.annotation.XmlRootElement;

import cbx.issuer.config.Coin;

@XmlRootElement
public class XpubDO {
	private Coin coinType;
	private String publicKey;
	private String chainCode;
	
	public XpubDO() {
		
	}
	
	public XpubDO(Coin coinType, String publicKey, String chainCode) {
		super();
		this.coinType = coinType;
		this.publicKey = publicKey;
		this.chainCode = chainCode;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getChainCode() {
		return chainCode;
	}
	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}

	public Coin getCoinType() {
		return coinType;
	}

	public void setCoinType(Coin coinType) {
		this.coinType = coinType;
	}
	
}
