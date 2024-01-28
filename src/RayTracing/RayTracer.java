package RayTracing;

import java.awt.Transparency;
import java.awt.color.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.imageio.ImageIO;




/**
 *  Main class for ray tracing exercise.
 */
public class RayTracer {

	public int imageWidth;
	public int imageHeight;
	
	public Camera camera;
	public GeneralSettings generalsettings;
	List<Material> materials=new ArrayList<>();
	List<Plane> planes=new ArrayList<>();
	List<Sphere> spheres= new ArrayList<>();
	List<Box> boxes=new ArrayList<>();
	List<Light> lights=new ArrayList<>();
	
	
	
	
	

	/**
	 * Runs the ray tracer. Takes scene file, output image file and image size as input.
	 */
	public static void main(String[] args) {

		try {

			RayTracer tracer = new RayTracer();

                        // Default values:
			tracer.imageWidth = 500;
			tracer.imageHeight = 500;

			if (args.length < 2)
				throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

			String sceneFileName = args[0];
			String outputFileName = args[1];

			if (args.length > 3)
			{
				tracer.imageWidth = Integer.parseInt(args[2]);
				tracer.imageHeight = Integer.parseInt(args[3]);
			}


			// Parse scene file:
			tracer.parseScene(sceneFileName);

			// Render scene:
			tracer.renderScene(outputFileName);

//		} catch (IOException e) {
//			System.out.println(e.getMessage());
		} catch (RayTracerException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	}

	
	
	
	
	
	
	
	/**
	 * Parses the scene file and creates the scene. Change this function so it generates the required objects.
	 */
	public void parseScene(String sceneFileName) throws IOException, RayTracerException
	{
		FileReader fr = new FileReader(sceneFileName);

		BufferedReader r = new BufferedReader(fr);
		String line = null;
		int lineNum = 0;
		System.out.println("Started parsing scene file " + sceneFileName);



		while ((line = r.readLine()) != null)
		{
			line = line.trim();
			++lineNum;

			if (line.isEmpty() || (line.charAt(0) == '#'))
			{  // This line in the scene file is a comment
				continue;
			}
			else
			{
				String code = line.substring(0, 3).toLowerCase();
				// Split according to white space characters:
				String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

				if (code.equals("cam"))
				{
					this.camera=Parse.parseCam(params);
					if(this.camera==null) {
						return;
					}

					System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
				}
				
				
				else if (code.equals("set"))
				{
					this.generalsettings=Parse.parseSet(params);
					if(this.generalsettings==null) {
						return;
					}

					System.out.println(String.format("Parsed general settings (line %d)", lineNum));
				}
				
				
				else if (code.equals("mtl"))
				{
					Material material=Parse.parseMaterial(params);
					if (material==null) {
						return;
					}
					this.materials.add(material);

					System.out.println(String.format("Parsed material (line %d)", lineNum));
				}
				
				
				else if (code.equals("sph"))
				{
					Sphere sphere=Parse.parseSphere(params);
					if(sphere==null) {
						return;
					}
					this.spheres.add(sphere);
					System.out.println(String.format("Parsed sphere (line %d)", lineNum));
				}
				
				
				else if (code.equals("pln"))
				{
					Plane plane=Parse.parsePlane(params);
					if(plane==null) {
						return;
					}
					this.planes.add(plane);
					System.out.println(String.format("Parsed plane (line %d)", lineNum));
				}
				
				
				else if (code.equals("lgt"))
				{	
					Light light=Parse.parseLight(params);
					if(light==null) {
						return;
					}
					this.lights.add(light);
					System.out.println(String.format("Parsed light (line %d)", lineNum));
				}
				
				
				else if (code.equals("box"))
				{
					Box box=Parse.parseBox(params);
					if(box==null) {
						return;
					}
					this.boxes.add(box);
					System.out.println(String.format("Parsed box (line %d)", lineNum));
				
					
				}
				else
				{
					System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
				}
			}
		}

                // It is recommended that you check here that the scene is valid,
                // for example camera settings and all necessary materials were defined.

		System.out.println("Finished parsing scene file " + sceneFileName);

	}

	
	
	
	
	
	
	
	
	/**
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName)
	{
		long startTime = System.currentTimeMillis();

		// Create a byte array to hold the pixel data:
		byte[] rgbData = new byte[imageWidth * imageHeight * 3];
		
	    // Combine all surfaces into a single list for intersection tests
		List<Surface> surfaces = new ArrayList<>();
		surfaces.addAll(this.spheres);
		surfaces.addAll(this.planes);
		surfaces.addAll(this.boxes);
		
		
		for(int y = 0; y < imageHeight; y++) {
			for(int x = 0; x < imageWidth; x++) {
				Vector pixelColor=new Vector(0,0,0);	
				Ray ray = constructRayThroughPixel(x+0.5, y+0.5);
				if(ray==null) {
					continue;
				}
				
	            // Find the closest intersected surface by the ray
				Surface surface = ray.findClosestIntersectedSurface(surfaces);
				if(surface == null) { 
	                // No intersection, set pixel color to background or white for fish-eye lens
					if(this.camera.fishEyeLens==false) {
						pixelColor=pixelColor.add(this.generalsettings.backGroundColor);
					}
					else {
						pixelColor=pixelColor.add(new Vector(1,1,1));
					}			
				}
				else {
	                // Calculate pixel color using recursive ray tracing
					Vector intersectPoint = surface.intersect(ray)[0];
					pixelColor=pixelColor.add(getColor(intersectPoint, surface, ray, surfaces, 
							this.generalsettings.maxRecursionLevel,  this.generalsettings.numberOfShadowRays));
						
				}
	            // Set RGB values in the byte array
				rgbData[(y * this.imageWidth + x) * 3]=(byte) Math.max(0, Math.min(255, pixelColor.x*255));  //red             
				rgbData[(y * this.imageWidth + x) * 3 + 1]=(byte) Math.max(0, Math.min(255, pixelColor.y*255));  //green
		        rgbData[(y * this.imageWidth + x) * 3 + 2]=(byte) Math.max(0, Math.min(255, pixelColor.z*255));	//blue
			}
		}
		
		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

		System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

                //Save the rendered image. This is already implemented, and should work without adding any code.
		saveImage(this.imageWidth, rgbData, outputFileName);

		System.out.println("Saved file " + outputFileName);

	}

	
	
	
	
	
	
	
	/**
	 * Constructs a ray passing through the specified pixel coordinates on the image plane.
	 *
	 * @param i The horizontal pixel coordinate.
	 * @param j The vertical pixel coordinate.
	 * @return The constructed ray.
	 */
	private Ray constructRayThroughPixel(double i, double j) { 
	    // Calculate pixel dimensions
		double pixelWidth=this.camera.screenWidth/(imageWidth);
		double pixelHeight=(pixelWidth*imageHeight)/imageWidth;         //pixelWidth/pixelHeight=imageWidth/imageHeight
		
	    // Calculate right and up values based on pixel position
		double rightVal=(((double)imageWidth)/2-(i))*pixelWidth;
		double upVal=(((double)imageHeight)/2-(j))*pixelHeight;
		
	    // Calculate right and up directions
		Vector rightDirection=this.camera.right.scalarMulti(rightVal);
		Vector upDirection=this.camera.up.scalarMulti(upVal);
	    
		// Calculate pixel position on the image plane
		Vector pixelPosition=this.camera.focalPoint.add(upDirection).add(rightDirection);
		
	    // Calculate ray direction
		Vector rayDirection=pixelPosition.sub(this.camera.position);
		Ray ray= new Ray(this.camera.position, rayDirection);
		
	    // If fish-eye lens is not enabled, return the ray
		if(this.camera.fishEyeLens==false) {
			return ray;
		}	  
		else { 
	        // Calculate parameters for fish-eye lens
			double Rf=pixelPosition.findDistance(this.camera.focalPoint);
			double theata;
			
	        // Calculate theata based on kFishValue
			if (this.camera.kFishValue>0&&this.camera.kFishValue<=1) {
				theata=(Math.atan(Rf/(this.camera.screenDistance/this.camera.kFishValue)))/this.camera.kFishValue;
			}
			else {
				if(this.camera.kFishValue==0) {
					theata=Rf/this.camera.screenDistance;
				}
				else {
					theata=(Math.asin(Rf/(this.camera.screenDistance/this.camera.kFishValue)))/this.camera.kFishValue;
				}
			}
			
			if(theata<=0) {
				theata=theata+2*Math.PI;
			}
			
			if(theata>=Math.PI/2) {
				return null;
			}
			
	        // Calculate distance and adjust pixel position for fish-eye lens
			double distance=Math.tan(theata)*this.camera.screenDistance;
			Vector moveToCorrectPosition=new Vector(pixelPosition.x-this.camera.focalPoint.x, 
					pixelPosition.y-this.camera.focalPoint.y, pixelPosition.z-this.camera.focalPoint.z).normalize();
			double scalar=Math.abs(distance-Rf);
			Vector fixPixelPosition=pixelPosition.add(moveToCorrectPosition.scalarMulti(scalar));
			Vector fixDirection=fixPixelPosition.sub(this.camera.position).normalize();
			Ray fixRay=new Ray(this.camera.position, fixDirection);
			return fixRay;
			
		}
	}
	
	
	
	
	
	
	
	/**
	 * Calculates the color of a given point on the surface.
	 *
     */
	private Vector getColor(Vector intersectPoint, Surface intersectSurface, Ray ray, List<Surface> surfaces,  
			int maxRec, int numOfShadowRays) {
		double epsilon=0.000000001F;
		Vector backGroundColor=this.generalsettings.backGroundColor;
		double transparency = this.materials.get(intersectSurface.getMaterialIndex()).transparency;
		Vector reflectColor =  this.materials.get(intersectSurface.getMaterialIndex()).reflectionColor;
		Vector transColor = new Vector(0,0,0);
		Vector resColor = new Vector(0,0,0);
		Vector diffAndSpecColor;
		
		//Calculate diffuse+specular color
		for(int i=0; i<this.lights.size(); i++) {
			diffAndSpecColor = this.lights.get(i).calcDiffPlusSpec(intersectPoint, intersectSurface , surfaces, numOfShadowRays,
													ray, this.materials);
			if(diffAndSpecColor != null) {
				resColor=resColor.add(diffAndSpecColor);
			}
		}
		
		//Multiply by (1-transparency)
		resColor=resColor.scalarMulti(1-transparency);
		
		if(maxRec>0) {
			// Transparency 			
			if(transparency>0) {
				Vector transOrigin=intersectPoint.add(ray.direction.scalarMulti(epsilon));
				Ray transRay=new Ray(transOrigin, ray.direction);
				Surface transSurface=transRay.findClosestIntersectedSurface(surfaces);
				if(transSurface != null) { 
					Vector transIntersectPoint = transSurface.intersect(transRay)[0];
					List<Surface> shorter = new ArrayList<Surface>(surfaces);  
					shorter.remove(intersectSurface);
					transColor=transColor.add(getColor(transIntersectPoint, transSurface, transRay, shorter,   
						maxRec-1, numOfShadowRays)); 
				}
				else { 
				transColor=transColor.add(backGroundColor);
				}
			}
			
			// Reflection 
			Vector N = intersectSurface.findNormal(intersectPoint).normalize();
			Vector forward=intersectPoint.add(N);
			Vector backward=intersectPoint.sub(N);
			if(ray.origin.findDistance(forward)>ray.origin.findDistance(backward)) {
					N=N.scalarMulti(-1);
				}
			Vector reflectOrigin=intersectPoint.add(ray.direction.scalarMulti(-1*epsilon));
			double DN=ray.direction.dotProd(N);
			Vector reflectDirection=ray.direction.sub(N.scalarMulti(2*DN));
			Ray reflectRay=new Ray(reflectOrigin,reflectDirection);
			Surface reflectSurface = reflectRay.findClosestIntersectedSurface(surfaces);
			if(reflectSurface != null) { 
				Vector refIntersectPoint = reflectSurface.intersect(reflectRay)[0];
				reflectColor=reflectColor.arrayProd(getColor(refIntersectPoint,  reflectSurface, reflectRay, surfaces,  
						maxRec-1, numOfShadowRays)); 
			}
		}
		
		transColor=transColor.scalarMulti(transparency);
		resColor=(resColor.add(transColor)).add(reflectColor); 		
		return resColor;
	}
	
	
	
	

	
	
	
	

	//////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT //////////////////////////////////////////

	/*
	 * Saves RGB data as an image in png format to the specified location.
	 */
	public static void saveImage(int width, byte[] rgbData, String fileName)
	{
		try {

			BufferedImage image = bytes2RGB(width, rgbData);
			ImageIO.write(image, "png", new File(fileName));

		} catch (IOException e) {
			System.out.println("ERROR SAVING FILE: " + e.getMessage());
		}

	}

	
	
	/*
	 * Producing a BufferedImage that can be saved as png from a byte array of RGB values.
	 */
	public static BufferedImage bytes2RGB(int width, byte[] buffer) {
	    int height = buffer.length / width / 3;
	    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
	    ColorModel cm = new ComponentColorModel(cs, false, false,
	            Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    SampleModel sm = cm.createCompatibleSampleModel(width, height);
	    DataBufferByte db = new DataBufferByte(buffer, width * height);
	    WritableRaster raster = Raster.createWritableRaster(sm, db, null);
	    BufferedImage result = new BufferedImage(cm, raster, false, null);

	    return result;
	}

	
	
	public static class RayTracerException extends Exception {
		public RayTracerException(String msg) {  super(msg); }
	}


}
