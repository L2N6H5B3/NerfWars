import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SettingsWindow {

	// Initializes settings window
	static JFrame settingWindow;
	
	// Initializes settings panel
	static JPanel contentPanel;
	
	// Initializes text labels
	static JLabel teamNameLabel, respawnTimeLabel;
	
	// Initializes text fields
	static JTextField teamNameField, respawnTimeField;
	
	// Initializes buttons
	static JButton saveButton, cancelButton;
	
	public SettingsWindow() {
	
		
		settingWindow = new JFrame("Settings");
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(4, 1));
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		contentPanel.setBorder(padding);

		teamNameField = new JTextField(MainClass.getTeamName(),10);
	    respawnTimeField = new JTextField(Integer.toString(MainClass.getRespawnTime()),10);
	    
	    teamNameLabel = new JLabel("Team Name: ");
	    respawnTimeLabel= new JLabel("Respawn Time: ");
	    
	    contentPanel.add(teamNameLabel);
	    contentPanel.add(teamNameField);
	    
	    contentPanel.add(respawnTimeLabel);
	    contentPanel.add(respawnTimeField);
	    
	    
	    saveButton = new JButton("Save");
	    saveButton.addActionListener((ActionEvent event) -> {
	    	
	    	MainClass.setTeamName(teamNameField.getText());
	    	MainClass.setRespawnTime(Integer.parseInt(respawnTimeField.getText()));
	    	
	    	
	    	settingWindow.dispose();
        });
	    
	    cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener((ActionEvent event) -> {
	    	settingWindow.dispose();
        });
	    
	    contentPanel.add(cancelButton);
	    contentPanel.add(saveButton);
	    
	    settingWindow.setContentPane(contentPanel);
	    settingWindow.pack();
	    
	    
	}
	
	
	public void showSettings() {
		teamNameField.setText(MainClass.getTeamName());
	    respawnTimeField.setText(Integer.toString(MainClass.getRespawnTime()));
	    settingWindow.setVisible(true);
	}

}
