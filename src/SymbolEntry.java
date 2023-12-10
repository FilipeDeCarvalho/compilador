import java.util.ArrayList;
import java.util.List;

public class SymbolEntry {
    private final int entryNumber;
    private final int code;
    private final String lexeme;
    private final int lengthBeforeTruncate;
    private final int lengthAfterTruncate;
    private final String lexemeType;
    private final List<Integer> lines = new ArrayList<>();

    public SymbolEntry(int entryNumber, int code, String lexeme, int lengthBeforeTruncate, int lengthAfterTruncate, String lexemeType) {
        this.entryNumber = entryNumber;
        this.code = code;
        this.lexeme = lexeme;
        this.lengthBeforeTruncate = lengthBeforeTruncate;
        this.lengthAfterTruncate = lengthAfterTruncate;
        this.lexemeType = lexemeType;
    }

    public void pushLine(int line) {
        lines.add(line);
    }

    public int getEntryNumber() {
        return entryNumber;
    }

    public int getCode() {
        return code;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLengthBeforeTruncate() {
        return lengthBeforeTruncate;
    }

    public int getLengthAfterTruncate() {
        return lengthAfterTruncate;
    }

    public String getLexemeType() {
        return lexemeType;
    }

    public List<Integer> getLines() {
        return lines;
    }
}
