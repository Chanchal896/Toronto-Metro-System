import java.util.*;
import java.io.*;
public class Metro {

	public static void main(String[] args) {
		graph metro = new graph();
		try {
			String s,d;
			int t;
			BufferedReader read = new BufferedReader(new FileReader("data.txt"));
			String a;
			while((a=read.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(a,",");
				s=st.nextToken();
				d=st.nextToken();
				t=Integer.parseInt(st.nextToken());
				metro.createdge(s,d,t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String a="Kipling";
		String b="York Mills";
		graph.shortestPath(metro,a,b);
	}
	static class inlist{
		String source;
		String destination;
		int time;
		boolean visted;
		
		inlist(String s,String d,int t){
			this.source=s;
			this.destination=d;
			this.time=t;
			this.visted = false;
			
		}
		public int gettime() {
			return this.time;
		}
		@Override
		public String toString(){
		return source+","+destination+","+time+"\n";
		}
	}
	public static class MyList {
	    ArrayList<inlist> list = new ArrayList<inlist>();

	    public MyList(String a,String b,int c) {
	        list.add(new inlist(a,b,c));
	    }

	    public void add(String s, String d, int t) {
	        inlist item = new inlist(s, d, t);
	        list.add(item);
	    }
	   public int gettime() {
		   for(int i=0;i<list.size();++i) {
			   return list.get(i).time;   
		   }
		return 0;
	   }
	   public String gets(int a) {
			  return (list.get(a).destination);
	   }
	   public String getdc(String a) {
		   for(int i=0;i<list.size();++i) {
			   if(list.get(i).destination==a) {
				   return list.get(i).destination;
			   }
		   }
		return null;
	   }
	   public boolean compare(String a) {
		   for(int i=0;i<list.size();++i) {
			   if(list.get(i).destination==a) {}
			   return true;
		   }
		return false;
	   }
	    @Override
		public String toString(){
		return list+"";
		}
	}
	
	static class pair implements Comparable<pair>{
		String name;
		int dis;
		
		public pair(String a,int b){
			this.name=a;
			this.dis=b;
		}
		
		@Override
		public int compareTo(pair p2) {
			return this.dis-p2.dis;
		}
		
	}
	
	public static class graph{
		 static HashMap<String,ArrayList<MyList>> graph;
		
		public graph(){
			graph=new HashMap<>();
		}
		public  void createnode(String cityname) {
			if(!(graph.containsKey(cityname))) {
				graph.put(cityname, new ArrayList<MyList>());
			}
		}
		
		public void createdge(String s,String d,int c) {
			if(!(graph.containsKey(s))) {
				graph.put(s, new ArrayList<MyList>());
			}
			if(!(graph.containsKey(d))) {
				graph.put(d, new ArrayList<MyList>());
			}
			graph.get(s).add(new MyList(s,d,c));
			graph.get(d).add(new MyList(d,s,c));
		}
		
		 public static List<String> shortestPath(graph g, String source, String dest) {
		        HashMap<String, Integer> distances = new HashMap<>();
		        HashMap<String, String> prev = new HashMap<>();
		        PriorityQueue<pair> pq = new PriorityQueue<>();

		        // set all distances to infinity except the source vertex
		        for (String v : g.graph.keySet()) {
		            if (v.equals(source)) {
		                distances.put(v, 0);
		            } else {
		                distances.put(v, Integer.MAX_VALUE);
		            }
		            prev.put(v, null);
		        }

		        // add source vertex to the priority queue
		        pq.add(new pair(source, 0));

		        while (!pq.isEmpty()) {
		            pair curr = pq.poll();
		            String currVertex = curr.name;
		            int currDist = curr.dis;

		            // check if we have already found a shorter path to this vertex
		            if (currDist > distances.get(currVertex)) {
		                continue;
		            }

		            // iterate through the neighbors of the current vertex
		            for (MyList neighbor : g.graph.get(currVertex)) {
		                String neighborVertex = neighbor.gets(0);
		                int weight = neighbor.gettime();
		                int alt = currDist + weight;

		                // update the distance and previous vertex if we found a shorter path
		                if (alt < distances.get(neighborVertex)) {
		                    distances.put(neighborVertex, alt);
		                    prev.put(neighborVertex, currVertex);
		                    pq.add(new pair(neighborVertex, alt));
		                }
		            }
		        }
		        System.out.println(distances.get(dest));
		        // construct the shortest path list by iterating through the previous vertices
		        List<String> shortestPath = new ArrayList<>();
		        String curr = dest;
		        while (curr != null) {
		            shortestPath.add(0, curr);
		            curr = prev.get(curr);
		        }
		        System.out.println(shortestPath);
		        return shortestPath;
		    }

		
		public static int getdistances(String s,String d) {
			if(!(graph.containsKey(s))) {
				System.out.println("Source not avaliable");
			}
			else {
				ArrayList<MyList> list=graph.get(s);
				for(int i=0;i<list.size();++i) {
					if(list.get(i).compare(d)) {
					return list.get(i).gettime();
					}
				}
			}
			return 0;
		}
		public static String getneighbours(String s) {
			if(!(graph.containsKey(s))) {
				System.out.println("Source not avaliable");
				return null;
			}
			else {
				ArrayList<MyList> list=graph.get(s);
				System.out.println(list);
				for(int i=0;i<list.size();++i) {
					return list.get(i).gets(i);
				}
			}
			return null;
		}
		@Override
		public String toString(){
		return graph+"";
		}	
	}}
