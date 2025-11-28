package br.edu.ies.api.business.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.apache.tika.detect.DefaultDetector
import org.apache.tika.metadata.Metadata
import org.apache.tika.mime.MediaType
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream

class IsPDFValidator : ConstraintValidator<IsPDF, MultipartFile> {
    private val metadata = Metadata()
    private val detector = DefaultDetector()

    override fun isValid(value: MultipartFile, context: ConstraintValidatorContext): Boolean =
        detector.detect(BufferedInputStream(value.inputStream), metadata) == MediaType.parse("application/pdf")
}
