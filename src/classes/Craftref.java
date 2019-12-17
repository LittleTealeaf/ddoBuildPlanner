package classes;

public class Craftref {

	private int index;
	private String uuid;

	public Craftref(Craftable craftable, int index) {
		this.index = index;
		this.uuid = craftable.getUUID();
	}
	
	public Craftref(String uuid, int index) {
		this.index = index;
		this.uuid = uuid;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getUUID() {
		return uuid;
	}
}
