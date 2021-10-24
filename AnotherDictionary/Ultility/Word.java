package Ultility;
public class Word {

    private String input;
    private String output;

    public Word(){
        input = "";
        output = "";
    }

    public Word(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public Word(Word w){
        this.input = w.input;
        this.output = w.output;
    }

    public void setinput(String input){
        this.input = input;
    }

    public String getinput(){
        return input;
    }

    public void setoutput(String output){
        this.output = output;
    }

    public String getoutput(){
        return output;
    }
}
