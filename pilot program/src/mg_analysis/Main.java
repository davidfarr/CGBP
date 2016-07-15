package mg_analysis;

import java.io.IOException;
import java.util.Scanner;

import mg_analysis.Txtanalyst;
import mg_analysis.bstar;

public class Main {

	public static void main(String[] args) {
		//startup things
		System.out.println("Welcome to the Mimulus guttatus data analysis tool. This program was created by David Farr in May 2016, an\n" +
		"undergraduate student from Central Washington University working with Dr. Allison Scoville. This tool is capable\n" +
		"of running a number of tests based off of data generated in 2015 concerning m.g. sequencing for phenotypic plasticity.\n" +
		"Feel free to use this program for any research on the condition that you cite the author & source." +
				"\n\nPlease choose a function:" +
		"\n\n------------------------------\n"+
		"1. Bxxx S vs All Significant Scaffold Frequencies (1-14) (anecdotal at this time)\n" +
		"2. Bxxx S vs All Significant Loci (& Above B* Threshold) vs. Annotated (Scaffold 1-14)\n" +
		"3. Sc vs T FDR = 0.05 Comparator (Need one of every window)\n" +
		"------------------------------\n");
		Scanner scan = new Scanner(System.in);
		int selection = scan.nextInt();
		switch(selection)
		{
			case 1:
				System.out.println("\n\n\n" +
						"Please select a test:\n" +
						"1. B500 S vs All\n" +
						"2. B600 S vs All\n");
				selection = scan.nextInt();
				if(selection > 0 && selection < 8)
				{
					System.out.println(mg_analysis.Txtanalyst.run4cols(selection));
				}
				break;
			case 2 :
				System.out.println("\n\n\n" +
						"Please select a test:\n" +
						"000 Run all SC vs T under FDR 0.05\n" +
						"999 Run all\n" +
						"1. B200 S vs All\n" +
						"2. B500 S vs All\n" +
						"3. B600 S vs All\n" +
						"4. B10 Sc vs T\n" +
						"5. B50 Sc vs T\n" +
						"6. B100 Sc vs T\n" +
						"7. B200 Sc vs T\n" +
						"8. B300 Sc vs T\n" +
						"9. B400 Sc vs T\n" +
						"10. B500 Sc vs T\n" +
						"11. B600 Sc vs T\n" +
						"12. B10 Sc vs T (FDR = 0.05)\n" +
						"13. B50 Sc vs T (FDR = 0.05)\n" +
						"14. B100 Sc vs T (FDR = 0.05)\n" +
						"15. B200 Sc vs T (FDR = 0.05)\n" +
						"16. B300 Sc vs T (FDR = 0.05)\n" +
						"17. B400 Sc vs T (FDR = 0.05)\n" +
						"18. B500 Sc vs T (FDR = 0.05)\n" +
						"19. B600 Sc vs T (FDR = 0.05)\n" +
						"20. B200 S vs All (FDR = 0.05)\n" +
						"21. B500 S vs All (FDR = 0.05)\n" +
						"22. B600 S vs All (FDR = 0.05)\n");
				selection = scan.nextInt();
				if(selection > 0 && selection < 23)
				{
					try {
						mg_analysis.Txtanalyst.fourColsVsAnnotated(selection);
						//System.out.println(mg_analysis.Txtanalyst.fourColsVsAnnotated(selection));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(selection == 000)
				{
					for(int i = 12; i<20;i++)
					{
						try
						{
							System.out.print("Running all Sc vs T with FDR = 0.05");
							mg_analysis.Txtanalyst.fourColsVsAnnotated(i);
						}
						catch (IOException e)
						{
							System.out.print("IO Exception, unable to complete run.");
						}
					}
				}
				if(selection == 999)
				{
					for(int i = 1; i<23;i++)
					{
						try
						{
							System.out.print("Running every window/FDR");
							mg_analysis.Txtanalyst.fourColsVsAnnotated(i);
						}
						catch (IOException e)
						{
							System.out.print("IO Exception, unable to complete run.");
						}
					}
				}
				break;
			case 3 :
				{
					WindowCompare.compareSCvT();
				}
				break;
			default :
				System.out.println("Your option was not available.");
		}

		scan.close();
	}

}
