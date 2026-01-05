package com.onlineecommerce.gui;

import com.onlineecommerce.model.Product;
import com.onlineecommerce.service.ECommerceService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProjectGUI {
    private final ECommerceService service = new ECommerceService();

    public void show() {
        JFrame frame = new JFrame("E-Commerce Demo (GUI)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        java.util.List<Product> products = service.listProducts();

        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10));
        for (Product p : products) {
            JPanel card = new JPanel();
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            card.setLayout(new BorderLayout());
            JLabel title = new JLabel(p.getName(), SwingConstants.CENTER);
            JLabel price = new JLabel("$" + p.getPrice(), SwingConstants.CENTER);
            JTextArea desc = new JTextArea(p.getDescription());
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            desc.setEditable(false);
            card.add(title, BorderLayout.NORTH);
            card.add(desc, BorderLayout.CENTER);
            card.add(price, BorderLayout.SOUTH);
            panel.add(card);
        }

        frame.add(new JScrollPane(panel));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectGUI().show());
    }
}