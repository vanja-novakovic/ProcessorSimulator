package org.unibl.etf.project.architecture;
public class Processor {
    public static Registry R1 = new Registry("R1");
    public static Registry R2 = new Registry("R2");
    public static Registry R3 = new Registry("R3");
    public static Registry R4 = new Registry("R4");

    private static boolean logicValue = false;
    public static boolean jumpValue = false;

    public static void load (Registry r1, Registry r2) {
        r1.setValue(r2.getValue());
    }
    public static void load (Registry r, Memory m) {
        r.setValue(m.getValue());
    }
    public static void store (Memory m, Registry r) {
        m.setValue(r.getValue());
    }
    public static void add (Registry r1, Registry r2) {
        r1.setValue(r1.getValue() + r2.getValue());
    }
    public static void add (Registry r, Memory m) {
        r.setValue(r.getValue() + m.getValue());
    }
    public static void subtract (Registry r1, Registry r2) {
        r1.setValue(r1.getValue() - r2.getValue());
    }
    public static void subtract (Registry r, Memory m) {
        r.setValue(r.getValue() - m.getValue());
    }
    public static void land (Registry r1, Registry r2) {
        logicValue = r1.getValue() != 0 && r2.getValue() != 0;
        System.out.println(r1.getName() + "&&" + r2.getName() + ": " + logicValue);
    }
    public static void land (Registry r, Memory m) {
        logicValue = r.getValue() != 0 && m.getValue() != 0;
        System.out.println(r.getName() + "&&" + m.identifier + ": " + logicValue);
    }
    public static void lor (Registry r1, Registry r2) {
        logicValue = r1.getValue() != 0 || r2.getValue() != 0;
        System.out.println(r1.getName() + "||" + r2.getName() + ": " + logicValue);
    }
    public static void lor (Registry r, Memory m) {
        logicValue = r.getValue() != 0 || m.getValue() != 0;
        System.out.println(r.getName() + "||" + m.identifier + ": " + logicValue);
    }
    public static void not (Registry r) {
        //r.value = ~ r.value;
        String value = Long.toBinaryString(r.getValue());
        int i = 0;
        while (value.charAt(i) == 0)
            i++;
        value = value.substring(i);
        StringBuilder valueB = new StringBuilder(value.length());
        valueB.append(value);
        for (i = 0; i < valueB.length(); i++) {

            if(valueB.charAt(i) == '0') {
                valueB.setCharAt(i, '1');
            }
            else
                valueB.setCharAt(i, '0');
        }
        r.setValue(Long.parseLong(valueB.toString(), 2));
    }

    public static void and (Registry r1, Registry r2) {
       r1.setValue(r1.getValue() & r2.getValue());

    }
    public static void and (Registry r, Memory m) {
        r.setValue(r.getValue() & m.getValue());
    }
    public static void or (Registry r1, Registry r2) {
        r1.setValue(r1.getValue() | r2.getValue());
    }
    public static void or (Registry r, Memory m) {
        r.setValue(r.getValue() | m.getValue());
    }
    public static void xor (Registry r1, Registry r2) {
        r1.setValue(r1.getValue() ^ r2.getValue());
    }
    public static void xor (Registry r, Memory m) {
        r.setValue(r.getValue() ^ m.getValue());
    }

    public static void je (Registry r1, Registry r2)
    {
        jumpValue = r1.getValue() == r2.getValue();
    }

    public static void jne (Registry r1, Registry r2) {
        jumpValue = r1.getValue() != r2.getValue();
    }

    public static void jg (Registry r1, Registry r2) {
        jumpValue = r1.getValue() > r2.getValue();
    }

    public static void jl (Registry r1, Registry r2) {
        jumpValue = r1.getValue() < r2.getValue();
    }

    public static void jge (Registry r1, Registry r2) {jumpValue = r1.getValue() >= r2.getValue(); }

    public static void jle (Registry r1, Registry r2) {jumpValue = r1.getValue() <= r2.getValue(); }

    public static void showAllRegistries() {
        System.out.println("Registries: ");
        System.out.println("R1: " + R1.getValue());
        System.out.println("R2: " + R2.getValue());
        System.out.println("R3: " + R3.getValue());
        System.out.println("R4: " + R4.getValue());
    }
}
