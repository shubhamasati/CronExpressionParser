package org.hank.model;

import lombok.Getter;
import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.hank.parser.FieldParser;
import org.hank.parser.FieldParserFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CronField {
    private final FieldType type;
    private final String fieldExpression;
    private final List<Integer> values;

    public CronField(String fieldExpression, FieldType type) throws InvalidCronFieldException {
        this.type = type;
        this.fieldExpression = fieldExpression;
        this.values = parseField();
    }

    private List<Integer> parseField() throws InvalidCronFieldException {
        FieldParser parser = FieldParserFactory.getParser(fieldExpression);
        return parser.parse(fieldExpression, type);
    }

    public String getParsedValues() {
        return values.stream().map(Object::toString).collect(Collectors.joining(" "));
    }
}
