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
    public void partHolder_partCount_partTotal_partHolderReturned() {
        String testPartCount = "1";
        String testPartTotal = "6";
        String testParticipant = testPartCount + " | " + testPartTotal;
        assertEquals(StringChecker.partHolder(testPartCount, testPartTotal), testParticipant);

        String testPartCount2 = null;
        String testPartTotal2 = null;
        String testParticipant2 = testPartCount2 + " | " + testPartTotal2;
        assertEquals(StringChecker.partHolder(testPartCount2, testPartTotal2), testParticipant2);
    }

    @Test
    public void add_initPartCount_endPartCountReturned() {
        assertEquals(StringChecker.add("1"), "2");
    }

    @Test
    public void sub_initPartCount_endPartCountReturned() {
        assertEquals(StringChecker.sub("1"), "0");
    }

}