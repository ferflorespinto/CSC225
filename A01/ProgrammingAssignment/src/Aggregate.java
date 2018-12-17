/*
Name: Jorge Fernando Flores Pinto
ID: V00880059
Aggregate.java
CSC225, Summer 2017

*/

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.text.DecimalFormat;

public class Aggregate {
	private static LinkedList<String[]> readFile(String filename) throws FileNotFoundException {
		File data = new File(filename);
		Scanner counter = new Scanner(data);
		int count = 0;

		String lines = "";
		LinkedList<String[]> data_list = new LinkedList<String[]>();
		while(counter.hasNextLine()) {
			lines = counter.nextLine();
			String[] arr_data = lines.split(",");
			data_list.add(arr_data);
		}

		return data_list;

	}

	private static String output(LinkedList<String[]> data, String filename) throws IOException{
		String[] name = filename.split(".csv");
		//PrintStream output = new PrintStream(new File(name[0] + "_aggregated.csv"));
		int index = 0;
		String[] first = data.get(0);
		int len = first.length;
		String line = "";
		StringBuilder sb = new StringBuilder();
		//This might by O(n^2)!
		for(int i = 0; i < len; i++) {
			if (index < data.size()) {
				if (i == first.length - 1) {
					sb.append(first[i] + "\n");
					index++;
					i = -1;
				}
				else {
					first = data.get(index);
					sb.append(first[i] + ",");
				}
			}
			else {
				break;
			}
		}
		String newStr = sb.toString();
		//output.print(newStr);
		return newStr;
	}
	private static LinkedList<String[]> count(LinkedList<String[]> data) {
		LinkedList<String[]> result = new LinkedList<String[]>();
		int looper = 1;
		int count = 0;
		result.add(data.get(0));
		String[] resultArr = data.get(1);
		String resultStr = concat(resultArr);
		String[] originalTitle = data.get(0);
		int size = originalTitle.length;

		while(looper < data.size()) {
			String[] dataArr = data.get(looper);
			String dataStr = concat(dataArr);

			if(resultStr.equals(dataStr)) {
				count++;
				if (looper == data.size() - 1) {
					String[] addString = new String[data.get(looper).length];
					addString = data.get(looper);
					addString[size  - 1] = "" + count;
					result.add(addString);
				}
				looper++;
			}
			else {
				String[] addString = new String[result.get(0).length];
				addString = resultArr;
				addString[size - 1] = "" + count;
				result.add(addString);
				count = 0;
				resultArr = data.get(looper);
				resultStr = concat(resultArr);
			}
		}
		String[] title = result.pop();
		title[size - 1] = "count(" + originalTitle[size - 1] + ")";
		result.addFirst(title);
		return result;

	}
	private static LinkedList<String[]> sum(LinkedList<String[]> data) {
		LinkedList<String[]> result = new LinkedList<String[]>();
		int looper = 1;
		int dataVal = 0;
		int sum = 0;
		result.add(data.get(0));
		String[] resultArr = data.get(1);
		String resultStr = concat(resultArr);
		String[] originalTitle = data.get(0);
		int size = originalTitle.length;

		while(looper < data.size()) {
			String[] dataArr = data.get(looper);
			String dataStr = concat(dataArr);
			dataVal = Integer.parseInt(dataArr[size - 1]);
			if(resultStr.equals(dataStr)) {
				sum += dataVal;
				if (looper == data.size() - 1) {
					String[] addString = new String[data.get(looper).length];
					addString = data.get(looper);
					addString[size - 1] = "" + sum;
					result.add(addString);
				}
				looper++;
			}

			else {
				String[] addString = new String[result.get(0).length];
				addString = resultArr;
				addString[size - 1] = "" + sum;
				result.add(addString);
				sum = 0;
				resultArr = data.get(looper);
				resultStr = concat(resultArr);
			}
		}
		String[] title = result.pop();
		title[size - 1] = "sum(" + originalTitle[size - 1] + ")";
		result.addFirst(title);
		return result;

	}
	private static LinkedList<String[]> avg(LinkedList<String[]> data) {
		LinkedList<String[]> result = new LinkedList<String[]>();
		int looper = 1;
		double count = 0;
		
		int dataVal = 0;
		double sum = 0;
		double average = 0;
		DecimalFormat df = new DecimalFormat("#.###");
		result.add(data.get(0));
		String[] resultArr = data.get(1);
		String resultStr = concat(resultArr);
		String[] originalTitle = data.get(0);
		int size = originalTitle.length;

		while(looper < data.size()) {
			String[] dataArr = data.get(looper);
			String dataStr = concat(dataArr);
			dataVal = Integer.parseInt(dataArr[size - 1]);
			if(resultStr.equals(dataStr)) {
				sum += dataVal;
				count++;
				if (looper == data.size() - 1) {
					String[] addString = new String[size];
					average = sum / count;
					addString = data.get(looper);
					addString[size - 1] = "" + Double.valueOf(df.format(average));
					result.add(addString);
				}
				looper++;
			}
			else {
				String[] addString = new String[size];
				average = sum / count;
				addString = resultArr;
				addString[size - 1] = "" + Double.valueOf(df.format(average));
				result.add(addString);
				sum = 0;
				count = 0;
				resultArr = data.get(looper);
				resultStr = concat(resultArr);
			}
		}
		String[] title = result.pop();
		title[size - 1] = "avg(" + originalTitle[size - 1] + ")";
		result.addFirst(title);
		return result;

	}
	private static int comp(String left, String right) {
		/* Little workaround (only the use of replaceAll) I found in: 
		https://stackoverflow.com/questions/24529205/ignore-numbers-in-a-string
		This lets us ignore any numbers in the Strings and compare them. If they
		are equal, then we compare with the numbers.
		*/
		String leftCopy = left.replaceAll("\\d+", "");
		String rightCopy = right.replaceAll("\\d+", "");

		if(leftCopy.compareTo(rightCopy) == 0) {
			//Left is greater than right
			if (left.compareTo(right) >= 0) {
				return 1;
			}
			//Right is greater than left
			else {
				return -1;
			}
		}
		else if (leftCopy.compareTo(rightCopy) > 0) {
			return 1;

		}
		else {
			return -1;
		}

	}
	/*
		I made the mergeSort and merge functions based on the pseudocode found
		in:
		http://www.codecodex.com/wiki/Merge_sort#Pseudocode 
	*/
	private static LinkedList<String[]> mergeSort(LinkedList<String[]> arr) {
		if (arr.size() <= 1) {
			return arr;
		}
		else {
			LinkedList<String[]> right = new LinkedList<String[]>();
			LinkedList<String[]> left = new LinkedList<String[]>();
			LinkedList<String[]> result = new LinkedList<String[]>();
			int middle = arr.size() / 2;

			for (int i = 0; i < middle; i++) {
					left.add(arr.get(i));
			}
			for (int i = middle; i < arr.size(); i++) {
					right.add(arr.get(i));
			}
			left = mergeSort(left);
			right = mergeSort(right);
			result = merge(left, right);
			return result;
		}

	}
	private static String concat(String[] arr) {
		String result = "";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length - 1; i++) {
			//result += arr[i];
			sb.append(arr[i]);
		}
		result = sb.toString();
		return result;
	}
	private static LinkedList<String[]> merge(LinkedList<String[]> left, LinkedList<String[]> right) {
		LinkedList<String[]> result = new LinkedList<String[]>();
		String[] leftArr = left.get(0);
		String leftStr = concat(leftArr);
		String[] rightArr = right.get(0);
		String rightStr = concat(rightArr);
		while (left.size() > 0 && right.size() > 0) {
			leftArr = left.get(0);
			rightArr = right.get(0);
			leftStr = concat(leftArr);
			rightStr = concat(rightArr);

			if(comp(leftStr, rightStr) < 0) {
				result.add(left.get(0));
				left.removeFirst();
			} 
			else {
				result.add(right.get(0));
				right.removeFirst();

			}
		}
		if(left.size() > 0) {
			while(!left.isEmpty()) {
				result.add(left.get(0));
				left.removeFirst();
			}
		}
		if(right.size() > 0) {
			while(!right.isEmpty()) {
				result.add(right.get(0));
				right.removeFirst();
			}
		}
		return result;

	}
	private static LinkedList<String[]> listShrinker(LinkedList<String[]> initial, String[] argument, int indexAggCol, String[] title) {
		int counter = argument.length - 3;
		int index = 0;
		int argumentIndex = 3;
		LinkedList<String[]> shrunk = new LinkedList<String[]>();
		while (index < initial.size()) {
			String[] addString = new String[counter + 1];
			String[] copyStringInitial = initial.get(index);
			for(int i = 0; i < counter; i++) {
				int column = findIndex(title, argument[argumentIndex]);
				addString[i] = copyStringInitial[column];
				argumentIndex++;
			}
			addString[counter] = copyStringInitial[indexAggCol];
			shrunk.add(addString);
			index++;
			argumentIndex = 3;
		}
		return shrunk;
	}
	private static int findIndex(String[] title, String col) throws IllegalArgumentException {
		for(int i = 0; i < title.length; i++) {
			if(title[i].equals(col)) {
				return i;
			}
		}
		throw new IllegalArgumentException("A grouping column does not exist.");
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, IllegalArgumentException {
		long startTime = System.currentTimeMillis();
		boolean valid = false;
		if(args[0].equals("count") || args[0].equals("sum") || args[0].equals("avg")) {
			valid = true; 
		}
		if (valid == false) {
			throw new IllegalArgumentException("The <function> argument is not one of 'count', 'sum', or 'avg'.");

		}

		LinkedList<String[]> data = readFile(args[2]);

		String[] title = data.get(0);
		int indexAggCol = 0;
		try {
			indexAggCol = findIndex(title, args[1]);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The aggregation column does not exist.");
		}

		String[] test = data.get(1);
		int dummy = 0;
		try {
			dummy = Integer.parseInt(test[indexAggCol]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Aggregation column contains non-numerical data.");
		}

		for(int i = 3; i < args.length; i++) {
			if(args[i].equals(args[1])) {
				throw new IllegalArgumentException("A column requested is used as both a grouping column and an aggregation column.");
			}
		}

		//String str = output(data, args[2]);
		//System.out.println(str);

		LinkedList<String[]> shrunkData = listShrinker(data, args, indexAggCol, title);
		title = shrunkData.get(0);

		//String shrunkStr = output(shrunkData, args[2]);
		//System.out.println(shrunkStr);

		//System.out.println();
		shrunkData.removeFirst();
		LinkedList<String[]> sorted = mergeSort(shrunkData);
		sorted.addFirst(title);
		//String sortedStr = output(sorted, args[2]);
		//System.out.println("Sorted table:\n" + sortedStr);

		if(args[0].equals("count")) {
			LinkedList<String[]> counting = count(sorted);
			String countStr = output(counting, args[2]);
			System.out.println(countStr);

		}
		else if(args[0].equals("sum")) {
			LinkedList<String[]> summing = sum(sorted);
			String sumStr = output(summing, args[2]);
			System.out.println(sumStr);

		}
		else if(args[0].equals("avg")) {
			LinkedList<String[]> averaging = avg(sorted);
			String avgStr = output(averaging, args[2]);
			System.out.println(avgStr);

		}
		System.out.println("Take: " + (System.currentTimeMillis() - startTime) + "ms");
	}
}