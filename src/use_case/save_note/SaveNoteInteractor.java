package use_case.save_note;

import entity.TextNote;
import entity.TextNoteBuilder;
import use_case.NoteOutputData;

import java.util.ArrayList;

/**
 * Save Note Use Case interactor to use the save note data access interface and tell the presenter to prepare success or
 * fail views.
 */
public class SaveNoteInteractor implements SaveNoteInputBoundary{
    private final SaveNoteOutputBoundary saveNotePresenter;
    private final SaveNoteDataAccessInterface saveNoteDAO;
    public SaveNoteInteractor(SaveNoteOutputBoundary saveNotePresenter, SaveNoteDataAccessInterface saveNoteDAO){
        this.saveNotePresenter = saveNotePresenter;
        this.saveNoteDAO = saveNoteDAO;
    }

    /**
     * Save the current file using the data access object based on the input data from the user
     * @param saveNoteInputData The input data from the controller to be saved to the file
     */
    @Override
    public void saveFile(SaveNoteInputData saveNoteInputData) {
        TextNote thisNote = TextNoteBuilder.createTextNote(
                saveNoteInputData.getFileName(),
                saveNoteInputData.getCreatedTime(),
                saveNoteInputData.getUsername(),
                saveNoteInputData.getNoteData());
        boolean saveSuccess = this.saveNoteDAO.saveNote(thisNote);
        boolean saveAPISuccess = this.saveNoteDAO.uploadUserFile(saveNoteInputData.getUsername(), thisNote);
        ArrayList<String> files = this.saveNoteDAO.getAllUserFiles(thisNote.getCreatedUser());
        if (saveSuccess){
            NoteOutputData noteOutputData = new NoteOutputData(thisNote.getFileName(),
                    thisNote.getFileTxt(), files, thisNote.getCreatedUser(), false);
            this.saveNotePresenter.prepareSaveNoteSuccessView(noteOutputData);
        }else {
            this.saveNotePresenter.prepareSaveNoteFailView("Save Failed");
        }
    }
}
