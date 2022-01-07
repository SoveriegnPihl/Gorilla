public class Vector {
	public double x;
	public double y;
	
	public Vector(double x, double y) {
		this.set(x,y);
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void add(Vector other) {
		this.add(other.x, other.y);
	}
	
	public void multiply(double m) {
		this.x *= m;
		this.y *= m;
	}

}