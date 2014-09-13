package messaging.gateway.message;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Implements logic to create {@link messaging.gateway.message.Message} instance from specified json string.
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

            if (Versions.V1_0_0.equals(protocolVersion) || Versions.V1_0_1.equals(protocolVersion)) {
                return context.deserialize(jsonObject.get("messageData"), MessageData.class);
            }

            if (Versions.V2_0_0.equals(protocolVersion)) {
                return context.deserialize(jsonObject.get("payload"), MessageData.class);
            }

            throw new IllegalArgumentException("unsupported protocolVersion: " + protocolVersion + ", supported versions: " + Versions.getSupportedVersions());
        }
    }
}
