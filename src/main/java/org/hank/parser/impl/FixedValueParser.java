package org.hank.parser.impl;

import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.hank.parser.FieldParser;

import java.util.ArrayList;
import java.util.List;

public class FixedValueParser implements FieldParser {
    @Override
    public List<Integer> parse(String fieldExpression, FieldType fieldType) throws InvalidCronFieldException {
        List<Integer> values = new ArrayList<>();

        String[] fieldArr = fieldExpression.strip().split(",");

        for (String valStr: fieldArr) {
            try {
                int val = Integer.parseInt(valStr);
                if (val < fieldType.getMinValue() || val > fieldType.getMaxValue()) {
                    throw new InvalidCronFieldException("Number " + val + " for " + fieldType +
                            " is outside valid range (" + fieldType.getMinValue() + "-" + fieldType.getMaxValue() + ")");
                }
                values.add(val);
            } catch (NumberFormatException nfe) {
                throw new InvalidCronFieldException("Invalid number '" + fieldExpression
                        + "' in field " + fieldType);
            }
        }
        return values;
    }
}
