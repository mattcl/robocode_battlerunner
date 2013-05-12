import java.util.ArrayList;

public class Match {
    int id;
    int width;
    int height;
    int numRounds;
    ArrayList<Entry> entries;

    public Match(int id, int width, int height, int numRounds) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.numRounds = numRounds;
        entries = new ArrayList<Entry>();
    }
    
    public void addEntry(Entry entry) {
        entries.add(entry);
    }
    
    public String getBotString() {
       String str = "";
       for (Entry entry : entries) {
           str += entry.properName + ",";
       }
       return str.substring(0, str.length() - 1);
    }

    @Override
    public String toString() {
        return "Match [id=" + id + ", width=" + width + ", height=" + height
                + ", entries=" + entries + "]";
    }
}
