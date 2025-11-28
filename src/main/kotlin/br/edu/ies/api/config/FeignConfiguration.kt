package br.edu.ies.api.config

import feign.codec.Encoder
import feign.form.ContentType
import feign.form.MultipartFormContentProcessor
import feign.form.spring.SpringFormEncoder
import feign.form.spring.SpringManyMultipartFilesWriter
import feign.form.spring.SpringSingleMultipartFileWriter
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.support.JsonFormWriter
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["br.edu.ies.api.infraestructure.client"])
@Configuration
class FeignConfiguration {
    @Bean
    fun feignFormEncoder(messageConverters: ObjectFactory<HttpMessageConverters>): Encoder =
        SpringFormEncoder(SpringEncoder(messageConverters)).apply {
            (getContentProcessor(ContentType.MULTIPART) as MultipartFormContentProcessor)
                .apply {
                    addFirstWriter(jsonFormWriter())
                    addFirstWriter(SpringSingleMultipartFileWriter())
                    addFirstWriter(SpringManyMultipartFilesWriter())
                }
        }

    @Bean
    fun jsonFormWriter() = JsonFormWriter()

}
