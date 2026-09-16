package music;

import java.util.ArrayList;

import org.w3c.dom.Node;

import edu.rutgers.cs112.node.LLNode;

/**
 * This class represents a library of song playlists.
 *
 * An ArrayList of Playlist objects represents the various playlists 
 * within one's library.
 * 
 * @author Jeremy Hui
 * @author Vian Miranda
 */
public class MusicLibrary {

    private ArrayList<Playlist> allPlaylists; // contains various playlists

    /**
     * DO NOT EDIT!
     * Constructor for Library.
     * 
     * @param allPlaylists passes in ArrayList of playlists
     */
    public MusicLibrary(ArrayList<Playlist> allPlaylists) {
        this.allPlaylists = allPlaylists;
    }

    /**
     * DO NOT EDIT!
     * Default constructor for an empty library. 
     */
    public MusicLibrary() {
        this(null);
    }

    /**
     * This method reads the songs from an input csv file, and creates a 
     * playlist from it.
     * Add new songs to the end of the circular linked lists
     * Read the instructions on the website for more detail
     * 
     * @param filename the playlist information input file
     * @return a Playlist object, which contains a reference to the LAST song 
     * in the ciruclar linkedlist playlist and the size of the playlist.
     */
    public Playlist constructPlaylist(String filename) {
        // write code here

        Playlist playlist = new Playlist(); 

        StdIn.setFile(filename); 

        int numSongs = 0;

        LLNode<Song> last = null;

        

        while (!StdIn.isEmpty()) {
            String[] data = StdIn.readLine().split(",");
            String name = data[0];
            String artist = data[1];
            int year = Integer.parseInt(data[2]);
            int pop = Integer.parseInt(data[3]);
            String link = data[4];

            Song song = new Song(name, artist, year, pop, link); 

            LLNode<Song> newNode = new LLNode<Song>(song);

            if (last == null) {
                last = newNode; 
                last.setNext(last); 
            } else {
                newNode.setNext(last.getNext());
                last.setNext(newNode);
                last = newNode;
            }

            numSongs++;
        }

        //If the playlist is empty, return a Playlist object with null in the last field, and 0 as its size.
        if (last == null) {
            return playlist;
        }

        playlist.setLast(last);
        playlist.setSize(numSongs);
        

        return playlist; //update this line, it is here for compilation purposes
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you. 
     * 
     * Adds a new playlist into the song library at a certain index.
     * 
     * @param filename the playlist information input file
     * @param playlistIndex the index of the location where the playlist will 
     * be added 
     */
    public void addPlaylist(String filename, int playlistIndex) {
        
        /* DO NOT UPDATE THIS METHOD */

        if ( allPlaylists == null ) {
            allPlaylists = new ArrayList<Playlist>();
        }
        if ( playlistIndex >= allPlaylists.size() ) {
            allPlaylists.add(constructPlaylist(filename));
        } 
        else if(playlistIndex < 0){
            allPlaylists.add(0, constructPlaylist(filename));
        }
        else {
            allPlaylists.add(playlistIndex, constructPlaylist(filename));
        }        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * It takes a playlistIndex, and removes the playlist located at that index.
     * 
     * @param playlistIndex the index of the playlist to remove
     * @return true if the playlist has been deleted
     */
    public boolean removePlaylist(int playlistIndex) {
        /* DO NOT UPDATE THIS METHOD */

        if ( allPlaylists == null || playlistIndex >= allPlaylists.size() || playlistIndex < 0) {
            return false;
        }

        allPlaylists.remove(playlistIndex);
            
        return true;
    }
    
    /** 
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * Adds multiple playlist to different indices based on the provided filenames
     * 
     * @param filenames an array of the filenames of playlists that should be 
     * added to the library
     */
    public void addAllPlaylists(String[] filenames) {
        
        // do not update this method
        allPlaylists = new ArrayList<Playlist>();
        
        for ( int ii = 0; ii < filenames.length; ii++ ) {
            addPlaylist(filenames[ii], ii);
        }
    }

    /**
     * This method adds a song to a specified playlist at a given position.
     * 
     * See the assignment description for full details 
     * 
     * @param playlistIndex the index where the playlist will be added
     * @param position the position in the playlist to which the song 
     * is to be added 
     * @param newSong the song to add
     * @return true if the song can be added and therefore has been added, 
     * false otherwise. 
     */
    public boolean addSong(int playlistIndex, int position, Song newSong) {
        // write code here

        if (playlistIndex < 0 || playlistIndex >= allPlaylists.size()) {
            return false; }

        Playlist playlist = allPlaylists.get(playlistIndex);

        if (position < 1 || position > playlist.getSize() + 1) { 
            return false; }

        LLNode<Song> newNode = new LLNode<Song>(newSong);

        if (playlist.getSize() == 0) {
            newNode.setNext(newNode);
            playlist.setLast(newNode);
            playlist.setSize(1);
            return true;
        } 

        LLNode<Song> last = playlist.getLast();
        LLNode<Song> first = playlist.getLast().getNext();

        if (position == 1) {
            newNode.setNext(first);
            last.setNext(newNode);
            playlist.setSize(playlist.getSize() + 1);
            return true;
        } else if (position == playlist.getSize() + 1) {
            newNode.setNext(first);
            last.setNext(newNode);
            playlist.setLast(newNode);
            playlist.setSize(playlist.getSize() + 1);
            return true;
        } else {
            LLNode<Song> ptr = first; 

            for (int i = 1; i < position - 1; i++) {
                ptr = ptr.getNext();
            }

            newNode.setNext(ptr.getNext());
            ptr.setNext(newNode);
            playlist.setSize(playlist.getSize() + 1);
            return true;

        }
        
       //update this line, it is here for compilation purposes
    }

    /**
     * Find a song in a given playlist given its name
     * @param playlistIndex
     * @param songName
     * @return Song object of song if found, otherwise null
     */
    public Song findSong(int playlistIndex, String songName){
        // write code here

        if (playlistIndex < 0 || playlistIndex >= allPlaylists.size()) {
            return null;
        }

        Playlist playlist = allPlaylists.get(playlistIndex);

        if (playlist.getSize() == 0) {
            return null;
        }        

        LLNode<Song> last = playlist.getLast();
        LLNode<Song> ptr = last.getNext(); 
    
        do {
            if (ptr.getData().getSongName().equals(songName)) {
                return ptr.getData();
            } 
            ptr = ptr.getNext(); 
         } while (ptr != last.getNext());

         return null; 


        //update this line, it is here for compilation purposes
    }

    /**
     * This method removes a song at a specified playlist, if the song exists. 
     *
     * See the assignment description for full details 
     * 
     * @param playlistIndex the playlist index within the songLibrary where 
     * the song is to be added.
     * @param song the song to remove.
     * @return true if the song is present in the playlist and therefore has 
     * been removed, false otherwise.
     */
  public boolean deleteSong(int playlistIndex, Song song) {
        // write code here

        if (playlistIndex < 0 || playlistIndex >= allPlaylists.size()) {
            return false;
        }

        Playlist playlist = allPlaylists.get(playlistIndex);

        if (playlist.getSize() == 0) {
            return false;
        }        

        LLNode<Song> last = playlist.getLast();
        LLNode<Song> ptr = last.getNext(); 
        LLNode<Song> prev = last; 
    
        boolean found = false;

        do {
            if (ptr.getData().getSongName().equals(song.getSongName())) {
                found = true; 
                break; 
            } else {
                prev = ptr; 
                ptr = ptr.getNext();
            } 
         } while (ptr != last.getNext());

         if (!found) {
            return false;
         } 

         if (playlist.getSize() == 1) {
            playlist.setLast(null);
            playlist.setSize(0);
            return true;
         }

         prev.setNext(ptr.getNext());
         if (ptr == last) {
            playlist.setLast(prev);
         }

         playlist.setSize(playlist.getSize() - 1); 
         return true;
    }

    /**
     * This method reverses the playlist located at playlistIndex
     * 
     * Each node in the circular linked list will point to the element that 
     * came before it.
     * 
     * @param playlistIndex the playlist to reverse
     */
    public void reversePlaylist(int playlistIndex) {
        // write code here

        if (playlistIndex < 0 || playlistIndex >= allPlaylists.size()) {
            return;
        }

        Playlist playlist = allPlaylists.get(playlistIndex);

        if (playlist.getSize() <= 1) {
            return;
        }    

        LLNode<Song> oldLast = playlist.getLast();
        LLNode<Song> oldFirst = playlist.getLast().getNext();  

        LLNode<Song> ptr = oldFirst; 
        LLNode<Song> last = oldLast;

        do { 
            LLNode<Song> temp = ptr.getNext(); 
            ptr.setNext(last);
            last = ptr; 
            ptr = temp; 

        } while (ptr != oldLast);

        ptr.setNext(last);

        playlist.setLast(oldFirst);
        
    }

    /**
     * This method combines two playlists.
     * 
     * See the assignment description on the website for full details on 
     * the procedure for combining the 2 playlists 
     * 
     * @param playlistIndex1 the first playlist to merge into one playlist
     * @param playlistIndex2 the second playlist to merge into one playlist
     */
    public void combinePlaylists(int playlistIndex1, int playlistIndex2) {
        // write code here

        if (playlistIndex1 < 0 || playlistIndex1 >= allPlaylists.size()) {
            return; 
        }
        if (playlistIndex2 < 0 || playlistIndex2 >= allPlaylists.size()) {
            return;
        }

        int low = Math.min(playlistIndex1, playlistIndex2);
        int high = Math.max(playlistIndex1, playlistIndex2);

        Playlist playlist1 = allPlaylists.get(low);
        Playlist playlist2 = allPlaylists.get(high);

        LLNode<Song> last1 = playlist1.getLast();
        LLNode<Song> last2 = playlist2.getLast();  

        if (last1 == null && last2 == null) {
            removePlaylist(high);
            return;
        }

        if (last1 == null) {
            playlist1.setLast(last2);
            playlist1.setSize(playlist2.getSize());
            removePlaylist(high);
            return;
        }

        if (last2 == null) {
            removePlaylist(high);
            return;
        }

        LLNode<Song> fLast = null;
        LLNode<Song> fFront = null; 

        LLNode<Song> ptr1 = playlist1.getLast().getNext();
        LLNode<Song> ptr2 = playlist2.getLast().getNext();

        last1.setNext(null);
        last2.setNext(null);

        while (ptr1 != null && ptr2 != null) {

            LLNode<Song> current = null;

            if (ptr1.getData().getPopularity() >= ptr2.getData().getPopularity()) {
                current = ptr1; 
                ptr1 = ptr1.getNext();
            } else if (ptr1.getData().getPopularity() < ptr2.getData().getPopularity()) {
                current = ptr2; 
                ptr2 = ptr2.getNext();
            }

            if (fFront == null) {
                fFront = current;
                fLast = current; 
            } else {
                fLast.setNext(current);
                fLast = current; 
            }
        }

        while (ptr1 != null ) {
            fLast.setNext(ptr1);
            fLast = ptr1; 
            ptr1 = ptr1.getNext();
        }

        
        while (ptr2 != null ) {
            fLast.setNext(ptr2);
            fLast = ptr2; 
            ptr2 = ptr2.getNext();
        }

        fLast.setNext(fFront);

        playlist1.setLast(fLast);
        playlist1.setSize(playlist1.getSize() + playlist2.getSize());

        removePlaylist(high);
    }

    /**
     * This method shuffles a specified playlist
     * 
     * See the full procedure for shuffling on the assignment description on the website
     *    
     * @param playlistIndex the playlist to shuffle in songLibrary
     */
    public void shufflePlaylist(int playlistIndex) {
        // write code here

        if (playlistIndex < 0 || playlistIndex >= allPlaylists.size()) {
            return;
        }

        Playlist playlist = allPlaylists.get(playlistIndex);

        if (playlist.getSize() <= 1) {
            return;
        }    

        LLNode<Song> newLast = null; 
        int size = playlist.getSize();

        while (playlist.getSize() > 0 ) {    

            int position = StdRandom.uniformInt(1, playlist.getSize() + 1);

            LLNode<Song> last = playlist.getLast();
            LLNode<Song> ptr = playlist.getLast().getNext();
            LLNode<Song> prev = last; 

            if (position == 1) {
                LLNode<Song> deleted = ptr; 

                if (playlist.getSize() == 1) {
                    playlist.setLast(null);
                } else {
                    prev.setNext(ptr.getNext());
                }

                playlist.setSize(playlist.getSize() - 1);
                deleted.setNext(null);

                if (newLast == null) {
                    deleted.setNext(deleted);
                    newLast = deleted;
                } else {
                    deleted.setNext(newLast.getNext());
                    newLast.setNext(deleted);
                    newLast = deleted;
                }
                continue;
            }

            for (int i = 1; i < position; i++) {
                prev = ptr;
                ptr = ptr.getNext();
            }

            LLNode<Song> deleted = ptr;

            prev.setNext(ptr.getNext());

            if (ptr == last) {
                playlist.setLast(prev);
            }

            playlist.setSize(playlist.getSize() - 1);
            deleted.setNext(null);

            if (newLast == null) {
                deleted.setNext((deleted));
                newLast = deleted;
            } else {
                deleted.setNext(newLast.getNext()); 
                newLast.setNext(deleted);
                newLast = deleted;
                }
            }

        playlist.setLast(newLast);
        playlist.setSize(size);
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Prints playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     */
    public void printPlaylist(int playlistIndex) {
        StdOut.printf("%nPlaylist at index %d (%d song(s)):%n", playlistIndex, allPlaylists.get(playlistIndex).getSize());
        if (allPlaylists.get(playlistIndex).getLast() == null) {
            StdOut.println("EMPTY");
            return;
        }
        LLNode<Song> ptr;
        for (ptr = allPlaylists.get(playlistIndex).getLast().getNext(); ptr != allPlaylists.get(playlistIndex).getLast(); ptr = ptr.getNext() ) {
            StdOut.print(ptr.getData().toString() + " -> ");
        }
        if (ptr == allPlaylists.get(playlistIndex).getLast()) {
            StdOut.print(allPlaylists.get(playlistIndex).getLast().getData().toString() + " -> POINTS TO FRONT");
        }
        StdOut.println();
    }

    public void printLibrary() {
        if (allPlaylists.size() == 0) {
            StdOut.println("\nYour library is empty!");
        } else {
                for (int ii = 0; ii < allPlaylists.size(); ii++) {
                printPlaylist(ii);
            }
        }
    }

    /*
     * Used to get and set objects.
     * DO NOT edit.
     */
     public ArrayList<Playlist> getPlaylists() { return allPlaylists; }
     public void setPlaylists(ArrayList<Playlist> p) { allPlaylists = p; }
}