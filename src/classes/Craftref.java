package classes;

public class Craftref {

	private int index;
	private transient Craftable craftable;

	public Craftref(Craftable craftable, int index) {
		this.index = index;
		this.craftable = craftable;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Craftable getCraftable() {
		return craftable;
	}
}
