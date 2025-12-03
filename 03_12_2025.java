You are given a 2D integer array points where points[i] = [xi, yi] represents the coordinates of the ith point on the Cartesian plane.

Return the number of unique trapezoids that can be formed by choosing any four distinct points from points.

A trapezoid is a convex quadrilateral with at least one pair of parallel sides. Two lines are parallel if and only if they have the same slope.

 

Example 1:

Input: points = [[-3,2],[3,0],[2,3],[3,2],[2,-3]]

Output: 2

Explanation:



There are two distinct ways to pick four points that form a trapezoid:

The points [-3,2], [2,3], [3,2], [2,-3] form one trapezoid.
The points [2,3], [3,2], [3,0], [2,-3] form another trapezoid.
Example 2:

Input: points = [[0,0],[1,0],[0,1],[2,1]]

Output: 1

Explanation:



There is only one trapezoid which can be formed.

 

Constraints:

4 <= points.length <= 500
–1000 <= xi, yi <= 1000
All points are pairwise distinct.

  Solution:


class Solution {
    public int countTrapezoids(int[][] points) {

        HashMap<Integer,HashMap<Integer,Integer>> t=new HashMap<>();
        HashMap<Integer,HashMap<Integer,Integer>> v=new HashMap<>();

        int n=points.length;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){


                int dx=points[j][0]-points[i][0];
                int dy=points[j][1]-points[i][1];

                if(dx<0||(dx==0 && dy<0)){
                    dx=-dx;
                    dy=-dy;
                }
                int g=gcd(dx,Math.abs(dy));
                int sx=dx/g;
                int sy=dy/g;
                int des=sx * points[i][1]-sy * points[i][0];
                
                int key1=(sx<<12)|(sy+2000);
                int key2=(dx<<12)|(dy+2000);

                t.computeIfAbsent(key1,k->new HashMap<>()).merge(des,1,Integer::sum);
                v.computeIfAbsent(key2,k->new HashMap<>()).merge(des,1,Integer::sum);
            }
        }
        return count(t)-count(v)/2;
    }
    private int count(HashMap<Integer,HashMap<Integer,Integer>> map){
        long ans=0;

        for(HashMap<Integer,Integer> inner:map.values()){
            long sum=0;

            for(int val:inner.values())sum+=val;
            
            for(int val:inner.values()){
                sum-=val;
                ans+=(long)val*sum;
            }
        }
        return (int) ans;
    }
    private int gcd(int a,int b){
        while(b!=0){
            int t=a % b;
            a=b;
            b=t;
        }
    return Math.abs(a);
    }

}
