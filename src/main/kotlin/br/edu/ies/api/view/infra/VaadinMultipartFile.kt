package br.edu.ies.api.view.infra

import com.vaadin.flow.server.streams.UploadMetadata
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import java.io.Serializable
import java.nio.file.Files

class VaadinMultipartFile(val metadata: UploadMetadata, val data: ByteArray) : MultipartFile, Serializable {
    override fun getInputStream(): InputStream = data.inputStream()

    override fun getName(): String = metadata.fileName

    override fun getOriginalFilename(): String = metadata.fileName

    override fun getContentType(): String = metadata.contentType

    override fun isEmpty(): Boolean = data.isEmpty()

    override fun getSize(): Long = data.size.toLong()

    override fun getBytes(): ByteArray = data

    override fun transferTo(dest: File) {
        Files.write(dest.toPath(), data)
    }
}