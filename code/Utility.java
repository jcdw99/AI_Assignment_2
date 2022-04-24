

public class Utility {

    /**
     * This class is not instantiable
     */
    private Utility() {

    }

    public static double[] sampleControlParams() {
        double w = Math.random() * 0.8;
        double c1  = Math.random() * 2;
        double c2 = Math.random() * 2;
        double c3 = Math.random() * 2;
        double lambda = Math.random();
        while (!stabilitySatisfied(w, c1, c2, c3, lambda)) {
            w = Math.random() * 0.8;
            c1  = Math.random() * 2;
            c2 = Math.random() * 2;
            c3 = Math.random() * 2;
            lambda = Math.random();
        }
        double[] data = {w, c1, c2, c3, lambda};
        return data;
    }

    public static boolean stabilitySatisfied(double w, double c1, double c2, double c3, double lam) {
        double LHS = c1 + (lam * c2) + (1 - lam) * c3;
        double RHS_TOP_NUM = 4 * (1 - w * w);
        double RHS_BOT_LEFT = (1 - w);
        double RHS_BOT_NUM = ( (c1 * c1) + (lam * lam) * (c2 * c2) + (1 - lam) * (1 - lam) * (c3 * c3) ) * (1 + w);
        double RHS_BOT_DEN = 3 * (c1 + (lam * c2) + ((1 - lam)*(c3))) * (c1 + (lam * c2) + ((1 - lam)*(c3)));
    
        double RHS = RHS_TOP_NUM / (RHS_BOT_LEFT + ( RHS_BOT_NUM / RHS_BOT_DEN));

        return LHS < RHS && LHS > 0;
    }

    public static double calcLambda(double w, double c1, double c2, double c3) {
        double lambda;
        do {
            lambda = Math.random();
        }
        while (!stabilitySatisfied(w, c1, c2, c3, lambda));
        return lambda;
    }

    /**
     * Calculates the inverted generational distance between archives A and B
     * 
     * Archive A represents the estimated pareto front
     * Archive B represents the true pareto front
     * @param a
     * @param b
     * @return
     */
    public static double IGD(Archive a, Archive b) throws Exception {
        double distance = 0.0;
        // for each vector in my estimated front
        double[] closests = new double[10];
        for (int i = 0; i < closests.length; i++)
            closests[i] = Double.MAX_VALUE;
        for (int i = 0; i < a.entries.length; i++) {
            // for each vector in the optimal front
            Vector found = a.entries[i];
            for (int j = 1; j < b.entries.length - 1; j++) {

                double dist = found.distance(b.entries[j]);

                for (int k = 0; k < closests.length; k++) {
                    if (dist < closests[k]) {
                        closests[k] = dist;
                        break;
                    }
                }

            }
        }
        for (int i = 0; i < closests.length; i++) {
            distance += (Math.pow(closests[i], 2));
        }

        return Math.sqrt(distance) / closests.length;
    }

}
