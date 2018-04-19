import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A simple program with which user can read and write diary.
 * Program uses a text file to record diary entries from user.
 * 
 * <p>In-program commands include:
 *  <ul>
 *   <li><code>create</code> - create file</li>
 *   <li><code>read</code> - read file</li>
 *   <li><code>write</code> - write into file</li>
 *   <li><code>quit</code> - exit program</li>
 *  </ul>
 * 
 * <p>Made for a programming course assignment in
 *  Turku University of Applied Sciences.
 * 
 * @author Ankat
 * @version 0.1
 */

public class DuckDiaries {
	
	/**
	 * Main method
	 * 
	 * @param args		not used
	 */
	public static void main(String[] args) {
		// user input scanner must be declared here because used as an argument in multiple cases
		final Scanner systemInReader = new Scanner(System.in);
		
		final File diaryFile = new File("diary.txt");
		
		// main commands for passing to print available tasks and validate user input
		ArrayList<String> mainCmds = new ArrayList<String>();
		mainCmds.add("read");
		mainCmds.add("write");
		mainCmds.add("quit");
		
		startupBanner();
		startupDiaryExist(diaryFile);		// inform user if a diary file exists and how to create it
		
		// MAIN LOOP
		String userTask = "";
		do {
			// check if diary file exists and add/remove create command based on this info
			if(!diaryFile.exists()) {
				if(!mainCmds.contains("create")) {
					mainCmds.add(0, "create");
				}
			} else {
				if(mainCmds.contains("create")) {
					mainCmds.remove("create");
				}
			}
			
			// begin querying user which task to run
			try {
				userTask = queryTask(systemInReader, mainCmds);
				
				// create
				if(mainCmds.contains("create") && userTask.equals("create")) {
					createDiary(diaryFile);
				}
				// read
				if(userTask.equals("read")) {
					readDiary(diaryFile);
				}
				
				// write
				if(userTask.equals("write")) {
					writeDiary(systemInReader, diaryFile);
				}
			}
			// user entered invalid command
			catch(InputMismatchException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		} while(!userTask.equals("quit"));
		
		System.out.println("See you later!");
		
		// close user input scanner before shutdown
		systemInReader.close();
	}
	
	/**
	 * Prints a banner when program is run.
	 */
	public static void startupBanner() {
		System.out.println("                  _");
		System.out.println("   Duck Diaries >(')____");
		System.out.println("                 (` =7  /");
		System.out.println("^~^~^~^~^~^~^~~^~^`---'~^~^~^~");
		System.out.println("");
	}
	
	/**
	 * Informs user about used diary file. 
	 * @param f			the diary file
	 */
	public static void startupDiaryExist(File f) {
		if(f.exists()) {
			System.out.println("Found existing diary file (" + f.getName() + ").");
		}
		else {
			System.out.println("Diary file (" + f.getName() + ") was not found. Use [create] command to initialize one.");
		}
	}
	
	/**
	 * Creates a diary file.
	 * @param f				 		the diary file
	 * @return <code>true</code>	if the file was created successfully
	 */
	public static boolean createDiary(File f) {
		System.out.println("Attempting to create a diary file (" + f.getName() + ")...");
		
		try {
			if(f.createNewFile()) {
				System.out.println("File created successfully!");
				return true;
			} else {
				System.out.println("ERROR: Something went wrong.");
				return false;
			}
		}
		catch(IOException e) {
			System.out.println("ERROR: " + e);
			return false;
		}
	}
	
	/**
	 * Returns a valid command chosen by the user.
	 * @param scanr						the user input scanner
	 * @param cmds						valid commands
	 * @return <code>String</code>		the command chosen by the user
	 * @throws InputMismatchException	if input does not match valid commands
	 */
	public static String queryTask(Scanner scanr, ArrayList<String> cmds) throws InputMismatchException {
		String readTask = "";
		String cmdsStringed = String.join(", ", cmds); //stringify commands for desired print format
		System.out.print("> Select task [" + cmdsStringed + "]: ");
		
		if(scanr.hasNextLine()) {
			readTask = scanr.nextLine();
			//invalid command inserted by user
			if(!cmds.contains(readTask)) {
				throw new InputMismatchException("Invalid input");
			}
		}

		return readTask;
	}
	
	/**
	 * Reads and prints the contents of the diary file
	 * @param f	the diary file
	 */
	public static void readDiary(File f) {
		try {
			Scanner fileReader = new Scanner(f);
			String line = "";
			int lineNumber = 0;
			
			if(f.length() > 0) {
				System.out.println("");
				while (fileReader.hasNext()) {
					line = fileReader.nextLine();
					lineNumber++;
					System.out.println(line);			
				}
				System.out.println("");
				System.out.println("Total rows:\t" + lineNumber);
				System.out.println("File size:\t" + f.length() + " bytes");
			}
			else {
				System.out.println("Diary file is empty.");
			}
			fileReader.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("ERROR: Diary file (" + f.getName() + ") not found");
		}
	}
	
	/**
	 * Gets current date and time.
	 * @return <code>String</code>	current date and time in "yyyy-MM-dd HH:mm:ss" format
	 */
	public static String getCurrentTimestamp() {
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return today.format(formatter);
	}
	
	/**
	 * Writes diary file with user input.
	 * @param scanr			the user input scanner
	 * @param f				the diary file
	 */
	public static void writeDiary(Scanner scanr, File f) {
		try {
			if(f.exists()) {
				// FileWriter class uses boolean value for second appending argument
				FileWriter writer = new FileWriter(f, true);
				String readLine = "";
				
				//write a separator line into the file if this is the first entry
				if(f.length() == 0) {
					writer.write("--------------------\r\n");
				}
				
				//start of entry
				writer.write(getCurrentTimestamp() + "\r\n");
				System.out.println("New entry for timestamp: " + getCurrentTimestamp());
				do {
					System.out.print("> Enter line(s) or [end]: ");
					readLine = scanr.nextLine();
					if(!readLine.equals("end")) {
						writer.write(readLine + "\r\n");
					}
				} while(!readLine.equals("end"));
				//end of entry
				writer.write("--------------------\r\n");
				writer.close();
			} else {
				System.out.println("ERROR: Diary file (" + f.getName() + ") not found.");
			}
		}
		catch(IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}
