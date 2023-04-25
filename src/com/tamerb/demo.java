package com.tamerb;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class demo extends Component {

    public JButton dosyaSec1;
    private JPanel panel1;

    private JTextField field1;
    private JButton dosyaSec2;
    private JTextField field2;

    private JButton matrixGoster;
    private JButton sonucGoster;
    private JTextField textFieldHiza1;
    private JTextField textFieldHiza2;

    private JTextField lenField1;
    private JTextField lenField2;
    private JButton export;


    private final int GAP = -2;
    private final int MATCH = 1;
    private final int MISMATCH = -1;

    private int matchValue = 0;


    int[][] matrix;
    private String rowSequence;
    private String colSequence;
    private int rowLen;
    private int colLen;

    JTable table;
    static JFrame frame = new JFrame("demo");

    public demo() {


        matrixGoster.addActionListener(actionEvent -> {
            table = new JTable(rowLen + 1, colLen + 1);

            matrix = new int[rowLen + 1][colLen + 1];

            // iç matris değerlerimizi algortimazmıdaki 3 farklı fonksiyona göre max değerleriyle doldurduk
            for (int i = 1; i <= rowLen; i++) {
                for (int j = 1; j <= colLen; j++) {

                    // iç matris değerlerimizi dolduruyoruz
                    if (rowSequence.charAt(i - 1) == colSequence.charAt(j - 1)) {
                        matchValue = MATCH;
                    } else {
                        matchValue = MISMATCH;
                    }
                    int a = matrix[i][j - 1] + GAP;
                    int b = matrix[i - 1][j - 1] + matchValue;
                    int c = matrix[i - 1][j] + GAP;
                    matrix[i][j] = Math.max(0, Math.max(a, Math.max(b, c)));
                    table.setValueAt(matrix[i][j], i, j);

                }
            }


            Font font = new Font("Verdana", Font.PLAIN, 12);
            table.setFont(font);
            table.setRowHeight(30);
            frame.setSize(600, 400);
            frame.add(new JScrollPane(table));
            frame.setVisible(true);
        });


        dosyaSec1.addActionListener(actionEvent -> {

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                    int value = Integer.parseInt(br.readLine());
                    String stringValue = br.readLine();
                    lenField1.setText(String.valueOf(value));
                    field1.setText(stringValue);
                    rowSequence = stringValue;
                    rowLen = value;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dosyaSec2.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                    int value = Integer.parseInt(br.readLine());
                    String stringValue = br.readLine();
                    lenField2.setText(String.valueOf(value));
                    colLen = value;
                    field2.setText(stringValue);
                    colSequence = stringValue;
                    colLen = value;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // traceback işlemi gerçekleştirir
        sonucGoster.addActionListener(actionEvent -> {
            StringBuilder hiza1Builder = new StringBuilder();
            StringBuilder hiza2Builder = new StringBuilder();

            // en buyuk degerın indeksini bulur
            int[] indeksler = new int[2];
            int maxDeger = matrix[0][0];

            for (int i = 0; i < rowLen; i++) {
                for (int j = 0; j < colLen; j++) {
                    if (matrix[i][j] > maxDeger) {
                        maxDeger = matrix[i][j];
                        indeksler[0] = i;
                        indeksler[1] = j;
                    }
                }
            }

            int row = indeksler[0];
            int col = indeksler[1];

            while (matrix[row][col] != 0) {
                if (matrix[row][col] == matrix[row - 1][col - 1] + matchValue) {
                    hiza1Builder.append(rowSequence.charAt(row - 1));
                    hiza2Builder.append(colSequence.charAt(col - 1));
                    row--;
                    col--;
                } else if (matrix[row][col] == matrix[row - 1][col] + GAP) {
                    hiza1Builder.append(rowSequence.charAt(row - 1));
                    hiza2Builder.append("-");
                    row--;
                } else if (matrix[row][col] == matrix[row][col - 1] + GAP) {
                    hiza1Builder.append("-");
                    hiza2Builder.append(colSequence.charAt(col - 1));
                    col--;

                }
            }

            String hiza1 = hiza1Builder.reverse().toString();
            String hiza2 = hiza2Builder.reverse().toString();
            textFieldHiza1.setText(hiza1);
            textFieldHiza2.setText(hiza2);


        });

     /*   export.addActionListener(actionEvent -> {
            try {
                FileOutputStream fileOut = new FileOutputStream("/home/tamerb/Documents/smith-waterman.xlsx");
                PrintWriter printWriter = new PrintWriter(fileOut);
                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        printWriter.print(table.getValueAt(i, j) + "\t");
                    }
                    printWriter.println();
                }
                printWriter.close();
                JOptionPane.showMessageDialog(null, "Dosya başarıyla kaydedildi.");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Dosya kaydedilemedi." + e);
            }
        });*/
    }


    public static void main(String[] args) throws
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame.setTitle("needleman wunsch algoritması");
        frame.setContentPane(new demo().panel1);
        frame.getContentPane().setPreferredSize(new Dimension(1100, 600));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

}

