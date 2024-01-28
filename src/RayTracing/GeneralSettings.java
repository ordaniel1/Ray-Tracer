package RayTracing;

public class GeneralSettings {
	double imageHeight; // Height of the image
	double imageWidth;     // Width of the image
	Vector backGroundColor; // Background color of the scene
	int numberOfShadowRays; // Number of shadow rays to be used
	int maxRecursionLevel; //Maximum recursion level for ray tracing
	
    // Constructor for the GeneralSettings class
	public GeneralSettings(Vector backGroundColor,
			int numberOfShadowRays, int maxRecursionLevel) {
		this.backGroundColor=backGroundColor;
		this.numberOfShadowRays=numberOfShadowRays;
		this.maxRecursionLevel=maxRecursionLevel;
		
	}
	
	
	
	
	
	
	

}
