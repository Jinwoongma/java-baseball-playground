package study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorTest {
    StringCalculator stringCalculator;

    String formula = null;
    Double answer = null;

    @BeforeEach
    void setup() {
        stringCalculator = new StringCalculator();
        formula = "2 + 3 * 4 / 2";
        answer = 10.0;
    }

    @Test
    @DisplayName("문자열이 공백을 기준으로 split 되는지 확인")
    void checkSplit() {
        String[] actual = stringCalculator.splitFormula(formula);
        assertThat(actual).containsExactly("2", "+", "3", "*", "4", "/", "2");
    }

    @ParameterizedTest
    @CsvSource(value = {"10:10.0", "0:0.0", "2.55:2.55"}, delimiter = ':')
    @DisplayName("문자로된 숫자가 Double 형으로 변환되는지 확인")
    void checkParseDouble(String actual, Double expected) {
        assertThat(stringCalculator.parseDouble(actual)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1.0:x:1.0"}, delimiter = ':')
    @DisplayName("연산자에 잘못된 값이 들어갔을 때, 예외처리 확인")
    void checkOperatorException(Double first, String second, Double third) {
        assertThatThrownBy(() -> {
            stringCalculator.calculate(first, second, third);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("잘못된 수식입니다."));
    }

    @ParameterizedTest
    @CsvSource(value = {"1.0,+,2.0,3.0", "1.5,-,0.75,0.75", "2.5,*,3,7.5", "10.0,/,5.0,2.0"})
    @DisplayName("제대로 된 값이 입력되었을 때, 연산이 제대로 되는지 확인")
    void checkOperator(Double first, String second, Double third, Double expected) {
        assertThat(stringCalculator.calculate(first, second, third)).isEqualTo(expected);
    }

    @Test
    @DisplayName("제대로 된 문자열 수식을 입력하였을 떄, 제대로 계산되는지 확인")
    void checkAnswer() {
        Double actual = stringCalculator.calculateString(formula);
        assertThat(actual).isEqualTo(answer);
    }
}
