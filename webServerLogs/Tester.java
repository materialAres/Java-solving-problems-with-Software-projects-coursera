import java.util.Date;

public class Tester {
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le.getLogInfo());
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2.getLogInfo());
    }

    public void testLogAnalyzer() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("data/weblog2_log");
        //analyzer.printAll();

        //System.out.println(analyzer.countUniqueIPs() + "\n");

        //analyzer.printAllHigherThanNum(400);
        //System.out.println();

        //System.out.println(analyzer.uniqueIPVisitsOnDay("Sep 24").size() + "\n");

        //System.out.println(analyzer.countUniqueIPsInRange(400,499));
        //System.out.println(analyzer.countVisitsPerIP() + "\n");
        //System.out.println(analyzer.mostNumberVisitsByIP(analyzer.countVisitsPerIP())  + "\n");

        System.out.println(analyzer.iPsMostVisits(analyzer.countVisitsPerIP()) + "\n");

        //System.out.println(analyzer.iPsForDays() + "\n");

        //System.out.println(analyzer.dayWithMostIPVisits(analyzer.iPsForDays()));

        System.out.println(analyzer.iPsWithMostVisitsOnDay(analyzer.iPsForDays(), "Sep 30"));
    }

    public static void main(String[] args) {
        Tester test = new Tester();
        test.testLogAnalyzer();
    }
}
