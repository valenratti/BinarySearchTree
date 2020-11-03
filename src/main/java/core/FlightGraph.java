package core;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

public class FlightGraph {
    public static void main(String args[]){
        Graph<String, DefaultWeightedEdge> flight = GraphTypeBuilder.<String, DefaultWeightedEdge>
                undirected().allowingMultipleEdges(false)
                .allowingSelfLoops(false).weighted(true)
                .edgeClass(DefaultWeightedEdge.class)
                .buildGraph();
    }
}
