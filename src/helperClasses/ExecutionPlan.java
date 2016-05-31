package helperClasses;

public class ExecutionPlan {
	public static String returnExecutionPlan (String planType) {
		String executionPlan = null;
		if(planType.equals("fil")) {
			executionPlan = "" +
	                "define stream cseEventStream (height int); " +
	                "" +
	                "@info(name = 'query1') " +
	                "from cseEventStream[height < 300 ] " +
	                "select height " +
	                "insert into outputStream ;";
		} else if (planType.equals("agg")) {
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
                    "from cseEventStream#window.length(5) " + 
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
		} 
		else if (planType.equals("pat3")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from every e1 =  cseEventStream " +
                    "-> e2 = cseEventStream[e1.height == e2.height]" +
                    "-> e3 = cseEventStream[e2.height == e3.height] "+
                    "select e1.height as height1, e2.height as height2, e3.height as height3  " + 
                    "insert into outputStream ;";
			
		} 
		
		else if (planType.equals("pat5")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from every e1 =  cseEventStream " +
                    "-> e2 = cseEventStream[e1.height == e2.height]" +
                    "-> e3 = cseEventStream[e2.height == e3.height] "+
                    "-> e4 = cseEventStream[e3.height == e4.height] "+
                    "-> e5 = cseEventStream[e4.height == e5.height] "+
                    "-> e6 = cseEventStream[e5.height == e6.height] "+
                    "select e1.height as height1, e2.height as height2, e3.height as height3, " +
                    "e4.height as height4, e5.height as height5, e6.height as height6 "+
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("seq3")) { 
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from every e1 =  cseEventStream, e2 = cseEventStream[e1.height == e2.height], e3 =  cseEventStream[e3.height == e2.height] " +
                    "select e1.height as height1, e2.height as height2, e3.height as height3 " + 
                    "insert into outputStream ;";
		} else if (planType.equals("seq5")) { 
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from every e1 =  cseEventStream, e2 = cseEventStream[e1.height == e2.height], e3 =  cseEventStream[e2.height == e3.height], " +
                    "e4 = cseEventStream[e3.height == e4.height], e5 =  cseEventStream[e4.height == e5.height], e6 =  cseEventStream[e5.height == e6.height] "+
                    "select e1.height as height1, e2.height as height2, e3.height as height3, e4.height as height4, e5.height as height5, e6.height as height6 " + 
                    "insert into outputStream ;";
		}
		return executionPlan;
	}
	
}
