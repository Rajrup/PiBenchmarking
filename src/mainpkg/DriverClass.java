package mainpkg;

import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helperClasses.ExecutionPlan;

public class DriverClass {
	/*
	 * constants
	 */
	private static final int SHORT_CONSTANT_INPUT = 1;
	private static final int SHORT_VARIED_INPUT = 2;
	private static final int LONG_CONSTANT_INPUT = 3;
	private static final int LONG_VARIED_INPUT = 4;
	private static final int SEQ_INPUT = 5;
	InputHandler inputHandler = null;
	public static String queryType = null;
	
	private static int input_type;
	
	public static Logger Log = LoggerFactory.getLogger(DriverClass.class);
	static int runtime;
	static int sleeptime;
	public static void main(String[] args) {
		// initialize the file path here
		Log.info("experiment started");
		if (args.length !=3 && args.length != 4)  {
			Log.info("invalid usage");
			Log.info("queryType, input type, runtime,sleepTime");
			return;
		}
		queryType = args[0];
		input_type = Integer.valueOf(args[1]);
		runtime = Integer.valueOf(args[2]);
		DriverClass dc = new DriverClass();
		Boolean val = dc.initiateExecutionPlan();
		if (val == false) {
			return;
		}
			if (runtime ==-1) {
				dc.sendDatatoSiddhi();
			} else {
				if (runtime!= -1 && args.length == 3) {
					Log.info("if runtime in not -1 then enter a sleeptime in seconds");
					return;
				}
				sleeptime = Integer.valueOf(args[3]);
				dc.sendDataForTimeIntervalToSiddhi();
				
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
//				System.out.println("receiver thread id: " + Thread.currentThread().getId());
				if (input_type == SHORT_CONSTANT_INPUT || input_type == SHORT_VARIED_INPUT) {
					EventPrinter.print(inEvents);
					return;
				}
			}
		});
		inputHandler = executionPlanRuntime.getInputHandler("cseEventStream");
		executionPlanRuntime.start();
		return true;

	}

	public void sendDatatoSiddhi() {
//		System.out.println("sender thread id: " + Thread.currentThread().getId());
		switch (input_type) {
		case SHORT_CONSTANT_INPUT: {
			System.out.println("input thread");
			System.out.println(Thread.currentThread().getId());
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
		case LONG_CONSTANT_INPUT: {
			while (true) {
				try {
					Object[] obj = { 10 };
					inputHandler.send(obj);
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}
			
		}
		case LONG_VARIED_INPUT: {
			int[] objArr = {3,7,9};
			int counter = 0;
			while (true) {
				if (counter >= 3) {
					counter = 0;
				}
				Object[] obj = new Object[1];
				obj[0] = objArr[counter];
				counter++;
				try {
					inputHandler.send(obj);
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}
			
		}
		/*
		 * this is a special input type to test the sequence query
		 */
		case SEQ_INPUT: {
			int[] objArr = {3,3,9};
			int counter = 0;
			while (true) {
				if (counter >= 3) {
					counter = 0;
				}
				Object[] obj = new Object[1];
				obj[0] = objArr[counter];
				counter++;
				try {
					inputHandler.send(obj);
					// Thread.sleep(1);
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}
		}
		}

	}
	public void sendDataForTimeIntervalToSiddhi() {
//		System.out.println("sender thread id: " + Thread.currentThread().getId());
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
		case LONG_CONSTANT_INPUT: {
			Long startTime = System.currentTimeMillis() / 1000;
			while (true) {
				try {
					Object[] obj = { 10 };
					inputHandler.send(obj);
					Long currT = System.currentTimeMillis() / 1000;
					if ((currT- startTime) >= runtime) {
						Thread.sleep(sleeptime);
						startTime = System.currentTimeMillis() / 1000;
						Log.info("experiment resumed after sleeping for " + Integer.toString(sleeptime));
					}
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}
			
		}
		case LONG_VARIED_INPUT: {
			Long startTime = System.currentTimeMillis() / 1000;
			int[] objArr = {3,7,9};
			int counter = 0;
			while (true) {
				if (counter >= 3) {
					counter = 0;
				}
				Object[] obj = new Object[1];
				obj[0] = objArr[counter];
				counter++;
				try {
					inputHandler.send(obj);
					Long currT = System.currentTimeMillis() / 1000;
					if ((currT- startTime) >= runtime) {
						Thread.sleep(sleeptime);
						startTime = System.currentTimeMillis() / 1000;
						Log.info("experiment resumed after sleeping for " + Integer.toString(sleeptime));
					}
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}
			
		}
		/*
		 * this is a special input type to test the sequence query
		 */
		case SEQ_INPUT: {
			Long startTime = System.currentTimeMillis() / 1000;
			int[] objArr = {3,3,9};
			int counter = 0;
			while (true) {
				if (counter >= 3) {
					counter = 0;
				}
				Object[] obj = new Object[1];
				obj[0] = objArr[counter];
				counter++;
				try {
					inputHandler.send(obj);
					Long currT = System.currentTimeMillis() / 1000;
					if ((currT- startTime) >= runtime) {
						Thread.sleep(sleeptime);
						startTime = System.currentTimeMillis() / 1000;
						Log.info("experiment resumed after sleeping for " + Integer.toString(sleeptime));
					}
				} catch (InterruptedException ie) {
					System.out.println("could not send to Siddhi");
					ie.printStackTrace();
				}
			}
		}
		}

	}
}
