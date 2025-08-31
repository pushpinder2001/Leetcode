Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
The '.' character indicates empty cells.

 

Example 1:


Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
Explanation: The input board is shown above and the only valid solution is shown below:


 

Constraints:

board.length == 9
board[i].length == 9
board[i][j] is a digit or '.'.
It is guaranteed that the input board has only one solution.

Solution:
class Solution {
    int [] rows=new int[9],cols=new int[9],boxes=new int[9];
    List<int[]> empties=new ArrayList<>();
    public void solveSudoku(char[][] board) {
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]=='.')empties.add(new int[]{i,j});
                else place(i,j,board[i][j]-'1');
            }
        }
        backtrack(board);
    }
   void place(int r,int c,int num){int m=1<<num;rows[r]|=m;cols[c]|=m;boxes[(r/3)*3+c/3]|=m;}
void remove(int r,int c,int num){int m=~(1<<num);rows[r]&=m;cols[c]&=m;boxes[(r/3)*3+c/3]&=m;}
    int countBits(int n){int c=0;while(n>0){n&=(n-1);c++;}return c;}
    int bitPos(int mask){int p=0;while((1<<p)!=mask)p++;return p;}

    boolean backtrack(char[][]board){
        if(empties.isEmpty())return true;
        int minOpt=10,idx=1,mask=0;
        for(int k=0;k<empties.size();k++){
            int []cell =empties.get(k);int r=cell[0],c=cell[1];
            int b=(r/3)*3+c/3,used=rows[r]|cols[c]|boxes[b];
            int opt=9-countBits(used);
            if(opt<minOpt){minOpt=opt;idx=k;mask=(~used)&0x1FF;if(opt==1)break;}
        }
        int []cell=empties.remove(idx);int r=cell[0],c=cell[1];
        while(mask!=0){
            int pick=mask&-mask;int num=bitPos(pick);
            place(r,c,num);board[r][c]=(char)(num+'1');
            if(backtrack(board))return true;
            remove(r,c,num);board[r][c]='.';mask-=pick;
        }
        empties.add(idx,cell);return false;
    }
}
