package com.tanghs.tmall.service;

import com.tanghs.tmall.dao.CategoryDAO;
import com.tanghs.tmall.dao.ProductDAO;
import com.tanghs.tmall.pojo.Category;
import com.tanghs.tmall.pojo.Product;
import com.tanghs.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired ProductDAO productDAO;
    @Autowired CategoryDAO categoryDAO;
    @Autowired ProductImageService productImageService;
    @Autowired OrderItemService orderItemService;
    @Autowired ReviewService reviewService;

    /**
     * @Author tanghs
     * @Description:分类的分页
     * @Date: 2020/5/9 9:25
     * @Version 1.0
     */
    public Page4Navigator<Product> list(int cid,int start, int size, int navigatePages) {
       Category  category = categoryDAO.findById(cid).orElse(null);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size,sort);
        Page pageFromJPA =productDAO.findByCategory(category,pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    /**
     * @Author tanghs
     * @Description:分类查询
     * @Date: 2020/5/9 9:24
     * @Version 1.0
     */
    public List<Product> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productDAO.findAll(sort);
    }

    /**
     * @Author tanghs
     * @Description: 新增分类
     * @Date: 2020/5/9 16:01
     * @Version 1.0
     */

    public void add(Product bean){
        productDAO.save(bean);
    }

    /**
     * @Author tanghs
     * @Description: 删除分类
     * @Date: 2020/5/11 9:42
     * @Version 1.0
     */

    public void delete(Integer id){
        productDAO.deleteById(id);
    }

    /**
     * @Author tanghs
     * @Description: 分类编辑
     * @Date: 2020/5/11 10:32
     * @Version 1.0
     */
    public Product get(Integer id){
        Product product = productDAO.findById(id).orElse(null);
        return product;
    }

    /**
     * @Author tanghs
     * @Description: 修改分类
     * @Date: 2020/5/11 14:32
     * @Version 1.0
     */
    public void update(Product bean){
        productDAO.save(bean);
    }

    /**
     * @Author tanghs
     * @Description:  为多个分类填充产品集合
     * @Date: 2020/5/18 11:18
     * @Version 1.0
     */
    public void fill(List<Category> categoryList){
        for (Category category:categoryList){
                fill(category);
        }
    }
    /**
     * @Author tanghs
     * @Description: 为分类填充产品集合
     * @Date: 2020/5/18 11:15
     * @Version 1.0
     */
    public void fill(Category category) {
        List<Product> productList = listByCategory(category);
        productImageService.setFirstProdutImages(productList);   //将单张图片存储传给前端页面
        category.setProducts(productList);
    }

    /** @@@
     * @Author tanghs
     * @Description: 为多个分类填充推荐产品集合，即把分类下的产品集合，
        * 按照8个为一行，拆成多行，以利于后续页面上进行显示
     * @Date: 2020/5/18 11:19
     * @Version 1.0
     */
    public void fillByRow(List<Category> categoryList){
        int productNumberEachRow = 8;
        for (Category category:categoryList){
            List<Product> products = category.getProducts();  //分类下的多行产品，每行产品又有多个产品名
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i= 0 ;i< products.size();i+=productNumberEachRow){
                int size = i+productNumberEachRow;
                size = size>products.size()?products.size():size;
                List<Product> productsOfEachRow = products.subList(i,size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    /**
     * @Author tanghs
     * @Description:  查询某个分类下的所有产品
     * @Date: 2020/5/18 11:23
     * @Version 1.0
     */

    public List<Product> listByCategory(Category category){
        List<Product> products= productDAO.findByCategoryOrderById(category);
        return products;
    }

    /**
     * @Author tanghs
     * @Description:  设置销售额和评价总数
     * @Date: q 15:22
     * @Version 1.0
     */

    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products)
            setSaleAndReviewNumber(product);
    }

    public  void setSaleAndReviewNumber(Product product){

            Integer saleCount = orderItemService.getSaleCount(product);
            product.setSaleCount(saleCount);
            //存入评价总数
            int reviewCount = reviewService.getCount(product);
            product.setReviewCount(reviewCount);
    }

    /**
     * @Author tanghs
     * @Description:     分类页搜索
     * @Date: 2020/6/4 10:36
     * @Version 1.0
     */
    public List<Product> search(String keyword, int start, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        List<Product> products =productDAO.findByNameLike("%"+keyword+"%",pageable);
        return products;
    }
}