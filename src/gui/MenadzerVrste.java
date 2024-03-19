package gui;

import tretmani.TipTretmana;
import tretmani.Tretmani;
import tretmani.VrstaTretmana;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;

public class MenadzerVrste extends JFrame {
    private JButton dodajVrstuButton;
    private JButton obrisiVrstuButton;
    private JScrollPane tabelaPanel;
    private JPanel vrstePanel;

    public MenadzerVrste() {
        final boolean[] menjanaCena = {false};
        final boolean[] menjanoTrajanje = {true};
        setTitle("Sve vrste tretmana");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Object[] vrste = Tretmani.sveVrsteTretmana.values().toArray();
        String[] header = {"Naziv", "Tip tretmana", "Cena", "Trajanje"};
        Object[][] sadrzaj = new Object[vrste.length][4];
        int i = 0;
        for (Object o : vrste) {
            VrstaTretmana vrsta = (VrstaTretmana) o;
            sadrzaj[i][0] = vrsta.getNazivVrsteTretmana();
            sadrzaj[i][1] = vrsta.getTipTretmana().getNazivTipaTretmana();
            sadrzaj[i][2] = vrsta.getCena();
            sadrzaj[i][3] = vrsta.getTrajanje();
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(sadrzaj, header) {
        };

        model.setColumnIdentifiers(header);
        JTable tabelaIspis = new JTable(model);

        TableModelListener listener = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int red = e.getFirstRow();
                    int kolona = e.getColumn();
                    VrstaTretmana vrsta = (VrstaTretmana) vrste[red];
                    String naziv = (String) tabelaIspis.getModel().getValueAt(red, 0);
                    TipTretmana tip = Tretmani.nadjiTip((String) tabelaIspis.getModel().getValueAt(red, 1));
                    int cena = 0;
                    if (kolona == 2 || menjanaCena[0]) {
                        cena = Integer.parseInt((String) tabelaIspis.getModel().getValueAt(red, 2));
                        menjanaCena[0] = true;
                    } else {
                        cena = (int) tabelaIspis.getModel().getValueAt(red, 2);
                    }
                    Duration trajanje = null;

                    if (kolona == 3) {
                        trajanje = Duration.parse((String) tabelaIspis.getModel().getValueAt(red, 3));
                        menjanoTrajanje[0] = true;
                    }
                    else {
                        trajanje = (Duration) tabelaIspis.getModel().getValueAt(red, 3);
                    }


                    Tretmani.sveVrsteTretmana.get(vrsta.idGetter()).editVrstaTretmana(naziv, tip, cena, trajanje);
                }
            }
        };
        tabelaIspis.getModel().addTableModelListener(listener);


        tabelaPanel.setViewportView(tabelaIspis);
        setContentPane(vrstePanel);
        tabelaIspis.setFocusable(false);
        tabelaIspis.setRowSelectionAllowed(true);
        setVisible(true);
        dodajVrstuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DodavanjeVrste dod = new DodavanjeVrste();
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
        vrstePanel = new JPanel();
        vrstePanel.setLayout(new GridBagLayout());
        tabelaPanel = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        vrstePanel.add(tabelaPanel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        vrstePanel.add(spacer1, gbc);
        dodajVrstuButton = new JButton();
        dodajVrstuButton.setText("Dodaj vrstu");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        vrstePanel.add(dodajVrstuButton, gbc);
        obrisiVrstuButton = new JButton();
        obrisiVrstuButton.setText("Obrisi vrstu");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        vrstePanel.add(obrisiVrstuButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        vrstePanel.add(spacer2, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return vrstePanel;
    }
}
