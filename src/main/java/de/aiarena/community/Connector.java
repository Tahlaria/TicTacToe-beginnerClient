package de.aiarena.community;

import java.io.*;
import java.net.Socket;

public class Connector implements Closeable, Runnable {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final String token;
    private final ConsumesMessages consumer;

    public Connector(String token, ConsumesMessages consumer) throws IOException{
        this.token = token;
        this.consumer = consumer;

        socket = new Socket("ai-arena.de",1805);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
    }

    public void send(MessageToServer message){
        try {
            System.out.println("Sending:\n"+message.toString());
            writer.write(message.toString());
            writer.flush();
        }catch(Exception ex){
            System.err.println("Error sending a message "+ex);
            ex.printStackTrace();
        }
    }

    public void run(){
        MessageToServer claimMessage = new MessageToServer("AMTP/0.0","CLAIM")
                .withHeader("Secret", token)
                .withHeader("Role", "Player");

        send(claimMessage);

        MessageFromServer currentMessage = null;
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                if(line.isEmpty()){
                    if(currentMessage != null){
                        System.out.println("Received:\n"+currentMessage.toString());
                        consumer.handleMessage(currentMessage);
                        currentMessage = null;
                    }
                    continue;
                }
                if(currentMessage == null){
                    currentMessage = new MessageFromServer(line);
                    continue;
                }
                currentMessage.addHeaderLine(line);
            }
        }catch(Exception ex){
            System.err.println("Exception in read loop: "+ex);
            ex.printStackTrace();
        }
        close();
    }

    public void close() {
        try{writer.close();}catch(Exception ex){}
        try{reader.close();}catch(Exception ex){}
        try{socket.close();}catch(Exception ex){}
    }
}
