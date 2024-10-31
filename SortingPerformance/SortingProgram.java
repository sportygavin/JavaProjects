import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SortingProgram {
    public static void main(String[] args) {


        createFiles();

//        Old Code file generation
//        generateRandomNumbersToFile(new File("InputFiles/AverageCase20000.txt"), 20000, 1, 20000);
//        generateRandomNumbersToFile(new File("InputFiles/AverageCase40000.txt"), 40000, 1, 40000);
//        generateRandomNumbersToFile(new File("InputFiles/AverageCase80000.txt"), 80000, 1, 80000);
//
//        generateBestCaseNumbersToFile(new File("InputFiles/BestCase20000.txt"), 20000);
//        generateBestCaseNumbersToFile(new File("InputFiles/BestCase40000.txt"), 40000);
//        generateBestCaseNumbersToFile(new File("InputFiles/BestCase80000.txt"), 80000);
//
//        generateWorstCaseNumbersToFile(new File("InputFiles/WorstCase20000.txt"), 20000);
//        generateWorstCaseNumbersToFile(new File("InputFiles/WorstCase40000.txt"), 40000);
//        generateWorstCaseNumbersToFile(new File("InputFiles/WorstCase80000.txt"), 80000);

        File[] inputFiles = {
                new File("InputFiles/AverageCase20000.txt"),
                new File("InputFiles/AverageCase40000.txt"),
                new File("InputFiles/AverageCase80000.txt"),
                new File("InputFiles/BestCase20000.txt"),
                new File("InputFiles/BestCase40000.txt"),
                new File("InputFiles/BestCase80000.txt"),
                new File("InputFiles/WorstCase20000.txt"),
                new File("InputFiles/WorstCase40000.txt"),
                new File("InputFiles/WorstCase80000.txt")
        };

        String[] algorithms = {"MergeSort", "InsertionSort", "SelectionSort","QuickSort"};

        System.out.println("Algorithm\t\tSize\t\tTime(ms)\tSwaps\t\tComparisons\t\tFile Type");

        runSortingAlgorithms(inputFiles, algorithms);

//        Old Code:
//        for (String algorithm : algorithms) {
//            System.out.println("=== " + algorithm + " ===");
//            for (File inputFile : inputFiles) {
//                File outputFile = new File("OutputFiles/" + inputFile.getName().replace(".txt", algorithm + "Output.txt"));
//                Sorting myApp = new Sorting(inputFile, outputFile, algorithm);
//
//                // Record time and perform sorting
//                long startTime = System.currentTimeMillis();
//                myApp.sort();
//                long endTime = System.currentTimeMillis();
//                long timeElapsed = endTime - startTime;
//
//
//                // Determine case type from the file name
//                String caseType = "";
//                if (inputFile.getName().contains("Average")) {
//                    caseType = "Average Case";
//                } else if (inputFile.getName().contains("Best")) {
//                    caseType = "Best Case";
//                } else if (inputFile.getName().contains("Worst")) {
//                    caseType = "Worst Case";
//                }
//
//                // Print results in a formatted line
//                System.out.printf("%-15s %-10d %-10d %-10d %-15d %-15s%n",
//                        algorithm,                // Algorithm name
//                        myApp.numbers.size(),     // Dataset size
//                        timeElapsed,              // Time taken
//                        myApp.getSwaps(),         // Number of swaps
//                        myApp.getComparisons(),
//                        caseType);  // Number of comparisons
//
//                // Check if the array is sorted
//                if (!myApp.checkIfSorted()) {
//                    System.out.println("Sorting failed for " + algorithm + " on " + inputFile.getName());
//                }
//            }
//            System.out.println();
//        }

    }

    private static void runSortingAlgorithms(File[] inputFiles, String[] algorithms) {
        for (String algorithm : algorithms) {
            System.out.println("=== " + algorithm + " ===");
            for (File inputFile : inputFiles) {
                File outputFile = new File("OutputFiles/" + inputFile.getName().replace(".txt", algorithm + "Output.txt"));
                Sorting myApp = new Sorting(inputFile, outputFile, algorithm);
                long timeElapsed = performSorting(myApp);
                printResults(algorithm, myApp, inputFile, timeElapsed);
            }
            System.out.println();
        }
    }

    private static void printResults(String algorithm, Sorting myApp, File inputFile, long timeElapsed) {
        String caseType = determineCaseType(inputFile);

        System.out.printf("%-15s %-10d %-10d %-10d %-15d %-15s%n",
                algorithm,
                myApp.numbers.size(),
                timeElapsed,
                myApp.getSwaps(),
                myApp.getComparisons(),
                caseType
        );

        // Verify if the array is sorted
        if (!myApp.checkIfSorted()) {
            System.out.println("Sorting failed for " + algorithm + " on " + inputFile.getName());
        }
    }

    private static String determineCaseType(File inputFile) {
        if (inputFile.getName().contains("Average")) {
            return "Average Case";
        } else if (inputFile.getName().contains("Best")) {
            return "Best Case";
        } else if (inputFile.getName().contains("Worst")) {
            return "Worst Case";
        }
        return "Unknown Case";
    }

    private static long performSorting(Sorting myApp) {
        long startTime = System.currentTimeMillis();
        myApp.sort();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void createFiles() {
        // Define an array of sizes for the different cases
        int[] sizes = {20000, 40000, 80000};

        // Generate Average Case Files
        for (int size : sizes) {
            generateRandomNumbersToFile(new File("InputFiles/AverageCase" + size + ".txt"), size, 1, size);
        }

        // Generate Best Case Files
        for (int size : sizes) {
            generateBestCaseNumbersToFile(new File("InputFiles/BestCase" + size + ".txt"), size);
        }

        // Generate Worst Case Files
        for (int size : sizes) {
            generateWorstCaseNumbersToFile(new File("InputFiles/WorstCase" + size + ".txt"), size);
        }
    }



    public static void generateRandomNumbersToFile(File file, int size, int min, int max) {
        Random rand = new Random();
        try {
            FileWriter writer = new FileWriter(file);

            for (int i = 0; i < size; i++) {
                int randomNum = rand.nextInt((max - min) + 1) + min;  // Generate random number between min and max
                writer.write(randomNum + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    public static void generateBestCaseNumbersToFile(File file, int size) {
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 1; i <= size; i++) {
                writer.write(i + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void generateWorstCaseNumbersToFile(File file, int size) {
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = size; i >= 1; i--) {
                writer.write(i + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


}