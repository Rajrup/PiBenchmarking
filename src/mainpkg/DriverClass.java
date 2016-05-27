package mainpkg;

import java.io.File;
import java.util.HashMap;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helperClasses.ExecutionPlan;
import helperClasses.FileAnalysis;

public class DriverClass {
	String dataSetType = "PLUG-11";
	InputHandler inputHandler = null;
	File outFile = null;
	HashMap<Long, Integer> inputHashMap = null;
	HashMap<Long, Integer> outputHashMap = null;
	private final Integer one = Integer.valueOf(1);
	public static String queryType = null;
	static final int TIME_INTERAVAL = 1*60; // 30 min;
	private static final int SHORT_CONSTANT_INPUT = 1;
	private static final int SHORT_VARIED_INPUT = 2;
	private static final int LONG_CONSTANT_INPUT = 3;
	private static final int LONG_VARIED_INPUT = 4;
	private static final int SEQ_INPUT = 5;
	private static int input_type;
	public static Logger Log = LoggerFactory.getLogger(DriverClass.class);

	public static void main(String[] args) {
		// initialise the file path here
		Log.info("experiment started");
		queryType = args[0];
		input_type = Integer.valueOf(args[1]);
		DriverClass dc = new DriverClass();
		Boolean val = dc.initiateExecutionPlan();
		if (val == true) {
			dc.initiateEventGen();
			dc.sendDatatoSiddhi();
		}

	}
	private Boolean initiateExecutionPlan() {
		SiddhiManager siddhiManager = new SiddhiManager();
		String executionPlan = ExecutionPlan.returnExecutionPlan(queryType);
		if (executionPlan == null) {
			Log.info("pls enter a valid execution plan as 2nd cmdline parameter");
			return false;
		}

		// Generating runtime
		ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(executionPlan);

		// Adding callback to retrieve output events from query
		executionPlanRuntime.addCallback("query1", new QueryCallback() {
			@Override
			public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
				if (input_type == SHORT_CONSTANT_INPUT || input_type == SHORT_VARIED_INPUT) {
					EventPrinter.print(inEvents);
					System.out.println("\n");
					return;
				}

				for (Event eve : inEvents) {
					Long key = Long.valueOf(System.currentTimeMillis() / 1000);
					Integer tempInt = outputHashMap.get(key);
					if (tempInt != null) {
						outputHashMap.put(key, tempInt + 1);
					} else {
						outputHashMap.put(key, one);
					}
				}
			}
		});
		inputHandler = executionPlanRuntime.getInputHandler("cseEventStream");
		executionPlanRuntime.start();
		return true;

	}

	private void initiateEventGen() {
		inputHashMap = new HashMap<Long, Integer>();
		outputHashMap = new HashMap<Long, Integer>();
	}

	public void sendDatatoSiddhi() {

		switch (input_type) {
		case SHORT_CONSTANT_INPUT: {
			System.out.println("input thread");
			System.out.println(Thread.currentThread().getId());
			// Object[] obj1 = {Integer.parseInt("10")};
			// Object[] obj2 = {Integer.parseInt("5")};
			for (int i = 0; i < 20; i++) {
				Object[] obj1 = { 10 };
				try {
					inputHandler.send(obj1);
					// Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
		case SHORT_VARIED_INPUT: {
			int[] objArr = {3,7,9,11};
			int counter = 0;
			for (int i = 0; i < 20; i++) {
				if (counter >= 4) {
					counter = 0;
				}
				Object[] obj = new Object[1];
				obj[0] = objArr[counter];
				counter++;
				try {
					System.out.println("sent obj");
					System.out.println(obj[0]);
					inputHandler.send(obj);
//					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
		case LONG_CONSTANT_INPUT:
			Long curentTime = System.currentTimeMillis() / 1000;
			Long key = curentTime;
			while (curentTime + TIME_INTERAVAL > key) {
				try {
					Object[] obj = { 10 };
					inputHandler.send(obj);
					key = Long.valueOf(System.currentTimeMillis() / 1000);
					Integer tempInt = inputHashMap.get(key);
					if (tempInt != null) {
						inputHashMap.put(key, tempInt + 1);
					} else {
						inputHashMap.put(key, one);
					}
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}

			fileWrite();
			break;
		case LONG_VARIED_INPUT: {
			curentTime = System.currentTimeMillis() / 1000;
			key = curentTime;
			int[] objArr = {3,7,9};
			int counter = 0;
			while (curentTime + TIME_INTERAVAL > key) {
				if (counter >= 3) {
					counter = 0;
				}
				Object[] obj = new Object[1];
				obj[0] = objArr[counter];
				counter++;
				try {
					inputHandler.send(obj);
					// Thread.sleep(1);
					// System.out.println(obj[0]);
					// hashMapupdate();
					key = Long.valueOf(System.currentTimeMillis() / 1000);

					Integer tempInt = inputHashMap.get(key);
					if (tempInt != null) {
						inputHashMap.put(key, tempInt + 1);
					} else {
						inputHashMap.put(key, one);
					}
					// Thread.sleep(1);
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}

			fileWrite();
			break;
		}
		/*
		 * this is a special input type to test the sequence query
		 */
		case SEQ_INPUT: {
			curentTime = System.currentTimeMillis() / 1000;
			key = curentTime;
			int[] objArr = {3,3,9};
			int counter = 0;
			while (curentTime + 30 > key) {
				if (counter >= 3) {
					counter = 0;
				}
				Object[] obj = new Object[1];
				obj[0] = objArr[counter];
				counter++;
				try {
					inputHandler.send(obj);
					// Thread.sleep(1);
					// System.out.println(obj[0]);
					// hashMapupdate();
					key = Long.valueOf(System.currentTimeMillis() / 1000);

					Integer tempInt = inputHashMap.get(key);
					if (tempInt != null) {
						inputHashMap.put(key, tempInt + 1);
					} else {
						inputHashMap.put(key, one);
					}
					// Thread.sleep(1);
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}

			fileWrite();
			break;
		}
		}

	}
	private synchronized void hashMapupdate() {
		Long key = Long.valueOf(System.currentTimeMillis() / 1000);
		if (inputHashMap.containsKey(key)) {
			Integer tempInt = inputHashMap.get(key);
			inputHashMap.put(key, tempInt + 1);
		} else {
			inputHashMap.put(Long.valueOf(System.currentTimeMillis() / 1000), Integer.valueOf(1));
		}
	}

	private synchronized void fileWrite() {
		Log.info("DataSet over fileWrite called");
		FileAnalysis obj = new FileAnalysis();
		// obj.calculateFrequency(inputFile.getAbsolutePath(),
		// "result-"+pathToFile);
		obj.calculateFrequencyFromBuffer(inputHashMap, "inputResult.csv");
		obj.calculateFrequencyFromBuffer(outputHashMap, "outputResult.csv");
		// obj.calculateFrequency(outFile.getAbsolutePath(),
		// "result-"+pathToFile);
		Log.info("experiment over");
	}

}
