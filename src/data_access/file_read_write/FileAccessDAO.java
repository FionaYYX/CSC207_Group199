package data_access.file_read_write;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FileAccessDAO {
    private final File dir;
    public Logger logger;
    public static final String ROOT_DIR = "User Data"+ File.separator;
    protected FileAccessDAO(){
        logger = Logger.getLogger("FileAccessLog");
        dir = new File(ROOT_DIR);
        dir.mkdirs();
    }
    public String getFileData(String username, String filename){
        try{
            String path = ROOT_DIR+username+ File.separator + filename + ".txt";
            return new String(Files.readAllBytes(Paths.get(path)));
        }catch (Exception e){
            this.logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }
}
