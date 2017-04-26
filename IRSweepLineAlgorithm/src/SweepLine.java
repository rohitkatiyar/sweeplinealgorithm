import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SweepLine {
	
	// List of all abscissae
	ArrayList<Abscissae> initialPoints_E = new ArrayList<Abscissae>();
	ArrayList<Double> removed_E = new ArrayList<Double>();
	
	// List to store lines
	ArrayList<Line> lineQueue_S = new ArrayList<Line>();
	ArrayList<Double> sortedIntersections_L = new ArrayList<Double>();
	ArrayList<Double> sortedIntersections_L_Y = new ArrayList<Double>();

	ArrayList<SLPoint> allPoints = new ArrayList<SLPoint>();
	ArrayList<Line> allLines = new ArrayList<Line>();
	
	public final int LEFT_END_POINT = 0;
	public final int RIGHT_END_POINT = 1;
	public final int INTERSECTION_POINT = 2;
	public static int pointID = 200;
	public static int pointIdIntersect = 5000;
	public static int lineID = 800;
	public final int NULL_ID = -100000;
	int iteration = 0;
	
	public static void main(String args[])
	{
		SweepLine sl = new SweepLine();
		
		sl.fillTestValues();
		sl.beginSweepLineAlgo();
		sl.printIntersectionAbscissae();
	}
	
	// Implementation of the Sweep Line Algorithm
	public void beginSweepLineAlgo()
	{
		double x;
		SLPoint p = null, intersectPointX1 = null, intersectPointX2 = null;
		Line segX1 = null, segX2 = null, segX = null;
		int index1, index2;
		
		// Save all the X values of all the points
		createInititalQueueWithAbscissae(allLines);
		
		// Save all the points
		createListOfAllPoints(allLines);
		
		// Pick the Point to which the abscissa belongs
		while(initialPoints_E.size() > 0)
		{
			System.out.println("Size of E: " + initialPoints_E.size());
			
			x = initialPoints_E.get(0).getxValue();
					
			for (SLPoint findP: allPoints)
			{
				if(initialPoints_E.get(0).getParentPointId() == findP.getPointId())
				{
					p = findP;
					break;
				}
				
			}
			
			// Remove the abscissa now
			removed_E.add(initialPoints_E.get(0).getxValue());
			initialPoints_E.remove(0);
			System.out.println("Removed: Size of E: " + initialPoints_E.size());
			
			// Compare the event types of the point.
			// If Left-End point - Add the line in the Queue S
			// If Right-End point - Remove the line from the Queue S
			// If Intersection point - Add the X value in List L
			if(p.getEventType() == LEFT_END_POINT)
			{
				for(Line ln: allLines)
				{
					if(p.getLineId() == ln.getLineId())
					{
						segX = ln;
						break;
					}
				}
				
				System.out.println("LEFT_END_POINT:x= " + p.getX().getxValue());
				
				// INSERT the segment
				INSERT(segX);
				
				// Find the ABOVE and BELOW line segments for segX
				// If they exists check if they intersect with the segX
				// If intersection point exists add the X value to the List E
				index1 = lineQueue_S.indexOf(segX) + 1;
				index2 = lineQueue_S.indexOf(segX) - 1;
				
				//System.out.println("index1: " + index1 + " S_size: " + lineQueue_S.size() + " index2: " + index2);
				
				// Finding the BELOW and ABOVE line segments
				if(index1 < lineQueue_S.size())
				{
					segX1 = lineQueue_S.get(index1);
				}
				
				if(index2 >= 0 && lineQueue_S.isEmpty() == false)
				{
					segX2 = lineQueue_S.get(index2);
				}
				
				// Checking for the intersection points
				if(segX1 != null)
				{
					intersectPointX1 = checkGetIntersection(segX1, segX);
					insertNewIntersectionPoint(intersectPointX1);
				}
				
				if(segX2 != null)
				{
					intersectPointX2 = checkGetIntersection(segX2, segX);
					insertNewIntersectionPoint(intersectPointX2);
				}
				
			}
			else if(p.getEventType() == RIGHT_END_POINT)
			{
				for(Line ln: allLines)
				{
					if(p.getLineId() == ln.getLineId())
					{
						segX = ln;
						break;
					}
				}
				
				System.out.println("RIGHT_END_POINT:x= " + p.getX().getxValue());
				
				// Find the ABOVE and BELOW line segments for segX
				// If they exists check if they intersect with each other
				// If intersection point exists add the X value to the List E
				index1 = lineQueue_S.indexOf(segX) + 1;
				index2 = lineQueue_S.indexOf(segX) - 1;
				
				if(index1 < lineQueue_S.size())
				{
					segX1 = lineQueue_S.get(index1);
				}
				
				if(index2 >= 0 && lineQueue_S.isEmpty() == false)
				{
					segX2 = lineQueue_S.get(index2);
				}
			
				if(segX1 != null && segX2 != null)
				{
					intersectPointX1 = checkGetIntersection(segX1, segX2);
					insertNewIntersectionPoint(intersectPointX1);
				}
				
				// Remove the segment
				lineQueue_S.remove(segX);
				
			}
			else if(p.getEventType() == INTERSECTION_POINT)
			{
				System.out.println("INTERSECTION_POINT:x= " + p.getX().getxValue());
				
				if(!sortedIntersections_L.contains(p.getX().getxValue()))
				{
					sortedIntersections_L.add(p.getX().getxValue());
					sortedIntersections_L_Y.add(p.getY());
				}
				
				for(Line ln: allLines)
				{
					if(ln.getLineId() == p.getX().getParentLineId())
					{
						segX1 = ln;
					}
					else if(ln.getLineId() == p.getX().getParentLineIdSecond())
					{
						segX2 = ln;
					}
				}
				
				Line tmp = null;
				
				if(segX1.getStartP().getX().getxValue() < segX2.getStartP().getX().getxValue())
				{
					tmp = segX2;
					segX2 = segX1;
					segX1 = tmp;
				}
				
				Line segX3 = null, segX4 = null;
				
				index1 = lineQueue_S.indexOf(segX1) + 1;
				index2 = lineQueue_S.indexOf(segX2) - 1;
				
				if(index1 < lineQueue_S.size())
				{
					segX3 = lineQueue_S.get(index1);
				}
				
				if(index2 >= 0 && lineQueue_S.isEmpty() == false)
				{
					segX4 = lineQueue_S.get(index2);
				}

				if(segX3 != null)
				{
					intersectPointX1 = checkGetIntersection(segX3, segX2);
					insertNewIntersectionPoint(intersectPointX1);
				}
				
				if(segX4 != null)
				{
					intersectPointX2 = checkGetIntersection(segX1, segX4);
					insertNewIntersectionPoint(intersectPointX2);
				}
				
				// Swapping the positions
				Collections.swap(lineQueue_S,lineQueue_S.indexOf(segX1), lineQueue_S.indexOf(segX2));
			}
			
			printStatus();
		}
		
	}
	
	// Function to populate List with all the X values of all points and sort them
	public void createInititalQueueWithAbscissae(ArrayList<Line> lines)
	{
		for(Line ln: lines)
		{
			initialPoints_E.add(ln.getStartP().getX());
			initialPoints_E.add(ln.getEndP().getX());	
		}
		
		sortTheInitialQueue();	
	}
	
	//Function to populate List with all points
	public void createListOfAllPoints(ArrayList<Line> lines)
	{
		for(Line ln: lines)
		{
			allPoints.add(ln.getStartP());
			allPoints.add(ln.getEndP());	
		}
		
		System.out.println("Total points: " + allPoints.size());
	}
	
	// Function to check and get the intersection points
	public SLPoint checkGetIntersection(Line l1, Line l2)
	{
		double A1, A2, B1, B2, C1, C2, x = 0.0, y = 0.0, determinant;
		SLPoint newPoint = null;
		
		A1 = l1.getEndP().getY() - l1.getStartP().getY();
		B1 = l1.getStartP().getX().getxValue() - l1.getEndP().getX().getxValue();
		C1 = (A1 * l1.getStartP().getX().getxValue()) + (B1 * l1.getStartP().getY());
		
		A2 = l2.getEndP().getY() - l2.getStartP().getY();
		B2 = l2.getStartP().getX().getxValue() - l2.getEndP().getX().getxValue();
		C2 = (A2 * l2.getStartP().getX().getxValue()) + (B2 * l2.getStartP().getY());
		
		determinant = (A1 * B2) - (A2 * B1);
		
		if(determinant != 0)
		{
			x = ((B2 * C1) - (B1 * C2))/determinant;
			y = ((A1 * C2) - (A2 * C1))/determinant;
		}
		
		if(x <= l1.getEndP().getX().getxValue() 
				&& x <= l2.getEndP().getX().getxValue() 
				&& x >= l1.getStartP().getX().getxValue() 
				&& x >= l2.getStartP().getX().getxValue())
		{
			newPoint = new SLPoint(new Abscissae(l1.getLineId(), l2.getLineId(), pointIdIntersect, x), y, 
					INTERSECTION_POINT, pointIdIntersect, l1.getLineId());
			
			pointIdIntersect++;
		}
		else
		{
			newPoint = null;
		}
			
		return newPoint;
	}
	
	public void sortTheInitialQueue()
	{
		Collections.sort(initialPoints_E, new Comparator<Abscissae>() {

			@Override
			public int compare(Abscissae o1, Abscissae o2) {
				
				if(o1.getxValue() > o2.getxValue())
				{
					return 1;
				}
				if(o1.getxValue() < o2.getxValue())
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		});
		
		System.out.println("Sorted X values: " + initialPoints_E.toString());
	}

	public void insertNewIntersectionPoint(SLPoint intersectPointX)
	{
		if(intersectPointX != null)
		{
			boolean pointPresentFlag = false;
			
			for(Abscissae abs: initialPoints_E)
			{
				if(abs.getxValue() == intersectPointX.getX().getxValue())
				{
					pointPresentFlag = true;
					break;
				}
			}
			
			if(!pointPresentFlag)
			{
				initialPoints_E.add(intersectPointX.getX());
				System.out.println("Added: Size of E: " + initialPoints_E.size());
				sortTheInitialQueue();
				
				allPoints.add(intersectPointX);
			}
			
		}
	}
	
	public void printIntersectionAbscissae()
	{
		System.out.println("sortedIntersections_L: " + sortedIntersections_L);
		System.out.println("sortedIntersections_L_Y: " + sortedIntersections_L_Y);
	}
	
	public void fillTestValues()
	{
		int lineId = lineID;
		int pointId = pointID;
		
		Line l1 = new Line(new SLPoint(
				           new Abscissae(lineId, NULL_ID, pointId, 2),4,LEFT_END_POINT,pointId++,lineId
				           ),
				           new SLPoint(
				           new Abscissae(lineId, NULL_ID, pointId, 8),10,RIGHT_END_POINT,pointId++,lineId),lineId);
		
		lineId++;
		
		Line l2 = new Line(new SLPoint(
		           new Abscissae(lineId, NULL_ID, pointId, 1),6,LEFT_END_POINT,pointId++,lineId
		           ),
		           new SLPoint(
		           new Abscissae(lineId, NULL_ID, pointId, 12),8,RIGHT_END_POINT,pointId++,lineId),lineId);

        lineId++;
        
        Line l3 = new Line(new SLPoint(
		           new Abscissae(lineId,NULL_ID, pointId, -2),4,LEFT_END_POINT,pointId++,lineId
		           ),
		           new SLPoint(
		           new Abscissae(lineId, NULL_ID, pointId, 10),10,RIGHT_END_POINT,pointId++,lineId),lineId);

        lineId++;
        
        Line l4 = new Line(new SLPoint(
		           new Abscissae(lineId, NULL_ID, pointId, 3),5,LEFT_END_POINT,pointId++,lineId
		           ),
		           new SLPoint(
		           new Abscissae(lineId, NULL_ID, pointId, 7),18,RIGHT_END_POINT,pointId++,lineId),lineId);

        lineId++;
		
        allLines.add(l1);
        allLines.add(l2);
        allLines.add(l3);
        allLines.add(l4);		
	}
	
	public void printStatus()
	{
		System.out.println("---------------------------------------------");
		System.out.print("S_Queue size: " + lineQueue_S.size());
		
		for(Line ln : lineQueue_S)
		{
			System.out.print(ln.toString());
		}
		
		System.out.println();
		System.out.println("E_Queue: " + initialPoints_E.toString());
		System.out.println("L_Queue: " + sortedIntersections_L);
		System.out.println("---------------------------------------------");	
	}
	
	public void INSERT(Line ln)
	{
		boolean linePresentFlag = false;
		
		for(Line l: lineQueue_S)
		{
			if(l.getLineId() == ln.getLineId())
			{
				linePresentFlag = true;
				break;
			}	
		}
		
		if(!linePresentFlag)
		{
			lineQueue_S.add(ln);	
		}	
	}

}
