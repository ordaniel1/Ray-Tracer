# Ray-Tracer
## Introduction

In this project, I've implemented a basic ray tracer that simulates the interaction of light with surfaces in a scene. The ray tracer shoots rays from the observer's eye (camera) through a screen and calculates the color of surfaces based on their materials and lighting conditions.

## Features

### Surfaces

- Spheres: Defined by center position and radius.
- Infinite planes: Defined by normal vector 𝑁 and an offset 𝑐 along the normal.
- Boxes (cubes): Defined by center position (x, y, z) and edge length.

### Materials

Materials define the color and reflectivity of surfaces. Each material has attributes like diffuse color, specular color, Phong specularity coefficient, reflection color, and transparency.

### Lights

Point lights are used to illuminate the scene. Each light has attributes such as position, color, specular intensity, shadow intensity, and light radius.

### Camera and General Settings

The camera is defined by position, look-at point, up vector, screen distance, and screen width. General settings include background color, number of shadow rays, maximum recursion level, and an option for a fish-eye lens.

## Implementation Details

The ray tracer implements soft shadows, reflection rays, transparency, and a fish-eye lens effect. It supports point lights and various surface materials, providing flexibility in scene creation.

## Usage

Run the code in the command line, providing input and output file paths, as well as image dimensions.

```bash
java -jar RayTrace.jar scenes\Spheres.txt scenes\Spheres.png 500 500
