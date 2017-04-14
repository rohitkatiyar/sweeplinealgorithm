
public class Line {
	Point startP, endP;
    private int lineId;
    
	public Line(Point startP, Point endP, int lineId) {
		super();
		this.startP = startP;
		this.endP = endP;
		this.lineId = lineId;
	}

	public Point getStartP() {
		return startP;
	}

	public void setStartP(Point startP) {
		this.startP = startP;
	}

	public Point getEndP() {
		return endP;
	}

	public void setEndP(Point endP) {
		this.endP = endP;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	@Override
	public String toString() {
		return "Line [lineId=" + lineId + "]";
	}

}
