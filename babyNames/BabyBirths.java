import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BabyBirths {
    public int yearValidation() {
        Scanner scan = new Scanner(System.in);
        int year;

        while (true) {
            try {
                System.out.print("Please, provide a year (must be between 1880 and 2014): ");
                year = scan.nextInt();
            } catch (InputMismatchException exception) {
                scan.next();
                System.out.println("You need to enter a number!\n");
                continue;
            }

            if (year < 1880 || year > 2014) {
                System.out.println("We do not have data for this year. Select a year between 1880 and 2014.\n");
                continue;
            }
            break;
        }
        return year;
    }

    public void printNames () {
    FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                                   " Gender " + rec.get(1) +
                                   " Num Born " + rec.get(2));
            }
        }
    }
    
    public void totalBirths() {
        FileResource file = new FileResource();
        int sumOfBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
       
       
        for (CSVRecord record : file.getCSVParser(false)) {
            int numberOfBornForName = Integer.parseInt(record.get(2));
            sumOfBirths += numberOfBornForName;
        
            if (record.get(1).equals("M")) {
                totalBoys += numberOfBornForName;
            } else {
                totalGirls += numberOfBornForName;
            }
        }
        System.out.println("Filename: " + file);
        System.out.println("The total number of newborns is " + sumOfBirths);
        System.out.println("The total number of girls is " + totalGirls);
        System.out.println("The total number of boys is " + totalBoys);
    }
    
    public void totalNumberOfNames() {
        FileResource file = new FileResource();
        int totalNames = 0;
        int girlNames = 0;
        int boyNames = 0;
        
        for (CSVRecord record : file.getCSVParser(false)) {
            totalNames++;
            
            if (record.get(1).equals("M")) {
                boyNames++;
            } else {
                girlNames++;
            }
        }
        System.out.println("The total number of names is " + totalNames);
        System.out.println("The total number of girl names is " + girlNames);
        System.out.println("The total number of boy names is " + boyNames);
    }
    
    public int getRank(int year, String name, String gender){
        String fileName = "yob" + year + ".csv";
        FileResource file = new FileResource("data/" + fileName);
        int rank = 0;
        
        for (CSVRecord record : file.getCSVParser(false)) {
            if (record.get(1).equals(gender)) {
                if (record.get(0).equals(name)) {
                    rank += 1;
                    return rank;
                 } else {
                    rank += 1;
                }
            }
        }
        return -1;
    }
    
    public String getName(int year, int rank, String gender) {
        String fileName = "yob" + year + ".csv";
        FileResource file = new FileResource("data/" + fileName);
        CSVParser parser = file.getCSVParser(false);
        int lineCounter = 1;
        
        for (CSVRecord record : parser) {
            if (record.get(1).equals(gender)) {
                if (lineCounter == rank) {
                    return record.get(0);
                } else {
                    lineCounter++;
                }
            }
        }
        return "NO NAME";
    }
    
    public String whatIsNameInYear(String name, int year, int newYear, String gender) {
        int actualRank = getRank(year, name, gender);
        String fileName = "yob" + year + ".csv";
        FileResource file = new FileResource("data/" + fileName);
        String nameInAnotherYear = getName(newYear, actualRank, gender);

        return nameInAnotherYear;
    }
    
    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource directory = new DirectoryResource();
        int highestRank = Integer.MAX_VALUE;
        int theYearOfHighestRank = -1;
        
        for (File file : directory.selectedFiles()) {
            //FileResource resource = new FileResource(file);
            int year = Integer.parseInt(file.getName().substring(3, 7));
            int currentRank = getRank(year, name, gender);
            
            if (currentRank < highestRank) {
                highestRank = currentRank;
                theYearOfHighestRank = year;
            }
        }
        return theYearOfHighestRank;
    }
    
    public double getAverageRank(String name, String gender) {
        DirectoryResource directory = new DirectoryResource();
        double ranksSum = 0.0;
        double numberOfRanks = 0.0;
        
        for (File file : directory.selectedFiles()) {
            FileResource resource = new FileResource(file);
            int year = Integer.parseInt(file.getName().substring(3, 7));
            int currentRank = getRank(year, name, gender);
            
            if (currentRank != -1) {
                ranksSum += currentRank;
                numberOfRanks++;
            }
        }
        
        if (numberOfRanks != 0.0) {
            return ranksSum / numberOfRanks;
        }
        return -1;
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int numberOfHigherRankBirths = 0;
        String fileName = "yob" + year + ".csv";
        FileResource file = new FileResource("data/" + fileName);
        
        for (CSVRecord record : file.getCSVParser()) {
            if (record.get(1).equals(gender)) {
                if (!record.get(0).equals(name)) {
                    numberOfHigherRankBirths += Integer.parseInt(record.get(2));
                } else {
                    break;
                }
            }
        }
        return numberOfHigherRankBirths;
    }

    public void testTotalBirths() {
        while (true) {
            try {
                totalBirths();
                break;
            } catch (ResourceException exception) {
                System.out.println("There was an error. Either the file name or the path is wrong.\n");
            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("There was an error. Did you select a valid file?\n");
            }
        }
    }
    
    public void testTotalNumberOfNames() {
        while (true) {
            try {
                totalNumberOfNames();
                break;
            } catch (ResourceException exception) {
                System.out.println("There was an error. Either the file name or the path is wrong.\n");
            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("There was an error. Did you select a valid file?\n");
            }
        }

    }
    
    public void testGetRank(){
        Scanner scan = new Scanner(System.in);
        int year;
        String name, gender;
        
        while (true) {
            year = yearValidation();
            System.out.print("Please, provide a name (the name must start with a capital letter): ");
            name = scan.next();
            System.out.print("Please, provide a gender writing M or F: ");
            gender = scan.next();
            int rank = getRank(year, name, gender);
            
            if (rank == -1) {
                System.out.println("The information does not match any entry in the file, try again.\n");
            } else {
                System.out.println("The rank of " + name + " in " + year + " is " + rank);
                break;
            }
        }
    }
    
    public void testGetName() {
        Scanner scan = new Scanner(System.in);
        int year, rank;
        String gender;

        while (true) {
            year = yearValidation();
            System.out.print("Please, provide a rank: ");
            rank = scan.nextInt();
            System.out.print("Please, provide a gender writing M or F: ");
            gender = scan.next();
            String nameAtSpecifiedRank = getName(year, rank, gender);

            if (nameAtSpecifiedRank.equals("NO NAME")) {
                System.out.println("The information does not match any entry in the file, try again.\n");
            } else {
                System.out.println("Name at rank " + rank + " in " + year + " is " + nameAtSpecifiedRank);
                break;
            }
        }
    }
    
    public void testWhatIsNameInYear() {
        Scanner scan = new Scanner(System.in);
        String name, gender;
        int currentYear, otherYear;

        while (true) {
            System.out.println("- N.B.: You will be asked to select two years. The first year " +
                                "is the one the name is born, then we check what name" +
                                " has the same rank in the second year.\n");
            currentYear = yearValidation();
            otherYear = yearValidation();
            System.out.print("Please, provide a name (the name must start with a capital letter): ");
            name = scan.next();
            System.out.print("Please, provide a gender writing M or F: ");
            gender = scan.next();

            String nameInAnotherYear = whatIsNameInYear(name, currentYear, otherYear, gender);

            if (nameInAnotherYear.equals("NO NAME")) {
                System.out.println("The information does not match any entry in the file, try again.\n");
            } else {
                System.out.println(name + " born in " + currentYear + " would be " + nameInAnotherYear +
                        " if they were born in " + otherYear);
                break;
            }
        }

    }
    
    public void testYearOfHighestRank() {
        Scanner scan = new Scanner(System.in);
        String name, gender;

        while (true) {
            System.out.print("Please, provide a name (the name must start with a capital letter): ");
            name = scan.next();
            System.out.print("Please, provide a gender writing M or F: ");
            gender = scan.next();

            int highestRankForName = yearOfHighestRank(name, gender);
            System.out.println("The year of highest rank is " + highestRankForName);
        }
    }
    
    public void testGetAverageRank() {
        double averageRankForName = getAverageRank("Johnny", "M");
        System.out.println("Average is " + averageRankForName);
    }
    
    public void testGetTotalBirthsRankedHigher() {
        Scanner scan = new Scanner(System.in);
        int year, higherRankBirths;
        String name, gender;
        
        while (true) {
            year = yearValidation();
            System.out.print("Please, provide a name (the name must start with a capital letter): ");
            name = scan.next();
            System.out.print("Please, provide a gender writing M or F: ");
            gender = scan.next();
            
            try {
                higherRankBirths = getTotalBirthsRankedHigher(year, name, gender);
            } catch (ResourceException exception) {
                System.out.println("The file does not exist, try again.\n");
                continue;
            }
            
            if (getRank(year, name, gender) == -1) {
                System.out.println("The information does not match any entry in the file, please try again.\n");
            } else {
                if (higherRankBirths == 0) {
                    System.out.println("There are no other births ranking higher than " + name + " in " + year);
                } else {
                    System.out.println("The number of higher rank births is " + higherRankBirths);
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
        BabyBirths sampleProgram = new BabyBirths();
        // sampleProgram.testGetTotalBirthsRankedHigher();
        // sampleProgram.testTotalBirths();
        sampleProgram.testTotalNumberOfNames();
        // sampleProgram.testGetRank();
        // sampleProgram.testGetName();
        // sampleProgram.testWhatIsNameInYear();
    }
}
