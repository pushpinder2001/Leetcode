A magician has various spells.

You are given an array power, where each element represents the damage of a spell. Multiple spells can have the same damage value.

It is a known fact that if a magician decides to cast a spell with a damage of power[i], they cannot cast any spell with a damage of power[i] - 2, power[i] - 1, power[i] + 1, or power[i] + 2.

Each spell can be cast only once.

Return the maximum possible total damage that a magician can cast.

 

Example 1:

Input: power = [1,1,3,4]

Output: 6

Explanation:

The maximum possible damage of 6 is produced by casting spells 0, 1, 3 with damage 1, 1, 4.

Example 2:

Input: power = [7,1,6,6]

Output: 13

Explanation:

The maximum possible damage of 13 is produced by casting spells 1, 2, 3 with damage 1, 6, 6.

 

Constraints:

1 <= power.length <= 105
1 <= power[i] <= 109


  Solution:

class Solution {
    public long maximumTotalDamage(int[] power) {
        Map<Integer,Long> freq=new HashMap<>();
        for(int p:power)freq.put(p,freq.getOrDefault(p,0L)+1);
        List<Integer> keys=new ArrayList<>(freq.keySet());
        Collections.sort(keys);

        int n=keys.size();
        long[] dp=new long[n];
        dp[0]=freq.get(keys.get(0))*keys.get(0);

        for(int i=1;i<n;i++){
            long take =freq.get(keys.get(i))*keys.get(i);
            int prev=binarySearch(keys,i-1,keys.get(i)-3);
            if(prev>=0)take+=dp[prev];
            dp[i]=Math.max(dp[i-1],take);

        }
        return dp[n-1];
    }
    private int binarySearch(List<Integer> keys,int end,int value){
        int l=0,r=end,ans=-1;
        while(l<=r){
            int mid=(l+r)/2;
            if(keys.get(mid)<=value){
                ans=mid;
                l=mid+1;

            }else r=mid-1;
        }
        return ans;

    }
}
