package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class SongLibController {

    @FXML Button add;
    @FXML Button edit;
    @FXML Button delete;
    @FXML ListView<String> songs;
    @FXML ListView<String> artists;

    private ObservableList<String> songList;
    private ObservableList<String> artistList;
    private ArrayList<String> detailedList;
    
    public void start() {

        try {
            ArrayList<String> songArray = new ArrayList<String>();
            ArrayList<String> artistArray = new ArrayList<String>();
            detailedList = getLibrary();
            for(String item: detailedList) {
                songArray.add(item.split(":")[0]);
                artistArray.add(item.split(":")[1]);
            }
            
            songList = FXCollections.observableArrayList(songArray);
            artistList = FXCollections.observableArrayList(artistArray);
            songs.setItems(songList);
            artists.setItems(artistList);
        } catch (FileNotFoundException e) {
            System.out.println("Library Data File not found.");
            System.err.println(e);
            System.exit(0);
        }
    }

    private ArrayList<String> getLibrary() throws FileNotFoundException {
        
        ArrayList<String> libraryData = new ArrayList<String>();
        Scanner libraryFile = new Scanner(new File("SongLibrary.txt"));

        while(libraryFile.hasNextLine()) {
            String line = libraryFile.nextLine();
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while(scanner.hasNext()) {
                libraryData.add(scanner.next());
            }
            scanner.close();
        }
        
        libraryFile.close();
        return libraryData;
    }

    public void addSong(ActionEvent e) {

    }

    public void editSong(ActionEvent e) {

    }

    public void deleteSong(ActionEvent e) {

    }

}
