package com.jam.receiptprocessorapi.controller


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jam.receiptprocessorapi.model.AddReceiptResponse
import com.jam.receiptprocessorapi.model.ApiError
import com.jam.receiptprocessorapi.model.PointsResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReceiptControllerIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val RESOURCES_DIR = "src/test/resources/"

    @Test
    fun testAddReceipt() {
        val json = File(RESOURCES_DIR + "json/target_receipt.json").readText(Charsets.UTF_8)

        val mvcResult = this.mockMvc.perform(
            post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andReturn()
        val objectMapper = jacksonObjectMapper()
        val addReceiptResponse = objectMapper.readValue<AddReceiptResponse>(mvcResult.response.contentAsString)

        // verify response id is a uuid
        assertTrue { isUUID(addReceiptResponse.id) }

    }

    @ParameterizedTest
    @CsvSource(
        "target_receipt.json, 28.0",
        "mm_corner_market_receipt.json, 109.0",
        "simple_receipt.json, 31.0",
        "morning_receipt.json, 15.0"
    )
    fun `test points with various receipts`(fileName: String, points: Double) {
        val json = File(RESOURCES_DIR + "json/$fileName").readText(Charsets.UTF_8)

        val mvcResult = this.mockMvc.perform(
            post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andReturn()
        val objectMapper = jacksonObjectMapper()
        val addReceiptResponse = objectMapper.readValue<AddReceiptResponse>(mvcResult.response.contentAsString)

        val id = addReceiptResponse.id

        // first test case expects the right number points when called the /points endpoint
        val pointsMvcResult = this.mockMvc.perform(get("/receipts/$id/points")).andReturn()
        val pointsJsonResponse = objectMapper.readValue<PointsResponse>(pointsMvcResult.response.contentAsString)

        assertEquals(pointsJsonResponse.points, points)
    }

    @Test
    fun testReceiptNotFound() {
        val id = UUID.randomUUID().toString() // this ID has not been added to the in memory db; so it should return a 404
        val pointsMvcResult = this.mockMvc.perform(get("/receipts/$id/points")).andReturn()
        val response = objectMapper.readValue<ApiError>(pointsMvcResult.response.contentAsString)

        assertEquals("No receipt found for that ID.", response.message)
        assertTrue { pointsMvcResult.response.status == HttpStatus.NOT_FOUND.value() }
    }

    @Test
    fun testInvalidAddReceipt() {
        val json = File(RESOURCES_DIR + "json/invalid_receipt_missing_retailer.json").readText(Charsets.UTF_8)

        val mvcResult = this.mockMvc.perform(
            post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andReturn()

        val objectMapper = jacksonObjectMapper()
        val apiError = objectMapper.readValue<ApiError>(mvcResult.response.contentAsString)

        // verify response msg and status code
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.response.status)
        assertEquals("The receipt is invalid.", apiError.message)

    }

    fun isUUID(string: String?): Boolean {
        try {
            UUID.fromString(string)
            return true
        } catch (ex: Exception) {
            return false
        }
    }

}

