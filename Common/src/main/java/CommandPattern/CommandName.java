package CommandPattern;

/**
 * enum, элементы которого представляют собой название команды
 * @author uvuv-643
 * @version 1.0
 */
public enum CommandName {

    ADD("add"),
    ADD_IF_MIN("add_if_min"),
    CLEAR("clear"),
    EXECUTE_SCRIPT("execute_script"),
    EXIT("exit"),
    FILTER_GREATER_THAN_NATIONALITY("filter_greater_than_nationality"),
    HELP("help"),
    INFO("info"),
    PRINT_DESCENDING("print_descending"),
    PRINT_FIELD_DESCENDING_ORDER("print_field_descending_order"),
    REMOVE_AT("remove_at"),
    REMOVE_BY_ID("remove_by_id"),
    REORDER("reorder"),
    SAVE("save"),
    SHOW("show"),
    UPDATE("update"),
    CHECK_ID("check_id"),
    AUTH("auth"),
    REGISTER("register");

    private String commandName;
    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public String toString() {
        return this.getCommandName();
    }

}
