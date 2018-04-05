/**
 * Simple diary program
 * @author Ankat
 * @version 0.1
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DuckDiaries {
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String userAction = null;
		final Scanner systemInReader = new Scanner(System.in);
		
		final File diaryFile = new File("diary.txt");
		// check if file exists; create file if doesn't exist
		if(!diaryFile.exists()) {
			diaryFile.createNewFile();
		}		
		
		printBanner();
		
		do {
			try {
				userAction = queryAction(systemInReader);
			}
			catch(InputMismatchException err) {
				System.out.println("ERROR: " + err.getMessage());
			}
		} while(userAction == null);
		
		readFile(diaryFile);
		readFile(diaryFile);
		
		// in the end: close scanners 
		systemInReader.close();
	}
	
	private static void readFile(File txt) throws FileNotFoundException {
		Scanner fileReader = new Scanner(txt);
		String line = "";
		
		while (fileReader.hasNext()) {
			line = fileReader.nextLine();
			System.out.println(line);			
		}
		fileReader.close();
	}
	
	/**
	 * Prints the startup banner
	 */
	
	private static void printBanner() {
		System.out.println("*************************");
		System.out.println("***   Duck Diaries   ****");
		System.out.println("*************************");
		System.out.println();
	}
	
	/**
	 * Queries user input which feature to use
	 * @return user input
	 * @throws Exception 
	 * @throws InputMismatchException invalid input from user
	 */
	public static String queryAction(Scanner scanr) throws InputMismatchException {
		System.out.print("Insert action [write, read or quit]: ");
		if(!(scanr.hasNext("write") || scanr.hasNext("read") || scanr.hasNext("quit"))) {
			scanr.nextLine(); //consume invalid input
			throw new InputMismatchException("invalid input");
		}
		return scanr.nextLine();
	}
}
