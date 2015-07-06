package com.twitter.analyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Arun Velagapalli This class provides main function and basic input,
 *         output file fields, file processing methods.
 * 
 *         For each tweet (line) WordCount.countwords() is called to update
 *         current counts of words, For each tweet (line)
 *         UniqueWords.getMedian() is called to update the current median, and
 *         appended to writeMedian() function
 * 
 *         When input file (tweets file) reading is complete , writeWordCount()
 *         is called to print all the words and their counts.
 * 
 */
public class TweetManager {
	private String inputFileName; // File input name for tweeets
	private String wordCountOutputFile; // output file for word count
	private String medianOutputFile; // output file for unique median

	public TweetManager() {

	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void setTweetsFile(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getInputTweetsFile() {
		return this.inputFileName;
	}

	public String getWordCountOutputFile() {
		return wordCountOutputFile;
	}

	public void setWordCountOutputFile(String wordCountOutputFile) {
		this.wordCountOutputFile = wordCountOutputFile;
	}

	public String getMedianOutputFile() {
		return medianOutputFile;
	}

	public void setMedianOutputFile(String medianOutputFile) {
		this.medianOutputFile = medianOutputFile;
	}

	/**
	 * appends current median to file
	 * 
	 * @param median
	 * @param out
	 *            OutputStreamWriter
	 * @throws Exception
	 */
	private void writeMedian(Double median, OutputStreamWriter out) throws Exception {
		try {
			BufferedWriter bw = new BufferedWriter(out);
			bw.append(median.toString());
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			throw new Exception(e.toString());
		}

	}

	/**
	 * Writes the counts of words map to specified outputstream.
	 * 
	 * @param out
	 *            OutputStreamWriter
	 * @throws Exception
	 */

	private void writeWordCount(Map<String, Integer> wordCountMap, OutputStreamWriter out) throws Exception {
		try {
			BufferedWriter bw = new BufferedWriter(out);
			for (Map.Entry<String, Integer> en : wordCountMap.entrySet()) {
				bw.write(en.getKey() + "\t\t\t\t\t" + en.getValue().toString());
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			throw new Exception(e.toString());
		}

	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		TweetManager tmgr = new TweetManager();

		// if running through eclipse IDE we should remove relative path
		// like tmgr.setInputFileName("tweet_input/tweets.txt");

		tmgr.setInputFileName("../tweet_input/tweets.txt");
		tmgr.setWordCountOutputFile("../tweet_output/ft1.txt");
		tmgr.setMedianOutputFile("../tweet_output/ft2.txt");

		WordCounter wcounter = new WordCounter();
		UniqueMedian umedian = new UniqueMedian();
		String line = "";
		Set<String> uniqueWords = new HashSet<String>();

		// setting output file stream objects, input filestream object
		File f1 = new File(tmgr.getWordCountOutputFile());
		File f2 = new File(tmgr.getMedianOutputFile());
		File f3 = new File(tmgr.getInputFileName());
		FileOutputStream fout1, fout2;
		FileInputStream fin = null;
		OutputStreamWriter out1, out2;
		try {
			// each time we run .sh script new output files are created to write
			// outputs

			if (!f1.exists()) {
				f1.createNewFile();
			} else if (f1.exists()) {
				f1.delete();
				f1.createNewFile();
			}
			if (!f2.exists()) {
				f2.createNewFile();
			} else if (f2.exists()) {
				f2.delete();
				f2.createNewFile();
			}
			fout1 = new FileOutputStream(f1);
			fout2 = new FileOutputStream(f2);
			out1 = new OutputStreamWriter(fout1);
			out2 = new OutputStreamWriter(fout2);

		} catch (Exception e) {
			throw new Exception(e.toString());
		}

		// read input file line by line (tweet) and process for word count and
		// unique median
		try {
			fin = new FileInputStream(f3);
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			while ((line = br.readLine()) != null) {
				String[] words = line.split("\\s");
				wcounter.countWords(words);
				for (String word : words) {
					// to make unique words put the words into a Set data
					// structure
					uniqueWords.add(word);
				}

				// compute current median for new uniquewords length
				Double currentMedian = umedian.getMedian(uniqueWords.size());
				// write current median value to file
				tmgr.writeMedian(currentMedian, out2);
				uniqueWords.clear();
			}
			Map<String, Integer> wordCountMap = wcounter.getWordCount();
			tmgr.writeWordCount(wordCountMap, out1);
		} catch (Exception e) {
			throw new Exception(e.toString());
		} finally {
			try {
				fout1.close(); // close all outputstreams finally
				fout2.close();
				fin.close();
			} catch (Exception e) {
				throw new Exception(e.toString());
			}

		}

	}
}
