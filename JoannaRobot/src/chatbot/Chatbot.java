package chatbot;

public class Chatbot {

	private String username;
	private boolean chatting;
	private Topic annie;

	public Chatbot() {
		annie = new ChatbotAnnie();
		username = "Unknown User";
		chatting = true;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Topic getAnnie() {
		return annie;
	}

	public void startChatting() {
		ChatbotMain.print("Hi! I am an intelligent machine that can respond to your input. Tell me your name.");
		username = ChatbotMain.getInput();
		while(chatting) {
			ChatbotMain.print("What would you like to talk about?");
			String response = ChatbotMain.getInput();
			if(annie.isTriggered(response)) {
				chatting = false;
				annie.talk(response);
			} else
				ChatbotMain.print("I'm sorry. I don't understand. I never said I was perfect.");
		}
	}
	
}
