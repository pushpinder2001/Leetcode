You are given an integer array nums and two integers k and numOperations.

You must perform an operation numOperations times on nums, where in each operation you:

Select an index i that was not selected in any previous operations.
Add an integer in the range [-k, k] to nums[i].
Return the maximum possible frequency of any element in nums after performing the operations.

 

Example 1:

Input: nums = [1,4,5], k = 1, numOperations = 2

Output: 2

Explanation:

We can achieve a maximum frequency of two by:

Adding 0 to nums[1], after which nums becomes [1, 4, 5].
Adding -1 to nums[2], after which nums becomes [1, 4, 4].
Example 2:

Input: nums = [5,11,20,20], k = 5, numOperations = 1

Output: 2

Explanation:

We can achieve a maximum frequency of two by:

Adding 0 to nums[1].
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
0 <= k <= 109
0 <= numOperations <= nums.length


  Solution:
class Solution {
    private int check(int []nums,int n,int t,int m){
        long nL=n;
        long tL=t;
        int l=lowerBound(nums,nL);
        int h=upperBound(nums,nL);
        int ll=lowerBound(nums,nL-tL);
        int hh=upperBound(nums,nL+tL);
        int res=(hh-h)+(l-ll);
        return Math.min(m,res)+(h-l);
    }
    private int lowerBound(int []arr,long target){
        int l=0,r=arr.length;
        while(l<r){
            int mid=(l+r)/2;
            if(arr[mid]<target)l=mid+1;
            else r=mid;        }
            return l;
    }
    private int upperBound(int []arr,long target){
    int l=0,r=arr.length;
    while(l<r){
        int mid=(l+r)/2;
        if(arr[mid]<=target)l=mid+1;
        else r=mid;
    }
    return l;
    }
    public int maxFrequency(int[] nums, int k, int numOperations) {
    int m=numOperations;
    Arrays.sort(nums);
    int ans=1;
    for(int i=0;i<nums.length-1;i++){
        ans=Math.max(ans,check(nums,nums[i],k,m));
        ans=Math.max(ans,check(nums,nums[i]-k,k,m));
        ans=Math.max(ans,check(nums,nums[i]+k,k,m));
    }    
    return ans;

    }
}

