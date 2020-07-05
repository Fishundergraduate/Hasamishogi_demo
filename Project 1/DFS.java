import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.ArrayList;



public class DFS {

    static public TreeNode execDfs(TreeNode root) {
        if (root == null) {
            throw new IllegalArgumentException("Nothing to print because input tree is null.");
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode max = new TreeNode(0);
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.val > max.val ) {
                max = node;
            }
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return max;
    }

    private static class TreeNode {
        int val;
        int x_from;
        int y_from;
        int x_to;
        int y_to;
        int[][] board;
        int[] pieces;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
        TreeNode(int[] pieces_tmp, int[][] board_tmp ){
            val = pieces_tmp[global_variant.Player2 - 1] - pieces_tmp[global_variant.Player1 -1];
            board = board_tmp;
            pieces = pieces_tmp;
        }
        TreeNode(int[] pieces_tmp , int x_1, int y_1, int x_2 , int y_2 , int[][] board_tmp){
            val = pieces_tmp[global_variant.Player2 - 1] - pieces_tmp[global_variant.Player1 -1];
            pieces = pieces_tmp;
            x_from = x_1;
            y_from = y_1;
            x_to = x_2;
            y_to = y_2;
            board = board_tmp;
        }
        TreeNode(int x_1, int y_1, int x_2, int y_2){
            x_from = x_1;
            y_from = y_1;
            x_to = x_2;
            y_to = y_2;
        }
    }

    public static int[] main(int[][] board, int[] pieces) {
        
        TreeNode root = new TreeNode(pieces,board);

        giveNode_Player2(root,root.board,root.pieces);

        if (root.left != null) {
            giveNode_Player1(root.left, root.left.board, root.left.pieces); 
        }

        if (root.right != null) {
            giveNode_Player1(root.right, root.right.board, root.right.pieces);
        }
        TreeNode ans = execDfs(root);

        if (root.left != null & root.right != null) {
            
        if (ans != root.left|| ans != root.right) {
            if (ans == root.left.left || ans == root.left.right) {
                ans = root.left;
            }
            if(ans == root.right.left || ans == root.right.right){
                ans = root.right;
            }
        }
        }

        do {
            Methods methods = new Methods();
            ArrayList<int[]> mypieceList = methods.searchPieces(global_variant.Player2, board);
            Boolean checker1 = true;
            Random random = new Random();
            int index = random.nextInt(mypieceList.size());
            int[] departure = mypieceList.get(index);
            for (int i = 0; i < 4&checker1; i++) {
                switch (i) {
                    case 0:
                        if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0]+1, departure[1], board)) {
                            checker1 = false;
                            ans = new TreeNode(departure[0],departure[1],departure[0]+1, departure[1]);
                        }
                        continue;
                    case 1:
                        if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0]-1, departure[1], board)) {
                            checker1 = false;
                            ans = new TreeNode(departure[0],departure[1],departure[0]-1, departure[1]);                                
                        }
                        continue;
                    case 2:
                    if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0], departure[1]+1, board)) {
                        checker1 = false;
                        ans = new TreeNode(departure[0],departure[1],departure[0], departure[1]+1);
                    }
                    continue;

                    case 3:
                    if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0], departure[1]-1, board)) {
                        checker1 = false;
                        ans = new TreeNode(departure[0],departure[1],departure[0], departure[1]-1);
                    }
                    continue;
                    default:
                        break;
                }
                
            }                
        } while (ans == null);
        int[] solve = { ans.x_from,ans.y_from,ans.x_to,ans.y_to };
        return solve;
    }


public  static void giveNode_Player2(TreeNode root, int[][] board,int[] pieces){
    Methods methods = new Methods();
    //methods.printBoard(board);
    
    ArrayList<int[]> mypieceList = methods.searchPieces(global_variant.Player2, board);
    ArrayList<int[]> otherpieceList = methods.searchPieces(global_variant.Player1, board);
    Boolean checker1 = true;
    Boolean checker2 = true;

    Random random = new Random();
    int index = random.nextInt(mypieceList.size());
    for (int i = 0; i < otherpieceList.size(); i++) {
        if (Math.abs(mypieceList.get(index)[0] - otherpieceList.get(i)[0]) <= 1) {
            if (methods.checkPosition(global_variant.Player2, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1], board) & checker1) {
                methods.movePiece(global_variant.Player2, board, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1]);
                pieces[global_variant.Player1 -1] = methods.pieceOnBoard(global_variant.Player1, board);
                pieces[global_variant.Player2-1] = methods.pieceOnBoard(global_variant.Player2, board);
                root.left = new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1],board);
                checker1 = false;
            }
            
        }
        if (Math.abs(mypieceList.get(index)[1] - otherpieceList.get(i)[1]) == 1  & checker2) {
            if (methods.checkPosition(global_variant.Player2, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1], board)) {
                methods.movePiece(global_variant.Player2, board, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1]);
                //methods.printBoard(board);
                System.out.println(pieces);
                pieces[global_variant.Player1 -1] = methods.pieceOnBoard(global_variant.Player1, board);
                pieces[global_variant.Player2-1] = methods.pieceOnBoard(global_variant.Player2, board);
                root.right = new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1],board);
                checker2 = false;
            }
        }
        if (checker1 == false & checker2 == false) {
            break;
        }
    }
    //a
    
}
public  static void giveNode_Player1(TreeNode root, int[][] board,int[] pieces){
    Methods methods = new Methods();
    
    ArrayList<int[]> mypieceList = methods.searchPieces(global_variant.Player1, board);
    ArrayList<int[]> otherpieceList = methods.searchPieces(global_variant.Player2, board);
    Boolean checker1 = true;
    Boolean checker2 = true;

    Random random = new Random();
    int index = random.nextInt(mypieceList.size());
    for (int i = 0; i < otherpieceList.size(); i++) {
        if (Math.abs(mypieceList.get(index)[0] - otherpieceList.get(i)[0]) <= 1) {
            if (methods.checkPosition(global_variant.Player1, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1], board) & checker1) {
                methods.movePiece(global_variant.Player1, board, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1]);
                pieces[global_variant.Player2 -1] = methods.pieceOnBoard(global_variant.Player2, board);
                pieces[global_variant.Player1-1] = methods.pieceOnBoard(global_variant.Player1, board);
                root.left = new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1],board);
                checker1 = false;
            }
            
        }
        if (Math.abs(mypieceList.get(index)[1] - otherpieceList.get(i)[1]) == 1  & checker2) {
            if (methods.checkPosition(global_variant.Player1, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1], board)) {
                methods.movePiece(global_variant.Player1, board, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1]);
                pieces[global_variant.Player2 -1] = methods.pieceOnBoard(global_variant.Player2, board);
                pieces[global_variant.Player1-1] = methods.pieceOnBoard(global_variant.Player1, board);
                root.right = new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1],board);
                checker2 = false;
            }
        }
        if (checker1 == false & checker2 == false) {
            break;
        }
    }
    //a
}
//a
}