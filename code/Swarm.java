public class Swarm {


    public Particle[] particles;
    private Archive archive;
    private double c1;
    private double c2;
    private double c3;
    private double w;
    private byte funcFlag;
    private byte objFlag;

    /**
     * Initializes a particle swarm that optimizes the specified function, with specified particle count
     * @param funcFlag A flag used to specify which benchmark function should be optimized
     * @param objFlag A flag used to specify which objective function should be optimized in this benchmark function
     * @param particles The number of particles in this swarm
     * @param dim The dimension of each particle in this swarm
     */
    public Swarm(byte funcFlag, byte objFlag, int particles, int dim, Archive a) throws Exception {
        
        double[] params = getParams(funcFlag);
        // double[] params = Utility.sampleControlParams();
        this.funcFlag = funcFlag;
        this.objFlag = objFlag;
        this.w = params[0];
        this.c1 = params[1];
        this.c2 = params[2];
        this.c3 = params[3];
        this.archive = a;
        this.particles = new Particle[particles];

        for (int i = 0; i < particles; i++) {
            this.particles[i] = new Particle(dim, funcFlag, objFlag, w, c1, c2, c3, archive);
        }
        updateHoodBest(findGlobalBest());
    }

    private double[] getParams(byte flag) throws Exception{
        switch (flag) {
            case 1: 
                double[] dat = {0.475, 1.8, 1.1, 1.8};
                return dat;
            case 2:
                double[] dat1 = {0.075, 1.6, 1.35, 1.9};
                return dat1;
            case 3:
                return Utility.sampleControlParams();
            case 4: 
                double[] dat3 = {0.175, 1.85, 1.35, 1.85};
                return dat3;
            case 6:  
                 double[] dat4 = {0.6, 1.85, 1.55, 1.8};
                return dat4;
            default:
                throw new Exception("Function Flag not recognized");
        }
    }

    /**
     * A single function call designed to catalyze a single particle update iteration for each particle in the swarm
     *
     * The steps of the procedure include
     *
     *      (1) attempt to update the pBest of each particle
     *      (2) attempt to update the neighborhood best of each particle
     *      (3) update the archive if possible to reflect this particle's current position
     *      (4) perform the velocity update procedure for this particle (involves tournament selection first)
     *      (5) perform the position update procedure for this particle
     */
    public void updateIteration() throws Exception{
        for (int i = 0; i < particles.length; i++) {
            // attempt pBest update for this particle if possible
            particles[i].attemptPBestUpdate();
            // update neighborhood best for this particle (using gBest currently)
            updateHoodBest(findGlobalBest());
            // attempt to add this particles position to the archive
            archive.tryArchiveAdd(particles[i].position.duplicate());
            //perform the velocity update
            particles[i].velUpdate();
            //perform position update
            particles[i].positionUpdate();
        }
    }

    public void runVelUpdate() throws Exception {
        for (int i = 0; i < particles.length; i++) {
            particles[i].velUpdate();
        }
    }
    /**
     * Update neighborhood best position and evaluation of all particles in the swarm
     * to reflect that of the provided argument
     * @param hoodBest
     * @throws Exception
     */
    public void updateHoodBest(Vector hoodBest) throws Exception {
        double gBestEval = Function.evaluate(hoodBest, funcFlag, objFlag);
        for (int i = 0; i < particles.length; i++) {
            particles[i].gBestVec = hoodBest.duplicate();
            particles[i].gBest = gBestEval;
        }
    }

    /**
     * Finds the GLOBAL best position of all particles in this swarm. This uses a start topology
     * neighborhood, which may not always be the intent. Assert that this is the desired functionality.
     * @return
     * @throws Exception
     */
    public Vector findGlobalBest() throws Exception {
        Vector gBest = particles[0].pBestVec;
        double gBestEval = Function.evaluate(gBest, funcFlag, objFlag);
        //find new Gbest
        for (int i = 1; i < particles.length; i++) {
            double thisEval = Function.evaluate(particles[i].pBestVec, funcFlag, objFlag);
            if (thisEval < gBestEval) {
                gBestEval = thisEval;
                gBest = particles[i].pBestVec;
            }
        }
        return gBest.duplicate();
    }

    /**
     * Archive-reference all current candidate solutions. If a candidate solution is found to be non-dominated
     * we should add this solution to the archive. All Archive entries which this solution dominates should be
     * removed from the archive.
     *
     * Might not be used in this context, rather used on a 1 by 1 particle depth function call
     */
    public void updateArchive() throws Exception {
        // for each candidate solution
        for (int i = 0; i < particles.length; i++) {
            Vector candidate = particles[i].position.duplicate();
            archive.tryArchiveAdd(candidate);
        }
        System.out.println(archive);
    }

    public void printArchive() {
        System.out.println(archive);
    }

    public String csvArchive() throws Exception {
        return archive.csvPrint();
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        try {
            b.append("Global Best Vector: " + findGlobalBest());
            b.append("Global Best Evaluation: " + Function.evaluate(findGlobalBest(), funcFlag, objFlag));
        } catch (Exception e) {
            e.printStackTrace();
        }

        b.append("\n\t[\n");
        for (Particle i : particles) {
            b.append("\t\t" + i);
        }
        b.append("\t]\n");
        return b.toString();
    }
}
