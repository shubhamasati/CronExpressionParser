package org.hank.model;

import lombok.Getter;
import lombok.Setter;
import org.hank.enums.FieldType;


@Getter
@Setter
public class Command {
    private FieldType type;
    private String command;

    public Command(String command) {
        this.type = FieldType.COMMAND;
        this.command = command;
    }


    public String getParsedValues() {
        return command;
    }
}
