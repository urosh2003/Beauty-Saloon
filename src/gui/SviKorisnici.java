package gui;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Kozmeticar;
import korisnici.Zaposleni;
import salon.Salon;
import salon.Saloni;
import tretmani.TipTretmana;
import tretmani.Tretmani;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SviKorisnici extends JFrame {
    private JScrollPane tabelaPanel;
    private JButton obrisiKorisnikaButton;
    private JButton dodajObukuButton;
    private JButton skloniObukuButton;
    private JPanel sviKorisniciPanel;
    private JButton resetujBonuseButton;

    public SviKorisnici() {
        setTitle("Svi korisnici");
        setSize(1500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Object[] korisnici = Korisnici.sviKorisnici.values().toArray();
        String[] header = {"Tip korisnika", "Ime", "Prezime", "Korisnicko ime", "Sifra", "Pol", "Broj telefona", "Adresa", "Nivo spreme", "Godine staza", "Plata", "Bonus", "Obucen za"};
        Object[][] sadrzaj = new Object[korisnici.length][13];
        int i = 0;
        for (Object o : korisnici) {
            Korisnik k = (Korisnik) o;
            sadrzaj[i][0] = k.getTip();
            sadrzaj[i][1] = k.getIme();
            sadrzaj[i][2] = k.getPrezime();
            sadrzaj[i][3] = k.korisnickoImeGetter();
            sadrzaj[i][4] = k.getLozinka();
            sadrzaj[i][5] = k.getPol();
            sadrzaj[i][6] = k.getTelefon();
            sadrzaj[i][7] = k.getAdresa();
            sadrzaj[i][8] = null;
            sadrzaj[i][9] = null;
            sadrzaj[i][10] = null;
            sadrzaj[i][11] = null;
            sadrzaj[i][12] = null;
            if (k.getTip() != Korisnik.tipKorisnika.KLIJENT) {
                sadrzaj[i][8] = ((Zaposleni) k).getSprema().name();
                sadrzaj[i][9] = ((Zaposleni) k).getGodineStaza();
                sadrzaj[i][10] = ((Zaposleni) k).odrediPlatu();
                sadrzaj[i][11] = ((Zaposleni) k).getBonus();
            }
            if (k.getTip() == Korisnik.tipKorisnika.KOZMETICAR) {
                sadrzaj[i][12] = ((Kozmeticar) k).getObucen();
            }

            i++;
        }

        DefaultTableModel model = new DefaultTableModel(sadrzaj, header) {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                if (mColIndex >= 1 && mColIndex <= 7) {
                    return true;
                }
                if ((mColIndex == 8 || mColIndex == 9) && sadrzaj[rowIndex][0] != Korisnik.tipKorisnika.KLIJENT) {
                    return true;
                }
                return false;
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
                    Korisnik k = (Korisnik) korisnici[red];
                    String ime = (String) tabelaIspis.getModel().getValueAt(red, 1);
                    String prezime = (String) tabelaIspis.getModel().getValueAt(red, 2);
                    String korisnickoIme = (String) tabelaIspis.getModel().getValueAt(red, 3);
                    String sifra = (String) tabelaIspis.getModel().getValueAt(red, 4);
                    String pol = (String) tabelaIspis.getModel().getValueAt(red, 5);
                    String telefon = (String) tabelaIspis.getModel().getValueAt(red, 6);
                    String adresa = (String) tabelaIspis.getModel().getValueAt(red, 7);

                    if (tabelaIspis.getModel().getValueAt(red, 0) == Korisnik.tipKorisnika.KLIJENT) {
                        Korisnici.sviKorisnici.get(k.idGetter()).editKorisnik(ime, prezime, pol, telefon, adresa, korisnickoIme, sifra);
                    } else {
                        Zaposleni.nivoSpreme sprema = Zaposleni.nivoSpreme.valueOf((String) tabelaIspis.getModel().getValueAt(red, 8));
                        int staz = 0;
                        if (kolona == 9) {
                            staz = Integer.parseInt((String) tabelaIspis.getModel().getValueAt(red, 9));
                        } else {
                            staz = (int) tabelaIspis.getModel().getValueAt(red, 9);
                        }
                        ((Zaposleni) Korisnici.sviKorisnici.get(k.idGetter())).editZaposleni(ime, prezime, pol, telefon, adresa, korisnickoIme, sifra, staz, sprema);
                    }
                }
            }
        };
        tabelaIspis.getModel().addTableModelListener(listener);


        tabelaPanel.setViewportView(tabelaIspis);
        setContentPane(sviKorisniciPanel);
        tabelaIspis.setFocusable(false);
        tabelaIspis.setRowSelectionAllowed(true);
        setVisible(true);
        obrisiKorisnikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Korisnik k = (Korisnik) korisnici[tabelaIspis.getSelectedRow()];
                int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Korisnici.obrisiKorisnika(k);
                    JOptionPane.showMessageDialog(null, "Brisanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    System.out.println("Cancelled.");
                }
            }
        });
        dodajObukuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Korisnik kor = (Korisnik) korisnici[tabelaIspis.getSelectedRow()];
                if (kor.getTip() != Korisnik.tipKorisnika.KOZMETICAR) {
                    JOptionPane.showMessageDialog(null, "Izabrani korisnik nije kozmeticar", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Kozmeticar k = (Kozmeticar) kor;
                ArrayList<String> opcije = new ArrayList<String>();
                ArrayList<Integer> ids = new ArrayList<Integer>();
                for (TipTretmana tip : Tretmani.sviTipoviTretmana.values()) {
                    if (!k.obucenZa(tip)) {
                        opcije.add(tip.getNazivTipaTretmana());
                        ids.add(tip.idGetter());
                    }
                }
                if (opcije.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kozmeticar je vec obucen za sve tipove tretmana", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TipTretmana tip = null;
                String[] options = opcije.toArray(new String[0]);

                JComboBox<String> comboBox = new JComboBox<>(options);

                int choice = JOptionPane.showOptionDialog(
                        null,
                        comboBox,
                        "Izaberite tip tretmana",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        options[0]
                );
                if (choice >= 0) {
                    int id = ids.get(comboBox.getSelectedIndex());
                    tip = Tretmani.sviTipoviTretmana.get(id);
                    System.out.println("Selected option: " + tip);
                    k.dodajObuku(tip);
                    JOptionPane.showMessageDialog(null, "Dodavanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    System.out.println("User cancelled.");
                    return;
                }
            }
        });
        skloniObukuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Korisnik kor = (Korisnik) korisnici[tabelaIspis.getSelectedRow()];
                if (kor.getTip() != Korisnik.tipKorisnika.KOZMETICAR) {
                    JOptionPane.showMessageDialog(null, "Izabrani korisnik nije kozmeticar", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Kozmeticar k = (Kozmeticar) kor;
                ArrayList<String> opcije = new ArrayList<String>();
                ArrayList<Integer> ids = new ArrayList<Integer>();
                for (TipTretmana tip : Tretmani.sviTipoviTretmana.values()) {
                    if (k.obucenZa(tip)) {
                        opcije.add(tip.getNazivTipaTretmana());
                        ids.add(tip.idGetter());
                    }
                }
                if (opcije.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kozmeticar nije obucen ni za jedan tip tretmana", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TipTretmana tip = null;
                String[] options = opcije.toArray(new String[0]);

                JComboBox<String> comboBox = new JComboBox<>(options);

                int choice = JOptionPane.showOptionDialog(
                        null,
                        comboBox,
                        "Izaberite tip tretmana",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        options[0]
                );
                if (choice >= 0) {
                    int id = ids.get(comboBox.getSelectedIndex());
                    tip = Tretmani.sviTipoviTretmana.get(id);
                    System.out.println("Selected option: " + tip);
                    k.skloniObuku(tip);
                    JOptionPane.showMessageDialog(null, "Uklanjanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    System.out.println("User cancelled.");
                    return;
                }
            }
        });
        resetujBonuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Salon.resetBonus();
                JOptionPane.showMessageDialog(null, "Uklanjanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                dispose();
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
        sviKorisniciPanel = new JPanel();
        sviKorisniciPanel.setLayout(new GridBagLayout());
        tabelaPanel = new JScrollPane();
        tabelaPanel.setPreferredSize(new Dimension(1400, 400));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        gbc.fill = GridBagConstraints.BOTH;
        sviKorisniciPanel.add(tabelaPanel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        sviKorisniciPanel.add(spacer1, gbc);
        obrisiKorisnikaButton = new JButton();
        obrisiKorisnikaButton.setText("Obrisi korisnika");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sviKorisniciPanel.add(obrisiKorisnikaButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sviKorisniciPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sviKorisniciPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sviKorisniciPanel.add(spacer4, gbc);
        skloniObukuButton = new JButton();
        skloniObukuButton.setText("Skloni obuku");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sviKorisniciPanel.add(skloniObukuButton, gbc);
        dodajObukuButton = new JButton();
        dodajObukuButton.setText("Dodaj obuku");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sviKorisniciPanel.add(dodajObukuButton, gbc);
        resetujBonuseButton = new JButton();
        resetujBonuseButton.setText("Resetuj bonuse");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sviKorisniciPanel.add(resetujBonuseButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return sviKorisniciPanel;
    }

}
