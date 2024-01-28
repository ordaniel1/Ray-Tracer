package RayTracing;

public class Camera {
	Vector position; // Position of the camera in 3D space
	double screenDistance; // Distance from the camera to the screen
	double screenWidth; // Width of the screen
	Vector direction; // Direction the camera is facing
	Vector right; // Right vector of the camera
	Vector up; // Up vector of the camera
	Vector focalPoint; // Focal point of the camera
	boolean fishEyeLens;  // Flag indicating whether the camera has a fish-eye lens
	double kFishValue; // Coefficient for fish-eye lens distortion
	
	
	
    // Constructor for the Camera class
	public Camera(Vector position, Vector lookAtPoint, Vector up, 
			double screenDistance, double screenWidth, boolean fishEyeLens, double kFishValue) {
		
		this.position=position; 
		this.direction=lookAtPoint.sub(this.position).normalize();  // Calculate camera direction vector normalized 
		this.right=this.direction.crossProd(up).normalize();   // Calculate the right vector of the camera
		this.up=this.right.crossProd(this.direction).normalize(); // Calculate the up vector of the camera
		this.screenDistance=screenDistance;
		
		// Calculate the focal point based on the screen distance
		Vector focalPoint=this.position.add((this.direction.scalarMulti(this.screenDistance))); 
		this.focalPoint=focalPoint;
		
		this.screenWidth=screenWidth;
		this.fishEyeLens=fishEyeLens;
		this.kFishValue=kFishValue;
		
	}
	
	
	

	
	
	
	
	

}









