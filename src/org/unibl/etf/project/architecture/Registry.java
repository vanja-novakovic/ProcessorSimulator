package org.unibl.etf.project.architecture;

public class Registry {
    public String name;
    private long value = 0;
    public Registry (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
