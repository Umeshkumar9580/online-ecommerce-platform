package online.ecommerce.platform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Main GUI application for the E-Commerce project.
 * Provides Add / View / Update / Delete / Search features.
 */
public class ProjectGUI extends JFrame {
    private final ECommerceService service = new ECommerceService();
    private final AutoSaveThread autoSave;
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTable table = new JTable(tableModel);

    public ProjectGUI() {
        super("E-Commerce Product Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // start autosave every 10 seconds
        autoSave = new AutoSaveThread(service, 10000);
        autoSave.start();

        initUI();
        loadTableData();
    }

    private void initUI() {
        // Top toolbar with buttons
        JToolBar toolBar = new JToolBar();
        JButton btnAdd = new JButton("Add Product");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete Selected");
        JTextField searchField = new JTextField(20);
        JButton btnSearch = new JButton("Search");

        toolBar.add(btnAdd);
        toolBar.add(btnUpdate);
        toolBar.add(btnDelete);
        toolBar.addSeparator();
        toolBar.add(btnRefresh);
        toolBar.addSeparator();
        toolBar.add(new JLabel("Search by name/id: "));
        toolBar.add(searchField);
        toolBar.add(btnSearch);

        add(toolBar, BorderLayout.NORTH);

        // Table columns
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Description", "Price", "Quantity", "Is Digital", "Link"});

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> openAddDialog());
        btnRefresh.addActionListener(e -> loadTableData());
        btnUpdate.addActionListener(e -> openUpdateDialog());
        btnDelete.addActionListener(e -> deleteSelectedProduct());
        btnSearch.addActionListener(e -> searchProducts(searchField.getText().trim()));

        // double-click to edit
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openUpdateDialog();
                }
            }
        });

        // graceful shutdown
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                autoSave.stopThread();
            }
        });
    }

    private void loadTableData() {
        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                return service.fetchProducts();
            }

            @Override
            protected void done() {
                try {
                    List<Product> list = get();
                    refreshTable(list);
                } catch (Exception ex) {
                    showError("Could not load products: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void refreshTable(List<Product> list) {
        tableModel.setRowCount(0);
        for (Product p : list) {
            if (p instanceof DigitalProduct) {
                DigitalProduct dp = (DigitalProduct) p;
                tableModel.addRow(new Object[]{dp.getId(), dp.getName(), dp.getDescription(), dp.getPrice(), dp.getQuantity(), true, dp.getLink()});
            } else {
                tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getQuantity(), false, ""});
            }
        }
    }

    private void openAddDialog() {
        ProductForm form = new ProductForm(this, "Add Product", null);
        form.setVisible(true);
        if (form.isSaved()) {
            Product p = form.getProduct();
            try {
                service.addProduct(p);
                showInfo("Product added.");
                loadTableData();
            } catch (Exception ex) {
                showError("Add failed: " + ex.getMessage());
            }
        }
    }

    private void openUpdateDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showError("Select a product to update.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Product existing = service.getById(id);
        if (existing == null) {
            showError("Product not found in cache.");
            return;
        }
        ProductForm form = new ProductForm(this, "Update Product", existing);
        form.setVisible(true);
        if (form.isSaved()) {
            Product p = form.getProduct();
            try {
                service.updateProduct(p);
                showInfo("Product updated.");
                loadTableData();
            } catch (Exception ex) {
                showError("Update failed: " + ex.getMessage());
            }
        }
    }

    private void deleteSelectedProduct() {
        int row = table.getSelectedRow();
        if (row == -1) { showError("Select a product to delete."); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete product ID " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            service.deleteProduct(id);
            showInfo("Product deleted.");
            loadTableData();
        } catch (Exception ex) {
            showError("Delete failed: " + ex.getMessage());
        }
    }

    private void searchProducts(String q) {
        if (q.isEmpty()) { loadTableData(); return; }
        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                List<Product> all = service.fetchProducts();
                // simple search by id or name
                return all.stream().filter(p -> {
                    try {
                        int idQ = Integer.parseInt(q);
                        return p.getId() == idQ || p.getName().toLowerCase().contains(q.toLowerCase());
                    } catch (NumberFormatException ex) {
                        return p.getName().toLowerCase().contains(q.toLowerCase());
                    }
                }).toList();
            }

            @Override
            protected void done() {
                try {
                    refreshTable(get());
                } catch (Exception ex) {
                    showError("Search failed: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // set look and feel system default
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            ProjectGUI gui = new ProjectGUI();
            gui.setVisible(true);
        });
    }

    // ----------------- Inner class: ProductForm dialog -----------------
    private static class ProductForm extends JDialog {
        private boolean saved = false;
        private final JTextField tfId = new JTextField(10);
        private final JTextField tfName = new JTextField(20);
        private final JTextField tfDesc = new JTextField(30);
        private final JTextField tfPrice = new JTextField(10);
        private final JTextField tfQty = new JTextField(6);
        private final JCheckBox cbDigital = new JCheckBox("Digital Product");
        private final JTextField tfLink = new JTextField(30);

        public ProductForm(Frame owner, String title, Product existing) {
            super(owner, title, true);
            setLayout(new BorderLayout());
            JPanel p = new JPanel(new GridLayout(0,2,5,5));
            p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            p.add(new JLabel("ID:")); p.add(tfId);
            p.add(new JLabel("Name:")); p.add(tfName);
            p.add(new JLabel("Description:")); p.add(tfDesc);
            p.add(new JLabel("Price:")); p.add(tfPrice);
            p.add(new JLabel("Quantity:")); p.add(tfQty);
            p.add(cbDigital); p.add(new JLabel(""));
            p.add(new JLabel("Digital Link:")); p.add(tfLink);

            add(p, BorderLayout.CENTER);

            JPanel btns = new JPanel();
            JButton btnSave = new JButton("Save");
            JButton btnCancel = new JButton("Cancel");
            btns.add(btnSave); btns.add(btnCancel);
            add(btns, BorderLayout.SOUTH);

            cbDigital.addActionListener(e -> tfLink.setEnabled(cbDigital.isSelected()));
            tfLink.setEnabled(false);

            if (existing != null) {
                tfId.setText(String.valueOf(existing.getId())); tfId.setEnabled(false);
                tfName.setText(existing.getName());
                tfDesc.setText(existing.getDescription());
                tfPrice.setText(String.valueOf(existing.getPrice()));
                tfQty.setText(String.valueOf(existing.getQuantity()));
                if (existing instanceof DigitalProduct) {
                    cbDigital.setSelected(true);
                    tfLink.setEnabled(true);
                    tfLink.setText(((DigitalProduct) existing).getLink());
                }
            }

            btnSave.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(tfId.getText().trim());
                    String name = tfName.getText().trim();
                    String desc = tfDesc.getText().trim();
                    double price = Double.parseDouble(tfPrice.getText().trim());
                    int qty = Integer.parseInt(tfQty.getText().trim());
                    boolean isDigital = cbDigital.isSelected();
                    String link = tfLink.getText().trim();

                    Product pprod;
                    if (isDigital) {
                        pprod = new DigitalProduct(id, name, desc, price, qty, link);
                    } else {
                        pprod = new Product(id, name, desc, price, qty);
                    }

                    // basic validation here, service will also validate
                    if (name.isEmpty()) throw new IllegalArgumentException("Name required");
                    if (price < 0) throw new IllegalArgumentException("Price must be >= 0");
                    if (qty < 0) throw new IllegalArgumentException("Quantity must be >= 0");

                    saved = true;
                    // temporarily store product in dialog
                    this.putClientProperty("product", pprod);
                    setVisible(false);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Numeric fields invalid", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancel.addActionListener(e -> { saved = false; setVisible(false); });

            pack();
            setLocationRelativeTo(owner);
        }

        private void putClientProperty(String string, Product pprod) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'putClientProperty'");
        }

        public boolean isSaved() { return saved; }

        public Product getProduct() {
            return (Product) getClientProperty("product");
        }

        private Product getClientProperty(String string) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getClientProperty'");
        }
    }
}
