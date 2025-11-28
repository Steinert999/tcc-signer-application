package br.edu.ies.api.infraestructure.repository.converter

import br.edu.ies.api.infraestructure.repository.document.enum.SignStatus
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component

@Component
@WritingConverter
class SignStatusWritingConverter : Converter<SignStatus, String> {
    override fun convert(source: SignStatus): String = source.name
}

@Component
@ReadingConverter
class SignStatusReadingConverter : Converter<String, SignStatus> {
    override fun convert(source: String): SignStatus = SignStatus.valueOf(source)
}
