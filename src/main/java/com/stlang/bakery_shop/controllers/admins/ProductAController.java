package com.stlang.bakery_shop.controllers.admins;


import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.domains.enums.ProductStatus;
import com.stlang.bakery_shop.domains.enums.Target;
import com.stlang.bakery_shop.services.iservices.ICategoryService;
import com.stlang.bakery_shop.services.iservices.IProductService;
import com.stlang.bakery_shop.utils.XUploadFileService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/admin/product")
public class ProductAController {

    private final String[] errors = {"", "Data not found !", "Update product failed !"};

    private final ICategoryService categoryService;
    private final IProductService productService;
    private final XUploadFileService xFileService;

    public ProductAController(ICategoryService categoryService, IProductService productService, XUploadFileService xFileService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.xFileService = xFileService;
    }

    @RequestMapping({"/index", "/index/{errorId}"})
    public String productAdminIndex(Model model,
                                    @RequestParam(value = "page") Optional<String> pageNo,
                                    @PathVariable("errorId") Optional<Integer> errorId
    ) {


        int pageSize = 12;
        int pageNumber = pageNo.map(Integer::parseInt).orElse(1);
        Page<Product> page = productService.findAllProducts(pageNumber, pageSize);

        // Nếu có lỗi //
        model.addAttribute("msg", errors[errorId.orElse(0)]);
        // Nếu có product được chọn //
        if (model.asMap().containsKey("product")) {
            model.addAttribute("product", model.asMap().get("product"));
        } else
            model.addAttribute("product", new Product());

        model.addAttribute("page", page);
        model.addAttribute("productStatus", ProductStatus.values());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("targets", Target.values());
        return "admin/product-view";
    }

    @RequestMapping("/edit/{productId}")
    public String productAdminEdit(@PathVariable long productId,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam("page") Integer pageNo) {
        redirectAttributes.addAttribute("page", pageNo);
        Product product = productService.findProductById(productId);
        if (product == null) {
            return "redirect:/admin/product/index/1";
        }
        redirectAttributes.addFlashAttribute("product", product);
        return "redirect:/admin/product/index";
    }

    @RequestMapping("/delete/{productId}")
    public String productAdminDelete(@PathVariable long productId,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam("page") Integer pageNo) {
        redirectAttributes.addAttribute("page", pageNo);
        if (productService.deleteProduct(productId) == -1) {
            return "redirect:/admin/product/index/1";
        }
        return "redirect:/admin/product/index";
    }

    @RequestMapping("/create")
    public String productAdminCreate(@RequestParam("page") Integer pageNo,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam("photoProduct") MultipartFile file,
                                     @Valid Product product,
                                     BindingResult result) throws IOException {
        redirectAttributes.addAttribute("page", pageNo);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
            redirectAttributes.addFlashAttribute("product", product);
            return "redirect:/admin/product/index";
        }
        if (file != null) {
            String fileName = xFileService.save(file, "/client/images/products");
            product.setImage(fileName);
        } else {
            product.setImage("");
        }
        productService.addProduct(product);
        return "redirect:/admin/product/index";
    }


    @RequestMapping("/update")
    public String productAdminUpdate(
            RedirectAttributes redirectAttributes,@Valid Product product,
            BindingResult result,
            @RequestParam("photoProduct") MultipartFile file ) throws IOException {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
            redirectAttributes.addFlashAttribute("product", product);
            return "redirect:/admin/product/index";
        }
        if (!file.isEmpty()) {
            String fileName = xFileService.save(file, "/client/images/products");
            product.setImage(fileName);
        } else {
            Product existingProduct = productService.findProductById(product.getId());
            product.setImage(existingProduct.getImage());
        }
        Product saveProduct = productService.updateProduct(product);
        if (saveProduct != null) {
            return "redirect:/admin/product/index/2";
        }
        return "admin/product-view";
    }

}
