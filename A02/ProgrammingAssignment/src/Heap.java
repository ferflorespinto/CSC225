/*
Name: Jorge Fernando Flores Pinto
ID: V00880059
Heap.java
CSC225 Summer 2017

*/

import java.io.*;
import java.util.*;

public class Heap {

	Task tasks[];
	int mapId[];
	int count = 0;
	boolean killFlag = false;

	public Heap(int size) {
		this.tasks = new Task[size + 1];
		mapId = new int[size];

	}
	public void add(Task task) {
		count++;
		if (count == 1) {
			this.tasks[count] = task;
			mapId[task.getId()] = count;
		}

		else {
			this.tasks[count] = task;
			mapId[task.getId()] = count;
			bubbleUp(count);
		}
		heapCheck();
	}
	public void remove(int id) {
		killFlag = true;
		bubbleDown(mapId[id]);
		this.tasks[mapId[id]] = null;
		mapId[id] = 0;
		count--;
		killFlag = false;
		heapCheck();
		bubbleUp(count);

	}
	public Task pop() {
		Task head = new Task(this.tasks[1].id, this.tasks[1].time);
		head.setPriority(this.tasks[1].getPriority());
		bubbleDown(1);
		this.tasks[mapId[head.getId()]] = null;
		mapId[head.getId()] = 0;
		count--;
		heapCheck();
		bubbleUp(count);
		
		return head;
	}
	public void bubbleUp(int idx) {
		if (idx > 1) {
			int parent = idx / 2;
			int curr = idx;
			Task t1 = this.tasks[parent];
			Task t2 = this.tasks[curr];
			if (t1 == null && t2 != null) {
				return;
			}
			else if (t2 == null && t1 != null) {
				return;
			}
			else if (t1 == null && t2 == null) {
				return;
			}
			else if (t2.getPriority() < t1.getPriority()) {
				Task temp = t1;
				int mapTemp = mapId[t1.getId()];	
				mapId[t1.getId()] = mapId[t2.getId()];
				mapId[t2.getId()] = mapTemp;
				t1 = t2;
				t2 = temp;
				this.tasks[parent] = t1;
				this.tasks[curr] = t2;
				t1 = this.tasks[parent];
				t2 = this.tasks[curr];
				bubbleUp(parent);
			}	
			else if(t2.getPriority() == t1.getPriority()) {
				if(t2.getId() < t1.getId()) {
					Task temp = t1;
					int mapTemp = mapId[t1.getId()];
					mapId[t1.getId()] = mapId[t2.getId()];
					mapId[t2.getId()] = mapTemp;
					t1 = t2;
					t2 = temp;
					this.tasks[parent] = t1;
					this.tasks[curr] = t2;
					t1 = this.tasks[parent];
					t2 = this.tasks[curr];
					bubbleUp(parent);
				}
			}
		}
	}
	public void bubbleDown(int idx) {
		int parent = idx;
		int leftChild = idx * 2;
		int rightChild = idx * 2 + 1;
		if (parent >= mapId.length) {
			return;
		}
		Task t1 = this.tasks[parent];
		Task t2 = null;
		Task t3 = null;
		if (leftChild <= mapId.length) {
			t2 = this.tasks[leftChild];
		}
		else {
			leftChild = count;
		}
		if(rightChild <= mapId.length) {
			t3 = this.tasks[rightChild];
		}
		else {
			if (leftChild <= mapId.length - 1) {
				rightChild = leftChild + 1;
			}
		}
		int nextIdx = parent + 1;
		Task next = null;
		if (nextIdx < mapId.length) {
			next = this.tasks[nextIdx];
		}
		else {
			next = this.tasks[count];
		}

		if (t2 == null && t3 == null) {
				Task temp = this.tasks[parent];
				int mapTemp = mapId[t1.getId()];
				if ((killFlag == true || this.tasks[parent].time == 0) && next == this.tasks[nextIdx] && next != null) {
					if (next == this.tasks[nextIdx]) {
						mapId[this.tasks[parent].getId()] = mapId[this.tasks[parent + 1].getId()];
						mapId[this.tasks[parent + 1].getId()] = mapTemp;
						this.tasks[parent] = this.tasks[parent + 1];
						this.tasks[parent + 1] = temp;
						bubbleDown(parent + 1);
					}
				}
				else if ((killFlag == true || this.tasks[parent].time == 0) && next == this.tasks[count] && next != null) {
					mapId[this.tasks[parent].getId()] = mapId[this.tasks[count].getId()];
					mapId[this.tasks[count].getId()] = mapTemp;
					this.tasks[parent] = this.tasks[count];
					this.tasks[count] = temp;
					bubbleDown(count + 1);
				}
			return;
		}
		else if((t2 == null && t3 != null)) {
			if (t1.getPriority() > t3.getPriority() || t1.time == 0 || killFlag == true) {
				Task temp  = t3;
				int mapTemp = mapId[t3.getId()];

				mapId[t3.getId()] = mapId[t1.getId()];
				mapId[t1.getId()] = mapTemp;
				this.tasks[rightChild] = this.tasks[parent];
				this.tasks[parent] = temp;
				bubbleDown(rightChild);
			}
			else if (t1.getPriority() == t3.getPriority()) {
				if (t1.getId() > t3.getId()) {
					Task temp  = t3;
					int mapTemp = mapId[t3.getId()];

					mapId[t3.getId()] = mapId[t1.getId()];
					mapId[t1.getId()] = mapTemp;
					this.tasks[rightChild] = this.tasks[parent];
					this.tasks[parent] = temp;
					bubbleDown(rightChild);
				}

			}
		}
		else if ((t3 == null && t2 != null)) {
			if (t1.getPriority() > t2.getPriority() || t1.time == 0 || killFlag == true) {
				Task temp  = t2;
				int mapTemp = mapId[t2.getId()];
				mapId[t2.getId()] = mapId[t1.getId()];
				mapId[t1.getId()] = mapTemp;
				this.tasks[leftChild] = this.tasks[parent];
				this.tasks[parent] = temp;
				bubbleDown(leftChild);
			}
			else if (t1.getPriority() == t2.getPriority()) {
				if (t1.getId() > t2.getId()) {
					Task temp  = t2;
					int mapTemp = mapId[t2.getId()];
					mapId[t2.getId()] = mapId[t1.getId()];
					mapId[t1.getId()] = mapTemp;
					this.tasks[leftChild] = this.tasks[parent];
					this.tasks[parent] = temp;
					bubbleDown(leftChild);
				}

			}
		}
		else if (t3.getPriority() < t2.getPriority()) {
			if (t1.getPriority() > t3.getPriority() || t1.time == 0 || killFlag == true) {
				Task temp  = this.tasks[rightChild];
				int mapTemp = mapId[t3.getId()];
				
				mapId[t3.getId()] = mapId[t1.getId()];
				mapId[t1.getId()] = mapTemp;
				this.tasks[rightChild] = this.tasks[parent];
				this.tasks[parent] = temp;
				
				bubbleDown(rightChild);
			}
			else if (t1.getPriority() == t3.getPriority()) {
				if (t1.getId() > t3.getId()) {
					Task temp  = t3;
					int mapTemp = mapId[t3.getId()];
					mapId[t3.getId()] = mapId[t1.getId()];
					mapId[t1.getId()] = mapTemp;
					this.tasks[rightChild] = this.tasks[parent];
					this.tasks[parent] = temp;
					bubbleDown(rightChild);
				}

			}

		}
		else if (t2.getPriority() < t3.getPriority()) {
			if (t1.getPriority() > t2.getPriority() || t1.time == 0 || killFlag == true) {
				Task temp  = t2;
				int mapTemp = mapId[t2.getId()];
				
				mapId[t2.getId()] = mapId[t1.getId()];
				mapId[t1.getId()] = mapTemp;
				this.tasks[leftChild] = this.tasks[parent];
				this.tasks[parent] = temp;
				
				bubbleDown(leftChild);
			}
			else if (t1.getPriority() == t2.getPriority()) {
				if (t1.getId() > t2.getId()) {
					Task temp  = t2;
					int mapTemp = mapId[t2.getId()];
					mapId[t2.getId()] = mapId[t1.getId()];
					mapId[t1.getId()] = mapTemp;
					this.tasks[leftChild] = this.tasks[parent];
					this.tasks[parent] = temp;
					
					bubbleDown(leftChild);
				}

			}
		}
		else if (t2.getPriority() == t3.getPriority()) {
			if (t2.getId() < t3.getId()) {
				if (t1.getPriority() > t2.getPriority() || t1.time == 0 || killFlag == true) {
					Task temp  = t2;
					int mapTemp = mapId[t2.getId()];
					
					mapId[t2.getId()] = mapId[t1.getId()];
					mapId[t1.getId()] = mapTemp;
					this.tasks[leftChild] = this.tasks[parent];
					this.tasks[parent] = temp;
					
					bubbleDown(leftChild);
				}
				else if (t1.getPriority() == t2.getPriority()) {
					if (t1.getId() > t2.getId()) {
						Task temp  = t2;
						int mapTemp = mapId[t2.getId()];
						mapId[t2.getId()] = mapId[t1.getId()];
						mapId[t1.getId()] = mapTemp;
						this.tasks[leftChild] = this.tasks[parent];
						this.tasks[parent] = temp;
						
						bubbleDown(leftChild);
					}

				}
			}
			else {
				if (t1.getPriority() > t3.getPriority() || t1.time == 0 || killFlag == true) {
					Task temp  = this.tasks[rightChild];
					int mapTemp = mapId[t3.getId()];
					
					mapId[t3.getId()] = mapId[t1.getId()];
					mapId[t1.getId()] = mapTemp;
					this.tasks[rightChild] = this.tasks[parent];
					this.tasks[parent] = temp;
					
					bubbleDown(rightChild);
				}
				else if (t1.getPriority() == t3.getPriority()) {
					if (t1.getId() > t3.getId()) {
						Task temp  = t3;
						int mapTemp = mapId[t3.getId()];
						
						mapId[t3.getId()] = mapId[t1.getId()];
						mapId[t1.getId()] = mapTemp;
						this.tasks[rightChild] = this.tasks[parent];
						this.tasks[parent] = temp;
						
						bubbleDown(rightChild);
					}

				}
			}
		}
	}
	public void heapCheck() {
		if (this.tasks[1] != null) {
			if (this.tasks[2] != null && this.tasks[3] != null) {
				if(this.tasks[2].getPriority() <= this.tasks[3].getPriority()) {
					if(this.tasks[1].getPriority() == this.tasks[2].getPriority()) {
						if(this.tasks[1].getId() > this.tasks[2].getId()) {
							bubbleUp(mapId[this.tasks[2].id]);
						}
					}
					else if(this.tasks[1].getPriority() > this.tasks[2].getPriority()) {
						bubbleUp(mapId[this.tasks[2].id]);
					}
				}
				else {
					if(this.tasks[1].getPriority() == this.tasks[3].getPriority()) {
						if(this.tasks[1].getId() > this.tasks[3].getId()) {
							bubbleUp(mapId[this.tasks[3].id]);
						}
					}
					else if(this.tasks[1].getPriority() > this.tasks[3].getPriority()) {
						bubbleUp(mapId[this.tasks[3].id]);
					}

				}
			}
			else if(this.tasks[2] != null && this.tasks[3] == null) {
				if(this.tasks[1].getPriority() == this.tasks[2].getPriority()) {
					if(this.tasks[1].getId() > this.tasks[2].getId()) {
						bubbleUp(mapId[this.tasks[2].id]);
					}
				}
				else if(this.tasks[1].getPriority() > this.tasks[2].getPriority()) {
					bubbleUp(mapId[this.tasks[2].id]);
				}
			}
			else if (this.tasks[2] == null && this.tasks[3] != null) {
				if(this.tasks[1].getPriority() == this.tasks[3].getPriority()) {
					if(this.tasks[1].getId() > this.tasks[3].getId()) {
						bubbleUp(mapId[this.tasks[3].id]);
					}
				}
				else if(this.tasks[1].getPriority() > this.tasks[3].getPriority()) {
					bubbleUp(mapId[this.tasks[3].id]);
				}
			}
		}
	}
	public int size() {
		return count;
	}

}