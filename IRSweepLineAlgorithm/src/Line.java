
public class Line {
	SLPoint startP, endP;
    private int lineId;
    
	public Line(SLPoint startP, SLPoint endP, int lineId) {
		super();
		this.startP = startP;
		this.endP = endP;
		this.lineId = lineId;
	}

	public SLPoint getStartP() {
		return startP;
	}

	public void setStartP(SLPoint startP) {
		this.startP = startP;
	}

	public SLPoint getEndP() {
		return endP;
	}

	public void setEndP(SLPoint endP) {
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
