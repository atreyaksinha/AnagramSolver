import java.util.*;

public class AnagramSolver { 
    private List<String> orderedDict; // stores clients inputs
    private Map<String, LetterInventory> lettersInDict; // maps word -> corresponding letter

    // This constructor initializes a new AnagramSolver object that uses the given list
    // as its dictionary. This does not change the list in any way. The dictionary
    // is a assumed to be nonempty collection of nonempty sequences of letters and
    // that it contains no duplicates.
    // It “preprocess” the dictionary in the constructor to compute all of the
    // inventories in advance (once per word).
    public AnagramSolver(List<String> dictionary) {
        prepareDictionary(dictionary);
        orderedDict = dictionary;
    }

    // This method uses recursive backtracking to find combinations of words that
    // have the same letters as the given string. It prints all combinations
    // of words from the dictionary that are anagrams of text and that include at
    // most max words (or an unlimited number of words if max is 0) to System.out.
    // Throws an IllegalArgumentException if max is less than 0.
    public void print(String text, int max) {
        if (max < 0) {
            throw new IllegalArgumentException();
        }
        List<String> listOfLetters = new ArrayList<String>();
        LetterInventory sortedInventory = new LetterInventory(text);
        print(text, sortedInventory, listOfLetters);
        Stack<String> stack = new Stack<String>();
        printOut(sortedInventory, max, stack, listOfLetters);
    }

    // This method stores all combinations of words from the dictionary into and Anagram
    private void prepareDictionary(List<String> dict) {
        lettersInDict = new HashMap<String, LetterInventory>();
        for (String word : dict) {
            lettersInDict.put(word, new LetterInventory(word));
        }
    }

    // This method extracts a String list from the given letter inventory 
    // Each element in the list serves as key for Anagram Dictionary map
    private void print(String text, LetterInventory inventory, List<String> list) {
        for (String word : orderedDict) {
            if (inventory.subtract(lettersInDict.get(word)) != null) {
                list.add(word);
            }
        }
    }

    // This method prints out every combination of words from the input list present in inventory
    // Each anagram is printed on a new line which contains words from list.
    // If the letter inventory is empty, it prints "[]"
    private void printOut(LetterInventory inventory, int max, Stack<String> stack, 
                                                                            List<String> list) {
        if (inventory.isEmpty()) {
            System.out.println(stack);
        }
        if (max == 0 || max != stack.size()) {
            for (String word : list) {
                LetterInventory newInventory = inventory.subtract(lettersInDict.get(word));
                if (newInventory != null) {
                    stack.push(word);
                    printOut(newInventory, max, stack, list);
                    stack.pop();
                }
            }
        }
    }
}