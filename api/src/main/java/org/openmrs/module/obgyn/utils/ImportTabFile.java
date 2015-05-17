package org.openmrs.module.obgyn.utils;

import java.io.*;

public class ImportTabFile {

	private String filename;
	private String encoding;
	private String encoding2;

	public ImportTabFile(String filename, String encoding, String encoding2) {
		this.filename = filename;
		this.encoding = encoding;
		this.encoding2 = encoding2;
	}

	public void readFile() {

		int countLines = 0;
		int countMatches = 0;
		String regex = "([0-9]+)(\\w)([0-9]+)(.+?)";
		try {
			 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("foo.txt"), encoding2)));

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
			String line = null;
			String prevLine = "";
			while ((line = reader.readLine()) != null) {

				if (line.matches(regex)) {
					out.println(prevLine);
					prevLine = "";
					countMatches++;
				} 

				if (countLines > 0)
					prevLine = prevLine +"\t" + line;
				else
					prevLine = line;

				countLines++;
			}
			out.flush();
			out.close();
			reader.close();

		} catch (Exception x) {
			System.err.format("IOException: %s%n", x);
		}
		System.out.println("Counted " + countLines + " lines");
		System.out.println("Matches " + countMatches + " lines");
	}

	public static void main(String[] args) {
		ImportTabFile importTabFile = new ImportTabFile(args[0], args[1], args[2]);
		importTabFile.readFile();
		System.out.println("finished reading");
	}
}
