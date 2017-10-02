package chatbot;

public class Chatbot {

	private String username;
	private boolean chatting;
	
	private String purpose;
	private String setting;
	private int familySize;
	private boolean forLiving;
	private boolean hasPets;
	private boolean buying;
	
	private Topic apt;
	private Topic townhouse;
	private Topic house;
	private Topic land;

	public Chatbot() {
		apt = new ChatbotAnnie();
		townhouse = new ChatbotStephanie();
		house = new JoannaChatbot();
		land = new ChatbotKevin();
		username = "";
		chatting = true;
		setting = "";
		familySize = 0;
		hasPets = false;
		buying = false;
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
		ChatbotMain.print("Welcome to JASK Real Estate! What is your name?");
		username = ChatbotMain.getInput();
		getInfo();
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
	
	public void getYesOrNo(String question, boolean field) {
		ChatbotMain.print(question);
		String response = ChatbotMain.getInput();
		boolean YesOrNo = false;
		while(!YesOrNo)
			if(ChatbotMain.findKeyword(response, "yes", 0) >= 0) {
				field = true;
				YesOrNo = true;
			} else if(ChatbotMain.findKeyword(response, "no", 0) >= 0) {
				field = false;
				YesOrNo = true;
			} else {
				ChatbotMain.print(question + " Yes or no?");
				response = ChatbotMain.getInput();
			}
	}
	
	public void getInfo() {
		getYesOrNo("residence?", forLiving);
	}
	
}
