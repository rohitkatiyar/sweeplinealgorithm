import java.util.ArrayList;

public class UnitTest {
	
	public final int LEFT_END_POINT = 0;
	public final int RIGHT_END_POINT = 1;
	public final int INTERSECTION_POINT = 2;
	public final int NULL_ID = -100000;
	
	ArrayList<Line> lines = new ArrayList<Line>();
	
	public static void main(String args[])
	{
		UnitTest ut = new UnitTest();
		
		ut.testCheckGetIntersection(new SweepLine());
	}
	
	public ArrayList<Line> fillTestValues()
	{
		int lineId = 0;
		int pointId = 0;
		
		Line l1 = new Line(new Point(
				           new Abscissae(lineId, NULL_ID, pointId, 2),4,LEFT_END_POINT,pointId++,lineId
				           ),
				           new Point(
				           new Abscissae(lineId, NULL_ID, pointId, 8),10,RIGHT_END_POINT,pointId++,lineId),lineId);
		
		lineId++;
		
		Line l2 = new Line(new Point(
		           new Abscissae(lineId, NULL_ID, pointId, 2),6,LEFT_END_POINT,pointId++,lineId
		           ),
		           new Point(
		           new Abscissae(lineId, NULL_ID, pointId, 12),8,RIGHT_END_POINT,pointId++,lineId),lineId);

        lineId++;
        
        Line l3 = new Line(new Point(
		           new Abscissae(lineId,NULL_ID, pointId, 2),4,LEFT_END_POINT,pointId++,lineId
		           ),
		           new Point(
		           new Abscissae(lineId, NULL_ID, pointId, 10),10,RIGHT_END_POINT,pointId++,lineId),lineId);

        lineId++;
        
        Line l4 = new Line(new Point(
		           new Abscissae(lineId, NULL_ID, pointId, 2),8,LEFT_END_POINT,pointId++,lineId
		           ),
		           new Point(
		           new Abscissae(lineId, NULL_ID, pointId, 4),18,RIGHT_END_POINT,pointId++,lineId),lineId);

        lineId++;
		
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(l4);
		
		return lines;
		
	}

	public void testCheckGetIntersection(SweepLine sl)
	{
        ArrayList<Line> testLines;
		
		testLines = fillTestValues();
		
		Point tmp = null;
		
		tmp = sl.checkGetIntersection(testLines.get(0), testLines.get(1));
		
		if(tmp != null)
		{
			System.out.println("Intersection between L1 and L2: " + tmp.getX().getxValue());
		}
		else
		{
			System.out.println("Intersection between L1 and L2 is NULL ");
		}
		
		tmp = sl.checkGetIntersection(testLines.get(1), testLines.get(2));
		if(tmp != null)
		{
			System.out.println("Intersection between L2 and L3: " + tmp.getX().getxValue());
		}
		else
		{
			System.out.println("Intersection between L2 and L3 is NULL ");
		}
		
		tmp = sl.checkGetIntersection(testLines.get(0), testLines.get(2));
		
		if(tmp != null)
		{
			System.out.println("Intersection between L1 and L3: " + tmp.getX().getxValue());
		}
		else
		{
			System.out.println("Intersection between L1 and L3 is NULL ");
		}
		
		tmp = sl.checkGetIntersection(testLines.get(0), testLines.get(3));
		
		if(tmp != null)
		{
			System.out.println("Intersection between L1 and L4: " + tmp.getX().getxValue());
		}
		else
		{
			System.out.println("Intersection between L1 and L4 is NULL ");
		}
		
		tmp = sl.checkGetIntersection(testLines.get(1), testLines.get(3));
		
		if(tmp != null)
		{
			System.out.println("Intersection between L2 and L4: " + tmp.getX().getxValue());
		}
		else
		{
			System.out.println("Intersection between L2 and L4 is NULL ");
		}
		
		tmp = sl.checkGetIntersection(testLines.get(2), testLines.get(3));
		
		if(tmp != null)
		{
			System.out.println("Intersection between L3 and L4: " + tmp.getX().getxValue());
		}
		else
		{
			System.out.println("Intersection between L3 and L4 is NULL ");
		}
		
		
		sl.createInititalQueueWithAbscissae(lines);
	}

}
