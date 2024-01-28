package RayTracing;

public abstract class Surface {
	
	 // Abstract method to get the material index of the surface
	abstract public int getMaterialIndex();    
	
    // Abstract method to find the intersection points between a ray and the surface
	abstract public Vector[] intersect(Ray ray); 
	
    // Abstract method to find the normal vector at a given point on the surface
	abstract public Vector findNormal(Vector v);
	
}
