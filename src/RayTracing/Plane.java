package RayTracing;

public class Plane extends Surface {
	Vector normal; // Normal vector of the plane
	double offset; // Offset of the plane from the origin along its normal (if P on the Plane: P*N=offset )
	int materialIndex; // Index of the material assigned to the plane
	
	
    // Constructor for the Plane class
	public Plane(Vector normal, double offset, int materialIndex) {
		this.normal=normal;
        // Adjust the offset to be negative
		this.offset=-1*offset;
		this.materialIndex=materialIndex-1;
	}
	
	
    // Implementation of the abstract method to get the material index of the plane
	public int getMaterialIndex() {
		return this.materialIndex;
	}
	
	
    // Implementation of the abstract method to find the intersection points between a ray and the plane
	public Vector[] intersect(Ray ray) {
		double t=this.findT(ray);
		if(t<=0) {
			return null;
		}
        // Calculate the intersection point and t value
		Vector[] res= {ray.origin.add(ray.direction.scalarMulti(t)), new Vector(t,0,0)};
		return res;
	}
	
	
    // Method to find the parameter t for intersection with a ray
	public double findT(Ray ray) {
		double directionDotNormal=ray.direction.dotProd(this.normal);
		if (directionDotNormal==0) { //no intersection
			return 0;
		}
		
        // Calculate the t value for intersection with the plane
		double t = -1*(ray.origin.dotProd(this.normal)+this.offset)/(directionDotNormal);
		return t;
	}
	
	
	
    // Method to project a point onto the plane
	public Vector projectAPoint(Vector u, Vector v) {
		Ray ray=new Ray(v, this.normal);
        // Find the intersection of the ray with the plane
		Vector[] intersect=this.intersect(ray);
		
        // If no intersection, try projecting from the opposite side
		if (intersect==null){
			Vector normal=this.normal.scalarMulti(-1);
			ray.direction=normal;
			intersect=this.intersect(ray);
		}
		
        // Calculate the normalized vector from the projected point to the original point
		return (intersect[0].sub(u)).normalize();
		
		
	}
	
	
    // Implementation of the abstract method to find the normal vector at a given point on the plane
	public Vector findNormal(Vector v) {
		return new Vector(this.normal.x, this.normal.y, this.normal.z).normalize();
	}
	
	
	
    // Check if a point is on the plane
	public boolean isPointOnPlane(Vector v) {
        // Check if the dot product of the normal and the point plus the offset is close to zero
		if(isSumZero(this.normal.dotProd(v), this.offset)) {
			return true;
		}
		return false;
		}
	
	
	
    // Static method to check if the sum of two numbers is close to zero
	public static boolean isSumZero(double x, double y) {   //returns |x+y|==0
		double epsilon=0.000001F;
		if (Math.abs(x+y)<=epsilon) {
			return true;
		}
		return false;
	}
	
	

}
