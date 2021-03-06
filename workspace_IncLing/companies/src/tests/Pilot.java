package tests;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.text.JTextComponent;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.fest.swing.fixture.JTreeFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TestName;
import org.softlang.company.factory.BeanFactory;
import org.softlang.company.factory.PojoFactory;
import org.softlang.company.impl.bean.CompanyImpl;
import org.softlang.company.impl.bean.EmployeeImpl;
import org.softlang.features.Logging;
import org.softlang.features.Precedence;
import org.softlang.swing.controller.Controller;
import org.softlang.swing.model.Model;
import org.softlang.swing.view.MainView;

import specifications.Configuration;

public class Pilot extends CompaniesTest {

	@Override
	protected void configure() {
		// set mandatory features
		super.configure();
		// CompaniesVariables.getSINGLETON().setGUI___(true);
		Configuration.GUI = true;
		// if (testName == null) {
		// throw new RuntimeException();
		// }
		// System.out.println("METHOD NAME:" + testName.getMethodName());
		// if (testName.getMethodName().equals("test1")) {
		//// CompaniesVariables.getSINGLETON().setCUT_NO_DEPARTMENT___(true);
		// } else if (testName.getMethodName().equals("test2")
		// || testName.getMethodName().equals("test2A")) {
		//// CompaniesVariables.getSINGLETON().setCUT_NO_MANAGER___(true);
		// } else if (testName.getMethodName().equals("test2B")) {
		//// CompaniesVariables.getSINGLETON().setCUT_WHATEVER___(true);
		//// CompaniesVariables.getSINGLETON().setTOTAL_WALKER___(true);
		// } else if (testName.getMethodName().equals("test3")) {
		//// CompaniesVariables.getSINGLETON().setTOTAL_WALKER___(true);
		// } else if (testName.getMethodName().equals("test4")) {
		//// CompaniesVariables.getSINGLETON().setTOTAL_REDUCER___(true);
		// } else if (testName.getMethodName().equals("test5")
		// || testName.getMethodName().startsWith("test6")
		// || testName.getMethodName().equals("test7")) {
		//// CompaniesVariables.getSINGLETON().setGUI___(false);
		//// CompaniesVariables.getSINGLETON().setLOGGING___(true);
		// if (testName.getMethodName().equals("test7") ||
		// testName.getMethodName().equals("test6PreTrue")){
		//// CompaniesVariables.getSINGLETON().setPRECEDENCE___(true);
		// }
		//
		// } else {
		// System.err.printf("%s did not set default configuration",
		// testName.getMethodName());
		// }
	}

	FrameFixture window1, window2;
	Model model1, model2;
	MainView view1, view2;
	Controller controller1, controller2;

	static Model model;
	static MainView view;
	static Controller controller;

	@Override
	@Before
	public void setup() {
		super.configure();
		FailOnThreadViolationRepaintManager.install();
		GuiQuery<MainView> action1 = new GuiQuery<MainView>() {
			protected MainView executeInEDT() {
				configure();

				model1 = new Model(new BeanFactory());
				// if (testName.getMethodName().startsWith("test2"))
				// model1 = new Model(new PojoFactory());

				view1 = new MainView(model1);
				controller1 = new Controller(model1, view1);
				controller1.start();
				return view1;

			}

		};
		
		MainView frame1 = GuiActionRunner.execute(action1);
		window1 = new FrameFixture(frame1);
		window1.show(new Dimension(500, 500));
	}

	@Test
	public void test1() {

//		Configuration.CUT_NO_DEPARTMENT = true;
		if (Configuration.CUT_NO_DEPARTMENT) {
			// clicking on the tree
			JTreeFixture tree = window1.tree();
//			assertThat(tree).isNotNull(); // sanity test
			tree = tree.selectPath("meganalysis/Development/Ray (Manager)");
			tree.click();

			// getting the value of "name" in the text component
			GenericTypeMatcher<JTextComponent> textAreaMatcherName = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					String expected = "Ray";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JTextComponentFixture Name = window1.textBox(textAreaMatcherName);
			Name.enterText("1");
			// clicking on the button "cut", this changes the value of "total"
			GenericTypeMatcher<JButton> noButtonMatcher = new GenericTypeMatcher<JButton>(JButton.class) {
				@Override
				protected boolean isMatching(JButton button) {
					String expected = "cut";
					String actual = button.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JButtonFixture noButton = window1.button(noButtonMatcher);
			noButton.requireEnabled();
			noButton.requireVisible();
			noButton.click();

			// asserting that the value of "total" was changed
			// after clicking on the button "cut"
			assertTrue(model1.str.contains("at method name:Model.cut"));

			// DG- CHECK
			// In the exec, cut company,employee,department
		}
	}

	@Test
	public void test2() {

//		Configuration.CUT_NO_MANAGER = true;
		if (Configuration.CUT_NO_MANAGER) {
			// clicking on the tree
			JTreeFixture tree = window1.tree();
//			assertThat(tree).isNotNull(); // sanity test
			tree = tree.selectPath("meganalysis/Development/");
			tree.click();

			// getting the value of "name" in the text component
			GenericTypeMatcher<JTextComponent> textAreaMatcherName = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					String expected = "Development";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JTextComponentFixture Name = window1.textBox(textAreaMatcherName);
			Name.enterText("1");
			// clicking on the button "cut", this changes the value of "total"
			GenericTypeMatcher<JButton> noButtonMatcher = new GenericTypeMatcher<JButton>(JButton.class) {
				@Override
				protected boolean isMatching(JButton button) {
					String expected = "cut";
					String actual = button.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JButtonFixture noButton = window1.button(noButtonMatcher);
			noButton.requireEnabled();
			noButton.requireVisible();
			noButton.click();

			// asserting that the value of "total" was changed
			// after clicking on the button "cut"
			assertTrue(model1.str.contains("at method name:Model.cut"));
		}
	}

	@Test
	public void test2A() {

//		Configuration.CUT_NO_MANAGER = true;
		if (Configuration.CUT_NO_MANAGER) {
			// clicking on the tree
			JTreeFixture tree = window1.tree();
//			assertThat(tree).isNotNull(); // sanity test
			tree = tree.selectPath("meganalysis/");
			tree.click();

			// getting the value of "name" in the text component
			GenericTypeMatcher<JTextComponent> textAreaMatcherName = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					String expected = "meganalysis";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JTextComponentFixture Name = window1.textBox(textAreaMatcherName);
			Name.enterText("1");
			// clicking on the button "cut", this changes the value of "total"
			GenericTypeMatcher<JButton> noButtonMatcher = new GenericTypeMatcher<JButton>(JButton.class) {
				@Override
				protected boolean isMatching(JButton button) {
					String expected = "cut";
					String actual = button.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JButtonFixture noButton = window1.button(noButtonMatcher);
			noButton.requireEnabled();
			noButton.requireVisible();
			noButton.click();

			// asserting that the value of "total" was changed
			// after clicking on the button "cut"
			assertTrue(model1.str.contains("at method name:Model.cut"));
		}
	}

	@Test
	public void test2B() {
//		Configuration.CUT_WHATEVER = true;
		Configuration.TOTAL_WALKER = true;

		if (Configuration.CUT_WHATEVER && Configuration.TOTAL_WALKER) {
			// clicking on the tree
			JTreeFixture tree = window1.tree();
//			assertThat(tree).isNotNull(); // sanity test
			tree = tree.selectPath("meganalysis/Development/Dev1/Dev1.1/Karl (Manager)");
			tree.click();

			// clicking on the button "cut", this changes the value of "total"
			GenericTypeMatcher<JButton> noButtonMatcher = new GenericTypeMatcher<JButton>(JButton.class) {
				@Override
				protected boolean isMatching(JButton button) {
					String expected = "cut";
					String actual = button.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JButtonFixture noButton = window1.button(noButtonMatcher);
			noButton.requireEnabled();
			noButton.requireVisible();
			noButton.click();

			// getting the value of "name" in the text component
			GenericTypeMatcher<JTextComponent> textAreaMatcher1 = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					String expected = "1172.5";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
//			JTextComponentFixture total1 = window1.textBox(textAreaMatcher1);
			// asserting that the value of "total" was changed
			// after clicking on the button "cut"

//			assertTrue(total1.text().equals("1172.5"));
		}
	}

	@Test
	public void test3() {

//		Configuration.TOTAL_WALKER = true;
		if (Configuration.TOTAL_WALKER) {
			// clicking on the tree
			JTreeFixture tree = window1.tree();
//			assertThat(tree).isNotNull(); // sanity test
			tree = tree.selectPath("meganalysis/Research/Craig (Manager)");
			tree.click();

			// getting the value of "name" in the text component
			GenericTypeMatcher<JTextComponent> textAreaMatcher = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					String expected = "123456.0";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			// JTextComponentFixture total = window1.textBox(textAreaMatcher);
			// total.enterText("3");

			tree = tree.selectPath("meganalysis/Research/");
			tree.click();

			GenericTypeMatcher<JTextComponent> textAreaMatcher1 = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					// String expected = "137035.03";
					// String expected = "13582.0";
					String expected = "137035.0";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JTextComponentFixture total1 = window1.textBox(textAreaMatcher1);
			// asserting that the value of "total" was changed
			// assertTrue(total1.text().equals("137035.03"));
			// assertTrue(total1.text().equals("13582.0"));
			assertTrue(total1.text().equals("137035.0"));
		}
	}

//	@Test
	public void test4() {

//		Configuration.TOTAL_REDUCER = true;
		if (Configuration.TOTAL_REDUCER) {
			// clicking on the tree
			JTreeFixture tree = window1.tree();
//			assertThat(tree).isNotNull(); // sanity test
			tree = tree.selectPath("meganalysis/Research/Erik");
			tree.click();

			// getting the value of "name" in the text component
			GenericTypeMatcher<JTextComponent> textAreaMatcher = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					String expected = "12345.0";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JTextComponentFixture total = window1.textBox(textAreaMatcher);
			total.enterText("5");

			tree = tree.selectPath("meganalysis/Research/");
			tree.click();

			GenericTypeMatcher<JTextComponent> textAreaMatcher1 = new GenericTypeMatcher<JTextComponent>(
					JTextComponent.class) {
				@Override
				protected boolean isMatching(JTextComponent tcomp) {
					// String expected = "137035.05";
					String expected = "124695.0";
					String actual = tcomp.getText();
					return actual == null ? false : expected.equals(actual);
				}
			};
			JTextComponentFixture total1 = window1.textBox(textAreaMatcher1);

			// asserting that the value of "total" was changed
			// after clicking on the button "cut"
			// assertTrue(total1.text().equals("137035.05"));
			assertTrue(total1.text().equals("124695.0"));
		}
	}

	/*
	 * public static void main(String[] args) {
	 * CompaniesVariables.getSINGLETON().setGUI___(true);
	 * CompaniesVariables.getSINGLETON().setTOTAL_WALKER___(true);
	 * 
	 * 
	 * model = new Model(new BeanFactory());
	 * 
	 * view = new MainView(model);
	 * 
	 * Controller controller = new Controller(model, view);
	 * 
	 * controller.teststr = ""; controller.teststr1 = "34567.0"; controller.start();
	 * 
	 * //DG //In the exec, cut company,employee,department }
	 */
	// SEPERATE TEST FOR ACCESS CONTROL

	
	@Test
	public void test6PreTrue() {
//		Configuration.PRECEDENCE = true;
		if (Configuration.PRECEDENCE) {

			EmployeeImpl sampleEmp = new EmployeeImpl();
			sampleEmp.setName("Divya");
			sampleEmp.setAddress("Austin,TX");
			sampleEmp.setManager(true);
			sampleEmp.setSalary(10);

			Logging l = new Logging();
			sampleEmp.addObserver(l);
			sampleEmp.setSalary(50);

			assertEquals(10, (int) sampleEmp.getOldSalary());
			assertEquals(sampleEmp.str, "LINE NO:34 at method:Logging.update"); // checks
																				// notifyobservers
																				// of
																				// setSalary
			assertEquals(50, (int) sampleEmp.getSalary());
			assertEquals("Divya", sampleEmp.getName());// checks if the name of the
														// company were changed
			assertEquals("Austin,TX", sampleEmp.getAddress());

			assertEquals(true, sampleEmp.getManager());

			// assertEquals(sampleCompany.companyStr,"LINE NUM:54at METHOD
			// NAME:addObserver");
		}
	}

	
	@After
	public void teardown() {
		// if (!(testName.getMethodName().equals("test5")) &&
		// !(testName.getMethodName().startsWith("test6"))
		// && !(testName.getMethodName().equals("test7")))
		 window1.cleanUp();
		// // System.out.println(CompaniesVariables.getSINGLETON().toString());
		// // CompaniesVariables.getSINGLETON().restore();
	}

}
