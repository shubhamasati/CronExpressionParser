package org.hank.model;

import lombok.Getter;

@Getter
public class CronExpression {
    private CronField minute;
    private CronField hour;
    private CronField dayOfMonth;
    private CronField month;
    private CronField dayOfWeek;
    private Command command;

    private CronExpression() {}

    public static CronExpressionBuilder builder() {
        return new CronExpressionBuilder();
    }

    public static class CronExpressionBuilder {
        private CronExpression cronExpression;

        public CronExpressionBuilder() {
            this.cronExpression = new CronExpression();
        }

        public CronExpressionBuilder setMinuteField(CronField field) {
            cronExpression.minute = field;
            return this;
        }

        public CronExpressionBuilder sethHourField(CronField field) {
            cronExpression.hour = field;
            return this;
        }

        public CronExpressionBuilder setDayOfMonthField(CronField field) {
            cronExpression.dayOfMonth = field;
            return this;
        }

        public CronExpressionBuilder setMonthField(CronField field) {
            cronExpression.month = field;
            return this;
        }

        public CronExpressionBuilder setDayOfWeekField(CronField field) {
            cronExpression.dayOfWeek = field;
            return this;
        }

        public CronExpressionBuilder setCommandField(Command field) {
            cronExpression.command = field;
            return this;
        }

        public CronExpression build() {
            return cronExpression;
        }
    }
}
