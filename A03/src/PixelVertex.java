/* PixelVertex.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Pixel Vertex Data Structure
   Name: Jorge Fernando Flores Pinto
   ID: V00880059
	
   This class constructs a PixelVertex with all its properties.
   Started from Bill's template.
*/ 

import java.util.Arrays;
import java.awt.Color;


public class PixelVertex{
	
	private int X;
	private int Y;
	private PixelVertex[] neighbours;
	private int degree;
	private Color color;
	private boolean visited;

	public PixelVertex(int X, int Y){
		this.X = X;
		this.Y = Y;
		this.degree = 0;
		this.neighbours = new PixelVertex[1];
		this.visited = false;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getColorRGB() {
		return this.color.getRGB();
	}
	
	public boolean isVisited(){
		return this.visited;
	}
	
	public void setVisited(boolean visited){
		this.visited = visited;
	}
	
	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX(){
		return this.X;
	}
	
	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY(){
		return this.Y;
	}
	
	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	*/
	public PixelVertex[] getNeighbours(){
		return this.neighbours;
	}
	
	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour){
		this.degree++;
		if (this.degree == 1){
			this.neighbours[0] = newNeighbour;
		} else {
			PixelVertex[] temp = new PixelVertex[this.degree];
			System.arraycopy(this.neighbours, 0, temp, 0, this.neighbours.length);
			temp[this.degree-1] = newNeighbour;
			this.neighbours = temp;
		}
		
	}
	
	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour){
		if (this.degree > 0) {
			int i = -1;
			PixelVertex[] newNeighbours = new PixelVertex[this.neighbours.length-1];
			for (int j = 0; j < this.neighbours.length; ++j){
				if (!isSameLocation(this.neighbours[j], neighbour))
					newNeighbours[++i] = this.neighbours[j];
			}
			this.degree--;
			System.arraycopy(newNeighbours, 0, this.neighbours, 0, this.degree);
		}
	}
	
	/* getDegree()
	   Return the degree of this vertex.
	*/
	public int getDegree(){
		return this.degree;
	}
	
	public boolean isSameLocation(PixelVertex currentVertex, PixelVertex otherVertex){
		int ovX = otherVertex.getX();
		int ovY = otherVertex.getY();
		if (ovX == currentVertex.getX() && ovY == currentVertex.getY())
			return true;
		return false;
		
	}
	
	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex){
		if (this.degree > 0)
			for (int i = 0; i < this.degree; ++i){
				if (isSameLocation(this.neighbours[i], otherVertex))
					return true; 			
			}
		return false;
	}
	
	
}