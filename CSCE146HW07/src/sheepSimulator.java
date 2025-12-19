/*
 * Written by John Llamas
 */
import java.io.File;
import java.util.Scanner;
public class sheepSimulator 
{
	
	public static void main(String[] args) 
	{
		boolean runAgain = true;
        Scanner scnr = new Scanner(System.in);

        while (runAgain) {
            try {
                //Read file
                File sheepFile = new File("sheepInfo.txt");
                sheepInfo[] allSheep = new sheepInfo[100]; // capacity = 100 sheep
                int numSheep = readSheepFile.readFromFile(sheepFile, allSheep);

                //Sort all sheep by arrival time (Selection sort)
                for (int i = 0; i < numSheep - 1; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < numSheep; j++) {
                        if (allSheep[j].getArrivalTime() < allSheep[minIndex].getArrivalTime()) {
                            minIndex = j;
                        }
                    }
                    sheepInfo temp = allSheep[i];
                    allSheep[i] = allSheep[minIndex];
                    allSheep[minIndex] = temp;
                }

                //Simulate shearing schedule
                sheepMinHeap<sheepInfo> waitingSheep = new sheepMinHeap<>();
                System.out.println("Schedule from the Provided File:\n");

                int currTime = 0;
                int index = 0; // next sheep to "arrive"

                while (index < numSheep || !waitingSheep.isEmpty()) {

                    // Add sheep that have arrived to waiting heap
                    while (index < numSheep && allSheep[index].getArrivalTime() <= currTime) {
                        waitingSheep.add(allSheep[index]);
                        index++;
                    }

                    // If no sheep are waiting, skip time to next arrival
                    if (waitingSheep.isEmpty()) {
                        if (index < numSheep) {
                            currTime = allSheep[index].getArrivalTime();
                        }
                        continue;
                    }

                    // Remove next sheep to shear (priority: shear time → name)
                    sheepInfo nextSheep = waitingSheep.remove();
                    System.out.println(nextSheep);

                    // Advance time
                    currTime += nextSheep.getShearTime();
                }

                //Ask user if they want to run again
                System.out.println("All sheep have been sheared! Run again? (yes/no): ");
                String choice = scnr.nextLine().trim().toLowerCase();
                runAgain = choice.equals("yes");

            } catch (Exception e) {
                e.printStackTrace();
                runAgain = false;
            }
        }

        scnr.close();
    } //end of main
} // end of sheepSimulator class