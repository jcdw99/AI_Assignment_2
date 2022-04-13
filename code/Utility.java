

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
        return LHS < RHS;
    }

    public static double calcLambda(double w, double c1, double c2, double c3) {
        double lambda;
        do {
            lambda = Math.random();
        }
        while (!stabilitySatisfied(w, c1, c2, c3, lambda));
        return lambda;
    }

}
