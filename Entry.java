
public class Entry {
    int id;
    String properName;

    public Entry(int id, String properName) {
       this.id = id;
       this.properName = formattedProperName(properName);
    }
    
    public String formattedProperName(String original) {
        int pos = original.lastIndexOf("_");
        if (pos < 0) {
            return original;
        }
        return original.substring(0, pos) + " " + original.substring(pos + 1);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Entry [id=" + id + ", properName=" + properName + "]";
    }
}
