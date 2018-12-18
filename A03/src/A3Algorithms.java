/* A3Algorithms.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Image Algorithms
   Name: Jorge Fernando Flores Pinto
   ID: V00880059
	
   This class applies the DFS and BFS algorithms to color the outline
   regions or fill the regions with a particular color. It also
   counts the number connected components (regions). 
   Started from Bill's template.
*/ 

import java.awt.Color;
import java.util.Queue;
import java.util.LinkedList;

public class A3Algorithms{
	
	public static Queue<PixelVertex> queue = new LinkedList<PixelVertex>();


	/* FloodFillDFS(v, viewer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){
		for (int i = 0; i < v.getDegree(); i++) {
			PixelVertex neighbour = v.getNeighbours()[i];
			if(!neighbour.isVisited()){
				neighbour.setVisited(true);
				FloodFillDFS(neighbour, viewer, fillColour);
			}
		}
		viewer.setPixel(v.getX(), v.getY(), fillColour);		
	}
	
	/* FloodFillBFS(v, viewer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){
		queue.add(v);
		v.setVisited(true);
		viewer.setPixel(v.getX(), v.getY(), fillColour);
		
		while (!queue.isEmpty()) {
			PixelVertex element = queue.remove();
			for (int i = 0; i < element.getDegree(); i++) {
				PixelVertex neighbour = element.getNeighbours()[i];
				
				if(!neighbour.isVisited()){
					queue.add(neighbour);
					neighbour.setVisited(true);
					viewer.setPixel(neighbour.getX(), neighbour.getY(), fillColour);
				}
			}
		}

	}
	
	/* OutlineRegionDFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){
		for (int i = 0; i < v.getDegree(); i++) {
			PixelVertex neighbour = v.getNeighbours()[i];
			if(!neighbour.isVisited()){
				neighbour.setVisited(true);
				OutlineRegionDFS(neighbour, viewer, outlineColour);
			}
		}
		if (v.getDegree() < 4)
			viewer.setPixel(v.getX(), v.getY(), outlineColour);
	}
	
	/* OutlineRegionBFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){
		queue.add(v);
		v.setVisited(true);
		
		if (v.getDegree() < 4)
			viewer.setPixel(v.getX(), v.getY(), outlineColour);
			
		while (!queue.isEmpty()) {
			PixelVertex element = queue.remove();
			for (int i = 0; i < element.getDegree(); i++) {
				PixelVertex neighbour = element.getNeighbours()[i];
				if(!neighbour.isVisited()){
					queue.add(neighbour);
					neighbour.setVisited(true);
					
					if (neighbour.getDegree() < 4)
						viewer.setPixel(neighbour.getX(), neighbour.getY(), outlineColour);
				}
			}
		}

	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		//I have left this commented in case "components" means number of colors

		/*LinkedList<Color> regions = new LinkedList<Color>();
		regions.add(G.getPixelVertex(0,0).getColor());
		
		// in graph.
		for (int x = 0; x < G.getWidth(); x++) {
			for (int y = 0; y < G.getHeight(); y++){
				PixelVertex vx = G.getPixelVertex(x, y);
				//We can assume the number of colors to be a constant
				if (!regions.contains(vx.getColor()))
					regions.add(vx.getColor());
			}
		}	
		return regions.size();*/

		int components = 0;
		for (int x = 0; x < G.getWidth(); x++) {
			for (int y = 0; y < G.getHeight(); y++){
				PixelVertex v = G.getPixelVertex(x,y);
				if (!v.isVisited()) {
					doCount(v);
					components++;
				}
			}
		}
		return components;
		
	}
	//Helper function that counts connected components using DFS.
	public static void doCount(PixelVertex v) {
		for (int i = 0; i < v.getDegree(); i++) {
			PixelVertex neighbour = v.getNeighbours()[i];
			if(!neighbour.isVisited()){
				neighbour.setVisited(true);
				doCount(neighbour);
			}
		}
	}
	
}