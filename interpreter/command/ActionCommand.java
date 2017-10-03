package interpreter.command;

public abstract class ActionCommand extends Command {

    protected ActionCommand(int line) {
        super(line);
    }

    public abstract void execute();

}
