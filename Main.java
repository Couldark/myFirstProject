import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Доброго времени суток! Введите ваше математическое выражение, соблюдая условия задачи");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(calc(input));
    }
    public static String calc(String input) throws IOException {
        Converter converter = new Converter();
        String answer = "";
        char[] charInput = input.toCharArray();
        int firstNumber;
        int secondNumber;
        char currentSign = ' ';
        int isCorrectSign = 0;
        String[] component = input.split(" ");

        if(component.length > 3 || component.length <= 1)
            throw new IOException("Это не математическая операция!");

        for (char c : charInput) {
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                currentSign = c;
                isCorrectSign++;
            }
        }
        if(isCorrectSign != 1){
            throw new IOException("Неверный формат математической операции!");
        }
        switch (currentSign) {
            case ('+') -> {
                component = input.split(" \\+ ");
            }
            case ('-') -> {
                component = input.split(" - ");
            }
            case ('*') -> {
                component = input.split(" \\* ");
            }
            case ('/') -> {
                component = input.split(" / ");
            }
        }
        if(!converter.isRoman(component[0]) && !converter.isRoman(component[1])){
            firstNumber = Integer.parseInt(component[0]);
            secondNumber = Integer.parseInt(component[1]);
            answer = Integer.toString(converter.calcInArabic(firstNumber, secondNumber, currentSign));

        } else if (converter.isRoman(component[0]) && converter.isRoman(component[1])) {
            firstNumber = converter.romanToArabic(component[0]);
            secondNumber = converter.romanToArabic(component[1]);

            int result = converter.calcInArabic(firstNumber, secondNumber, currentSign);

            if (result > 0)
                return converter.arabicToRoman(result);

            throw new ArithmeticException("В римской системе нет отрицательных чисел!");

        } else {
            throw new IOException("В одном запросе используются цифры из разных математических систем");
        }
        return answer;
    }
}
class Converter{
    final private HashMap<Character, Integer> romanMap = new HashMap<>();

    public Converter(){
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
    }
    public boolean inArrange(int currentNumber){
        return 1 <= currentNumber && currentNumber <= 10;
    }
    public int calcInArabic(int firstNumber, int secondNumber, char currentSign) throws IOException {
        int answer = -1;
        if (!inArrange(firstNumber) || !inArrange(secondNumber)){
            throw new IOException("Числа не находятся в диапазоне от 1 до 10!");
        }
        switch (currentSign) {
            case ('+') -> {
                answer = firstNumber + secondNumber;
            }
            case ('-') -> {
                answer = firstNumber - secondNumber;
            }
            case ('*') -> {
                answer = firstNumber * secondNumber;
            }
            case ('/') -> {
                answer = firstNumber / secondNumber;
            }
            default -> throw new IllegalArgumentException("Неверный знак операции");
        }

        return answer;
    }
    public boolean isRoman(String number){
        return romanMap.containsKey(number.charAt(0));
    }

    public int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }
    public String arabicToRoman(int arabic){
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < arabicValues.length; i++) {
            while (arabic >= arabicValues[i]) {
                roman.append(romanSymbols[i]);
                arabic -= arabicValues[i];
            }
        }

        return roman.toString();
    }
}