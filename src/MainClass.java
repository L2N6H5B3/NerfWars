import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class MainClass {
	
	// Initializes variable for ticker.
	static TickerModule ticker;
	
	// Initializes variables ready for configuration file import.
	static int maxPlayers = 20;
	static int respawnTimer = 30;
	static int rows = 6;
	static int columns = 4;
	static Icon aliveIcon = new ImageIcon("Nerf_logo.png");
	static Icon deadIcon = new ImageIcon("RIP.jpg");
	static int aliveIconWidth = 108;
	static int deadIconWidth = 70;
	static int iconHeight = 60;
	static int buttonTextSize = 20;
	static int teamnameDeathsTextSize = 30;
	static int totalDeathsLimit = 30;
	static String team = "Team";
	static String serverIP;
	static int serverPort;
	static int totalDeathCount = 0;
	static String totalDeathsOutputLocation;
	static int listLength;

	// Initializes WindowFrame
	JFrame window;
	
	// Initializes Panels
	JPanel titleFrame, mainFrame;
	
	// Initializes Labels
	static JLabel teamName;

	JLabel totalDeaths;
	
	// Initializes MenuBar
	JMenuBar menubar;
	
	// Initializes settings window
	static SettingsWindow settings;
	
	// Initializes ArrayMaps
	static Map<String, Integer> buttonMap = new HashMap<String, Integer>();
	static Map<Integer, String> playerMap = new HashMap<Integer, String>();	
	// Initializes Arrays
	static PlayerModule[] playerArray;
	JButton[] buttonArray;
	
	// Initializes Deaths output
	TextOutput deaths;
	
	// Initializes Network Module
	static NetworkModule networkOut;
	
	public MainClass() throws FileNotFoundException {
		
		// Import configuration file:
		Scanner configFile = new Scanner(new File("configuration.cfg"));
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		maxPlayers = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		respawnTimer = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		rows = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		columns = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		aliveIcon = new ImageIcon(configFile.nextLine());
		configFile.nextLine(); // Skips one line
		deadIcon = new ImageIcon(configFile.nextLine());
		configFile.nextLine(); // Skips one line
		iconHeight = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		buttonTextSize = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		teamnameDeathsTextSize = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		totalDeathsLimit = (int)configFile.nextShort();
		configFile.nextLine(); configFile.nextLine(); // Skips two lines
		team = team+" "+configFile.nextLine();
		configFile.nextLine(); // Skips one lines
		totalDeathsOutputLocation = configFile.nextLine();
		configFile.nextLine(); // Skips one lines
		serverIP = configFile.nextLine();
		configFile.nextLine(); // Skips one lines
		serverPort = Integer.parseInt(configFile.nextLine());
		configFile.close();

		// Adjusts icon size
		Image img = ((ImageIcon) aliveIcon).getImage() ;  
		Image newimg = img.getScaledInstance(aliveIconWidth,iconHeight,java.awt.Image.SCALE_SMOOTH);  
		aliveIcon = new ImageIcon(newimg);
		
		Image img2 = ((ImageIcon) deadIcon).getImage() ;  
		Image newimg2 = img2.getScaledInstance(deadIconWidth,iconHeight,java.awt.Image.SCALE_SMOOTH);  
		deadIcon = new ImageIcon(newimg2);
		
		// Creates ticker module
		ticker = new TickerModule();
		
		// Initializes settings window
		settings = new SettingsWindow();
		
		
		
		
                
		// Initializes window
		window = new JFrame("Nerf Deathmatch!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setLayout(new BorderLayout());
	    window.setBounds(0,0,200,200);
	    window.setUndecorated(true);
	    
		// Initializes panels
		JPanel titleFrame = new JPanel();
		JPanel mainFrame = new JPanel();
	    titleFrame.setLayout(new BoxLayout(titleFrame, 1));
	    titleFrame.setBorder(BorderFactory.createEmptyBorder(5, 0, 35, 0));
	    mainFrame.setLayout(new GridLayout(rows,columns));
 
	    // Adds title labels
	    teamName = new JLabel(team);
	    totalDeaths = new JLabel("Total Deaths: "+totalDeathCount);
	    teamName.setFont(new Font("Stencil", Font.PLAIN, teamnameDeathsTextSize));
	    totalDeaths.setFont(new Font("Stencil", Font.PLAIN, teamnameDeathsTextSize));
	    titleFrame.add(teamName);
	    titleFrame.add(totalDeaths);

	    
	    
	    
	    
		// Loads player names from players.txt into Player Array
	    playerArray = new PlayerModule[maxPlayers];
		Scanner playerFile = new Scanner(new File("players.txt"));
		playerFile.nextLine(); playerFile.nextLine(); // Skips two lines
		int count = 0;
		while (playerFile.hasNextLine()) {
			PlayerModule player = new PlayerModule(playerFile.nextLine(), respawnTimer);
			playerArray[count] = player;
			count++;
		}
		playerFile.close();
	    
	    // Counts number of array objects
	    int listLength = 0;
	    for (int i = 0; i < playerArray.length; i ++)
	        if (playerArray[i] != null)
	            listLength++;
	    
	    // Creates buttons for each array object
	    buttonArray = new JButton[maxPlayers];
	    int counter = 0;
	    while (counter < listLength)  {
	    	JButton currentButton = newButton("[0](0) "+ playerArray[counter].getName(), buttonTextSize);
	    	currentButton.addActionListener(new Action());
	    	buttonArray[counter] = currentButton;
	    	buttonMap.put("[0](0) "+playerArray[counter].getName(), counter);
	    	playerMap.put(counter,"[0](0) "+playerArray[counter].getName());
	    	mainFrame.add(currentButton);
	    	
	    	counter++;
	    }  
	    
	    
	    // Initializes menu bar and menu
	 	menubar = new JMenuBar();
	 	JMenu options = new JMenu("Options");
	    options.setMnemonic(KeyEvent.VK_O);
	         
		// Initializes menu items
		JMenuItem optionsItem = new JMenuItem("Settings");
		optionsItem.setMnemonic(KeyEvent.VK_O);
		optionsItem.setToolTipText("Show Settings");
		optionsItem.addActionListener((ActionEvent event) -> {
			settings.showSettings();
		});
		JMenuItem resetItem = new JMenuItem("Reset");
		resetItem.setMnemonic(KeyEvent.VK_P);
		resetItem.setToolTipText("Reset Game");
		resetItem.addActionListener((ActionEvent event) -> {
			resetGame();
		});
		JMenuItem connectItem = new JMenuItem("Connect");
		connectItem.setMnemonic(KeyEvent.VK_C);
		connectItem.setToolTipText("Connect to Server");
		connectItem.addActionListener((ActionEvent event) -> {
			connectToServer();
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_E);
		exitItem.setToolTipText("Exit application");
		exitItem.addActionListener((ActionEvent event) -> {
			disconnectFromServer();
			System.exit(0);
		});
		// Add items to menuList
		options.add(optionsItem);
		options.add(resetItem);
		options.add(connectItem);
		options.add(exitItem);
		 
		// Add menuList to menu
		menubar.add(options); 
	    
	    
		// Finishes window configuration
	    window.setJMenuBar(menubar);
	    window.add(titleFrame, BorderLayout.PAGE_START);
	    window.add(mainFrame, FlowLayout.CENTER);
	    window.pack();
	    window.setVisible(true);
	    
	    // Refreshes window contents
	    window.revalidate();
	    
	    // Loads deathCount output
	    deaths = new TextOutput(totalDeathsOutputLocation);
	    
	    // Loads network module
	    networkOut = new NetworkModule(serverIP, serverPort);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		MainClass Deathmatch = new MainClass();
			
		System.out.println("Main thread");
        new Thread(new Runnable() {
            @Override
            public void run() {
            System.out.println("Inner Thread");
            while (true) {
         	    ticker.update();
         	}
            }
        }).start();
		
		Deathmatch.revalidate();

	}
	
	public static void connectToServer() {
        networkOut.connect();
	}
	
	public static void disconnectFromServer() {
		if (networkOut.isConnected()) {
		networkOut.disconnect();
		}
	}
	
	public static int getNoPlayers() {
		return maxPlayers;
	}
	
	public static void setNoPlayers(int num) {
		maxPlayers = num;
	}
	
	public static int getRespawnTime() {
		return respawnTimer;
	}
	
	public static void setRespawnTime(int num) {
		respawnTimer = num;
	}
	
	public static String getTotalDeathsLocation() {
		return totalDeathsOutputLocation;
	}
	
	public static void setTotalDeathsLocation(String location) {
		totalDeathsOutputLocation = location;
	}
	
	public static String getTeamName() {
		return team;
	}
	
	public static void setTeamName(String name) {
		team = name;
		teamName.setText(team);
	}
	
	public static Map<String, Integer> getButtonMap() {
		return buttonMap;
	}
	
	public static PlayerModule[] getPlayerArray() {
		return playerArray;
	}
	
	public void changeButtonName(String name, String nameChange) {
		int number = buttonMap.get(name);
		buttonArray[number].setText(nameChange);
	}
	
	public void changeButtonIcon(String name, Icon icon) {
		int number = buttonMap.get(name);
		buttonArray[number].setIcon(icon);
	}
	
	
	public static JButton newButton(String name, int textSize) {
		JButton createdButton = new JButton(name, aliveIcon);
		createdButton.setFont(new Font("Stencil", Font.PLAIN, textSize));
		createdButton.setHorizontalAlignment(SwingConstants.LEFT);
		createdButton.setFocusPainted(false);
		return createdButton;
	}

	
	public void revalidate() {
		window.revalidate();
	}
	
	
	public class Action implements ActionListener {

		public void actionPerformed(ActionEvent emm) {
			String name = (String)emm.getActionCommand();
			UpdatePlayer(name);
		}
	}
	
	
	public void resetGame() {
		totalDeathCount = 0;
		totalDeaths.setText("Total Deaths: "+totalDeathCount);
		try {
			deaths.writeNewSingle(totalDeathCount);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	if (networkOut.isConnected()) {
	    	try {
				networkOut.sendData(totalDeathCount);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	
    	// Counts number of array objects
	    int listLength = 0;
	    for (int i = 0; i < playerArray.length; i ++)
	        if (playerArray[i] != null)
	            listLength++;
    	
    	
    	int counter = 0;
    	System.out.println(counter);
    	System.out.println(listLength);
    	while (counter < listLength) {
    		playerArray[counter].resetDeaths();
    		
    		System.out.println(playerMap.get(counter));
    		
    		changeButtonName(playerMap.get(counter), playerArray[counter].toString());
    		counter++;
    	}
    	
	}
	
	public void UpdatePlayer(String name) {
		
		// Grabs number for array
		int number = buttonMap.get(name);
		
		// Reset respawn time of PlayerModule
		playerArray[number].changeRespawnTime(respawnTimer);
		
		// If already counting down, don't start again
		if (playerArray[number].getTimeRemaining() < 1) {
			
			// Increment Death Count
			playerArray[number].addDeath();
			// Change icon to deadIcon
			changeButtonIcon(name, deadIcon);
			// Change name of button
			changeButtonName(name, playerArray[number].toString());
			// Change name of button in arrayMaps
			buttonMap.put(playerArray[number].toString(), number);
			playerMap.put(number, playerArray[number].toString());
			// Reset the respawn timer
			playerArray[number].resetTimer();
			//Increments totalDeath and changes totalDeath label
	    	totalDeathCount++;
	    	try {
				deaths.writeNewSingle(totalDeathCount);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    	
	    	if (networkOut.isConnected()) {
		    	try {
					networkOut.sendData(totalDeathCount);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	totalDeaths.setText("Total Deaths: "+totalDeathCount);
	    	
	    	if (playerArray[number].tickListenerUnassigned) {
	    		playerArray[number].tickListenerAssigned();
		    	ticker.addTickListener(new TickListener() {
		
		    	    @Override
		    	    public void onTick(float deltaTime) {
		    	    	if (playerArray[number].getTimeRemaining() > 0) {
		    	    	playerArray[number].decrementTime();
		    	    	changeButtonName(name, playerArray[number].toString());
		    	    	
		    	    	} else {
		    	    		changeButtonIcon(name, aliveIcon);
		    	    	}
		    	    }
		    	});
	    	}
	    }	
	}
}