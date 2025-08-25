Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.

 

Example 1:


Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,4,7,5,3,6,8,9]
Example 2:

Input: mat = [[1,2],[3,4]]
Output: [1,2,3,4]
 

Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 104
1 <= m * n <= 104
-105 <= mat[i][j] <= 105
 
Solution:

class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        if(mat==null||mat.length==0)return new int[0];
        int m=mat.length,n=mat[0].length;
        int[]result=new int[m*n];
        int row=0,col=0;

        for(int i=0;i<m*n;i++){
            result[i]=mat[row][col];
            if((row+col)%2==0){
                if(col==n-1)row++;
                else if(row==0)col++;
                else{row--;col++;}
            }else{
                if(row==m-1)col++;
                else if(col==0)row++;
                else{
                    row++;
                    col--;
                }
            }
        }
        return result;
    }
}
