package de.aiarena.community;

import java.util.HashMap;

public class YourGameLogic {
    enum Field{
        TopLeft, Top, TopRight,
        Left, Center, Right,
        BottomLeft, Bottom, BottomRight
    }
    enum Player{
        You, NotYou, Empty
    }

    private HashMap<Field, Player> gameBoard = new HashMap<>();
    private HashMap<Field,String> fieldNames = new HashMap<>();

    public YourGameLogic(){
        for(Field f : Field.values()){
            gameBoard.put(f,Player.Empty);
        }

        fieldNames.put(Field.TopLeft,"Top-Left");
        fieldNames.put(Field.Top,"Top");
        fieldNames.put(Field.TopRight,"Top-Right");
        fieldNames.put(Field.Left,"Left");
        fieldNames.put(Field.Center,"Center");
        fieldNames.put(Field.Right,"Right");
        fieldNames.put(Field.BottomLeft,"Bottom-Left");
        fieldNames.put(Field.Bottom,"Bottom");
        fieldNames.put(Field.BottomRight,"Bottom-Right");
    }

    public void updateField(String field, boolean me) throws Exception{
        for(Field f : Field.values()){
            if(fieldNames.get(f).equals(field)){
                gameBoard.put(f, me ? Player.You : Player.NotYou);
                return;
            }
        }
        throw new Exception("Unknown field "+field);
    }



    // Edit stuff here!
    public final String token = "<Your token>";
    public String nextAction() throws Exception{
        for(Field f : Field.values()){
            if(gameBoard.get(f) == Player.Empty){
                return fieldNames.get(f);
            }
        }
        throw new Exception("No field left");
    }
}
