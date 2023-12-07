package data_access.API;

import java.util.logging.Logger;
/**
 * Abstract class API is the parent class for all Dropbox API endpoints.
 */
public abstract class DropBoxAPI {
    /**
     * String containing initial path for all http requestBody used in Http request.
     */
    private String requestBody;
    /**
     * Logger logs messages for all Exceptions in Dropbox API calls.
     */
    private Logger logger;
    /**
     * String containing the access token for all Dropbox API calls.
     */
    public static String APIToken = "Bearer sl.BrSslDxRCfI2inLkiqYe5LVyhct3nlTlrZM6V2vLHZ8rGJeHrwUe2eFAl-qa5cydcw9AufGJcPOWs-4o8hwfOASALYsHoO-1Fgesr07Zb7QTm872hbWx_OfqLydcHfMZ1fmJAPJYICTSHyYZ0eF-vJo";
    /**
     * Class to initialize request body and logger for use in all API subclasses.
     */
    protected DropBoxAPI(){
        requestBody = "{\"path\": \"/NoDraw_folder/";
        logger = Logger.getLogger("APIConnectionLog");
    }
    public Logger getLogger() {
        return logger;
    }
    public String getRequestBody() {
        return requestBody;
    }
}
