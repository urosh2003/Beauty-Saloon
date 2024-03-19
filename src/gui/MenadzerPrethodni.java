package gui;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Kozmeticar;
import tretmani.Tretmani;
import tretmani.VrstaTretmana;
import tretmani.ZakazanTretman;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MenadzerPrethodni extends JFrame {
    private JPanel prethodniPanel;
    private JButton otkaziButton;
    private JButton nijeSePojavioButton;
    private JButton izvrsenButton;
    private JButton obrisiTretmanButton;
    private JScrollPane tabelaPanelPrethodni;


    public MenadzerPrethodni() {
        setTitle("Zakazani Tretmani");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ArrayList<ZakazanTretman> zakazani = Tretmani.odrediPrethodneTretmane();
        String[] header = {"Naziv tretmana", "Datum i vreme", "Kozmeticar", "Klijent", "Cena", "Status"};
        Object[][] sadrzaj = new Object[zakazani.size()][6];
        int i = 0;
        for (ZakazanTretman zakazan : zakazani) {
            sadrzaj[i][0] = zakazan.getNaziv();
            sadrzaj[i][1] = zakazan.getDatum_i_vreme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"));
            sadrzaj[i][2] = zakazan.getKozmeticar().getIme() + " " + zakazan.getKozmeticar().getPrezime();
            sadrzaj[i][3] = zakazan.getKlijent().getIme() + " " + zakazan.getKlijent().getPrezime();
            sadrzaj[i][4] = zakazan.getCena();
            sadrzaj[i][5] = zakazan.getStatus();
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(sadrzaj, header) {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                if (mColIndex == 5 || mColIndex == 4) {
                    return false;
                }
                return true;
            }
        };

        model.setColumnIdentifiers(header);
        JTable tabelaIspis = new JTable(model);
        TableModelListener listener = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int red = e.getFirstRow();
                    int kolona = e.getColumn();
                    Object data = tabelaIspis.getModel().getValueAt(red, kolona);
                    ZakazanTretman zakazan = zakazani.get(red);
                    VrstaTretmana vrstaTretmana = zakazan.getVrsta();
                    LocalDateTime datum_i_vreme = zakazan.getDatum_i_vreme();
                    Kozmeticar kozmeticar = zakazan.getKozmeticar();
                    Korisnik klijent = zakazan.getKlijent();
                    if (kolona == 0) {
                        vrstaTretmana = Tretmani.nadjiVrstu((String) data);
                    }
                    if (kolona == 1) {
                        datum_i_vreme = LocalDateTime.parse((String) data, DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"));
                        datum_i_vreme.minusMinutes(datum_i_vreme.getMinute());
                    }
                    if (kolona == 2) {
                        String[] imePrezime = ((String) data).split(" ");
                        kozmeticar = (Kozmeticar) Korisnici.nadjiKorisnika(imePrezime[0], imePrezime[1]);
                    }
                    if (kolona == 3) {
                        String[] imePrezime = ((String) data).split(" ");
                        klijent = Korisnici.nadjiKorisnika(imePrezime[0], imePrezime[1]);
                    }
                    Tretmani.sviZakazaniTretmani.get(zakazan.idGetter()).editZakazanTretman(vrstaTretmana, datum_i_vreme, kozmeticar, klijent);
                }
            }
        };
        tabelaIspis.getModel().addTableModelListener(listener);
        tabelaPanelPrethodni.setViewportView(tabelaIspis);
        setContentPane(prethodniPanel);
        tabelaIspis.setFocusable(false);
        tabelaIspis.setRowSelectionAllowed(true);
        setVisible(true);


        otkaziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZakazanTretman tretman = zakazani.get(tabelaIspis.getSelectedRow());
                int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Tretmani.otkazi(tretman);
                    JOptionPane.showMessageDialog(null, "Otkazivanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } else {
                    System.out.println("Cancelled.");
                }
            }
        });
        nijeSePojavioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZakazanTretman tretman = zakazani.get(tabelaIspis.getSelectedRow());
                int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Tretmani.nijePojavio(tretman);
                    JOptionPane.showMessageDialog(null, "Azuriranje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } else {
                    System.out.println("Cancelled.");
                }
            }
        });
        izvrsenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZakazanTretman tretman = zakazani.get(tabelaIspis.getSelectedRow());
                int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Tretmani.izvrsi(tretman);
                    JOptionPane.showMessageDialog(null, "Izvrsavanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } else {
                    System.out.println("Cancelled.");
                }
            }
        });
        obrisiTretmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZakazanTretman tretman = zakazani.get(tabelaIspis.getSelectedRow());
                int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Tretmani.obrisiZakazan(tretman);
                    JOptionPane.showMessageDialog(null, "Brisanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } else {
                    System.out.println("Cancelled.");
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
        prethodniPanel = new JPanel();
        prethodniPanel.setLayout(new GridBagLayout());
        tabelaPanelPrethodni = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        prethodniPanel.add(tabelaPanelPrethodni, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        prethodniPanel.add(spacer1, gbc);
        otkaziButton = new JButton();
        otkaziButton.setText("Otkazi");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prethodniPanel.add(otkaziButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prethodniPanel.add(spacer2, gbc);
        nijeSePojavioButton = new JButton();
        nijeSePojavioButton.setText("Nije se pojavio");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prethodniPanel.add(nijeSePojavioButton, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prethodniPanel.add(spacer3, gbc);
        izvrsenButton = new JButton();
        izvrsenButton.setText("Izvrsen");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prethodniPanel.add(izvrsenButton, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prethodniPanel.add(spacer4, gbc);
        obrisiTretmanButton = new JButton();
        obrisiTretmanButton.setText("Obrisi tretman");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prethodniPanel.add(obrisiTretmanButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return prethodniPanel;
    }
}
