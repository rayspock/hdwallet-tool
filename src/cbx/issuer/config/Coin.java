package cbx.issuer.config;

public enum Coin {
	BTC(0),
	BCH(145),
	ETH(60),
	LTC(2),
	XRP(144);
	
	private final int value;
	
	Coin(final int 	newValue) {
		value = newValue;
	}
	
	public int getValue() {
		return this.value;
	}
}
