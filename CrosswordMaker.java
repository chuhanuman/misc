//TODO: Make duplicate words work
//TODO: Build crossword with most connections
//TODO: Allow user to input answers
import java.util.Scanner;
public class CrosswordMaker {
	public static String[] totalWordsAdded = {};
	public static int[][] totalWordsPos = {};
	public static String[] totalHints = {};
	public static char spacer = ' ';
	public static char hidden = '0';
  public static void main(String[] args) {
  	Scanner sc = new Scanner(System.in);
  	System.out.print("Enter title: ");
  	String title = sc.nextLine();
  	System.out.println("Enter a word and hint in this format:word~hint");
  	System.out.println("You can keep entering new words each line.");
  	System.out.println("If you want to stop just hit enter with nothing in the text box.");
  	String[] words = {};
    String[] hints = {};
  	while (true) {
  		String input = sc.nextLine();
  		if (input.isEmpty()) {
  			break;
  		} else {
  			int index = input.indexOf('~');
  			if (index == -1) {
  				System.out.println("Please use the format in these parentheses (word~hint).");
  				continue;
  			}
  			words = append(words,input.substring(0,index));
  			hints = append(hints,input.substring(index+1));
  		}
  	}
    sc.close();
    char[][] board = createBoard(words,hints);
    System.out.println(title);
    displayHiddenBoard(board);
    sortTotals();
    displayHints();
  }
  public static char[][] createBoard(String[] words, String[] hints) {
    int len = 0;
    int start = totalWordsPos.length;
    for (int i=1;i<words.length;i++) {
      len += words[i].length();
    }
    len = Math.max(1,len);
    char[][] board = new char[len*2+1][len*2+words[0].length()];
    for (int i=0;i<len*2+1;i++) {
      for (int j=0;j<len*2+words[0].length();j++) {
        board[i][j] = spacer;
      }
    }
    for (int i=0;i<words[0].length();i++) {
      board[len][len+i] = words[0].charAt(i);
    }
    String[] wordsAdded = {words[0]};
    totalWordsAdded = append(totalWordsAdded,wordsAdded[0]);
    totalHints = append(totalHints,hints[0]);
	  int[][] wordsPos = {{len,len,1}};
	  totalWordsPos = append(totalWordsPos,wordsPos[0]);
    boolean added;
    while (wordsAdded.length < words.length) {
    	added = false;
      for (int i=0;i<words.length;i++) {
    	  if (!contains(wordsAdded,words[i])) {
    		  for (int j=0;j<wordsAdded.length;j++) {
    			  int[][] connections = findConnections(wordsAdded[j],words[i]);
    			  if (connections.length > 0) {
    			  	for (int[] connection:connections) {
    			  		char[][] newboard = addToBoard(board,words[i],wordsAdded[j],connection,wordsPos[j]);
    			  		if (!newboard.equals(board)) {
    			  			board = newboard;
    			  			wordsAdded = append(wordsAdded,words[i]);
    			  			totalWordsAdded = append(totalWordsAdded,words[i]);
    			  			totalHints = append(totalHints,hints[i]);
    			  			int[] tempPos = {0,0,0};
    			  			if (wordsPos[j][2] == 0) {
    			  				tempPos[0] = wordsPos[j][0]-connection[1];
    			  				tempPos[1] = wordsPos[j][1]+connection[0];
    			  				tempPos[2] = 1;
    			  			} else if (wordsPos[j][2] == 1) {
    			  				tempPos[0] = wordsPos[j][0]+connection[0];
    			  				tempPos[1] = wordsPos[j][1]-connection[1];
    			  				tempPos[2] = 0;
    			  			}
    			  			added = true;
    			  			wordsPos = append(wordsPos,tempPos);
    			  			totalWordsPos = append(totalWordsPos,tempPos);
  	    			  	break;
    			  		}
    			  	}
    			  }
    			  if (added) {
      		  	break;
      		  }
    		  }
    		  if (added) {
    		  	break;
    		  }
    	  }
      }
      if (!added) {
      	String[] unusedWords = new String[words.length-wordsAdded.length];
      	String[] unusedHints = new String[unusedWords.length];
      	int cur = 0;
      	for (int i=0;i<words.length;i++) {
      		if (!contains(wordsAdded,words[i])) {
      			unusedWords[cur] = words[i];
      			unusedHints[cur] = hints[i];
      			cur++;
      		}
      	}
      	board = cleanUp(board,start,start+wordsAdded.length);
      	char[][] board2 = createBoard(unusedWords,unusedHints);
      	char[][] newboard = new char[board.length+board2.length-1][];
      	int rowLen = Math.max(board[0].length,board2[0].length);
      	for (int i=start+wordsPos.length;i<totalWordsPos.length;i++) {
      		totalWordsPos[i][1] += board.length-1;
      	}
      	for (int i=0;i<board.length-1;i++) {
      		newboard[i] = rightPad(board[i],rowLen); 
      	}
      	for (int i=0;i<board2.length;i++) {
      		newboard[board.length-1+i] = rightPad(board2[i],rowLen); 
      	}
      	board = newboard;
	      return newboard;
      }
    }
    return cleanUp(board,start,start+wordsAdded.length);
  }
  public static char[][] cleanUp(char[][] board, int start, int end) {
  	int topRow,bottomRow,leftCol,rightCol;
  	topRow = bottomRow = leftCol = rightCol = 0;
  	boolean allEmpty;
  	for (int i=0;i<board.length;i++) {
  		allEmpty = true;
    	for (int j=0;j<board[i].length;j++) {
    		if (board[i][j] != spacer) {
    			allEmpty = false;
    			break;
    		}
    	}
    	if (!allEmpty) {
    		topRow = i;
    		break;
    	}
    }
  	for (int i=board.length-1;i>=topRow;i--) {
  		allEmpty = true;
    	for (int j=0;j<board[i].length;j++) {
    		if (board[i][j] != spacer) {
    			allEmpty = false;
    			break;
    		}
    	}
    	if (!allEmpty) {
    		bottomRow = i;
    		break;
    	}
    }
  	for (int i=0;i<board[0].length;i++) {
  		allEmpty = true;
    	for (int j=0;j<board.length;j++) {
    		if (board[j][i] != spacer) {
    			allEmpty = false;
    			break;
    		}
    	}
    	if (!allEmpty) {
    		leftCol = i;
    		break;
    	}
    }
  	for (int i=board[0].length-1;i>=leftCol;i--) {
  		allEmpty = true;
    	for (int j=0;j<board.length;j++) {
    		if (board[j][i] != spacer) {
    			allEmpty = false;
    			break;
    		}
    	}
    	if (!allEmpty) {
    		rightCol = i;
    		break;
    	}
    }
  	for (int i=0;i<topRow-1;i++) {
    	board = removeRow(board,0);
    }
  	int colLen = board.length;
  	for (int i=0;i<colLen-bottomRow+(topRow-1)-2;i++) {
    	board = removeRow(board,board.length-1);
    }
  	for (int i=0;i<leftCol-1;i++) {
    	board = removeCol(board,0);
    }
  	int rowLen = board[0].length;
  	for (int i=0;i<rowLen-rightCol+(leftCol-1)-2;i++) {
    	board = removeCol(board,board[0].length-1);
    }
  	for (int i=start;i<end;i++) {
  		totalWordsPos[i][0] -= leftCol-1;
  		totalWordsPos[i][1] -= topRow-1;
  	}
  	return board;
  }
  public static char[][] addToBoard(char[][] board, String word, String word2, int[] connection, int[] pos) {
  	char[][] tempboard = new char[board.length][];
  	for(int k=0;k<board.length;k++)
  	    tempboard[k] = board[k].clone();
  	int x=0,y=0;
  	for (int i=0;i<word.length();i++) {
  		boolean noadjacent = true;
  		if (pos[2] == 0) {
  			x = pos[0]-connection[1]+i;
  			y = pos[1]+connection[0];
  			if (board[y][x] != word.charAt(i)) {
  				if (board.length > y + 1 && board[y+1][x] != spacer) {
  					noadjacent = false;
  				}
  				if (y > 0  && board[y-1][x] != spacer) {
  					noadjacent = false;
  				}
  			}
  			if (i == 0 && x > 0 && board[y][x-1] != spacer) {
  				noadjacent = false;
  			}
  			if (i == word.length()-1 && x + 1 < board[y].length && board[y][x+1] != spacer) {
  				noadjacent = false;
  			}
  		} else if (pos[2] == 1) {
  			x = pos[0]+connection[0];
  			y = pos[1]-connection[1]+i;
  			if (board[y][x] != word.charAt(i)) {
  				if (board[y].length > x + 1 && board[y][x+1] != spacer) {
  					noadjacent = false;
  				}
  				if (x > 0  && board[y][x-1] != spacer) {
  					noadjacent = false;
  				}
  			}
  			if (i == 0 && y > 0 && board[y-1][x] != spacer) {
  				noadjacent = false;
  			}
  			if (i == word.length()-1 && y + 1 < board.length && board[y+1][x] != spacer) {
  				noadjacent = false;
  			}
  		}
  		boolean empty = board[y][x] == spacer || board[y][x] == word.charAt(i);
  		if (empty && noadjacent) {
  			tempboard[y][x] = word.charAt(i);
  		} else {
  			return board;
  		}
  	}
  	return tempboard;
  }
  public static void sortTotals() {
  	boolean sorted = true;
  	for (int i=1;i<totalWordsPos.length;i++) {
  		if (comparePos(totalWordsPos[i-1],totalWordsPos[i]) < 0) {
  			int[] tempPos = totalWordsPos[i-1];
  			String tempString = totalWordsAdded[i-1];
  			String tempHint = totalHints[i-1];
  			totalWordsPos[i-1] = totalWordsPos[i];
  			totalWordsAdded[i-1] = totalWordsAdded[i];
  			totalHints[i-1] = totalHints[i];
  			totalWordsPos[i] = tempPos;
  			totalWordsAdded[i] = tempString;
  			totalHints[i] = tempHint;
  			sorted = false;
  		}
  	}
  	if (!sorted) {
  		sortTotals();
  	}
  }
  public static int comparePos(int[] pos1, int[] pos2) {
  	if (pos1.equals(pos2)) {
  		return 0;
  	} else if (pos1[2] == pos2[2]) {
  		if (pos1[2] == 1) {
  			if (pos1[1] == pos2[1]) {
  				return pos2[0] - pos1[0];
  			} else {
  				return pos2[1] - pos1[1];
  			}
  		} else {
  			if (pos1[0] == pos2[0]) {
  				return pos2[1] - pos1[1];
  			} else {
  				return pos2[0] - pos1[0];
  			}
  		}
  	} else {
  		return pos2[2] - pos1[2];
  	}
  }
  public static void displayBoard(char[][] board) {
  	for (int i=0;i<board.length;i++) {
      for (int j=0;j<board[0].length;j++) {
        System.out.print(board[i][j]);
      }
      System.out.println();
    }
  }
  public static void displayHiddenBoard(char[][] board) {
  	for (int i=0;i<board.length;i++) {
      for (int j=0;j<board[0].length;j++) {
      	if (board[i][j] == spacer) {
      		System.out.print(board[i][j]);
      	} else {
      		System.out.print(hidden);
      	}
      }
      System.out.println();
    }
  }
  public static void displayHints() {
  	int cur = 1;
  	String category = "Down";
  	for (int i=0;i<totalWordsPos.length;i++) {
  		if (category == "Down" && totalWordsPos[i][2] == 1) {
  			cur = 1;
  			category = "Across";
  		}
  		System.out.println(cur + " " + category + " : " + totalHints[i]);
  		cur++;
  	}
  }
  public static int[][] findConnections(String word1, String word2) {
    int numConnections = 0;
    int cur = 0;
    for (int i=0;i<word1.length();i++) {
      cur = 0;
      while (word2.substring(cur).indexOf(word1.charAt(i)) >= 0) {
        numConnections++;
        cur += word2.substring(cur).indexOf(word1.charAt(i)) + 1;
      }
    }
    int[][] connections = new int[numConnections][2];
    int cur2 = 0;
    for (int i=0;i<word1.length();i++) {
      cur = 0;
      while (word2.substring(cur).indexOf(word1.charAt(i)) >= 0) {
        cur += word2.substring(cur).indexOf(word1.charAt(i)) + 1;
        connections[cur2][0] = i;
        connections[cur2][1] = cur - 1;
        cur2++;
      }
    }
    return connections;
  }
  public static boolean contains(String[] arr, String str) {
	  for (String str2:arr) {
		  if (str.equals(str2)) {
			  return true;
		  }
	  }
	  return false;
  }
  public static String[] append(String[] str, String element) {
  	String[] newstr = new String[str.length+1];
  	for (int i=0;i<str.length;i++) {
  		newstr[i] = str[i];
  	}
  	newstr[str.length] = element;
  	return newstr;
  }
  public static int[][] append(int[][] arr, int[] element) {
  	int[][] newarr = new int[arr.length+1][];
  	for (int i=0;i<arr.length;i++) {
  		newarr[i] = arr[i];
  	}
  	newarr[arr.length] = element;
  	return newarr;
  }
  public static char[][] removeRow(char[][] arr, int index) {
  	char[][] newarr = new char[arr.length-1][];
  	for (int i=0;i<arr.length;i++) {
  		if (i < index) {
  			newarr[i] = arr[i];
  		} else if (i > index) {
  			newarr[i-1] = arr[i];
  		}
  	}
  	return newarr;
  }
  public static char[][] removeCol(char[][] arr, int index) {
  	char[][] newarr = new char[arr.length][];
  	for (int i=0;i<arr.length;i++) {
  		char[] newrow = new char[arr[i].length-1];
  		for (int j=0;j<arr[i].length;j++) {
  			if (j < index) {
    			newrow[j] = arr[i][j];
    		} else if (j > index) {
    			newrow[j-1] = arr[i][j];
    		}
  		}
  		newarr[i] = newrow;
  	}
  	return newarr;
  }
  public static char[] rightPad(char[] arr, int len) {
  	char[] newarr = new char[len];
  	for (int i=0;i<len;i++) {
  		if (i < arr.length) {
  			newarr[i] = arr[i];
  		} else {
  			newarr[i] = spacer;
  		}
  	}
  	return newarr;
  }
}