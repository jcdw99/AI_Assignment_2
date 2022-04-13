public class Driver {
    public static void main(String[] args) throws Exception {


        Archive a = new Archive(250, (byte) 3);
        Swarm s1 = new Swarm((byte) 3, (byte) 1, 25, 10, a);
        Swarm s2 = new Swarm((byte) 3, (byte) 2, 25, 10, a);

        for (int i = 0; i < 500; i ++) {
            s1.updateIteration();
            s2.updateIteration();
        }
        System.out.println(a.csvPrint());
    }
}
