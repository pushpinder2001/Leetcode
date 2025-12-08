A square triple (a,b,c) is a triple where a, b, and c are integers and a2 + b2 = c2.

Given an integer n, return the number of square triples such that 1 <= a, b, c <= n.

 

Example 1:

Input: n = 5
Output: 2
Explanation: The square triples are (3,4,5) and (4,3,5).
Example 2:

Input: n = 10
Output: 4
Explanation: The square triples are (3,4,5), (4,3,5), (6,8,10), and (8,6,10).
 

Constraints:

1 <= n <= 250

  Solution:
class Solution {
    public int countTriples(int n) {
     int res=0;
     for(int u=2;u*u <=n;u++){
        for(int v=1;v<u;v++){
            if(((u-v) & 1 )==0 || gcd(u,v)!=1)continue;
            int c=u*u+v*v;
            if(c>n) continue;

            res+=(n/c)<<1;
        }
     }   
     return res;
    }
    int gcd(int x,int y){
        return y==0?x:gcd(y,x%y);
    }
}
