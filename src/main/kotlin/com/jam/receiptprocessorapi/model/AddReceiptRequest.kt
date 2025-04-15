package com.jam.receiptprocessorapi.model


import java.time.LocalDate
import java.time.LocalTime

data class AddReceiptRequest(
    val retailer: String,
    val purchaseDate: LocalDate,
    val purchaseTime: LocalTime,
    var items: MutableList<ItemDto> = mutableListOf(),
    val total: String
)