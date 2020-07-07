import java.util.ArrayList;
import java.util.Scanner;

//https://khurata.hatenablog.com/entry/2019/04/04/081801
class global_variant {
    final static  int Player1 = 1;
    final static int Player2 = 2;
    static String[] yoko_number = {"１","２","３","４","５","６","７","８","９"};
    static String[] tate_number = {"一","二","三","四","五","六","七","八","九"};
    final static int depth =5;
    static int[][] board;
    
}

class Mystate extends Methods {   
    
    int[][] board =  {
        {1,1,1,1,1,1,1,1,1},
       {0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0},
       {2,2,2,2,2,2,2,2,2}             ///9*9 board
       };
    int[] pieces ={9,9}; //The number of pieces on board of each Player 1 and Player 2
    
    
}



public class MainActivity extends Mystate{
    public static void main(String[] args) {

        Mystate mystate = new Mystate();
        Scanner scan = new Scanner(System.in);
        Methods methods = new Methods();

        int nowPlayer = 0;
        int otherPlayer = 0;

        // start with player 1
        nowPlayer = global_variant.Player1;
        otherPlayer = mystate.switchPlayer(nowPlayer);
        //start game
        while (mystate.pieces[nowPlayer-1]>= 3 & mystate.pieces[otherPlayer-1]>=3) {
            switch (nowPlayer) {
                case global_variant.Player1:
                    
                    System.out.println("Player" + nowPlayer + "さんの手番です．");
                    mystate.printBoard(mystate.board);                    
                    movePiece(nowPlayer,mystate.board,scan);

                    mystate.printBoard(mystate.board);
                    mystate.pieces[nowPlayer-1] = mystate.pieceOnBoard(nowPlayer, mystate.board);
            
                    nowPlayer = mystate.switchPlayer(nowPlayer);
                    otherPlayer = mystate.switchPlayer(otherPlayer);
                    break;

                case global_variant.Player2:
                    System.out.println("Player" + nowPlayer + "さんの手番です．");
                    //mystate.printBoard(mystate.board);   
                    global_variant.board = mystate.board; 
                    int[] solve = DFS.main(mystate.pieces);
                    for (int i : solve) {
                        System.out.print(i+1+"\t");
                    }
                    System.out.println("");
                    methods.movePiece(nowPlayer,mystate.board, solve[0],solve[1] , solve[2], solve[3]) ;//movePiece(int player, int[][] board,int x_1, int y_1,int x_2 , int y_2)
    
                    //mystate.printBoard(mystate.board);
                    mystate.pieces[nowPlayer-1] = mystate.pieceOnBoard(nowPlayer, mystate.board);
                    mystate.pieces[otherPlayer-1] = mystate.pieceOnBoard(otherPlayer, mystate.board);
                    nowPlayer = mystate.switchPlayer(nowPlayer);
                    otherPlayer = mystate.switchPlayer(otherPlayer);
                    break;
            
                default:
                    try {
                        throw new Exception("nowPlayer Error! invalid range");
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                    break;
            }
        }
        //end game
        if (mystate.pieces[nowPlayer-1]<3 & mystate.pieces[otherPlayer-1]<3) {
            System.out.println("Draw... ");
        }else{
            if (mystate.pieces[global_variant.Player1-1]<3) {
                System.out.println("Winner : Player 2");
            }else{
                System.out.println("Winner : Player 1");
            }
        }
        scan.close();
    }
}
/**
 * Methods
 */ 
class Methods {    
    //show Boardstate
    void printBoard( int[][] board ){
        //show Column number 
        System.out.print("　 ");
        for (int i = 0; i < global_variant.yoko_number.length; i++) {
            System.out.print(global_variant.yoko_number[i]+" ");
        }
        System.out.print("\n");
        //show Row number and Board state
        int col = 0;
        for (int[] is : board) {
            System.out.print(global_variant.tate_number[col] + "|");
            for (int is2 : is) {
                if (is2 == 1) {
                    System.out.print("歩|");
                }
                if(is2 == 2){
                    System.out.print("と|");
                }
                if(is2 == 0){
                    System.out.print("　|");
                }
                if(is2 != 0 & is2 != 1 & is2 != 2){
                    System.out.println("ERROR!! board has invaild index");
                }
            }
            System.out.print("\n");
            col++;
        }
    }
    ArrayList<int[]> searchPieces(int player,int[][] board){
        ArrayList<int[]> stack = new ArrayList<int[]>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == player) {
                    //System.out.println(String.format("%d,%d", i,j));
                    int[] pair = {i,j};
                    stack.add(pair);                   
                }else{
                    continue;
                }
            }
        }
        return stack;
    }
    // show nuber of current pieces
    int pieceOnBoard(int player,int[][] board){
        int counter = 0;
        for (int[] is : board) {
            for (int i = 0; i < 9; i++) {
                if (is[i] == player) {
                    counter++;
                }
            }
            }
        return counter;
    }
    //show Other Player
    int switchPlayer(int player) {
        if (player == global_variant.Player1) {
            return global_variant.Player2;
        }
        if (player == global_variant.Player2) {
            return global_variant.Player1;
        } else {
            System.out.println("ERROR!! Player number is out of range.");
            return 0;
        }
    }

    // do Players Turn
    static void movePiece(int player, int[][] board,Scanner scan) {
        Methods methods = new Methods();
        int x_1 = 10, y_1 = 10; // Departure
        int x_2 = 10, y_2 = 10; // Destination
        Boolean isOK = (Boolean) false; // Checker

        // input test
        do {
            System.out.println("動かしたい駒の位置を選択してください．行列；(e.g: 1 4)");
            x_1 = scan.nextInt() ;
            y_1 = scan.nextInt()  ;
            String strtmp = scan.nextLine();
            isOK = methods.checkRange(player, x_1 - 1, y_1 - 1, board); // 0 origin
            if (!isOK) {
                System.out.println("ERROR! Pair of " + x_1 + "," + y_1 + " is invalid.");
            } else {
                // scan.close();
            }
        } while (!isOK);
        // output Test
        do {
            System.out.println("移動先を選択してください．行列；(e.g: 1 9)");
            x_2 = scan.nextInt();
            y_2 = scan.nextInt();
            String strtmp = scan.nextLine();
            isOK = methods.checkPosition(player, x_1 - 1, y_1 - 1, x_2 - 1, y_2 - 1, board); // 0 origin
            if (!isOK) {
                System.out.println("ERROR! Pair of " + x_2 + "," + y_2 + " is invalid");
            } else {
                // scan.close();
            }
        } while (!isOK);
        // moving piece
        board[x_1-1][y_1-1] = 0;
        board[x_2-1][y_2-1] = player;

        methods.deletePiece(player, x_2-1 , y_2-1, board);

    }
    //move Piece for AI
    void movePiece(int player, int[][] board,int x_1, int y_1,int x_2 , int y_2) {
        Methods methods = new Methods();
        //int x_1 = 10, y_1 = 10; // Departure
        //int x_2 = 10, y_2 = 10; // Destination
        //Boolean isOK = (Boolean) false; // Checker

        //System.out.println("Player" + player + "さんの手番です．");
        // input test
        /*
        do {
            System.out.println("動かしたい駒の位置を選択してください．行列；(e.g: 1 4)");
            x_1 = scan.nextInt() ;
            y_1 = scan.nextInt()  ;
            String strtmp = scan.nextLine();
            isOK = checkRange(player, x_1 - 1, y_1 - 1, board); // 0 origin
            if (!isOK) {
                System.out.println("ERROR! Pair of " + x_1 + "," + y_1 + " is invalid.");
            } else {
                // scan.close();
            }
        } while (!isOK);
        // output Test
        do {
            System.out.println("移動先を選択してください．行列；(e.g: 1 9)");
            x_2 = scan.nextInt();
            y_2 = scan.nextInt();
            String strtmp = scan.nextLine();
            isOK = methods.checkPosition(player, x_1 - 1, y_1 - 1, x_2 - 1, y_2 - 1, board); // 0 origin
            if (!isOK) {
                System.out.println("ERROR! Pair of " + x_2 + "," + y_2 + " is invalid");
            } else {
                // scan.close();
            }
        } while (!isOK);
        // moving piece
        */
        board[x_1][y_1] = 0;
        board[x_2][y_2] = player;

        methods.deletePiece(player, x_2 , y_2, board);

    }

    // check Position
    Boolean checkRange(int player, int x, int y, int[][] board) {
        if (x < 0 || 9 <= x || y < 0 || 9 <= y) {
            System.out.println("Out of Range");
            return false;
        }
        if (board[x][y] == player) {
            return true;
        } else {
            return false;
        }
    }

    Boolean checkPosition(int player, int x_1, int y_1, int x_2, int y_2, int[][] board) {
        if (!checkRange(player, x_1 , y_1, board)) {
            return false;
        }
        if (!checkRange(0, x_2, y_2, board)) {
            return false;
        }

        if (board[x_2][y_2] != 0) { // Destination must be blank
            System.out.println("Destination must be blank");
            return false;
        }
        if (x_1 == x_2 & y_1 == y_2) { // Destination is different from Deparutre
            System.out.println("Destination is different from Departure");
            return false;
        }
        if (x_1 != x_2 & y_1 != y_2) { // Not Allowed Diagonal movement
            System.out.println("Invalid Diagonal movement");
            return false;
        }
        if (x_1 == x_2) { // Horizontal Moving
            int upward = Math.max(y_1, y_2);
            int downward = Math.min(y_1, y_2);
            for (int i = downward + 1; i < upward; i++) {
                if (board[x_1][i] != 0) {
                    System.out.println("Invalid Horizontal Moving");
                    return false;
                }
            }
        }
        if (y_1 == y_2) {// Vartical Moving
            int upward = Math.max(x_1, x_2);
            int downward = Math.min(x_1, x_2);
            for (int i = downward + 1; i < upward; i++) {
                if (board[i][y_1] != 0) {
                    System.out.println("Invalid Vartical Moving");
                    return false;
                }
            }
        }
        return true;
    }

    void deletePiece(int player, int x, int y, int[][] board) {
        Methods methods = new Methods();
        int nowPlayer = 0;
        nowPlayer = player;
        int otherPlayer =0;
        otherPlayer = methods.switchPlayer(player);

        takeUp( nowPlayer, otherPlayer,x,y,board);
        takeDown( nowPlayer, otherPlayer, x, y, board);
        takeRight( nowPlayer, otherPlayer, x, y,board);
        takeLeft(nowPlayer, otherPlayer,x,y ,board);

    }
    //置いた駒より上を探す
    void takeUp(int nowPlayer, int otherPlayer, int x, int y, int[][] board){
        if (y == 0|| y == 1) {//端は挟めない
            return;
        }
        int i=0;
        for (i = y-1; i >=0 ; i--) {//置いた駒から上で連続探し
            if (board[x][i] != otherPlayer) {
                break;
            }
        }
        if(i == -1){
            i++;
        }
        if (i>=0 & i < y-1 & board[x][i] == nowPlayer) {//iが範囲内で(x,y)~(x,i)を挟めていたら消す
            for (i = y-1; board[x][i] == otherPlayer; i--) {
                board[x][i] =0;
            }            
        }
    }
    //置いた駒より下を探す
    void takeDown(int nowPlayer,int otherPlayer,int x, int y, int[][] board){
            if (y == 8 || y == 9) {//端は挟めない
                return;
            }
            int i = 0;
            for ( i = y+1; i <9; i++) {//置いた駒から下で連続探し
                if (board[x][i] != otherPlayer) {
                    break;
                }
            }
            if (i == 9) {
                i--;
            }
            if (i < 9 & i > y+1 & board[x][i] == nowPlayer) {//(x,y) ~ (x,i)で挿めていたら消す
                for (int j = y+1;board[x][j]== otherPlayer; j++) {
                    board[x][j] = 0;
                }
            }
        }
    //置いた駒より左を探す
    void takeLeft(int nowPlayer,int otherPlayer,int x, int y, int[][] board){
        if (x == 0 || x == 1) { //端は挟めない
            return;
        }
        int i =0;
        for (i = x-1; x>=0; i--) {//連続探し
            if (board[i][y] != otherPlayer){
                break;
            }
        }
        if (i == -1) {
            i++;
        }
        if (i>=0 & i < x-1 & board[i][y] == nowPlayer) {//(x,y)~(i,y)で挟めていたら消す
            for (int j = x-1; board[j][y] == otherPlayer; j--) {
                board[j][y] =0;
            }            
        }
    }
    void takeRight(int nowPlayer,int otherPlayer,int x,int y, int[][] board){
        if (x ==8 || x == 9) {//端は挟めない
            return;            
        }
        int i=0;
        for (i = x+1; i<9; i++) {
            if (board[i][y] != otherPlayer) {//連続探し
                break;
            }
        }
        if (i == 9) {
            i--;
        }
        if (i>=x+1 &  i < 9 & board[i][y] == nowPlayer) {//(x,y)~(i,y)で挟めていたら消す
            for (int j = x+1; board[j][y] == otherPlayer; j++) {
                board[j][y] = 0;
            }
        }
    }
}
    
