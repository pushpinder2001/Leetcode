Given the root of a binary tree, split the binary tree into two subtrees by removing one edge such that the product of the sums of the subtrees is maximized.

Return the maximum product of the sums of the two subtrees. Since the answer may be too large, return it modulo 109 + 7.

Note that you need to maximize the answer before taking the mod and not after taking it.

 

Example 1:


Input: root = [1,2,3,4,5,6]
Output: 110
Explanation: Remove the red edge and get 2 binary trees with sum 11 and 10. Their product is 110 (11*10)
Example 2:


Input: root = [1,null,2,3,4,null,null,5,6]
Output: 90
Explanation: Remove the red edge and get 2 binary trees with sum 15 and 6.Their product is 90 (15*6)
 

Constraints:

The number of nodes in the tree is in the range [2, 5 * 104].
1 <= Node.val <= 1044

  Solution:

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
     static final int MOD=1_000_000_007;
     long totalSum=0;
     long maxProduct=0;

     long getTotalSum(TreeNode root){
        if(root==null)return 0;
        return root.val+getTotalSum(root.left)+getTotalSum(root.right);
     }
     long dfs(TreeNode root){
        if(root==null)return 0;

        long left=dfs(root.left);
        long right=dfs(root.right);

        long subSum=root.val+left+right;

        long product=subSum*(totalSum-subSum);
        maxProduct=Math.max(maxProduct,product);

        return subSum;
     }


    public int maxProduct(TreeNode root) {
        totalSum=getTotalSum(root);
        dfs(root);
        return(int) (maxProduct%MOD);
    }
}
