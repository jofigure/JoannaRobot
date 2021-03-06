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
		familySize = 0;
		forLiving = hasPets = buying = false;
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
		ChatbotMain.print("-\n" + "Hello! Welcome to JASK Real Estate. What is your name?");
		username = ChatbotMain.getInput();
		evaluate();
	}
	
	public void direct() {
		chatting = true;
		while(chatting) {
			String response = ChatbotMain.getInput();
			if(land.isTriggered(response)) {
				chatting = false;
				land.talk(response);
			} else if(apt.isTriggered(response)) {
				chatting = false;
				apt.talk(response);
			} else if(townhouse.isTriggered(response)) {
				chatting = false;
				townhouse.talk(response);
			} else if(house.isTriggered(response)) {
				chatting = false;
				house.talk(response);
			} else {
				ChatbotMain.print("I'm sorry. I don't understand. Could you be more specific?");
			}
		}
	} 
	
	public void throwBack() {
		ChatbotMain.print("Then what is it that you are looking for?");
		direct();
	}
	
	public void evaluate() {
		collectInfo();
		ChatbotMain.print("Are you looking for anything specific? If so, what?");
		direct();
	}
	
	public void collectInfo() {
		forLiving = ChatbotMain.YesNo("Are you looking for a residence?");
		if(forLiving) {
			ChatbotMain.print("How many people will be living in there?");
			familySize = ChatbotMain.getPositiveNumInput();
			hasPets = ChatbotMain.YesNo("Will pets be living with you?");
			buying = ChatbotMain.YesNo("Are you looking to buy?");
		}
	}

}
