package com.tanghs.tmall.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.tanghs.tmall.pojo.Product;
import com.tanghs.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tanghs.tmall.dao.CategoryDAO;
import com.tanghs.tmall.pojo.Category;
 
@Service
public class CategoryService {
    @Autowired CategoryDAO categoryDAO;

    /**
     * @Author tanghs
     * @Description:分类的分页
     * @Date: 2020/5/9 9:25
     * @Version 1.0
     */
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size,sort);
        Page pageFromJPA =categoryDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    /**
     * @Author tanghs
     * @Description:分类查询
     * @Date: 2020/5/9 9:24
     * @Version 1.0
     */
    public List<Category> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    /**
     * @Author tanghs
     * @Description: 新增分类
     * @Date: 2020/5/9 16:01
     * @Version 1.0
     */

    public void add(Category bean){
        categoryDAO.save(bean);
    }

    /**
     * @Author tanghs
     * @Description: 删除分类
     * @Date: 2020/5/11 9:42
     * @Version 1.0
     */

    public void delete(Integer id){
        categoryDAO.deleteById(id);
    }

    /**
     * @Author tanghs
     * @Description: 分类编辑
     * @Date: 2020/5/11 10:32
     * @Version 1.0
     */
    public Category get(Integer id){
        Category category = categoryDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category does not exist!"));
        return category;
    }

    /**
     * @Author tanghs
     * @Description: 修改分类
     * @Date: 2020/5/11 14:32
     * @Version 1.0
     */
    public void update(Category bean){
        categoryDAO.save(bean);
    }

    /**
     * @Author tanghs
     * @Description:  避免查询产品上的分类时，陷入无穷递归（和Order与OrderItem的情况一样，删除产品的分类属性，即查询时置空）
     * @Date: 2020/5/18 10:52
     * @Version 1.0
     */
    public void removeCategoryFromProduct(Category category){
        List<Product> products = category.getProducts();
        if(null != products){
            for(Product product:products){
                product.setCategory(null);
            }
        }
        List<List<Product>> productsByRow = category.getProductsByRow();
        if(null != productsByRow){
            for (List<Product> ps:productsByRow){
                for (Product product:ps){
                    product.setCategory(null);
                }
            }
        }
    }
    /**
     * @Author tanghs
     * @Description:
     * @Date: 2020/5/18 14:45
     * @Version 1.0
     */
    public void removeCategoryFromProduct(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromProduct(category);
        }
    }
}