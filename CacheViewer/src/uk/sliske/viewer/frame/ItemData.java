package uk.sliske.viewer.frame;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import com.sk.cache.wrappers.ItemDefinition;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ItemData extends JPanel {
	private static final long	serialVersionUID	= 1L;

	private final JLabel		lblName_1;
	private final JLabel		lblId_1;
	private final JLabel		lblMembers_1;
	private final JLabel		lblStackable_1;
	private final JLabel		lblEdible_1;
	private final JLabel		lblTradeable_1;
	private final JLabel		lblSlot_1;
	private final JLabel		lblNoted_1;

	/**
	 * Create the panel.
	 */
	public ItemData(final ItemDefinition item) {
		lblName_1 = new JLabel(item.name);
		lblId_1 = new JLabel(item.getId() + "");
		lblMembers_1 = new JLabel(item.members + "");
		lblNoted_1 = new JLabel("" + item.noted);
		lblStackable_1 = new JLabel("" + item.stackable);
		lblEdible_1 = new JLabel("" + item.edible);
		lblTradeable_1 = new JLabel("" + item.tradeable);
		lblSlot_1 = new JLabel("" + item.slot);

		final double min = Double.MIN_VALUE;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 25, 150, 5, 150, 60, 150, 5, 150, 25 };
		gridBagLayout.rowHeights = new int[] { 30, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 30 };
		gridBagLayout.columnWeights = new double[] { min, min, min, min, min, min, min, min, min };
		gridBagLayout.rowWeights = new double[] { min, min, min, min, min, min, min, min, min, min, min, min, min, min, min, min, min, min, min, min, min };
		setLayout(gridBagLayout);

		JLabel lblName = new JLabel("name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		add(lblName, gbc_lblName);

		GridBagConstraints gbc_lblName_1 = new GridBagConstraints();
		gbc_lblName_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblName_1.gridx = 3;
		gbc_lblName_1.gridy = 2;
		add(lblName_1, gbc_lblName_1);

		JLabel lblId = new JLabel("id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 1;
		gbc_lblId.gridy = 4;
		add(lblId, gbc_lblId);

		GridBagConstraints gbc_lblId_1 = new GridBagConstraints();
		gbc_lblId_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblId_1.gridx = 3;
		gbc_lblId_1.gridy = 4;
		add(lblId_1, gbc_lblId_1);

		JLabel lblMembers = new JLabel("members");
		GridBagConstraints gbc_lblMembers = new GridBagConstraints();
		gbc_lblMembers.insets = new Insets(0, 0, 5, 5);
		gbc_lblMembers.gridx = 1;
		gbc_lblMembers.gridy = 6;
		add(lblMembers, gbc_lblMembers);

		GridBagConstraints gbc_lblMembers_1 = new GridBagConstraints();
		gbc_lblMembers_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMembers_1.gridx = 3;
		gbc_lblMembers_1.gridy = 6;
		add(lblMembers_1, gbc_lblMembers_1);

		JLabel lblNoted = new JLabel("noted");
		GridBagConstraints gbc_lblNoted = new GridBagConstraints();
		gbc_lblNoted.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoted.gridx = 1;
		gbc_lblNoted.gridy = 8;
		add(lblNoted, gbc_lblNoted);

		GridBagConstraints gbc_lblNoted_1 = new GridBagConstraints();
		gbc_lblNoted_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoted_1.gridx = 3;
		gbc_lblNoted_1.gridy = 8;
		add(lblNoted_1, gbc_lblNoted_1);

		JLabel lblStackable = new JLabel("stackable");
		GridBagConstraints gbc_lblStackable = new GridBagConstraints();
		gbc_lblStackable.insets = new Insets(0, 0, 5, 5);
		gbc_lblStackable.gridx = 1;
		gbc_lblStackable.gridy = 10;
		add(lblStackable, gbc_lblStackable);

		GridBagConstraints gbc_lblStackable_1 = new GridBagConstraints();
		gbc_lblStackable_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblStackable_1.gridx = 3;
		gbc_lblStackable_1.gridy = 10;
		add(lblStackable_1, gbc_lblStackable_1);

		JLabel lblEdible = new JLabel("edible");
		GridBagConstraints gbc_lblEdible = new GridBagConstraints();
		gbc_lblEdible.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdible.gridx = 1;
		gbc_lblEdible.gridy = 12;
		add(lblEdible, gbc_lblEdible);

		GridBagConstraints gbc_lblEdible_1 = new GridBagConstraints();
		gbc_lblEdible_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdible_1.gridx = 3;
		gbc_lblEdible_1.gridy = 12;
		add(lblEdible_1, gbc_lblEdible_1);

		JLabel lblTradeable = new JLabel("tradeable");
		GridBagConstraints gbc_lblTradeable = new GridBagConstraints();
		gbc_lblTradeable.insets = new Insets(0, 0, 5, 5);
		gbc_lblTradeable.gridx = 1;
		gbc_lblTradeable.gridy = 14;
		add(lblTradeable, gbc_lblTradeable);

		GridBagConstraints gbc_lblTradeable_1 = new GridBagConstraints();
		gbc_lblTradeable_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTradeable_1.gridx = 3;
		gbc_lblTradeable_1.gridy = 14;
		add(lblTradeable_1, gbc_lblTradeable_1);

		JLabel lblSlot = new JLabel("slot");
		GridBagConstraints gbc_lblSlot = new GridBagConstraints();
		gbc_lblSlot.insets = new Insets(0, 0, 5, 5);
		gbc_lblSlot.gridx = 1;
		gbc_lblSlot.gridy = 16;
		add(lblSlot, gbc_lblSlot);

		GridBagConstraints gbc_lblSlot_1 = new GridBagConstraints();
		gbc_lblSlot_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSlot_1.gridx = 3;
		gbc_lblSlot_1.gridy = 16;
		add(lblSlot_1, gbc_lblSlot_1);

	}

}
