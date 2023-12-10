import java.util.Stack;

public class StaticChecker {

    public static class LexicalResponse {
        public String lexeme;
        public String fullLexeme;
        public int lengthBeforeTruncate;
        public int lengthAfterTruncate;
        public boolean isAcceptedBeforeTruncate;
        public boolean isAcceptedAfterTruncate;
        public String lexemeType;
    }

    public class LexicalAnalyzer {
        private Automaton automaton = new Automaton();
        private LexicalResponse currentLexicalSubject = new LexicalResponse();
        private Stack<LexicalResponse> lexicalStack = new Stack<>();

        public LexicalAnalyzer() {
            this.resetResponse();
        }

        private void resetResponse() {
            this.currentLexicalSubject.lengthBeforeTruncate = 0;
            this.currentLexicalSubject.lengthAfterTruncate = 0;
            this.currentLexicalSubject.isAcceptedBeforeTruncate = false;
            this.currentLexicalSubject.isAcceptedAfterTruncate = false;
            this.currentLexicalSubject.lexeme = "";
            this.currentLexicalSubject.fullLexeme = "";
            this.currentLexicalSubject.lexemeType = "";
        }

        public Stack<LexicalResponse> getLexicalStack() {
            return this.lexicalStack;
        }

        public LexicalResponse popFromLexicalStack() {
            return lexicalStack.pop();
        }

        public void feedSubject(char token, boolean acceptance, String nodeType) {
            if (this.currentLexicalSubject.lexeme.length() < 30 || (!nodeType.equals("cons-cadeia") && !nodeType.equals("cons-real") && !nodeType.equals("cons-inteiro"))) {
                this.currentLexicalSubject.lexeme += token;
                this.currentLexicalSubject.lengthBeforeTruncate++;
                this.currentLexicalSubject.isAcceptedBeforeTruncate = acceptance;
            }
            this.currentLexicalSubject.fullLexeme += token;
            this.currentLexicalSubject.lengthAfterTruncate++;
            this.currentLexicalSubject.lexemeType = nodeType;
        }

        public void nextToken(char token, int scope, boolean lineEnd) {
            Node nextNode = this.automaton.feed(token, scope);

            if (nextNode == null) {
                if (this.automaton.isCurrentNodeAcceptance() && this.currentLexicalSubject.isAcceptedBeforeTruncate) {
                    this.currentLexicalSubject.isAcceptedAfterTruncate = true;
                    lexicalStack.push(this.currentLexicalSubject);
                    this.resetResponse();
                } else {
                    // Implemente ErrorManager e seu método throwError
                    // ErrorManager.throwError("O Lexeme " + this.currentLexicalSubject.lexeme + " foi interrompido antes do estado de aceitação");
                }

                this.automaton.reset();
                Node newLexemeNode = this.automaton.feed(token, scope);

                if (newLexemeNode == null) {
                    if (token != ' ')
                        // ErrorManager.throwError("O token " + token + " é invalido");

                        this.automaton.reset();
                } else {
                    this.feedSubject(token, newLexemeNode.isAcceptance(), newLexemeNode.getStateType());
                    this.automaton.setCurrentNode(newLexemeNode);
                }

            } else {
                this.feedSubject(token, nextNode.isAcceptance(), nextNode.getStateType());
                this.automaton.setCurrentNode(nextNode);
            }

            if (lineEnd && this.automaton.isCurrentNodeAcceptance()) {
                this.forceEnd();
            }
        }

        public void forceEnd() {
            if (this.currentLexicalSubject.lexeme.length() > 0 && this.automaton.isCurrentNodeAcceptance()) {
                this.currentLexicalSubject.isAcceptedAfterTruncate = true;
                lexicalStack.push(this.currentLexicalSubject);
                this.resetResponse();
            }
            this.automaton.reset();
        }
    }
}

    // Classes Automaton e Node necessitam ser implementadas em Java

