package org.hank.parser;

import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;

import java.util.List;

public interface FieldParser {
    List<Integer> parse(String fieldExpression, FieldType fieldType) throws InvalidCronFieldException;
}