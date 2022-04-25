import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;

public class Swarm {

    public Particle[] particles;
    private Archive archive;
    private double c1;
    private double c2;
    private double c3;
    private double w;
    private byte funcFlag;
    private byte objFlag;
    public static boolean SINGLEMODE = false;
    private boolean SPSO;
    

    /**
     * Initializes a particle swarm that optimizes the specified function, with
     * specified particle count
     * 
     * @param funcFlag  A flag used to specify which benchmark function should be
     *                  optimized
     * @param objFlag   A flag used to specify which objective function should be
     *                  optimized in this benchmark function
     * @param particles The number of particles in this swarm
     * @param dim       The dimension of each particle in this swarm
     */
    public Swarm(byte funcFlag, byte objFlag, int particles, int dim, Archive a, boolean spso) throws Exception {

        double[] params = getParams(funcFlag);
        // double[] params = Utility.sampleControlParams();
        this.funcFlag = funcFlag;
        this.objFlag = objFlag;
        this.w = params[0];
        this.c1 = params[1];
        this.c2 = params[2];
        this.c3 = params[3];
        this.SPSO = spso;
        this.archive = a;
        this.particles = new Particle[particles];

        for (int i = 0; i < particles; i++) {
            this.particles[i] = new Particle(dim, funcFlag, objFlag, w, c1, c2, c3, archive);
            if (SINGLEMODE)
                this.particles[i].lambda = 1;
        }
        if (!SPSO)
            updateHoodBest(findGlobalBest());
        else {
            updateSPSOBest(funcFlag, objFlag, dim);
        }
    }

    /**
     * this is where you will do the speciation neighborhood updates. Follow the algorithm
     * referenced in the paper I wrote.
     */
    @SuppressWarnings("unchecked")
    private void updateSPSOBest(byte funcFlag, byte objFlag, int dim) throws Exception {

       double radius = 0.05 * dim;
       Particle[] sorted =  getSortedArray(funcFlag, objFlag);
       ArrayList<Particle> seeds = new ArrayList<>();
       for (Particle p: sorted) {

            boolean found = false;
            for (Particle seed: seeds) {
                if (seed.position.distance(p.position) < radius && Function.inDomain(p.position, funcFlag)) {
                    // this particle is part of an existing species, continue scan
                    found = true;
                    break;
                }
            }

            if (!found && Function.inDomain(p.position, funcFlag)) {
                // this particle is a new species
                seeds.add(p);
            }
       }

       ArrayList<Particle>[] map = (ArrayList<Particle>[]) new ArrayList[seeds.size()];
       // for each seed
        for (int i = 0; i < seeds.size(); i++) {
            // add seed to map
            map[i] = new ArrayList<Particle>();
            map[i].add(seeds.get(i));
            // for each particle
            for (Particle j: sorted) {
                // if particle is seed, continue
                if (seeds.get(i) == j) continue;
                // if particle is within radius of seed, and in domain, add it
                if (seeds.get(i).position.distance(j.position) < radius && Function.inDomain(j.position, funcFlag)) {
                    map[i].add(j);
                }
            }
        }

        // iterate collection (map) and assign neighborhood bests (GBESTS)
        for (int i = 0; i < map.length; i++) {
            for (Particle inHood: map[i]) {
                inHood.gBest = Function.evaluate(seeds.get(i).position, funcFlag, objFlag);
                inHood.gBestVec = seeds.get(i).position.duplicate();
            }
        }
    }

    /**
     * duplicates the particles array and returns a sorted copy of this array.
     * Sorting with respect to objective function evaluation. The sorted array is 
     * returned in decreasing order of fitness. In the context of minimization, this is sorted
     * from highest -> lowest evaluation.
     * 
     * @param funcFlag ZDT function to be evaluated (1,2,3,4, or 6)
     * @param objFlag OBJ function, either 1 or 2
     * @return
     * @throws Exception
     */
    private Particle[] getSortedArray(byte funcFlag, byte objFlag) throws Exception {

        //duplicate particles[] to avoid aliasing changes
        Particle[] arr = new Particle[particles.length];
        for (int i = 0; i < particles.length; i++) {
            arr[i] = particles[i];
        }

        // create comparator to sort particle array
        Comparator<Particle> c = new Comparator<Particle>() {
            @Override
            public int compare(Particle o1, Particle o2) {
                try {
                    if (Function.evaluate(o1.position, funcFlag, objFlag) 
                        > Function.evaluate(o2.position, funcFlag, objFlag))
                        return -1;
                    if (Function.evaluate(o1.position, funcFlag, objFlag) 
                        < Function.evaluate(o2.position, funcFlag, objFlag))
                        return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };

        // sort arr using comparator
        Arrays.sort(arr, c);
        return arr;
    }

    private double[] getParams(byte flag) throws Exception {
        switch (flag) {
            case 1:
                double[] dat = { 0.475, 1.8, 1.1, 1.8 };
                return dat;
            case 2:
                double[] dat1 = { 0.075, 1.6, 1.35, 1.9 };
                return dat1;
            case 3:
                double[] dats = Utility.sampleControlParams();
                return dats;
            case 4:
                double[] dat3 = { 0.175, 1.85, 1.35, 1.85 };
                return dat3;
            case 6:
                double[] dat4 = { 0.6, 1.85, 1.55, 1.8 };
                return dat4;
            default:
                throw new Exception("Function Flag not recognized");
        }
    }

    /**
     * A single function call designed to catalyze a single particle update
     * iteration for each particle in the swarm
     *
     * The steps of the procedure include
     *
     * (1) attempt to update the pBest of each particle
     * (2) attempt to update the neighborhood best of each particle
     * (3) update the archive if possible to reflect this particle's current
     * position
     * (4) perform the velocity update procedure for this particle (involves
     * tournament selection first)
     * (5) perform the position update procedure for this particle
     */
    public void updateIteration() throws Exception {

        Vector r1 = Vector.ZeroOne(particles[0].dim);
        Vector r2 = Vector.ZeroOne(particles[0].dim);
        Vector r3 = Vector.ZeroOne(particles[0].dim);

        for (int i = 0; i < particles.length; i++) {
            // attempt pBest update for this particle if possible
            particles[i].attemptPBestUpdate();
            // update neighborhood best for this particle (using gBest currently)
            updateHoodBest(findGlobalBest());
            // attempt to add this particles position to the archive
            archive.tryArchiveAdd(particles[i].position.duplicate());
            // update particle to reflect new random vector
            particles[i].updateR1R2R3(r1, r2, r3);
            // perform the velocity update
            particles[i].velUpdate();
            // perform position update
            particles[i].positionUpdate();
        }
    }

    public void runVelUpdate() throws Exception {
        for (int i = 0; i < particles.length; i++) {
            particles[i].velUpdate();
        }
    }

    /**
     * Update neighborhood best position and evaluation of all particles in the
     * swarm
     * to reflect that of the provided argument
     * 
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
     * Finds the GLOBAL best position of all particles in this swarm. This uses a
     * start topology
     * neighborhood, which may not always be the intent. Assert that this is the
     * desired functionality.
     * 
     * @return
     * @throws Exception
     */
    public Vector findGlobalBest() throws Exception {
        Vector gBest = particles[0].pBestVec;
        double gBestEval = Function.evaluate(gBest, funcFlag, objFlag);
        // find new Gbest
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
     * Archive-reference all current candidate solutions. If a candidate solution is
     * found to be non-dominated
     * we should add this solution to the archive. All Archive entries which this
     * solution dominates should be
     * removed from the archive.
     *
     * Might not be used in this context, rather used on a 1 by 1 particle depth
     * function call
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

    public void printPBests() {
        for (Particle i : this.particles) {
            System.out.println(i.pBest);
        }
    }

    public String csvArchive(int z) throws Exception {
        return archive.csvPrint(z);
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
