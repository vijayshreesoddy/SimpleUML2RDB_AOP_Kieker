package org.eclipse.m2m.qvt.oml.examples.blackbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class QVToTransformer {

	//insert the path to your .qvto file here (including the .qvto file ending)
	private static final String filepath = "";
	
	public static String[][] changes = {
		// .+? matches 1+ of any character, but isn't greedily trying to match the whole document to one pattern
		{"when \\{.+?\\}.+?\\{","//call your log function here"},
	};
	
	public static void main(String[] args) throws IOException {
		if (args.length == 1 && args[0].equals("-prime")) {
			transformRules(true);
		} else if (args.length == 1 && args[0].equals("-defuse")) {
			transformRules(false);
		} else {
			System.out.println("Usage:\n\t-prime to apply changes to qvto file\n\t-defuse to revert back");
		}
	}
	
	private static void transformRules(boolean prime) throws IOException {
		File qvtoFile = new File(filepath);
		BufferedReader qvtoReader = new BufferedReader(new FileReader(qvtoFile));
		String qvtoContent = "";
		String line = qvtoReader.readLine();
		while (line != null) {
			qvtoContent += line+"\n";
			line = qvtoReader.readLine();
		}
		qvtoReader.close();
		
		if (prime) {
			for (String[] aChange : changes) {
				qvtoContent = Pattern.compile("("+aChange[0]+")", Pattern.DOTALL)
					.matcher(qvtoContent)
					.replaceAll("$1\n//primed\n"+aChange[1]+"\n//primedEnd");
			}
		} else {
			qvtoContent = Pattern.compile("\n//primed.+?//primedEnd", Pattern.DOTALL)
				.matcher(qvtoContent)
				.replaceAll("");
		}
		
		FileWriter qvtoWriter = new FileWriter(qvtoFile);
		qvtoWriter.write(qvtoContent);
		qvtoWriter.close();
	}

}
