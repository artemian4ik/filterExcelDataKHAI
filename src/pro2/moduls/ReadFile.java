package pro2.moduls;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile extends Thread {

	ArrayList <DataFile> listDataFile = new ArrayList();
	String pathFile = ""; 
	
	public ReadFile(String pathFile, String data) {
		super(data);
		ClearDataFile();
		this.pathFile = pathFile;
	}
	
	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';
	
	public void run() {
		try {
			Scanner scanner = new Scanner(new File(pathFile));
			while (scanner.hasNext()) {
				List line = parseLine(scanner.nextLine());
				listDataFile.add(new DataFile(line.get(0).toString(), line.get(1).toString(), line.get(2).toString(), line.get(3).toString(),
						line.get(4).toString(), line.get(5).toString(), line.get(6).toString(), line.get(7).toString(), line.get(8).toString(),
						line.get(9).toString(), line.get(10).toString(), line.get(11).toString(), line.get(12).toString(), line.get(13).toString(),
						line.get(14).toString(), line.get(15).toString(), line.get(16).toString(), line.get(17).toString(), line.get(18).toString(),
						line.get(19).toString(), line.get(20).toString()));
				//System.out.println("Country [id= " + line.get(0) + ", code= " + line.get(1) + " , name=" + line.get(2) + " , name=" + line.get(3) + " , name=" + line.get(4) +"]");
			}
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			
		}
		
		System.out.println(Thread.currentThread().getName() + " finished...");
	}

	public static List parseLine(String cvsLine) {
		return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
	}
	
	public static List parseLine(String cvsLine, char separators) {
		return parseLine(cvsLine, separators, DEFAULT_QUOTE);
	}

	public static List parseLine(String cvsLine, char separators, char customQuote) {

		List<String> result = new ArrayList<>();
		
		if (cvsLine == null && cvsLine.isEmpty()) {
			return result;
		}

		if (customQuote == ' ') {
			customQuote = DEFAULT_QUOTE;
		}

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		char[] chars = cvsLine.toCharArray();

		for (char ch : chars) {

			if (inQuotes) {
				startCollectChar = true;
				if (ch == customQuote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {
					if (ch == '"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}

				}
			} else {
				if (ch == customQuote) {

					inQuotes = true;

					if (chars[0] != '"' && customQuote == '"') {
						curVal.append('"');
					}

					if (startCollectChar) {
						curVal.append('"');
					}

				} else if (ch == separators) {

					result.add(curVal.toString());

					curVal = new StringBuffer();
					startCollectChar = false;

				} else if (ch == 'r') {
		
					continue;
				} else if (ch == 'n') {
					break;
				} else {
					curVal.append(ch);
				}
			}
		}

		result.add(curVal.toString());
		return result;
	}
	
	public ArrayList<DataFile> GetListDataFile(){
		return listDataFile;
	}
	
	public void ClearDataFile() {
		listDataFile.clear();
	}
}
