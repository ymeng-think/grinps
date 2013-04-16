package tw.sample.multimedia;

public class MusicLister {

    private MusicFinder finder;

    public void setFinder(MusicFinder finder) {
        this.finder = finder;
    }

    public MusicFinder getFinder() {
        return finder;
    }
}
