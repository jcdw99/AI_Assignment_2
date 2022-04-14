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
        if (args.length != 5)
            usage();
        
        int ARCHIVE_SIZE = Integer.parseInt(args[0]);
        byte FLAG = Byte.parseByte(args[1]);
        int SWARM_SIZE = Integer.parseInt(args[2]);
        int DIMENSION = Integer.parseInt(args[3]);
        int ITERS = Integer.parseInt(args[4]);
        Archive a = new Archive(ARCHIVE_SIZE, FLAG);
        Swarm s1 = new Swarm(FLAG, (byte) 1, SWARM_SIZE, DIMENSION, a);
        Swarm s2 = new Swarm(FLAG, (byte) 2, SWARM_SIZE, DIMENSION, a);

        for (int i = 0; i < ITERS; i ++) {
            s1.updateIteration();
            s2.updateIteration();
        }
        System.out.println(a.csvPrint());
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
}
