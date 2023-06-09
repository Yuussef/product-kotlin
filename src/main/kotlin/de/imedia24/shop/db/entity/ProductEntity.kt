package de.imedia24.shop.db.entity

import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class ProductEntity(
        @Id
    @Column(name = "sku", nullable = false)
    val sku: String= "",

        @Column(name = "name", nullable = false)
    val name: String= "",

        @Column(name = "description")
    val description: String= "",

        @Column(name = "price", nullable = false)
    val price: BigDecimal= BigDecimal(112),

        @UpdateTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime= ZonedDateTime.now(),

        @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime= ZonedDateTime.now(),


        )
