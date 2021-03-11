package pt.uc.sob.defektor.common.com;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PluginHelp implements Serializable {
    private String help;

    public PluginHelp setHelp(String help) {
        this.help = help;
        return this;
    }
}
