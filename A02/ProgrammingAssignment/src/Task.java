/* 
Name: Jorge Fernando Flores Pinto
ID: V00880059
CSC225, Summer 2017
Task.java

*/
public class Task {
	public int priority;
	public int id;
	public int time;
	
	public Task() {}
	
	public Task(int id, int time) {
		this.priority = 0;
		this.id = id;
		this.time = time;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	public int getTime() {
		return this.time;
	}
	
}