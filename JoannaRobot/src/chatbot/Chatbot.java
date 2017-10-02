package chatbot;

public class Chatbot {

	private String username;
	private boolean chatting;
	
	private int familySize;
	private boolean hasPets;
	private String setting;
	private boolean Buying;
	
	private Topic apt;
	private Topic townhouse;
	private Topic house;
	private Topic land;

	public Chatbot() {
		apt = new ChatbotAnnie();
		townhouse = new ChatbotStephanie();
		house = new JoannaChatbot();
		land = new ChatbotKevin();
		username = "Unknown User";
		chatting = true;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Topic getApt() {
		return apt;
	}

	public Topic getTownhouse() {
		return townhouse;
	}

	public Topic getHouse() {
		return house;
	}

	public Topic getLand() {
		return land;
	}

	public void startChatting() {
		ChatbotMain.print("Hi! I am an intelligent machine that can respond to your input. Tell me your name.");
		username = ChatbotMain.getInput();
		while(chatting) {
			ChatbotMain.print("What would you like to talk about?");
			String response = ChatbotMain.getInput();
			if(apt.isTriggered(response) || townhouse.isTriggered(response) || house.isTriggered(response) || land.isTriggered(response)) {
				chatting = false;
				if(apt.isTriggered(response))
					apt.talk(response);
				else if(townhouse.isTriggered(response))
					townhouse.talk(response);
				else if(house.isTriggered(response))
					house.talk(response);
				else if(land.isTriggered(response))
					land.talk(response);
			} else
				ChatbotMain.print("I'm sorry. I don't understand.");
		}
	}
	
}
