package com.jam.receiptprocessorapi.repository

import com.jam.receiptprocessorapi.model.Receipt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ReceiptRepository: JpaRepository<Receipt, UUID>