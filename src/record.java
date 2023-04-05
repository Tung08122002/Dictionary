/**
 * Contains the word and the meaning.
 */
public class record {
    private String word;
    private String meaning;

    record(){
    }

    record(String word, String meaning)  {
        this.word = word;
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
