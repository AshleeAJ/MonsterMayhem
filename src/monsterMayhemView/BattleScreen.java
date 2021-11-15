package monsterMayhemView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import monsterMayhem.Entity;
import monsterMayhem.Monster;
import monsterMayhem.Player;
import monsterMayhemController.SessionController;
import monsterMayhemController.SessionMemory;

@SuppressWarnings("serial")
public class BattleScreen extends JFrame {
	JLabel lblTurnholderStats;
	JTextArea turnholderStats;
	JLabel lblAffectedStats;
	JTextArea affectedEntityStats;
	JLabel lblTurn;
	JComboBox<String> comboAction;
	JComboBox<String> comboActOn;
	JButton btnDoAction;
	
	public BattleScreen() {
		super("Monster Mayhem: Battle");
		super.setLayout(new BorderLayout());
		
		JPanel panelStats = new JPanel();
		panelStats.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		lblTurnholderStats = new JLabel();
		c.gridx = 0;
		c.ipadx = 15;
		c.gridy = 0;
		panelStats.add(lblTurnholderStats, c);
		turnholderStats = new JTextArea();
		turnholderStats.setEditable(false);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		turnholderStats.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		c.gridx = 0;
		c.gridy = 1;
		panelStats.add(turnholderStats, c);
		lblAffectedStats = new JLabel();
		c.gridx = 1;
		c.ipadx = 0;
		c.gridy = 0;
		panelStats.add(lblAffectedStats, c);
		affectedEntityStats = new JTextArea();
		affectedEntityStats.setEditable(false);
		affectedEntityStats.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		c.gridx = 1;
		c.gridy = 1;
		panelStats.add(affectedEntityStats, c);
		this.add(panelStats, BorderLayout.NORTH);
		
		JPanel panelTurn = new JPanel();
		panelTurn.setLayout(new GridBagLayout());
		lblTurn = new JLabel();
		c.gridx = 0;
		c.gridy = 0;
		panelTurn.add(lblTurn, c);
		comboAction = new JComboBox<String>();
		comboAction.addItem("Attack");
		comboAction.addItem("Heal");
		comboAction.addItem("Power-Up");
		c.gridx = 0;
		c.gridy = 1;
		panelTurn.add(comboAction, c);
		comboActOn = new JComboBox<String>();
		for (Monster m : SessionMemory.getInstance().getMonsters()) {
			comboActOn.addItem(m.getName());
		}
		for (Player p : SessionMemory.getInstance().getPlayers()) {
			comboActOn.addItem(p.getName());
		}
		c.gridx = 1;
		c.gridy = 1;
		panelTurn.add(comboActOn, c);
		btnDoAction = new JButton("Do");
		c.gridx = 2;
		c.gridy = 1;
		panelTurn.add(btnDoAction, c);
		
		this.add(panelTurn, BorderLayout.SOUTH);
		updateScreenValues();
		updateSelectedEntityStats();
		pack();
		
		btnDoAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SessionController.getInstance().checkActionLegality(
						SessionMemory.getInstance().getCurrentTurn(), getAction(), 
						SessionController.getInstance().findEntity(getEntityActOn()))) {
					if (SessionMemory.getInstance().getTurns().size() > 0) {
						boolean succeeded;
						if (getAction() == "Attack") {
							succeeded = SessionController.getInstance().Attack(SessionMemory.getInstance().getCurrentTurn(),
									SessionController.getInstance().findEntity(getEntityActOn()));
							if (!succeeded) {
								JOptionPane.showMessageDialog(panelTurn, "The attack missed!");
							}
							else {
								JOptionPane.showMessageDialog(panelTurn, SessionMemory.getInstance().getCurrentTurn() + " attacked "
										+ getEntityActOn());
								if (SessionController.getInstance().findEntity(getEntityActOn()).getHealth() <= 0 ) {
									Entity deadEntity = SessionController.getInstance().findEntity(getEntityActOn());
									comboActOn.removeItem(getEntityActOn());
									JOptionPane.showMessageDialog(panelTurn, deadEntity + " died.");
									SessionMemory.getInstance().removeSpecificTurn(deadEntity);
									if (deadEntity.getClass() == Monster.class) {
										SessionMemory.getInstance().removeMonster((Monster) deadEntity);
										checkBattleStatus();
									}
									else if (deadEntity.getClass() == Player.class) {
										SessionMemory.getInstance().removePlayer((Player) deadEntity);
										checkBattleStatus();
									}
								}
							}
						}
						else if (getAction() == "Heal") {
							succeeded = SessionController.getInstance().Heal(SessionMemory.getInstance().getCurrentTurn(),
									SessionController.getInstance().findEntity(getEntityActOn()));
							if (!succeeded) {
								JOptionPane.showMessageDialog(panelTurn, "The healing spell failed!");
							}
							else {
								JOptionPane.showMessageDialog(panelTurn, SessionMemory.getInstance().getCurrentTurn() + " healed "
										+ getEntityActOn());
							}
						}
						else if (getAction() == "Power-Up") {
							succeeded = SessionController.getInstance().PowerUp(SessionMemory.getInstance().getCurrentTurn());
							if (!succeeded) {
								JOptionPane.showMessageDialog(panelTurn, "Move failed.");
							}
							else {
								JOptionPane.showMessageDialog(panelTurn, SessionMemory.getInstance().getCurrentTurn() + " powered up.");
							}
						}
					}
					try {
						SessionMemory.getInstance().removeTurn();
						updateScreenValues();
					} catch (IndexOutOfBoundsException e1) {
						if (SessionMemory.getInstance().getTurns().size() <= 0 && SessionMemory.getInstance().getMonsters().size() > 0 
								&& SessionMemory.getInstance().getPlayers().size() > 0) {
							JOptionPane.showMessageDialog(panelTurn, "Battle is not finished, generating new turns...");
							SessionMemory.getInstance().addGeneratedTurns(SessionController.getInstance().generateTurns());
							updateScreenValues();
						}
					}
					
				}
				else {
					JOptionPane.showMessageDialog(panelTurn, "Illegal action.");
				}
			}
		});
		
		comboActOn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSelectedEntityStats();
			}
		});
		
		
	}
	
	protected void updateScreenValues() {
		lblTurnholderStats.setText(SessionMemory.getInstance().getCurrentTurn().getName() + "'s stats: ");
		turnholderStats.setText(SessionController.getInstance().displayStats(
				SessionMemory.getInstance().getCurrentTurn()));
		lblTurn.setText("It's " + SessionMemory.getInstance().getCurrentTurn().getName() + "'s turn.");
		updateSelectedEntityStats();
	}
	
	protected void updateSelectedEntityStats() {
		Entity display = null;
		ArrayList<Entity> entities = new ArrayList<Entity>();
		entities.addAll(SessionMemory.getInstance().getMonsters());
		entities.addAll(SessionMemory.getInstance().getPlayers());
		for (Entity e : entities) {
			if (comboActOn.getSelectedItem() == e.getName()) {
				display = e;
			}
		}
		try {
			lblAffectedStats.setText(display.getName() + "'s stats:");
			affectedEntityStats.setText(SessionController.getInstance().displayStats(display));
		}
		catch (NullPointerException e) {
			lblAffectedStats.setText("stats:");
			affectedEntityStats.setText(" ");
		}
		
	}
	
	protected void checkBattleStatus() {
		if (SessionMemory.getInstance().getMonsters().size() <= 0 ) {
			JOptionPane.showMessageDialog(btnDoAction, "Battle is finished. "
					+ "The players successfully defeated the monsters and may fight again if they wish.");
			new SetupScreen().setVisible(true);
			SessionMemory.getInstance().resetMemory();
			BattleScreen.super.dispose();
		}
		else if (SessionMemory.getInstance().getPlayers().size() <= 0) {
			JOptionPane.showMessageDialog(btnDoAction, "Battle is finished. The players could not defeat the monsters.");
			new HomeScreen().setVisible(true);
			SessionMemory.getInstance().resetMemory();
			BattleScreen.super.dispose();
		}
	}
	
	public String getAction() {
		return comboAction.getSelectedItem().toString(); 
	};
	
	public String getEntityActOn() {
		return comboActOn.getSelectedItem().toString();
	};
}
