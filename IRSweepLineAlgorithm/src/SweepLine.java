import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SweepLine {
	
	ArrayList<Abscissae> initialPoints_E = new ArrayList<Abscissae>();
	ArrayList<Line> lineQueue_S = new ArrayList<Line>();
	ArrayList<Double> sortedIntersections_L = new ArrayList<Double>();
	ArrayList<Point> allPoints = new ArrayList<Point>();
	
	public final int LEFT_END_POINT = 0;
	public final int RIGHT_END_POINT = 1;
	public final int INTERSECTION_POINT = 2;
	public static int pointID = 200;
	
	public static void main(String args[])
	{
		
	}
	
	public void beginSweepLineAlgo()
	{
		double x;
		Point p = null;
		
		ArrayList<Line> allLines = new ArrayList<Line>();
		
		createInititalQueueWithAbscissae(allLines);
		
		while(initialPoints_E.size() > 0)
		{
			x = initialPoints_E.get(0).getxValue();
			
			for (Point findP: allPoints)
			{
				if(initialPoints_E.get(0).getParentPointId() == findP.getPointId())
				{
					p = findP;
					break;
				}
				
			}
			
			if(p.getEventType() == LEFT_END_POINT)
			{
				
			}
			else if(p.getEventType() == RIGHT_END_POINT)
			{
				
			}
			else if(p.getEventType() == INTERSECTION_POINT)
			{
				
			}
		}
		
	}
	
	public void createInititalQueueWithAbscissae(ArrayList<Line> lines)
	{
		for(Line ln: lines)
		{
			initialPoints_E.add(ln.getStartP().getX());
			initialPoints_E.add(ln.getEndP().getX());	
		}
		
		sortTheInitialQueue();	
	}
	
	public Point checkGetIntersection(Line l1, Line l2)
	{
		double A1, A2, B1, B2, C1, C2, x = 0.0, y = 0.0, determinant;
		
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
		}
		else
		{
			x =  -200000;
		}
		
		Point newPoint = new Point(new Abscissae(l1.getLineId(), pointID, x), y, 
				INTERSECTION_POINT, pointID, l1.getLineId());
		
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

}
