public class Rectangle {
	public double x;
	public double y;
	public double height;
	public double width;
	
	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean overlaps(Rectangle other) {
		//four cases to consider:
		//this rect. left of other rect.
		//this rect. right of other rect.
		//this rect. above other rect.
		//this rect. below other rect.
		boolean noOverlap = 
				this.x + this.width < other.x ||
				other.x + other.width < this.x ||
				this.y + this.height < other.y ||
				other.y + other.height < this.y;
		return !noOverlap;
		
	}

}