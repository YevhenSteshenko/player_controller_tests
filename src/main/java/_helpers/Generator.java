package _helpers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class Generator {
    @RequiredArgsConstructor
    @Getter
    @Accessors(fluent = true)
    public enum CharType{
        LATIN("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz"),
        CYRILLIC("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя"),
        DIGITS("1234567890"),
        DIACRITICS("âêîôûŵŷäëïöüẅÿáéíóúẃýàèìòùẁỳ"),
        SPEC("!#$%^\";{}<>=*:-.()_`'[]+&№@|/?, "),
        ;

        private final String sequence;
    }

    public static String randomString(int length, String excludes, CharType ... types) {
        int roundedLength = length;
        if (length % types.length != 0){
            roundedLength = (length / types.length + 1) * types.length;
        }

        StringBuilder characters = new StringBuilder();

        for (CharType type: types) {
            String filtered = exclude(type, excludes);
            String generated = getRandomStringFromSequence(filtered, roundedLength / types.length);
            characters.append(generated);
        }
        return characters.substring(0, length);
    }

    private static String exclude(CharType type, String excludes){
        String filtered = type.sequence();
        for (char ch: excludes.toCharArray() ) {
            if (CharType.SPEC.sequence().contains(String.valueOf(ch))){
                filtered = filtered.replaceAll("\\" + ch, "");
            } else {
                filtered = filtered.replaceAll(String.valueOf(ch), "");
            }
        }
        return filtered;
    }

    private static String getRandomStringFromSequence(String sequence, int length){
        Random rng = new Random();
        char[] randomChars = new char[length];
        for (int i = 0; i < length; i++) {
            randomChars[i] = sequence.charAt(rng.nextInt(sequence.length()));
        }
        return String.valueOf(randomChars);
    }

    public static Integer randomInt(Integer from, Integer to) {
        return new Random().nextInt(to - from + 1) + from;
    }
}
