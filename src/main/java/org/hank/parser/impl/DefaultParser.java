package org.hank.parser.impl;

import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.hank.parser.FieldParser;

import java.util.List;

public class DefaultParser implements FieldParser {

    @Override
    public List<Integer> parse(String fieldExpression, FieldType fieldType) throws InvalidCronFieldException {
        try {
            int value = Integer.parseInt(fieldExpression);

            // Validate that the value is within the allowed range
            if (value < fieldType.getMinValue() || value > fieldType.getMaxValue()) {
                throw new InvalidCronFieldException("Number " + fieldExpression + " for " + fieldType +
                        " is outside valid range (" + fieldType.getMinValue() +
                        "-" + fieldType.getMaxValue() + ")");
            }
            return List.of(value);  // Add the valid single value to the set
        } catch (NumberFormatException nfe) {
            throw new InvalidCronFieldException("Invalid number '" + fieldExpression + "' in field " + fieldType);
        }
    }
}
