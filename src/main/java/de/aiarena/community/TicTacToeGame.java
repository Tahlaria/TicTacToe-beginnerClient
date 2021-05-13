package de.aiarena.community;

import java.io.IOException;

public class TicTacToeGame implements ConsumesMessages{

    public static void main(String[] args) throws Exception{
        new TicTacToeGame();
        while(true){
            Thread.sleep(1000);
        }
    }

    private final Connector serverConnection;
    private int slot = -1;
    private final YourGameLogic gameLogic;

    public TicTacToeGame() throws IOException{
        gameLogic = new YourGameLogic();
        serverConnection = new Connector(gameLogic.token, this);
        (new Thread(serverConnection)).start();
    }

    @Override
    public void handleMessage(MessageFromServer message) {
        switch(message.responseCode){
            case 1:
                String slotOccupied = message.getHeader("Slot");
                String actor = message.getHeader("ActionBy");
                String needsToAct = message.getHeader("ActionRequiredBy");

                if(slotOccupied != null){
                    boolean isItMe = Integer.parseInt(actor) == slot;
                    try {
                        gameLogic.updateField(slotOccupied, isItMe);
                    }catch(Exception ex){
                        System.err.println("Cannot update game field: "+ex);
                    }
                }

                if(needsToAct != null){
                    try {
                        String action = gameLogic.nextAction();

                        MessageToServer msg = new MessageToServer("AMTP/0.0","GAME")
                                .withHeader("Action","Put")
                                .withHeader("Slot",action);
                        serverConnection.send(msg);
                    }catch(Exception ex){
                        System.err.println("Cannot act! "+ex);
                    }

                }

                break;
            case 5:
                slot = Integer.parseInt(message.getHeader("Slot"));
                break;
            case 9:

                serverConnection.close();
                System.exit(0);
        }
    }
}
