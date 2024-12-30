package com.example.productservicenov24.services;


import com.example.productservicenov24.dtos.FakeStoreProductsDto;
import com.example.productservicenov24.models.Category;
import com.example.productservicenov24.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate = new RestTemplate();

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductsDto fakeStoreProductsDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductsDto.class);
        return convertFakeStoreProductsDtoToProduct(fakeStoreProductsDto);
    }

    public List<Product> getAllProducts() {
        FakeStoreProductsDto[] fakeStoreProductsDtoList = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductsDto[].class);
        List<Product> products = new ArrayList<>();
        assert fakeStoreProductsDtoList != null;
        for (FakeStoreProductsDto fakeStoreProductsDto : fakeStoreProductsDtoList) {
            products.add(convertFakeStoreProductsDtoToProduct(fakeStoreProductsDto));
        }
        return products;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductsDto fakeStoreProductsDto = new FakeStoreProductsDto();
        fakeStoreProductsDto.setId(product.getId());
        fakeStoreProductsDto.setTitle(product.getTitle());
        fakeStoreProductsDto.setDescription(product.getDescription());
        fakeStoreProductsDto.setPrice(product.getPrice());
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductsDto);
        ResponseExtractor<ResponseEntity<FakeStoreProductsDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductsDto.class);
        FakeStoreProductsDto fakeStoreProductsDto1 = Objects.requireNonNull(restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor)).getBody();
        return convertFakeStoreProductsDtoToProduct(fakeStoreProductsDto1);
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