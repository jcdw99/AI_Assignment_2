

public class Particle {

    public Vector position;
    public Vector pBestVec;
    public double pBest;
    public Vector gBestVec;
    public double gBest;
    public Vector velocity;
    public double w;
    public double c1;
    public double c2;
    public double c3;
    public double lambda;
    private Archive archive;
    private byte funcFlag;
    private byte objFlag;

    /** 
     * Initialization of a new particle
     */
    public Particle(int dim, byte funcFlag, byte objFlag, double w,  double c1, double c2, double c3, Archive arc) throws Exception {
        
        this.w = w;
        this.c1 =c1;
        this.c2 = c2;
        this.c3 = c3;
        this.lambda = Utility.calcLambda(w, c1, c2, c3);
        this.funcFlag = funcFlag;
        this.objFlag = objFlag;

        int[] getDomain = Function.getDomain(funcFlag);
        position = Vector.randBetween(getDomain[0], getDomain[1], dim);
        pBestVec = position.duplicate();
        pBest = Function.evaluate(position, funcFlag, objFlag);
        velocity = new Vector(dim);
        archive = arc;
        archive.tryArchiveAdd(position);

    } 

    /**
     * performs the velocity update equation of MGPSO 
     * @return
     * @throws Exception
     */
    public void velUpdate() throws Exception {

        Vector inWeight = velocity.scale(w);
        Vector cog = Vector.ZeroOne(velocity.size()).scale(c1).prod(pBestVec.sub(position));
        Vector soc = Vector.ZeroOne(velocity.size()).scale(lambda * c2).prod(gBestVec.sub(position));
        Vector arc = Vector.ZeroOne(velocity.size()).scale((1-lambda) * c3).prod(archive.tournySelect().sub(position));
        this.velocity = cog.add(inWeight).add(soc).add(arc);
    }

    /**
     * performs the position update equation of MGPSO
     * @throws Exception
     */
    public void positionUpdate() throws Exception {
        this.position = position.duplicate().add(velocity.duplicate());
    }

    /**
     * attempts to add the current position of this particle to the archive
     * @return boolean representing whether this particles position was added to the archive or not
     * @throws Exception
     */
    public boolean attemptArchiveAdd() throws Exception {
        return archive.tryArchiveAdd(position);
    }

    /**
     * attempts to update the pBest of this vector to reflect the current particle position
     * @return boolean representing whether this particle's pBest was updated or not
     * @throws Exception
     */
    public boolean attemptPBestUpdate() throws Exception {
        double thisEval = Function.evaluate(position, funcFlag, objFlag);
        if (thisEval < pBest && Function.inDomain(position, funcFlag)) {
            pBestVec = position.duplicate();
            pBest = thisEval;
            return true;
        }
        return false;
    }

    public String toString() {
        return position.toString();
    }


    

}