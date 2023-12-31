package data_access_tests;

import data_access.API.APIFactory;
import data_access.API.DeleteDataPostAPI;
import data_access.API.UploadUserFilePostAPI;
import data_access.DeleteNoteDAO;
import data_access.SaveNoteDAO;
import data_access.file_read_write.AllUserFilesDAO;
import data_access.file_read_write.DeleteNoteWriterDAO;
import data_access.file_read_write.FileAccessDAO;
import data_access.file_read_write.TextNoteWriterDAO;
import entity.TextNote;
import entity.TextNoteBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeleteNoteDAOTests {
    private DeleteNoteDAO deleteNoteDAO;
    private SaveNoteDAO saveNoteDAO;
    private AllUserFilesDAO allUserFilesDAO;
    private final String testUser = "Test User";
    private TextNote textNote;

    @Before
    public void init(){
        TextNoteWriterDAO textNoteWriterDAO = new TextNoteWriterDAO();
        DeleteNoteWriterDAO deleteNoteWriterDAO = new DeleteNoteWriterDAO();
        allUserFilesDAO = new AllUserFilesDAO();
        textNote = TextNoteBuilder.createTextNote("TestFile",
                LocalDateTime.now(),
                testUser, "Test Data");
        UploadUserFilePostAPI uploadAPI = APIFactory.uploadAPI();
        DeleteDataPostAPI deleteAPI = APIFactory.deleteAPI();
        saveNoteDAO = new SaveNoteDAO(textNoteWriterDAO, allUserFilesDAO, uploadAPI);
        deleteNoteDAO = new DeleteNoteDAO(allUserFilesDAO, deleteNoteWriterDAO, deleteAPI);
    }

    @Test
    public void testDeleteFileReturnsTrue(){
        saveNoteDAO.saveNote(textNote);
        assertTrue(deleteNoteDAO.deleteNote(testUser, textNote.getFileName()));
    }

    @Test
    public void testDeleteOneFile(){
        TextNote textNote1 = TextNoteBuilder.createTextNote("TestFile1",
                LocalDateTime.now(),
                testUser, "Test Data1");
        TextNote textNote2 = TextNoteBuilder.createTextNote("TestFile2",
                LocalDateTime.now(),
                testUser, "Test Data2");
        saveNoteDAO.saveNote(textNote1);
        saveNoteDAO.saveNote(textNote2);
        saveNoteDAO.saveNote(textNote);
        ArrayList<String> fileList = new ArrayList<>();
        fileList.add("TestFile");
        fileList.add("TestFile1");
        fileList.add("TestFile2");
        deleteNoteDAO.deleteNote(testUser, "TestFile1");
        ArrayList<String> userFiles = allUserFilesDAO.getAllUserFiles(testUser);
        assertFalse(userFiles.contains("TestFile1"));
    }

    @After
    public void deleteTestFiles(){
        String root = FileAccessDAO.ROOT_DIR;
        File f = new File(root + testUser);
        if (f.exists()){
            try{
                FileUtils.deleteDirectory(f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
