package com.jam.receiptprocessorapi.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.jam.receiptprocessorapi.model.AddReceiptRequest
import com.jam.receiptprocessorapi.model.Item
import com.jam.receiptprocessorapi.model.ItemDto
import com.jam.receiptprocessorapi.model.Receipt
import com.jam.receiptprocessorapi.repository.ReceiptRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.test.assertEquals


@ExtendWith(MockKExtension::class)
class ReceiptServiceTest {
    // manually create since it does not support @value injection
    lateinit var receiptService: ReceiptService

    @MockK
    lateinit var receiptRepository: ReceiptRepository

    private val RESOURCES_DIR = "src/test/resources/"

    @BeforeEach
    fun setUp() {
        receiptService = ReceiptService(receiptRepository, llmGenerated = false)
    }

    @Test
    fun testAddReceipt() {

        val purchaseDate = LocalDate.now()
        val purchaseTime = LocalTime.now()
        val addReceiptRequest = AddReceiptRequest(
            retailer = "Target",
            purchaseDate = purchaseDate,
            purchaseTime = purchaseTime,
            total = "23",
            items = mutableListOf(ItemDto(shortDescription = "short", price="11"), ItemDto(shortDescription = "long", price="12"))
        )
        val receipt = Receipt(
            id = UUID.randomUUID(),
            retailer = "Target",
            purchaseDate = purchaseDate,
            purchaseTime = purchaseTime,
            items = mutableListOf(Item(id=null, shortDescription = "short", price="11"), Item(id=null, shortDescription = "long", price="12")),
            total = "23"
        )
        every { receiptRepository.save(any()) }.returns(receipt)
        val addReceiptResponse = receiptService.saveReceipt(addReceiptRequest)
        assertEquals(receipt.id.toString(), addReceiptResponse.id)

    }

    @ParameterizedTest
    @CsvSource(
        "target_receipt.json, 28.0",
        "mm_corner_market_receipt.json, 109.0",
        "simple_receipt.json, 31.0",
        "morning_receipt.json, 15.0"
    )
    fun `test points with various receipts`(fileName: String, points: Double) {
        val jsonFile = File(RESOURCES_DIR + "json/$fileName")

        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val receipt = objectMapper.readValue<Receipt>(jsonFile)
        receipt.id = UUID.randomUUID()
        val optional = Optional.of(receipt)

        every { receiptRepository.findById(any()) }.returns(optional)
        val pointsResponse = receipt.id?.let { receiptService.getPoints(it) }
        assertEquals(pointsResponse?.points, points)
    }
}