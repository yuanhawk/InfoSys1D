package tech.sutd.pickupgame.util;

import org.junit.Test;

import tech.sutd.pickupgame.R;

import static org.junit.Assert.*;

public class StringCheckerTest {

    @Test
    public void caseImage() {
        assertEquals(StringChecker.caseImage("Badminton"), R.drawable.ic_badminton);
        assertEquals(StringChecker.caseImage("Cycling"), R.drawable.ic_cycling);
        assertEquals(StringChecker.caseImage("Table Tennis"), R.drawable.ic_ping_pong);
        assertEquals(StringChecker.caseImage("Volleyball"), R.drawable.ic_voll);
        assertEquals(StringChecker.caseImage("Running"), R.drawable.ic_running);
    }

    @Test
    public void partHolder() {
        String testPartCount = "1";
        String testPartTotal = "6";
        String testParticipant = testPartCount + " | " + testPartTotal;
        assertEquals(StringChecker.partHolder(testPartCount, testPartTotal), testParticipant);
    }

    @Test
    public void addPartCount() {
        assertEquals(StringChecker.add("1"), "2");
    }

    @Test
    public void minusPartCount() {
        assertEquals(StringChecker.sub("1"), "0");
    }

}