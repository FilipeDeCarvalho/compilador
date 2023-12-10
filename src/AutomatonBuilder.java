import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AutomatonBuilder {

    public static Node build() {
        Node startNode = new Node(false, "start");

        // Tratamento do espaço em branco
        Node spaceNode = new Node(false, "space");
        startNode.addLink(spaceNode, List.of(' '), 0);
        spaceNode.addLink(spaceNode, List.of(' '), 0);

        // Dígitos e alfabeto
        List<Character> digits = IntStream.rangeClosed('0', '9')
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList());
        List<Character> alphabet = IntStream.rangeClosed('A', 'Z')
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList());
        alphabet.addAll(IntStream.rangeClosed('a', 'z')
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList()));

        List<Character> stringCharacters = new ArrayList<>(alphabet);
        stringCharacters.addAll(digits);
        stringCharacters.addAll(Arrays.asList('$', '_', '.', ' '));

        // Cons-inteiro
        Node consInteiroNode = new Node(true, "cons-inteiro");
        startNode.addLink(consInteiroNode, digits, 0);
        consInteiroNode.addLink(consInteiroNode, digits, 0);

// Cons-real
        Node consRealDotNode = new Node(false, "cons-real");
        consInteiroNode.addLink(consRealDotNode, List.of('.'), 0);

        Node consRealDigitNode = new Node(true, "cons-real");
        consRealDotNode.addLink(consRealDigitNode, digits, 0);
        consRealDigitNode.addLink(consRealDigitNode, digits, 0);

        Node consRealExpNode = new Node(false, "cons-real");
        consRealDigitNode.addLink(consRealExpNode, List.of('e'), 0);

        Node consRealExpSigNode = new Node(false, "cons-real");
        consRealExpNode.addLink(consRealExpSigNode, List.of('-', '+'), 0);

        Node consRealExpDigitNode = new Node(true, "cons-real");
        consRealExpSigNode.addLink(consRealExpDigitNode, digits, 0);
        consRealExpNode.addLink(consRealExpDigitNode, digits, 0);
        consRealExpDigitNode.addLink(consRealExpDigitNode, digits, 0);

// Division
        Node divisionNode = new Node(true, "/");
        startNode.addLink(divisionNode, List.of('/'), 0);

        // Plus
        Node plusSignNode = new Node(true, "+");
        startNode.addLink(plusSignNode, List.of('+'), 0);

// Percent
        Node percentSignNode = new Node(true, "%");
        startNode.addLink(percentSignNode, List.of('%'), 0);

// Open parenthesis
        Node openParenthesisNode = new Node(true, "(");
        startNode.addLink(openParenthesisNode, List.of('('), 0);

// Closed parenthesis
        Node closedParenthesisNode = new Node(true, ")");
        startNode.addLink(closedParenthesisNode, List.of(')'), 0);

// Comma
        Node commaNode = new Node(true, ",");
        startNode.addLink(commaNode, List.of(','), 0);

// Double dots
        Node doubleDotsNode = new Node(true, ":");
        startNode.addLink(doubleDotsNode, List.of(':'), 0);

// Double dots equal
        Node doubleDotsEqualNode = new Node(true, ":=");
        doubleDotsNode.addLink(doubleDotsEqualNode, List.of('='), 0);

// Dot-comma
        Node dotCommaNode = new Node(true, ";");
        startNode.addLink(dotCommaNode, List.of(';'), 0);

// Interrogation
        Node interrogationNode = new Node(true, "?");
        startNode.addLink(interrogationNode, List.of('?'), 0);

// Open bracket
        Node openBracketNode = new Node(true, "[");
        startNode.addLink(openBracketNode, List.of('['), 0);

// Closed bracket
        Node closedBracketNode = new Node(true, "]");
        startNode.addLink(closedBracketNode, List.of(']'), 0);

// Open curly braces
        Node openCurlyBraces = new Node(true, "{");
        startNode.addLink(openCurlyBraces, List.of('{'), 0);

// Closed curly braces
        Node closedCurlyBraces = new Node(true, "}");
        startNode.addLink(closedCurlyBraces, List.of('}'), 0);

// Minus
        Node minusNode = new Node(true, "-");
        startNode.addLink(minusNode, List.of('-'), 0);

// Times
        Node timesNode = new Node(true, "*");
        startNode.addLink(timesNode, List.of('*'), 0);

// Exclamation
        Node exclamationNode = new Node(false, "!");
        startNode.addLink(exclamationNode, List.of('!'), 0);

// Different
        Node differentNode = new Node(true, "!=");
        exclamationNode.addLink(differentNode, List.of('='), 0);

// Hashtag
        Node hashtagNode = new Node(true, "#");
        startNode.addLink(hashtagNode, List.of('#'), 0);

// Less-than
        Node lessThanNode = new Node(true, "<");
        startNode.addLink(lessThanNode, List.of('<'), 0);

// Less-than-or-equal-to
        Node lessThanOfEqualToNode = new Node(true, "<=");
        lessThanNode.addLink(lessThanOfEqualToNode, List.of('='), 0);

// Equal
        Node equalNode = new Node(false, "=");
        startNode.addLink(equalNode, List.of('='), 0);

// Equal-equal
        Node equalEqualNode = new Node(true, "==");
        equalNode.addLink(equalEqualNode, List.of('='), 0);

// More-than
        Node moreThanNode = new Node(true, ">");
        startNode.addLink(moreThanNode, List.of('>'), 0);

// More-than-or-equal-to
        Node moreThanOfEqualToNode = new Node(true, ">=");
        moreThanNode.addLink(moreThanOfEqualToNode, List.of('='), 0);

// Block comment
        Node commentNode = new Node(false, "comment");
        divisionNode.addLink(commentNode, List.of('*'), 0);
        Node anythingNode = new Node(false, "comment");
        commentNode.addLink(anythingNode, List.of(), 0, true);
        anythingNode.addLink(anythingNode, List.of(), 0, true);
        Node possibleCloseComment = new Node(false, "comment");
        anythingNode.addLink(possibleCloseComment, List.of('*'), 0);
        Node closeComment = new Node(false, "comment-finish");
        possibleCloseComment.addLink(closeComment, List.of('/'), 0);
        possibleCloseComment.addLink(anythingNode, List.of(), 0, true);

// Line comment
        Node lineCommentNode = new Node(false, "line-comment");
        divisionNode.addLink(lineCommentNode, List.of('/'), 0);
        lineCommentNode.addLink(lineCommentNode, List.of(), 0, true);

// Cons-cadeia
        Node startStringNode = new Node(false, "cons-cadeia");
        startNode.addLink(startStringNode, List.of('"'), 0);
        Node stringContentNode = new Node(false, "cons-cadeia");
        startStringNode.addLink(stringContentNode, stringCharacters, 0);
        stringContentNode.addLink(stringContentNode, stringCharacters, 0);
        Node endStringNode = new Node(true, "cons-cadeia");
        startStringNode.addLink(endStringNode, List.of('"'), 0);
        stringContentNode.addLink(endStringNode, List.of('"'), 0);

// Cons-caracter
        Node startCharNode = new Node(false, "cons-caracter");
        startNode.addLink(startCharNode, List.of('\''), 0);
        Node contentCharNode = new Node(false, "cons-caracter");
        startCharNode.addLink(contentCharNode, alphabet, 0);
        Node endCharNode = new Node(true, "cons-caracter");
        startCharNode.addLink(endCharNode, List.of('\''), 0);
        contentCharNode.addLink(endCharNode, List.of('\''), 0);

        List<Character> variableChars = new ArrayList<>(alphabet);
        variableChars.add('_');
        Node variableNode = new Node(true, "variavel");
        startNode.addLink(variableNode, variableChars, 0);
        variableNode.addLink(variableNode, Stream.concat(alphabet.stream(), Stream.concat(digits.stream(), Stream.of('_'))).collect(Collectors.toList()), 0);

        // Palavra-reservada
        Node reservedWordNode = new Node(false, "palavra-reservada");
        variableNode.addLink(reservedWordNode, List.of('-'), 0);
        Node reservedWordPostDashNode = new Node(true, "palavra-reservada");
        reservedWordNode.addLink(reservedWordPostDashNode, alphabet, 0);
        reservedWordPostDashNode.addLink(reservedWordPostDashNode, alphabet, 0);

        // Nom-funcao
        Node funcNode = new Node(true, "nom-funcao");
        startNode.addLink(funcNode, alphabet, 1);
        funcNode.addLink(funcNode, Stream.concat(alphabet.stream(), digits.stream()).collect(Collectors.toList()), 1);

        // Nom-programa
        Node progNode = new Node(true, "nom-programa");
        startNode.addLink(progNode, alphabet, 2);
        progNode.addLink(progNode, Stream.concat(alphabet.stream(), digits.stream()).collect(Collectors.toList()), 2);




        return startNode;
    }
}


