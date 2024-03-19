package gui;

import korisnici.Korisnici;
import korisnici.Korisnik;
import tretmani.Tretmani;
import tretmani.VrstaTretmana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecepcionerMeni extends JFrame {
    private JButton zakaziTretmanButton;
    private JButton odjavaButton;
    private JPanel recepcionerPanel;
    private JLabel lblWelcome;
    private JButton zakazanibtn;


    public RecepcionerMeni() {
        lblWelcome.setText("Dobrodosli, " + Korisnici.trenutniKorisnik.getIme());
        setTitle("Kozmeticki salon");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(recepcionerPanel);
        setVisible(true);

        odjavaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Korisnici.logout();
                LoginFrame log = new LoginFrame();
                dispose();
            }
        });
        zakaziTretmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> opcije = new ArrayList<String>();
                ArrayList<Integer> ids = new ArrayList<Integer>();
                for (Korisnik k : Korisnici.sviKorisnici.values()) {
                    if (k.getTip() == Korisnik.tipKorisnika.KLIJENT) {
                        opcije.add(k.ispisiKorisnika());
                        ids.add(k.idGetter());
                    }
                }
                Korisnik klijent = null;
                String[] options = opcije.toArray(new String[0]);

                JComboBox<String> comboBox = new JComboBox<>(options);

                int choice = JOptionPane.showOptionDialog(
                        null,
                        comboBox,
                        "Izaberite klijenta",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        options[0]
                );
                if (choice >= 0) {
                    int id = ids.get(comboBox.getSelectedIndex());
                    klijent = Korisnici.sviKorisnici.get(id);
                    System.out.println("Selected option: " + klijent);
                    Tretmani.zakazi(klijent);
                } else {
                    System.out.println("User cancelled.");
                }
            }
        });
        zakazanibtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SalonZakazani zakazani = new SalonZakazani();
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
        recepcionerPanel = new JPanel();
        recepcionerPanel.setLayout(new GridBagLayout());
        zakaziTretmanButton = new JButton();
        zakaziTretmanButton.setText("Zakazi tretman");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        recepcionerPanel.add(zakaziTretmanButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        recepcionerPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        recepcionerPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        recepcionerPanel.add(spacer3, gbc);
        zakazanibtn = new JButton();
        zakazanibtn.setText("Buduci tretmani");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        recepcionerPanel.add(zakazanibtn, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        recepcionerPanel.add(spacer4, gbc);
        odjavaButton = new JButton();
        odjavaButton.setText("Odjava");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        recepcionerPanel.add(odjavaButton, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        recepcionerPanel.add(label1, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        recepcionerPanel.add(spacer5, gbc);
        lblWelcome = new JLabel();
        lblWelcome.setText("Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        recepcionerPanel.add(lblWelcome, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return recepcionerPanel;
    }

}
