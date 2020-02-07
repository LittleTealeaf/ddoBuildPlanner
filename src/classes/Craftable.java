package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A representation of a craftable item in DDO, where there's multiple enchantment choices given
 * @author Tealeaf
 * @see Craftref
 */
public class Craftable {

	private String name;

	private String uuid;

	private List<Enchref> choices;

	/**
	 * Creates an empty {@code Craftable} object<br><br>
	 * This will create an empty {@link Craftable} object with a random {@code UUID}
	 * @see #Craftable(String)
	 * @see #Craftable(String, String)
	 * @see #Craftable(String, String, List)
	 */
	public Craftable() {
		this(UUID.randomUUID().toString());
	}

	/**
	 * Creates a {@code Craftable} object with given parameters<br>
	 * <br>This will create an empty {@link Craftable} object, using the given {@code UUID} as the new object's {@code UUID}
	 * @param uuid The {@code UUID} of the new {@link Craftable} object
	 * @see #Craftable()
	 * @see #Craftable(String, String)
	 * @see #Craftable(String, String, List)
	 */
	public Craftable(String uuid) {
		this("", uuid);
	}

	/**
	 * Creates a {@Code Craftable} object with given parameters<br>
	 * <br>
	 * This creates an empty {@link Craftable} object that's given a {@code name} and {@code UUID}
	 * @param name The {@link String string} representation of the new {@link Craftable craftable} object
	 * @param uuid The {@code UUID} to give the new {@link Craftable craftable} object
	 * @see #Craftable()
	 * @see #Craftable(String)
	 * @see #Craftable(String, String, List)
	 */
	public Craftable(String name, String uuid) {
		this(name, uuid, new ArrayList<>());
	}

	/**
	 * Creates a {@code Craftable} object with given parameters<br><br>
	 * This creates a {@link Craftable} object that's given a {@code name}, {@code UUID}, and assigned a list of {@link Enchref Enchrefs}
	 * @param name The {@link String string} representation of the new {@link Craftable craftable} object
	 * @param uuid The {@code UUID} to give the new {@link Craftable craftable} object
	 * @param choices A List of {@link Enchref Enchrefs} to assign the new {@link Craftable craftable} object
	 * @see Enchref
	 * @see #Craftable()
	 * @see #Craftable(String)
	 * @see #Craftable(String, String)
	 */
	public Craftable(String name, String uuid, List<Enchref> choices) {
		this.name = name;
		this.uuid = uuid;
		this.choices = choices;
	}

	/**
	 * Gets the name of the {@code Craftable}
	 * @return Name of the {@link Craftable}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the {@code Craftable}
	 * @param name Name to give the {@link Craftable}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the unique UUID of the {@code Craftable}
	 * @return UUID of the {@link Craftable}
	 */
	public String getUUID() {
		return uuid;
	}

	/**
	 * Gives the {@code Craftable} a new UUID
	 * @param uuid New UUID of the {@link Craftable}
	 */
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gives the {@code Craftable} a new random UUID<br>
	 * This uses the {@link UUID#randomUUID()#toString()} FUNCTION
	 */
	public void newUUID() {
		this.uuid = UUID.randomUUID().toString();
	}

	/**
	 * Gets all the choices of the {@code Craftable}
	 * @return {@link Craftable} Choices
	 * @see Enchref
	 */
	public List<Enchref> getChoices() {
		return choices;
	}

	/**
	 * Gets the {@code Enchref} of the {@code Craftable} with a choice index
	 * @param index Index choice of the {@link Craftable}
	 * @return {@link Enchref} at that selection<br>Null if index is out of range
	 * @see Enchref
	 */
	public Enchref getChoice(int index) {
		if(index < choices.size() && index >= 0) return choices.get(index);
		else return null;
	}

	/**
	 * Sets the choices of the {@code Craftable}
	 * @param choices List of {@link Enchref} options
	 * @see Enchref
	 */
	public void setChoices(List<Enchref> choices) {
		this.choices = choices;
	}

	/**
	 * Adds a {@code Enchref} to the {@code Craftable} choices
	 * @param choice {@link Enchref} to add to the {@link Craftable}
	 * @see Enchref
	 */
	public void addChoice(Enchref choice) {
		this.choices.add(choice);
	}

	/**
	 * Removes a {@code Enchref} to the {@code Craftable} choices
	 * @param choice {@link Enchref} to remove from the {@link Craftable}
	 * @see Enchref
	 */
	public void removeChoice(Enchref choice) {
		this.choices.remove(choice);
	}

	/**
	 * Updates a {@code Enchref} in the {@code Craftable} choices. Adds the new {@code Enchref} if the old {@code Enchref} is not currently in the list
	 * @param from {@link Enchref} currently in the {@link Craftable} choices to be replaced
	 * @param to {@link Enchref} to replace the <code>from</code> variable with.
	 * @see Enchref
	 */
	public void updateChoice(Enchref from, Enchref to) {
		if(choices.contains(from)) choices.set(choices.indexOf(from), to);
		else choices.add(to);
	}
	
	@Override
	public String toString() {
		return name + " [" + uuid + "]";
	}
}
