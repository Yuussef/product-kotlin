package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponseList
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        val productEntity: ProductEntity? = productRepository.findBySku(sku);
        if (productEntity != null) {
            return productEntity.toProductResponse()
        } else {
            return null;
        }
    }

    fun addProduct(productEntity: ProductEntity): ProductResponse {
        val productEntity: ProductEntity = productRepository.save(productEntity);
        return productEntity.toProductResponse();
    }

    fun getProductsBySkus(skus: List<String>): List<ProductResponse>? {
        val listProduct:List<ProductEntity> =productRepository.findAllBySkus(skus)
        return listProduct.toProductResponseList();
    }

    fun updateProduct(sku: String, updatedProduct: ProductEntity): ProductResponse {
        val productToUpdate = productRepository.findById(sku).orElseThrow { NoSuchElementException("Product with SKU $sku not found") }

        val updatedProductWithSku = productToUpdate.copy(
                name = updatedProduct.name,
                description = updatedProduct.description,
                price = updatedProduct.price,
        )

        val product:ProductEntity= productRepository.save(updatedProductWithSku)
        return product.toProductResponse();
    }

}
