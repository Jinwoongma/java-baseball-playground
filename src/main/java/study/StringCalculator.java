package study;

import java.lang.reflect.Array;
import java.util.Arrays;

public class StringCalculator {

    public String[] splitFormula(String str) {
        return str.split(" ");
    }

    public Double parseDouble(String str) {
        return Double.parseDouble(str);
    }

    public Double calculate(Double first, String second, Double third) {
        if (second.equals("+"))
            return first + third;
        if (second.equals("-"))
            return first - third;
        if (second.equals("*"))
            return first * third;
        if (second.equals("/"))
            return first / third;

        throw new IllegalArgumentException("잘못된 수식입니다.");
    }

    public Double calculateString(String formula) {
        String[] lst = splitFormula(formula);
        Double ret = parseDouble(lst[0]);
        for (int i = 1; i < lst.length - 1; i = i + 2) {
            ret = calculate(ret, lst[i], parseDouble(lst[i + 1]));
        }
        return ret;
    }

}
