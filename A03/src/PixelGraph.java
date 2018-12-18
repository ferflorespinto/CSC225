/* PixelGraph.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Pixel Graph Data Structure
   Name: Jorge Fernando Flores Pinto
   ID: V00880059
	
   This class constructs a PixelGraph of PixelVertices.
   Started from Bill's template.
*/ 

import java.awt.Color;

public class PixelGraph{
	
	private int width;
	private int height;
	public PixelVertex[][] vertex;
	//public int components = 0;

	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour 
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image. 
	*/
	public PixelGraph(Color[][] imagePixels){
		this.width = imagePixels.length;
		this.height = imagePixels[0].length;
		this.vertex = new PixelVertex[this.width][this.height];
		for (int x = 0; x < this.width; x++)
			for (int y = 0; y < this.height; y++){
				PixelVertex vx = new PixelVertex(x, y);
				vx.setColor(imagePixels[x][y]);
								
				if (x-1 >= 0)
					setNeighbours(vx, x-1, y);
				if (y-1 >= 0)
					setNeighbours(vx, x, y-1);
				
				this.vertex[x][y] = vx;
			}
		
	}
	
	public void setNeighbours(PixelVertex v, int posX, int posY) {
		PixelVertex n = vertex[posX][posY];
		if (!n.isNeighbour(v) && n.getColorRGB() == v.getColorRGB()){
			v.addNeighbour(n);
			n.addNeighbour(v);
		}
		
	}
	
	
	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object 
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the 
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){		
		return vertex[x][y];
	}
	
	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getWidth(){
		return this.width;
	}
	
	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getHeight(){
		return this.height;
	}
	
}