package pkgPokerBLL;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class TestHands {

	@Test
	public void TestFullHouse() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.THREE,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.THREE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FOUR,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.FOUR,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FOUR,eSuit.SPADES));		
		h.EvaluateHand();

		//	Hand better be a full house
		assertEquals(eHandStrength.FullHouse.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		//	HI hand better be 'Four'
		assertEquals(eRank.FOUR.getiRankNbr(),
				h.getHandScore().getHiHand().getiRankNbr());

		//	LO hand better be 'Three'
		assertEquals(eRank.THREE.getiRankNbr(),
				h.getHandScore().getLoHand().getiRankNbr());

		//	Full House has no kickers.
		assertEquals(0,h.getHandScore().getKickers().size());
	}

	@Test
	public void TestIsAcesAndEights() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.ACE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.ACE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.THREE,eSuit.SPADES));		
		h.EvaluateHand();

		//	Hand better be an aces and eights
		assertEquals(eHandStrength.AcesEights.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		//	HI hand better be 'Null'
		assertEquals(null,
				h.getHandScore().getHiHand());

		//	LO hand better be 'Null'
		assertEquals(null,
				h.getHandScore().getLoHand());

		//	Aces and Eights has 1 kicker.
		assertEquals(1,h.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestIsAcesAndEights2() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.THREE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.ACE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));		
		h.EvaluateHand();

		//	Hand better be an aces and eights
		assertEquals(eHandStrength.AcesEights.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		//	HI hand better be 'Null'
		assertEquals(null,
				h.getHandScore().getHiHand());

		//	LO hand better be 'Null'
		assertEquals(null,
				h.getHandScore().getLoHand());

		//	Aces and Eights has 1 kicker.
		assertEquals(1,h.getHandScore().getKickers().size());
	}

	@Test
	public void TestIsHandHighCard(){
		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.ACE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.TEN,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.SIX,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.THREE,eSuit.SPADES));		
		h.EvaluateHand();

		//	Hand better be a highcard
		assertEquals(eHandStrength.HighCard.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		//	HI hand better be 'Ace'
		assertEquals(eRank.ACE.getiRankNbr(),
				h.getHandScore().getHiHand().getiRankNbr());

		//	LO hand better be 'Ten'
		assertEquals(eRank.TEN.getiRankNbr(),
				h.getHandScore().getLoHand().getiRankNbr());

		//	High hand has 3 kickers.
		assertEquals(3,h.getHandScore().getKickers().size());
	}

	@Test
	public void TestIsHandHighCard2(){
		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.THREE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.TEN,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.SIX,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));		
		h.EvaluateHand();

		//	Hand better be a highcard
		assertEquals(eHandStrength.HighCard.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		//	HI hand better be 'Ace'
		assertEquals(eRank.ACE.getiRankNbr(),
				h.getHandScore().getHiHand().getiRankNbr());

		//	LO hand better be 'Ten'
		assertEquals(eRank.TEN.getiRankNbr(),
				h.getHandScore().getLoHand().getiRankNbr());

		//	High hand has 3 kickers.
		assertEquals(3,h.getHandScore().getKickers().size());
	}
	@Test
	public void TestIsHandPair(){
		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.QUEEN,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.QUEEN,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.SIX,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.THREE,eSuit.SPADES));		
		h.EvaluateHand();

		//	Hand better be a Pair
		assertEquals(eHandStrength.Pair.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		//	HI hand better be 'Ace'
		assertEquals(eRank.QUEEN.getiRankNbr(),
				h.getHandScore().getHiHand().getiRankNbr());

		//	LO hand better be 'Null'
		assertEquals(null,
				h.getHandScore().getLoHand());

		//	Pair has 3 kickers.
		assertEquals(3,h.getHandScore().getKickers().size());
	}

	@Test
	public void TestIsHandPair2(){
		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.QUEEN,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.SIX,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.QUEEN,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.THREE,eSuit.SPADES));		
		h.EvaluateHand();

		//	Hand better be a Pair
		assertEquals(eHandStrength.Pair.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		//	HI hand better be 'Ace'
		assertEquals(eRank.QUEEN.getiRankNbr(),
				h.getHandScore().getHiHand().getiRankNbr());

		//	LO hand better be 'Null'
		assertEquals(null,
				h.getHandScore().getLoHand());

		//	Pair has 3 kickers.
		assertEquals(3,h.getHandScore().getKickers().size());
	}

	@Test
	public void TestRoyalFlush(){  
		Hand q = new Hand();
		q.AddCardToHand(new Card(eRank.ACE,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.KING,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.QUEEN,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.JACK,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.TEN,eSuit.CLUBS));      
		q.EvaluateHand();

		assertEquals(eHandStrength.RoyalFlush.getHandStrength(),
				q.getHandScore().getHandStrength().getHandStrength());

		assertEquals(null,
				q.getHandScore().getHiHand());

		assertEquals(null,
				q.getHandScore().getLoHand());

		assertEquals(0,q.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestRoyalFlush2(){  
		Hand q = new Hand();
		q.AddCardToHand(new Card(eRank.KING,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.JACK,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.TEN,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.ACE,eSuit.CLUBS));
		q.AddCardToHand(new Card(eRank.QUEEN,eSuit.CLUBS));      
		q.EvaluateHand();

		assertEquals(eHandStrength.RoyalFlush.getHandStrength(),
				q.getHandScore().getHandStrength().getHandStrength());

		assertEquals(null,
				q.getHandScore().getHiHand());

		assertEquals(null,
				q.getHandScore().getLoHand());

		assertEquals(0,q.getHandScore().getKickers().size());
	}
	@Test
	public void TestStraightFlush(){
		Hand c = new Hand();
		c.AddCardToHand(new Card(eRank.TEN,eSuit.CLUBS));
		c.AddCardToHand(new Card(eRank.NINE,eSuit.CLUBS));
		c.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		c.AddCardToHand(new Card(eRank.SEVEN,eSuit.CLUBS));
		c.AddCardToHand(new Card(eRank.SIX,eSuit.CLUBS));      
		c.EvaluateHand();

		assertEquals(eHandStrength.StraightFlush.getHandStrength(),
				c.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.TEN.getiRankNbr(),
				c.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				c.getHandScore().getLoHand());

		assertEquals(0,c.getHandScore().getKickers().size());
	}
	@Test
	public void TestStraightFlush2(){
		Hand c = new Hand();
		c.AddCardToHand(new Card(eRank.TEN,eSuit.SPADES));
		c.AddCardToHand(new Card(eRank.NINE,eSuit.SPADES));
		c.AddCardToHand(new Card(eRank.EIGHT,eSuit.SPADES));
		c.AddCardToHand(new Card(eRank.SEVEN,eSuit.SPADES));
		c.AddCardToHand(new Card(eRank.JACK,eSuit.SPADES));      
		c.EvaluateHand();

		assertEquals(eHandStrength.StraightFlush.getHandStrength(),
				c.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.JACK.getiRankNbr(),
				c.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				c.getHandScore().getLoHand());

		assertEquals(0,c.getHandScore().getKickers().size());
	}
	@Test
	public void TestisFourKind(){
		Hand b = new Hand();
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));
		b.AddCardToHand(new Card(eRank.TEN,eSuit.CLUBS));      
		b.EvaluateHand();

		assertEquals(eHandStrength.FourOfAKind.getHandStrength(),
				b.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.FIVE.getiRankNbr(),
				b.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				b.getHandScore().getLoHand());

		assertEquals(1,b.getHandScore().getKickers().size());
	}
	@Test
	public void TestisFourKind2(){
		Hand b = new Hand();
		b.AddCardToHand(new Card(eRank.QUEEN,eSuit.HEARTS));
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));
		b.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));      
		b.EvaluateHand();

		assertEquals(eHandStrength.FourOfAKind.getHandStrength(),
				b.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.FIVE.getiRankNbr(),
				b.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				b.getHandScore().getLoHand());

		assertEquals(1,b.getHandScore().getKickers().size());
	}
	@Test
	public void TestisFlush(){
		Hand a = new Hand();
		a.AddCardToHand(new Card(eRank.QUEEN,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.TEN,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.SIX,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.SEVEN,eSuit.SPADES));       
		a.EvaluateHand();

		assertEquals(eHandStrength.Flush.getHandStrength(),
				a.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.QUEEN.getiRankNbr(),
				a.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				a.getHandScore().getLoHand());

		assertEquals(4,a.getHandScore().getKickers().size());
	}
	@Test
	public void TestisFlush2(){
		Hand a = new Hand();
		a.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));
		a.AddCardToHand(new Card(eRank.TEN,eSuit.CLUBS));
		a.AddCardToHand(new Card(eRank.QUEEN,eSuit.CLUBS));
		a.AddCardToHand(new Card(eRank.SEVEN,eSuit.CLUBS));
		a.AddCardToHand(new Card(eRank.SIX,eSuit.CLUBS));       
		a.EvaluateHand();

		assertEquals(eHandStrength.Flush.getHandStrength(),
				a.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.QUEEN.getiRankNbr(),
				a.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				a.getHandScore().getLoHand());

		assertEquals(4,a.getHandScore().getKickers().size());
	}
	@Test
	public void TestisStraight(){
		Hand d = new Hand();
		d.AddCardToHand(new Card(eRank.SIX,eSuit.CLUBS));
		d.AddCardToHand(new Card(eRank.FOUR,eSuit.SPADES));
		d.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));
		d.AddCardToHand(new Card(eRank.THREE,eSuit.SPADES));
		d.AddCardToHand(new Card(eRank.TWO,eSuit.DIAMONDS));       
		d.EvaluateHand();

		assertEquals(eHandStrength.Straight.getHandStrength(),
				d.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.SIX.getiRankNbr(),
				d.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				d.getHandScore().getLoHand());

		assertEquals(0,d.getHandScore().getKickers().size());
	}
	@Test
	public void TestisStraight2(){
		Hand d = new Hand();
		d.AddCardToHand(new Card(eRank.JACK,eSuit.CLUBS));
		d.AddCardToHand(new Card(eRank.TEN,eSuit.SPADES));
		d.AddCardToHand(new Card(eRank.NINE,eSuit.HEARTS));
		d.AddCardToHand(new Card(eRank.EIGHT,eSuit.SPADES));
		d.AddCardToHand(new Card(eRank.SEVEN,eSuit.DIAMONDS));       
		d.EvaluateHand();

		assertEquals(eHandStrength.Straight.getHandStrength(),
				d.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.JACK.getiRankNbr(),
				d.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				d.getHandScore().getLoHand());

		assertEquals(0,d.getHandScore().getKickers().size());
	}
	@Test
	public void TestThreeOfAKind(){ 
		Hand e = new Hand();
		e.AddCardToHand(new Card(eRank.SEVEN,eSuit.CLUBS));
		e.AddCardToHand(new Card(eRank.EIGHT,eSuit.SPADES));
		e.AddCardToHand(new Card(eRank.SEVEN,eSuit.HEARTS));
		e.AddCardToHand(new Card(eRank.SEVEN,eSuit.SPADES));
		e.AddCardToHand(new Card(eRank.TWO,eSuit.DIAMONDS));       
		e.EvaluateHand();

		assertEquals(eHandStrength.ThreeOfAKind.getHandStrength(),
				e.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.SEVEN.getiRankNbr(),
				e.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				e.getHandScore().getLoHand());

		assertEquals(2,e.getHandScore().getKickers().size());
	}
	@Test
	public void TestThreeOfAKind2(){ 
		Hand e = new Hand();
		e.AddCardToHand(new Card(eRank.TWO,eSuit.CLUBS));
		e.AddCardToHand(new Card(eRank.EIGHT,eSuit.SPADES));
		e.AddCardToHand(new Card(eRank.SEVEN,eSuit.HEARTS));
		e.AddCardToHand(new Card(eRank.SEVEN,eSuit.SPADES));
		e.AddCardToHand(new Card(eRank.SEVEN,eSuit.DIAMONDS));       
		e.EvaluateHand();

		assertEquals(eHandStrength.ThreeOfAKind.getHandStrength(),
				e.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.SEVEN.getiRankNbr(),
				e.getHandScore().getHiHand().getiRankNbr());

		assertEquals(null,
				e.getHandScore().getLoHand());

		assertEquals(2,e.getHandScore().getKickers().size());
	}
	@Test
	public void TestTwoPair(){
		Hand y = new Hand();
		y.AddCardToHand(new Card(eRank.NINE,eSuit.CLUBS));
		y.AddCardToHand(new Card(eRank.EIGHT,eSuit.SPADES));
		y.AddCardToHand(new Card(eRank.NINE,eSuit.HEARTS));
		y.AddCardToHand(new Card(eRank.EIGHT,eSuit.SPADES));
		y.AddCardToHand(new Card(eRank.THREE,eSuit.DIAMONDS));     
		y.EvaluateHand();

		assertEquals(eHandStrength.TwoPair.getHandStrength(),
				y.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.NINE.getiRankNbr(),
				y.getHandScore().getHiHand().getiRankNbr());

		assertEquals(eRank.EIGHT.getiRankNbr(),
				y.getHandScore().getLoHand().getiRankNbr());

		assertEquals(1,y.getHandScore().getKickers().size());
	}
	@Test
	public void TestTwoPair2(){
		Hand y = new Hand();
		y.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		y.AddCardToHand(new Card(eRank.NINE,eSuit.SPADES));
		y.AddCardToHand(new Card(eRank.NINE,eSuit.HEARTS));
		y.AddCardToHand(new Card(eRank.TWO,eSuit.SPADES));
		y.AddCardToHand(new Card(eRank.EIGHT,eSuit.DIAMONDS));     
		y.EvaluateHand();

		assertEquals(eHandStrength.TwoPair.getHandStrength(),
				y.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.NINE.getiRankNbr(),
				y.getHandScore().getHiHand().getiRankNbr());

		assertEquals(eRank.EIGHT.getiRankNbr(),
				y.getHandScore().getLoHand().getiRankNbr());

		assertEquals(1,y.getHandScore().getKickers().size());
	}


}
