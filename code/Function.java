public class Function {


    public static double evaluate(Vector x, byte funcFlag, byte objFlag) throws Exception {
        if (objFlag == 1) {
            return f1(x, funcFlag);
        } else {
            return f2(x, funcFlag);
        }
    }

    /**
     * Computes the function evaluation of f1, for the benchmark function denoted by the flag
     * @param x
     * @param flag
     * @return
     * @throws Exception
     */
    public static double f1(Vector x, byte flag) throws Exception {
        switch (flag) {
            // ZDT Test Function T1
            case 1:
                return ZDT1.f1(x);
            case 3:
                return ZDT3.f1(x);
            case 2:
                return ZDT2.f1(x);
            default:
                throw new Exception("Function Flag Invalid");
        }
    }

    /**
     * Computes the function evaluation of f2, for the benchmark function denoted by the flag
     * @param x
     * @param flag
     * @return
     * @throws Exception
     */
    public static double f2(Vector x, byte flag) throws Exception {
        switch (flag) {
            // ZDT Test Function T1
            case 1:
                return ZDT1.f2(x);
            case 3:
                return ZDT3.f2(x);
            case 2:
                return ZDT2.f2(x);
            default:
                throw new Exception("Function Flag Invalid");   
        }
    }

    /**
     * Returns true or false depending on whether or not the Vector is within the domain
     * of the MGPSO benchmark function referenced by the flag.
     * @param x
     * @param flag
     * @return
     * @throws Exception
     */
    public static boolean inDomain(Vector x, byte flag) throws Exception {
        switch (flag) {
            // ZDT Test Function T1
            case 1:
                return ZDT1.inDomain(x);
            case 3: 
                return ZDT3.inDomain(x);
            case 2:
                return ZDT2.inDomain(x);
            default:
                throw new Exception("Function Flag Invalid");   
        }
    }

    /**
     * Returns the domain of the MGPSO benchmark function specified by the flag.
     * The returned dataype is an array of length 2, specifiying the lower and upper bounds
     * @param flag
     * @return
     * @throws Exception
     */
    public static int[] getDomain(byte flag) throws Exception {
        switch (flag) {
            // ZDT Test Function T1
            case 1:
                return ZDT1.getDomatin();
            case 3: 
                return ZDT3.getDomatin();
            case 2:
                return ZDT2.getDomatin();

            default:
                throw new Exception("Function Flag Invalid");
                
        }
    }

    static class ZDT3 { 

        private static double f1(Vector x) throws Exception {
            return x.atIndex(0);
        }
        private static double f2(Vector x) throws Exception {
            double g = 1 + 9 * (x.scale(1/(x.size() - 1)).sum(1));
            double h = 1 - Math.sqrt(f1(x) / g) - (f1(x) / g)*(Math.sin(10*Math.PI*f1(x)));
            double f2 = g * h;
            return f2;
        }

        public static boolean inDomain(Vector x) throws Exception {
            for (int i = 0; i < x.size(); i++) 
                if (x.atIndex(i) < 0 || x.atIndex(i) > 1)
                    return false;
            return true;
        }

        public static int[] getDomatin() {
            int[] domain = {0, 1};
            return domain;
        }


    }

    static class ZDT1 {
        
        private static double f1(Vector x) throws Exception {
            return x.atIndex(0);
        }
    
        private static double f2(Vector x) throws Exception {
            double g = 1 + 9 * (x.scale(1/(x.size() - 1)).sum(1));
            double h = 1 - Math.sqrt((f1(x) / g));
            double f2 = g * h;
            return f2;
        }

        public static boolean inDomain(Vector x) throws Exception {
            for (int i = 0; i < x.size(); i++) 
                if (x.atIndex(i) < 0 || x.atIndex(i) > 1)
                    return false;
            return true;
        }

        public static int[] getDomatin() {
            int[] domain = {0, 1};
            return domain;
        }
    }

    static class ZDT2 {
        
        private static double f1(Vector x) throws Exception {
            return x.atIndex(0);
        }
    
        private static double f2(Vector x) throws Exception {
            double g = 1 + 9 * (x.scale(1/(x.size() - 1)).sum(1));
            double h = 1 - Math.pow((f1(x) / g), 2);
            double f2 = g * h;
            return f2;
        }

        public static boolean inDomain(Vector x) throws Exception {
            for (int i = 0; i < x.size(); i++) 
                if (x.atIndex(i) < 0 || x.atIndex(i) > 1)
                    return false;
            return true;
        }

        public static int[] getDomatin() {
            int[] domain = {0, 1};
            return domain;
        }
    }
}
