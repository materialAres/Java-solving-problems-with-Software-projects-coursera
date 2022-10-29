import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        records = new ArrayList<>();
    }

    public ArrayList<LogEntry> getRecords() {
        return records;
    }

    public void readFile(String filename) {
        // reads each log in a file and adds it in an array list
        records.clear();
        FileResource file = new FileResource(filename);

        for (String line : file.lines()) {
            LogEntry logEntry = WebLogParser.parseEntry(line);
            records.add(logEntry);
        }
    }

    public void printAll() {
        // prints all the web log records in the list
        for (LogEntry le : records) {
            System.out.println(le.getLogInfo());
        }
    }

    public int countUniqueIPs() {
        // counts how many unique IPs are in the list
        ArrayList<String> uniqueIPs = new ArrayList<>();

        for (LogEntry logEntry : records) {
            String currentIP = logEntry.getIpAddress();

            if (!uniqueIPs.contains(currentIP))
                uniqueIPs.add(currentIP);
        }
        return uniqueIPs.size();
    }

    public void printAllHigherThanNum(int num) {
        // prints the web log with status code greater than the one specified by 'num'
        for (LogEntry logEntry : records) {
            int currentStatusCode = logEntry.getStatusCode();

            if (currentStatusCode > num)
                System.out.println(logEntry.getLogInfo());
        }
    }

    public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
        // counts how many unique IPs are there in a specific day, returning them
        ArrayList<String> uniqueIPs = new ArrayList<>();

        for (LogEntry logEntry : records) {
            String currentIP = logEntry.getIpAddress();
            String currentDate = logEntry.getAccessTime().toString();

            if (!uniqueIPs.contains(currentIP) && currentDate.contains(someday))
                uniqueIPs.add(currentIP);
        }
        return uniqueIPs;
    }

    public int countUniqueIPsInRange(int low, int high) {
        // counts unique IPs whose status code is between 'low' and 'high' inclusive
        ArrayList<String> uniqueIPsInRange = new ArrayList<>();

        for (LogEntry logEntry : records) {
            int currentStatusCode = logEntry.getStatusCode();
            String currentIP = logEntry.getIpAddress();

            if (currentStatusCode >= low && currentStatusCode <= high && !(uniqueIPsInRange.contains(currentIP)))
                uniqueIPsInRange.add(currentIP);
        }
        return uniqueIPsInRange.size();
    }

    public HashMap<String, Integer> countVisitsPerIP() {
        // counts how many visits come from each IP
        HashMap<String, Integer> visitsCounterPerIP = new HashMap<>();

        for (LogEntry record : records) {
            String currentIP = record.getIpAddress();

            if (!visitsCounterPerIP.containsKey(currentIP))
                visitsCounterPerIP.put(currentIP, 1);
            else
                visitsCounterPerIP.put(currentIP, visitsCounterPerIP.get(currentIP) + 1);
        }
        return visitsCounterPerIP;
    }

    public int mostNumberVisitsByIP(HashMap<String, Integer> visitsPerIP) {
        // returns the higher number of visits from a single IP, without specifying the IP
        int maxNumberOfVisits = Integer.MIN_VALUE;

        for (Integer visits : visitsPerIP.values()) {
            if (visits > maxNumberOfVisits) {
                maxNumberOfVisits = visits;
            }
        }
        return maxNumberOfVisits;
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> visitsPerIP) {
        // returns a list IPs which have visited the site more often
        int maxNumberOfVisits = mostNumberVisitsByIP(visitsPerIP);
        ArrayList<String> iPsWithMostVisits = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : visitsPerIP.entrySet()) {
            String currentIP = entry.getKey();
            int currentNumberOfVisits = entry.getValue();

            if (currentNumberOfVisits == maxNumberOfVisits) {
                iPsWithMostVisits.add(currentIP);
            }
        }
        return iPsWithMostVisits;
    }

    public HashMap<String, ArrayList<String>>  iPsForDays() {
        // returns a map which couples a day with a list of IPs that visited the site on that day
        HashMap<String, ArrayList<String>> iPsForDifferentDays = new HashMap<>();

        for (LogEntry entry : records) {
            String currentDate = String.valueOf(entry.getAccessTime()).substring(4, 10);
            String currentIP = entry.getIpAddress();
            ArrayList<String> currentIPList = new ArrayList<>();

            if (!iPsForDifferentDays.containsKey(currentDate)) {
                currentIPList.add(currentIP);
                iPsForDifferentDays.put(currentDate, currentIPList);
            } else {
                iPsForDifferentDays.get(currentDate).add(currentIP);
            }

        }
        return iPsForDifferentDays;
    }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> IPsForDays) {
        // returns the most visited day
        int maxNumberOfVisitsInDay = Integer.MIN_VALUE;
        String dayWithMaxVisits = null;

        for (Map.Entry<String, ArrayList<String>> entry : IPsForDays.entrySet()) {
            ArrayList<String> currentIPList = entry.getValue();
            int currentVisitsInDay = currentIPList.size();

            if (currentVisitsInDay > maxNumberOfVisitsInDay) {
                maxNumberOfVisitsInDay = currentVisitsInDay;
                dayWithMaxVisits = entry.getKey();
            }
        }
        return  dayWithMaxVisits;
    }

    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> IPsForDays, String date) {
        // returns a list of IP addresses that had the most accesses on the given day
        ArrayList<String> mostVisitedIPsOnDay = new ArrayList<>();
        HashMap<String, Integer> visitsPerIP = new HashMap<>();

        for (Map.Entry<String, ArrayList<String>> entry : IPsForDays.entrySet()) {
            String currentDate = entry.getKey();

            if (currentDate.equals(date)) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    String currentIP = entry.getValue().get(i);

                    if (!visitsPerIP.containsKey(currentIP))
                        visitsPerIP.put(currentIP, 1);
                    else
                        visitsPerIP.put(currentIP, visitsPerIP.get(currentIP) + 1);
                }
                int mostNumberVisitsByIP = mostNumberVisitsByIP(visitsPerIP);

                for (Map.Entry<String, Integer> secondEntry : visitsPerIP.entrySet()) {
                    if (secondEntry.getValue() == mostNumberVisitsByIP) {
                        mostVisitedIPsOnDay.add(secondEntry.getKey());
                    }
                }
            }
        }
        return mostVisitedIPsOnDay;
    }
}