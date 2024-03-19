package gui;

import korisnici.Korisnici;
import korisnici.Kozmeticar;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.UtilDateModel;
import tretmani.TipTretmana;
import tretmani.Tretmani;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Properties;

public class KozmeticariIzvestaj extends JFrame {
    private JScrollPane tabelaPanel;
    private JPanel KizvestajPanel;
    private JButton dodajBonusIzvrseniButton;
    private JButton dodajBonusPrihodiButton;

    public KozmeticariIzvestaj() {
        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();

        JDateComponentFactory fabrika = new JDateComponentFactory();
        JDatePicker datePickerOd = fabrika.createJDatePicker();
        JDatePicker datePickerDo = fabrika.createJDatePicker();

        Object[] components = {"Od:", datePickerOd, "Do:", datePickerDo};
        Object[] optionsDate = {"OK", "Cancel"};
        LocalDate datumOd = null;
        LocalDate datumDo = null;


        int option = JOptionPane.showOptionDialog(null, components, "Date Picker", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, optionsDate, optionsDate[0]);
        if (option == JOptionPane.OK_OPTION) {
            GregorianCalendar selectedDate = (GregorianCalendar) datePickerOd.getModel().getValue();
            datumOd = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            selectedDate = (GregorianCalendar) datePickerDo.getModel().getValue();
            datumDo = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            System.out.println("Selected date: " + datumOd + " " + datumDo);
        } else {
            System.out.println("User cancelled.");
        }

        setTitle("Svi korisnici");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Object[] kozmeticari = Korisnici.sviKozmeticari.values().toArray();
        String[] header = {"Ime", "Prezime", "Broj izvrsenih", "Ukupan prihod", "Bonus"};
        Object[][] sadrzaj = new Object[kozmeticari.length][13];
        int i = 0;
        for (Object o : kozmeticari) {
            Kozmeticar k = (Kozmeticar) o;
            sadrzaj[i][0] = k.getIme();
            sadrzaj[i][1] = k.getPrezime();
            sadrzaj[i][2] = Tretmani.brojTretmana(k, datumOd, datumDo);
            sadrzaj[i][3] = Tretmani.prihodKozmeticara(k, datumOd, datumDo);
            sadrzaj[i][4] = k.getBonus();
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(sadrzaj, header) {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        model.setColumnIdentifiers(header);
        JTable tabelaIspis = new JTable(model);


        tabelaPanel.setViewportView(tabelaIspis);
        setContentPane(KizvestajPanel);
        tabelaIspis.setFocusable(false);
        tabelaIspis.setRowSelectionAllowed(true);
        setVisible(true);

        LocalDate finalDatumOd = datumOd;
        LocalDate finalDatumDo = datumDo;
        dodajBonusIzvrseniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog("Unesite broj min broj tretmana za bonus:");

                if (userInput != null) {
                    int broj = Integer.parseInt(userInput);
                    for (Kozmeticar k : Korisnici.sviKozmeticari.values()) {
                        if (Tretmani.brojTretmana(k, finalDatumOd, finalDatumDo) >= broj) {
                            k.setBonus(5000);
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Dodavanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    System.out.println("User canceled the input.");
                }
            }
        });
        dodajBonusPrihodiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog("Unesite broj min prihod za bonus:");

                if (userInput != null) {
                    int broj = Integer.parseInt(userInput);
                    for (Kozmeticar k : Korisnici.sviKozmeticari.values()) {
                        if (Tretmani.prihodKozmeticara(k, finalDatumOd, finalDatumDo) >= broj) {
                            k.setBonus(5000);
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Dodavanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    System.out.println("User canceled the input.");
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        KizvestajPanel = new JPanel();
        KizvestajPanel.setLayout(new GridBagLayout());
        tabelaPanel = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        KizvestajPanel.add(tabelaPanel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        KizvestajPanel.add(spacer1, gbc);
        dodajBonusIzvrseniButton = new JButton();
        dodajBonusIzvrseniButton.setText("Dodaj bonus izvrseni");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        KizvestajPanel.add(dodajBonusIzvrseniButton, gbc);
        dodajBonusPrihodiButton = new JButton();
        dodajBonusPrihodiButton.setText("Dodaj bonus prihodi");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        KizvestajPanel.add(dodajBonusPrihodiButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        KizvestajPanel.add(spacer2, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return KizvestajPanel;
    }

}
