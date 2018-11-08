
public enum Player {

	NONE('?'), O('o'), X('x');
	
	private char symbol;
	
	Player(char symbol) {
		this.symbol = symbol;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
}
