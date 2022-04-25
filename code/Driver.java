import java.io.PrintWriter;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws Exception {

        /**
         * Run arguments are in this order
         *  (1) Archive Size [Integer]
         *  (2) Function Flag [Byte]
         *  (3) Swarm Size [Integer]
         *  (4) Problem Dimension [Integer] 
         *  (5) Iterations [Integer]
         *  (6) Speciation-Mode [Boolean]
         */

        if (args.length != 6)
            usage();

    
        int ARCHIVE_SIZE = Integer.parseInt(args[0]);
        byte FLAG = Byte.parseByte(args[1]);
        int SWARM_SIZE = Integer.parseInt(args[2]);
        int DIMENSION = Integer.parseInt(args[3]);
        int ITERS = Integer.parseInt(args[4]);
        boolean SPECMODE = Boolean.parseBoolean(args[5]);
        String MODE = (SPECMODE) ? "SP/": "IW/";
        String FILENAME = fileName(ARCHIVE_SIZE, SWARM_SIZE, ITERS, FLAG, DIMENSION);
        Archive my_front = new Archive(ARCHIVE_SIZE, FLAG);
        Archive optimal = Function.getOptimalFront(1000, FLAG);

        Swarm s1 = new Swarm(FLAG, (byte) 1, SWARM_SIZE, DIMENSION, my_front, SPECMODE);
        Swarm s2 = new Swarm(FLAG, (byte) 2, SWARM_SIZE, DIMENSION, my_front, SPECMODE);

        ArrayList<Double> arr = new ArrayList<>();
        int printCount = 1;
        for (int i = 0; i < ITERS; i++) {
            s1.updateIteration();
            s2.updateIteration();
            if (i % 20 == 0 && i != 0)
                arr.add(Utility.IGD(my_front.arcToF1F2(), optimal));

            if (i % 500 == 0 && i != 0) {
                System.out.println(my_front.csvPrint(printCount++));
            }
        }
        System.out.println(my_front.csvPrint(printCount));
        Double[] IGD = arr.toArray(new Double[arr.size()]);
        writeDistances(FILENAME, FLAG, IGD, MODE);
    }

    public static void usage() {
        String usage = "\n===== Run arguments are in this order ====\n" +
        "\t(1) Archive Size [Integer]\n" +
        "\t(2) Function Flag [Byte]\n" +
        "\t(3) Swarm Size [Integer]\n" +
        "\t(4) Problem Dimension [Integer]\n"+
        "\t(5) Iterations [Integer]\n" +
        "\t(6) Speciation-Mode [Boolean]\n" +
        "==========================================\n";
        System.out.println(usage);
        System.exit(1);
    }

    public static String fileName(int arcSize, int swarmSize, int Iters, byte flag, int dim) throws Exception {
        switch (flag) {
            case 1: return String.format("IGD_SS%s_A%s_I%s_D%s_", swarmSize, arcSize, Iters, dim);
            case 2: return String.format("IGD_SS%s_A%s_I%s_D%s_", swarmSize, arcSize, Iters, dim);
            case 3: return String.format("IGD_SS%s_A%s_I%s_D%s_", swarmSize, arcSize, Iters, dim);
            case 4: return String.format("IGD_SS%s_A%s_I%s_D%s_", swarmSize, arcSize, Iters, dim);
            case 6: return String.format("IGD_SS%s_A%s_I%s_D%s_", swarmSize, arcSize, Iters, dim);
            default:
                throw new Exception ("FLAG NOT FOUND");
        }
    }

    public static String funcName(byte flag) {
        switch (flag) {
            case 1: return "ZDT1/";
            case 2: return "ZDT2/";
            case 3: return "ZDT3/";
            case 4: return "ZDT4/";
            case 6: return "ZDT6/";
            default: break;
        }
        return null;
    }

    public static void writeDistances(String FILENAME, byte flag, Double[] data, String path) {
        try {
            PrintWriter writer = new PrintWriter("logs/" +path + funcName(flag) + "IGD/" + FILENAME + (int)(Math.random() * 100000) + ".csv", "UTF-8");

            for (int i = 0; i < data.length; i++) {
                if (i < data.length - 1)
                    writer.println(data[i] + ",");
                else 
                    writer.println(data[i]);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
