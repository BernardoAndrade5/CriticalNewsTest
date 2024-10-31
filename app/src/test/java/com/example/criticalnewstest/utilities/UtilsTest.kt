package com.example.criticalnewstest.utilities

import org.junit.Assert.*

import org.junit.Test

class UtilsTest {

    @Test
    fun formatDate_validInput_returnsFormattedDate() {
        val inputDate = "2023-12-20T10:00:00Z"
        val inputFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val outputFormat = "dd/MM/yyyy HH:mm"
        val expectedOutput = "20/12/2023 10:00"

        val actualOutput = Utils.formatDate(inputDate, inputFormat, outputFormat)

        assertEquals(expectedOutput, actualOutput)
    }
}