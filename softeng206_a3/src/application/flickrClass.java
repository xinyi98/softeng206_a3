package application;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

import com.flickr4java.flickr.*;
import com.flickr4java.flickr.photos.*;
import javafx.concurrent.Task;

public class flickrClass extends Task<Void> {
    private static int _num;
    private static String _searchTerm;


    public flickrClass(String searchTerm, int num){
        _searchTerm = searchTerm;
        _num = num;
    }

    private String _storage;

    public String getAPIKey(String key) throws Exception {
        // TODO fix the following based on where you will have your config file stored

//        String myDirectory = "206a3_team17"; // user Folder Name
//        String users_home = System.getProperty("user.home");
//        String temp = users_home.replace("\\", "/") + File.separator + myDirectory;
//        String config = temp + "/flickr-api-keys.txt";

        String config = System.getProperty("user.dir")
                + System.getProperty("file.separator")+ "flickr-api-keys.txt";

//		String config = System.getProperty("user.home")
//				+ System.getProperty("file.separator")+ "bin"
//				+ System.getProperty("file.separator")+ "flickr-api-keys.txt";

        File file = new File(config);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ( (line = br.readLine()) != null ) {
            if (line.trim().startsWith(key)) {
                br.close();
                return line.substring(line.indexOf("=")+1).trim();
            }
        }
        br.close();
        throw new RuntimeException("Couldn't find " + key +" in config file "+file.getName());
    }

    public Void call() {
        try {
            String apiKey = getAPIKey("apiKey");
            String sharedSecret = getAPIKey("sharedSecret");

            Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());

            String query = _searchTerm;
//            int resultsPerPage = 5;
            int page = 0;

            PhotosInterface photos = flickr.getPhotosInterface();
            SearchParameters params = new SearchParameters();
            params.setSort(SearchParameters.RELEVANCE);
            params.setMedia("photos");
            params.setText(query);

            PhotoList<Photo> results = photos.search(params, _num, page);
            System.out.println("Retrieving " + results.size()+ " results");

            for (Photo photo: results) {
                try {
                    String myDirectory = "206a3_team17"; // user Folder Name
                    String users_home = System.getProperty("user.home");
                    String temp = users_home.replace("\\", "/") + File.separator + myDirectory;
//                    String folderLoc = temp + "/" + _searchTerm;
                    String folderLoc = temp + "/" + "dog";

//                    String cmd = "mkdir " + _searchTerm;
                    String cmd = "mkdir " + folderLoc;
                    ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
                    Process process = builder.start();

                    BufferedImage image = photos.getImage(photo,Size.LARGE);
                    String filename = query.trim().replace(' ', '-')+"-"+System.currentTimeMillis()+"-"+photo.getId()+".jpg";
                    File outputfile = new File(folderLoc, filename);
                    ImageIO.write(image, "jpg", outputfile);
                    System.out.println("Downloaded "+filename);
                } catch (FlickrException fe) {
                    System.err.println("Ignoring image " +photo.getId() +": "+ fe.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\nDone");
        return null;
    }

}
