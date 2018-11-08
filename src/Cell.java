
public class Cell {
	
	private int x;
	
	private int y;
	
	private Player player;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.player = Player.NONE;
	}
	
	public boolean isAvailable() {
		return this.player == Player.NONE;
	}
	
	public boolean isMatchLocation(int x, int y) {
		return this.x == x && this.y == y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
