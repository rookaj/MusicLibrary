package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class SongLibController {

    @FXML ListView<String> songs;
    @FXML ListView<String> artists;
    
    @FXML TextField detailedSong;
    @FXML TextField detailedArtist;
    @FXML TextField detailedAlbum;
    @FXML TextField detailedYear;
    
    @FXML TabPane tabPane;
    
    @FXML Tab addTab;
    @FXML TextField addSong;
    @FXML TextField addArtist;
    @FXML TextField addAlbum;
    @FXML TextField addYear;
    @FXML Button submitAdd;
    @FXML Button cancelAdd;
    
    @FXML Tab editTab;
    @FXML TextField editSong;
    @FXML TextField editArtist;
    @FXML TextField editAlbum;
    @FXML TextField editYear;
    @FXML Button submitEdit;
    @FXML Button cancelEdit;
    
    @FXML Tab deleteTab;
    @FXML TextField deleteSong;
    @FXML TextField deleteArtist;
    @FXML TextField deleteAlbum;
    @FXML TextField deleteYear;
    @FXML Button submitDelete;
    @FXML Button cancelDelete;
    
    private ObservableList<String> songList;
    private ObservableList<String> artistList;
    private ObservableList<String> albumList;
    private ObservableList<String> yearList;
    
    private TreeSet<String> detailedList;
    
    public void start() {

        try {
            detailedList = getLibraryData();
            setLibrary();   
        } catch (FileNotFoundException e) {
            System.out.println("Library Data File not found.");
            System.exit(0);
        }
       /* trying to fix scrolling between listviews:
        songs.addEventFilter(javafx.scene.input.ScrollEvent.ANY, event -> { 
            System.out.println("ScrollEvent!"); });
        songs.addEventFilter(javafx.scene.control.ScrollToEvent.ANY, event -> { System.out.println("ScrollToEvent!"); });
        */
        songs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int index = songs.getSelectionModel().selectedIndexProperty().intValue();
                artists.getSelectionModel().select(index);
                detailedSong.setText(songList.get(index));
                detailedArtist.setText(artistList.get(index));
                detailedAlbum.setText(albumList.get(index));
                detailedYear.setText(yearList.get(index));
                
                editSong.setText(songList.get(index));
                editArtist.setText(artistList.get(index));
                editAlbum.setText(albumList.get(index));
                editYear.setText(yearList.get(index));
                deleteSong.setText(songList.get(index));
                deleteArtist.setText(artistList.get(index));
                deleteAlbum.setText(albumList.get(index));
                deleteYear.setText(yearList.get(index));
            }
        });
        artists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int index = artists.getSelectionModel().selectedIndexProperty().intValue();
                songs.getSelectionModel().select(index);
                detailedSong.setText(songList.get(index));
                detailedArtist.setText(artistList.get(index));
                detailedAlbum.setText(albumList.get(index));
                detailedYear.setText(yearList.get(index));
                
                editSong.setText(songList.get(index));
                editArtist.setText(artistList.get(index));
                editAlbum.setText(albumList.get(index));
                editYear.setText(yearList.get(index));
                deleteSong.setText(songList.get(index));
                deleteArtist.setText(artistList.get(index));
                deleteAlbum.setText(albumList.get(index));
                deleteYear.setText(yearList.get(index));
            }
        });

    }

    private void setLibrary() {
        int index = 0;
        ArrayList<String> songArray = new ArrayList<String>();
        ArrayList<String> artistArray = new ArrayList<String>();
        ArrayList<String> albumArray = new ArrayList<String>();
        ArrayList<String> yearArray = new ArrayList<String>();
        
        for(String item: detailedList) {
            String[] info = item.split(":");
            songArray.add(info[0]);
            artistArray.add(info[1]);
            albumArray.add(info[2]);
            yearArray.add(info[3]);
        }
        
        songList = FXCollections.observableArrayList(songArray);
        artistList = FXCollections.observableArrayList(artistArray);
        albumList = FXCollections.observableArrayList(albumArray);
        yearList = FXCollections.observableArrayList(yearArray);
        songs.setItems(songList);
        artists.setItems(artistList);
        

        
        songs.getSelectionModel().select(index);
        artists.getSelectionModel().select(index);
        detailedSong.setText(songList.get(index));
        detailedArtist.setText(artistList.get(index));
        detailedAlbum.setText(albumList.get(index));
        detailedYear.setText(yearList.get(index));
        editSong.setText(songList.get(index));
        editArtist.setText(artistList.get(index));
        editAlbum.setText(albumList.get(index));
        editYear.setText(yearList.get(index));
        deleteSong.setText(songList.get(index));
        deleteArtist.setText(artistList.get(index));
        deleteAlbum.setText(albumList.get(index));
        deleteYear.setText(yearList.get(index));
    }
    
    private TreeSet<String> getLibraryData() throws FileNotFoundException {
        
        TreeSet<String> libraryData = new TreeSet<String>();
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

    public void submit(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b == submitAdd) {
            
        } else if(b == submitEdit) {
            
        } else if(b == submitDelete) {
            
        } else {
            System.out.println("Submit Button Error");
        }
    }

    public void cancel(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b == cancelAdd) {
            
        } else if(b == cancelEdit) {
            
            int index = songs.getSelectionModel().selectedIndexProperty().intValue();
            editSong.setText(songList.get(index));
            editArtist.setText(artistList.get(index));
            editAlbum.setText(albumList.get(index));
            editYear.setText(yearList.get(index));
            tabPane.getSelectionModel().select(0);
        } else if(b == cancelDelete) {
            tabPane.getSelectionModel().select(0);
            
        } else {
            System.out.println("Cancel Button Error");
        }
    }

}
