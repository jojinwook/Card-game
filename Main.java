package card;
import java
import java.util.*;
import java.io.*;
import card.Card;

public class Main {
	private static final Card cards[] = { new Card("오크", 5, 1), new Card("인간", 3, 3), new Card("마법사", 1, 5), new Card("용", 3, 0), new Card("언데드", 2, 4)};	// 카드목록  
	
	private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));  
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
	public static void main(String[] args) throws Exception {   //throws Exception 예외 처리 
		Player p1 = new Player("Player 1");
		Player p2 = new Player("Player 2");
		
		while (p1.isAlive() && p2.isAlive()) {
			setRandomCards(p1.getCards());
			printCards(p1);
			Card p1Cards[] = selectCards(p1);
			
			setRandomCards(p2.getCards());
			printCards(p2);
			Card p2Cards[] = selectCards(p2);
			
			int p1Hp = 0; // 플레이어에게 들어갈 데미지     
			int p1Damage = 0;   // 공격가능한 데미지
			int p2Hp = 0;  
			int p2Damage = 0;
			
			for (int i = 0; i < 3; i++) {
				p1Hp += p1Cards[i].getHp();    // 총합 체력 데미지 설정   
				p1Damage += p1Cards[i].getDamage();
				
				p2Hp += p2Cards[i].getHp();
				p2Damage += p2Cards[i].getDamage();
			}
			p1.addHp(Math.min(0, p1Hp - p2Damage)); // 플레이어당 체력 빼주기 
			p2.addHp(Math.min(0, p2Hp - p1Damage));
		}
		clearScreen();    // clearScreen 
		bw.write(p1.getName());
		bw.write(" 님의 체력: ");
		bw.write(String.valueOf(p1.getHp()));
		bw.write("\n");
		bw.write(p2.getName());
		bw.write(" 님의 체력: ");
		bw.write(String.valueOf(p2.getHp()));
		bw.write("\n");
		bw.flush();   // 체력 
	}

	private static void setRandomCards(ArrayList<Card> c) {
		c.clear();
		for (int i = 0; i < 5; i++) {
			c.add(cards[(int) (cards.length * Math.random())]);  //  랜덤 카드 
		}
	}

	private static void printCards(Player p) throws IOException {
		clearScreen();
		bw.write(p.getName());
		bw.write("님의 카드\n----------------------------------------------\n");
		int i = 0;
		for (Card card : p.getCards()) {
			bw.write(String.valueOf(i++));
			bw.write("번째 카드: (데미지: ");
			bw.write(String.valueOf(card.getDamage()));
			bw.write("체력: ");
			bw.write(String.valueOf(card.getHp()));
			bw.write(")");
			bw.write(card.getName());
			bw.write("\n\n");
		}
		bw.flush();    // 플레이어들이 가지고 있는 카드 소개   
	}

	private static Card[] selectCards(Player p) throws Exception {
		Card cards[] = new Card[3];
		bw.write(p.getName());
		bw.write(" 선택한 카드 3장의 번호를 순서대로 입력해주세요.\n\n ex) 123\n");
		bw.flush();
		String selectedCards = br.readLine();
		
		int check = 0;
	
		
		for (int i = 0; i < 3; i++) {
			int selectedCardNumber = selectedCards.charAt(i) - '0';
			cards[i] = p.getCards().get(selectedCardNumber);
			if ((check & (1 << selectedCardNumber)) > 0)
				throw new Error("같은 카드는 사용이 불가능합니다.");
			check |= 1 << selectedCardNumber;             // 비트마스킹?크?   카드선택과 중본선택 x  83~85    
		}
		
		return cards;
	}

	private static void clearScreen() throws IOException {
		for (int i = 0; i < 80; i++)
			bw.write("\n");
		bw.flush();    
	}
}
####
class Player {
	private String name;
	private int hp = 10;
	private ArrayList<Card> cards = new ArrayList<>();

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public void addHp(int amount) {
		hp += amount;
	}

	public boolean isAlive() {
		return hp > 0;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
}
