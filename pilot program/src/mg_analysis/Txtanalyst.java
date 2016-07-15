package mg_analysis;
import mg_analysis.ReportGen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import mg_analysis.bstar;

/*4-Column Data Guide
 * Column[0] P-Value Rank
 * Column[1] Scaffold_chromosome number_locus number
 * Column[2] B value
 * Column[3] B* value
 * Column[4] P-Value (capped at 0.5)
 * Column[5] FDR Cutoff equation:  0.1 * p-value rank / (# of windows/2) <-- the last row where p-value < cutoff = B* critical value for file
 */
public class Txtanalyst {
	public static String run4cols(int selectionWindow) {
		//Short, presentation list
		ArrayList<String> quickResults = new ArrayList<String>();
		//create long list of all the line item scaffolds - there will be duplicates
		ArrayList<String> longScaffolds = new ArrayList<String>();
		
		//take pre-screened selection window and choose appropriate file path
		String filepath;
		String otherFDRFilepath;
		int window = 500;
		switch(selectionWindow)
		{
		case 1:
			filepath = "datafiles/3_Example of output files/B200_S_vs_all_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B200_S_vs_all_FDR_05.txt";
			window = 200;
			break;
		case 2:
			filepath = "datafiles/3_Example of output files/B500_S_vs_all_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B500_S_vs_all_FDR_05.txt";
			window = 500;
			break;
		case 3:
			filepath = "datafiles/3_Example of output files/B600_S_vs_all_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B500_S_vs_all_FDR_05.txt";
			window = 600;
			break;
		case 4:
			filepath = "datafiles/3_Example of output files/B10_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B10_s_v_t_FDR_05.txt";
			window = 10;
			break;
		case 5:
			filepath = "datafiles/3_Example of output files/B50_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B50_s_v_t_FDR_05.txt";
			window = 50;
			break;
		case 6:
			filepath = "datafiles/3_Example of output files/B100_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B100_s_v_t_FDR_05.txt";
			window = 100;
			break;
		case 7:
			filepath = "datafiles/3_Example of output files/B200_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B200_s_v_t_FDR_05.txt";
			window = 200;
			break;
		case 8:
			filepath = "datafiles/3_Example of output files/B300_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B300_s_v_t_FDR_05.txt";
			window = 300;
			break;
		case 9:
			filepath = "datafiles/3_Example of output files/B400_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B400_s_v_t_FDR_05.txt";
			window = 400;
			break;
		case 10:
			filepath = "datafiles/3_Example of output files/B500_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B500_s_v_t_FDR_05.txt";
			window = 500;
			break;
		case 11:
			filepath = "datafiles/3_Example of output files/B600_s_v_t_FDR_10.txt";
			otherFDRFilepath = "datafiles/3_Example of output files/B600_s_v_t_FDR_05.txt";
			window = 600;
			break;
		default:
			filepath = "";
			break;
		}
		
		//open the text file for the window analysis
		try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				//Find all of the chromosome numbers listed in file		
				String[] linevars = line.split("\t");
				//only take significant ones (consider b* and p-value
				//Get b* significance cutoff after FDR calculation
				Double fdrcutoff = bstar.significance(filepath);
				
				if (Double.parseDouble(linevars[3]) < 0.05 && Double.parseDouble(linevars[2]) < fdrcutoff)
				{
					//only take between scaffolds 1-14
					if (Integer.parseInt(linevars[0].substring(linevars[0].indexOf("_")+1,linevars[0].lastIndexOf("_"))) < 15 )
					{
						longScaffolds.add(linevars[0].substring(0,linevars[0].lastIndexOf("_")));
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
			e.printStackTrace();
		}
		
		Set<String> uniqueSet = new HashSet<String>(longScaffolds);
		for (String temp : uniqueSet)
		{
			if(Collections.frequency(longScaffolds, temp) > 1)
			{
				quickResults.add(temp + ": " + Collections.frequency(longScaffolds, temp));
			}
		}
		
		return String.join("\n", quickResults);
	}
	public static String fourColsVsAnnotated(int type) throws IOException
	{
		ArrayList<String> results = new ArrayList<String>();
		//Read the option for analysis
		String filepath;
		int window = 500;
		String testtype = "";
		String fdr = "";
		switch(type)
		{
		case 1:
			filepath = "datafiles/3_Example of output files/B200_S_vs_all_FDR_10.txt";
			window = 200;
			testtype = "SvAll";
			fdr = "FDR_10";
			break;
		case 2:
			filepath = "datafiles/3_Example of output files/B500_S_vs_all_FDR_10.txt";
			window = 500;
			testtype = "SvAll";
			fdr = "FDR_10";
			break;
		case 3:
			filepath = "datafiles/3_Example of output files/B600_S_vs_all_FDR_10.txt";
			window = 600;
			testtype = "SvAll";
			fdr = "FDR_10";
			break;
		case 4:
			filepath = "datafiles/3_Example of output files/B10_sc_v_t_FDR_10.txt";
			window = 10;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 5:
			filepath = "datafiles/3_Example of output files/B50_sc_v_t_FDR_10.txt";
			window = 50;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 6:
			filepath = "datafiles/3_Example of output files/B100_sc_v_t_FDR_10.txt";
			window = 100;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 7:
			filepath = "datafiles/3_Example of output files/B200_sc_v_t_FDR_10.txt";
			window = 200;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 8:
			filepath = "datafiles/3_Example of output files/B300_sc_v_t_FDR_10.txt";
			window = 300;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 9:
			filepath = "datafiles/3_Example of output files/B400_sc_v_t_FDR_10.txt";
			window = 400;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 10:
			filepath = "datafiles/3_Example of output files/B500_sc_v_t_FDR_10.txt";
			window = 500;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 11:
			filepath = "datafiles/3_Example of output files/B600_sc_v_t_FDR_10.txt";
			window = 600;
			testtype = "SCvT";
			fdr = "FDR_10";
			break;
		case 12:
			filepath = "datafiles/3_Example of output files/B10_sc_v_t_FDR_05.txt";
			window = 10;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 13:
			filepath = "datafiles/3_Example of output files/B50_sc_v_t_FDR_05.txt";
			window = 50;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 14:
			filepath = "datafiles/3_Example of output files/B100_sc_v_t_FDR_05.txt";
			window = 100;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 15:
			filepath = "datafiles/3_Example of output files/B200_sc_v_t_FDR_05.txt";
			window = 200;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 16:
			filepath = "datafiles/3_Example of output files/B300_sc_v_t_FDR_05.txt";
			window = 300;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 17:
			filepath = "datafiles/3_Example of output files/B400_sc_v_t_FDR_05.txt";
			window = 400;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 18:
			filepath = "datafiles/3_Example of output files/B500_sc_v_t_FDR_05.txt";
			window = 500;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 19:
			filepath = "datafiles/3_Example of output files/B600_sc_v_t_FDR_05.txt";
			window = 600;
			testtype = "SCvT";
			fdr = "FDR_05";
			break;
		case 20:
			filepath = "datafiles/3_Example of output files/B200_S_vs_all_FDR_05.txt";
			window = 200;
			testtype = "SvAll";
			fdr = "FDR_05";
			break;
		case 21:
			filepath = "datafiles/3_Example of output files/B500_S_vs_all_FDR_05.txt";
			window = 500;
			testtype = "SvAll";
			fdr = "FDR_05";
			break;
		case 22:
			filepath = "datafiles/3_Example of output files/B600_S_vs_all_FDR_05.txt";
			window = 600;
			testtype = "SvAll";
			fdr = "FDR_05";
			break;
		default:
			filepath = "";
			break;
		}
		
		System.out.print("\nBreaking source list into concatenated array...");
		//break the source into a list of scaffold/loci (concatenated)
		ArrayList<String> rawList = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				//Find all of the chromosome numbers listed in file		
				String[] linevars = line.split("\t");
				//only take ones that are significant
				if (Double.parseDouble(linevars[4]) < 0.05 && Double.parseDouble(linevars[3]) <= bstar.significance(filepath))
				{
					//only take between scaffolds 1-14
					if (Integer.parseInt(linevars[1].substring(linevars[1].indexOf("_")+1,linevars[1].lastIndexOf("_"))) < 15 )
					{
						//unlike the basic frequency function do not cut off after scaffold number
						//also prep to rename sNNffold to scaffold for annotation comparison
						StringBuffer buf = new StringBuffer(linevars[1]);
						buf.replace(0, 3, "sca");
						rawList.add(buf.toString());
						//status update
						//System.out.print("\nAdded " + buf.toString());
						//example format: scaffold_2_12251956
					}
					else
					{
						//System.out.println("\n.");
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
			e.printStackTrace();
		}
		//results=rawList; <--diagnostics
		
		System.out.print("\nLoading annotated list...");
		//Load the annotated list into an arraylist
		ArrayList<String> annotatedlist = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("datafiles/MimNewAnnotated.txt")))
		{
			String line;
			while((line = br.readLine()) != null)
			{		
				//Pull all the scaffold/locus rows and also add in start/stop regions
				String[] linevars = line.split("\t");
				String correctedId;				
				correctedId = linevars[0].concat("~" + linevars[1] + "+" + linevars[2]);
				annotatedlist.add(correctedId);
				//ex format: scaffold_95~33066+35526
				//if we have a result match, add to results
				for(String row : rawList)
				{
					String rowsca = row.substring(0, row.lastIndexOf("_"));
					String matchsca = correctedId.substring(0,correctedId.indexOf("~"));
					rowsca.replaceAll("[^0-9]", "");
					matchsca.replaceAll("[^0-9]", "");
					
					if(rowsca.equals(matchsca))
					{
						//check if in between start/stop
						int start = Integer.parseInt(linevars[1]);
						int end = Integer.parseInt(linevars[2]);
						int bp = Integer.parseInt(row.substring(row.lastIndexOf("_")+1, row.length()));
						if(bp < (end + 1) && bp > (start - 1))
						{
							//only add a range once
							if(!results.contains(row + "\t" + linevars[1] + "-" + linevars[2]))
							{
								//add to list
								results.add((row + "\t" + linevars[1] + "-" + linevars[2]));
							}
						}
					}

				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.print("\nLoading QTL list...");
		//Load the QTL list into arraylist also just like the annotated
		ArrayList<String> qtllist = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader("datafiles/QTL10.781778combinedDESeq.txt")))
		{
			String line;
			while((line = br.readLine()) != null)
			{
				String[] linevars = line.split("\t");
				String qtlId;
				qtlId = linevars[8].concat("~" + linevars[9] + "+" + linevars[10]);
			}
		}
		
		//results = annotatedlist; //<--diagnostic
		//Compare the arraylists and put the results into the results list. If something from the qtl matches but it's not annotated, also add that
		System.out.print("\nMatching source array to annotated tags...");
		ArrayList<String> starts = new ArrayList<String>();
		ArrayList<String> ends = new ArrayList<String>();
		for (String row : annotatedlist)
		{		
				//start is between 2nd and 3rd underscore
				String start = row.substring(row.indexOf("~")+1,row.indexOf("+"));
				//end is between 3rd underscore and the index of the last of the string
				String end = row.substring(row.indexOf("+")+1,row.length());
				start = start.replaceAll("[^0-9]", "");
				if(start.length() > 0)
					{
					starts.add(start);
					}
				end = end.replaceAll("[^0-9]", "");
				if(end.length() > 0)
					{
					ends.add(end);
					}
		}

		
		
		//Do something even if there's nothing!!
		if (results.isEmpty() == true)
		{
			results.add("No matches in annotated list.");
		}
		System.out.print("\nBuilding report file...");
		//pass results to reportgen class to make a full report file
		return(ReportGen.saveResults(results, window,testtype,fdr));
		
		//return String.join("\n", results);
	}

}
