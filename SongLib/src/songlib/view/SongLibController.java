//Alexander Rook
//Matt Raday

package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SongLibController {

    @FXML ListView<String> songs;
    
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
    
    @FXML Tab errorTab;
    @FXML TextArea errorText;
    
    private ObservableList<String> displayList;
    private ObservableList<String> songList;
    private ObservableList<String> artistList;
    private ObservableList<String> albumList;
    private ObservableList<String> yearList;
    
    private TreeSet<String> songTreeSet;
    
    public void start() {

        try {
            songTreeSet = getLibraryData();
            
        } catch (FileNotFoundException e) {
            songTreeSet = new TreeSet<String>();
        }

        if(songTreeSet.size() > 0) {
            setLibrary();
        } else {
            songList = FXCollections.observableArrayList();
            artistList = FXCollections.observableArrayList();
            albumList = FXCollections.observableArrayList();
            yearList = FXCollections.observableArrayList();
            displayList = FXCollections.observableArrayList();
            
            songs.setItems(displayList);
            
            detailedSong.setText("");
            detailedArtist.setText("");
            detailedAlbum.setText("");
            detailedYear.setText("");
            
            editSong.setText("");
            editArtist.setText("");
            editAlbum.setText("");
            editYear.setText("");
            deleteSong.setText("");
            deleteArtist.setText("");
            deleteAlbum.setText("");
            deleteYear.setText("");
        }

        songs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(songTreeSet.size() != 0 && !songs.getSelectionModel().isEmpty()) {
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
                } else {
                    detailedSong.setText("");
                    detailedArtist.setText("");
                    detailedAlbum.setText("");
                    detailedYear.setText("");
                    
                    editSong.setText("");
                    editArtist.setText("");
                    editAlbum.setText("");
                    editYear.setText("");
                    deleteSong.setText("");
                    deleteArtist.setText("");
                    deleteAlbum.setText("");
                    deleteYear.setText("");
                }
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
        
        for(String item: songTreeSet) {
            String[] info = item.split("\t", -1);
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
        
        return;
    }
    
    private TreeSet<String> getLibraryData() throws FileNotFoundException {
        
        TreeSet<String> libraryData = new TreeSet<String>();
        Scanner libraryFile = new Scanner(new File("SongLibrary.txt"), "UTF-16");

        while(libraryFile.hasNextLine()) {
            String line = libraryFile.nextLine();
            if(!line.equals("")) {
            	libraryData.add(line);
            }
           
        }
        
        libraryFile.close();
        return libraryData;
    }
    
    private void updateLibrary() {
        try{
            Iterator<String> iter = songTreeSet.iterator();
            String current = "";
            PrintWriter writer = new PrintWriter("SongLibrary.txt", "UTF-16");
            
            while(iter.hasNext() ) {
                current = iter.next();
                writer.println(current);
            }
            writer.close();
        } catch (IOException e) {
            tabPane.getSelectionModel().select(3);
            errorText.setText("Error updating library to file: ." + "SongLibrary.txt\n");
        }
        
        return;
    }
    
    private boolean isDuplicate(String newSong, int oldindex) {
        Iterator<String> iter = songTreeSet.iterator();
        String[] songDetails = newSong.split("\t", -1);
        String current = "";
        int counter = 0;
        
        while(iter.hasNext() ) {
            current = iter.next();
            if(counter == oldindex) {
                counter++;
                continue;
            }
            String[] info = current.split("\t", -1);
            if(info[0].equalsIgnoreCase(songDetails[0]) && info[1].equalsIgnoreCase(songDetails[1])) {
                return true;
            }
            counter++;
        }
        
        return false;
    }
    
    private void add() {     
        int index = 0;
    	if(addSong.getText().equals("") || addArtist.getText().equals("")) {
    	    tabPane.getSelectionModel().select(3);
            errorText.setText("Song Name/Artist cannot be blank.\n");
            
    		return;
    	}
    	
    	String newSong = addSong.getText() + "\t" + addArtist.getText() + "\t" + addAlbum.getText() + "\t" + addYear.getText();
    	if(isDuplicate(newSong, -1)) {
    	    tabPane.getSelectionModel().select(3);
            errorText.setText("Error adding: " + addSong.getText() + " by " + addArtist.getText() + " already in Library.\n");
    	    return;
    	}
    	
    	songTreeSet.add(newSong);
    	if(songTreeSet.size() == 1) {
    	    index = 0;
    	} else {
    	    index = songTreeSet.headSet(newSong).size();
    	}
    	
    	songList.add(index, addSong.getText());
    	artistList.add(index, addArtist.getText());
    	albumList.add(index, addAlbum.getText());
        yearList.add(index, addYear.getText());
    	displayList.add(index, (addSong.getText() + " - " + addArtist.getText()));
    	
    	songs.getSelectionModel().select(index);
    	
    	updateLibrary();
    	
    	return;
    }
    
    private void edit() {
        if(songTreeSet.size() == 0) {
            tabPane.getSelectionModel().select(3);
            errorText.setText("Error editing: There are no songs in the library. Please add song first.\n");
            return;
        }
        
        if(editSong.getText().equals("") || editArtist.getText().equals("")) {
    	    tabPane.getSelectionModel().select(3);
            errorText.setText("Song Name/Artist cannot be blank.\n");
            
    		return;
    	}
    	String oldSong = detailedSong.getText() + "\t" + detailedArtist.getText() + "\t" + detailedAlbum.getText() + "\t" + detailedYear.getText();
    	String newSong = editSong.getText() + "\t" + editArtist.getText() + "\t" + editAlbum.getText() + "\t" + editYear.getText();
    	String[] info = newSong.split("\t", -1);
    	int oldindex = songTreeSet.headSet(oldSong).size();
        
    	if(isDuplicate(newSong, oldindex)) {
    	    tabPane.getSelectionModel().select(3);
            errorText.setText("Error editing: " + editSong.getText() + " by " + editArtist.getText() + " already in Library.\n");
            return;
        }
    	
    	songTreeSet.remove(oldSong);
        songList.remove(oldindex);
        artistList.remove(oldindex);
        albumList.remove(oldindex);
        yearList.remove(oldindex);
        displayList.remove(oldindex);

    	songTreeSet.add(newSong);
    	int index = songTreeSet.headSet(newSong).size();

        songList.add(index, info[0]);
        artistList.add(index, info[1]);
        albumList.add(index, info[2]);
        yearList.add(index, info[3]);
        displayList.add(index, (info[0] + " - " + info[1]));

        
        songs.getSelectionModel().select(index);
        
        updateLibrary();
        tabPane.getSelectionModel().select(0);
        return;
    	
    }
    
    private void delete() {
        String deletedSong = deleteSong.getText() + "\t" + deleteArtist.getText() + "\t" + deleteAlbum.getText() + "\t" + deleteYear.getText();
        
        if(songTreeSet.contains(deletedSong)) {
            int index = songTreeSet.headSet(deletedSong).size();
            songs.getSelectionModel().clearSelection();
            
            songTreeSet.remove(deletedSong);
            songList.remove(index);
            artistList.remove(index);
            albumList.remove(index);
            yearList.remove(index);
            displayList.remove(index);
            
            if(index == songTreeSet.size() && index != 0) {
            	songs.getSelectionModel().select(index - 1);
            } else if(songTreeSet.size() != 0) {
            	songs.getSelectionModel().select(index);
            } else {
                detailedSong.setText("");
                detailedArtist.setText("");
                detailedAlbum.setText("");
                detailedYear.setText("");
                
                editSong.setText("");
                editArtist.setText("");
                editAlbum.setText("");
                editYear.setText("");
                deleteSong.setText("");
                deleteArtist.setText("");
                deleteAlbum.setText("");
                deleteYear.setText("");
            }
            
        } else {
            tabPane.getSelectionModel().select(3);
            errorText.setText("Error deleting: " + deleteSong.getText() + " by " + deleteArtist.getText() + " does not exist in library.\n");
        }
        
        updateLibrary();
        tabPane.getSelectionModel().select(0);
        return;
    }
    
    public void submit(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b == submitAdd) {
            add();
            
            addSong.setText("");
            addArtist.setText("");
            addAlbum.setText("");
            addYear.setText("");
        } else if(b == submitEdit) {
            edit();
            
        } else if(b == submitDelete) {
            delete();
            
            
        } else {
            tabPane.getSelectionModel().select(3);
            errorText.setText("Submit Button Error\n");
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
            tabPane.getSelectionModel().select(3);
            errorText.setText("Cancel Button Error\n");
        }
    }

}
