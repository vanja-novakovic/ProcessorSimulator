package org.unibl.etf.project.simulation;
import org.unibl.etf.project.architecture.*;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class Interpreter {

    public static void main (String[] args) throws IOException, NullPointerException {
        List<String> program = null;
        File currentFile = new File(System.getProperty("user.home"));
        File file = new File (currentFile.getPath() + File.separator + "program.txt");

        if(file.exists()) {
           program = Files.readAllLines(file.toPath());
        }

        boolean active = true;
        Scanner scanner = new Scanner(System.in);
        if(program != null && program.size() > 0) {
            System.out.println("Press enter to continue or type in \"show\" to inspect memory");
            for (int i = 0; i < program.size(); i++) {
                System.out.print("#" + (i + 1) + " >> ");
                String interpreted = interpretProgram(program.get(i).trim().toLowerCase());
                if (interpreted.equals("")) {
                    var debug = scanner.nextLine().trim().toLowerCase();
                    if("show".equals(debug)) {
                        Processor.showAllRegistries();
                        Memory.showAllMemoryLocations();
                    }
                }
                else {
                    for (int j = 0; j < program.size(); j++) {
                        if(program.get(j).trim().equalsIgnoreCase(interpreted.trim())) {
                            i = j;
                        }
                    }
                }
            }
        }
        System.out.println("Interpreter started. ");
        while(active) {

            System.out.print(">> ");
            String instruction = scanner.nextLine().trim().toLowerCase();
            if ("end".equals(instruction)) {
                active = false;
            }
            else {
                interpret(instruction);
            }
        }
    }
    public static void interpret (String instruction) {
        String[] parameters = instruction.split(" ");

        if ("declare".equals(parameters[0]) && parameters.length== 3)
        {
            long n = Long.parseLong(parameters[2]);
            if (Memory.isMemoryLocation(parameters[1]))
                Memory.updateMemoryLocation(parameters[1], n);
            else
                new Memory(parameters[1], n);
        }
        else if("declare".equals(parameters[0]) && parameters.length == 2) {
            if(!Memory.isMemoryLocation(parameters[1]))
                new Memory(parameters[1]);
        }
        else if ("read".equals(parameters[0]) && (isRegistry(parameters[1]) || Memory.isMemoryLocation(parameters[1])))
        {
            if (isRegistry(parameters[1]))
                System.out.println(parameters[1] + ": " + getRegistryFromID(parameters[1]).getValue());
            else
                System.out.println(parameters[1] + ": " + Memory.getMemoryLocation(parameters[1]).getValue());
        }

        else if ("load".equals(parameters[0]) && parameters.length == 3 && isRegistry(parameters[1])
                && (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2])))
        {
            Registry registry = getRegistryFromID(parameters[1]);
            if (registry != null)
            {
                if (isRegistry(parameters[2]))
                    Processor.load(registry, getRegistryFromID(parameters[2]));
                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.load(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.load(registry, new Memory(n));
                }
            }
        }
        else if ("store".equals(parameters[0]) && parameters.length == 3 && Memory.isMemoryLocation(parameters[1])
            && isRegistry(parameters[2])) {
            Memory memory = Memory.getMemoryLocation(parameters[1]);
            Processor.store(memory, getRegistryFromID(parameters[2]));
        }
        else if ("add".equals(parameters[0]) && parameters.length == 3 && isRegistry(parameters[1])
                && (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2])))
        {
            Registry registry = getRegistryFromID(parameters[1]);
            if (registry != null)
            {
                if (isRegistry(parameters[2]))
                    Processor.add(registry, getRegistryFromID(parameters[2]));

                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.add(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.add(registry, new Memory(n));
                }
            }
        }
        else if ("subtract".equals(parameters[0]) && parameters.length == 3 && isRegistry(parameters[1])
                && (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2])))
        {
            Registry registry = getRegistryFromID(parameters[1]);
            if (registry != null)
            {
                if (isRegistry(parameters[2]))
                    Processor.subtract(registry, getRegistryFromID(parameters[2]));

                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.subtract(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.subtract(registry, new Memory(n));
                }
            }
        }
        else if ("land".equals(parameters[0]) && parameters.length == 3 &&
                isRegistry(parameters[1]) &&
                (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2])))
        {
            Registry registry = getRegistryFromID(parameters[1]);

            if (registry != null)
                if (isRegistry(parameters[2]))
                    Processor.land(registry, getRegistryFromID(parameters[2]));

                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.land(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.land(registry, new Memory(n));
                }
        }

        else if ("lor".equals(parameters[0]) && parameters.length == 3 &&
                isRegistry(parameters[1]) &&
                (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2])))
        {
            Registry registry = getRegistryFromID(parameters[1]);

            if (registry != null)
                if (isRegistry(parameters[2]))
                    Processor.lor(registry, getRegistryFromID(parameters[2]));

                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.lor(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.lor(registry, new Memory(n));
                }
        }

        else if ("and".equals(parameters[0]) && parameters.length == 3 && isRegistry(parameters[1]) &&
                (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2]))) {
            Registry registry = getRegistryFromID(parameters[1]);

            if (registry != null)
                if (isRegistry(parameters[2]))
                    Processor.and(registry, getRegistryFromID(parameters[2]));

                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.and(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.and(registry, new Memory(n));
                }
        }
        else if ("or".equals(parameters[0]) && parameters.length == 3 &&
                isRegistry(parameters[1]) &&
                (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2])))
        {
            Registry registry = getRegistryFromID(parameters[1]);

            if (registry != null)
                if (isRegistry(parameters[2]))
                    Processor.or(registry, getRegistryFromID(parameters[2]));

                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.or(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.or(registry, new Memory(n));
                }
        }
        else if ("xor".equals(parameters[0]) && parameters.length == 3 && isRegistry(parameters[1]) &&
                (isRegistry(parameters[2]) || Memory.isMemoryLocation(parameters[2]) || parse(parameters[2])))
        {
            Registry registry = getRegistryFromID(parameters[1]);

            if (registry != null)
                if (isRegistry(parameters[2]))
                    Processor.xor(registry, getRegistryFromID(parameters[2]));

                else if (Memory.isMemoryLocation(parameters[2]))
                    Processor.xor(registry, Memory.getMemoryLocation(parameters[2]));
                else
                {
                    long n = Long.parseLong(parameters[2]);
                    Processor.xor(registry, new Memory(n));
                }
        }
        else if ("not".equals(parameters[0]) && parameters.length == 2 && isRegistry(parameters[1]))
        {
            Registry registry = getRegistryFromID(parameters[1]);

            if (registry != null)
                Processor.not(registry);
        }
    }

    public static String interpretProgram(String instruction) {
        interpret(instruction);
        String[] parameters = instruction.split(" ");

        if ("je".equals(parameters[0]) && parameters.length == 4 &&
                isRegistry(parameters[1]) && isRegistry(parameters[2]))
        {
            Registry registry1 = getRegistryFromID(parameters[1]);
            Registry registry2 = getRegistryFromID(parameters[2]);

            if (registry1 != null && registry2 != null)
                Processor.je(registry1, registry2);

            if (Processor.jumpValue)
                return parameters[3];
        }
        else if ("jne".equals(parameters[0]) && parameters.length == 4 &&
                isRegistry(parameters[1]) && isRegistry(parameters[2]))
        {
            Registry registry1 = getRegistryFromID(parameters[1]);
            Registry registry2 = getRegistryFromID(parameters[2]);

            if (registry1 != null && registry2 != null)
                Processor.jne(registry1, registry2);

            if (Processor.jumpValue)
                return parameters[3];
        }
        else if ("jg".equals(parameters[0]) && parameters.length == 4
                && isRegistry(parameters[1]) && isRegistry(parameters[2]))
        {
            Registry registry1 = getRegistryFromID(parameters[1]);
            Registry registry2 = getRegistryFromID(parameters[2]);

            if (registry1 != null && registry2 != null)
                Processor.jg(registry1, registry2);

            if (Processor.jumpValue)
                return parameters[3];
        }
        else if ("jl".equals(parameters[0]) && parameters.length == 4
                && isRegistry(parameters[1]) && isRegistry(parameters[2]))
        {
            Registry registry1 = getRegistryFromID(parameters[1]);
            Registry registry2 = getRegistryFromID(parameters[2]);

            if (registry1 != null && registry2 != null)
                Processor.jl(registry1, registry2);

            if (Processor.jumpValue)
                return parameters[3];
        }
        else if ("jge".equals(parameters[0]) && parameters.length == 4
                && isRegistry(parameters[1]) && isRegistry(parameters[2]))
        {
            Registry registry1 = getRegistryFromID(parameters[1]);
            Registry registry2 = getRegistryFromID(parameters[2]);

            if (registry1 != null && registry2 != null)
                Processor.jge(registry1, registry2);

            if (Processor.jumpValue)
                return parameters[3];
        }
        else if ("jle".equals(parameters[0]) && parameters.length == 4
                && isRegistry(parameters[1]) && isRegistry(parameters[2]))
        {
            Registry registry1 = getRegistryFromID(parameters[1]);
            Registry registry2 = getRegistryFromID(parameters[2]);

            if (registry1 != null && registry2 != null)
                Processor.jle(registry1, registry2);

            if (Processor.jumpValue)
                return parameters[3];
        }
        return "";
    }

    private static boolean isRegistry(String identifier)
    {
        return ("r1".equals(identifier) || "r2".equals(identifier) ||
                "r3".equals(identifier) || "r4".equals(identifier));
    }

    private static Registry getRegistryFromID(String identifier)
    {
        return switch (identifier) {
            case "r1" -> Processor.R1;
            case "r2" -> Processor.R2;
            case "r3" -> Processor.R3;
            case "r4" -> Processor.R4;
            default -> null;
        };
    }
    private static boolean parse (String parameter) {
        try {
            Long.parseLong(parameter);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
