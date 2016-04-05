package gr.stathis.week3;

/**
 * Write a description of class LogAnalyzer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class LogAnalyzer {

    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        records = new ArrayList<LogEntry>();
    }

    public void readFile() {
        FileResource fr = new FileResource();
        for (String line : fr.lines()) {
            LogEntry le = WebLogParser.parseEntry(line);
            records.add(le);
        }

    }

    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }

    public int countUniqueIPs() {
        HashMap<String, Integer> mapIPs = new HashMap<>();
        for (LogEntry le : records) {
            String addIP = le.getIpAddress();
            if (!mapIPs.containsKey(addIP)) {
                mapIPs.put(addIP, 1);//first time
            } else {

                mapIPs.put(addIP, mapIPs.get(addIP) + 1);//every next time
            }
        }
        return mapIPs.size();
    }

    public void printAllHigherThanNum(int num) {
        for (LogEntry le : records) {
            int temp = le.getStatusCode();
            if (temp > num) {
                System.out.println(temp);
            }
        }
    }

    public int uniqueIPVisitsOnDay(String someday) {
        ArrayList<String> recordsWithDay = new ArrayList<String>();
        for (LogEntry le : records) {
            String ipAdd = le.getIpAddress();
            String str = le.getAccessTime().toString().substring(4, 10);
            if (str.equals(someday) && !recordsWithDay.contains(ipAdd)) {
                recordsWithDay.add(ipAdd);
            }
        }
        return recordsWithDay.size();
    }

    public int countUniqueIPsInRange(int low, int high) {
        ArrayList<String> recordsInRange = new ArrayList<String>();
        for (LogEntry le : records) {
            String ipAdd = le.getIpAddress();
            int status = le.getStatusCode();
            if (status >= low && status <= high && !recordsInRange.contains(ipAdd)) {
                recordsInRange.add(ipAdd);
            }
        }
        return recordsInRange.size();
    }

    /**
     * The method countVisitsPerIP , which has no parameters. This method
     * returns a HashMap<String, Integer> that maps an IP address to the number
     * of times that IP address appears in records , meaning the number of times
     * this IP address visited the website. Recall that records stores LogEntrys
     * from a file of web logs.
     *
     * @return
     */
    public HashMap<String, Integer> countVisitsPerIP() {
        HashMap<String, Integer> mapCountVisPerIP = new HashMap<String, Integer>();
        for (LogEntry le : records) {
            String ipAdd = le.getIpAddress();
            if (!mapCountVisPerIP.containsKey(ipAdd)) {
                mapCountVisPerIP.put(ipAdd, 1);
            } else {
                mapCountVisPerIP.put(ipAdd, mapCountVisPerIP.get(ipAdd) + 1);
            }
        }
        return mapCountVisPerIP;
    }

    /**
     * The method mostNumberVisitsByIP , which has one parameter, a
     * HashMap<String, Integer> that maps an IP address to the number of times
     * that IP address appears in the web log file. This method returns the
     * maximum number of visits to this website by a single IP address.
     *
     * @param map
     * @return This method returns the maximum number of visits to this website
     * by a single IP address
     */
    public int mostNumberVisitsByIP(HashMap<String, Integer> map) {
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry.getValue();
    }

    /**
     * The method iPsMostVisits , which has one parameter, a
     * HashMap<String, Integer> that maps an IP address to the number of times
     * that IP address appears in the web log file. This method returns an
     * ArrayList of Strings of IP addresses that all have the maximum number of
     * visits to this website. For example, the call iPsMostVisits on a HashMap
     * formed using the file weblog3short_log returns the ArrayList with these
     * two IP addresses, 61.15.121.171 and 84.133.195.161. Both of them visited
     * the site three times, which is the maximum number of times any IP address
     * visited the site.
     *
     * @param map
     * @return ArrayList
     */
    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map) {
        int maxValue = mostNumberVisitsByIP(map);
        ArrayList<String> recordsList = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxValue) {
                recordsList.add(entry.getKey());
            }
        }
        return recordsList;
    }

    /**
     * The method iPsForDays , which has no parameters. This method returns a
     * HashMap<String, ArrayList<String>> that uses records and maps days from
     * web logs to an ArrayList of IP addresses that occurred on that day. A day
     * is in the format “MMM DD” where MMM is the first three characters of the
     * month name with the first letter capital and the others in lowercase, and
     * DD is the day in two digits (examples are “Dec 05” and “Apr 22”). For
     * example, for the file weblog3short_log , after building this HashMap, if
     * you print it out, you will see that Sep 14 maps to one IP address, Sep 21
     * maps to four IP addresses, and Sep 30 maps to five IP addresses.
     *
     * @return
     */
    public HashMap<String, ArrayList<String>> iPsForDays() {
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (LogEntry le : records) {
            ArrayList<String> recordsList = new ArrayList<String>();
            String s = le.getAccessTime().toString().substring(4, 10);
            for (LogEntry leIn : records) {
                String sIn = leIn.getAccessTime().toString().substring(4, 10);
                String ipAdd = leIn.getIpAddress();
                if (sIn.equals(s) && (!recordsList.contains(ipAdd))) {
                    recordsList.add(ipAdd);
                }
            }
            map.put(s, recordsList);
        }
        return map;
    }

    /**
     * The method dayWithMostIPVisits , which has one parameter that is a
     * HashMap<String, ArrayList<String>> that uses records and maps days from
     * web logs to an ArrayList of IP addresses that occurred on that day. This
     * method returns the day that has the most IP address visits. If there is a
     * tie, then return any such day. For example, if you use the file
     * weblog3short_log , then this method should return the day most visited as
     * Sep 30.
     *
     * @param map
     * @return the day that has the most IP address visits
     */
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
        HashMap<String, Integer> myMap = new HashMap<String, Integer>();
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            String day = entry.getKey();
            int numIPs = entry.getValue().size();
            myMap.put(day, numIPs);
        }
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : myMap.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    /**
     * The method iPsWithMostVisitsOnDay , which
     * has two parameters—the first one is a HashMap<String, ArrayList<String>>
     * that uses records and maps days from web logs to an ArrayList of IP
     * addresses that occurred on that day, and the second parameter is a String
     * representing a day in the format “MMM DD” described above. This method
     * returns an ArrayList<String> of IP addresses that had the most accesses
     * on the given day. For example, if you use the file weblog3short_log , and
     * the parameter for the day is “Sep 30”, then there are two IP addresses in
     * the ArrayList returned: 61.15.121.171 and 177.4.40.87. Hint: This method
     * should call another method you have written.
     *
     * @param map records and maps days from web logs to an ArrayList of IP
     * addresses that occurred on that day
     * @param someday day in the format “MMM DD”
     * @return ArrayList<String> of IP addresses that had the most accesses on
     * the given day
     */
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String someday) {
        ArrayList<String> lista = null;
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            String day = entry.getKey();
            if (day.equals(someday)) {
                lista = entry.getValue();
            }
        }
        return lista;
    }
}
