package intrepid.weardemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import echonest.v4.Artist;
import echonest.v4.Song;
import intrepid.weardemo.utils.CustomUtils;
import spotify.models.SpotifyTrack;

public class CustomQueue {

    public CustomQueue() {
    }

    public static CustomQueue createQueue() {
        return new CustomQueue();
    }

    private List<SpotifyTrack> tracks = new ArrayList<SpotifyTrack>();
    private HashMap<String, Boolean> uriMap = new HashMap<String, Boolean>();
    private HashMap<String, Integer> artistCount = new HashMap<String, Integer>();
    private List<Song> currentENSongChoices = new ArrayList<Song>();
    private List<Artist> artistList = new ArrayList<Artist>();

    private SpotifyTrack currentTrack;

    int songChoiceIndex = 0;
    int artistChoiceIndex = 0;
    int currentQueueSize = 0;

    public void addTrack(SpotifyTrack track) {
        tracks.add(track);
    }

    public void incrementSongChoiceIndex() {
        songChoiceIndex++;
    }

    public void resetSongChoiceIndex() {
        songChoiceIndex = 0;
    }

    public void incrementArtistChoiceIndex() {
        artistChoiceIndex++;
    }

    public void resetArtistChoiceIndex() {
        artistChoiceIndex = 0;
    }

    public String getCurrentTrackUri() {
        return currentTrack.getUri();
    }

    public void incrementCurrentSize() {
        currentQueueSize++;
    }

    public void resetQueueSize() {
        currentQueueSize = 0;
    }

    public Song getCurrentlyIndexedSong() {
        return currentENSongChoices.get(songChoiceIndex);
    }

    public String getCurrentArtistName() {
        return currentTrack.getArtists().get(0).getName();
    }

    public boolean currentTrackIsLive() {
        return currentTrack.getAlbum().getName().toLowerCase().contains("live");
    }

    public boolean currentTrackExistsInQueue() {
        return uriMap.containsKey(currentTrack.getUri());
    }

    public void addCurrentTrackUriToList() {
        uriMap.put(getCurrentTrackUri(), true);
    }

    public int getSize() {
        return tracks.size();
    }

    public void updateArtistCount(String artistName) {
        String strippedName = CustomUtils.stripNonLetters(artistName);
        if (artistCount.containsKey(strippedName)) {
            Integer count = artistCount.get(strippedName);
            count++;
            artistCount.put(strippedName, count);
        } else {
            artistCount.put(strippedName, 1);
        }
    }

    public Integer getArtistCount(String artistName) {
        String strippedName = CustomUtils.stripNonLetters(artistName);
        boolean contains = artistCount.containsKey(strippedName);
        Integer count = artistCount.get(strippedName);
        return !contains ? 0 : count;
    }

    public boolean currentTrackFromCompilation() {
        return currentTrack.getArtists().get(0).getName().contains("Various Artists");
    }

    public boolean notAvailableInUS() {
        return !currentTrack.getMarkets().contains("US");
    }

    public List<SpotifyTrack> getTracks() {
        return tracks;
    }

    public HashMap<String, Boolean> getUriMap() {
        return uriMap;
    }

    public HashMap<String, Integer> getArtistCount() {
        return artistCount;
    }

    public SpotifyTrack getCurrentTrack() {
        return currentTrack;
    }

    public List<Song> getCurrentENSongChoices() {
        return currentENSongChoices;
    }

    public int getSongChoiceIndex() {
        return songChoiceIndex;
    }

    public int getArtistChoiceIndex() {
        return artistChoiceIndex;
    }

    public int getCurrentQueueSize() {
        return currentQueueSize;
    }

    public void setCurrentTrack(SpotifyTrack currentTrack) {
        this.currentTrack = currentTrack;
    }

    public void setCurrentENSongChoices(List<Song> currentENSongChoices) {
        this.currentENSongChoices = currentENSongChoices;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }
}
