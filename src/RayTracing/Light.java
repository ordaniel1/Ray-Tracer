package RayTracing;

import java.util.List;
import  java.util.Random;

public class Light {
	Vector position; //Position of the point light
	Vector color; //The color of the light
	double specularIntensity;
	double shadowIntensity;
	double lightRadius; //Used to compute soft shadows
	
	
    // Constructor for the Light class
	public Light(Vector position, Vector color,
			double specularIntensity, double shadowIntensity, double lightRadius) {
		
		this.position=position;
		this.color=color;
		this.specularIntensity=specularIntensity;
		this.shadowIntensity=shadowIntensity;
		this.lightRadius=lightRadius;
	}
	
	
	
	
	
	
	public Vector calcDiffPlusSpec(Vector surfacePoint, Surface surface , List<Surface> list, int numOfShadowRays, Ray ray,
			List<Material> materialList) {
		
		Vector N=(surfacePoint.sub(this.position)).normalize(); //normalized vector from light source to the surface		
		double offset= (N.dotProd(this.position));         //N*P=d  , d is the offset
		Plane plane=new Plane(N, offset, 1);               //Perpendicular to the ray from light source to the surface
		Vector randPoint=new Vector(1,2,3);
		
		
		
		//find perpendicular vectors that spans the plane(the rectangle)
		Vector projectRand=plane.projectAPoint(this.position, randPoint);
		Vector perpendicular=(N.crossProd(projectRand)).normalize();
		
		
		double cellSize=this.lightRadius/numOfShadowRays;
		Vector move=this.position.add((perpendicular).scalarMulti(-0.5*this.lightRadius));
		Vector start=move.add((projectRand).scalarMulti(-0.5*this.lightRadius));  
		double counter=0; 
		Vector widthDirection=projectRand.scalarMulti(cellSize);
		Vector heightDirection=perpendicular.scalarMulti(cellSize);
		
		Random rand=new Random();
		double epsilon=0.00000001F;
		double noSurfaceIntersected=0;
		for(int i=0; i<numOfShadowRays; i++) {
			for (int j=0; j<numOfShadowRays; j++) {
				double xSample=rand.nextDouble();
				double ySample=rand.nextDouble();
				
				Vector shootPosition=start.add(widthDirection.scalarMulti(xSample));    
				shootPosition=shootPosition.add(heightDirection.scalarMulti(ySample));   
				Vector shootDirection=(surfacePoint.sub(shootPosition)).normalize();
				
				Ray shoot=new Ray(shootPosition, shootDirection);				
				Surface closestSurface=shoot.findClosestIntersectedSurface(list);
				if (closestSurface==null) {
					noSurfaceIntersected++;
					continue;
				}
				Vector[] intersection=closestSurface.intersect(shoot);
				Vector intersectionPoint=intersection[0];
				if(intersectionPoint.findDistance(surfacePoint)<epsilon) {
					 counter++;
				 }
				 
				 start=start.add(widthDirection);
			}
			Vector back=widthDirection.scalarMulti(numOfShadowRays*this.lightRadius);
			start=start.sub(back);
			start=start.add(heightDirection);
		}
		if(noSurfaceIntersected==numOfShadowRays*numOfShadowRays) {
			return null;
		}
		
		
		double hitRatio=counter/(numOfShadowRays*numOfShadowRays);
		double lightIntensity= (1-this.shadowIntensity) +this.shadowIntensity*(hitRatio);
		return diffSpec(surfacePoint, surface, ray, materialList).scalarMulti(lightIntensity);
	}
	
	
	
	
	
	
	
	private Vector diffSpec(Vector surfacePoint, Surface surface, Ray ray, List<Material> materialList) {
		Vector KD=materialList.get(surface.getMaterialIndex()).diffuseColor;
		Vector N=surface.findNormal(surfacePoint).normalize();
		
		
		Vector forward=surfacePoint.add(N);
		Vector backward=surfacePoint.sub(N);
			if(this.position.findDistance(forward)>this.position.findDistance(backward)) {
					N=N.scalarMulti(-1);
			}
		Vector L=this.position.sub(surfacePoint).normalize();  
		double NL=N.dotProd(L);
		if(NL<0) {
			NL=(-1)*NL;
		}
		Vector diff=KD.scalarMulti(NL);
		Vector diffColor=diff.arrayProd(this.color);
		
		
		//specular;
		Vector KS=materialList.get(surface.getMaterialIndex()).specularColor;
		NL=N.dotProd(L);
		Vector R=L.sub(N.scalarMulti(2*NL));
		double phong=materialList.get(surface.getMaterialIndex()).phong;
		Vector V=(ray.direction.scalarMulti(-1)).normalize();
		double RV=R.dotProd(V);
		if(RV<0) {
			RV=(-1)*RV;
		}
		Vector spec=KS.scalarMulti(this.specularIntensity*Math.pow(RV, phong));
		Vector specColor=spec.arrayProd(this.color);
		
		
		//result is sum of diff and spec
		Vector diffSpec=diffColor.add(specColor);		
		return diffSpec;
		
	}
		
	
	
	
	
	
	
	
	

}
