You are given a binary string s.

Return the number of substrings with dominant ones.

A string has dominant ones if the number of ones in the string is greater than or equal to the square of the number of zeros in the string.

 

Example 1:

Input: s = "00011"

Output: 5

Explanation:

The substrings with dominant ones are shown in the table below.

i	j	s[i..j]	Number of Zeros	Number of Ones
3	3	1	0	1
4	4	1	0	1
2	3	01	1	1
3	4	11	0	2
2	4	011	1	2
Example 2:

Input: s = "101101"

Output: 16

Explanation:

The substrings with non-dominant ones are shown in the table below.

Since there are 21 substrings total and 5 of them have non-dominant ones, it follows that there are 16 substrings with dominant ones.

i	j	s[i..j]	Number of Zeros	Number of Ones
1	1	0	1	0
4	4	0	1	0
1	4	0110	2	2
0	4	10110	2	3
1	5	01101	2	3


  Solution:
class Solution {
    public int numberOfSubstrings(String s) {
        int n=s.length();
        int [] pref=new int[n+1];
        for(int i=0;i<n;i++){
            pref[i+1]=pref[i]+(s.charAt(i)=='1'?1:0);
        }
        List<Integer> Z=new ArrayList<>();
        for(int i=0;i<n;i++)
        if(s.charAt(i)=='0')Z.add(i);
        int m=Z.size();

        long ans=0;
        int i=0;
        while(i<n){
            if(s.charAt(i)=='0'){i++;continue;}
            int j=i;
            while(j<n && s.charAt(j)=='1')j++;
            long len=j-i;
            ans+=len*(len+1)/2;
            i=j;
        }
        int B=(int)Math.sqrt(n)+2;
        for(int a=0;a<m;a++){
            int Lmin=(a==0?0:Z.get(a-1)+1),Lmax=Z.get(a);
            if(Lmin>Lmax)continue;
            for(int z=1;z<=B;z++){
                int b=a+z-1;
                if(b>=m)break;

                int Rmin=Z.get(b),Rmax=(b+1<m?Z.get(b+1)-1:n-1);
                if(Rmin>Rmax) continue;

                int need=z*z,r=Rmin;

                for(int l=Lmin;l<=Lmax;l++){
                    if(pref[Rmax+1]-pref[l]<need) continue;
                    while(r<=Rmax && pref[r+1]-pref[l]<need)r++;
                    if(r>Rmax)break;
                    ans+=(Rmax-r+1);
                }
            }
        }
        return (int)ans;
    }
}
