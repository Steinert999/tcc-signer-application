package br.edu.ies.api.infraestructure.repository.converter

import br.edu.ies.api.infraestructure.repository.document.enum.SignerType
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component


@Component
@WritingConverter
class SignerTypeWritingConverter : Converter<SignerType, String> {
    override fun convert(source: SignerType): String = source.name
}

@Component
@ReadingConverter
class SignerTypeReadingConverter : Converter<String, SignerType> {
    override fun convert(source: String): SignerType = SignerType.valueOf(source)
}