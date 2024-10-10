package org.hank;

import org.hank.exceptions.InvalidCronFieldException;
import org.hank.model.CronExpression;
import org.hank.service.CronExpressionService;
import org.hank.service.impl.CronExpressionServiceImpl;

public class CronExpressionParserApp {
    public static void main(String[] args) throws InvalidCronFieldException {

        String inputExpression = args[0];

        System.out.println(inputExpression);

        CronExpressionService cronExpressionService
                = new CronExpressionServiceImpl();

        CronExpression cronExpression = cronExpressionService.parseCronExpression(inputExpression);
        String formatedParsedExpression = cronExpressionService.printCronExpression(cronExpression);
        System.out.println(formatedParsedExpression);
    }
}