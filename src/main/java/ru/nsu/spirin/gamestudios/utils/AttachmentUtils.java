package ru.nsu.spirin.gamestudios.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.nsu.spirin.gamestudios.model.entity.message.Attachment;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class AttachmentUtils {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @SneakyThrows
    public static String parseTo(List<Attachment> attachments) {
        return mapper.writeValueAsString(attachments);
    }

    @SneakyThrows
    public static <T> T parseFrom(String content, TypeReference<T> valueTypeRef) {
        return mapper.readValue(content, valueTypeRef);
    }

    public static List<Attachment> parseAttachments(String attachments) {
        if (attachments == null) {
            return new ArrayList<>();
        }

        try {
            return parseFrom(attachments, new TypeReference<>() {});
        }
        catch (Exception exception) {
            return new ArrayList<>();
        }
    }
}
