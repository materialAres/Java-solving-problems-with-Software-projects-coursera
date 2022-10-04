import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
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
    
    public void totalBirths(FileResource file) {
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
       System.out.println("The total number of newborns is " + sumOfBirths);
       System.out.println("The total number of girls is " + totalGirls);
       System.out.println("The total number of boys is " + totalBoys);
    }
    
    public void totalNumberOfNames(FileResource file) {
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
        String fileName = "yob" + year + "short.csv";
        FileResource file = new FileResource("testing/" + fileName);
        int rank = 0;
        boolean found = false;
        
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
        String fileName = "yob" + year + "short.csv";
        FileResource file = new FileResource("testing/" + fileName);
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
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int actualRank = getRank(year, name, gender);
        String fileName = "yob" + year + "short.csv";
        FileResource file = new FileResource("testing/" + fileName);
        String nameInAnotherYear = getName(newYear, actualRank, gender);
        
        System.out.println(name + " born in " + year + " would be " + nameInAnotherYear + " if they were born in " + newYear);
    }
    
    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource directory = new DirectoryResource();
        int highestRank = Integer.MAX_VALUE;
        int theYearOfHighestRank = -1;
        
        for (File file : directory.selectedFiles()) {
            FileResource resource = new FileResource(file);
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
        String fileName = "yob" + year + "short.csv";
        FileResource file = new FileResource("testing/" + fileName);
        
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
        FileResource file = new FileResource("data/yob2014.csv");
        totalBirths(file);
    }
    
    public void testTotalNumberOfNames() {
        FileResource file = new FileResource("data/example-small.csv");
        totalNumberOfNames(file);
    }
    
    public void testGetRank(){
        int rank = getRank(2012,"Mason", "M");
	System.out.println("Rank = " + rank );
    }
    
    public void testGetName(){
        String nameAtSpecifiedRank = getName(2013, 7, "F");
	System.out.println("Name = " + nameAtSpecifiedRank);
    }
    
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Jacob", 2014, 2013, "M");
    }
    
    public void testYearOfHighestRank() {
        int highestRankForName = yearOfHighestRank("Isabella", "F");
        System.out.println("The year of highest rank is " + highestRankForName);
    }
    
    public void testGetAverageRank() {
        double averageRankForName = getAverageRank("Johnny", "M");
        System.out.println("Average is " + averageRankForName);
    }
    
    public void testGetTotalBirthsRankedHigher() {
        int higherRankBirths = getTotalBirthsRankedHigher(2012, "Ethan", "M");
        System.out.println("The number of higher rank births is " + higherRankBirths);
    }
}
