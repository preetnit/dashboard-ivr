package com.design.ivrservice.core.export

import mu.KotlinLogging
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintWriter

@Service
class CSVExportServiceImpl: CSVExportService {
    private val logger = KotlinLogging.logger {}

    override fun exportData(csvHeader: Array<String>, csvBody: List<List<String>>): ByteArrayInputStream {

        logger.info { "Exporting Data to CSV File" }
        val byteArrayOutputStream =
            ByteArrayOutputStream()
                .use { out ->
                    // defining the CSV printer
                    CSVPrinter(
                        PrintWriter(out),
                        CSVFormat.DEFAULT.withHeader(*csvHeader)
                    )
                        .use { csvPrinter ->
                            // populating the CSV content
                            csvBody.forEach { record ->
                                csvPrinter.printRecord(record)
                            }

                            // writing the underlying stream
                            csvPrinter.flush()

                            ByteArrayInputStream(out.toByteArray())
                        }
                }

        return byteArrayOutputStream
    }
}
