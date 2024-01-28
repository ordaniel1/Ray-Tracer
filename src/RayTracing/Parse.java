package RayTracing;

public class Parse {
	
    // Method to parse camera parameters from a string array
	public static Camera parseCam(String[] params) {
		
        // Check if there are enough parameters to create a camera
		if (params.length<11) {
			System.out.println("Invalid Camera");
			return null;
		}
		
        // Parse individual parameters
		double px=Double.parseDouble(params[0]);
		double py=Double.parseDouble(params[1]);
		double pz=Double.parseDouble(params[2]);
		Vector position=new Vector(px, py, pz);
		
		double lx=Double.parseDouble(params[3]);
		double ly=Double.parseDouble(params[4]);
		double lz=Double.parseDouble(params[5]);
		Vector lookAtPoint=new Vector(lx, ly, lz);
		
		double ux=Double.parseDouble(params[6]);
		double uy=Double.parseDouble(params[7]);
		double uz=Double.parseDouble(params[8]);
		Vector up=new Vector(ux, uy, uz);
		
		double screenDistance=Double.parseDouble(params[9]);
		double screenWidth=Double.parseDouble(params[10]);
		
        // Initialize fishEyeLens parameters with default values
		boolean fishEyeLens=false;
		double kFishValue =  0.5;
		
        // Check if additional parameters are provided for fishEyeLens
		if(params.length>11) {
			fishEyeLens=true;
			kFishValue=Double.parseDouble(params[12]);
            // Validate kFishValue
			if((kFishValue<-1)||(kFishValue>1)) {
				System.out.println("Invalid kFishValue");
				return null;
			}
		}
		
        // Create and return a Camera object
		Camera cam=new Camera(position, lookAtPoint, up, screenDistance, screenWidth,
				fishEyeLens, kFishValue);
		
		return cam;
		
		
	}
	
	
	
    // Method to parse general settings parameters from a string array
	public static GeneralSettings parseSet(String[] params) {
        // Check if there are enough parameters to create general settings
		if (params.length<5) {
			System.out.println("Invalid General Settings");
			return null;
		}
		
        // Parse individual parameters
		double r=Double.parseDouble(params[0]);
		double g=Double.parseDouble(params[1]);
		double b=Double.parseDouble(params[2]);
		Vector backGroungColor=new Vector(r,g,b);
		
		int numberOfShadowRays=Integer.parseInt(params[3]);
		int maxRecursionLevel=Integer.parseInt(params[4]);
		
        // Create and return a GeneralSettings object
		GeneralSettings set= new GeneralSettings(backGroungColor, numberOfShadowRays, maxRecursionLevel);
		return set;
	}
	
	
	
    // Method to parse material parameters from a string array
	public static Material parseMaterial(String[] params) {
        // Check if there are enough parameters to create a material
		if (params.length<11) {
			System.out.println("Invalid Material");
			return null;
		}
		
        // Parse individual parameters
		double dr=Double.parseDouble(params[0]);
		double dg=Double.parseDouble(params[1]);
		double db=Double.parseDouble(params[2]);
		Vector diffuseColor= new Vector(dr, dg, db);
		
		double sr=Double.parseDouble(params[3]);
		double sg=Double.parseDouble(params[4]);
		double sb=Double.parseDouble(params[5]);
		Vector specularColor= new Vector(sr, sg, sb);
		
		double rr=Double.parseDouble(params[6]);
		double rg=Double.parseDouble(params[7]);
		double rb=Double.parseDouble(params[8]);
		Vector reflectionColor= new Vector(rr, rg, rb);
		
		double phong=Double.parseDouble(params[9]);
		double transparency=Double.parseDouble(params[10]);
		
        // Create and return a Material object
		Material mat=new Material(diffuseColor, specularColor,
				reflectionColor, phong, transparency);
		return mat;
	}
	
	
	
	
    // Method to parse sphere parameters from a string array
	public static Sphere parseSphere(String[] params) {
        // Check if there are enough parameters to create a sphere
		if (params.length<5) {
			System.out.println("Invalid Sphere");
			return null;
		}
		
        // Parse individual parameters
		double px=Double.parseDouble(params[0]);
		double py=Double.parseDouble(params[1]);
		double pz=Double.parseDouble(params[2]);
		Vector center=new Vector(px, py, pz);
		
		double radius= Double.parseDouble(params[3]);
		int materialIndex= Integer.parseInt(params[4]);
		
        // Create and return a Sphere object
		Sphere sphere= new Sphere(center, radius, materialIndex);
		return sphere;
	}
	
	
	
	
    // Method to parse plane parameters from a string array
	public static Plane parsePlane(String[] params) {
        // Check if there are enough parameters to create a plane
		if (params.length<5) {
			System.out.println("Invalid Plane");
			return null;
		}
		
        // Parse individual parameters
		double nx=Double.parseDouble(params[0]);
		double ny=Double.parseDouble(params[1]);
		double nz=Double.parseDouble(params[2]);
		Vector normal=new Vector(nx, ny, nz);
		
		double offset= Double.parseDouble(params[3]);
		int materialIndex= Integer.parseInt(params[4]);
		
        // Create and return a Plane object
		Plane plane= new Plane(normal, offset, materialIndex);
		return plane;
	}
	
	
	
	
    // Method to parse box parameters from a string array
	public static Box parseBox(String[] params) {
        // Check if there are enough parameters to create a box
		if (params.length<5) {
			System.out.println("Invalid Box");
			return null;
		}
		
        // Parse individual parameters
		double px=Double.parseDouble(params[0]);
		double py=Double.parseDouble(params[1]);
		double pz=Double.parseDouble(params[2]);
		Vector center=new Vector(px, py, pz);
		
		double edgeLength= Double.parseDouble(params[3]);
		int materialIndex= Integer.parseInt(params[4]);
		
        // Create and return a Box object
		Box box= new Box(center, edgeLength, materialIndex);
		return box;
	}
	
	
	
    // Method to parse light parameters from a string array
	public static Light parseLight(String[] params) {
        // Check if there are enough parameters to create a light object
		if (params.length<9) {
			System.out.println("Invalid Light");
			return null;
		}
		
        // Parse individual parameters
		double px=Double.parseDouble(params[0]);
		double py=Double.parseDouble(params[1]);
		double pz=Double.parseDouble(params[2]);
		Vector position=new Vector(px, py, pz);
		
		double r=Double.parseDouble(params[3]);
		double g=Double.parseDouble(params[4]);
		double b=Double.parseDouble(params[5]);
		Vector color=new Vector(r,g,b);
		
		double specularIntensity=Double.parseDouble(params[6]);
		double shadowIntensity=Double.parseDouble(params[7]);
		double lightRadius=Double.parseDouble(params[8]);
		
        // Create and return a Light object
		Light light=new Light(position, color, specularIntensity, 
				shadowIntensity, lightRadius);
		
		return light;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
