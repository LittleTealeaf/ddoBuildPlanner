package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Craftable {

	private String name;

	private String uuid;

	private List<Enchantment> choices;

	public Craftable() {
		this(UUID.randomUUID().toString());
	}

	public Craftable(String uuid) {
		this("", uuid);
	}

	public Craftable(String name, String uuid) {
		this(name, uuid, new ArrayList<Enchantment>());
	}

	public Craftable(String name, String uuid, List<Enchantment> choices) {
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

	public void newUUID() {
		this.uuid = UUID.randomUUID().toString();
	}

	public List<Enchantment> getChoices() {
		return choices;
	}

	public Enchantment getChoice(int index) {
		if(index < choices.size()) return choices.get(index);
		else return null;
	}

	public void setChoices(List<Enchantment> choices) {
		this.choices = choices;
	}

	public void addChoice(Enchantment choice) {
		this.choices.add(choice);
	}

	public void removeChoice(Enchantment choice) {
		this.choices.remove(choice);
	}
}
