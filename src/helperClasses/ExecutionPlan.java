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
		}
		else if (planType.equals("agglensli5")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.length(5) " + 
                    "select avg(height) as avgHt " + 
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("agglensli500")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.length(500) " + 
                    "select avg(height) as avgHt " + 
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("agglenbat6")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.lengthBatch(6) " + 
                    "select avg(height) as AvgtHeight " + 
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("agglenbat60")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.lengthBatch(60) " + 
                    "select avg(height) as AvgtHeight " + 
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("agglenbat600")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.lengthBatch(600) " + 
                    "select avg(height) as AvgtHeight " + 
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("agglenbat6000")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.lengthBatch(6000) " + 
                    "select avg(height) as AvgtHeight " + 
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("aggtimebat1")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.timeBatch(1 sec) " + 
                    "select avg(height) as AvgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimebat20")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.timeBatch(20 sec) " + 
                    "select avg(height) as avgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimebat10")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.timeBatch(10 sec) " + 
                    "select avg(height) as avgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimebat15")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.timeBatch(15 sec) " + 
                    "select avg(height) as avgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimebat5")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.timeBatch(5 sec) " + 
                    "select avg(height) as avgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimesli1")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.time(1 sec) " + 
                    "select avg(height) as avgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimesli5")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.time(5 sec) " + 
                    "select avg(height) as avgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimesli10")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.time(10 sec) " + 
                    "select avg(height) as avgHeight " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("aggtimesli60")) {
			executionPlan = "" +
    				"define stream cseEventStream (height int); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.time(1 min) " + 
                    "select avg(height) as avgHeight " + 
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
