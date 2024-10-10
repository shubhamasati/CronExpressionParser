package org.hank.parser;

import org.hank.parser.impl.DefaultParser;
import org.hank.parser.impl.FixedValueParser;
import org.hank.parser.impl.IntervalParser;
import org.hank.parser.impl.RangeParser;

public class FieldParserFactory {
    public static FieldParser getParser(String fieldExpression) {
        if (fieldExpression.contains(",")) {
            return new FixedValueParser();
        } else if (fieldExpression.contains("-")) {
            return new RangeParser();
        } else if (fieldExpression.startsWith("*")) {
            return new IntervalParser();
        } else {
            return new DefaultParser();
        }
    }
}
