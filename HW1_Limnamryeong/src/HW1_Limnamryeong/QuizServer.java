//QuizServer.java
//202334530_임남령
package HW1_Limnamryeong;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizServer 
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
		
		//Multi-client Support
		ServerSocket listner = new ServerSocket(Integer.parseInt(port)); // make server socket
		System.out.println("[Server]Quiz server is running...");
		ExecutorService pool = Executors.newFixedThreadPool(20); // make 20 thread
		while (true)
		{
			Socket sock = listner.accept();
			pool.execute(new Quizprogram(sock));
		}
	}
	
	private static class Quizprogram implements Runnable
	{
		private Socket socket;
	
		Quizprogram(Socket socket)
		{
			this.socket = socket;
		}
		
		@Override
		public void run()
		{
			System.out.println("Connected: " + socket);
			try
			{
				BufferedReader in = null;
				BufferedWriter out = null;
				int score = 0; // score
				int quiz_num = 0; // quiz count
				String[] quiz_questions = { // questions
						"How many bits are there in an IPv4 address?",
						"In the IPv4 address system, when the IP is 12.34.158.0/24, how many bits are allocated for the host?",
						"What is the port number for HTTP?",
						"What command should I enter in the cmd window to check my computer's IP address?",
						"Is a connection-oriented type of socket TCP or UDP?",
				};
				String[] quiz_answers = { // answers
						"32",
						"8",
						"80",
						"ipconfig",
						"TCP"
				};

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	            
	            while (quiz_num < 5)
	            {            	
	            	out.write("Quiz number " + (quiz_num + 1) + ": " + quiz_questions[quiz_num] + "\n"); // send Quiz to client
	            	out.flush();
	            	
	            	String inputMessage = in.readLine(); // receive answer
	            	System.out.println(inputMessage); // print received answer
	            	
	            	if(inputMessage.equals(quiz_answers[quiz_num])) // if answer is correct
	            	{
	            		out.write("Correct!\n");
	            		score += 20;
	            	}
	            	else // if answer is wrong
	            		out.write("Incorrect\n");
	            		
	            	out.flush();
	            	quiz_num++; // increase quiz number
	            }
	            
	            out.write("Total score: " + score + "\n"); // send total score
				out.flush();
			} 
			catch (IOException e) // error case
			{
				System.out.println("Error:" + socket);
				System.out.println(e.getMessage());
			}
			finally
			{
				try
				{
					if(socket != null)
						socket.close(); // close socket
					
				}
				catch (IOException e) // error case
				{
					System.out.println("Error:" + socket);
				}
				System.out.println("Closed: " + socket);
			}
		}
	}
}
