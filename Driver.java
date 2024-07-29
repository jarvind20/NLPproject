import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Collections;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Driver {

    public static ArrayList<Sentence> mylist = new ArrayList<Sentence>();

    public static void main(String[] args) throws Exception {

        ArrayList<Sentence> jlist = new ArrayList<Sentence>();
        String jliststr = null;
        int count = 0;
        // parsing a CSV file into Scanner class constructor
        try {
            Scanner myscan = new Scanner(
                    new File("testdata.manual.2009.06.14.csv"));

            myscan.useDelimiter(","); // sets the delimiter pattern
            // System.out.println("Delimiter: " + myscan.delimiter());

            while (myscan.hasNext()) { // returns a boolean value
                // System.out.println(myscan.nextLine());
                Sentence sent = Sentence.convertLine(myscan.nextLine());
                mylist.add(sent);

                // System.out.println("Here:" + sent.getText() + "-------The score is: " +
                // sent.getSentiment() + "\n");

                // get timestamp from sentence
                jliststr = (sent.getTimestamp());
                // System.out.println("jliststr is " + jliststr + sent);

                // Check if date falls within the date range
                if (sent.keep(jliststr)) {
                    System.out.println("Added the timestamp: " + jliststr);
                    jlist.add(sent);
                    count = count + 1;
                } else {
                    System.out.println("Inside the else. Not adding the timestamp : " + jliststr);
                }

            }
            System.out.println("New Tweet Array List: " + jlist);
            System.out.println("Total count: " + count);
            myscan.close(); // closes the scanner

        } catch (Exception exp) {
            System.out.println("Error with Scanner");
            exp.printStackTrace();
        }

    }

    public static HashMap<String, Integer> printTopWords(ArrayList<Sentence> sentences) {
        HashMap<String, Integer> wordMap = new HashMap<>();
        ArrayList<String> check = new ArrayList<String>();
        ArrayList<String> fling = new ArrayList<String>();

        for (int i = 0; i < sentences.size(); i++) {
            check = sentences.get(i).splitSentence();
            // System.out.println("check is ---------------" + check);
            for (int j = 0; j < check.size(); j++) {
                fling.add(check.get(j));
                // System.out.println("fling is -----------" + fling);
            }
        }

        String[] cut = new String[fling.size()];

        for (int k = 0; k < fling.size(); k++) {
            cut[k] = fling.get(k);
        }

        for (int j = 0; j < cut.length; j++) {
            if (wordMap.containsKey(cut[j])) {
                wordMap.replace(cut[j], wordMap.get(cut[j]) + 1);
            } else {
                wordMap.put(cut[j], 1);
            }
        }

        /* ___________________________________ */

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : wordMap.entrySet())
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                maxEntry = entry;
        int maxValueLen = maxEntry.getValue().toString().length();
        ArrayList<String> results = new ArrayList<String>();
        for (Map.Entry set : wordMap.entrySet()) {
            String value = set.getValue().toString();
            while (value.length() < maxValueLen)
                value = " " + value;
            results.add(value + " of " + set.getKey());
        }
        Collections.sort(results);
        Collections.reverse(results);

        for (int i = 0; i < results.size() && i < 100; i++) {
            System.out.println(results.get(i));
        }

        return wordMap;

    }

}
