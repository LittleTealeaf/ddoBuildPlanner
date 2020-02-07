package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class SetBonus {

	private final String uuid;
	private String name;
	private List<BonusTier> bonusTiers;

	public SetBonus() {
		this(UUID.randomUUID().toString());
	}

	public SetBonus(String uuid) {
		this(uuid, "");
	}

	public SetBonus(String uuid, String name) {
		this(uuid, name, new ArrayList<>());
	}

	public SetBonus(String uuid, String name, List<BonusTier> bonusTiers) {
		this.uuid = uuid;
		this.name = name;
		this.bonusTiers = bonusTiers;
	}

	public String getUUID() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BonusTier> getBonusTiers() {
		return bonusTiers;
	}

	public void setBonusTiers(List<BonusTier> bonusTiers) {
		this.bonusTiers = bonusTiers;
	}

	public void addBonusTier(BonusTier bonusTier) {
		this.bonusTiers.add(bonusTier);
	}

	public void removeBonusTier(BonusTier bonusTier) {
		this.bonusTiers.remove(bonusTier);
	}

	public List<Enchref> getEnchantments(int pieceCount) {
		BonusTier tier = null;

		for(BonusTier t : bonusTiers) {
			tier = ((tier == null) || (tier.getPieceCount() < t.getPieceCount() && t.getPieceCount() <= pieceCount)) ? t : tier;
		}

		return tier.getBonuses();
	}

	public static class BonusTier {

		private int pieceCount;
		private List<Enchref> bonuses;

		public BonusTier() {
			this(0);
		}

		public BonusTier(int pieceCount) {
			this(pieceCount, new ArrayList<>());
		}

		public BonusTier(int pieceCount, List<Enchref> bonuses) {
			this.pieceCount = pieceCount;
			this.bonuses = bonuses;
		}

		public int getPieceCount() {
			return pieceCount;
		}

		public void setPieceCount(int pieceCount) {
			this.pieceCount = pieceCount;
		}

		public List<Enchref> getBonuses() {
			return bonuses;
		}

		public void setBonuses(List<Enchref> bonuses) {
			this.bonuses = bonuses;
		}

		public void addBonus(Enchref bonus) {
			bonuses.add(bonus);
		}

		public void removeBonus(Enchref bonus) {
			bonuses.remove(bonus);
		}
	}

	public static class SetBonusExport {

		private final SetBonus setbonus;
		private final List<Enchantment> enchantments;

		@SuppressWarnings("unlikely-arg-type")
		public SetBonusExport(SetBonus setbonus) {
			this.setbonus = setbonus;

			enchantments = new ArrayList<>();

			for (BonusTier tier : setbonus.getBonusTiers())
				for (Enchref e : tier.getBonuses())
					if (!enchantments.contains(e)) {
						enchantments.add(e.getEnchantment());
					}
		}

		public SetBonus importSetBonus() {
			Enchantments.addEnchantments(enchantments);
			return setbonus;
		}
	}
}
