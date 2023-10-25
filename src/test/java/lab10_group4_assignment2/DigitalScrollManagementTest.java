package lab10_group4_assignment2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DigitalScrollManagementTest {

    private DigitalScrollManagement management;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        management = new DigitalScrollManagement();
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void testListFiles() {
        management.listFiles(new File("src/main/Local"));
        assertTrue(outContent.toString().contains("tester.txt"));
    }

    @Test
    public void testFindFilePath() {
        String path = management.findFilePath(new File("src/main/Local"), "tester.txt");
        assertEquals("src/main/Local/tester.txt", path);
    }

    @Test
    public void testIsBinary() {
        byte[] validBinaryData = "01010101 10101010".getBytes();
        byte[] invalidBinaryData = "This is not binary.".getBytes();

        assertTrue(management.is_Binary(validBinaryData));
        assertTrue(!management.is_Binary(invalidBinaryData));
    }

    @Test
    public void testBinaryToString() {
        String binary = "01001000 01100101 01101100 01101100 01101111";
        String expected = "Hello";
        String result = DigitalScrollManagement.binaryToString(binary);
        assertEquals(expected, result);
    }



}
