package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponseList
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*


@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock
    lateinit var
            productRepository: ProductRepository

    @InjectMocks
    lateinit var
            productService:ProductService

    @Test
    fun findProductBySku_whenSkuExist (){
        // Given
        val sku = "test_sku"
        val productEntity = ProductEntity(
                sku = sku,
                name = "Test Product",
                description = "Test product description",
                price = BigDecimal(10.0)
        )
        Mockito.`when`(productRepository.findBySku(sku)).thenReturn(productEntity)

        // When
        val result = productService.findProductBySku(sku)

        // Then
        assertEquals(productEntity.toProductResponse(), result)
    }



    @Test
    fun findProductBySkuNotExist() {
        // Given
        val sku = "nonexistent_sku"
        Mockito.`when`(productRepository.findBySku(sku)).thenReturn(null)

        // When
        val result = productService.findProductBySku(sku)

        // Then
        assertNull(result)
    }

    @Test
    fun savedproductResponse() {  //
        // Given
        val productEntity = ProductEntity(
                sku = "test_sku",
                name = "Test Product",
                description = "Test product description",
                price = BigDecimal(10.0)
        )
        val productResponse = ProductResponse(
                sku = "test_sku",
                name = "Test Product",
                description = "Test product description",
                price = BigDecimal(10.0)
        )

        Mockito.`when`(productRepository.save(productEntity)).thenReturn(productEntity)

        // When
        val result = productService.addProduct(productEntity)

        // Then
        assertEquals(productResponse, result)
    }

    @Test
    fun getProductsBySkus_listProduct() {
        // Given
        val skus = listOf("sku1", "sku2")
        val productEntities = listOf(
                ProductEntity("sku1", "Product 1", "Product 1 description", BigDecimal(10.0)),
                ProductEntity("sku2", "Product 2", "Product 2 description", BigDecimal(20.0))
        )
        Mockito.`when`(productRepository.findAllBySkus(skus)).thenReturn(productEntities)

        // When
        val result = productService.getProductsBySkus(skus)

        // Then
        assertNotNull(result)
        if (result != null) {
            assertEquals(2, result.size)
        }
        assertEquals(productEntities.toProductResponseList(), result)
    }

        @Test
        fun updateProduct_should_update_return_updated_product() {
            // Given
            val sku = "test_sku"
            val existingProduct = ProductEntity(
                    sku = sku,
                    name = "Old Product",
                    description = "Old product description",
                    price = BigDecimal(10.0)
            )
            val updatedProduct = ProductEntity(
                    sku = sku,
                    name = "Updated Product",
                    description = "Updated product description",
                    price = BigDecimal(15.0)
            )
            val updatedProductResponse = ProductResponse(
                    sku = sku,
                    name = "Updated Product",
                    description = "Updated product description",
                    price = BigDecimal(15.0)
            )

            Mockito.`when`(productRepository.findById(sku)).thenReturn(Optional.of(existingProduct))
            Mockito.`when`(productRepository.save(updatedProduct)).thenReturn(updatedProduct)

            // When
            val result = productService.updateProduct(sku, updatedProduct)

            // Then
            assertEquals(updatedProductResponse, result)
        }

    @Test
    fun updateProduct_should_throw_NoSuchElementException_when_product_SKU_does_not_exist() {
        // Given
        val sku = "nonexistent_sku"
        val updatedProduct = ProductEntity(
                sku = sku,
                name = "Updated Product",
                description = "Updated product description",
                price = BigDecimal(15.0)
        )

        Mockito.`when`(productRepository.findById(sku)).thenReturn(Optional.empty())

        // When & Then
        assertThrows<NoSuchElementException> {
            productService.updateProduct(sku, updatedProduct)
        }
    }

}