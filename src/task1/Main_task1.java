package task1;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main_task1 {
    private static final int WORDS_IN_TEXT = 25;

    public static void main(String[] args) {
        printLine("\n\tЗадача 1. Генератор словаря\n", System.out);

        final String text = getText(WORDS_IN_TEXT);
        final Function<String, Collection<String>> dictionarist = s ->
                Stream.of(s.replaceAll("[\r\t\n]", " ").split(" "))
                        .collect(Collectors.toCollection(TreeSet::new));
        final Collection<String> dictionary = getDictionary(text, dictionarist);
        printLine(dictionary, System.out);
    }

//    private static String getText(int wordsInText) {
//        final int MIN_LENGTH = 3;
//        final int MAX_LENGTH = 10;
//        final int FIRST_CHAR = 350;
//        final int LAST_CHAR = 400;
//        final String SEPARATOR = " ";
//        final StringBuilder sb = new StringBuilder();
//        final Random rnd = new Random();
//        for (int i = 0; i < wordsInText; i++) {
//            final int length = rnd.nextInt(MAX_LENGTH + 1 - MIN_LENGTH) + MIN_LENGTH;
//            for (int j = 0; j < length; j++) {
//                sb.append((char) (rnd.nextInt(LAST_CHAR + 1 - FIRST_CHAR) + FIRST_CHAR));
//            }
//            sb.append(SEPARATOR);
//        }
//        sb.deleteCharAt(sb.lastIndexOf(SEPARATOR));
//        return sb.toString();
//    }

    private static String getText(int wordsInText) {
        final int MIN_LENGTH = 3;
        final int MAX_LENGTH = 10;
        final int FIRST_CHAR = 350;
        final int LAST_CHAR = 400;
        final String SEPARATOR = " ";
        final StringBuilder sb = new StringBuilder();
        final Random rnd = new Random();
        if (sb.toString().split(SEPARATOR).length <= wordsInText) {
            final int length = rnd.nextInt(MAX_LENGTH + 1 - MIN_LENGTH) + MIN_LENGTH;
            for (int j = 0; j < length; j++) {
                sb.append((char) (rnd.nextInt(LAST_CHAR + 1 - FIRST_CHAR) + FIRST_CHAR));
            }
            String[] array = sb.toString().split(SEPARATOR);
            sb
                    .append(SEPARATOR)
                    .append(getText(wordsInText - array.length));
        }
        return sb.toString();
    }

    private static Collection<String> getDictionary(String text, Function<String, Collection<String>> function) {
        return function.apply(text);
    }

    private static void printLine(Object source, PrintStream out) {
        if (source instanceof String) {
            out.println(source);
        } else if (source instanceof Collection) {
            ((Collection) source).forEach(out::println);
        } else Set.of(source).forEach(out::println);
    }
}
