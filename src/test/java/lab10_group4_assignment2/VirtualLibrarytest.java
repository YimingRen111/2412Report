package lab10_group4_assignment2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class VirtualLibrarytest {

    private VirtualLibrary library;

    @BeforeEach
    public void setUp() {
        library = new VirtualLibrary();
    }

    @Test
    public void testAddDigitalScrollFromBinary() throws IOException {
        String binaryData = "...";
        String uploader_name = "testUploader";
        String filename = "testFilename";
        library.addDigitalScrollFromBinary(binaryData, uploader_name, filename);
        assertNotNull(library.getDigitalScroll(String.valueOf(VirtualLibrary.IDNUM - 1)));
    }

    @Test
    public void testEditDigitalScroll() throws IOException {
        String id = "2"; //
        String newContent = "new content";
        String uploader_name = "newUploader";
        library.editDigitalScroll(id, newContent, uploader_name);
        assertEquals(newContent, library.getScrollContent(id));
    }

    @Test
    public void testDownloadAction() throws IOException {
        String id = "2";
        DigitalScroll originalScroll = library.getDigitalScroll(id);
        int originalDownloadCount = originalScroll.getDownload();
        library.downloadAction(originalScroll);

        DigitalScroll updatedScroll = library.getDigitalScroll(id);
        assertEquals(originalDownloadCount + 1, updatedScroll.getDownload());
    }

    @Test
    public void testRemoveDigitalScroll() throws IOException {
        String id = "2";
        library.removeDigitalScroll(id);

        assertNull(library.getDigitalScroll(id));
    }

    @Test
    public void testGetAllDigitalScrolls() {
        ArrayList<DigitalScroll> scrolls = library.getAllDigitalScrolls();
        assertFalse(scrolls.isEmpty());
    }

    @Test
    public void testIsExist() {
        String existingId = "2";
        String nonExistingId = "10000";

        assertTrue(library.isExist(existingId));
        assertFalse(library.isExist(nonExistingId));
    }


}
