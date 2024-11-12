//QuizClient.java
//202334530_임남령
package HW1_Limnamryeong;
import java.io.*;
import java.net.*;
import java.util.*;

public class QuizClient
{
	public static void main(String[] args) throws Exception
	{
		File inputfile = new File("server_info.dat"); // read configuration file
		String ip, port;
		
		if(!inputfile.exists()) // If file is missing, set IP, port number to default values
		{
			ip = "localhost";
			port = "1234";
		}
		else //read IP, port number from configuration file
		{
			BufferedReader reader = new BufferedReader(new FileReader(inputfile));
			ip = reader.readLine();
			port = reader.readLine();
			reader.close(); // close reader
		}
		
		var socket = new Socket(ip, Integer.parseInt(port)); // make client socket
		System.out.println("[Client] Connected.");
		
		var scanner = new Scanner(System.in);
		var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		var out = new PrintWriter(socket.getOutputStream(), true);
		
		while(true)
		{
			String inputMessage = in.readLine(); // receive question
			System.out.println(inputMessage);
			
			if (inputMessage.startsWith("Total score")) // receive Total score, end connection
				break;
			
			System.out.print("Input answer: >> ");
			
			String answer = scanner.nextLine();
			out.println(answer); // send answer to server
			
			inputMessage = in.readLine(); //receive correct or incorrect
			System.out.println(inputMessage);

		}
		
		scanner.close(); // close scanner
		socket.close(); // close socket
		System.out.println("Connection closed.");
	}
}
