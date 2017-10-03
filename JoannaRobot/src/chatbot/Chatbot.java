package chatbot;

public class Chatbot {

	private String username;
	private boolean chatting;
	
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
	}
	
	public String getUsername() {
		return username;
	}
	public int getFamilySize() {
		return familySize;
	}
	public boolean getForLiving() {
		return forLiving;
	}
	public boolean getHasPets() {
		return hasPets;
	}
	public boolean getBuying() {
		return buying;
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
	
	public boolean YesNo(String question) {
		ChatbotMain.print(question);
		String response = ChatbotMain.getInput();
		boolean YesOrNo = false;
		while(!YesOrNo)
			if(ChatbotMain.findKeyword(response, "yes", 0) >= 0) {
				return true;
			} else if(ChatbotMain.findKeyword(response, "no", 0) >= 0) {
				return false;
			} else {
				ChatbotMain.print(question + " Yes or no?");
				response = ChatbotMain.getInput();
			}
		return false;
	}
	
	public void getInfo() {
		forLiving = YesNo("Are you looking for a residence?");
		if(forLiving) {
			ChatbotMain.print("How big is your family?");
			familySize = ChatbotMain.getPositiveNumInput();
			hasPets = YesNo("Do you have any pets?");
			buying = YesNo("Are you looking to buy?");
		}
	}
} 
 