package game;

public class Pair {
	int x;
	int y;
	public Pair(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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
	@Override
	public String toString() {
		return x + "," + y;
	}

	public boolean equals(Pair pair) {
		return pair.y == y && pair.x == x;
	}
}
