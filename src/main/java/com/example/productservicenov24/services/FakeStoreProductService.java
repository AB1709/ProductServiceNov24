package com.example.productservicenov24.services;


import com.example.productservicenov24.dtos.FakeStoreProductsDto;
import com.example.productservicenov24.models.Category;
import com.example.productservicenov24.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate = new RestTemplate();

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getProductById(Long id){
        FakeStoreProductsDto fakeStoreProductsDto =  restTemplate.getForObject("https://fakestoreapi.com/products/"+id, FakeStoreProductsDto.class);
        return convertFakeStoreProductsDtoToProduct(fakeStoreProductsDto);
    }
    private Product convertFakeStoreProductsDtoToProduct(FakeStoreProductsDto fakeStoreProductsDto) {
        if (fakeStoreProductsDto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(fakeStoreProductsDto.getId());
        product.setTitle(fakeStoreProductsDto.getTitle());
        product.setDescription(fakeStoreProductsDto.getDescription());
        product.setPrice(fakeStoreProductsDto.getPrice());

        Category category = new Category();
        category.setTitle(fakeStoreProductsDto.getCategory());
        product.setCategory(category);

        return product;
    }
}