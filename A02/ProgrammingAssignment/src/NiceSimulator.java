/*
Name: Jorge Fernando Flores Pinto
ID: V00880059
NiceSimulator.java
CSC225 Summer 2017

*/
import java.io.*;
import java.util.*;

public class NiceSimulator {

	public static final int SIMULATE_IDLE = -2;
	public static final int SIMULATE_NONE_FINISHED = -1;
	
	public static Heap tasks;
	public static Task map[];
	public static int maxTasks;
	
	
	/* Constructor(maxTasks)
	   Instantiate the data structure with the provided maximum 
	   number of tasks. No more than maxTasks different tasks will
	   be simultaneously added to the simulator, and additionally
	   you may assume that all task IDs will be in the range
	     0, 1, ..., maxTasks - 1
	*/
	public NiceSimulator(int maxTasks){
		this.maxTasks = maxTasks - 1;
		this.tasks = new Heap(maxTasks);
		this.map = new Task[maxTasks];
	}			
	/* taskValid(taskID)
	   Given a task ID, return true if the ID is currently
	   in use by a valid task (i.e. a task with at least 1
	   unit of time remaining) and false otherwise.
	   
	   Note that you should include logic to check whether 
	   the ID is outside the valid range 0, 1, ..., maxTasks - 1
	   of task indices.
	
	*/
	public boolean taskValid(int taskID){
		if(map[taskID] != null) {
			return true;
		}
		return false;
		
	}
	
	/* getPriority(taskID)
	   Return the current priority value for the provided
	   task ID. You may assume that the task ID provided
	   is valid.
	
	*/
	public int getPriority(int taskID){
		Task getTask = map[taskID];
		return getTask.getPriority();
	}
	
	/* getRemaining(taskID)
	   Given a task ID, return the number of timesteps
	   remaining before the task completes. You may assume
	   that the task ID provided is valid.
	
	*/
	public int getRemaining(int taskID){
		Task getTask = map[taskID];
		return getTask.getTime();
	}
	
	
	/* add(taskID, time_required)
	   Add a task with the provided task ID and time requirement
	   to the system. You may assume that the provided task ID is in
	   the correct range and is not a currently-active task.
	   The new task will be assigned nice level 0.
	*/
	public void add(int taskID, int time_required){
		Task newTask = new Task(taskID, time_required);
		this.tasks.add(newTask);
		map[taskID] = newTask;
	}
	
	
	/* kill(taskID)
	   Delete the task with the provided task ID from the system.
	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.
	*/
	public void kill(int taskID){
		this.tasks.remove(taskID);
		map[taskID] = null;

	}
	
	
	/* renice(taskID, new_priority)
	   Change the priority of the the provided task ID to the new priority
       value provided. The change must take effect at the next simulate() step.
	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.
	
	*/
	public void renice(int taskID, int new_priority){
		Task reniceTask = this.tasks.tasks[tasks.mapId[taskID]];
		int old_priority = reniceTask.getPriority();
		reniceTask.setPriority(new_priority);
		if(this.tasks.mapId[taskID] > 1) {
			if (new_priority <= old_priority) {
				this.tasks.bubbleUp(this.tasks.mapId[taskID]);
			}
			else {
				this.tasks.bubbleDown(this.tasks.mapId[taskID]);
			}
		}
		else if (this.tasks.size() > 1) {
			this.tasks.bubbleDown(this.tasks.mapId[taskID]);
		}
		this.tasks.heapCheck();
	}

	
	/* simulate()
	   Run one step of the simulation:
		 - If no tasks are left in the system, the CPU is idle, so return
		   the value SIMULATE_IDLE.
		 - Identify the next task to run based on the criteria given in the
		   specification (tasks with the lowest priority value are ranked first,
		   and if multiple tasks have the lowest priority value, choose the 
		   task with the lowest task ID).
		 - Subtract one from the chosen task's time requirement (since it is
		   being run for one step). If the task now requires 0 units of time,
		   it has finished, so remove it from the system and return its task ID.
		 - If the task did not finish, return SIMULATE_NONE_FINISHED.
	*/
	
	public int simulate() {
		while (this.tasks.size() > 0) {
			Task task = this.tasks.tasks[1];
			if (task != null && task.time > 0) {
				task.time--;
				map[task.getId()].setPriority(map[task.getId()].getPriority());
				if (task.time == 0) {
					int id = task.getId();
					this.tasks.pop();
					map[id] = null;
					return id;
				}
				else
					return SIMULATE_NONE_FINISHED;
			}
		}		
		return SIMULATE_IDLE;
	
	}

}