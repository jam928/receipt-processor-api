package com.jam.receiptprocessorapi.controller

import ch.qos.logback.core.model.Model
import com.jam.receiptprocessorapi.model.AddReceiptRequest
import com.jam.receiptprocessorapi.model.AddReceiptResponse
import com.jam.receiptprocessorapi.model.PointsResponse
import com.jam.receiptprocessorapi.model.Receipt
import com.jam.receiptprocessorapi.service.ReceiptService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping("/receipts")
class ReceiptController(val receiptService: ReceiptService) {

    @GetMapping("/{id}/points")
    fun getPoints(@PathVariable id: String): PointsResponse {
        return receiptService.getPoints(UUID.fromString(id))
    }

    @PostMapping("/process")
    fun process(@RequestBody addReceiptRequest: AddReceiptRequest): AddReceiptResponse {
        return receiptService.saveReceipt(addReceiptRequest)
    }
}