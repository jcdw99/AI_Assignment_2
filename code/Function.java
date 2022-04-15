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
            case 6:
                return ZDT6.f1(x);
            case 4:
                return ZDT4.f1(x);
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
            case 6:
                return ZDT6.f2(x);
            case 4:
                return ZDT4.f2(x);
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
            case 6:
                return ZDT6.inDomain(x);
            case 4:
                return ZDT4.inDomain(x);
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
                return ZDT1.getDomain();
            case 3: 
                return ZDT3.getDomain();
            case 2:
                return ZDT2.getDomain();
            case 6:
                return ZDT6.getDomain();
            case 4:
                return ZDT4.getDomain();

            default:
                throw new Exception("Function Flag Invalid");
                
        }
    }

    /** 
     * Gets the optimal front for this objective function 
     * 
     * Decides on which front to return based on provided flag.
     * The front is returned in an archive, which is an array of Vectors
     */
    public static Archive getOptimalFront(int length, byte flag) throws Exception{
        switch (flag) {
            // ZDT Test Function T1
            case 1:
                return ZDT1.optimalFront(length);
            case 3: 
                return ZDT3.optimalFront(length);
            case 2:
                return ZDT2.optimalFront(length);
            case 4:
                return ZDT4.optimalFront(length);
            case 6:
                return ZDT6.optimalFront(length);

            default:
                throw new Exception("Function Flag Invalid");          
        }
    }

    static class ZDT3 { 

        private static double f1(Vector x) throws Exception {
            return x.atIndex(0);
        }
        private static double f2(Vector x) throws Exception {
            double g = 1 + 9 * (x.scale(1.0/(x.size() - 1)).sum(1));
            double h = 1 - Math.sqrt(f1(x) / g) - (f1(x) / g)*(Math.sin(10*Math.PI*f1(x)));
            double f2 = g * h;
            return f2;
        }

        public static boolean inDomain(Vector x) throws Exception {
            int[] dom = getDomain();
            for (int i = 0; i < x.size(); i++) 
                if (x.atIndex(i) < dom[0] || x.atIndex(i) > dom[1])
                    return false;
            return true;
        }

        public static int[] getDomain() {
            int[] domain = {0, 1};
            return domain;
        }

        private static double optimalf2(double f1) {
            return 1 - Math.sqrt(f1) - f1 * Math.sin(10 * Math.PI * f1);
        }

        public static Archive optimalFront(int numEntries) {
            Archive a = new Archive(numEntries);
            for (int i = 0; i < numEntries; i++) {
                double f1 = (double) i / numEntries;
                double[] dat = {f1 , optimalf2(f1)};
                a.entries[i] = new Vector(dat);
            }
            return a;
        }


    }

    static class ZDT1 {
        
        private static double f1(Vector x) throws Exception {
            return x.atIndex(0);
        }
    
        private static double f2(Vector x) throws Exception {
            double g = 1 + 9 * (x.scale(1.0/(x.size() - 1)).sum(1));
            double h = 1 - Math.sqrt((f1(x) / g));
            double f2 = g * h;
            return f2;
        }

        public static boolean inDomain(Vector x) throws Exception {
            int[] dom = getDomain();
            for (int i = 0; i < x.size(); i++) 
                if (x.atIndex(i) < dom[0] || x.atIndex(i) > dom[1])
                    return false;
            return true;
        }

        public static int[] getDomain() {
            int[] domain = {0, 1};
            return domain;
        }

        private static double optimalf2(double f1) {
            return 1 - Math.sqrt(f1);
        }

        public static Archive optimalFront(int numEntries) {
            Archive a = new Archive(numEntries);
            for (int i = 0; i < numEntries; i++) {
                double f1 = (double) i / numEntries;
                double[] dat = {f1 , optimalf2(f1)};
                a.entries[i] = new Vector(dat);
            }
            return a;
        }
    }

    static class ZDT2 {
        
        private static double f1(Vector x) throws Exception {
            return x.atIndex(0);
        }
    
        private static double f2(Vector x) throws Exception {
            double g = 1 + 9 * (x.scale(1.0/(x.size() - 1)).sum(1));
            double h = 1 - Math.pow((f1(x) / g), 2);
            double f2 = g * h;
            return f2;
        }

        public static boolean inDomain(Vector x) throws Exception {
            int[] dom = getDomain();
            for (int i = 0; i < x.size(); i++) 
                if (x.atIndex(i) < dom[0] || x.atIndex(i) > dom[1])
                    return false;
            return true;
        }

        public static int[] getDomain() {
            int[] domain = {0, 1};
            return domain;
        }

        public static double optimalf2(double f1 ){
            return 1 - Math.pow(f1, 2);
        }

        public static Archive optimalFront(int numEntries) {
            Archive a = new Archive(numEntries);
            for (int i = 0; i < numEntries; i++) {
                double f1 = (double) i / numEntries;
                double[] dat = {f1 , optimalf2(f1)};
                a.entries[i] = new Vector(dat);
            }
            return a;
        }
    }
    static class ZDT6 {
        
        private static double f1(Vector x) throws Exception {
            return 1 - Math.exp(x.atIndex(0) * -4) * Math.pow(Math.sin(6.0 * Math.PI * x.atIndex(0)), 6);
        }
        
        private static double f2(Vector x) throws Exception {
            double g = 1 + 9 * Math.pow((x.sum(1) / (x.size() - 1.0)), 0.25);
            double h = 1 - Math.pow((f1(x) / g), 2);
            System.out.printf("G:%s and H:%s\n", g, h);
            double f2 = g * h;
            return f2;
        }
        public static boolean inDomain(Vector x) throws Exception {
            int[] dom = getDomain();
            for (int i = 0; i < x.size(); i++) 
                if (x.atIndex(i) < dom[0] || x.atIndex(i) > dom[1])
                    return false;
            return true;
        }
        public static int[] getDomain() {
            int[] domain = {0, 1};
            return domain;
        }

        public static double optimalf2(double f1 ){
            return 1 - Math.exp(-4.0 * f1) * Math.pow(Math.sin(6.0 * Math.PI * f1), 6);
        }

        public static Archive optimalFront(int numEntries) {
            Archive a = new Archive(numEntries);
            for (int i = 0; i < numEntries; i++) {
                double f1 = (double) i / numEntries;
                double[] dat = {f1 , optimalf2(f1)};
                a.entries[i] = new Vector(dat);
            }
            return a;
        }
    }

    static class ZDT4 {
        
        private static double f1(Vector x) throws Exception {
            return x.atIndex(0);
        }
        private static double f2(Vector x) throws Exception {
            double g = 1.0 + 10.0*(x.size() - 1.0) + x.pow(2).shift(10.0*Math.cos(4.0*Math.PI)).sum(1);
            double h = 1 - Math.sqrt((f1(x) / g));
            double f2 = g * h;
            return f2;
        }
        public static boolean inDomain(Vector x) throws Exception {
            double x1 = x.atIndex(0);
            if (x1 < 0 || x1 > 1)
                return false;
            int[] dom = getDomain();
            for (int i = 1; i < x.size(); i++) 
                if (x.atIndex(i) < dom[0] || x.atIndex(i) > dom[1])
                    return false;
            return true;
        }
        public static int[] getDomain() {
            int[] domain = {-5, 5};
            return domain;
        }

        public static double optimalf2(double f1 ){
            return 1 - Math.sqrt(f1);
        }

        public static Archive optimalFront(int numEntries) {
            Archive a = new Archive(numEntries);
            for (int i = 1; i < numEntries; i++) {
                double f1 = (double) i / numEntries;
                double[] dat = {f1 , optimalf2(f1)};
                a.entries[i] = new Vector(dat);
            }
            return a;
        }
    }
}
