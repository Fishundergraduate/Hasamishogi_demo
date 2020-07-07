import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

//import jdk.nashorn.api.tree.Tree;
//import sun.tools.tree.ThrowStatement;

import java.util.ArrayList;



public class DFS {

    private static class TreeNode {
        int val = -1;
        int x_from;
        int y_from;
        int x_to;
        int y_to;
        int[][] board;
        int[] pieces;
        int treenode_depth = 0;
        TreeNode parent;
        ArrayList<TreeNode> child = new ArrayList<TreeNode>();
        TreeNode(int x) { 
            val = x; 
            treenode_depth = -1;
        }
        TreeNode(int[] pieces_tmp, int[][] board_tmp ){
            val = pieces_tmp[global_variant.Player2 - 1] - pieces_tmp[global_variant.Player1 -1];
            board = board_tmp;
            pieces = pieces_tmp;
            treenode_depth = 0;
        }
        TreeNode(int[] pieces_tmp , int x_1, int y_1, int x_2 , int y_2 , int[][] board_tmp, TreeNode treeNode){
            val = pieces_tmp[global_variant.Player2 - 1] - pieces_tmp[global_variant.Player1 -1];
            pieces = pieces_tmp;
            x_from = x_1;
            y_from = y_1;
            x_to = x_2;
            y_to = y_2;
            board = board_tmp;
            treenode_depth = treeNode.treenode_depth +1;
            parent = treeNode;
        }
        TreeNode(int x_1, int y_1, int x_2, int y_2){
            val = -1;
            x_from = x_1;
            y_from = y_1;
            x_to = x_2;
            y_to = y_2;
            treenode_depth = 1;
        }
    }

    public static int[] main(final int[] pieces) {
        final int[][] board = global_variant.board;
        TreeNode root = new TreeNode(pieces,board);
        root.parent = new TreeNode(-130);

        giveNode_Player2(root,root.board,root.pieces);

        TreeNode ans = execDfs(root);
        System.out.println(ans.treenode_depth);

        
       if (ans.treenode_depth <= 0) {
           
        Methods methods = new Methods();
        ArrayList<int[]> mypieceList = methods.searchPieces(global_variant.Player2,board);
       

        while(ans.treenode_depth <= 0){
            System.out.println("In");
            Boolean checker1 = true;
            Random random = new Random();
            while (checker1) {
                int index = random.nextInt(mypieceList.size());
                int[] departure = mypieceList.get(index);
                index = random.nextInt()%9+1;
                switch (random.nextInt()%4){
                    case 0:
                        if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0]+index, departure[1], board)) {
                            checker1 = methods.checkRange(global_variant.Player2,departure[0]+index,  departure[1], board);
                            if (!checker1) {
                                ans = new TreeNode(departure[0],departure[1],departure[0]+index, departure[1]);
                                ans.parent = new TreeNode(-1);
                                break;
                            }
                        }
                        continue;
                    case 1:
                        if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0]-index, departure[1], board)) {
                            checker1 = methods.checkRange(global_variant.Player2,departure[0]-index,  departure[1], board);
                            if (!checker1) {
                                ans = new TreeNode(departure[0],departure[1],departure[0]-index, departure[1]);
                                ans.parent = new TreeNode(-1); 
                                break;    
                            }
                        }
                        continue;
                    case 2:
                    if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0], departure[1]+index, board)) {
                        checker1 = methods.checkRange(global_variant.Player2,departure[0],  departure[1]+index, board);
                        if (!checker1) {
                            ans = new TreeNode(departure[0],departure[1],departure[0], departure[1]+index);
                            ans.parent = new TreeNode(-1);
                            break;
                        }
                    }
                    continue;

                    case 3:
                    if (methods.checkPosition(global_variant.Player2, departure[0],departure[1],departure[0], departure[1]-index, board)) {
                        checker1 = methods.checkRange(global_variant.Player2,departure[0],  departure[1]-index, board);
                        if (!checker1) {
                            ans = new TreeNode(departure[0],departure[1],departure[0], departure[1]-index);
                            ans.parent = new TreeNode(-1);
                            break;
                        }
                    }
                    continue;
                    default:                        
                        break;
                }
                
            }
                 
        } 
        }

        
        

        int[] solve = { ans.x_from,ans.y_from,ans.x_to,ans.y_to };
        return solve;
    }

    public static ArrayList<TreeNode> giveNode_Player(int num,TreeNode root, int[][] board,int[] pieces) {
        switch (num) {
            case 1:
                return giveNode_Player1(root, board, pieces);
                //break;
            case 2:
                return giveNode_Player2(root, board, pieces);
                //break;
            default:
                ArrayList<TreeNode> solve = new ArrayList<TreeNode>();
                solve.add(new TreeNode(-130));
                return solve;
                //break;
        }
    }


public  static ArrayList<TreeNode> giveNode_Player2(TreeNode root, int[][] board,int[] pieces){
    Methods methods = new Methods();
    final int[][] board_cp = board;
    int[][] tmp;
    //methods.printBoard(board);
    
    ArrayList<int[]> mypieceList = methods.searchPieces(global_variant.Player2, board);
    ArrayList<int[]> otherpieceList = methods.searchPieces(global_variant.Player1, board);
    ArrayList<TreeNode> solveList = new ArrayList<TreeNode>();
    Boolean checker1 = true;
    Boolean checker2 = true;

    //Random random = new Random();
    //int index = random.nextInt(mypieceList.size());
    for (int j = 0; j < mypieceList.size(); j++,board = board_cp) {
    int    index = j;
        
    
    for (int i = 0; i < otherpieceList.size(); i++,board = board_cp) {/* todo cheker1 ,2の挙動はどうする？ */
        if (Math.abs(mypieceList.get(index)[0] - otherpieceList.get(i)[0]) <= 1) {
            if (methods.checkPosition(global_variant.Player2, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1], board) & checker1) {
                tmp = methods.movePiece(global_variant.Player2, board, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1]);
                board = tmp;
                pieces[global_variant.Player1 -1] = methods.pieceOnBoard(global_variant.Player1, board);
                pieces[global_variant.Player2-1] = methods.pieceOnBoard(global_variant.Player2, board);
                solveList.add(new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1],board,root));
                checker1 = false;
                board = board_cp;
            }
            
        }
        if (Math.abs(mypieceList.get(index)[1] - otherpieceList.get(i)[1]) == 1  ) {
            if (methods.checkPosition(global_variant.Player2, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1], board)& checker2) {
                tmp = methods.movePiece(global_variant.Player2, board, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1]);
                board = tmp;
                //methods.printBoard(board);
                //System.out.println(pieces);
                pieces[global_variant.Player1 -1] = methods.pieceOnBoard(global_variant.Player1, board);
                pieces[global_variant.Player2-1] = methods.pieceOnBoard(global_variant.Player2, board);
                solveList.add(new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1],board,root));
                checker2 = false;
                board = board_cp;
            }
        }
        if (checker1 == false & checker2 == false) {
            break;
        }
    }
}
return solveList;
    //a
    
}
public  static ArrayList<TreeNode> giveNode_Player1(TreeNode root, int[][] board,int[] pieces){
    Methods methods = new Methods();
    
    ArrayList<int[]> mypieceList = methods.searchPieces(global_variant.Player1, board);
    ArrayList<int[]> otherpieceList = methods.searchPieces(global_variant.Player2, board);
    Boolean checker1 = true;
    Boolean checker2 = true;
    ArrayList<TreeNode> solveList = new ArrayList<TreeNode>();
    int[][] tmp;
    final int[][] board_cp = board;

    //Random random = new Random();
    //int index = random.nextInt(mypieceList.size());
    for (int j = 0; j < mypieceList.size(); j++,board = board_cp) {
        int index = j;
    
    for (int i = 0; i < otherpieceList.size(); i++,board = board_cp) {
        if (Math.abs(mypieceList.get(index)[0] - otherpieceList.get(i)[0]) <= 1) {
            if (methods.checkPosition(global_variant.Player1, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1], board) & checker1) {
                tmp = methods.movePiece(global_variant.Player1, board, mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1]);
                board = tmp;
                pieces[global_variant.Player2 -1] = methods.pieceOnBoard(global_variant.Player2, board);
                pieces[global_variant.Player1-1] = methods.pieceOnBoard(global_variant.Player1, board);
                solveList.add(new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], mypieceList.get(index)[0], otherpieceList.get(i)[1],board,root));
                checker1 = false;
                board = board_cp;
            }
            
        }
        if (Math.abs(mypieceList.get(index)[1] - otherpieceList.get(i)[1]) == 1 ) {
            if (methods.checkPosition(global_variant.Player1, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1], board) & checker2) {
                tmp = methods.movePiece(global_variant.Player1, board, mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1]);
                board = tmp;
                pieces[global_variant.Player2 -1] = methods.pieceOnBoard(global_variant.Player2, board);
                pieces[global_variant.Player1-1] = methods.pieceOnBoard(global_variant.Player1, board);
                solveList.add(new TreeNode(pieces,mypieceList.get(index)[0], mypieceList.get(index)[1], otherpieceList.get(i)[0],  mypieceList.get(index)[1],board,root));
                checker2 = false;
                board = board_cp;
            }
        }
        if (checker1 == false & checker2 == false) {
            break;
        }
    }
}
    //a
    
    return solveList;
}
//a


    static public TreeNode execDfs(TreeNode root) {
        if (root == null) {
            throw new IllegalArgumentException("Nothing to print because input tree is null.");
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode max = root;
        stack.addFirst(root);
        for (int i =0;!stack.isEmpty()|| i == 0 ;i++) {
            System.out.println("time:"+i);
            TreeNode node = stack.pop();
            if (node.val >= max.val ) {
                max = node;
            }
            node.child = giveNode_Player(node.treenode_depth%2+1, node, node.board, node.pieces);
            ArrayList<TreeNode> children = node.child;
            for (TreeNode treeNode : children) {
                stack.addFirst(treeNode);
            }
        }
        while(max.treenode_depth > 1) 
        {
            System.out.println(max.treenode_depth);
            max = max.parent;
        }
        return max;
    }
    static public TreeNode execBfs(TreeNode root) {
        if (root == null) {
            throw new IllegalArgumentException("Nothing to print because input tree is null.");
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode max = root;
        stack.addFirst(root);
        for (int i =0;!stack.isEmpty() ;i++) {
            System.out.println("time:"+i);
            TreeNode node = stack.pop();
            if (node.val >= max.val ) {
                max = node;
            }
            node.child = giveNode_Player(node.treenode_depth%2+1, node, node.board, node.pieces);
            ArrayList<TreeNode> children = node.child;
            
            for (TreeNode treeNode : children) {
                stack.addLast(treeNode);
            }
                    
                }
        while(max.treenode_depth > 1) 
        {
            System.out.println(max.treenode_depth);
            max = max.parent;
        }
        return max;
    }
}