import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {
    public List<Character> validCharacters = Arrays.asList('_', ' ', '$', '%', '=', '>', '<', '!', '#', '?', ';', ':', ',', '{', '}', '[', ']', '(', ')', '*', '-', '+', '/', '.');

    public boolean isValid(char c) {
        return Character.isLetterOrDigit(c) || validCharacters.contains(c);
    }

    public String filterFromString(String input) {
        return input.chars()
                .mapToObj(i -> (char) i)
                .filter(this::isValid)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
