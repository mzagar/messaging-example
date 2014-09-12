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

            int messageId = jsonObject.get("messageId").getAsInt();
            int timestamp = jsonObject.get("timestamp").getAsInt();
            String protocolVersion = jsonObject.get("protocolVersion").getAsString();
            MessageData messageData = createMessageData(protocolVersion, jsonObject, context);

            return new Message(messageId, timestamp, protocolVersion, messageData);
        }

        private MessageData createMessageData(String protocolVersion, JsonObject jsonObject, JsonDeserializationContext context) {
            if (protocolVersion == null) {
                return null;
            }

            if (protocolVersion.startsWith("1.0")) {
                return context.deserialize(jsonObject.get("messageData"), MessageData.class);
            }

            if (protocolVersion.startsWith("2.0")) {
                return context.deserialize(jsonObject.get("payload"), MessageData.class);
            }

            return null;
        }
    }
}
