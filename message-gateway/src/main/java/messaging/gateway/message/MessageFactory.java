package messaging.gateway.message;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Creates Message instance from specified json String.
 *
 * Parses json string protocolVersion to properly deserialize different versions of Message object.
 *
 * Created by mzagar on 11.9.2014.
 */
public class MessageFactory {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageDeserializer()).create();

    public Message createFromJson(String json) {
        return gson.fromJson(json, Message.class);
    }

    private static final class MessageDeserializer implements JsonDeserializer<Message> {
        @Override
        public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            int messageId = validJsonElementOrThrow(jsonObject, "messageId").getAsInt();
            int timestamp = validJsonElementOrThrow(jsonObject, "timestamp").getAsInt();
            String protocolVersion = validJsonElementOrThrow(jsonObject, "protocolVersion").getAsString();
            MessageData messageData = createMessageData(protocolVersion, jsonObject, context);

            return new Message(messageId, timestamp, protocolVersion, messageData);
        }

        private JsonElement validJsonElementOrThrow(JsonObject jsonObject, String name) {
            JsonElement jsonElement = jsonObject.get(name);
            if (jsonElement == null) {
                throw new IllegalArgumentException(name + " expected but is missing");
            }
            return jsonElement;
        }

        private MessageData createMessageData(String protocolVersion, JsonObject jsonObject, JsonDeserializationContext context) {
            if (protocolVersion == null) {
                throw new IllegalArgumentException("protocolVersion expected but is null");
            }

            if ("1.0.0".equals(protocolVersion) || "1.0.1".equals(protocolVersion)) {
                return context.deserialize(jsonObject.get("messageData"), MessageData.class);
            }

            if ("2.0.0".equals(protocolVersion)) {
                return context.deserialize(jsonObject.get("payload"), MessageData.class);
            }

            throw new IllegalArgumentException("unsupported protocolVersion: " + protocolVersion);
        }
    }
}
