package application;

import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;

public class createVideoTask extends Task<Void> {
    private Main _stage;
    private String _searchTerm;

    public createVideoTask(Main stage){
        _stage = stage;
//        _searchTerm = _stage.getSearchTerm;
    }

    @Override
    protected Void call() throws Exception {
        Thread.sleep(300);
//        try {
////            String myDirectory = "206a3_team17"; // user Folder Name
////            String users_home = System.getProperty("user.home");
////            String path = users_home.replace("\\", "/") + File.separator + myDirectory;
////
////            // search in wiki
//////            String cmd = "wikit " + _toSearch;
////            ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
////            Process process = pb.start();
//
//        } catch (IOException e) {
//
//        }
        return null;
    }
}
