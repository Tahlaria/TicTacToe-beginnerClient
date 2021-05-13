package de.aiarena.community;

public interface ConsumesMessages {
    void handleMessage(MessageFromServer message);
}
