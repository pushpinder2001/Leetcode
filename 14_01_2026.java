You are given a 2D integer array squares. Each squares[i] = [xi, yi, li] represents the coordinates of the bottom-left point and the side length of a square parallel to the x-axis.

Find the minimum y-coordinate value of a horizontal line such that the total area covered by squares above the line equals the total area covered by squares below the line.

Answers within 10-5 of the actual answer will be accepted.

Note: Squares may overlap. Overlapping areas should be counted only once in this version.

 

Example 1:

Input: squares = [[0,0,1],[2,2,1]]

Output: 1.00000

Explanation:



Any horizontal line between y = 1 and y = 2 results in an equal split, with 1 square unit above and 1 square unit below. The minimum y-value is 1.

Example 2:

Input: squares = [[0,0,2],[1,1,1]]

Output: 1.00000

Explanation:



Since the blue square overlaps with the red square, it will not be counted again. Thus, the line y = 1 splits the squares into two equal parts.

 

Constraints:

1 <= squares.length <= 5 * 104
squares[i] = [xi, yi, li]
squares[i].length == 3
0 <= xi, yi <= 109
  
  Solution:


class Solution {
    static class Event{
        double y,x1,x2;
        int type;
        Event(double y,double x1,double x2,int type){
            this.y=y;this.x1=x1;this.x2=x2;this.type=type;
        }
    }
    double [] xs,seg;
    int[] cnt;
    void update(int node ,int l,int r,int ql,int qr,int val){
        if(qr<=l||r<=ql)return ;
        if(ql<=l && r<=qr)cnt[node]+=val;
        else{
            int m=(l+r)/2;
            update(node*2,l,m,ql,qr,val);
            update(node*2+1,m,r,ql,qr,val);
        }
        if(cnt[node]>0)seg[node]=xs[r]-xs[l];
        else if(r-l==1)seg[node]=0;
        else seg[node]=seg[node*2]+seg[node*2+1];
    }
    public double separateSquares(int[][] squares) {
        ArrayList<Double> list=new ArrayList<>();
        for(int []s:squares){
            list.add((double)s[0]);
            list.add(s[0]+s[2]*1.0);
        }
        xs=list.stream().distinct().sorted().mapToDouble(d->d).toArray();

        ArrayList<Event>events=new ArrayList<>();
        for(int[]s:squares){
            events.add(new Event(s[1],s[0],s[0]+s[2],1));
            events.add(new Event(s[1]+s[2],s[0],s[0]+s[2],-1));
            
        }
        events.sort((a,b)->Double.compare(a.y,b.y));

        int n=xs.length;
        cnt=new int[4*n];
        seg=new double[4*n];

        double total=0,prevY=events.get(0).y;
        ArrayList<double[]> strips=new ArrayList<>();

        for(Event e:events){
            if(e.y>prevY){
                double h=e.y-prevY;
                double w=seg[1];
                total+=w*h;
                strips.add(new double[]{prevY,h,w});
                prevY=e.y;
            }
            int l=Arrays.binarySearch(xs,e.x1);
            int r=Arrays.binarySearch(xs,e.x2);
            update(1,0,n-1,l,r,e.type);
        } 
        double half=total/2,acc=0;
        for(double []s:strips){
            if(acc+s[1]*s[2]>=half)
            return s[0]+(half-acc)/s[2];
            acc+=s[1]*s[2];
        }
        return 0;
    }
}
