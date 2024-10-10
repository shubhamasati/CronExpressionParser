package org.hank.service.impl;

import lombok.Getter;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.hank.model.Command;
import org.hank.model.CronExpression;
import org.hank.model.CronField;
import org.junit.jupiter.api.*;

class CronExpressionServiceImplTest {

    private CronExpressionServiceImpl underTest;

    public CronExpressionServiceImplTest() {
        underTest = new CronExpressionServiceImpl();
    }

    @DisplayName("Should parse the expression successfully given the valid cron string")
    @Test
    void shouldValidateSuccessfullyWhenGivenValidCronString() throws InvalidCronFieldException {
        String cronString = "*/15 0 1,15 * 1-5 /usr/bin/find";

        String expectedFormattedString = "minute        0 15 30 45\n" +
                "hour          0\n" +
                "dayOfMonth    1 15\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "dayOfWeek     1 2 3 4 5\n" +
                "command       /usr/bin/find";

        CronExpression cronExpression = underTest.parseCronExpression(cronString);

        Assertions.assertThat(cronExpression)
                .isNotNull();

        Assertions.assertThat(cronExpression)
                .hasNoNullFieldsOrProperties();

        Assertions.assertThat(cronExpression.getMinute())
                .isNotNull()
                .isInstanceOf(CronField.class)
                .isNotNull();

        Assertions.assertThat(cronExpression.getHour())
                .isNotNull()
                .isInstanceOf(CronField.class)
                .isNotNull();

        Assertions.assertThat(cronExpression.getMonth())
                .isNotNull()
                .isInstanceOf(CronField.class)
                .isNotNull();

        Assertions.assertThat(cronExpression.getDayOfWeek())
                .isNotNull()
                .isInstanceOf(CronField.class)
                .isNotNull();

        Assertions.assertThat(cronExpression.getDayOfMonth())
                .isNotNull()
                .isInstanceOf(CronField.class)
                .isNotNull();

        Assertions.assertThat(cronExpression.getCommand())
                .isNotNull()
                .isInstanceOf(Command.class)
                .isNotNull();

        Assertions.assertThat(underTest.printCronExpression(cronExpression))
                .isNotNull()
                .isEqualToIgnoringCase(expectedFormattedString);
    }


    @Getter
    @Nested
    @DisplayName("Validation for Minute Field")
    class MinuteFieldTest {
        private final FieldType fieldType = FieldType.MINUTE;

        @DisplayName("Should raise the exception given the invalid minute value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidMinuteValue() {
            int value = 65;
            String cronString = value + " 10 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + value + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid interval value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidIntervalValue() {
            int intervalValue = 65;
            String cronString = "*/" + intervalValue + " */15 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Interval value " + intervalValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValue() {
            int invalidValue = 76;
            String cronString = "0-" + invalidValue + " 0 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value where minValue is greater than maxValue")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValueWhereMinGreaterThanMax() {
            int minVal = 23;
            int maxVal = 10;
            String cronString =  minVal + "-" + maxVal + " 0 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Invalid Range for " + fieldType + ", minValue " + minVal + " is greater than maxValue " + maxVal);
        }

        @DisplayName("Should raise the exception given the invalid Fixed value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidFixedValue() {
            int invalidValue = 98;
            String cronString = "10," + invalidValue + " 10 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }
    }


    @Getter
    @Nested
    @DisplayName("Validation for Hour Field")
    class HourFieldTest {

        private final FieldType fieldType = FieldType.HOUR;

        @DisplayName("Should raise the exception given the invalid hour value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidHourValue() {
            int value = 33;
            String cronString = "45 " + value + " 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + value + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid interval value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidIntervalValue() {
            int intervalValue = 50;
            String cronString = "* */" + intervalValue + " 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Interval value " + intervalValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValue() {
            int invalidValue = 33;
            String cronString = "45 0-" + invalidValue + " 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value where minValue is greater than maxValue")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValueWhereMinGreaterThanMax() {
            int minVal = 23;
            int maxVal = 10;
            String cronString = "45 " + minVal + "-" + maxVal + " 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Invalid Range for " + fieldType + ", minValue " + minVal + " is greater than maxValue " + maxVal);
        }

        @DisplayName("Should raise the exception given the invalid Fixed value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidFixedValue() {
            int invalidValue = 33;
            String cronString = "10 10," + invalidValue + " 1,15 * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

    }

    @Getter
    @Nested
    @DisplayName("Validation for DayOfMonth Field")
    class DayOfMonthFieldTest {

        private final FieldType fieldType = FieldType.DAY_OF_MONTH;

        @DisplayName("Should raise the exception given the invalid Day of Month value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidDayOfMonthValue() {
            int value = 33;
            String cronString = "45 12 " + value + " * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + value + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid interval value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidIntervalValue() {
            int intervalValue = 35;
            String cronString = "* 0 */" + intervalValue + " * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Interval value " + intervalValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValue() {
            int invalidValue = 33;
            String cronString = "45 0 12-" + invalidValue + " * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value where minValue is greater than maxValue")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValueWhereMinGreaterThanMax() {
            int minVal = 23;
            int maxVal = 10;
            String cronString = "45 0 " + minVal + "-" + maxVal + " * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Invalid Range for " + fieldType + ", minValue " + minVal + " is greater than maxValue " + maxVal);
        }

        @DisplayName("Should raise the exception given the invalid Fixed value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidFixedValue() {
            int invalidValue = 33;
            String cronString = "10 0 10," + invalidValue + " * 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }
    }

    @Getter
    @Nested
    @DisplayName("Validation for Month Field")
    class MonthFieldTest {

        private final FieldType fieldType = FieldType.MONTH;

        @DisplayName("Should raise the exception given the invalid Month value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidMonthValue() {
            int value = 13;
            String cronString = "45 12 23 " + value + " 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + value + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid interval value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidIntervalValue() {
            int intervalValue = 15;
            String cronString = "* 0 22 */" + intervalValue + " 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Interval value " + intervalValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValue() {
            int invalidValue = 23;
            String cronString = "45 0 * 1-" + invalidValue + " 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value where minValue is greater than maxValue")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValueWhereMinGreaterThanMax() {
            int minVal = 11;
            int maxVal = 10;
            String cronString = "45 0 * " + minVal + "-" + maxVal + " 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Invalid Range for " + fieldType + ", minValue " + minVal + " is greater than maxValue " + maxVal);
        }

        @DisplayName("Should raise the exception given the invalid Fixed value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidFixedValue() {
            int invalidValue = 13;
            String cronString = "10 0 * 10," + invalidValue + " 1-5 /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }
    }

    @Getter
    @Nested
    @DisplayName("Validation for DayOfWeek Field")
    class DayOfWeekFieldTest {

        private final FieldType fieldType = FieldType.DAY_OF_WEEK;

        @DisplayName("Should raise the exception given the invalid Day of Week value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidDayOfWeekValue() {
            int value = 13;
            String cronString = "45 12 23 1-5 " + value + " /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + value + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid interval value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidIntervalValue() {
            int intervalValue = 15;
            String cronString = "* 0 22 1-5 */" + intervalValue + " /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Interval value " + intervalValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValue() {
            int invalidValue = 23;
            String cronString = "45 0 * 1-5 0-" + invalidValue + " /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }

        @DisplayName("Should raise the exception given the invalid Range value where minValue is greater than maxValue")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidRangeValueWhereMinGreaterThanMax() {
            int minVal = 6;
            int maxVal = 2;
            String cronString = "45 0 * 1-5 " + minVal + "-" + maxVal + " /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Invalid Range for " + fieldType + ", minValue " + minVal + " is greater than maxValue " + maxVal);
        }

        @DisplayName("Should raise the exception given the invalid Fixed value")
        @Test
        void shouldRaiseExceptionWhenGivenInvalidFixedValue() {
            int invalidValue = 13;
            String cronString = "10 0 * 1-5 2," + invalidValue + " /usr/bin/find";

            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert = Assertions.assertThatThrownBy(() -> {
                underTest.parseCronExpression(cronString);
            });

            abstractThrowableAssert
                    .isInstanceOf(InvalidCronFieldException.class)
                    .hasMessage("Number " + invalidValue + " for " + fieldType + " is outside valid range (" + fieldType.getMinValue() +"-" + fieldType.getMaxValue()+")");
        }
    }


}