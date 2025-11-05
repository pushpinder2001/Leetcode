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

 

Constraints:

nums.length == n
1 <= n <= 105
1 <= nums[i] <= 109
1 <= x <= k <= nums.length

  Solution:
class Solution {
    public long[] findXSum(int[] nums, int k, int x) {
        int n=nums.length;
        long [] ans=new long[n-k+1];

        HashMap<Integer,Integer> freq=new HashMap<>();
        Comparator<Integer>cmp=(a,b)->{
            int fa=freq.getOrDefault(a,0),fb=freq.getOrDefault(b,0);
            if(fa!=fb)return Integer.compare(fa,fb);
            return Integer.compare(a,b);
        };
        TreeSet<Integer> topX=new TreeSet<>(cmp);
        TreeSet<Integer> rest=new TreeSet<>(cmp);
        long sumTop=0L;
        for(int i=0;i<n;i++){
            int v=nums[i];
            int old=freq.getOrDefault(v,0);
            if(old>0){
                if(topX.remove(v)){
                    sumTop-=(long)old*v;
                }
                else{
                    rest.remove(v);
                }
            }
            freq.put(v,old+1);
            rest.add(v);

            sumTop=rebalance(topX,rest,freq,x,sumTop);

            if(i>=k){
                int u=nums[i-k];
                int oldU=freq.get(u);
                if(topX.remove(u)){
                    sumTop-=(long)oldU*u;
                }else{
                    rest.remove(u);
                }
                if(oldU==1){
                    freq.remove(u);
                }else{
                    freq.put(u,oldU-1);
                    rest.add(u);
                }
                sumTop=rebalance(topX,rest,freq,x,sumTop);
            }

            if(i>=k-1){
                ans[i-k+1]=sumTop;
            }
        }
        return ans;
    }

    public long rebalance(TreeSet<Integer> topX,TreeSet<Integer> rest,Map<Integer,Integer>freq,int x,long sumTop){

        while(topX.size()<x && !rest.isEmpty()){
            int best=rest.last();
            rest.remove(best);
            topX.add(best);
            sumTop+=(long)freq.get(best)*best;
        }
        while(topX.size()>x){
            int worst=topX.first();
            topX.remove(worst);
            rest.add(worst);
            sumTop-=(long) freq.get(worst)*worst;
        }
        while(!topX.isEmpty() && !rest.isEmpty()){
            int worstTop=topX.first();
            int bestRest=rest.last();
            int fw=freq.get(worstTop),fr=freq.get(bestRest);
            if(fr>fw||(fr==fw && bestRest >worstTop)){
                topX.remove(worstTop);
                rest.remove(bestRest);
                topX.add(bestRest);
                rest.add(worstTop);
                sumTop+=(long)fr*bestRest -(long)fw*worstTop;
            }
            else{
                break;
            }
        }
        return sumTop;
    }
}

