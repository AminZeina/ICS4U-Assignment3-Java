import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This program sorts, searches, and inserts numbers in a list without any built in methods.
 * 
 * @author Amin Zeina
 * @version 1.0
 * @since 2020-04-12
 */
 
public class SortAndSearch {
  
  /**
  * This method is a binary search. It takes receives a number + arrayList, then searches for 
  * that number. Then returns the position of the value, or that that value doesn't exist.
  */
 
  public static String binarySearch(int valueToSearch, ArrayList<Integer> sortedList) {
    int lowIndex = 0;
    int highIndex = sortedList.size() - 1;
    int midIndex = 0;
    String stringToReturn;
    
    while (lowIndex <= highIndex) {
      // Set middle index
      midIndex = (lowIndex + highIndex) / 2;
      
      if (valueToSearch > sortedList.get(midIndex)) {
        // All values before middle are not the number, no need to check them. Adjust low Index
        lowIndex = midIndex + 1;
      } else if (valueToSearch < sortedList.get(midIndex)) {
        // All values after middle are not the number, no need to check them. Adjust high Index.
        highIndex = midIndex - 1;
      } else {
        break;
      }
    }
    
    if (lowIndex > highIndex) {
      stringToReturn = "The value " + valueToSearch + " does not exist in this list.";
    } else {
      stringToReturn = "The value " + valueToSearch + " exists at position # " + (midIndex + 1) 
          + ".";
    }
    
    return stringToReturn;
  }
  
  /**
  * This method sorts an arrayList without any built in methods. It recieves an unsorted arrayList,
  * then returns a sorted arrayList in ascending order.
  */
 
  public static ArrayList<Integer> sortList(ArrayList<Integer> listToSort) {
    
    /* Sort array from least to greatest, goes from [1] and compares to all other values 
      (with inner loop), ensures that [1] is now the lowest value. Then does the same for 
      [2] and so on.
    */
    for (int counterOne = 0; counterOne < listToSort.size(); counterOne++) {
      for (int counterTwo = counterOne + 1; counterTwo < listToSort.size(); counterTwo++) {
        int tempIntToMove = 0;
        // Check if next # is smaller, then swap places if it is.
        if (listToSort.get(counterOne) > listToSort.get(counterTwo)) {
          tempIntToMove = listToSort.get(counterOne);
          listToSort.set(counterOne, listToSort.get(counterTwo));
          listToSort.set(counterTwo, tempIntToMove);
        }
      }
    }
    return listToSort;
  }
  
  /**
  * This method recieves the sorted array and a value to insert, then inserts the value in a 
  * postion that would keep the array sorted, shifting the array to accomplish this.
  */
  
  public static ArrayList<Integer> insertValue(ArrayList<Integer> sortedList, int valueToInsert) {
    
    int listSize = sortedList.size();
    int elementCounter;
    
    // Add value to the end, to avoid issue with .set being out of index later on.
    sortedList.add(valueToInsert);
    
    // Check if valueToInsert is smaller, since if it's larger, no need to move anything. (at end)
    if (valueToInsert < sortedList.get(listSize - 1)) {
      
      // Starting from greatest value, comaparing array value to insert value, and shifting array
      for (elementCounter = listSize - 1; valueToInsert < sortedList.get(elementCounter) 
          && elementCounter > 0;elementCounter--) {
        /* Repeat so long as valueToInsert is smaller than next value, each time shifting current 
           value to make room for the new, inserted value
        */
        sortedList.set(elementCounter + 1, sortedList.get(elementCounter));
      }
      /* Original value isn't lost because it has already been shifted. But if counter == 0,
         then valueToInsert is smallest or 2nd smallest in list, so must come at [0] or [1]. 
         Can't do in for Loop since counter > 0, if counter is 0 then it is out-of-index error.
      */
      if (elementCounter == 0 && valueToInsert < sortedList.get(elementCounter)) {
        // Value < smallest, value should be at [0]
        sortedList.set(elementCounter + 1, sortedList.get(elementCounter));
        sortedList.set(elementCounter, valueToInsert);
      } else {
        // Value is no longer less then next value, so can insert value at the next position. 
        sortedList.set(elementCounter + 1, valueToInsert);
      }
    }
    
    return sortedList;
  }
  
  /**
  * This class generates the random array of 250 values, then calls sortArray to sort.
  * Then gets input from the user to add values and search the array.
  */
 
  public static void main(String[] args) {
  
    // Delcare variables 
    ArrayList<Integer> randomValuesList = new ArrayList<Integer>(); 
    int valueToSearch;
    int valueToInsert;

    Random random = new Random();
    Scanner userInput = new Scanner(System.in);
    StringBuilder stringBuilder = new StringBuilder();
  
    // Populate array with random values 
    for (int counter = 0; counter < 250; counter++) {
      randomValuesList.add(random.nextInt(499) + 1);
    }
  
    // Show unsorted array to user
    //stackoverflow.com/questions/31718178/how-to-remove-the-brackets-from-arraylisttostring
    System.out.println("Here is the unsorted list:");
    for (int counter : randomValuesList) {
      stringBuilder.append(counter).append(", ");
    }

    stringBuilder.setLength(stringBuilder.length() - 2);
    System.out.println(stringBuilder.toString());
    
    // Visual Clarity
    System.out.print("\n");
    
    // Call sortListmethod to sort array
    ArrayList<Integer> sortedList = sortList(randomValuesList);
    
    // Reset string Builder
    stringBuilder.setLength(0);
    
    // Show sorted array to user
    System.out.println("Here is the sorted list:");
    for (int counter : sortedList) {
      stringBuilder.append(counter).append(", ");
    }
    
    stringBuilder.setLength(stringBuilder.length() - 2);
    System.out.println(stringBuilder.toString());
    
    System.out.print("\n");
    
    
    while (true) {
      
      System.out.print("Enter an integer to add to the list: ");
      // Get number to add, then add it
      try {
        valueToInsert = userInput.nextInt();
        userInput.nextLine();
      } catch (Exception e) {
        System.err.println("That is not an integer. Please try again");
        userInput.nextLine();
        continue;
      }
      
      // Insert value entered and show to user
      sortedList = insertValue(sortedList, valueToInsert);
      System.out.println("\nHere is the sorted array with your number inserted:");
      stringBuilder.setLength(0);
      for (int counter : sortedList) {
        stringBuilder.append(counter).append(", ");
      }
    
      stringBuilder.setLength(stringBuilder.length() - 2);
      System.out.println(stringBuilder.toString());
      
      System.out.print("Do you want to add another value? (yes/no): ");
      if (userInput.nextLine().toLowerCase().equals("yes")) {
        continue;
      } else {
        break;
      }
    }
    
    while (true) {
      
      System.out.print("Enter a number to search for: ");
      // Get number to search for, then search for it
      try {
        valueToSearch = userInput.nextInt();
        userInput.nextLine();
        System.out.println(binarySearch(valueToSearch, randomValuesList));
        
      } catch (Exception e) {
        System.err.println("That is not an integer. Please try again");
        userInput.nextLine();
        continue;
      }
      
      
      System.out.print("Do you want to search again? (yes/no): ");
      if (userInput.nextLine().toLowerCase().equals("yes")) {
        continue;
      } else {
        break;
      }
    }
    
    System.out.println("Program End.");
  }
}