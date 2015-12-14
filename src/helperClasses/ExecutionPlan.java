package helperClasses;

public class ExecutionPlan {
	public static String returnExecutionPlan (String planType) {
		String executionPlan = null;
		if(planType.equals("fil")) {
			executionPlan = "" +
	                "define stream cseEventStream (height int); " +
	                "" +
	                "@info(name = 'query1') " +
	                "from cseEventStream[height < 300 and height > 0 and height!= 7] " +
	                "select height " +
	                "insert into outputStream ;";
		} else if (planType.equals("LenAggAvg")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.length(5) " + 
                    "select avg(height) as avgHt " + 
                    "insert into outputStream ;";
			
		} else if (planType.equals("LenAggCount")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.length(5) " + 
                    "select count(height) as countHeight " + 
                    "insert into outputStream ;";
		} else if (planType.equals("TimeAgg")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.time(1 sec) " + 
                    "select count(height) as countHeight " + 
                    "insert into outputStream ;";
		}  else if (planType.equals("LenBatAgg")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.lengthBatch(5) " + 
                    "select avg(height) as AvgtHeight " + 
                    "insert into outputStream ;";
		} else if (planType.equals("seq")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from every e1 =  cseEventStream " +
                    "-> e2 = cseEventStream[e1.height > e2.height]" +
                    "-> e3 = cseEventStream[e2.height > e3.height] "+
                    "select e1.height as height1, e2.height as height2, e3.height as height3  " + 
                    "insert into outputStream ;";
		} else if (planType.equals("seq2")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query0') " +
                    "from cseEventStream #window.time(200 millisecond)" + 
                    "select height as height " + 
                    "insert into midStream ;" +
                    
                    "@info(name = 'query1') " +
                    "from every e1 =  midStream" +
                    "-> e2 = midStream[e1.height > e2.height]" +
                    "-> e3 = midStream[e2.height > e3.height] "+
                    "select e1.height as height1, e2.height as height2, e3.height as height3  " + 
                    "insert into outputStream ;";
		}
		
		//test comment
		return executionPlan;
	}
}
