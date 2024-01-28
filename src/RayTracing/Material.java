package RayTracing;

public class Material {
	Vector diffuseColor; //This is the "regular" color of a surface (RGB)
	Vector specularColor; //Specularity is the reflection of a light source (RGB, defines the intensity and color of that reflection)
	Vector reflectionColor; //Reflections from the surface are multiplied by this value (RGB)
	double phong; //Phong specularity coefficient
	double transparency; // Transparency level of the material (0 when the material is opaque and 1 when the material is completely transparent)
	
	
    // Constructor for the Material class
	public Material(Vector diffuseColor, Vector SpecularColor,
			Vector ReflectionColor, double phong, double transparency) {
		
		this.diffuseColor= diffuseColor;
		this.specularColor= SpecularColor;
		this.phong=phong;
		this.reflectionColor= ReflectionColor;
		this.transparency=transparency;
		
		
	}
	
	
	

		
	
}
