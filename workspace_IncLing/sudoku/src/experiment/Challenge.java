package experiment;

import org.junit.runner.JUnitCore;
import Combinations.Combination;
import Combinations.FeatureCombination;
import Main.ProductGeneration;
import Main.TargetSystem;
import report.Record;
import report.RunListernerReport;
import specifications.Configuration;
import tests.BoardManagerTest;
import tests.BoardTest;
import tests.FieldTest;
import tests.SudokuFacadeTest;
import tests.SudokuGeneratorTest;
import tests.TestMAB;
import tests.TestMain;

public class Challenge {

	public void executeJunitTestCase(ProductGeneration tools) {
		int count = 0;
		Record record = new Record();
		for (Combination combination : tools.getCombsForTest()) {
			for (FeatureCombination f : combination.getListFeatures()) {
				if (f.getName().equals("STATES")) {
					Configuration.STATES = (f.getState().equals("0") ? false : true);
				} else if (f.getName().equals("UNDO")) {
					Configuration.UNDO = (f.getState().equals("0") ? false : true);
				} else if (f.getName().equals("COLOR")) {
					Configuration.COLOR = (f.getState().equals("0") ? false : true);
				} else if (f.getName().equals("SOLVER")) {
					Configuration.SOLVER = (f.getState().equals("0") ? false : true);
				} else if (f.getName().equals("GENERATOR")) {
					Configuration.GENERATOR = (f.getState().equals("0") ? false : true);
				} else if (f.getName().equals("EXTENDEDSUDOKU")) {
					Configuration.EXTENDEDSUDOKU = (f.getState().equals("0") ? false : true);
				} else if (f.getName().equals("BASE")) {
					Configuration.BASE = (f.getState().equals("0") ? false : true);
				}
			}
			System.err.println("Configuration:");
			Configuration.productPrint();
			System.out.println("");
			if (Configuration.validProduct()) {
				count++;
				JUnitCore junit = new JUnitCore();
				junit.addListener(new RunListernerReport(Configuration.returnProduct(), record));
				org.junit.runner.Result result = junit.run(BoardManagerTest.class, BoardTest.class, FieldTest.class,
						SudokuFacadeTest.class, SudokuGeneratorTest.class, TestMAB.class, TestMain.class);
				Configuration.productPrint();
				System.out.println("\n\n ----------------------- \n\n");
			} else {
				System.err.println("****** Invalid ******");
			}
		}
		try {
			record.record2();
		} catch (Exception e) {
		}
		System.out.println("Configurations count:" + count);

	}

	public void run(TargetSystem tg, String path) {
		ProductGeneration products = new ProductGeneration();
		products.run(tg, path);
		executeJunitTestCase(products);
	}

	public static void main(String[] args) {
		long startTime = 0;
		long finishTime = 0;
		int totalExecution = 10;
		int index = 0;
		finishTime = 0;
		startTime = System.currentTimeMillis();
		Challenge challenge = new Challenge();
		while (index < totalExecution) {
			/** all_valid_conf **/
			String path = "C:/Users/Fischer_PC/Documents/2019_CSS_Journal/workspace_IncLing/Tools/All_valid_conf/sudoku/products";
			challenge.run(TargetSystem.SUDOKU, path);
			finishTime = System.currentTimeMillis() - startTime;
			index++;
		}

		float average = finishTime / index;

		System.out.println("Total time (ms): " + finishTime + " time average (s): " + (average / 1000)
				+ " number of times performed:" + index);

	}
}