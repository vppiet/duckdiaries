/**
 * Simple diary program
 * @author Ankat
 * @version 0.1
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class DuckDiaries {
	
	public static void main(String[] args) throws Exception {
		// user input scanner must be declared here because used as an argument in multiple cases
		final Scanner systemInReader = new Scanner(System.in);
		// file setup
		final File diaryFile = new File("diary.txt");
		
		// main commands
		ArrayList<String> mainCmds = new ArrayList<String>();
		mainCmds.add("read");
		mainCmds.add("write");
		mainCmds.add("quit");
		
		printBanner();
		
		// main loop
		String userTask = "";
		do {
			try {
				userTask = queryTask(systemInReader, mainCmds);
				//read
				if(userTask.equals(mainCmds.get(0))) {
					try {
						readDiary(diaryFile);
					}
					catch(FileNotFoundException err) {
						System.out.println("ERROR: file not found");
						System.out.println("Creating diary file...");
						if(diaryFile.createNewFile()) {
							System.out.println("File created (diary.txt)");
						} else {
							System.out.println("Something went wrong while creating the diary file.");
						}
					}
				}
				//write
				if(userTask.equals(mainCmds.get(1))) {
					writeDiary(systemInReader, diaryFile);
				}
			}
			catch(InputMismatchException err) {
				System.out.println("ERROR: " + err.getMessage());
			}
		} while(!userTask.equals("quit"));
		
		System.out.println("See you later!");
		// in the end, close user input scanner
		systemInReader.close();
	}

	
	// Prints banner
	private static void printBanner() {
		System.out.println("******************_ ");
		System.out.println("***Duck Diaries >(')____");
		System.out.println("*****************(` =7  / ");
		System.out.println("^~^~^~^~^~^~^~~^~^`---'~^~ ");
	}
	
	
	// Asks user's choice of task and returns it
	private static String queryTask(Scanner scanr, ArrayList<String> cmds) throws InputMismatchException {
		String readTask = "";
		String cmdsStringed = String.join(", ", cmds); //stringify commands for desired print format
		System.out.print("> Select task [" + cmdsStringed + "]: ");
		
		if(scanr.hasNextLine()) {
			readTask = scanr.nextLine();
			//invalid command inserted by user
			if(!cmds.contains(readTask)) {
				throw new InputMismatchException("invalid input");
			}
		}

		return readTask;
	}
	
	
	// Prints file contents 
	private static void readDiary(File f) throws FileNotFoundException {
		Scanner fileReader = new Scanner(f);
		String line = "";
		
		while (fileReader.hasNext()) {
			line = fileReader.nextLine();
			System.out.println(line);			
		}
		fileReader.close();
	}
	
	
	// Returns current date & time
	private static String getCurrentTimestamp() {
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return today.format(isoFormatter);
	}
	
	
	// Writes user input to file 
	private static void writeDiary(Scanner scanr, File f) throws IOException {
		// FileWriter class uses boolean value for second appending argument
		FileWriter writer = new FileWriter(f, true);
		String readLine = "";
		
		//start of entry
		writer.write(getCurrentTimestamp() + "\r\n");
		System.out.println("New entry for date: " + getCurrentTimestamp());
		do {
			System.out.print("> Enter line(s) or [end]: ");
			if(scanr.hasNext()) {
				readLine = scanr.nextLine();
				if(!readLine.equals("end")) {
					writer.write(readLine + "\r\n");
				}
			}
		} while(!readLine.equals("end"));
		//end of entry
		writer.write("----------\r\n\r\n");
		writer.close();
	}
}
