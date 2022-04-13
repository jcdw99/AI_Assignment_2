
import java.util.ArrayList;
import java.util.Arrays;

public class Archive {

    private Vector[] entries;
    private int size;
    private int tournySize;
    private byte funcFlag;

    public Archive(int maxEntries, byte funcFlag) {
        entries = new Vector[maxEntries];
        this.size = 0;
        this.tournySize = 3;
        this.funcFlag = funcFlag;

    }

    /**
     * attempts to add the specified candidate solution to the archive. If the archive is full, but this solution
     * is non-dominated, entries are removed according to crowding distance
     *
     * if the candidate solution is dominated by entries currently in the archive, this add attempt is disregarded
     *
     * @param candidate
     * @return true if the candidate solution was added to the archive, false otherwise
     * @throws Exception
     */
    public boolean tryArchiveAdd(Vector candidate) throws Exception {
        if (!Function.inDomain(candidate, funcFlag))
            return false;
        if (!isDominated(candidate)) {
            Vector[] dominates = removeDominated(candidate);
            if (size < this.entries.length) {
                entries[size] = candidate;
                size ++;
                return true;
            } else {

                // init most crowded vector
                Vector v = new Vector(entries[0].size());
                // init most crowded val;
                double crowdAmount = Double.MIN_VALUE;
                // init most crowd index = 0;
                int crowdDex = 0;

                // for each particle in swarm
                for (int i = 0; i < size; i++) {
                    double thisCrowd = crowdingDistance(entries[i]);
                    if (thisCrowd > crowdAmount) {
                        crowdDex = i;
                        v = entries[i];
                        crowdAmount = thisCrowd;
                    }
                }
                entries[crowdDex] = v.duplicate();
            }

        }
        return false;
    }

    /**
     * determines if the supplied candidate solution is dominated by any entry currently in the archive.
     * The negation of this returned value is NOT (isDominant) but rather (isNondominated)
     * @param candidate
     * @return
     * @throws Exception   Done
     */
    public boolean isDominated(Vector candidate) throws Exception {
        for (int i = 0;  i < size; i++) {
            // thing in the archive
            Vector thisEntry = entryF1F2Form(entries[i]);
            if (thisEntry.dominates(entryF1F2Form(candidate)))
                return true;
        }
        return false;
    }

    /**
     * translates this vector which is in multidimensional form, to a point (F1, F2)
     * @param candidate
     * @return
     * @throws Exception  Done
     */
    private Vector entryF1F2Form(Vector candidate) throws Exception {
        double[] data = {Function.f1(candidate, funcFlag), Function.f2(candidate, funcFlag)};
        return new Vector(data);
    }
    /**
     * remove all vectors that are dominated by a provided candidate solution
     * The removed entries are returned as a list of vectors.
     *
     * Importantly, the removed values create gaps in the entries[], therefore once all
     * solutions are removed, remaining entries are shifted downwards and gaps are filled.
     * @param cand
     * @return
     */
    private Vector[] removeDominated(Vector cand) throws Exception {
        ArrayList<Vector> dominated = new ArrayList<>();
        Vector candidate = entryF1F2Form(cand);
        //remove dominated entries
        int origSize = size;
        for (int i = 0; i < origSize; i++) {
            Vector exist = entryF1F2Form(entries[i]);
            if (candidate.dominates(exist)) {
                dominated.add(exist);
                entries[i] = null;
                size --;
            }
        }
        //fix gaps in entries[] as a result of removing
        Vector[] newEntries = new Vector[entries.length];
        int index = 0;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null)
                newEntries[index++] = entries[i];
        }
        entries = newEntries;
        return dominated.toArray(new Vector[dominated.size()]);
    }

    public Vector tournySelect() throws Exception {
        Vector[] vecs = new Vector[tournySize];
        for (int i = 0; i < tournySize; i++) {
            vecs[i] = entries[(int) (Math.random() * size)];
        }
        int minDex = 0;
        double bestDist = Double.MIN_VALUE;
        for (int i = 0; i < vecs.length; i++) {
            if (crowdingDistance(vecs[i]) > bestDist) {
                minDex = i;
            }
        }

        return vecs[minDex].duplicate();
    }

    /**
     * compute the crowding distance of the supplied vector according to textbook formula
     * @param x
     * @return
     * @throws Exception
     */
    public double crowdingDistance(Vector x) throws Exception {

        Vector candidate = entryF1F2Form(x);

        double[] F1 = new double[size];
        double[] F2 = new double[size];
        for (int i = 0; i < size; i++) {
            Vector ent = entryF1F2Form(entries[i]);
            F1[i] = ent.atIndex(0);
            F2[i] = ent.atIndex(1);
        }
        Arrays.sort(F1);
        Arrays.sort(F2);

        int f1dex = 0;
        int f2dex = 0;
        double f1XEval = candidate.atIndex(0);
        double f2XEval = candidate.atIndex(1);
        for (int i = 0; i < size; i++) {
            if (F1[i] == f1XEval)
                f1dex = i;
            if (F2[i] == f2XEval)
                f2dex = i;
        }
        if (f1dex == 0 || f1dex == size - 1 || f2dex == 0 || f2dex == size - 1)
            return Double.MAX_VALUE;
        else {
            double f1Diff = (Math.abs((F1[f1dex + 1] - F1[f1dex])) + Math.abs((F1[f1dex] - F1[f1dex - 1]))) / 2;
            double f2Diff = (Math.abs((F2[f2dex + 1] - F2[f2dex])) + Math.abs((F2[f2dex] - F2[f2dex - 1]))) / 2;
            return f1Diff + f2Diff;
        }

    }

    public String csvPrint() throws Exception {
        StringBuilder b = new StringBuilder();
        b.append("F1, F2\n");
        for (int i = 0; i < size; i++) {
            Vector v = entryF1F2Form(entries[i]); 
            b.append(v.atIndex(0));
            b.append(",");
            b.append(v.atIndex(1));
            if (i < size - 1)
                b.append("\n");
        }
        return b.toString();
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("-----ARCHIVE-----\n");
        b.append("\t[\n");
        int nonNull = 0;
        for (Vector i: entries) {
            if (i != null) {
                nonNull++;
                try {
                    b.append("\t  " + i.toString().replace("\n", "") + "  => " + entryF1F2Form(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                b.append("\t  [ null ]\n");
            }
        }
        b.append(String.format("EntryCount: %s/%s\n", nonNull, entries.length));
        b.append("\n-----ARCHIVE-----\n");
        return b.toString();
    }
}