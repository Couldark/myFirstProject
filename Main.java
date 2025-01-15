import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Доброго времени суток, введите операцию (например, 2 + 3 или V * II):");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        try {
            String output = calc(input);
            System.out.println("Ответ: " + output);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static String calc(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат ввода.");
        }

        String number1 = parts[0];
        String operator = parts[1];
        String number2 = parts[2];

        boolean isRoman = isRoman(number1) && isRoman(number2);
        boolean isArabic = isArabic(number1) && isArabic(number2);

        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Числа должны быть либо оба арабскими, либо оба римскими.");
        }

        int a = isRoman ? romanToInt(number1) : Integer.parseInt(number1);
        int b = isRoman ? romanToInt(number2) : Integer.parseInt(number2);

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10 включительно.");
        }

        int result = switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
        };

        if (isRoman && result < 1) {
            throw new IllegalArgumentException("Результат римских чисел должен быть больше или равен I.");
        }

        return isRoman ? intToRoman(result) : String.valueOf(result);

    }

    private static boolean isRoman(String s) {
        return s.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    private static boolean isArabic(String s) {
        return s.matches("\\d+");
    }

    private static int romanToInt(String s) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && romanMap.get(s.charAt(i)) > romanMap.get(s.charAt(i - 1))) {
                result += romanMap.get(s.charAt(i)) - 2 * romanMap.get(s.charAt(i - 1));
            } else {
                result += romanMap.get(s.charAt(i));
            }
        }
        return result;
    }

    private static String intToRoman(int num) {
        String[] romanNumerals = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                result.append(romanNumerals[i]);
            }
        }
        return result.toString();
    }
}
