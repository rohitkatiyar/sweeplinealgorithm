
public class Abscissae {
	
	int parentLineId, parentPointId;
	double xValue;

	
	public Abscissae(int parentLineId, int parentPointId, double xValue) {
		super();
		this.parentLineId = parentLineId;
		this.parentPointId = parentPointId;
		this.xValue = xValue;
	}

	public double getxValue() {
		return xValue;
	}

	public void setxValue(double xValue) {
		this.xValue = xValue;
	}

	public int getParentLineId() {
		return parentLineId;
	}

	public void setParentLineId(int parentLineId) {
		this.parentLineId = parentLineId;
	}

	public int getParentPointId() {
		return parentPointId;
	}

	public void setParentPointId(int parentPointId) {
		this.parentPointId = parentPointId;
	}

	@Override
	public String toString() {
		return "Abscissae [parentLineId=" + parentLineId + ", parentPointId=" + parentPointId + ", xValue=" + xValue
				+ "]";
	}
}
