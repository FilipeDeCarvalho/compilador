import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Link> links = new ArrayList<>();
    private boolean acceptanceState;
    private String stateType;

    public Node(boolean acceptanceState, String stateType) {
        this.acceptanceState = acceptanceState;
        this.stateType = stateType;
    }

    public void addLink(Node nodeDestination, List<Character> tokens, int scope) {
        this.links.add(new Link(this, nodeDestination, tokens, scope, false));
    }

    public void addLink(Node nodeDestination, List<Character> tokens, int scope, boolean anything) {
        this.links.add(new Link(this, nodeDestination, tokens, scope, anything));
    }

    public Node defineNextNode(char nextToken, int nextScope) {
        for (Link link : this.links) {
            if (link.checkValid(nextToken, nextScope)) {
                return link.getDestination();
            }
        }

        for (Link link : this.links) {
            if (link.checkForAnything()) {
                return link.getDestination();
            }
        }

        return null;
    }

    public boolean isAcceptance() {
        return this.acceptanceState;
    }

    public String getStateType() {
        return this.stateType;
    }

    public boolean isComment() {
        String state = this.getStateType();
        return "comment".equals(state) || "line-comment".equals(state) || "commentFinish".equals(state);
    }

    // Implementação da classe Link necessária
}

// Classe Link precisa ser implementada. Ela deve ter um construtor e os métodos checkValid(), getDestination(), e checkForAnything().
