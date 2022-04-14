import java.io.PrintWriter;

public class Driver {
    public static void main(String[] args) throws Exception {

        /**
         * Run arguments are in this order
         *  (1) Archive Size [Integer]
         *  (2) Function Flag [Byte]
         *  (3) Swarm Size [Integer]
         *  (4) Problem Dimension [Integer] 
         *  (5) Iterations [Integer]
         */

        // for (int i = 0; i < 500; i++) {
        //     double[] d1 = {Math.random(), Math.random()};
        //     double[] d2 = {Math.random(), Math.random()};
        //     Vector a = new Vector(d1);
        //     Vector b = new Vector(d2);
        //     System.out.println(a.toString().substring(0, a.toString().length() - 1) + " dominates " + b.toString().substring(0, b.toString().length() - 1) +" ?    :" + a.dominates(b));
        // }

        // System.exit(1);
        if (args.length != 5)
            usage();
        
        int ARCHIVE_SIZE = Integer.parseInt(args[0]);
        byte FLAG = Byte.parseByte(args[1]);
        int SWARM_SIZE = Integer.parseInt(args[2]);
        int DIMENSION = Integer.parseInt(args[3]);
        int ITERS = Integer.parseInt(args[4]);
        String FILENAME = fileName(ARCHIVE_SIZE, SWARM_SIZE, ITERS, FLAG);
        Archive my_front = new Archive(ARCHIVE_SIZE, FLAG);
        Archive optimal = Function.getOptimalFront(1000, FLAG);
        Swarm s1 = new Swarm(FLAG, (byte) 1, SWARM_SIZE, DIMENSION, my_front);
        Swarm s2 = new Swarm(FLAG, (byte) 2, SWARM_SIZE, DIMENSION, my_front);
        double[] IGD = new double[ITERS];
        for (int i = 0; i < ITERS; i ++) {
            s1.updateIteration();
            s2.updateIteration();
            IGD[i] = Utility.IGD(my_front.arcToF1F2(), optimal);
        }

        System.out.println(my_front.csvPrint());
        writeDistances(FILENAME, FLAG, IGD);
    }

    public static void usage() {
        String usage = "\n===== Run arguments are in this order ====\n" +
        "\t(1) Archive Size [Integer]\n" +
        "\t(2) Function Flag [Byte]\n" +
        "\t(3) Swarm Size [Integer]\n" +
        "\t(4) Problem Dimension [Integer]\n"+
        "\t(5) Iterations [Integer]\n" +
        "==========================================\n";
        System.out.println(usage);
        System.exit(1);
    }

    public static String fileName(int arcSize, int swarmSize, int Iters, byte flag) {
        switch (flag) {
            case 1: return String.format("IGD_SS%s_A%s_I%s", swarmSize, arcSize, Iters);
            case 2: return String.format("IGD_SS%s_A%s_I%s", swarmSize, arcSize, Iters);
            case 3: return String.format("IGD_SS%s_A%s_I%s", swarmSize, arcSize, Iters);
            case 4: 
            case 5:
            default: break;
        }
        return null;
    }

    public static String funcName(byte flag) {
        switch (flag) {
            case 1: return "ZDT1/";
            case 2: return "ZDT2/";
            case 3: return "ZDT3/";
            case 4: 
            case 5:
            default: break;
        }
        return null;
    }

    public static void writeDistances(String FILENAME, byte flag, double[] data) {
        try {
            PrintWriter writer = new PrintWriter("logs/" + funcName(flag) + "IGD/" + FILENAME + (int)(Math.random() * 100) + ".csv", "UTF-8");

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
