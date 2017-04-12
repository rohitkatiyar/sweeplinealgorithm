
public class Point {
	
	double y;
	int eventType, pointId, lineId; //0-Left end point, 1- right end point, 2- intersection point
	Abscissae x;

	
	public Point(Abscissae x, double y, int eventType, int pointId, int lineId) {
		super();
		this.y = y;
		this.eventType = eventType;
		this.pointId = pointId;
		this.lineId = lineId;
		this.x = x;
	}
	
	public int getLineId() {
		return lineId;
	}
	public void setLineId(int lineId) {
		this.lineId = lineId;
	}
	public Abscissae getX() {
		return x;
	}
	public void setX(Abscissae x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	@Override
	public String toString() {
		return "Point [y=" + y + ", eventType=" + eventType + ", pointId=" + pointId + ", x=" + x.getxValue() + "]";
	}
	
	

}
