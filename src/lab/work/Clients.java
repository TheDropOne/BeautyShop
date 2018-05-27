package lab.work;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Clients extends JPanel implements PanelDataManager {

    JPanel panel = null;
    DefaultListModel listModel = new DefaultListModel();
    JList clientsList = null;
    JButton addButton = null;
    JButton delButton = null;
    ClientManager manager = new ClientManager();

    public Clients() {
        panel = this;

        addButton = new JButton("Добавить");
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addButton.getMinimumSize().height));

        delButton = new JButton("Удалить");
        delButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, delButton.getMinimumSize().height));

        // Вычитываем список клиентов


        reloadData();

        // Создаем UI элементы
        this.setLayout(new BorderLayout());
        //


        clientsList = new JList();

        clientsList.setModel(listModel);
        clientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        clientsList.setCellRenderer(new FilmDataRenderer());

        clientsList.getSelectionModel().addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        delButton.setEnabled(true);
                    }
                });


        this.add(new JScrollPane(clientsList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        addButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(
                    panel,
                    "Имя клиента",
                    "Введите полное имя коиента",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (newName.trim().length() > 0) {
                manager.addDataRecord(new String[]{"name"}, new String[]{newName});
                reloadData();
            }
        });
        delButton.setEnabled(false);
        delButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить запись?", "Предупреждение", dialogButton, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    // Saving code here
                    ClientData doc = (ClientData) clientsList.getSelectedValue();
                    manager.deleteRecordByID(doc.getId());
                    reloadData();
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(delButton);

        this.add(buttonPanel, BorderLayout.EAST);

    }

    public void reloadData() {
        listModel.clear();
        ArrayList<ClientData> data = manager.loadAllData();
        for (int i = 0; i < data.size(); i++) {
            listModel.addElement(data.get(i));
        }
        delButton.setEnabled(false);
    }

    public static class FilmDataRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            if (value instanceof ClientData) {
                return super.getListCellRendererComponent(
                        list, ((ClientData) value).getName(), index,
                        isSelected, hasFocus);
            }
            return super.getListCellRendererComponent(list, value, index,
                    isSelected, hasFocus);
        }
    }
}
