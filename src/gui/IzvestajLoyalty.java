package gui;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Kozmeticar;
import korisnici.Zaposleni;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.UtilDateModel;
import salon.Salon;
import salon.Saloni;
import tretmani.Tretmani;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Properties;

public class IzvestajLoyalty extends JFrame {
    private JButton podesiLoyaltyUslovButton;
    private JLabel lblUslov;
    private JScrollPane tabelaPanel;
    private JPanel LoyaltyPanel;

    public IzvestajLoyalty() {
        setTitle("Svi loyalty korisnici");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Object[] korisnici = Korisnici.sviLoyalty().toArray();
        String[] header = {"Ime", "Prezime", "Korisnicko ime", "Bodovi"};
        Object[][] sadrzaj = new Object[korisnici.length][4];
        int i = 0;
        for (Object o : korisnici) {
            Korisnik k = (Korisnik) o;
            sadrzaj[i][0] = k.getIme();
            sadrzaj[i][1] = k.getPrezime();
            sadrzaj[i][2] = k.korisnickoImeGetter();
            sadrzaj[i][3] = k.getPare();
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(sadrzaj, header) {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        model.setColumnIdentifiers(header);
        JTable tabelaIspis = new JTable(model);


        lblUslov.setText("Trenutni uslov za loyalty je: " + Saloni.trenutniSalon.getLoyaltyUslov());
        tabelaPanel.setViewportView(tabelaIspis);
        setContentPane(LoyaltyPanel);
        tabelaIspis.setFocusable(false);
        tabelaIspis.setRowSelectionAllowed(true);
        setVisible(true);


        podesiLoyaltyUslovButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog("Unesite broj min prihod za bonus:");

                if (userInput != null) {
                    int broj = Integer.parseInt(userInput);
                    Saloni.trenutniSalon.setLoyaltyUslov(broj);

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
        LoyaltyPanel = new JPanel();
        LoyaltyPanel.setLayout(new GridBagLayout());
        tabelaPanel = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        LoyaltyPanel.add(tabelaPanel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        LoyaltyPanel.add(spacer1, gbc);
        podesiLoyaltyUslovButton = new JButton();
        podesiLoyaltyUslovButton.setText("Podesi loyalty uslov");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LoyaltyPanel.add(podesiLoyaltyUslovButton, gbc);
        lblUslov = new JLabel();
        lblUslov.setText("Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        LoyaltyPanel.add(lblUslov, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LoyaltyPanel.add(spacer2, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return LoyaltyPanel;
    }
}
