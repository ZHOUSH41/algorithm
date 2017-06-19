import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by zhou on 2017/6/18.
 */
public class Percolation{
    private int gridLength;
    private boolean[] isOpen;
    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF fullness;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private int numberOfOpenSite;

    private void checkBound(int i,int j)
    {
        if (i < 1 || i> gridLength){
            throw new IndexOutOfBoundsException();
        }
        if(j < 1 || j > gridLength){
            throw new IndexOutOfBoundsException();
        }
    }

    private int siteIndex(int i, int j)
    {
        checkBound(i,j);
        int x = j;
        int y = i;
        return (y - 1) * gridLength + (x);
    }

    public Percolation(int N)
    {
        if (N < 1)
        {
            throw new IllegalArgumentException();
        }
        gridLength = N;
        int arraySize = N*N + 2;
        isOpen = new boolean[arraySize];

        virtualTopIndex = 0;
        virtualBottomIndex = (N*N) + 1;

        isOpen[virtualTopIndex] = true;
        isOpen[virtualBottomIndex] = true;

        percolation = new WeightedQuickUnionUF(arraySize);
        fullness = new WeightedQuickUnionUF(arraySize);

        for(int j = 1; j <= N;j++)
        {
            int i = 1;
            int topSiteIndex = siteIndex(i,j);
            percolation.union(virtualTopIndex,topSiteIndex);
            fullness.union(virtualTopIndex,topSiteIndex);

            i = N;
            int bottomSiteIndex= siteIndex(i,j);
            percolation.union(virtualBottomIndex,bottomSiteIndex);

        }
    }

    public boolean isOpen(int i,int j)
    {
        int siteIndex = siteIndex(i,j);
        return isOpen[siteIndex];
    }
    public void open(int i, int j) {
        int siteIndex = siteIndex(i, j);
        if (!isOpen[siteIndex]) {
            isOpen[siteIndex] = true;
            numberOfOpenSite++;
            if (j > 1 && isOpen(i, j - 1)) {
                int indexToLeft = siteIndex(i, j - 1);
                percolation.union(siteIndex, indexToLeft);
                fullness.union(siteIndex, indexToLeft);
            }
            if (j < gridLength && isOpen(i, j + 1)) {
                int indexToRight = siteIndex(i, j + 1);
                percolation.union(siteIndex, indexToRight);
                fullness.union(siteIndex, indexToRight);
            }
            if (i > 1 && isOpen(i - 1, j)) {
                int indexToTop = siteIndex(i - 1, j);
                percolation.union(siteIndex, indexToTop);
                fullness.union(siteIndex, indexToTop);
            }
            if (i < gridLength && isOpen(i + 1, j)) {
                int indexToBottom = siteIndex(i + 1, j);
                percolation.union(siteIndex, indexToBottom);
                fullness.union(siteIndex, indexToBottom);
            }
        }
    }
    public boolean isFull(int i,int j)
    {
        int siteIndex = siteIndex(i, j);
        return (fullness.connected(virtualTopIndex, siteIndex) && isOpen[siteIndex]);
    }

    public boolean percolates()
    {
        if(gridLength > 1)
        {
            return percolation.connected(virtualTopIndex,virtualBottomIndex);
        }
        else
        {
            return isOpen[siteIndex(1,1)];
        }
    }
    public int numberOfOpenSites()
    {
        return numberOfOpenSite;
    }

    public static void  main(String[] args)
    {
        Percolation percolation = new Percolation(1);
        System.out.println(percolation.percolates());
        percolation.open(1,1);
        System.out.println(percolation.percolates());
        Percolation percolation2 = new Percolation(2);
        System.out.println(percolation2.percolates());
        percolation2.open(1,1);
        System.out.println(percolation2.percolates());
        percolation2.open(2,1);
        System.out.println(percolation2.percolates());
    }
}