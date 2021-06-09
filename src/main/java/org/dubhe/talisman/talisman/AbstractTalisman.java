package org.dubhe.talisman.talisman;

public abstract class AbstractTalisman {

    private final String name;

    protected AbstractTalisman(String name) {
        this.name = name;
    }

    public abstract void execute();

    public String getName() {
        return this.name;
    }

}
