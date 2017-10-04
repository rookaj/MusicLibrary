package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
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
    
    private ObservableList<String> displayList;
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

        songs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int index = songs.getSelectionModel().selectedIndexProperty().intValue();
                
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
        ArrayList<String> displayArray = new ArrayList<String>();
        
        for(String item: detailedList) {
            String[] info = item.split(":", -1);
            songArray.add(info[0]);
            artistArray.add(info[1]);
            albumArray.add(info[2]);
            yearArray.add(info[3]);
            
            displayArray.add(info[0] + " - " + info[1]);
        }
        
        songList = FXCollections.observableArrayList(songArray);
        artistList = FXCollections.observableArrayList(artistArray);
        albumList = FXCollections.observableArrayList(albumArray);
        yearList = FXCollections.observableArrayList(yearArray);
        displayList = FXCollections.observableArrayList(displayArray);
        
        songs.setItems(displayList);
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
    

    private boolean isDuplicate(String newSong) {
        Iterator<String> iter = detailedList.iterator();
        String[] songDetails = newSong.split(":", -1);
        String current = "";
        
        while(iter.hasNext() ) {
            current = iter.next();
            String[] info = current.split(":", -1);
            if(info[0].equals(songDetails[0]) || info[1].equals(songDetails[1])) {
                return true;
            }
        }
        
        return false;
    }
    
    private void add() {       
    	if(addSong.getText().equals("") || addArtist.getText().equals("")) {
    		System.out.println("Song Name/Artist cannot be blank");
    		//popup needed
    		return;
    	}
    	
    	String newSong = addSong.getText() + ":" + addArtist.getText() + ":" + addAlbum.getText() + ":" + addYear.getText();
    	if(isDuplicate(newSong)) {
    	    //popup needed
    	    System.out.println("Error adding: " + addSong.getText() + " by " + addArtist.getText() + " already in Library.");
    	    return;
    	}
    	
    	detailedList.add(newSong);
    	int index = detailedList.headSet(newSong).size();

    	songList.add(index, addSong.getText());
    	artistList.add(index, addArtist.getText());
    	albumList.add(index, addAlbum.getText());
        yearList.add(index, addYear.getText());
    	
    	songs.getSelectionModel().select(index);
    }
    
    private void edit() {
    	String oldSong = detailedSong.getText() + ":" + detailedArtist.getText() + ":" + detailedAlbum.getText() + ":" + detailedYear.getText();
    	String newSong = editSong.getText() + ":" + editArtist.getText() + ":" + editAlbum.getText() + ":" + editYear.getText();
        detailedList.remove(oldSong);
    	if(isDuplicate(newSong)) {
    	    detailedList.add(oldSong);
            //popup needed
            System.out.println("Error adding: " + addSong.getText() + " by " + addArtist.getText() + " already in Library.");
            return;
        }
    	
        int oldindex = detailedList.headSet(oldSong).size();
        songList.remove(oldindex);
        artistList.remove(oldindex);

    	detailedList.add(newSong);
    	int index = detailedList.headSet(newSong).size();

        songList.add(index, editSong.getText());
        artistList.add(index, editArtist.getText());
        albumList.add(index, editAlbum.getText());
        yearList.add(index, editYear.getText());
    	
    }
    
    private void delete() {
        String deletedSong = deleteSong.getText() + ":" + deleteArtist.getText() + ":" + deleteAlbum.getText() + ":" + deleteYear.getText();
        
        if(detailedList.contains(deletedSong)) {
            detailedList.remove(deletedSong);
        } else {
            //popup needed
            System.out.println("Error deleting: "+ deleteSong.getText() + " by " + deleteArtist.getText() + " does not exist");
        }
    }
    
    public void submit(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b == submitAdd) {
            add();
        } else if(b == submitEdit) {
            edit();
        } else if(b == submitDelete) {
            delete();
        } else {
            System.out.println("Submit Button Error");
        }
    }

    public void cancel(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b == cancelAdd) {
            addSong.setText("");
            addArtist.setText("");
            addAlbum.setText("");
            addYear.setText("");
            
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
