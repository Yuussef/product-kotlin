package de.imedia24.shop.controller

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController()
@RequestMapping("/api/product")
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }
    @PostMapping
    fun addProduct(@RequestBody product: ProductEntity): ResponseEntity<ProductResponse> {
       val savedProduct:ProductResponse = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }
    @GetMapping("/all")
        fun getProductsBySkus(@RequestParam skus: List<String>): ResponseEntity<List<ProductResponse>> {
        val products = productService.getProductsBySkus(skus)
        return if(products == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(products)
        }
    }
    @PatchMapping("/{sku}")
    fun updateProduct(@PathVariable sku: String, @RequestBody productUpdate: ProductEntity): ResponseEntity<ProductResponse> {
        val updatedProduct = productService.updateProduct(sku, productUpdate)
        return ResponseEntity.ok(updatedProduct)
    }
}
