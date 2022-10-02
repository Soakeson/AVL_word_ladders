import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.util.ArrayList;

public class LadderGame {
    public int total = 0;
    static int MaxWordSize = 15;  //Max legnth word allowed
    ArrayList<String>[] allList;  // Array of ArrayLists of words of each length.
    Random random;  // Random number generator

    /**
     *  Creates separate ArrayLists for words of each length
     * @param dictionaryFileName  Contains all words to be used in word ladder in alpha order
     */
    public LadderGame(String dictionaryFileName) {
        random = new Random();
        allList = new ArrayList[MaxWordSize];
        for (int i = 0; i < MaxWordSize; i++)
            allList[i] = new ArrayList<String>();

        try {
            Scanner reader = new Scanner(new File(dictionaryFileName));
            while (reader.hasNext()) {
                String word = reader.next();
                if (word.length()< MaxWordSize) allList[word.length()].add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Given starting and ending words, create a word ladder of minimal length.
     * @param startWord  Beginning word of word ladder
     * @param endWord  Ending word on word ladder
     */
    public void play(String startWord, String endWord, String type) {
        total = 0;
        if (startWord.length() != endWord.length()){
            System.out.println("Words are not the same length");
            return;
         }
        if (startWord.length()  >= MaxWordSize) return;
        ArrayList<String> list = allList[startWord.length()];
        ArrayList<String> wordList = (ArrayList<String>) list.clone();
        if (!wordList.contains(startWord) || !wordList.contains(endWord)) {
            System.out.println("Start word or end word is not in the dictionary");
            return;
        }
        System.out.printf("%s: Seeking a solution from " + startWord + " -> " + endWord + " Size of List " + wordList.size() + "\n", type);
        LadderInfo initLadder = new LadderInfo(startWord, endWord, 0, startWord);
        if (type == "A*") { //lets the user select which type they want to run the wordladders through A* or Brute.
            AVLTree<LadderInfo> aQueue = new AVLTree<>();
            aQueue.insert(initLadder);
            findLadderAVL(aQueue, wordList, startWord, endWord);
        } else {
            Queue<LadderInfo> bQueue = new Queue<>();
            bQueue.add(initLadder);
            findLadderQueue(bQueue, wordList, startWord, endWord);
        }
    }

    /**
     * Given a queue, wordList, starWord, and endWord recursivly travel a list of words one letter at a time
     * until either a word is found or the queue is empty.
     * @param queue A AVLTree priority queue of LadderInfo containing partial solutions
     * @param wordList List of all words that are the same length as the start and end word
     * @param startWord Beginning word of word ladder
     * @param endWord Ending word of word ladder
     *
     *
     */
    private void findLadderAVL(AVLTree<LadderInfo> queue, ArrayList<String> wordList, String startWord, String endWord) {
        total++;
        LadderInfo current = queue.deleteMin();
        if (current == null) {
            System.out.println("No path from " + startWord + " to " + endWord);
            return;
        }
        //find the differences between words and count the number of them
        for (int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i);
            int diffCount = getDiff(current.word, word);
            if (diffCount == 1) {
                LadderInfo next = new LadderInfo(word, endWord, current.moves+1, current.ladder + " " + word);
                queue.insert(next);
                wordList.remove(word);
                i--; //decrement i becuase list has gotten smaller after removing a word
                if (word.equals(endWord)) {
                    System.out.println(next + "Total Enqueus: " + total);
                    return;
                }
            }
        }
        findLadderAVL(queue, wordList, startWord, endWord);
    }

    private void findLadderQueue(Queue<LadderInfo> queue, ArrayList<String> wordList, String startWord, String endWord) {
        total++;
        LadderInfo current = queue.remove();
        if (current == null) {
            System.out.println("No path from " + startWord + " to " + endWord);
            return;
        }
        // find the differences between words and count the number of them
        for (int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i);
            int diffCount = getDiff(current.word, word);
            if (diffCount == 1) {
                LadderInfo next = new LadderInfo(word, endWord, current.moves+1, current.ladder + " " + word);
                queue.add(next);
                wordList.remove(word);
                i--; //decrement i becuase list has gotten smaller after removing a word
                if (word.equals(endWord)) {
                    System.out.println(next + " Total Enqueus: " + total);
                    return;
                }
            }
        }
        findLadderQueue(queue, wordList, startWord, endWord);
    }

    private int getDiff(String w1, String w2) {
        int diffCount = 0;
        for (int i = 0; i < w1.length(); i++) {
            if (w1.charAt(i) != w2.charAt(i)) {
                diffCount++;
            }
        }
        return diffCount;
    }


    /**
     * Find a word ladder between random words of length len
     * @param len  Length of words in desired word ladder
     */
    public void play(int len, String type) {
       if (len >= MaxWordSize) return;
        ArrayList<String> list = allList[len];
        String firstWord = list.get(random.nextInt(list.size()));
        String lastWord = list.get(random.nextInt(list.size()));
        if (type == "A*") {
            play(firstWord, lastWord, "A*");
        } else if (type == "Brute") {
            play(firstWord, lastWord, "Brute");
        } else {
            play(firstWord, lastWord, "A*");
            play(firstWord, lastWord, "Brute");
        }
        
        System.out.println();
    }

}

