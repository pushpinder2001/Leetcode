You are given an array nums of n integers and two integers k and x.

The x-sum of an array is calculated by the following procedure:

Count the occurrences of all elements in the array.
Keep only the occurrences of the top x most frequent elements. If two elements have the same number of occurrences, the element with the bigger value is considered more frequent.
Calculate the sum of the resulting array.
Note that if an array has less than x distinct elements, its x-sum is the sum of the array.

Return an integer array answer of length n - k + 1 where answer[i] is the x-sum of the subarray nums[i..i + k - 1].

 

Example 1:

Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

Output: [6,10,12]

Explanation:

For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
Example 2:

Input: nums = [3,8,7,8,7,5], k = 2, x = 2

Output: [11,15,15,15,12]

Explanation:

Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].

  Solution:

class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n=nums.length;
        int []ans=new int[Math.max(0,n-k+1)];
        Map<Integer,Integer> freq=new HashMap<>();
        for(int i=0;i<k;i++){
            freq.put(nums[i],freq.getOrDefault(nums[i],0)+1);
        }
        ans[0]=computeXSum(freq,x);
        for(int i=k;i<n;i++){
            int add=nums[i];
            int rem=nums[i-k];
            freq.put(add,freq.getOrDefault(add,0)+1);
            int fr=freq.get(rem)-1;
            if(fr==0)freq.remove(rem);
            else freq.put(rem,fr);

            ans[i-k+1]=computeXSum(freq,x);
        }
        return ans;
    }

    private int computeXSum(Map<Integer,Integer> freq,int x){
        List<int[]> items=new ArrayList<>();
        for(Map.Entry<Integer,Integer> e:freq.entrySet()){
            items.add(new int[]{e.getKey(),e.getValue()} );
        }
        items.sort((a,b)->{
            if(a[1]!=b[1])return b[1]-a[1];
            return b[0]-a[0];
        });
        long sum=0;
        int take=Math.min(x,items.size());
        for(int i=0;i<take;i++){
            sum+=1L* items.get(i)[0]*items.get(i)[1];
        }
        return (int)sum;
    }
}
