Design a data structure that can efficiently manage data packets in a network router. Each data packet consists of the following attributes:

source: A unique identifier for the machine that generated the packet.
destination: A unique identifier for the target machine.
timestamp: The time at which the packet arrived at the router.
Implement the Router class:

Router(int memoryLimit): Initializes the Router object with a fixed memory limit.

memoryLimit is the maximum number of packets the router can store at any given time.
If adding a new packet would exceed this limit, the oldest packet must be removed to free up space.
bool addPacket(int source, int destination, int timestamp): Adds a packet with the given attributes to the router.

A packet is considered a duplicate if another packet with the same source, destination, and timestamp already exists in the router.
Return true if the packet is successfully added (i.e., it is not a duplicate); otherwise return false.
int[] forwardPacket(): Forwards the next packet in FIFO (First In First Out) order.

Remove the packet from storage.
Return the packet as an array [source, destination, timestamp].
If there are no packets to forward, return an empty array.
int getCount(int destination, int startTime, int endTime):

Returns the number of packets currently stored in the router (i.e., not yet forwarded) that have the specified destination and have timestamps in the inclusive range [startTime, endTime].
Note that queries for addPacket will be made in increasing order of timestamp.

 

Example 1:

Input:
["Router", "addPacket", "addPacket", "addPacket", "addPacket", "addPacket", "forwardPacket", "addPacket", "getCount"]
[[3], [1, 4, 90], [2, 5, 90], [1, 4, 90], [3, 5, 95], [4, 5, 105], [], [5, 2, 110], [5, 100, 110]]

Output:
[null, true, true, false, true, true, [2, 5, 90], true, 1]

Explanation

Router router = new Router(3); // Initialize Router with memoryLimit of 3.
router.addPacket(1, 4, 90); // Packet is added. Return True.
router.addPacket(2, 5, 90); // Packet is added. Return True.
router.addPacket(1, 4, 90); // This is a duplicate packet. Return False.
router.addPacket(3, 5, 95); // Packet is added. Return True
router.addPacket(4, 5, 105); // Packet is added, [1, 4, 90] is removed as number of packets exceeds memoryLimit. Return True.
router.forwardPacket(); // Return [2, 5, 90] and remove it from router.
router.addPacket(5, 2, 110); // Packet is added. Return True.
router.getCount(5, 100, 110); // The only packet with destination 5 and timestamp in the inclusive range [100, 110] is [4, 5, 105]. Return 1.
Example 2:

Input:
["Router", "addPacket", "forwardPacket", "forwardPacket"]
[[2], [7, 4, 90], [], []]

Output:
[null, true, [7, 4, 90], []]

Explanation

Router router = new Router(2); // Initialize Router with memoryLimit of 2.
router.addPacket(7, 4, 90); // Return True.
router.forwardPacket(); // Return [7, 4, 90].
router.forwardPacket(); // There are no packets left, return [].
 

Constraints:

2 <= memoryLimit <= 105
1 <= source, destination <= 2 * 105
1 <= timestamp <= 109
1 <= startTime <= endTime <= 109
At most 105 calls will be made to addPacket, forwardPacket, and getCount methods altogether.
queries for addPacket will be made in increasing order of timestamp.

  Solution:
  

class Router {
private final Queue<int[]> queue;
private final int maxMemory;
private final Map<Integer,Destination> destinations;
    public Router(int memoryLimit) {
        queue=new LinkedList<>();
        maxMemory=memoryLimit;
        destinations=new HashMap<>();
    }
    
    public boolean addPacket(int source, int destination, int timestamp) {
        Destination dest=destinations.get(destination);
        if(dest!=null && dest.contains(timestamp,source))return false;
        if(queue.size()==maxMemory){
            int[]firstPacket=queue.poll();
            Destination dest1=destinations.get(firstPacket[1]);
            dest1.removePacket();
                    }
    if(dest==null){
        dest=new Destination();
        destinations.put(destination,dest);
    }
    dest.addPacket(timestamp,source);
    queue.add(new int[]{source,destination,timestamp});
    return true;
    }
    
    
    public int[] forwardPacket() {
        if(queue.isEmpty())return new int[]{};
        int []firstPacket=queue.poll();
        Destination dest1=destinations.get(firstPacket[1]);
        dest1.removePacket();
        return new int[]{firstPacket[0],firstPacket[1],firstPacket[2]};
    }
    
    public int getCount(int destination, int startTime, int endTime) {
        Destination dest=destinations.get(destination);
        if(dest==null)return 0;
        return dest.getCount(startTime,endTime);
    }
}
class Destination{
    private static final long MULTIPLIER=1000000L;
    private int start=-1,end=-1;
    private final List<int[]> packets;
    private final Set<Long> packetSet;
    Destination(){
        packets=new ArrayList<>();
        packetSet=new HashSet<>();

    }
    public void addPacket(int timestamp,int source){
        if(start==-1){
            start=0;
        }
        if(packets.size()>end+1){
            packets.set(end+1,new int[]{timestamp,source});
            end++;
        }else{
            packets.add(new int[]{timestamp,source});
            end++;
        }
        long x=(long)timestamp*MULTIPLIER+source;
        packetSet.add(x);
    }
    public void removePacket(){
        long x=(long)packets.get(start)[0]*MULTIPLIER + packets.get(start)[1];
packetSet.remove(x);
if(start==end){
    start=-1;
    end=-1;

}else{
    start++;
}    
}
public int getCount(int startTime,int endTime){
    if(start==-1)return 0;
    int lb=end+1;
    int ub=-1;
    int tempStart=start;
    int tempEnd=end;
    while(tempStart<=tempEnd){
        int mid=tempStart+(tempEnd-tempStart)/2;
        int x=packets.get(mid)[0];
        if(x>=startTime){
            lb=mid;
            tempEnd=mid-1;

        }else{
            tempStart=mid+1;
        }
    }
    tempStart=start;
    tempEnd=end;
    while(tempStart<=tempEnd){
        int mid=tempStart+(tempEnd-tempStart)/2;
        int x=packets.get(mid)[0];
        if(x<=endTime){
            ub=mid;
            tempStart=mid+1;
        }else{
            tempEnd=mid-1;

        }

    }
    if(lb<=ub)return ub-lb+1;

return 0;
}
public boolean contains(int timestamp,int source){
    if(start==-1)return false;
    long key=(long)timestamp*MULTIPLIER+source;
    return packetSet.contains(key);
}
}
/**
 * Your Router object will be instantiated and called as such:
 * Router obj = new Router(memoryLimit);
 * boolean param_1 = obj.addPacket(source,destination,timestamp);
 * int[] param_2 = obj.forwardPacket();
 * int param_3 = obj.getCount(destination,startTime,endTime);
 */
