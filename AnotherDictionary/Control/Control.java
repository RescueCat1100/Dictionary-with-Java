package Control;

import java.util.ArrayList;

import Ultility.Ulti;

public class Control {

    public String outputSearch (String s, Ulti dict) {
        if(s == null ) {
            return "";
        } 
        for (int i = 0; i < dict.limitWord(); i++) {
            if(dict.indexWord(i).getinput().toLowerCase().equals(s.toLowerCase())) {
                return dict.indexWord(i).getoutput();
            }
        }
        return "";
    }

    public String[] inputSearch(String s, Ulti dict) {
            
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < dict.limitWord(); i++) {
            if(dict.indexWord(i).getinput().toLowerCase().startsWith(s.toLowerCase())) {
                list.add(dict.indexWord(i).getinput());
            }
        }

        String[] out = new String[list.size()];
        out = list.toArray(out);
        return out;
    }

    public Dict trigger () {
        Dict dict = new Dict();
        dict.insert();
        return dict;
    }

}
