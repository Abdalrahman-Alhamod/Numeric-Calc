import java.util.ArrayList;
import java.util.Arrays;

class Matrix {
    private ArrayList<ArrayList<Integer>> a; // ArrayList of ArrayLists to represent the matrix
    private int n; // Number of rows in the matrix
    private int m; // Number of columns in the matrix

    static final int mod = (int) (1e9 + 7); // A constant representing modulo value for arithmetic operations

    // Constructor to create an n x m matrix initialized with zeros
    public Matrix(int rows, int columns) {
        n = rows;
        m = columns;
        a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                row.add(0);
            }
            a.add(row);
        }
    }

    // Method to resize the matrix to X x Y and initialize with zeros
    public void resize(int rows, int columns) {
        n = rows;
        m = columns;
        a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                row.add(0);
            }
            a.add(row);
        }
    }

    // Method to get a specific row of the matrix
    public ArrayList<Integer> getRow(int row) {
        return a.get(row);
    }

    // Method to get the entire matrix data (all rows)
    public ArrayList<ArrayList<Integer>> getData() {
        return a;
    }

    // Method to assign the values of another matrix 'other' to this matrix
    public Matrix assign(Matrix other) {
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            ArrayList<Integer> otherRow = other.getRow(i);
            for (int j = 0; j < m; j++) {
                row.set(j, otherRow.get(j));
            }
        }
        return this;
    }

    // Method to assign a constant value 'c' to all elements of the matrix
    public Matrix assign(int c) {
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            Arrays.fill(row.toArray(), c);
        }
        return this;
    }

    // Method to add another matrix 'other' to this matrix
    public Matrix add(Matrix other) {
        Matrix sum = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            ArrayList<Integer> otherRow = other.getRow(i);
            ArrayList<Integer> sumRow = sum.getRow(i);
            for (int j = 0; j < m; j++) {
                sumRow.set(j, (row.get(j) + otherRow.get(j)) % mod); // Perform modulo arithmetic while adding elements
            }
        }
        return sum;
    }

    // Method to add a constant value 'c' to all elements of the matrix
    public Matrix add(int c) {
        Matrix sum = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            ArrayList<Integer> sumRow = sum.getRow(i);
            for (int j = 0; j < m; j++) {
                sumRow.set(j, (row.get(j) + c) % mod); // Perform modulo arithmetic while adding constant value
            }
        }
        return sum;
    }

    // Method to subtract another matrix 'other' from this matrix
    public Matrix subtract(Matrix other) {
        Matrix sub = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            ArrayList<Integer> otherRow = other.getRow(i);
            ArrayList<Integer> subRow = sub.getRow(i);
            for (int j = 0; j < m; j++) {
                subRow.set(j, (row.get(j) - otherRow.get(j) + mod) % mod); // Perform modulo arithmetic while subtracting elements
            }
        }
        return sub;
    }

    // Method to subtract a constant value 'c' from all elements of the matrix
    public Matrix subtract(int c) {
        Matrix sub = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            ArrayList<Integer> subRow = sub.getRow(i);
            for (int j = 0; j < m; j++) {
                subRow.set(j, (row.get(j) - c + mod) % mod); // Perform modulo arithmetic while subtracting constant value
            }
        }
        return sub;
    }

    // Method to multiply this matrix with another matrix 'other'
    public Matrix multiply(Matrix other) {
        Matrix product = new Matrix(n, other.m);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            ArrayList<ArrayList<Integer>> otherData = other.getData();
            ArrayList<Integer> productRow = product.getRow(i);
            for (int j = 0; j < other.m; j++) {
                for (int k = 0; k < m; k++) {
                    productRow.set(j, (productRow.get(j) + (row.get(k) * otherData.get(k).get(j)) % mod) % mod); // Perform modulo arithmetic while multiplying elements
                }
            }
        }
        return product;
    }

    // Method to multiply all elements of the matrix with a constant value 'c'
    public Matrix multiply(int c) {
        Matrix product = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = a.get(i);
            ArrayList<Integer> productRow = product.getRow(i);
            for (int j = 0; j < m; j++) {
                productRow.set(j, (row.get(j) * c) % mod); // Perform modulo arithmetic while multiplying with constant value
            }
        }
        return product;
    }

    static Matrix neutral; // A matrix representing the neutral element for matrix multiplication

    static void neutralize() {
        for (int i = 0; i < neutral.n; i++) {
            neutral.getRow(i).set(i, 1); // Set diagonal elements of the neutral matrix to 1
        }
    }

    static Matrix power(Matrix A, long p) {
        Matrix ans = neutral;
        while (p > 0) {
            if ((p & 1) == 1) {
                ans = ans.multiply(A); // Multiply ans with A if the current bit of p is 1
            }
            p >>= 1; // Right-shift p to check the next bit
            A = A.multiply(A); // Square A at each step
        }
        return ans;
    }

    static Matrix sumPow(Matrix A, long k) {
        if (k == 0) {
            return neutral; // If k is zero, return the neutral matrix
        }
        if (k % 2 == 0) {
            return neutral.add(A.multiply(sumPow(A, k - 1))); // Recursively calculate sumPow(A, k - 1) and add it to the neutral matrix
        }
        Matrix c = A.multiply(A);
        return neutral.add(A).multiply(sumPow(c, k / 2)); // Recursively calculate sumPow(c, k / 2) and multiply it with (neutral + A)
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Integer> row : a) {
            sb.append(a.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}