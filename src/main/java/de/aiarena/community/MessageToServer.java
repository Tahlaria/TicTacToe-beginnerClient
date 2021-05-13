package de.aiarena.community;

import java.util.HashMap;

public class MessageToServer {
    public final String protocol;
    public final String command;
    private HashMap<String,String> headers = new HashMap<String,String>();

    MessageToServer(String protocol, String command){
        this.protocol = protocol;
        this.command = command;
    }

    public MessageToServer withHeader(String key, String value){
        headers.put(key,value);
        return this;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(protocol+" "+command+"\n");
        for(String key : headers.keySet()){
            sb.append(key+": "+headers.get(key)+"\n");
        }
        sb.append("\n");

        return sb.toString();
    }
}
