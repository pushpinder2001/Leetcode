Given an integer numRows, return the first numRows of Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:


 

Example 1:

Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
Example 2:

Input: numRows = 1
Output: [[1]]

Solution:

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> pascal=new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> row=new ArrayList<>(Collections.nCopies(i+1,1));
            for(int j=1;j<i;j++){
                int val=pascal.get(i-1).get(j-1)+pascal.get(i-1).get(j);
                row.set(j,val);
            }
            pascal.add(row);
        }
        return pascal;
    }
}
