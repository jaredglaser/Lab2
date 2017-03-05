package pkgPokerBLL;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import pkgPokerEnum.eCardNo;
import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;



public class Hand {

	private UUID HandID;
	private boolean bIsScored;
	private HandScore HS;
	private ArrayList<Card> CardsInHand = new ArrayList<Card>();

	public Hand()
	{

	}

	public void AddCardToHand(Card c)
	{
		CardsInHand.add(c);
	}

	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	public HandScore getHandScore()
	{
		return HS;
	}

	public Hand EvaluateHand()
	{
		Hand h = Hand.EvaluateHand(this);
		return h;
	}

	private static Hand EvaluateHand(Hand h)  {

		Collections.sort(h.getCardsInHand());

		//	Another way to sort
		//	Collections.sort(h.getCardsInHand(), Card.CardRank);

		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pkgPokerBLL.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pkgPokerBLL.Hand.class;
				cArg[1] = pkgPokerBLL.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bIsScored = true;
			h.HS = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}












	public static boolean isHandRoyalFlush(Hand h, HandScore hs)
	{
		//if it is a flush AND the first card is a ten AND the cards go from ten to ace.
		if(isFlush(h) && h.getCardsInHand().get(0).geteRank() == eRank.TEN && isStraight(h)){
			return true;
		}
		return false;
	}


	public static boolean isHandStraightFlush(Hand h, HandScore hs)
	{
		return (isFlush(h) && isStraight(h));
	}	

	public static boolean isHandFourOfAKind(Hand h, HandScore hs)
	{
		//counts the number in a row of the same card rank
		int inRow = 1;
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){
			if(inRow == 4){
				return true;
			}
			//next card does match in rank
			if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				inRow += 1;
			}
			//next card does not match in rank
			else
				inRow = 1;
		}
		//catch if the last card was matching
		if(inRow == 4){
			return true;
		}
		//there were not four matching rank cards
		return false;
	}	

	public static boolean isHandFlush(Hand h, HandScore hs)
	{
		return isFlush(h);
	}		

	public static boolean isHandStraight(Hand h, HandScore hs)
	{
		return isStraight(h);
	}	

	public static boolean isHandThreeOfAKind(Hand h, HandScore hs)
	{
		//counts the number in a row of the same card rank
		int inRow = 1;
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){
			if(inRow == 3){
				return true;
			}
			//next card does match in rank
			if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				inRow += 1;
			}
			//next card does not match in rank
			else
				inRow = 1;
		}
		//catch if the last card was matching
		if(inRow == 3){
			return true;
		}
		//there were not three matching rank cards
		return false;
	}		

	public static boolean isHandTwoPair(Hand h, HandScore hs)
	{

		//counts the number of pairs
		int pairs = 1;
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){
			//next card does match in rank
			if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				pairs += 1;
			}
		}
		if(pairs == 2){
			return true;
		}
		//there were not two pairs
		else
			return false;
	}	

	public static boolean isHandPair(Hand h, HandScore hs)
	{
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){
			//next card does match in rank
			if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				return true;
			}
		}
		return false;
	}	

	//TODO: Implement This Method
	public static boolean isHandHighCard(Hand h, HandScore hs)
	{
		return false;
	}	

	//TODO: Implement This Method
	public static boolean isAcesAndEights(Hand h, HandScore hs)
	{
		//counts the number of aces and eights
		int aces = 0;
		int eights = 0;
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size(); i++){
			//card is ace
			if(h.getCardsInHand().get(i).geteRank() == eRank.ACE){
				aces +=1;
			}
			//card is eight
			if(h.getCardsInHand().get(i).geteRank() == eRank.EIGHT){
				eights +=1;
			}

		}
		if(aces == 2 && eights == 2){
			return true;
		}
		//there were not two pairs of aces and eights
		else
			return false;
	}	


	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}

		return isFullHouse;

	}

	private static boolean isStraight(Hand h){
		//		//if the first card is not 2-10, then it cannot be a straight.
		//		if(h.getCardsInHand().get(0).geteRank() == eRank.ACE || h.getCardsInHand().get(0).geteRank() == eRank.KING || h.getCardsInHand().get(0).geteRank() == eRank.QUEEN || h.getCardsInHand().get(0).geteRank() == eRank.JACK ){
		//			return false;
		//		}
		for(int i = 0; i < h.getCardsInHand().size()-1; i++){
			//if the cards are NOT in order then it is NOT a straight.
			if(!(h.getCardsInHand().get(i).geteRank().compareTo(h.getCardsInHand().get(i+1).geteRank()) == 1)){
				return false;
			}
		}
		return true;
	}

	private static boolean isFlush(Hand h){
		for(int i = 0; i < h.getCardsInHand().size()-1; i++){
			//if the card suits are different OR the card ranks are the same. It is not a flush.
			if(h.getCardsInHand().get(i).geteSuit() != h.getCardsInHand().get(i+1).geteSuit() || h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				return false;
			}
		}
		return true;
	}
}
