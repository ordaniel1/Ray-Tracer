package RayTracing;

public class Vector {
    // Components of 3D vector
	double x;
	double y;
	double z;
	
	
    // Constructor for the Vector class
	public Vector(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	
	// Override the equals method for comparing vectors
	@Override
	public boolean equals(Object obj) {
		// Check if the compared object is the same reference
		if (this == obj)
			return true;
		
		// Check if the compared object is null or of a different class
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Vector other = (Vector) obj;
		
        // Check if the components are equal using doubleToLongBits for precision
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
	
	// Create a copy of the vector
	public Vector copy() {
		return new Vector(this.x, this.y, this.z);
	}
	
	// Multiply the vector by a scalar
	public Vector scalarMulti(double a) {
		return new Vector(this.x*a, this.y*a, this.z*a);
		
	}
	
	// Calculate the dot product with another vector
	public double dotProd(Vector v) {
		return (this.x*v.x + this.y*v.y+ this.z*v.z);
	}
	
	
    // Calculate the cross product with another vector
	public Vector crossProd(Vector v) {
		return new Vector(this.y*v.z-this.z*v.y, this.z*v.x-this.x*v.z, this.x*v.y-this.y*v.x);
	}
	
    // Add another vector to this vector
	public Vector add(Vector v) {
		return new Vector(this.x+v.x, this.y+v.y, this.z+v.z);
	}
	
    // Subtract another vector from this vector
	public Vector sub(Vector v) {
		return new Vector(this.x-v.x, this.y-v.y, this.z-v.z); 
	}
	
    // Normalize the vector to unit length
	public Vector normalize() {
		double a=1/Math.sqrt(this.dotProd(this));
		return this.scalarMulti(a);
	}
	
	
    // Calculate the distance between two points represented by vectors
	public double findDistance(Vector v){
		double arg1= Math.pow((this.x-v.x), 2);
		double arg2= Math.pow((this.y-v.y), 2);
		double arg3= Math.pow((this.z-v.z), 2);
		return  Math.sqrt(arg1+arg2+arg3);
	}
	
    // Perform element-wise multiplication (array product) with another vector [a,b,c]*[d,e,f]=[a*d, b*e, c*f]
	public Vector arrayProd(Vector v) {     
		return new Vector(this.x*v.x, this.y*v.y, this.z*v.z);
	}
}
