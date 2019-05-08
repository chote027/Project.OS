import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread implements Runnable {
    private ServerSocket server;
    private int port;
    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    private int lifePoint = 5;
    public ServerThread(int port) {
        this.port = port;
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            
        }
    }

    @Override
    public void run() {
        //initialize variables
        String[] movieList = {"Lion King", "Frozen", "Avatar", "Interstellar", "Martian", "IT","The Nun"
                             ,"Avengers", "Iron Man", "Star War"};
        String User = "";
        String selectedMovie;
        char[] hiddenMovie;
        int wrongCounter = 0;
        char[] wrong = new char[5];
        boolean LetterFound = false;
        boolean solved = false;
        selectedMovie = movieList[(int)(Math.random()*movieList.length)];
        hiddenMovie = new char[selectedMovie.length()];

        int isWin = 0;
        int isLose = 0;

        for(int i = 0; i < selectedMovie.length(); i++) {
            if(selectedMovie.charAt(i) == ' ') {
                hiddenMovie[i] = ' ';
            } else {
                hiddenMovie[i] = '_';
            }
        }

        StringBuilder respondHiddenMovie = new StringBuilder();
        int respondWrongCounter = wrongCounter;
        StringBuilder respondWrong = new StringBuilder();

        System.out.println("server picked : "+ selectedMovie);

        //server keeps listening to client
        while(true) {
            try {
                Socket clientSocket = server.accept();
                
                //connect to client
                inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                
                    //play till client win, lose, exit
                    while(true) {
                        System.out.print("\nserver update underscore : ");
                        respondHiddenMovie.delete(0, respondHiddenMovie.length());
                        for(int i = 0; i < selectedMovie.length(); i++) {
                            System.out.print(hiddenMovie[i]);
                            respondHiddenMovie.append(hiddenMovie[i] + " ");
                        }

                        System.out.print("\nserver update wrong letter : ");
                        respondWrong.delete(0, respondWrong.length());
                        for(int i = 0; i < wrong.length; i++) {
                            System.out.print(wrong[i] + " ");
                            respondWrong.append(wrong[i]+"");
                        }
                        System.out.println("\n");

                        //check client actioned
                        String action = (String) inFromClient.readObject();
                        System.out.println(action);
                        if(action.equals("Start") || action.equals("GetStatus")) {
                            //response game status
                            respondWrongCounter = wrongCounter;

                            outToClient.writeObject(respondHiddenMovie + "," + respondWrongCounter + "," + respondWrong
                                                    + "," + isWin + "," + isLose);
                        } else if (action.equals("GetAnswer") && isLose == 1) {
                            outToClient.writeObject(selectedMovie);
                        } else if (action.equals("Exit")) {
                            outToClient.close();
                            inFromClient.close();
                            clientSocket.close();
                        } else if(action.equals("Y") || action.equals("y")) {   //check if client wanted to continue?
                            //reset and continue checking
                            User = "";
                            selectedMovie = "";
                            wrongCounter = 0;
                            wrong = new char[5];
                            LetterFound = false;
                            solved = false;
                            selectedMovie = movieList[(int)(Math.random()*movieList.length)];
                            hiddenMovie = new char[selectedMovie.length()];

                            isWin = 0;
                            isLose = 0;

                            for(int i = 0; i < selectedMovie.length(); i++) {
                                if(selectedMovie.charAt(i) == ' ') {
                                    hiddenMovie[i] = ' ';
                                } else {
                                    hiddenMovie[i] = '_';
                                }
                            }
                
                            System.out.println("server picked new movie => " + selectedMovie);
                        } else if(action.equals("N") || action.equals("n")) {   //check if client wanted to continue?
                            outToClient.close();
                            inFromClient.close();
                            clientSocket.close();
                        } else if(action.substring(0,5).equals("Send:")) {
                            //get client input
                            User = action.substring(5,6);
                            System.out.print("\nclient guessed : " + User);

                            //game logical
                            LetterFound = false;
                            for(int i = 0; i < selectedMovie.length(); i++) {
                                if(User.toUpperCase().charAt(0) == selectedMovie.toUpperCase().charAt(i)) {
                                    hiddenMovie[i] = selectedMovie.charAt(i);
                                    LetterFound = true;
                                }
                            }
                            if(!LetterFound) {
                                wrong[wrongCounter] = User.charAt(0);
                                wrongCounter++;
                            }
                            int hiddenCount = 0;
                            for(int i = 0; i < selectedMovie.length(); i++) {
                                if('_' == hiddenMovie[i]) {
                                    hiddenCount++;
                                }
                            }
                            if(hiddenCount > 0) {
                                solved = false;
                            } else {
                                solved = true;
                            }
                        }

                        if(wrongCounter >= lifePoint) {
                            isLose = 1;
                        }

                        if(solved) {
                            isWin = 1;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
				}
            } 
        }
}