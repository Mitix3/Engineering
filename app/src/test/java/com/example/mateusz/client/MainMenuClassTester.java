package com.example.mateusz.client;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainMenuClassTester {

    private MainMenu testMenu;

    @Before
    public void initialize()
    {
        testMenu = new MainMenu();
    }

    @Test
    public void checkIfIpAddressMatches(){

        assertFalse(testMenu.ipAddressIsWrittenProperly("100.200.300.100"));
        assertFalse(testMenu.ipAddressIsWrittenProperly("123456789"));
        assertFalse(testMenu.ipAddressIsWrittenProperly("-192.168.102.14"));
        assertTrue(testMenu.ipAddressIsWrittenProperly("192.168.102.14"));

    }
    @Test
    public void checkIfPortMatches(){

        assertFalse(testMenu.portIsNumber("ABCDEF"));
        assertTrue(testMenu.portIsNumber("5123163"));
        assertTrue(testMenu.portIsNumber("5123"));

    }



    public void checkDivideTextFromFileIntoIpAndPortParts(){

        try
        {
            testMenu.divideTextFromFileIntoIpAndPortParts("192.192.9255123");
        }
        catch(RuntimeException re)
        {
            String message = "File's content is formatted in weird way...";
            assertEquals(message, re.getMessage());
            throw re;
        }

    }
    @Test
    public void checkDivideTextFromFileIntoIpAndPortPartsNextCondition() {

        testMenu.divideTextFromFileIntoIpAndPortParts("192.168.123.21 5432");

        assertEquals("192.168.123.21",testMenu.getIpPartOfString());
        assertEquals("5432",testMenu.getPortPartOfString());


    }

}



