package org.hank.parser.impl;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.junit.jupiter.api.Test;

import java.util.List;

class IntervalParserTest {
    private final IntervalParser underTest;

    public IntervalParserTest() {
        this.underTest = new IntervalParser();
    }

    @Test
    public void testValidValueParsing() throws InvalidCronFieldException {
        List<Integer> parsedData = underTest.parse("*/2", FieldType.HOUR);

        Assertions.assertThat(parsedData)
                .isNotNull()
                .isNotEmpty();

        Assertions.assertThat(parsedData)
                .isEqualTo(List.of(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22));
    }

    @Test
    public void shouldThrowExceptionOnInvalidValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("*/10", FieldType.DAY_OF_WEEK));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Interval value 10 for DAY_OF_WEEK is outside valid range (0-6)");

    }

    @Test
    public void shouldThrowExceptionOnInvalidFormatValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("*/10.0", FieldType.HOUR));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Invalid number '*/10.0' in field HOUR");

    }
}