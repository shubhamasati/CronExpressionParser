package org.hank.service.impl;

import org.hank.enums.FieldType;
import org.hank.exceptions.InvalidCronFieldException;
import org.hank.model.Command;
import org.hank.model.CronExpression;
import org.hank.model.CronField;
import org.hank.service.CronExpressionService;

public class CronExpressionServiceImpl implements CronExpressionService {

    @Override
    public String printCronExpression(CronExpression expression) {
        return String.format("%-14s%s\n%-14s%s\n%-14s%s\n%-14s%s\n%-14s%s\n%-14s%s",
                expression.getMinute().getType().getAlias(), expression.getMinute().getParsedValues(),
                expression.getHour().getType().getAlias(), expression.getHour().getParsedValues(),
                expression.getDayOfMonth().getType().getAlias(), expression.getDayOfMonth().getParsedValues(),
                expression.getMonth().getType().getAlias(), expression.getMonth().getParsedValues(),
                expression.getDayOfWeek().getType().getAlias(), expression.getDayOfWeek().getParsedValues(),
                expression.getCommand().getType().getAlias(), expression.getCommand().getParsedValues());
    }

    @Override
    public CronExpression parseCronExpression(String expression) throws InvalidCronFieldException {
        expression = expression.strip();
        String[] fields = expression.split(" ");

        CronField minute = new CronField(fields[FieldType.MINUTE.getPosition()], FieldType.MINUTE);
        CronField hour = new CronField(fields[FieldType.HOUR.getPosition()], FieldType.HOUR);
        CronField dayOfWeek = new CronField(fields[FieldType.DAY_OF_WEEK.getPosition()], FieldType.DAY_OF_WEEK);
        CronField month = new CronField(fields[FieldType.MONTH.getPosition()], FieldType.MONTH);
        CronField dayOfMonth = new CronField(fields[FieldType.DAY_OF_MONTH.getPosition()], FieldType.DAY_OF_MONTH);
        Command command = new Command(fields[FieldType.COMMAND.getPosition()]);

        return CronExpression.builder()
                .setMinuteField(minute)
                .sethHourField(hour)
                .setDayOfMonthField(dayOfMonth)
                .setMonthField(month)
                .setDayOfWeekField(dayOfWeek)
                .setCommandField(command)
                .build();
    }
}
