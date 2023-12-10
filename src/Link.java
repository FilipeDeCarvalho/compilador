import java.util.List;

public class Link {
    private int scope;
    private List<Character> tokens;
    private Node nodeOrigin;
    private Node nodeDestination;
    private boolean anything;

    public Link(Node nodeOrigin, Node nodeDestination, List<Character> tokens, int scope, boolean anything) {
        this.nodeOrigin = nodeOrigin;
        this.nodeDestination = nodeDestination;
        this.tokens = tokens;
        this.scope = scope;
        this.anything = anything;
    }

    public boolean checkValid(char token, int scope) {
        return this.scope == scope && tokens.contains(token);
    }

    public boolean checkForAnything() {
        return anything;
    }

    public NodePair getNodes() {
        return new NodePair(nodeOrigin, nodeDestination);
    }

    public Node getDestination() {
        return nodeDestination;
    }

    // Classe NodePair para representar o par de nós (origem e destino)
    public static class NodePair {
        private final Node nodeOrigin;
        private final Node nodeDestination;

        public NodePair(Node nodeOrigin, Node nodeDestination) {
            this.nodeOrigin = nodeOrigin;
            this.nodeDestination = nodeDestination;
        }

        public Node getNodeOrigin() {
            return nodeOrigin;
        }

        public Node getNodeDestination() {
            return nodeDestination;
        }
    }
}
