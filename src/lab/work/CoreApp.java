package lab.work;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;


public class CoreApp {

    public static void main(String[] args) {
        new CoreApp();
    }

    public CoreApp() {
        JFrame guiFrame = new JFrame();

        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Магазин косметики");
        guiFrame.setSize(1000, 600);

        guiFrame.setLocationRelativeTo(null);

        //создаем таб панель для раздельного отображения сеансов и билетов
        JTabbedPane tabbedPane = new JTabbedPane();
        guiFrame.add(tabbedPane, BorderLayout.CENTER);


        //добавляем объект Films - класс для работы со списком фильмов
        tabbedPane.addTab("Пользователи", new Clients());
        tabbedPane.addTab("Товары", new Goods());

        tabbedPane.addChangeListener(e -> {
            if (e.getSource() instanceof JTabbedPane) {
                JTabbedPane tp = (JTabbedPane) e.getSource();
                int index = tp.getSelectedIndex();
                Component cp = tp.getComponentAt(index);
                if (cp instanceof PanelDataManager) {
                    PanelDataManager pane = (PanelDataManager) cp;
                    pane.reloadData();
                }
            }
        });

        guiFrame.setVisible(true);
    }
}
