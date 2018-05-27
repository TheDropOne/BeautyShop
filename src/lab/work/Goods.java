package lab.work;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Goods extends JPanel implements PanelDataManager {

    JPanel panel = null;
    DefaultListModel listModel = new DefaultListModel();
    JList clientsList = null;
    JButton addButton = null;
    JButton delButton = null;
    GoodsManager manager = new GoodsManager();

    Goods() {
        panel = this;

        addButton = new JButton("Добавить");
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addButton.getMinimumSize().height));

        delButton = new JButton("Удалить");
        delButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, delButton.getMinimumSize().height));

        // Вычитываем список клиентов
        reloadData();

        // UI
        this.setLayout(new BorderLayout());

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
                    "Наименование товара",
                    "Введите наименование товара",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (newName.trim().length() > 0) {
                manager.addDataRecord(new String[]{"name"}, new String[]{newName});
                reloadData();
            }
        });
        delButton.setEnabled(false);
        delButton.addActionListener(e -> {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить запись?", "Предупреждение", dialogButton, JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                // Saving code here
                Object object = clientsList.getSelectedValue();
                if (object instanceof ClientData) {
                    manager.deleteRecordByID(((ClientData) object).getId());
                }
                if (object instanceof GoodsData) {
                    manager.deleteRecordByID(((GoodsData) object).getId());
                }
                reloadData();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(delButton);

        this.add(buttonPanel, BorderLayout.EAST);
    }

    public void reloadData() {
        listModel.clear();
        ArrayList<GoodsData> data = manager.loadAllData();
        for (int i = 0; i < data.size(); i++) {
            listModel.addElement(data.get(i));
        }

        delButton.setEnabled(false);
    }

    static class FilmDataRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            if (value instanceof ClientData) {
                return super.getListCellRendererComponent(
                        list, ((GoodsData) value).getName(), index,
                        isSelected, hasFocus);
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
        }
    }
}
