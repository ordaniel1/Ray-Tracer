package RayTracing;

public class Sphere extends Surface{
	
	Vector center; // Center of the sphere
	double radius; // Radius of the sphere
	int materialIndex; // Index of the material assigned to the sphere
	
	
    // Constructor for the Sphere class
	public Sphere(Vector center, double radius, int materialIndex) {
		this.center=center;
		this.radius=radius;
		this.materialIndex=materialIndex-1; // Material index is stored as materialIndex-1 (assumed 0-based indexing)

	}
	
    // Implementation of the abstract method to get the material index of the sphere
	public int getMaterialIndex() {
		return this.materialIndex;
	}
	
    // Implementation of the abstract method to find the intersection points between a ray and the sphere
	public Vector[] intersect(Ray ray) {
		double t=this.findT(ray);
        // If no intersection, return null
		if (t<=0) {
			return null;
		}
        // Calculate the intersection point and t value
		Vector[] res= {ray.origin.add(ray.direction.scalarMulti(t)), new Vector(t,0,0)};
		return res;
		
		
	}
	
	
    // Method to find the parameter t for intersection with a ray
	public double findT(Ray ray) {
		Vector L=this.center.sub(ray.origin);  // Vector from the ray's origin to the sphere's center
		double tca=L.dotProd(ray.direction); // Projection of L onto the ray direction
		
		// If the sphere is behind the ray, or partially behind, return 0
		if(tca<0) {         
			return 0;
		}
		
		 // Squared distance from the sphere's center to the ray
		double squared_d= (L.dotProd(L))-Math.pow(tca, 2);
		
		double squared_r=Math.pow(this.radius, 2);
		 
        // If the ray misses the sphere, return 0
		 if(squared_d>squared_r) {
			 return 0;
		 }
		 
	        // Calculate the distance from the point of intersection to the surface of the sphere
		 double thc=Math.sqrt(squared_r-squared_d);
		 
		 double t1=tca-thc;
		 double t2=tca+thc;
		 
	     // Return the minimum positive t value
		 return(Math.min(t1, t2));
		 
		
	}
	
	
    // Implementation of the abstract method to find the normal vector at a given point on the sphere
	public Vector findNormal(Vector v) {
        // Normalize the vector from the center of the sphere to the point on its surface
		Vector res=(v.sub(this.center)).normalize();
		return res;
	}
	
}
