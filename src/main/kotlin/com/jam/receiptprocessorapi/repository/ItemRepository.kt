package com.jam.receiptprocessorapi.repository

import com.jam.receiptprocessorapi.model.Item
import com.jam.receiptprocessorapi.model.Receipt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemRepository: JpaRepository<Item, UUID>