You are given an integer c representing c power stations, each with a unique identifier id from 1 to c (1‑based indexing).

These stations are interconnected via n bidirectional cables, represented by a 2D array connections, where each element connections[i] = [ui, vi] indicates a connection between station ui and station vi. Stations that are directly or indirectly connected form a power grid.

Initially, all stations are online (operational).

You are also given a 2D array queries, where each query is one of the following two types:

[1, x]: A maintenance check is requested for station x. If station x is online, it resolves the check by itself. If station x is offline, the check is resolved by the operational station with the smallest id in the same power grid as x. If no operational station exists in that grid, return -1.

[2, x]: Station x goes offline (i.e., it becomes non-operational).

Return an array of integers representing the results of each query of type [1, x] in the order they appear.

Note: The power grid preserves its structure; an offline (non‑operational) node remains part of its grid and taking it offline does not alter connectivity.

 

Example 1:

Input: c = 5, connections = [[1,2],[2,3],[3,4],[4,5]], queries = [[1,3],[2,1],[1,1],[2,2],[1,2]]

Output: [3,2,3]

Explanation:



Initially, all stations {1, 2, 3, 4, 5} are online and form a single power grid.
Query [1,3]: Station 3 is online, so the maintenance check is resolved by station 3.
Query [2,1]: Station 1 goes offline. The remaining online stations are {2, 3, 4, 5}.
Query [1,1]: Station 1 is offline, so the check is resolved by the operational station with the smallest id among {2, 3, 4, 5}, which is station 2.
Query [2,2]: Station 2 goes offline. The remaining online stations are {3, 4, 5}.
Query [1,2]: Station 2 is offline, so the check is resolved by the operational station with the smallest id among {3, 4, 5}, which is station 3.
Example 2:

Input: c = 3, connections = [], queries = [[1,1],[2,1],[1,1]]

Output: [1,-1]

Explanation:

There are no connections, so each station is its own isolated grid.
Query [1,1]: Station 1 is online in its isolated grid, so the maintenance check is resolved by station 1.
Query [2,1]: Station 1 goes offline.
Query [1,1]: Station 1 is offline and there are no other stations in its grid, so the result is -1.
 

Constraints:

1 <= c <= 105
0 <= n == connections.length <= min(105, c * (c - 1) / 2)
connections[i].length == 2
1 <= ui, vi <= c
ui != vi
1 <= queries.length <= 2 * 105
queries[i].length == 2
queries[i][0] is either 1 or 2.
1 <= queries[i][1] <= c

  Solution:


class Solution {
    void dfs(int id,int node,List<List<Integer>> adj,boolean []vis,Map<Integer,TreeSet<Integer>> mp,Map<Integer,Integer> mpp){
        vis[node]=true;
        mp.get(id).add(node);
        mpp.put(node,id);
        for(int it:adj.get(node)){
            if(!vis[it]){
                dfs(id,it,adj,vis,mp,mpp);
            }
        }
        }
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        List<List<Integer>> adj=new ArrayList<>();
        for(int i=0;i<c;i++)adj.add(new ArrayList<>());
        for(int[] it:connections){
            adj.get(it[0]-1).add(it[1]-1);
            adj.get(it[1]-1).add(it[0]-1);

        }
        boolean []vis=new boolean[c];
        boolean [] online =new boolean[c];
        Arrays.fill(online,true);

        int id=0;
        Map<Integer,TreeSet<Integer>> mp=new HashMap<>();
        Map<Integer,Integer> mpp=new HashMap<>();

        for(int i=0;i<c;i++){
            if(!vis[i]){
                mp.put(id,new TreeSet<>());
                dfs(id,i,adj,vis,mp,mpp);
                id++;
            }

        } 
        List<Integer> ansList=new ArrayList<>();
        for(int []q:queries){
            int type=q[0];
            int node=q[1]-1;
            int compId=mpp.get(node);
            if(type==2){
                mp.get(compId).remove(node);
                online[node]=false;
                continue;
            }
            if(online[node]){
                ansList.add(node+1);
              }
              else if(!mp.get(compId).isEmpty()) {
                ansList.add(mp.get(compId).first()+1);
              }else{
                ansList.add(-1);
              }
        }
int [] ans=new int [ansList.size()];
for(int i=0;i<ansList.size();i++)ans[i]=ansList.get(i);
    return ans;
    }
}
