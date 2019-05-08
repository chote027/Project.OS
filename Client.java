import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class Client {

    private static int lifePoint = 5;
    private static int defaultPort = 7777;
	private static int isWin = 0;
    private static int isLose = 0;
	private static int wrongCounter = 0;
    private static String HOST = "127.0.0.1"; //server
    private static Socket socket = null;
    private static String hiddenMovie = "";
    private static String wrong = "";
	private static ObjectOutputStream SocketOutput = null;
    private static ObjectInputStream SocketInput = null;
	private static String Human[] = {    
            " ___________.._______\n" +
            "| .__________))______|\n"+
            "| | / /      ||\n"+
			"| |/ /       ||\n"+
			"| | /        ||.-''.           \n"+
			"| |/         ||  _  |          \n"+
			"| |          ||  `/,|          \n"+
			"| |          (| `_.'           \n"+
			"| |         .-`--'.            \n"+
			"| |        /Y . . Yy           \n"+
			"| |       // |   | ||          \n"+
			"| |      //  | . | ||          \n"+
			"| |     (_)  |   | (_)         \n"+
			"| |          ||'||             \n"+
			"| |          || ||             \n"+
			"| |          || ||             \n"+
			"| |          || ||             \n"+
			"| |         / | ||             \n"+
			"=========|_ -    - =======\n"+
            "=========================||\n",
            
            " ___________.._______\n" +
            "| .__________))______|\n"+
            "| | / /      ||\n"+
			"| |/ /       ||\n"+
			"| | /        ||.-''.           \n"+
			"| |/         ||  _  |          \n"+
			"| |          ||  `/,|          \n"+
			"| |          (| `_.'           \n"+
			"| |         .-`--'.            \n"+
			"| |        /Y . . Yy           \n"+
			"| |       // |   | ||          \n"+
			"| |      //  | . | ||          \n"+
			"| |     (_)  |   | (_)         \n"+
			"| |            '               \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"==========================\n"+
			"=========================||\n",

            " ___________.._______\n" +
            "| .__________))______|\n"+
            "| | / /      ||\n"+
			"| |/ /       ||\n"+
			"| | /        ||.-''.           \n"+
			"| |/         ||  _  |          \n"+
			"| |          ||  `/,|          \n"+
			"| |          (| `_.'           \n"+
			"| |         .-`--'             \n"+
			"| |        /Y . .              \n"+
			"| |       // |   |             \n"+
			"| |      //  | . |             \n"+
			"| |     (_)  |   |             \n"+
			"| |            '               \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"==========================\n"+
			"=========================||\n",

            " ___________.._______\n" +
            "| .__________))______|\n"+
            "| | / /      ||\n"+
			"| |/ /       ||\n"+
			"| | /        ||.-''.           \n"+
			"| |/         ||  _  |          \n"+
			"| |          ||  `/,|          \n"+
			"| |          (| `_.'           \n"+
			"| |          -`--'             \n"+
			"| |           . .              \n"+
			"| |          |   |             \n"+
			"| |          | . |             \n"+
			"| |          |   |             \n"+
			"| |            '               \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"==========================\n"+
			"=========================||\n",

            " ___________.._______\n" +
            "| .__________))______|\n"+
            "| | / /      ||\n"+
			"| |/ /       ||\n"+
			"| | /        ||.-''.           \n"+
			"| |/         ||  _  |          \n"+
			"| |          ||  `/,|          \n"+
			"| |          (| `_.'           \n"+
			"| |          -`--'             \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"==========================\n"+
			"=========================||\n",

            "| ____________________\n" +
            "| .__________________|\n"+
            "| | / /        \n"+
			"| |/ /         \n"+
			"| | /                          \n"+
			"| |/                           \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"| |                            \n"+
			"==========================\n"+
			"=========================||\n",
    };
  
    public static void main( String[] args ) {
        Scanner keyboard;
		
        System.out.println("Connect to server " + HOST + " : " + defaultPort);
        try {
          
            socket = new Socket(HOST, defaultPort);
     
            SocketOutput = new ObjectOutputStream(socket.getOutputStream());
            SocketOutput.writeObject("Start");
        
            SocketInput = new ObjectInputStream(socket.getInputStream());
            String newPort = (String) SocketInput.readObject();
            System.out.println("New Port :: " + newPort);
            keyboard = new Scanner(System.in);

            socket = new Socket(HOST, Integer.parseInt(newPort));
            SocketOutput = new ObjectOutputStream(socket.getOutputStream());
			SocketInput = new ObjectInputStream(socket.getInputStream());
			 
			GetStatus();	//this must be called to initialize underscore
            while (isWin == 0 && isLose ==0) {
				System.out.println("**********************************************\n");
				System.out.println(Human[lifePoint-wrongCounter]);

				System.out.println("Movie  : \t" + hiddenMovie);
				System.out.println("Health Points : " + (lifePoint - wrongCounter));
				System.out.println("Used Letter   : " + wrong);
				System.out.println("\n");

                System.out.print("\nYour Answer => ");
                
                sendUserInput(keyboard.nextLine());
               
                GetStatus();

            	if (isWin == 1) {
					System.out.println();
					System.out.println("***********************************************\n ");
					System.out.println("************** Congrats! You Win **************\n");
                	System.out.println("************ Answer is : " + hiddenMovie +" **********\n" );
					System.out.println("***********************************************\n ");
					System.out.println( "\n" );

					System.out.println("         $$$$                                 ");
					System.out.println("        $$$$$$$                           $$$ ");
					System.out.println("         $$$$$$$                         $$$$ ");
					System.out.println("          $$$$$$$      $$      $$       $$$$$ ");
					System.out.println("           $$$$$$$   $$$$$   $$$$$    $$$$$$$ ");
					System.out.println("            $$$$$$$ $$$$$$$ $$$$$$$  $$$$$$$  ");
					System.out.println("             $$$$$$ $$$$$$$ $$$$$$$  $$$$$$  ");
					System.out.println("             $$$$$$ $$$$$$$ $$$$$$$ $$$$$$  ");
					System.out.println("  $$$$$      $$$$$$ $$$$$$$ $$$$$$$ $$$$$$  ");
					System.out.println(" $$$$$$$$$  $$$$$$$ $$$$$$$ $$$$$$$ $$$$$  ");
					System.out.println(" $$$$$$$$$$$$$$$$$$ $$$$$$$ $$$$$$$ $$$$$  ");
					System.out.println("    $$$$$$$$$$$$$$$ $$$$$$$ $$$$$$ $$$$$$  ");
					System.out.println("       $$$$$$$$$$$$$ $$$$$  $$ $$$$$$ $$  ");
					System.out.println("        $$$$$$$$$$$$     $$$$$     $$$$$  ");
					System.out.println("          $$$$$$$$$$$$$$$$$$$$$$$$$$$$$  ");
					System.out.println("           $$$$$$$$$$$$$$$$$$$$$$$$$$$  ");
					System.out.println("             $$$$$$$$$$$$$$$$$$$$$$$$  ");
					System.out.println("                $$$$$$$$$$$$$$$$$$$$  ");

					System.out.print("\nContinue? (Y/N) => ");
					String isContinue = keyboard.nextLine();
					if(isContinue.equals("Y") || isContinue.equals("y")) {
						SocketOutput.writeObject(isContinue);

						GetStatus();
					} else {
						SocketOutput.writeObject("Exit");
        
           				SocketOutput.close();
            			SocketInput.close();
            			socket.close();
					}			
            	}
            	else if (isLose == 1) {
					String answer = GetAnswer();
					System.out.println(Human[0]);

					System.out.println("********************************************\n ");
					System.out.println("********** Game Over You're Dead. **********\n");
                	System.out.println("******** Answer is: " + answer + " **********\n");
					System.out.println("********************************************\n ");

					System.out.println("          uu$$$$$$$$$$$$$$$$$uu          ");
					System.out.println("         u$$$$$$$$$$$$$$$$$$$$$u         ");
					System.out.println("        u$$$$$$$$$$$$$$$$$$$$$$$u        ");
					System.out.println("       u$$$$$$$$$$$$$$$$$$$$$$$$$u       ");
					System.out.println("       u$$$$$$$$$$$$$$$$$$$$$$$$$u       ");
					System.out.println("       u$$$$$$$   $$$$$   $$$$$$$u       ");
					System.out.println("       u$$$$$      u$u       $$$$u       ");
					System.out.println("        $$$u       u$u       u$$$        ");
					System.out.println("        $$$u      u$$$u      u$$$        ");
					System.out.println("         u$$$$uu$$$   $$$uu$$$$u         ");
					System.out.println("          u$$$$$$$u   u$$$$$$$u          ");
					System.out.println("            u$$$$$$$u$$$$$$$u            ");
					System.out.println("             u$u$u$u$u$u$u$u             ");
					System.out.println("  uuu        $$u$ $ $ $ $u$$       uuu   ");
					System.out.println(" u$$$$        $$$$$u$u$u$$$       u$$$$  ");
					System.out.println("  $$$$$uu      u$$$$$$$$$u     uu$$$$$$  ");
					System.out.println("u$$$$$$$$$$$uu    uuuu    uuuu$$$$$$$$$$ ");
					System.out.println("$$$$u$$$$$$$$$$uuu   uu$$$$$$$$$uu$$$    ");
					System.out.println(" uu      u$$$$$$$$$$$uu u$uu             ");
					System.out.println("           uuuu u$$$$$$$$$$uuu           ");
					System.out.println("  u$$$uuu$$$$$$$$$uu u$$$$$$$$$$$uuu$$$  ");
					System.out.println("  $$$$$$$$$$u           u$$$$$$$$$$$     ");
					System.out.println("   u$$$$$u                      u$$$$u   ");

					SocketOutput.writeObject("Exit");
        
           			SocketOutput.close();
            		SocketInput.close();
            		socket.close();
           		}   
			}	
        }
         catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

	private static void sendUserInput(String s){
        try {
            SocketOutput.writeObject("Send:"+s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	 private static void GetStatus(){
        try {
            SocketOutput.writeObject("GetStatus");
            String input = (String) SocketInput.readObject();
            String[] temp = input.split(",");  

            hiddenMovie = temp[0];
            wrongCounter = Integer.parseInt(temp[1]);
            wrong = temp[2];
            isWin = Integer.parseInt(temp[3]);
            isLose = Integer.parseInt(temp[4]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static String GetAnswer(){
        try {
            SocketOutput.writeObject("GetAnswer");
            String input = (String) SocketInput.readObject();
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
