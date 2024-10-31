import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import static java.util.Collections.swap;

public class Sorting {

    ArrayList<Integer> numbers;
    ArrayList<Integer> temp; // used only for mergeSort
    File outputFile;
    long comparisons;
    long swaps;
    String algorithm;

    public Sorting(File input, File output, String algorithm) {
        this.outputFile = output;
        this.numbers = readListFromFile(input);
        this.comparisons = 0;
        this.swaps = 0;
        this.algorithm = algorithm;
    }

    public void sortm(){
        temp = new ArrayList<>(numbers.size());
        for(int i =0; i < numbers.size(); i++)
            temp.add(0);
        recursiveMerge(numbers, 0, numbers.size());
    }

    public void recursiveMerge(ArrayList<Integer> array, int left, int right){
        if(right - left <= 1) {
            return;

        } else {

            int middle = (left + right) / 2;
            recursiveMerge(array, left, middle); // call mergesort on the left side of itself
            recursiveMerge(array, middle, right); // call mergesort on the right side of itself
            merge(array, left, middle, right);
        }
    }

    private void merge(ArrayList<Integer> array, int left, int middle, int right) {
        int leftIndex = left;
        int rightIndex = middle;
        int tempIndex = left;

        while(leftIndex < middle && rightIndex < right){
            comparisons++;
            if(array.get(leftIndex) <= array.get(rightIndex)){
                temp.set(tempIndex,array.get(leftIndex));
                leftIndex++;
            } else{
                temp.set(tempIndex,array.get(rightIndex));
                rightIndex++;
            }
            tempIndex++;
            swaps++;
        }

        // clean up code
        while(leftIndex < middle){ // check if there are leftovers to the right
            temp.set(tempIndex, array.get(leftIndex));
            leftIndex++;
            tempIndex++;
            swaps++;
        }
        while(rightIndex < right){ // check if there are any leftovers to the right
            temp.set(tempIndex, array.get(rightIndex));
            rightIndex++;
            tempIndex++;
            swaps++;
        }

        // okay, numbers in temp are sorted so we copy them back to the original array
        for(int i = left; i < right; i++){
            array.set(i,temp.get(i));
        }
    }

    public void sort() {
        switch (algorithm) {
            case "InsertionSort":
                insertionSort(numbers);
                break;
            case "SelectionSort":
                SelectionSort(numbers);
                break;
            case "MergeSort":
                sortm();
                break;
            case "QuickSort":
                recursiveQuickSort(numbers, 0, numbers.size()-1);
                break;
            default:
                System.out.println("Unknown algorithm: " + algorithm);
        }
        writeListToFile(outputFile, numbers);
    }

    private void SelectionSort(ArrayList<Integer> array) {
        int n = array.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array.get(j) < array.get(minIndex)) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array.get(i);
                array.set(i, array.get(minIndex));
                array.set(minIndex, temp);
                swaps++;
            }
        }
    }

    private ArrayList<Integer> readListFromFile(File inputFile) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    public void insertionSort(ArrayList<Integer> list) {
        int n = list.size();

        // Go through 1 to n elements
        for (int i = 1; i < n; i++) {
            int key = list.get(i);
            int j = i - 1;

            while (j >= 0) {
                this.comparisons++;

                if (list.get(j) > key) {
                    list.set(j + 1, list.get(j));
                    j--;
                    this.swaps++;
                } else {
                    break;
                }
            }
            list.set(j + 1, key);  
        }
    }

    private void recursiveQuickSort(ArrayList<Integer> array, int left, int right){
        if(left >= right) // ??? I think...
            return;
        int pivot = partition(array, left, right);

        recursiveQuickSort(array, left, pivot - 1);
        recursiveQuickSort(array, pivot + 1, right);
    }

    private int partition(ArrayList<Integer> array, int left, int right) {
        int pivotIndex = medianOfThree(array, left, right); // Choose pivot using median-of-three
        int pivotValue = array.get(pivotIndex);

        // Swap the pivot with the leftmost element (if necessary)
        if (pivotIndex != left) {
            swap(array, left, pivotIndex);
            swaps++;
        }

        int leftPointer = left + 1;
        int rightPointer = right;

        while (leftPointer <= rightPointer) {
            // Find elements larger than the pivot on the left
            while (leftPointer <= right && array.get(leftPointer) <= pivotValue) {
                leftPointer++;
                comparisons++;
            }

            // Find elements smaller than the pivot on the right
            while (rightPointer >= left && array.get(rightPointer) > pivotValue) {
                rightPointer--;
                comparisons++;
            }

            // Swap left and right pointers if they haven't crossed
            if (leftPointer < rightPointer) {
                swap(array, leftPointer, rightPointer);
                swaps++;
            }
        }

        // Swap the pivot into its final place (swap with rightPointer)
        swap(array, left, rightPointer);
        swaps++;

        // Return the position of the pivot
        return rightPointer;
    }

//    private int partition(ArrayList<Integer> array, int left, int right) {
//        int pivotIndex = medianOfThree(array, left, right); // Choose pivot using median-of-three
//        int pivotValue = array.get(pivotIndex);
//        swap(array, pivotIndex, right); // Swap pivot to the end
//
//        int leftPointer = left;
//        int rightPointer = right - 1;
//
//        while (leftPointer <= rightPointer) {
//            comparisons++;
//            while (leftPointer <= rightPointer && array.get(leftPointer) <= pivotValue) {
//                leftPointer++;
//                comparisons++;
//            }
//            comparisons++;
//            while (leftPointer <= rightPointer && array.get(rightPointer) > pivotValue) {
//                rightPointer--;
//                comparisons++;
//            }
//            if (leftPointer < rightPointer) {
//                swap(array, leftPointer, rightPointer);
//                comparisons++;
//                swaps++;
//            }
//        }
//
//        // Swap pivot with leftPointer, as that's where the split happens
//        swap(array, leftPointer, right);
//        swaps++;
//
//        return leftPointer; // Return the pivot index
//    }

    private int medianOfThree(ArrayList<Integer> array, int left, int right) {
        int mid = left + (right - left) / 2;

        int a = array.get(left);
        int b = array.get(mid);
        int c = array.get(right);

        // Find the median of array[left], array[mid], array[right]
        if ((a > b) == (a < c)) {
            return left; // a is the median
        } else if ((b > a) == (b < c)) {
            return mid; // b is the median
        } else {
            return right; // c is the median
        }
    }

    public boolean checkIfSorted() {
        // use a "gauntlet"
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) > numbers.get(i + 1)) {
                return false;
            }
        }
        return true;
    }


    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    private void writeListToFile(File outputFile, ArrayList<Integer> list) {
        try {
            FileWriter writer = new FileWriter(outputFile);
            for (int num : list) {
                writer.write(num + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}
