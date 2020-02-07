package classes;

import java.util.UUID;

/**
 * A reference to a {@link Craftable} object. Works by referencing it's UUID and index of a choice
 * to get the {@link Enchref}.
 *
 * @author Tealeaf
 */
public class Craftref {

	/**
	 * UUID of the referenced {@link Craftable}
	 */
	private final String uuid;
	/**
	 * Index of the Crafting Choice
	 *
	 * @see Craftable#getChoice(int)
	 */
	private int index;

	/**
	 * Creates a {@link Craftref} object with the given {@link Craftable} and initial {@link #index}
	 *
	 * @param craftable {@link Craftable} to copy
	 * @param index     {@link #index} of initial choice
	 */
	public Craftref(Craftable craftable, int index) {
		this.index = index;
		this.uuid = craftable.getUUID();
	}

	/**
	 * Creates a {@link Craftref} object with a given {@link UUID} and {@code index}
	 *
	 * @param uuid  {@link #uuid} of the {@link Craftable}
	 * @param index {@link #index} of initial choice
	 */
	public Craftref(String uuid, int index) {
		this.index = index;
		this.uuid = uuid;
	}

	/**
	 * Gets the current {@link #index} of the {@link Craftref}
	 * @return Current {@link #index}
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the {@link #index} of the {@link Craftref}
	 * @param index New {@link #index} of the {@link Craftref}
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Gets the {@link #uuid} of the {@link Craftref}
	 * @return {@link #uuid} of the {@link Craftref}
	 */
	public String getUUID() {
		return uuid;
	}
}
