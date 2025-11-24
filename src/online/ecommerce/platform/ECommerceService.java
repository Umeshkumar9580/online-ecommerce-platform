package online.ecommerce.platform;


import java.util.*;

/**
 * Service layer: validation, cache, synchronized methods for thread-safety.
 */
public class ECommerceService {
    private final ProductDAO dao = new ProductDAO();
    // cache maps id -> Product
    private final Map<Integer, Product> productCache = new HashMap<>();

    public ECommerceService() {
        try {
            // initialize cache from DB
            loadCacheFromDB();
        } catch (Exception e) {
            // ignore: empty DB possible
        }
    }

    private synchronized void loadCacheFromDB() throws Exception {
        List<Product> all = dao.getAllProducts();
        productCache.clear();
        for (Product p : all) productCache.put(p.getId(), p);
    }

    private void validate(Product p) throws InvalidProductException {
        if (p.getPrice() < 0) throw new InvalidProductException("Negative price not allowed");
        if (p.getQuantity() < 0) throw new InvalidProductException("Negative quantity not allowed");
        if (p.getName() == null || p.getName().trim().isEmpty()) throw new InvalidProductException("Name required");
    }

    public synchronized void addProduct(Product p) throws Exception {
        validate(p);
        if (productCache.containsKey(p.getId())) {
            throw new InvalidProductException("Product with id " + p.getId() + " already exists");
        }
        dao.insertProduct(p);
        productCache.put(p.getId(), p);
    }

    public synchronized void updateProduct(Product p) throws Exception {
        validate(p);
        if (!productCache.containsKey(p.getId())) {
            throw new InvalidProductException("Product with id " + p.getId() + " not found");
        }
        dao.updateProduct(p);
        productCache.put(p.getId(), p);
    }

    public synchronized void deleteProduct(int id) throws Exception {
        if (!productCache.containsKey(id)) {
            throw new InvalidProductException("Product with id " + id + " not found");
        }
        dao.deleteProduct(id);
        productCache.remove(id);
    }

    public synchronized List<Product> fetchProducts() throws Exception {
        // always load fresh from DB to remain consistent
        List<Product> all = dao.getAllProducts();
        productCache.clear();
        for (Product p : all) productCache.put(p.getId(), p);
        return new ArrayList<>(productCache.values());
    }

    public synchronized Product getById(int id) {
        return productCache.get(id);
    }

    /**
     * Called by AutoSaveThread: sync in-memory cache to DB (upsert-like).
     * This ensures background persistence and demonstrates multithreading + synchronization.
     */
    public synchronized void syncCacheToDB() {
        try {
            for (Product p : new ArrayList<>(productCache.values())) {
                // if product exists in DB then update else insert
                Product dbP = dao.getProductById(p.getId());
                if (dbP == null) {
                    dao.insertProduct(p);
                } else {
                    dao.updateProduct(p);
                }
            }
        } catch (Exception e) {
            System.out.println("Auto-save error: " + e.getMessage());
        }
    }
}

