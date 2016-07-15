package mg_analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WindowCompare {
	public static void compareSCvT()
	{
		//Create an array that will capture every significant row by b* and adj p val
		ArrayList<String> allsignificants = new ArrayList<String>();
		//Create an array that holds all the filepaths to look in
		ArrayList<String> filepaths = new ArrayList<String>();
		filepaths.add("mg_analysis_200SCvTFDR_05.txt");
		filepaths.add("mg_analysis_10SCvTFDR_05.txt");
		filepaths.add("mg_analysis_300SCvTFDR_05.txt");
		filepaths.add("mg_analysis_50SCvTFDR_05.txt");
		filepaths.add("mg_analysis_400SCvTFDR_05.txt");
		filepaths.add("mg_analysis_500SCvTFDR_05.txt");
		filepaths.add("mg_analysis_600SCvTFDR_05.txt");
		
		//Iterate through each file
		try{
			for(String filepath : filepaths)
			{
				try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
				{
					String line;
					while ((line = br.readLine()) != null)
					{
						if (!line.contains("Adj."))
						{
							//Sort every field by tab delimited	
							String[] linevars = line.split("\t");
							//only take ones that are significant
							if (Double.parseDouble(linevars[2]) < 0.05)
							{
								//Prepare the string to check if it exists first
								String pre = linevars[0].substring(0,linevars[0].indexOf("_")) + "\t" + linevars[1] + "\t" + linevars[2] + "\t" + filepath.substring(12, filepath.lastIndexOf("S"));					
								if (!allsignificants.contains(pre))
								{
									allsignificants.add(pre);
								}
							}
						}		
					}	
					br.close();
				} 
				catch (FileNotFoundException e) {
					System.out.print("File was not found.");
					e.printStackTrace();
				} 
				catch (IOException e) {
					System.out.print("IOException");
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			System.out.print("File iteration failed: \n");
			System.out.println(e.getMessage());
		}
		try{
			PrintWriter writer = new PrintWriter("SC_vs_T_Comparison.txt", "UTF-8");
			writer.write("Scaffold_bp#\tRange\tAdj.P-Value\tWindow\n");
			for(String row : allsignificants)
			{
				writer.write(row + "\n");
			}
			writer.close();
		}
		catch (Exception e)
		{
			System.out.print("Failed to create comparison file.");
		}
		System.out.print("Report Complete.");
		
	}

}
