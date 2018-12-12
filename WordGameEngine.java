import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.Math;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/** Methods to a game that searches for words on a board.
 *
 *@author Blake Schilleci (wbs0013@auburn.edu)
 *@version 03-29-18
 *
 */
 
public class WordGameEngine implements WordSearchGame {

   private TreeSet<String> lexicon; 
   private String[][] gameBoard; 
   private static final int MAX_NEIGHBORS = 8;
   private int width;
   private int height;
   private boolean[][] visited; 
   private ArrayList<Integer> path; 
   private String currentWord; 
   private SortedSet<String> allWords;
   private ArrayList<Position> path2;
   private int squareRoot;

/** Constructor for Word Game.
 */
 
   public WordGameEngine() {
   
      String[][] board = new String[][]{{"E", "E", "C", "A"}, 
         {"A", "L", "E", "P"}, 
         {"H","N", "B", "O"}, 
         {"Q", "T", "T", "Y"}};
   }
   
   /** Method that loads the lexicon. 
    *@param fileName String
    */
 
   public void loadLexicon(String fileName) {
      boolean lexLoaded = false;
      
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      
      lexicon = new TreeSet<String>();
      
      try {
         Scanner s = 
            new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         
         while (s.hasNext()) {
            String str = s.next();
            lexicon.add(str.toUpperCase());
            s.nextLine();
         }
         lexLoaded = true;
      }
      catch (Exception e) {
         throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e);
      }
   }
   
   /** Method that sets the board. 
    *@param letterArray String[]
    */
   
   public void setBoard(String[] letterArray) {
   
      if (letterArray == null) {
         
         throw new IllegalArgumentException();
      
      }
      
      if ((letterArray.length % (int)Math.sqrt(letterArray.length)) != 0) {
         
         throw new IllegalArgumentException();
         
      }
      
      
      try {
      
         int size = letterArray.length;
         squareRoot = (int)Math.sqrt(size);
         gameBoard = new String[squareRoot][squareRoot];
         int i = 0;
         int j = 0;
      
         for (String s : letterArray) {
            if (j == squareRoot) {
               i++;
               j = 0;
            }
            gameBoard[i][j] = s;
            j++;
         }
         height = squareRoot;
         width = squareRoot;
      }
      
      catch (Exception e) {
         throw new IllegalArgumentException();
      }
      
      
   } 
   
   /** Creates a String representation of the board, 
    *suitable for printing to standard output.
    *@return String
    */
   
   public String getBoard() {
   
      String board = "";
      for (int i = 0; i < height; i ++) {
         if (i > 0) {
            board += "\n";
         }
         for (int j = 0; j < width; j++) {
            board += gameBoard[i][j] + " ";
         }
      }
      return board;
   }
      
   
   /** Finds all valid words within the game board.
    *@param minimumWordLength int
    *@return SortedSet<>
    */
   
   
   public SortedSet<String> getAllValidWords(int minimumWordLength) {
      
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      path2 = new ArrayList<Position>();
      allWords = new TreeSet<String>();
      currentWord = "";
      
      for (int i = 0; i < height; i++) {
         
         for (int j = 0; j < width; j ++) {
            currentWord = gameBoard[i][j];
            
            if (isValidWord(currentWord) && currentWord.length() >= minimumWordLength) {
               allWords.add(currentWord);
            }
            
            if (isValidPrefix(currentWord)) {
               Position temp = new Position(i,j);
               path2.add(temp);
               depthFirstSearch(i, j, minimumWordLength); 
            
               path2.remove(temp);
            }
         }
      }
      return allWords;
   }
   
   private void depthFirstSearch(int x, int y, int min) {
      
      Position start = new Position(x, y);
      markAllUnvisited(); 
      markPathVisited(); 
     
      for (Position p : start.neighbors()) {
        
         if (!isVisited(p)) {
            visit(p);
            
            if (isValidPrefix(currentWord + gameBoard[p.x][p.y])) {
               currentWord += gameBoard[p.x][p.y];
               path2.add(p);
              
               if (isValidWord(currentWord) && currentWord.length() >= min) {
                  allWords.add(currentWord);
               }
               depthFirstSearch(p.x, p.y, min);
               
               path2.remove(p);
              
               int endIndex = currentWord.length() - gameBoard[p.x][p.y].length();
               
               currentWord = currentWord.substring(0, endIndex);
            }
         }
      }
      
      markAllUnvisited(); 
      
      markPathVisited(); 
   }      
  
  /** Method calculates the score for each word found.
   *@param words SortedSet<>
   *@param minimumWordLength int
   *@return int
   */
   
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
   
      int score = 0;
      Iterator<String> itr = words.iterator();
      
      while (itr.hasNext()) {
         
         String word = itr.next();
      
         if (word.length() >= minimumWordLength && isValidWord(word)
             && !isOnBoard(word).isEmpty()) {
         
            score += (word.length() - minimumWordLength) + 1;
         }
      }
      return score;      
   }
   
   /** Checks if the word is valid and within the lexicon. 
    *@param wordToCheck String
    *@return boolean
    */
   
   public boolean isValidWord(String wordToCheck) {
   
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      wordToCheck = wordToCheck.toUpperCase();
      return lexicon.contains(wordToCheck);
   }
   
   /** Check to see if the prefix of a word is within the lexicon.
    *@param prefixToCheck String
    *@return boolean 
    */
    
   public boolean isValidPrefix(String prefixToCheck) {
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      if (prefixToCheck == null) {
         
         throw new IllegalArgumentException();
      }
      
      prefixToCheck = prefixToCheck.toUpperCase();
      String word = lexicon.ceiling(prefixToCheck);
      
      if (word != null) {
         
         return word.startsWith(prefixToCheck);
      }
      
      return false;
   }
   
   /** Checks to see if the word is actually on the board.
    *@param wordToCheck String
    *@return List<>
    */ 
    
   public List<Integer> isOnBoard(String wordToCheck) {
      
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      path2 = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      currentWord = "";
      path = new ArrayList<Integer>();
      
      for (int i = 0; i < height; i++) {
         
         for (int j = 0; j < width; j ++) {
         
            if (wordToCheck.equals(gameBoard[i][j])) {
               path.add(i * width + j); 
               return path;
            }
            
            if (wordToCheck.startsWith(gameBoard[i][j])) {
               Position pos = new Position(i, j);
               path2.add(pos); 
               currentWord = gameBoard[i][j]; 
               depthFirstSearch2(i, j, wordToCheck); 
               
               if (!wordToCheck.equals(currentWord)) {
                  path2.remove(pos);
               }
               else {
               
                  for (Position p: path2) {
                     path.add((p.x * width) + p.y);
                  } 
                  return path;
               }
            }
         }
      }
      return path;
   }
   
  
   private void depthFirstSearch2(int x, int y, String wordToCheck) {
      
      Position start = new Position(x, y);
      markAllUnvisited(); 
      markPathVisited(); 
     
      for (Position p: start.neighbors()) {
         
         if (!isVisited(p)) {
            visit(p);
            
            if (wordToCheck.startsWith(currentWord + gameBoard[p.x][p.y])) {
               currentWord += gameBoard[p.x][p.y]; 
               path2.add(p);
               depthFirstSearch2(p.x, p.y, wordToCheck);
               
               if (wordToCheck.equals(currentWord)) {
                  return;
               }
               
               else {
                  path2.remove(p);
               
                  int endIndex = currentWord.length() - gameBoard[p.x][p.y].length();
                  currentWord = currentWord.substring(0, endIndex);
               }
            }
         }
      }
      markAllUnvisited(); 
      markPathVisited(); 
   }
   
   private void markAllUnvisited() {
      visited = new boolean[width][height];
      for (boolean[] row : visited) {
         Arrays.fill(row, false);
      }
   }
   
   private void markPathVisited() {
      for (int i = 0; i < path2.size(); i ++) {
         visit(path2.get(i));
      }
   }

   
   private class Position {
      int x;
      int y;
   
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      @Override
      public String toString() {
         return "(" + x + ", " + y + ")";
      }
   
      public Position[] neighbors() {
         
         Position[] neighbors = new Position[MAX_NEIGHBORS];
         int count = 0;
         Position p;
        
         for (int i = -1; i <= 1; i++) {
            
            for (int j = -1; j <= 1; j++) {
               
               if (!((i == 0) && (j == 0))) {
                  p = new Position(x + i, y + j);
                  
                  if (isValid(p)) {
                     neighbors[count++] = p;
                  }
               }
            }
         }
         return Arrays.copyOf(neighbors, count);
      }
   }

   
   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < width) && (p.y >= 0) && (p.y < height);
   }

   
   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }

   
   private void visit(Position p) {
      visited[p.x][p.y] = true;
   }
   
  
   
   
}