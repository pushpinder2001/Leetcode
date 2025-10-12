You are given two integers, m and k, and an integer array nums.

A sequence of integers seq is called magical if:
seq has a size of m.
0 <= seq[i] < nums.length
The binary representation of 2seq[0] + 2seq[1] + ... + 2seq[m - 1] has k set bits.
The array product of this sequence is defined as prod(seq) = (nums[seq[0]] * nums[seq[1]] * ... * nums[seq[m - 1]]).

Return the sum of the array products for all valid magical sequences.

Since the answer may be large, return it modulo 109 + 7.

A set bit refers to a bit in the binary representation of a number that has a value of 1.

 

Example 1:

Input: m = 5, k = 5, nums = [1,10,100,10000,1000000]

Output: 991600007

Explanation:

All permutations of [0, 1, 2, 3, 4] are magical sequences, each with an array product of 1013.

Example 2:

Input: m = 2, k = 2, nums = [5,4,3,2,1]

Output: 170

Explanation:

The magical sequences are [0, 1], [0, 2], [0, 3], [0, 4], [1, 0], [1, 2], [1, 3], [1, 4], [2, 0], [2, 1], [2, 3], [2, 4], [3, 0], [3, 1], [3, 2], [3, 4], [4, 0], [4, 1], [4, 2], and [4, 3].

Example 3:

Input: m = 1, k = 1, nums = [28]

Output: 28

Explanation:

The only magical sequence is [0].

 

Constraints:

1 <= k <= m <= 30
1 <= nums.length <= 50
1 <= nums[i] <= 108

  Solution:

class Solution {
    static final int MOD=1_000_000_007,MAX=31;
    static final long[] FACT=new long[MAX],INV_FACT=new long[MAX];
    static {
        FACT[0]=1;
        for(int i=1;i<MAX;i++)FACT[i]=FACT[i-1]*i % MOD;
        INV_FACT[MAX-1]=pow(FACT[MAX-1],MOD-2);
        for(int i=MAX-1;i>0;i--)INV_FACT[i-1]=INV_FACT[i]*i%MOD;
    }
    static long pow(long x,int n){
        long res=1;
        for(;n>0;n>>=1,x=x*x%MOD)
           if((n&1)==1)res=res*x % MOD;
    return res;
    }
    public int magicalSum(int m, int k, int[] nums) {
        int n=nums.length;
        int[][]pows=new int[n][m+1];
        for(int i=0;i<n;i++){
            pows[i][0]=1;
            for(int j=1;j<=m;j++)
            pows[i][j]=(int)((long)pows[i][j-1]*nums[i]%MOD);

        }
        int [][][][]memo=new int[n][m+1][m/2+1][k+1];
        for(int [][][]a:memo)
          for(int [][]b:a)
          for(int[]c:b)
          Arrays.fill(c,-1);

    return (int)(dfs(0,m,0,k,pows,memo)*FACT[m]%MOD);
    }
    long dfs(int i,int mLeft,int carry,int kLeft,int [][]pows,int[][][][]memo){
        int ones=Integer.bitCount(carry);
        if(ones+mLeft<kLeft)return 0;
        if(i==pows.length)return (mLeft==0 && ones==kLeft)?1:0;
        if(memo[i][mLeft][carry][kLeft]!=-1)return memo[i][mLeft][carry][kLeft];

        long res=0;
        for(int j=0;j<=mLeft;j++){
            int bit=(carry+j)&1;
            if(bit<=kLeft){
                long r=dfs(i+1,mLeft-j,(carry+j)>>1,kLeft-bit,pows,memo);
                res=(res+r*pows[i][j]%MOD*INV_FACT[j])%MOD;
            }
        }
        return memo[i][mLeft][carry][kLeft]=(int)res;
    }
}
