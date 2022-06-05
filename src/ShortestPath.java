import java.nio.file.Paths;
import java.util.*;

public class ShortestPath{
    private int V;

    // private static class Node{
    //     public final String name;
    //     public Map<Node, Integer> adjacentNodes;

    //     public Node(String n){
    //         this.name = n;
    //         adjacentNodes = new HashMap<Node, Integer>();
    //     }
    // }

    // public static ArrayList<Node> convert(int[][] inMatrix){
    //     ArrayList<Node> nodeList = new ArrayList<Node>();
    //     for(int i = 0; i < inMatrix.length; i++){
    //         for (int j=0 ; j<inMatrix[0].length ; j++){
    //             nodeList.add(new Node(i+"-"+j));
    //         }
            
    //     }
    //     for(int i = 0; i < inMatrix.length; i++){
    //         for(int j = 0; j < inMatrix[i].length; j++){
    //             Node currentNode = nodeList.get(inMatrix.length*i + j);
    //             if (i>0){
    //                 Node nextNode = nodeList.get(inMatrix.length*(i-1) + j);
    //                 currentNode.adjacentNodes.put(nextNode,inMatrix[i-1][j]);
    //             }
    //             if (j>0){
    //                 Node nextNode = nodeList.get(inMatrix.length*i + j-1);
    //                 currentNode.adjacentNodes.put(nextNode,inMatrix[i][j-1]);
    //             }
    //             if (i<inMatrix.length-1){
    //                 Node nextNode = nodeList.get(inMatrix.length*(i+1) + j);
    //                 currentNode.adjacentNodes.put(nextNode,inMatrix[i+1][j]);
    //             }
    //             if (j<inMatrix[0].length-1){
    //                 Node nextNode = nodeList.get(inMatrix.length*i + j+1);
    //                 currentNode.adjacentNodes.put(nextNode,inMatrix[i][j+1]);
    //             }
    //         }
    //     }
    //     return nodeList;
    // }

    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;
 
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
 
        return min_index;
    }
 
    // A utility function to print the constructed distance array
    void printSolution(int dist[])
    {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }
 
    // Function that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    void dijkstra(int graph[][], int src)
    {
        this.V=graph.length*graph[0].length;
        this.V=9;
        int dist[] = new int[V]; // The output array. dist[i] will hold
        // the shortest distance from src to i
 
        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];
 
        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
 
        // Distance of source vertex from itself is always 0
        dist[src] = 0;
 
        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);
 
            // Mark the picked vertex as processed
            sptSet[u] = true;
 
            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)
 
                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }
 
        // print the constructed distance array
        printSolution(dist);
    }
 
    // Driver method
    public static void main(String[] args)
    {
        String filename="day15test.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> arrayField = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (!line.isEmpty()) arrayField.add(line.split(""));
            }
            int[][] field = new int[arrayField.size()][arrayField.get(0).length];
            for (int i=0; i<arrayField.size(); i++){
                for (int j=0; j<arrayField.get(0).length; j++){
                    field[i][j] = Integer.parseInt(arrayField.get(i)[j]);
                }
            }
            ShortestPath t = new ShortestPath();
            t.dijkstra(field, 0);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        int graph[][] = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                                      { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                                      { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                                      { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                                      { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                                      { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
                                      { 0, 0, 0, 0, 0, 2, 0, 1, 6 },
                                      { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                                      { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        // ShortestPath t = new ShortestPath();
        // t.dijkstra(graph, 0);
    }
}
