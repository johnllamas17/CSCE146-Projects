/*
 * Written by John Llamas
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readSheepFile 
{
	// Reads from file and fills a SheepInfo array, returning the number of entries read
    public static int readFromFile(File newFile, sheepInfo[] sheepArr) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(newFile);
        int count = 0;

        while (fileScanner.hasNextLine() && count < sheepArr.length) 
        {
            String line = fileScanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\t");
            if (parts.length < 3) continue;

            String name = parts[0];
            int shearTime = Integer.parseInt(parts[1]);
            int arrivalTime = Integer.parseInt(parts[2]);

            sheepArr[count] = new sheepInfo(name, shearTime, arrivalTime);
            count++;
        }
        fileScanner.close();
        return count; // number of sheep read
    }
}// end of readSheepFile class
