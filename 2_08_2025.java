You have two fruit baskets containing n fruits each. You are given two 0-indexed integer arrays basket1 and basket2 representing the cost of fruit in each basket. You want to make both baskets equal. To do so, you can use the following operation as many times as you want:

Chose two indices i and j, and swap the ith fruit of basket1 with the jth fruit of basket2.
The cost of the swap is min(basket1[i],basket2[j]).
Two baskets are considered equal if sorting them according to the fruit cost makes them exactly the same baskets.

Return the minimum cost to make both the baskets equal or -1 if impossible.

 

Example 1:

Input: basket1 = [4,2,2,2], basket2 = [1,4,1,2]
Output: 1
Explanation: Swap index 1 of basket1 with index 0 of basket2, which has cost 1. Now basket1 = [4,1,2,2] and basket2 = [2,4,1,2]. Rearranging both the arrays makes them equal.
Example 2:

Input: basket1 = [2,3,4,1], basket2 = [3,2,5,1]
Output: -1
Explanation: It can be shown that it is impossible to make both the baskets equal.
 

Constraints:

basket1.length == basket2.length
1 <= basket1.length <= 105
1 <= basket1[i],basket2[i] <= 109


  SoLution:

class Solution {
    public long minCost(int[] basket1, int[] basket2) {
        Map<Integer,Integer> totalCounts=new HashMap<>();
        for(int fruit:basket1)totalCounts.put(fruit,totalCounts.getOrDefault(fruit,0)+1);
        for(int fruit:basket2)totalCounts.put(fruit,totalCounts.getOrDefault(fruit,0)+1);
    
    long minVal=Long.MAX_VALUE;
    for(Map.Entry<Integer,Integer>entry:totalCounts.entrySet()){
        if(entry.getValue()%2!=0)return -1;
        minVal=Math.min(minVal,entry.getKey());
    }
    List<Long> fruits_to_swap=new ArrayList<>();
    Map<Integer,Integer> count1=new HashMap<>();
    for(int fruit:basket1)count1.put(fruit,count1.getOrDefault(fruit,0)+1);
    for(Map.Entry<Integer,Integer> entry:totalCounts.entrySet()){
        int fruit=entry.getKey();
        int diff=count1.getOrDefault(fruit,0)-(entry.getValue()/2);
        for(int i=0;i<Math.abs(diff);i++){
            fruits_to_swap.add((long)fruit);
        }
    }
    Collections.sort(fruits_to_swap);
    long totalCost=0;
    int swapsToMake=fruits_to_swap.size()/2;
    for(int i=0;i<swapsToMake;i++){
        totalCost+=Math.min(fruits_to_swap.get(i),2*minVal);
    }
    return totalCost;
    }
}
