import java.util.HashMap;

public class Responder {
    private HashMap<String, String> responseMap;

    public Responder() {
        responseMap = new HashMap<>();
        prepareResponses();
    }

    private void prepareResponses() {
        responseMap.put("slow", "Have you tried restarting your computer?");
        responseMap.put("crash", "Please check the error logs and send them to IT.");
        responseMap.put("password", "You can reset your password at the IT portal.");
        responseMap.put("wifi", "Try forgetting the network and reconnecting.");
        responseMap.put("screen", "Check if the monitor cable is securely plugged in.");
    }

    public String generateResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a question.";
        }

        String lowerInput = input.toLowerCase();
        String[] words = lowerInput.split("\\s+");

        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-z]", "");
            if (!cleanWord.isEmpty() && responseMap.containsKey(cleanWord)) {
                return responseMap.get(cleanWord);
            }
        }

        return "I'm sorry, I didn't understand that. Try using keywords like 'slow', 'crash', or 'wifi'.";
    }
}