package mg_analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class bstar {
	public static Double significance(String filepath)
	{
		/*4-Column Data Guide
		 * Column[0] P-Value Rank
		 * Column[1] Scaffold_chromosome number_locus number
		 * Column[2] B value
		 * Column[3] B* value
		 * Column[4] P-Value (capped at 0.5)
		 * Column[5] FDR Cutoff equation:  0.1 * p-value rank / (# of windows/2) <-- the last row where p-value < cutoff = B* critical value for file
		 */
		
		Double cutoff = 0.00;
		//open the text file for the window analysis
		ArrayList<Double> bstars = new ArrayList<Double>();
		try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{	
				String[] linevars = line.split("\t");
				//build a double arraylist of all B* that fall below the cutoff
				if(Double.parseDouble(linevars[4]) < Double.parseDouble(linevars[5]))
				{
					bstars.add(Double.parseDouble(linevars[3]));
				}				
			}	
			br.close();
		} 
		catch (FileNotFoundException e) {
			System.out.print("File was not found.");
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		//get the last fdr, which should be the cutoff
		cutoff = bstars.get(bstars.size()-1);		
		return cutoff;
	}
}
