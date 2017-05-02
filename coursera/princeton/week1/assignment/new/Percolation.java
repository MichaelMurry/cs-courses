import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;

public class Percolation {
   
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF ufPerc;
	private int[][] site;
	private int n;
	private int vtop;
	private int vbot;

	// create n-by-n grid, with all sites blocked
    public Percolation(int N) {               
    	// check input bound

    	n = N;
    	uf = new WeightedQuickUnionUF(n*n + 2);
    	ufPerc = new WeightedQuickUnionUF(n*n + 2);
    	site = new int[n][n];
    	vtop = n*n;
    	vbot = n*n + 1
    }

	public void checkInputBounds(int row, int col) {
		// check if row and col are outside prescribed range 
		if ((row < 1 || col < 1 || row > n || col > n)) {
			throw new java.lang.IndexOutOfBoundsException();	
		}
	}

    // convert 2d array index to 1d array index
    public int convert2dTo1dIndex(int row, int col) {
    	checkInputBounds(row, col);

    	return (row-1) * n + (col-1);
    }

    // open site (row, col) if it is not open already
   	public void open(int row, int col) {
    	checkInputBounds(row, col);

   		// check if already open
   		if (isOpen(row, col)) return;
		site[row-1][col-1] = 1;

   		// convert 2d array index to 1d array index
   		int indx = convert2dTo1dIndex(row, col);

   		// if top row, connect site to virtual top site in ufPerc
   		if (row == 1 && !uf.connected(indx, top)) {
   			ufPerc.union(indx, vtop);
   		}

   		// if bot row, connect site to virtual bot site in ufPerc
   		if (row == n) {
   			ufPerc.union(indx, vbottom);
   		}

		// check if we're on the right edge of grid
		if ((col+1) <= n) {
			// check if right neighbor site is open, if so, connect
			if (isOpen(row, col + 1)) {
		 		uf.union(indx, (row - 1)*n + col);
		 		ufPerc.union(indx, (row - 1)*n + col);
			}
		}

		// check if we're on the left edge of grid
		if ((col-1) > 0) {
			// check if left neighbor site is open, if so, connect
			if (isOpen(row, col-1)) {
		 		uf.union(indx, (row-1)*n + (col-2));
		 		ufPerc.union(indx, (row-1)*n + (col-2));
			}
		}

		// check if we're on the top edge of grid
		if ((row-1) > 0) {
			// check if top neighbor site is open, if so, connect
			if (isOpen(row-1, col)) {
		 		uf.union(indx, (row-2)*n + (col-1));
		 		ufPerc.union(indx, (row-2)*n + (col-1));
			}
		}

		// check if we're on the bottom edge of grid
		if ((row + 1) <= n) {
			// check if bottom neighbor site is open, if so, connect
			if (isOpen(row + 1, col)) {
		 		uf.union(indx, row*n + col - 1);
		 		ufPerc.union(indx, row*n + col - 1);
			}
		}
   	}    

   	// is site (row, col) open?
   	public boolean isOpen(int row, int col) {
    	checkInputBounds(row, col);

		return site[row-1][col-1] == 1;
   	}

   	// print out Grid
	public void printGrid() {
		for (int[] row : site) {
			System.out.println(Arrays.toString(row));
		}
	}

	// is site (row, col) full?
   	public boolean isFull(int row, int col) {
    	checkInputBounds(row, col);
		
		if (!isOpen(row, col)) {
			return false;
		}	

   		// convert 2d array index to 1d array index
   		int indx = convert2dTo1dIndex(row, col);

   		// check if connected with top row
		for (int i = 0; i < n; i++) {
			if (uf.connected(i, indx)) {
				return true;
			}
		}
		return false;	
	}

	// number of open sites
   	public int numberOfOpenSites() {

   	}    
   	
   	// does the system percolate?
   	public boolean percolates() {
   		if (ufPerc.connected(vtop, vbottom)){
   			return true;
   		}
   		return false;
   	}           

    // test client
    public static void main(String[] args){
    	Percolation tst = new Percolation(6);
    	tst.open(1,1);
    	tst.open(1,3);
    	tst.open(2,2);
    	tst.open(2,3);
    	tst.open(3,2);
    	tst.percolates();

    	tst.printGrid();


    } 
}