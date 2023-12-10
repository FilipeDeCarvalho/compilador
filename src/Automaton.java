
// Classe Automaton
public class Automaton {
    private Node startNode;
    private Node currentNode;

    public Automaton() {
        this.startNode = AutomatonBuilder.build(); // Implemente este método para construir o estado inicial
        this.currentNode = this.startNode;
    }

    public void reset() {
        this.currentNode = this.startNode;
    }

    public Node feed(char token, int scope) {
        // Implemente o método defineNextNode na classe Node
        return this.currentNode.defineNextNode(token, scope);
    }

    public boolean isCurrentNodeTheStartNode() {
        return this.currentNode == this.startNode;
    }

    public Node getCurrentNode() {
        return this.currentNode;
    }

    public void setCurrentNode(Node node) {
        this.currentNode = node;
    }

    // Implementação da classe Node e AutomatonBuilder.build() necessária
}

// Integração na classe LexicalAnalyzer
public class LexicalAnalyzer {
    // ... Restante da classe ...

    public void nextToken(char token, int scope, boolean lineEnd) {
        Node nextNode = this.automaton.feed(token, scope);

        if (nextNode == null) {
            if (this.automaton.getCurrentNode().isAcceptance() && this.currentLexicalSubject.isAcceptedBeforeTruncate) {
                this.currentLexicalSubject.isAcceptedAfterTruncate = true;
                lexicalStack.push(this.currentLexicalSubject);
                this.resetResponse();
            } else {
                // Implemente ErrorManager e seu método throwError
                ErrorManager.throwError("O Lexeme " + this.currentLexicalSubject.lexeme + " foi interrompido antes do estado de aceitação");
            }

            this.automaton.reset();
            Node newLexemeNode = this.automaton.feed(token, scope);

            if (newLexemeNode == null) {
                if (token != ' ')
                    ErrorManager.throwError("O token " + token + " é invalido");

                this.automaton.reset();
            } else {
                this.feedSubject(token, newLexemeNode.isAcceptance(), newLexemeNode.getStateType());
                this.automaton.setCurrentNode(newLexemeNode);
            }

        } else {
            this.feedSubject(token, nextNode.isAcceptance(), nextNode.getStateType());
            this.automaton.setCurrentNode(nextNode);
        }

        // Resto da implementação
    }

    // Resto da classe ...
}


