package org.unibl.etf.project.architecture;
import java.util.ArrayList;

public class Memory {
    private static long nextAddress = 1;
    public static ArrayList<Memory> takenMemoryLocations = new ArrayList<>();
    public long address;
    public long value;
    public String identifier;

    public Memory (String identifier) {
        this.identifier = identifier;
        this.value = 0;
        this.address = nextAddress++;
        takenMemoryLocations.add(this);
    }
    public Memory (String identifier, long value) {
        this.identifier = identifier;
        this.value = value;
        this.address = nextAddress++;
        takenMemoryLocations.add(this);
    }
    public Memory (long value) {
        this.value = value;
        this.address = 0;
    }
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public static boolean isMemoryLocation (String identifier) {
        for (Memory m : takenMemoryLocations) {
            if (m.identifier.equals(identifier))
                return true;
        }
        return false;
    }
    public static Memory getMemoryLocation (String identifier) {
        for (Memory m : takenMemoryLocations) {
            if (m.identifier.equals(identifier))
                return m;
        }
        return null;
    }
    public static void updateMemoryLocation (String identifier, long value) {
        for (Memory m : takenMemoryLocations) {
            if (m.identifier.equals(identifier))
                m.value = value;
        }
    }
    public static void showAllMemoryLocations () {
        System.out.println("Memory: ");
        for (Memory m : takenMemoryLocations) {
            System.out.println(m.address + " " + m.identifier + " " + m.value);
        }
    }


}
