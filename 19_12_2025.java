You are given an integer n indicating there are n people numbered from 0 to n - 1. You are also given a 0-indexed 2D integer array meetings where meetings[i] = [xi, yi, timei] indicates that person xi and person yi have a meeting at timei. A person may attend multiple meetings at the same time. Finally, you are given an integer firstPerson.

Person 0 has a secret and initially shares the secret with a person firstPerson at time 0. This secret is then shared every time a meeting takes place with a person that has the secret. More formally, for every meeting, if a person xi has the secret at timei, then they will share the secret with person yi, and vice versa.

The secrets are shared instantaneously. That is, a person may receive the secret and share it with people in other meetings within the same time frame.

Return a list of all the people that have the secret after all the meetings have taken place. You may return the answer in any order.

 

Example 1:

Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
Output: [0,1,2,3,5]
Explanation:
At time 0, person 0 shares the secret with person 1.
At time 5, person 1 shares the secret with person 2.
At time 8, person 2 shares the secret with person 3.
At time 10, person 1 shares the secret with person 5.​​​​
Thus, people 0, 1, 2, 3, and 5 know the secret after all the meetings.
Example 2:

Input: n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
Output: [0,1,3]
Explanation:
At time 0, person 0 shares the secret with person 3.
At time 2, neither person 1 nor person 2 know the secret.
At time 3, person 3 shares the secret with person 0 and person 1.
Thus, people 0, 1, and 3 know the secret after all the meetings.
Example 3:

Input: n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
Output: [0,1,2,3,4]
Explanation:
At time 0, person 0 shares the secret with person 1.
At time 1, person 1 shares the secret with person 2, and person 2 shares the secret with person 3.
Note that person 2 can share the secret at the same time as receiving it.
At time 2, person 3 shares the secret with person 4.
Thus, people 0, 1, 2, 3, and 4 know the secret after all the meetings.
 

Constraints:

2 <= n <= 105
1 <= meetings.length <= 105
meetings[i].length == 3
0 <= xi, yi <= n - 1
xi != yi
1 <= timei <= 105
1 <= firstPerson <= n - 1

  Solution:

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {

      Map<Integer,List<int[]>> timeMeetings=new TreeMap<>();
      for(int[] m:meetings){
        int x=m[0],y=m[1],t=m[2];
        timeMeetings.computeIfAbsent(t,k-> new ArrayList<>()).add(new int[]{x,y});
      }    
      boolean [] ks=new boolean[n];
      ks[0]=true;
      ks[firstPerson]=true;

      for(int t:timeMeetings.keySet())
{
Map<Integer,List<Integer>> meetList=new HashMap<>();
for(int []m:timeMeetings.get(t)){

    int x=m[0],y=m[1];
    meetList.computeIfAbsent(x,k->new ArrayList<>()).add(y);
    meetList.computeIfAbsent(y,k->new ArrayList<>()).add(x);
}


Set<Integer> start=new HashSet<>();
for(int []m:timeMeetings.get(t)){
    int x=m[0],y=m[1];
    if(ks[x])start.add(x);
    if(ks[y])start.add(y);
}
Queue<Integer> q=new LinkedList<>(start);
while(!q.isEmpty()){
    int person=q.poll();
    for(int nextPerson:meetList.getOrDefault(person,new ArrayList<>())){
        if(!ks[nextPerson]){
            ks[nextPerson]=true;
            q.offer(nextPerson);
        }
    }
}
}
List<Integer> res=new ArrayList<>();
for(int i=0;i<n;i++){
    if(ks[i])res.add(i);
}
return res;
    }
}
