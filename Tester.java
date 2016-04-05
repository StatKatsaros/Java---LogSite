package gr.stathis.week3;

/**
 * Write a description of class Tester here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;

public class Tester {

    public static void main(String[] args) {
        Tester ts = new Tester();
        ts.testLogAnalyzer();

    }

    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }

    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile();
        la.printAll();
        int uniq = la.countUniqueIPs();
        System.out.println("The unique ips are: " + uniq);
        la.printAllHigherThanNum(400);
        int uniqIpWithDay = la.uniqueIPVisitsOnDay("Sep 24");
        System.out.println("Unique Ip per Day: " + uniqIpWithDay);
        int statCode = la.countUniqueIPsInRange(200, 299);
        System.out.println("Unique Ip per Range: " + statCode);
        HashMap<String,Integer> counts = la.countVisitsPerIP();
        System.out.println("Hashmap is: "+counts);
        int maxi = la.mostNumberVisitsByIP(counts);
        System.out.println("The most number visits per IPs is: " + maxi);
        ArrayList<String> lista = la.iPsMostVisits(counts);
        System.out.println("and the IPs are: "+ lista);
        HashMap<String, ArrayList<String>> map = la.iPsForDays();
        System.out.println("The map is: "+map);
        String day = la.dayWithMostIPVisits(map);
        System.out.println("The day with the most visited IPs is: "+ day);
        ArrayList<String> listb = la.iPsWithMostVisitsOnDay(map, "Sep 29");
        System.out.println(listb);
        

    }

    
}
