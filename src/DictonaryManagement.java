/**
 * Call some function of the dictionary.
 */
public class DictonaryManagement {
    public void addWord(record rs) {
        Dictonary.map.put(rs.getWord(), rs.getMeaning());
    }

    public void editWord(record rs) {
        Dictonary.map.replace(rs.getWord(), rs.getMeaning());
    }

    public void deleteWord(String word) {
        Dictonary.map.remove(word);
    }
}
