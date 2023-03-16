package ru.vsu.cs.yachnyy_m_a.task2_mlita;

public class LinearEquationSystem {
    private int[][] expanded_matrix;

    public LinearEquationSystem(int[][] expanded_matrix) {
        this.expanded_matrix = expanded_matrix;
    }

    public double[] solve() {
        if(expanded_matrix[0].length - expanded_matrix.length != 1) return null;

        long determinant = Matrix.determinant(Matrix.removeColumn(expanded_matrix, expanded_matrix[0].length));

        if(determinant == 0) return null;

        double[] res = new double[expanded_matrix.length];

        for (int i = 0; i < res.length; i++) {
            res[i] = Matrix.determinant(matrixForVariable(i + 1)) * 1.0 / determinant;
        }

        return res;
    }

    public int[][] matrixForVariable(int index){
        int[][] res = new int[expanded_matrix.length][expanded_matrix.length];
        for (int i = 0; i < expanded_matrix.length; i++) {
            for (int j = 0; j < expanded_matrix.length; j++) {
                res[i][j] = expanded_matrix[i][j == index - 1 ? expanded_matrix.length : j];
            }
        }
        return res;
    }

}
