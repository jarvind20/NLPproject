
// Joshua A  Steven M
//stanford edu 
//geeksforgeeks.com  slackoverflow.com
import java.util.Properties;

import javax.lang.model.util.ElementScanner6;

import org.ejml.simple.SimpleMatrix;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Sentence {
	String Text;
	String Author;
	String Timestamp;

	public boolean keep(String temporalRange) throws ParseException {
		boolean result = false;
		String testdate_compare = "May 11 2009 - Jun 14 2009";
		String[] dates = testdate_compare.split("-");
		String sd1 = dates[0].trim();
		String sd2 = dates[1].trim();
		System.out.println("sd1 is: " + sd1);
		System.out.println("sd2 is: " + sd2);

		SimpleDateFormat f = new SimpleDateFormat("MMM dd yyyy");
		// Format the dates
		Date startDate = f.parse(sd1);
		Date endDate = f.parse(sd2);
		Date temprange = f.parse(temporalRange);

		System.out.println("startDate is: " + startDate);
		System.out.println("endDate is: " + endDate);

		// Convert the dates to timestamp
		Timestamp timeStampstartDate = new Timestamp(startDate.getTime());
		Timestamp timeStampendDate = new Timestamp(endDate.getTime());
		Timestamp senttimestamp = new Timestamp(temprange.getTime());

		if (senttimestamp.getTime() >= timeStampstartDate.getTime()
				&& senttimestamp.getTime() <= timeStampendDate.getTime()) {
			result = true;
		}
		return result;
	}

	public int getSentiment() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation annotation = pipeline.process(Text);
		CoreMap sentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
		Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
		return RNNCoreAnnotations.getPredictedClass(tree);
	}

	public Sentence(String text, String author, String timestamp) {
		Text = text;
		Author = author;
		Timestamp = timestamp;
		Text = Text.replace(".", "");
		Text = Text.replace("\"", "");
		Text = Text.replace(",", "");
		Author = Author.replace("\"", "");
		Timestamp = Timestamp.replace("\"", "");
	}

	public String getText() { // this function returns Text
		return Text;
	}

	public void setText(String text) { // this function sets Text
		Text = text;

	}

	public String getAuthor() { // this function returns Author
		return Author;
	}

	public void setAuthor(String author) { // this function sets Author
		Author = author;

	}

	public String getTimestamp() { // this function returns Timestamp

		return Timestamp;
	}

	public void setTimestamp(String timestamp) { // this function sets Timestamp
		Timestamp = timestamp;

	}

	public String toString() {
		String str;
		str = "{author:" + getAuthor() + ", sentence:" + '"' + getText() + '"' + ", timestamp:" + '"'
				+ getTimestamp()
				+ '"' + "}";
		return str;
	}

	public static Sentence convertLine(String line) {
		String[] word = line.split("\",\"");
		// System.out.println("In convertLine method : full line is :: " + line);
		// System.out.println("In convertLine method " + word[2]);

		for (int i = 0; i < word.length; i++) {

			if (word[i].charAt(0) == '\"') {
				word[i] = word[i].substring(1, word[i].length());
			}
			if (word[i].charAt(word[i].length() - 1) == '\"') {
				word[i] = word[i].substring(0, word[i].length() - 1);
			}

			word[word.length - 1] = word[word.length - 1].replaceAll("\\.", "");
			word[word.length - 1] = word[word.length - 1].replaceAll(",", "");
			word[word.length - 1] = word[word.length - 1].replaceAll("\"", "");

			// System.out.println(word[i]);
		}

		String t[] = word[2].split(" ");

		// System.out.println("t is " + t);
		String Timestamp = t[1] + " " + t[2] + " " + t[5];
		// System.out.println("Tstamp: " + Timestamp);
		String Author = word[4];
		// System.out.println("author " + Author);

		String Text = word[5];
		// System.out.println("text " + Text);

		for (int i = 6; i < word.length; i++)
			Text = Text + word[i];

		Sentence s = new Sentence(Text, Author, Timestamp);
		// System.out.println("PPPPPPP ------------ " + s);
		return s;
	}

	public ArrayList<String> splitSentence() {
		ArrayList<String> wordlist = new ArrayList<String>();
		String[] stopwords = { "a", "about", "above", "after", "again", "against", "all", "am", "an", "and",
				"any",
				"are",
				"aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between",
				"both", "but",
				"by",
				"can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't",
				"doing", "don't",
				"down",
				"during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't",
				"have", "haven't",
				"having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself",
				"him", "himself",
				"his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is",
				"isn't", "it",
				"it's",
				"its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor",
				"not", "of",
				"off",
				"on", "once", "only", "or", "other", "ought", "our", "ours ourselves", "out", "over",
				"own", "same",
				"shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some",
				"such", "than",
				"that",
				"that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's",
				"these", "they",
				"they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too",
				"under", "until",
				"up",
				"very", "was", "wasn't", "we", "we'd", "we'll", "we're", "we've", "were", "weren't",
				"what", "what's",
				"when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why",
				"why's", "with",
				"won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your",
				"yours", "yourself",
				"yourselves" };
		String[] strar = Text.split(" "); // this splits the sentence
		ArrayList<String> space = new ArrayList<String>();
		space.add("");

		for (int k = 0; k < strar.length; k++) {
			wordlist.add(strar[k].toLowerCase());
		}

		for (int y = 0; y < strar.length; y++) {
			for (int j = 0; j < stopwords.length; j++) {
				if (strar[y] == stopwords[j]) {
					wordlist.set(y, "");

				}
			}

		}

		wordlist.removeAll(space);
		return wordlist;

	}
}
