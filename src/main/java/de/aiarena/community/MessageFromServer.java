package de.aiarena.community;

import java.util.HashMap;

public class MessageFromServer {
    public final int responseCode;
    private HashMap<String,String> headers = new HashMap<String,String>();

    MessageFromServer(String firstLine){
        responseCode = Integer.parseInt(firstLine);
    }

    public void addHeaderLine(String line){
        String[] parts = line.split(":");
        String key = parts[0].trim();
        String value = parts[1].trim();

        headers.put(key,value);
    }

    public String getHeader(String header){
        return headers.get(header);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(""+responseCode+"\n");
        for(String key : headers.keySet()){
            sb.append(key+": "+headers.get(key)+"\n");
        }
        return sb.toString();
    }
}
