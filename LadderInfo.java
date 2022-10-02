/**
 * This class creates WordInfo objects which allows the program
 * to keep track of words, their partial ladder, and number of smoves in the word ladder.
 */
public class LadderInfo implements Comparable<LadderInfo>  {
    public String word;  //last word in the word ladder
    public String endWord; //target word
    public int moves;    //number of in the word ladder
    public String ladder;// printable representation of ladder
    public int score;

    public LadderInfo(String word, String endWord, int moves, String ladder) {
        this.word = word;
        this.endWord = endWord;
        this.moves = moves;
        this.ladder = ladder;
        this.score = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) !=  endWord.charAt(i)) {
                score++;
            }
        }
        score = score + moves;
    }

    @Override
    public int compareTo(LadderInfo l) {
        if (this.score > l.score)
            return 1;
        if (this.score < l.score)
            return -1;
        return 0;
    }

    public String toString() {
        return "Word " + word    + " Moves " +moves  + " Ladder ["+ ladder +"]";
    }

}

