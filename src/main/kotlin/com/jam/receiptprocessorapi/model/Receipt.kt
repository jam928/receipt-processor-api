package com.jam.receiptprocessorapi.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Entity
data class Receipt(
    @Id
    @GeneratedValue
    var id: UUID? = null,
    val retailer: String,
    val purchaseDate: LocalDate,
    val purchaseTime: LocalTime,
    @OneToMany(mappedBy = "receipt", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var items: MutableList<Item> = mutableListOf(),
    val total: String,
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    @CreationTimestamp
    val updatedAt: LocalDateTime? = null,
) {
    override fun toString(): String {
        return "Receipt(id=$id, retailer='$retailer', purchaseDate=$purchaseDate, purchaseTime=$purchaseTime, total=$total, createdAt=$createdAt, updatedAt=$updatedAt, items=$items)"
    }
}