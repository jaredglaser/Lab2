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
		if(isFlush(h) && h.getCardsInHand().get(0).geteRank() == eRank.ACE && isStraight(h)){
			hs.setHandStrength(eHandStrength.RoyalFlush);
			return true;
		}
		return false;
	}


	public static boolean isHandStraightFlush(Hand h, HandScore hs)
	{
		if((isFlush(h) && isStraight(h))){

			hs.setHandStrength(eHandStrength.StraightFlush);
			hs.setHiHand(h.getCardsInHand().get(0).geteRank());
			//hs.setLoHand(h.getCardsInHand().get(1).geteRank()); I do not think there is a lo hand.

			return true;
		}
		return false;
	}	

	public static boolean isHandFourOfAKind(Hand h, HandScore hs)
	{
		//counts the number in a row of the same card rank
		int inRow = 1;
		//loop through all cards
		int position = 0;
		int badposition = -1;
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){

			//next card does match in rank
			if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				position = i;
				inRow += 1;
			}

			//next card does not match in rank
			else
				inRow = 1;
			badposition = i;
		}
		//there are four in a row
		if(inRow == 4){
			ArrayList<Card> kickers = new ArrayList<Card>();

			hs.setHandStrength(eHandStrength.FourOfAKind);
			hs.setHiHand(h.getCardsInHand().get(position).geteRank());
			if(badposition != -1){
				kickers.add(h.getCardsInHand().get(h.getCardsInHand().size()-1));
			}
			else
				kickers.add(h.getCardsInHand().get(badposition));
			hs.setKickers(kickers);
			return true;
		}
		//there were not four matching rank cards
		return false;
	}	

	public static boolean isHandFlush(Hand h, HandScore hs)
	{
		if(isFlush(h)){
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(1));
			kickers.add(h.getCardsInHand().get(2));
			kickers.add(h.getCardsInHand().get(3));
			kickers.add(h.getCardsInHand().get(4));

			hs.setHandStrength(eHandStrength.Flush);
			hs.setHiHand(h.getCardsInHand().get(0).geteRank());
			hs.setKickers(kickers);
			return true;
		}
		return false;
	}		

	public static boolean isHandStraight(Hand h, HandScore hs)
	{
		if(isStraight(h)){

			hs.setHandStrength(eHandStrength.Straight);
			hs.setHiHand(h.getCardsInHand().get(0).geteRank());

			return true;
		}
		return false;
	}	

	public static boolean isHandThreeOfAKind(Hand h, HandScore hs)
	{
		ArrayList<Card> high = new ArrayList<Card>();
		ArrayList<Card> kickers = new ArrayList<Card>();

		boolean isthree = false;
		//counts the number in a row of the same card rank
		int inRow = 1;
		int loc = -1;
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){
			if(inRow == 3){
				isthree = true;
			}
			//next card does match in rank
			else if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				high.add(h.getCardsInHand().get(i));
				inRow += 1;
				loc = i;
			}
			//next card does not match in rank
			else{
				kickers.add(h.getCardsInHand().get(i));
				inRow = 1;
			}
		}
		//catch if the last card was matching
		if(inRow == 3){

			high.add(h.getCardsInHand().get(4));

			if(kickers.size() == 1){//if there was 1 kicker.
				kickers.add(h.getCardsInHand().get(h.getCardsInHand().size()-1));//must have been the last card that wasn't checked.
			}
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(loc).geteRank());
			hs.setKickers(kickers);
			return true;
		}
		//there were not three matching rank cards
		return false;
	}		

	public static boolean isHandTwoPair(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		ArrayList<Integer> pairloc = new ArrayList<Integer>();
		//counts the number of pairs
		int pairs = 0;
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){
			//next card does match in rank
			if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				pairs += 1;
				pairloc.add(i);
				i++;
			}
			else
				kickers.add(h.getCardsInHand().get(i));
		}
		if(pairs == 2){

			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(pairloc.get(0)).geteRank());//the first card of the pairs is the highest
			hs.setLoHand(h.getCardsInHand().get(pairloc.get(1)).geteRank());//the third card of the pairs is the lowest
			if(kickers.size() == 0){ //if there aren't any kickers
				kickers.add(h.getCardsInHand().get(h.getCardsInHand().size()-1)); //must have been the last card that wasn't checked
			}
			hs.setKickers(kickers);
			return true;
		}
		//there were not two pairs
		else
			return false;
	}	

	public static boolean isHandPair(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		ArrayList<Integer> pairloc = new ArrayList<Integer>();
		boolean pair = false;
		//loop through all cards
		for(int i = 0; i< h.getCardsInHand().size()-1; i++){
			//next card does match in rank
			if( h.getCardsInHand().get(i).geteRank() == h.getCardsInHand().get(i+1).geteRank()){
				pairloc.add(i);
				pair = true;
			}
			else{
				kickers.add(h.getCardsInHand().get(i));
			}
		}
		if(pair){
			hs.setHandStrength(eHandStrength.Pair);
			hs.setHiHand(h.getCardsInHand().get(pairloc.get(0)).geteRank());
			hs.setLoHand(null);
			if(kickers.size() == 2){ //if there are 2 kickers, then the last card is a kicker and wasn't part of a pair
				kickers.add(h.getCardsInHand().get(h.getCardsInHand().size()-1));
			}
			hs.setKickers(kickers);
			return true;
		}
		return false;
	}	


	public static boolean isHandHighCard(Hand h, HandScore hs)
	{
		// sets the HandStrength as HighCard and finds the highCard and the SecondHighest card and
		// sets them as the HighHand and LowHand respectively 

		ArrayList<Card> kickers = new ArrayList<Card>();
		kickers.add(h.getCardsInHand().get(2));
		kickers.add(h.getCardsInHand().get(3));
		kickers.add(h.getCardsInHand().get(4));
		hs.setKickers(kickers);

		hs.setHandStrength(eHandStrength.HighCard);
		hs.setHiHand(h.getCardsInHand().get(0).geteRank());
		hs.setLoHand(h.getCardsInHand().get(1).geteRank());

		return true;
	}	


	public static boolean isAcesAndEights(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();

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
			else if(h.getCardsInHand().get(i).geteRank() == eRank.EIGHT){

				eights +=1;
			}
			else
				kickers.add(h.getCardsInHand().get(i));

		}
		if(aces == 2 && eights == 2){
			hs.setHandStrength(eHandStrength.AcesEights);
			hs.setHiHand(null);
			hs.setLoHand(null);
			hs.setKickers(kickers);
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
