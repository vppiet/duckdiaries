/**
 * Simple diary program
 * @author Ankat
 * @version 0.1
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class DuckDiaries {
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String userAction = "";
		final Scanner systemInReader = new Scanner(System.in);
		final File diaryFile = new File("diary.txt");
		
		//main commands in array
		ArrayList<String> mainCmds = new ArrayList<String>();
		mainCmds.add("read");
		mainCmds.add("write");
		mainCmds.add("quit");
		
		printBanner();
		
		do {
			try {
				userAction = queryAction(systemInReader, mainCmds);
			}
			catch(InputMismatchException err) {
				System.out.println("ERROR: " + err.getMessage());
			}
		} while(!userAction.equals("quit"));
		
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
	private static String queryAction(Scanner scanr, ArrayList<String> cmds) throws InputMismatchException {
		String readAction = "";
		String actions = String.join(", ", cmds); //stringify commands for desired print format
		System.out.print("Select action [" + actions + "]: ");
		
		if(scanr.hasNextLine()) {
			readAction = scanr.nextLine();
			//invalid command inserted by user
			if(!cmds.contains(readAction)) {
				throw new InputMismatchException("invalid input");
			}
		}

		return readAction;
	}
}
