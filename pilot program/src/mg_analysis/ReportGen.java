package mg_analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ReportGen {
	static String saveResults(ArrayList<String> report, int window, String type, String fdr) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("mg_analysis_" + window + type + fdr +".txt", "UTF-8");
		String delim = "\t";
		String newline = "\n";
		String startrangetag;
		//String scrub;
		//header row
		writer.print("Scaffold\tRange\tAdj.P.Value\tSequence.Desc\tBiological.Process\tCellular.Component\tMolecular.Function");
		System.out.println("\nWriting report...");
		for(String analystrow : report)
		{
			//System.out.print("\nWriting: " + analystrow.toString());
			//start next row
			writer.print(newline);
			//write the scaffold number
			writer.print(analystrow.substring(analystrow.indexOf("_")+1,analystrow.lastIndexOf("\t")));
			//write the range
			//split the columns in the array so the range is easier to get
			String rowdata[];
			rowdata = analystrow.split("\t");			
			//String range = analystrow.substring(analystrow.indexOf("\t"),analystrow.length()-1);
			//range.replace("\t", "");
			writer.print(delim);
			//writer.print(range);
			writer.print(rowdata[1]);
			
			//get the start rang tag that we will use to query everything else
			//parse the range
			//String[]rangeparts = range.split("-");
			//startrangetag = rangeparts[0];
			startrangetag = rowdata[1];
			
			//find an adj p value for the start tag
			try (BufferedReader br = new BufferedReader(new FileReader("datafiles/QTL10.781778combinedDESeq.txt")))
			{
				String line;
				while((line = br.readLine()) != null)
				{		
					String[] linevars = line.split("\t");
					if (startrangetag.contains(linevars[9]))
					{
						writer.print(delim);
						writer.print(linevars[7]);
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("No adj. P-val for " + analystrow);
			}
			
			
			//Print descriptions
			try (BufferedReader br = new BufferedReader(new FileReader("datafiles/MimNewAnnotated.txt")))
			{
				String line;
				while((line = br.readLine()) != null)
				{		
					//(print cols 3,4,5,6)
					String[] linevars = line.split("\t");
					if (startrangetag.contains(linevars[2]))
					{
						writer.print(delim);
						writer.print(linevars[3]);
						writer.print(delim);
						writer.print(linevars[4]);
						writer.print(delim);
						writer.print(linevars[5]);
						writer.print(delim);
						writer.print(linevars[6]);
						break;
					}
				}
			}
			catch (Exception e)
			{
				writer.print(delim);
			}
		}
		String status = "Completed report.";
		//close the writer
		writer.close();
		System.out.print("Report complete.");
		return status;
	}

}
