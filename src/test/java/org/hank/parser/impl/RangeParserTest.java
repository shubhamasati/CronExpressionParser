package org.hank.parser.impl;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.junit.jupiter.api.Test;

import java.util.List;

class RangeParserTest {
    private final RangeParser underTest;

    public RangeParserTest() {
        this.underTest = new RangeParser();
    }

    @Test
    public void testValidValueParsing() throws InvalidCronFieldException {
        List<Integer> parsedData = underTest.parse("2-8", FieldType.HOUR);

        Assertions.assertThat(parsedData)
                .isNotNull()
                .isNotEmpty();

        Assertions.assertThat(parsedData)
                .isEqualTo(List.of(2, 3, 4, 5, 6, 7, 8));
    }

    @Test
    public void shouldThrowExceptionOnInvalidMinValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("10-11", FieldType.DAY_OF_WEEK));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Number 10 for DAY_OF_WEEK is outside valid range (0-6)");

    }

    @Test
    public void shouldThrowExceptionOnInvalidMaxValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("2-10", FieldType.DAY_OF_WEEK));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Number 10 for DAY_OF_WEEK is outside valid range (0-6)");

    }

    @Test
    public void shouldThrowExceptionOnMinValInvalidFormatValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("2.0-10", FieldType.HOUR));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Invalid number '2.0' in field HOUR");

    }

    @Test
    public void shouldThrowExceptionOnMaxValInvalidFormatValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("2-10.0", FieldType.HOUR));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Invalid number '10.0' in field HOUR");

    }
}