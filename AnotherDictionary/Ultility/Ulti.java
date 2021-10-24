package Ultility;

import java.util.ArrayList;


public class Ulti {

    private ArrayList<Word> word = new ArrayList<>();


    public void addWord(Word w) {
        word.add(w);
    }
    

    public void removeWord(int i) {
        word.remove(i);
    }

    public int limitWord() {
        return word.size();
    }

    public Word indexWord(int i) {
        return word.get(i);
    }

    public void setWord(int i, Word w) {
        word.set(i, w);
    }

    public void removeWord(String input, String output) {
        for (int i = 0; i < word.size(); ++i) {
            if (input.toLowerCase().equals(word.get(i).getinput().toLowerCase()) 
            && output.toLowerCase().equals(word.get(i).getoutput().toLowerCase())) {
                word.remove(i);
                break;
            }
        }
    }

    public boolean existed(Word w) {
        for (int i = 0; i < word.size(); ++i) {
            if (w.getinput().equals(word.get(i).getinput())) {
                return true;
            }
        }
        return false;
    }
}
