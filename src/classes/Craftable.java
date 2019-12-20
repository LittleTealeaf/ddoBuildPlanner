package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Craftable {

	private String name;

	private String uuid;

	private List<Enchref> choices;

	public Craftable() {
		this(UUID.randomUUID().toString());
	}

	public Craftable(String uuid) {
		this("", uuid);
	}

	public Craftable(String name, String uuid) {
		this(name, uuid, new ArrayList<Enchref>());
	}

	public Craftable(String name, String uuid, List<Enchref> choices) {
		this.name = name;
		this.uuid = uuid;
		this.choices = choices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUUID() {
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gives the crafting object a new random UUID
	 */
	public void newUUID() {
		this.uuid = UUID.randomUUID().toString();
	}

	public List<Enchref> getChoices() {
		return choices;
	}

	public Enchref getChoice(int index) {
		if(index < choices.size()) return choices.get(index);
		else return null;
	}

	public void setChoices(List<Enchref> choices) {
		this.choices = choices;
	}

	public void addChoice(Enchref choice) {
		this.choices.add(choice);
	}

	public void removeChoice(Enchref choice) {
		this.choices.remove(choice);
	}

	public void updateChoice(Enchref from, Enchref to) {
		if(choices.contains(from)) choices.set(choices.indexOf(from), to);
		else choices.add(to);
	}
}
