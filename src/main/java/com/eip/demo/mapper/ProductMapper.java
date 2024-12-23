package com.eip.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.eip.demo.model.Product;
import com.eip.demo.model.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Metodo per mappare singolo oggetto Product -> ProductDTO
    ProductDTO productToProductDTO(Product product);

    // Metodo per mappare una lista di Product -> Lista di ProductDTO
    List<ProductDTO> productsToProductDTOs(List<Product> products);
}