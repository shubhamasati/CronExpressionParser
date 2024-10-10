package org.hank.parser.impl;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.junit.jupiter.api.Test;

import java.util.List;

class FixedValueParserTest {

    private final FixedValueParser underTest;

    public FixedValueParserTest() {
        this.underTest = new FixedValueParser();
    }

    @Test
    public void testValidValueParsing() throws InvalidCronFieldException {
        List<Integer> parsedData = underTest.parse("12,10,9", FieldType.HOUR);

        Assertions.assertThat(parsedData)
                .isNotNull()
                .isNotEmpty();

        Assertions.assertThat(parsedData)
                .isEqualTo(List.of(12, 10, 9));
    }

    @Test
    public void shouldThrowExceptionOnInvalidValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("24,10", FieldType.HOUR));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Number 24 for HOUR is outside valid range (0-23)");

    }

    @Test
    public void shouldThrowExceptionOnInvalidFormatValue() {
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                Assertions.assertThatThrownBy(() -> underTest.parse("24.0,10", FieldType.HOUR));

        abstractThrowableAssert
                .isInstanceOf(InvalidCronFieldException.class)
                .hasMessage("Invalid number '24.0,10' in field HOUR");

    }

}