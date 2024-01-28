package RayTracing;

import java.util.List;

public class Ray {
	
	Vector origin; // Origin of the ray
	Vector direction; // Direction of the ray (normalized)

	
	// Constructor for the Ray class
	public Ray(Vector origin, Vector direction) {
		this.origin=origin;
		this.direction=direction.normalize();
	}
	
	
    // Method to find the closest intersected surface from a list of surfaces
	public Surface findClosestIntersectedSurface (List<Surface> list) {
		// Initialize minimum t value and closest surface
		double tMin=Double.MAX_VALUE;
		Surface closest = null;
		
        // Iterate through each surface in the list
		for (int i=0; i<list.size(); i++) {
			Surface surface=list.get(i);
			Vector[] intersection=surface.intersect(this); // Find the intersection point with the current surface
			if (intersection!=null){ // If there is an intersection
				double t=intersection[1].x;
				if(t<tMin) { //// Update the closest surface if the current intersection is closer
					tMin=t;
					closest=surface;
				}
			}
		}
        // Return the closest surface
		return closest;
			
	}
	
    // Method to find the point of closest intersection on any surface from a list of surfaces
	public Vector closestIntersectPoint(List<Surface> list) {
        // Find the closest intersected surface
		Surface closest=this.findClosestIntersectedSurface(list);
        // Return the intersection point on the closest surface
		return (closest.intersect(this)[0]);
	}
	
	
}
