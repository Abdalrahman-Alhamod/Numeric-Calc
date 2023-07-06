import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * This class represents a matrix of double values and provides various operations on matrices.
 * <p>
 * The matrix is stored as an ArrayList of ArrayLists of Double values.
 */
class Matrix {
    /**
     * The matrix data is stored as an ArrayList of ArrayLists.
     * Each inner ArrayList represents a row of the matrix.
     */
    private ArrayList<ArrayList<Double>> a; // ArrayList of ArrayLists to represent the matrix
    /**
     * The number of rows in the matrix.
     */
    private int n;
    /**
     * The number of columns in the matrix.
     */
    private int m;

    /**
     * Creates an n x m matrix initialized with zeros.
     *
     * @param rows    the number of rows in the matrix
     * @param columns the number of columns in the matrix
     */
    public Matrix(int rows, int columns) {
        n = rows;
        m = columns;
        a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                row.add(0.0);
            }
            a.add(row);
        }
    }

    /**
     * Creates a matrix from the given data.
     *
     * @param data the matrix data as an ArrayList of ArrayLists
     * @throws NullPointerException if the data is null
     * @throws ArithmeticException  if the rows in the data have different sizes
     */
    public Matrix(ArrayList<ArrayList<Double>> data) {
        this.a = Objects.requireNonNull(data, "a cannot be null");
        int size = a.get(0).size();
        for (ArrayList<Double> row : a) {
            if (row.size() != size)
                throw new ArithmeticException("rows size is not identical");
        }
        n = a.size();
        m = size;
    }

    /**
     * Resizes the matrix to the given number of rows and columns and initializes all elements to zero.
     *
     * @param rows    the new number of rows
     * @param columns the new number of columns
     */
    public void resize(int rows, int columns) {
        n = rows;
        m = columns;
        a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                row.add(0.0);
            }
            a.add(row);
        }
    }

    /**
     * Returns the row at the specified index.
     *
     * @param index the row index
     * @return the row at the specified index
     */
    public ArrayList<Double> getRow(int index) {
        return a.get(index);
    }

    /**
     * Sets the row at the specified index to the given row.
     *
     * @param index the row index
     * @param row   the new row
     */
    public void setRow(int index, ArrayList<Double> row) {
        a.set(index, row);
    }

    /**
     * Returns the entire matrix data as an ArrayList of ArrayLists.
     *
     * @return the matrix data
     */
    public ArrayList<ArrayList<Double>> getData() {
        return a;
    }

    /**
     * Assigns the values of another matrix to this matrix.
     *
     * @param other the matrix to assign
     * @return this matrix after assigning the values of the other matrix
     * @throws ArithmeticException if the matrices have different sizes
     */
    public Matrix assign(Matrix other) {
        if (n != other.n || m != other.m)
            throw new ArithmeticException("Matrices dimensions mismatch");
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            ArrayList<Double> otherRow = other.getRow(i);
            for (int j = 0; j < m; j++) {
                row.set(j, otherRow.get(j));
            }
        }
        return this;
    }

    /**
     * Assigns a constant value to all elements of the matrix.
     *
     * @param c the constant value to assign
     * @return this matrix after assigning the constant value
     */
    public Matrix assign(double c) {
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            Collections.fill(row, c);
        }
        return this;
    }

    /**
     * Adds another matrix to this matrix and returns the result as a new matrix.
     *
     * @param other the matrix to add
     * @return the sum of the matrices as a new matrix
     * @throws ArithmeticException if the matrices have different sizes
     */
    public Matrix add(Matrix other) {
        if (n != other.n || m != other.m)
            throw new ArithmeticException("Matrices dimensions mismatch");
        Matrix sum = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            ArrayList<Double> otherRow = other.getRow(i);
            ArrayList<Double> sumRow = sum.getRow(i);
            for (int j = 0; j < m; j++) {
                sumRow.set(j, (row.get(j) + otherRow.get(j))); // Perform modulo arithmetic while adding elements
            }
        }
        return sum;
    }

    /**
     * Add a constant value to all elements of the matrix.
     *
     * @param c the constant value to Add
     * @return sum matrix after Adding the constant value
     */
    public Matrix add(double c) {
        Matrix sum = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            ArrayList<Double> sumRow = sum.getRow(i);
            for (int j = 0; j < m; j++) {
                sumRow.set(j, (row.get(j) + c)); // Perform modulo arithmetic while adding constant value
            }
        }
        return sum;
    }

    /**
     * Subtracts another matrix from this matrix and returns the result as a new matrix.
     *
     * @param other the matrix to subtract
     * @return the difference between the matrices as a new matrix
     * @throws ArithmeticException if the matrices have different sizes
     */
    public Matrix subtract(Matrix other) {
        if (n != other.n || m != other.m)
            throw new ArithmeticException("Matrices dimensions mismatch");
        Matrix sub = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            ArrayList<Double> otherRow = other.getRow(i);
            ArrayList<Double> subRow = sub.getRow(i);
            for (int j = 0; j < m; j++) {
                subRow.set(j, row.get(j) - otherRow.get(j)); // Perform modulo arithmetic while subtracting elements
            }
        }
        return sub;
    }

    /**
     * Subtract a constant value to all elements of the matrix.
     *
     * @param c the constant value to Subtract
     * @return sub matrix after Subtracting the constant value
     */
    public Matrix subtract(double c) {
        Matrix sub = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            ArrayList<Double> subRow = sub.getRow(i);
            for (int j = 0; j < m; j++) {
                subRow.set(j, row.get(j) - c); // Perform modulo arithmetic while subtracting constant value
            }
        }
        return sub;
    }

    /**
     * Multiplies this matrix by the given matrix.
     *
     * @param other the matrix to multiply by
     * @return a new matrix that is the result of multiplying this matrix by the other matrix
     * @throws ArithmeticException if the matrices have incompatible sizes for multiplication
     */
    public Matrix multiply(Matrix other) {
        if (this.m != other.n) {
            throw new ArithmeticException("Matrices dimensions mismatch");
        }
        Matrix product = new Matrix(n, other.m);
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            ArrayList<ArrayList<Double>> otherData = other.getData();
            ArrayList<Double> productRow = product.getRow(i);
            for (int j = 0; j < other.m; j++) {
                for (int k = 0; k < m; k++) {
                    productRow.set(j, productRow.get(j) + (row.get(k) * otherData.get(k).get(j))); // Perform modulo arithmetic while multiplying elements
                }
            }
        }
        return product;
    }

    /**
     * Multiplies this matrix by the given scalar.
     *
     * @param scalar the scalar to multiply by
     * @return a new matrix that is the result of multiplying this matrix by the scalar
     */
    public Matrix multiply(double scalar) {
        Matrix product = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = a.get(i);
            ArrayList<Double> productRow = product.getRow(i);
            for (int j = 0; j < m; j++) {
                productRow.set(j, row.get(j) * scalar); // Perform modulo arithmetic while multiplying with constant value
            }
        }
        return product;
    }

    /**
     * A matrix representing the neutral element for matrix multiplication
     */
    static Matrix neutral;

    /**
     * Initializes the static neutral matrix to the identity matrix of the given size.
     */
    static void neutralize() {
        for (int i = 0; i < neutral.n; i++) {
            neutral.getRow(i).set(i, 1.0); // Set diagonal elements of the neutral matrix to 1
        }
    }

    /**
     * Computes the power of the given matrix A to the given non-negative integer exponent p.
     * Returns the resulting matrix.
     *
     * @param A the matrix to compute the power of
     * @param p the non-negative integer exponent to raise the matrix to
     * @return the resulting matrix
     */
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

    /**
     * Computes the sum of the powers of the given matrix A up to the given non-negative integer exponent k.
     * Returns the resulting matrix.
     *
     * @param A the matrix to compute the powers of
     * @param k the non-negative integer exponent to sum the powers up to
     * @return the resulting matrix
     */
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

    /**
     * Returns a string representation of the matrix.
     *
     * @return a string representation of the matrix
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Double> row : a) {
            sb.append(row.toString());
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Solves the system of linear equations represented by the matrix using <b>Gaussian elimination</b>.
     * <p>
     * Returns an ArrayList of solutions to the system of equations, or null if there is no unique solution.
     *
     * @return an ArrayList of solutions to the system of equations, or null if there is no unique solution
     */
    public ArrayList<Double> solve() {
        int rowCount = n;
        int colCount = m;

        int lead = 0;
        for (int r = 0; r < rowCount; r++) {
            if (lead >= colCount)
                break;

            int i = r;
            while (a.get(i).get(lead) == 0) {
                i++;
                if (i == rowCount) {
                    i = r;
                    lead++;
                    if (lead == colCount)
                        return null; // No unique solution
                }
            }

            // Swap rows i and r
            ArrayList<Double> temp = a.get(i);
            a.set(i, a.get(r));
            a.set(r, temp);

            double lv = a.get(r).get(lead);
            for (int j = 0; j < colCount; j++)
                a.get(r).set(j, a.get(r).get(j) / lv);

            for (i = 0; i < rowCount; i++) {
                if (i != r) {
                    double lv2 = a.get(i).get(lead);
                    for (int j = 0; j < colCount; j++)
                        a.get(i).set(j, a.get(i).get(j) - a.get(r).get(j) * lv2);
                }
            }

            lead++;
        }
        //System.out.println(a);
        // Extract the solution from the transformed matrix
        ArrayList<Double> solution = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            double val = (i < colCount) ? a.get(i).get(colCount - 1) : 0.0;
            solution.add(val);
        }

        return solution;
    }

    /**
     * Returns a <b>Vandermonde</b> matrix of the given x values.
     *
     * @param xp an ArrayList of x values
     * @return the Vandermonde matrix of the given x values
     */
    static Matrix getVandermonde(ArrayList<Double> xp) {
        ArrayList<ArrayList<Double>> a = new ArrayList<>();
        for (int i = 0; i < xp.size(); i++) {
            ArrayList<Double> v = new ArrayList<>();
            double x = xp.get(i);
            for (int j = 0; j < xp.size(); j++) {
                v.add(Math.pow(x, j));
            }
            a.add(v);
        }
        return new Matrix(a);
    }

    /**
     * Returns the number of columns in the matrix.
     *
     * @return the number of columns in the matrix
     */
    public int getColsNum() {
        return m;
    }

    /**
     * Sets the number of columns in the matrix to the given value.
     *
     * @param cols the new number of columns in the matrix
     */
    public void setColsNum(int cols) {
        m = cols;
    }
}