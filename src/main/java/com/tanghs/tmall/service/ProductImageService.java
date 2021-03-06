package com.tanghs.tmall.service;
 
import com.tanghs.tmall.dao.ProductImageDAO;
import com.tanghs.tmall.pojo.OrderItem;
import com.tanghs.tmall.pojo.Product;
import com.tanghs.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;

@Service
public class ProductImageService   {
     
    public static final String type_single = "single";
    public static final String type_detail = "detail";
     
    @Autowired ProductImageDAO productImageDAO;
    @Autowired ProductService productService;
 
    /**
     * @Author tanghs
     * @Description:  新增产品图片
     * @Date: 2020/5/13 15:48
     * @Version 1.0
     */
    public void add(ProductImage bean) {
        productImageDAO.save(bean);
    }
    /**
     * @Author tanghs
     * @Description:  删除产品图片
     * @Date: 2020/5/13 15:49
     * @Version 1.0
     */
    public void delete(Integer id) {
        productImageDAO.deleteById(id);
    }
    /**
     * @Author tanghs
     * @Description:  获取产品图片
     * @Date: 2020/5/13 15:49
     * @Version 1.0
     */
    public ProductImage get(Integer id) {
        return productImageDAO.findById(id).orElse(null);
    }
    /**
     * @Author tanghs
     * @Description:  产品图片单张展示
     * @Date: 2020/5/13 15:49
     * @Version 1.0
     */
    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, type_single);
    }
    /**
     * @Author tanghs
     * @Description: 产品详情图片展示
     * @Date: 2020/5/13 15:50
     * @Version 1.0
     */
    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, type_detail);
    }
    /**
     * @Author tanghs
     * @Description:  默认产品展示图片
     * @Date: 2020/5/13 15:50
     * @Version 1.0
     */
    public void setFirstProdutImage(Product product) {
        List<ProductImage> singleImages = listSingleProductImages(product);
        if(!singleImages.isEmpty())
            product.setFirstProductImage(singleImages.get(0));  //获取默认第一张单张产品图片
        else
            product.setFirstProductImage(new ProductImage()); //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。
 
    }

    /**
     * @Author tanghs
     * @Description:
     * @Date: 2020/5/13 17:25
     * @Version 1.0
     */
    public void setFirstProdutImages(List<Product> products) {
        for (Product product : products)
            setFirstProdutImage(product);
    }

    /**
     * @Author tanghs
     * @Description:   在订单项详情页展示缩略图
     * @Date: 2020/6/9 10:23
     * @Version 1.0
     */

    public void setFirstProductImagesOnOrderItems(List<OrderItem> ois) {
        for (OrderItem orderItem : ois) {
            setFirstProdutImage(orderItem.getProduct());
        }
    }
}