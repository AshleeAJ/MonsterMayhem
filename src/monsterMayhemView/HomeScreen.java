package monsterMayhemView;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class HomeScreen extends JFrame {
	private JLabel lblHeader;
	private JButton btnStart;
	private JButton btnQuit;
	
	public HomeScreen() {
		super("Monster Mayhem: Home");
		super.setLayout(new GridLayout(3,0));
		lblHeader = new JLabel("Monster Mayhem");
		btnStart = new JButton("Start New");
		btnQuit = new JButton("Quit");
		this.add(lblHeader);
		this.add(btnStart);
		this.add(btnQuit);
		pack();
	
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HomeScreen.super.dispose();
				new SetupScreen().setVisible(true);
			}
		});
		
		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HomeScreen.super.dispose();
			}
		});
	}

}
