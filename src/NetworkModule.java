import java.net.*;
import java.io.*;
 
public class NetworkModule {
	
    // Initialize socket and output stream
    private Socket socket = null;
    private DataOutputStream output = null;
    private String IPAddress;
    private int Port;
    private boolean status;
    
 
    // constructor to put ip address and port
    public NetworkModule(String address, int port) {
       this.IPAddress = address;
       this.Port = port;
       status = false;
    }

    public void connect() {
    	 // Establish a connection
        try {
            socket = new Socket(this.IPAddress, this.Port);
            System.out.println("Connected");
            status = true;
        	output = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {System.out.println(u);}
        catch(IOException i)
        {System.out.println(i);}
    }

    public void disconnect() {
    	try {
			output.writeUTF("Disconnect");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    // Close the connection
	    try
	    {
	    	status = false;
	        socket.close();
	    }
	    catch(IOException i)
	    {System.out.println(i);}
	}
    
    public void sendData(int data) throws IOException {
    	output.writeUTF(Integer.toString(data));
    }
    
    public boolean isConnected() {
    	return status;
    }
}