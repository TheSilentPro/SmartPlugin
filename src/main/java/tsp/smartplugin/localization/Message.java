package tsp.smartplugin.localization;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class Message {

    private String text = "";
    private Collection<UUID> receivers = new HashSet<>();
    private UnaryOperator<String> function = null;
    private String[] args = null;
    private boolean sendToConsole = false;

    public Message text(String text) {
        this.text = text;
        return this;
    }

    public Message receivers(Collection<UUID> receivers) {
        this.receivers = receivers;
        return this;
    }

    public Message receivers(UUID... receivers) {
        return receivers(Arrays.asList(receivers));
    }

    public Message receiver(UUID receiver) {
        this.receivers.add(receiver);
        return this;
    }

    public Message function(UnaryOperator<String> function) {
        this.function = function;
        return this;
    }

    public Message args(String... args) {
        this.args = args;
        return this;
    }

    public Message includeConsole(boolean include) {
        this.sendToConsole = include;
        return this;
    }

    public Message includeConsole() {
        return includeConsole(true);
    }

    @Nonnull
    public String getText() {
        return text;
    }

    @Nonnull
    public Collection<UUID> getReceivers() {
        return receivers;
    }

    @Nullable
    public UnaryOperator<String> getFunction() {
        return function;
    }

    @Nullable
    public String[] getArgs() {
        return args;
    }

    public boolean shouldSendToConsole() {
        return sendToConsole;
    }

}
