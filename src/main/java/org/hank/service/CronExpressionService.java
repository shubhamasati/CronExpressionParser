package org.hank.service;

import org.hank.exceptions.InvalidCronFieldException;
import org.hank.model.CronExpression;

public interface CronExpressionService {
    String printCronExpression(CronExpression expression);
    CronExpression parseCronExpression(String expression) throws InvalidCronFieldException;
}
