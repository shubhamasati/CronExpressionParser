package org.hank.parser.impl;

import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.hank.parser.FieldParser;

import java.util.ArrayList;
import java.util.List;

public class RangeParser implements FieldParser {

    @Override
    public List<Integer> parse(String fieldExpression, FieldType fieldType) throws InvalidCronFieldException {
        List<Integer> values = new ArrayList<>();

        String[] fieldArr = fieldExpression.strip().split("-");

        int minVal = getMinVal(fieldArr, fieldType);
        int maxVal = getMaxVal(fieldArr, fieldType);
        if (minVal > maxVal) {
            throw new InvalidCronFieldException("Invalid Range for " + fieldType + ", minValue " + minVal +
                    " is greater than maxValue " + maxVal);
        }
        for (int i = minVal; i <= maxVal; i++) {
            values.add(i);
        }

        return values;
    }

    private static int getMaxVal(String[] fieldArr, FieldType fieldType) throws InvalidCronFieldException {
        try {
            int maxVal = Integer.parseInt(fieldArr[1]);
            if (maxVal < fieldType.getMinValue() || maxVal > fieldType.getMaxValue()) {
                throw new InvalidCronFieldException("Number " + maxVal + " for " + fieldType +
                        " is outside valid range (" + fieldType.getMinValue() + "-" + fieldType.getMaxValue() + ")");
            }
            return maxVal;
        } catch (NumberFormatException nfe) {
            throw new InvalidCronFieldException("Invalid number '" + fieldArr[1]
                    + "' in field " + fieldType);
        }
    }

    private static int getMinVal(String[] fieldArr, FieldType fieldType) throws InvalidCronFieldException {
        try {
            int minVal = Integer.parseInt(fieldArr[0]);
            if (minVal < fieldType.getMinValue() || minVal > fieldType.getMaxValue()) {
                throw new InvalidCronFieldException("Number " + minVal + " for " + fieldType +
                        " is outside valid range (" + fieldType.getMinValue() + "-" + fieldType.getMaxValue() + ")");
            }
            return minVal;
        } catch (NumberFormatException nfe) {
            throw new InvalidCronFieldException("Invalid number '" + fieldArr[0] + "' in field " + fieldType);
        }
    }
}
