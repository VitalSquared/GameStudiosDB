package ru.nsu.spirin.gamestudios.model.entity.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Attachment {
    private @JsonProperty("name") String name;
    private @JsonProperty("id") int ID;
    private @JsonProperty("content") byte[] content;
}
