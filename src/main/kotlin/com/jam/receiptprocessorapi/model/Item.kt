package com.jam.receiptprocessorapi.model

import jakarta.persistence.*
import java.util.*

@Entity
data class Item(
    @Id
    @GeneratedValue
    var id: UUID? = null,
    val shortDescription: String,
    val price: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    var receipt: Receipt? = null
) {
    override fun toString(): String {
        return "Item(id=$id, shortDescription='$shortDescription', price='$price')"
    }
}