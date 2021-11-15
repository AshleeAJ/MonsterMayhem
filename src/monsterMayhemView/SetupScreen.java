package monsterMayhemView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import monsterMayhemController.SessionMemory;
import monsterMayhem.Element;
import monsterMayhem.Monster;
import monsterMayhem.Player;
import monsterMayhemController.SessionController;

@SuppressWarnings("serial")
public class SetupScreen extends JFrame {
	private JLabel lblHeader;
	private JButton btnBack;
	private JButton btnStart;
	private JTabbedPane paneSelect;
	
	private static JTextField txtName;
	private static JTextField txtPower;
	private static JTextField txtLife;
	private static JTextField txtSpeed;
	private static JComboBox<String> comboWeapons;
	private static JList<Player> listPartyMember;
	private static DefaultListModel<Player> listModelPlayer;
	
	private static JComboBox<String> comboMonsters;
	private static JList<Monster> listMonsterGroup;
	private static DefaultListModel<Monster> listModelMonster;
		
	public SetupScreen() {
		super("Monster Mayhem: Setup");
		super.setLayout(new BorderLayout());
		lblHeader = new JLabel("Setup Page");
		this.add(lblHeader, BorderLayout.NORTH);
		
		btnBack = new JButton("Back");
		btnStart = new JButton("Start");
		JPanel panelBtns = new JPanel();
		panelBtns.setLayout(new FlowLayout());
		panelBtns.add(btnBack);
		panelBtns.add(btnStart);
		this.add(panelBtns, BorderLayout.SOUTH);
		
		JPanel panelPlayerContainer = new JPanel();
		
		JPanel panelPlayerInfo = new JPanel();
		panelPlayerInfo.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel lblName = new JLabel("Name: ");
		c.gridx = 0;
		c.gridy = 0;
		panelPlayerInfo.add(lblName, c);
		txtName = new JTextField(20);
		c.gridx = 1;
		c.gridy = 0;
		panelPlayerInfo.add(txtName, c);
		JLabel lblPower = new JLabel("Power: ");
		c.gridx = 0;
		c.gridy = 1;
		panelPlayerInfo.add(lblPower, c);
		txtPower = new JTextField(10);
		c.gridx = 1;
		c.gridy = 1;
		panelPlayerInfo.add(txtPower, c);
		JLabel lblLife = new JLabel("Life: ");
		c.gridx = 0;
		c.gridy = 2;
		panelPlayerInfo.add(lblLife, c);
		txtLife = new JTextField(10);
		c.gridx = 1;
		c.gridy = 2;
		panelPlayerInfo.add(txtLife, c);
		JLabel lblSpeed = new JLabel("Speed: ");
		c.gridx = 0;
		c.gridy = 3;
		panelPlayerInfo.add(lblSpeed, c);
		txtSpeed = new JTextField(10);
		c.gridx = 1;
		c.gridy = 3;
		panelPlayerInfo.add(txtSpeed, c);
		JLabel lblWeapon = new JLabel("Weapon: ");
		c.gridx = 0;
		c.gridy = 4;
		panelPlayerInfo.add(lblWeapon, c);
		comboWeapons = new JComboBox<String>();
		comboWeapons.addItem(Element.getIce().getElementName());
		comboWeapons.addItem(Element.getWater().getElementName());
		comboWeapons.addItem(Element.getFire().getElementName());
		c.gridx = 1;
		c.gridy = 4;
		panelPlayerInfo.add(comboWeapons, c);
		
		JPanel panelPlayerParty = new JPanel();
		panelPlayerParty.setLayout(new GridBagLayout());
		JLabel lblPartyMember = new JLabel("Party Members: ");
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 0;
		panelPlayerParty.add(lblPartyMember, c);
		listModelPlayer = new DefaultListModel<Player>();
		listPartyMember = new JList<Player>(listModelPlayer);
		c.gridx = 0;
		c.ipady = 15; 
		c.gridy = 1;
		panelPlayerParty.add(listPartyMember, c);
		JButton btnAddPlayer = new JButton("Add Player");
		c.gridx = 0;
		c.ipady = 0; 
		c.gridwidth = 1;
		c.gridy = 2;
		panelPlayerParty.add(btnAddPlayer, c);
		JButton btnRemovePlayer = new JButton("Remove Player");
		c.gridx = 1;
		c.gridy = 2;
		panelPlayerParty.add(btnRemovePlayer, c);
		
		panelPlayerContainer.add(panelPlayerInfo);
		panelPlayerContainer.add(panelPlayerParty);

		JPanel panelMonster = new JPanel();
		panelMonster.setLayout(new GridBagLayout());
		
		JLabel lblMonster = new JLabel("Monster");
		c.gridx = 0;
		c.gridy = 0;
		panelMonster.add(lblMonster, c);
		comboMonsters = new JComboBox<String>();
		for (Monster m : Monster.getMonsters()) {
			comboMonsters.addItem(m.getName());
		}
		c.gridx = 0;
		c.gridy = 1;
		panelMonster.add(comboMonsters, c);
		JLabel lblMonsterGroup = new JLabel("Monster Horde: ");
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 0;
		panelMonster.add(lblMonsterGroup, c);
		listModelMonster = new DefaultListModel<Monster>();
		listMonsterGroup = new JList<Monster>(listModelMonster);
		c.gridx = 1;
		c.ipady = 15;
		c.gridy = 1;
		panelMonster.add(listMonsterGroup, c);
		JButton btnAddMonster = new JButton("Add Monster");
		c.gridx = 1;
		c.ipady = 0; 
		c.gridwidth = 1;
		c.gridy = 2;
		panelMonster.add(btnAddMonster, c);
		JButton btnRemoveMonster = new JButton("Remove Monster");
		c.gridx = 2;
		c.gridy = 2;
		panelMonster.add(btnRemoveMonster, c);
		
		paneSelect = new JTabbedPane();
		paneSelect.addTab("Players", panelPlayerContainer);
		paneSelect.addTab("Monsters", panelMonster);
		
		this.add(paneSelect, BorderLayout.CENTER);
		pack();
		
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SetupScreen.super.dispose();
				new HomeScreen().setVisible(true);
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SessionMemory.getInstance().addGeneratedTurns(SessionController.getInstance().generateTurns());
				SetupScreen.super.dispose();
				new BattleScreen().setVisible(true);
			}
		});
		
		btnAddPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player player = SessionController.getInstance().createPlayer();
				SessionMemory.getInstance().addPlayer(player);
				SessionController.updateListModelAdd(player);
				
			}
		});
		
		btnRemovePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player player = getPartyMembers().getSelectedValue();
				SessionMemory.getInstance().removePlayer(player);
				SessionController.updateListModelRemove(player);
			}
		});
		
		btnAddMonster.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Monster monster = SessionController.getInstance().findMonster(getSelectedMonster());
				SessionMemory.getInstance().addMonster(monster);
				SessionController.updateListModelAdd(monster);
			}
		});
		
		btnRemoveMonster.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Monster monster = getMonsterGroup().getSelectedValue();
				SessionMemory.getInstance().removeMonster(monster);
				SessionController.updateListModelRemove(monster);
			}
		});
	
	}
	
	public static String getInputName() {
		return txtName.getText();
	}
	
	public static int getInputSpeed() {
		return Integer.parseInt(txtSpeed.getText());
	}
	
	public static int getInputPower() {
		return Integer.parseInt(txtPower.getText());
	}
	
	public static int getInputLife() {
		return Integer.parseInt(txtLife.getText());
	}
	
	public static String getInputWeapon() {
		return comboWeapons.getSelectedItem().toString();
	}
	
	public static JList<Player> getPartyMembers() {
		return listPartyMember;
	};
	
	public static DefaultListModel<Player> getPartyMembersModel() {
		return listModelPlayer;
	};
	
	public static JList<Monster> getMonsterGroup() {
		return listMonsterGroup;
	};
	
	public static DefaultListModel<Monster> getMonsterGroupModel() {
		return listModelMonster;
	};
	
	public static String getSelectedMonster() {
		return comboMonsters.getSelectedItem().toString();
	}
	
}
