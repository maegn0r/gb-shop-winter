package ru.gb.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.api.common.enums.Status;
import ru.gb.entity.Product;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDaoProxy {

    private final ProductDao productDao;

    private LocalDateTime ldt;
    private HashMap <Long, Product> tempData;

    @PostConstruct
    private void init (){
        tempData = new HashMap<Long, Product>();
        ldt = LocalDateTime.now();
        List<Product> listProducts = productDao.findAll();
        listProducts.forEach(item -> tempData.put(item.getId(),item));
    }

    public long count() {
        return productDao.count();
    }

    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    public Product save(Product product) {
        Product product1 = productDao.save(product);
        tempData.put(product1.getId(), product1);
        return product1;
    }

    public List<Product> findAll() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (ldt.plusMinutes(10L).isBefore(currentTime)){
            ldt = currentTime;
            List<Product> listProducts = productDao.findAll();
            tempData.clear();
            listProducts.forEach(item -> tempData.put(item.getId(),item));
            return listProducts;
        } else {
            return new ArrayList<>(tempData.values());
        }
    }

    public void deleteById(Long id) {
        tempData.remove(id);
        productDao.deleteById(id);
    }

    public List<Product> findAllByStatus(Status active) {
        return productDao.findAllByStatus(active);
    }
    public List<Product> findAllByStatus(Status status, Pageable pageable) {
        return productDao.findAllByStatus(status,pageable);
    };
    public List<Product> findAllByStatus(Status status, Sort sort) {
        return productDao.findAllByStatus(status,sort);
    };
}
