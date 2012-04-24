package view;

public class SplayerViewBuilder {

    public static void build (SplayerViewManager svm) {
        // Build Main
        ViewModule main;
        svm.add(main);
        
        // Build Playlist
        ViewModule playList;
        svm.add(playlist);
        
        // Build Library
        ViewModule library;
        svm.add(library);
    }
}
