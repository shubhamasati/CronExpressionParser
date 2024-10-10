package org.hank.parser.impl;

import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.hank.parser.FieldParser;

import java.util.ArrayList;
import java.util.List;

public class IntervalParser implements FieldParser {
    @Override
    public List<Integer> parse(String fieldExpression, FieldType fieldType) throws InvalidCronFieldException {
        List<Integer> values = new ArrayList<>();
        String[] fieldArr = fieldExpression.strip().split("/");

        int interval = 1;
        if (fieldArr.length > 1) {
            try {
                interval = Integer.parseInt(fieldArr[1]);
                if (interval < fieldType.getMinValue() || interval > fieldType.getMaxValue()) {
                    throw new InvalidCronFieldException("Interval value " + interval + " for " + fieldType +
                            " is outside valid range (" + fieldType.getMinValue() + "-" + fieldType.getMaxValue() + ")");
                }
            } catch (NumberFormatException numberFormatException) {
                throw new InvalidCronFieldException("Invalid number '" + fieldExpression
                        + "' in field " + fieldType);
            }
        }
        for (int i = fieldType.getMinValue(); i <= fieldType.getMaxValue(); i += interval) {
            values.add(i);
        }
        return values;
    }
}
