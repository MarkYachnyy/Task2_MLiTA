package ru.vsu.cs.yachnyy_m_a.task2_mlita.util;

import ru.vsu.cs.yachnyy_m_a.task2_mlita.LinearEquationSystem;
import ru.vsu.cs.yachnyy_m_a.task2_mlita.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormMain extends JFrame {
    private JPanel PanelMain;
    private JButton ButtonLoadMatrixFromFile;
    private JButton ButtonSolveSystem;
    private JLabel LabelAnswer;
    private JTextArea TextAreaSystem;

    private JFileChooser InputFileChooser;

    private int[][] matrix;

    public FormMain() {
        this.setTitle("Решение СЛУ методом Крамера");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(PanelMain);
        this.pack();

        InputFileChooser = new JFileChooser();
        InputFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("text files", "txt"));
        InputFileChooser.setCurrentDirectory(new File("."));

        TextAreaSystem.setEditable(false);

        ButtonLoadMatrixFromFile.addActionListener(event -> {
            if (InputFileChooser.showOpenDialog(PanelMain) == JFileChooser.APPROVE_OPTION) {
                matrix = ArrayUtils.readIntArray2FromFile(InputFileChooser.getSelectedFile().getPath());
                SetTextInInputArea();
                LabelAnswer.setText("-");
            }
        });

        ButtonSolveSystem.addActionListener(event -> {
            double[] solution = new LinearEquationSystem(matrix).solve();
            if (solution == null) {
                LabelAnswer.setText("Система либо несовместная, либо неопределённая");
            } else {
                LabelAnswer.setText(IntStream.range(0, solution.length).
                        mapToObj(i -> "x" + (i + 1) + " = " + (solution[i] % 1 == 0 ? String.valueOf((int) solution[i]) : solution[i])).
                        collect(Collectors.joining("; ")));
            }
        });

        this.pack();
    }

    private void SetTextInInputArea() {
        StringBuilder res = new StringBuilder();
        for (int[] row : matrix) {
            res.append(IntStream.range(0, row.length - 1).
                    mapToObj(i -> (i > 0 ? " " + (row[i] < 0 ? '-' : '+') + " " : "") + Math.abs(row[i]) + "*x" + (i + 1)).
                    collect(Collectors.joining())).append(" = ").append(row[row.length - 1]).append('\n');
        }
        TextAreaSystem.setText(res.toString());
    }
}
