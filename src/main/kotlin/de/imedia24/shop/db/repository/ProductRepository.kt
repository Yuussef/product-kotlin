package de.imedia24.shop.db.repository

import de.imedia24.shop.db.entity.ProductEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<ProductEntity, String> {

    fun findBySku(sku: String): ProductEntity?
    @Query("SELECT p FROM ProductEntity p WHERE p.sku IN :skus")
    fun findAllBySkus(skus: List<String>): List<ProductEntity>
    override fun findAll() :List<ProductEntity>
}