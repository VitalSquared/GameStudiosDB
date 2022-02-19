package ru.nsu.spirin.gamestudios.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Attachment {
    private @JsonProperty("name") String name;
    //private @JsonProperty("content") String contentString;
    private @JsonProperty("id") int ID;
    private @JsonProperty("content") byte[] content;

    public byte[] getContent() {
        //this.content = contentString.getBytes();
        return this.content;
    }

    public void setContent(byte[] bytes) {
        this.content = bytes;
        //this.contentString = new String(this.content);
    }
}
