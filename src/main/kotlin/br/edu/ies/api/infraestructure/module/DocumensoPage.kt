package br.edu.ies.api.infraestructure.module

import com.fasterxml.jackson.annotation.JsonProperty

class DocumensoPage<T>(
    @field:JsonProperty("data")
    val data: List<T>,
    @field:JsonProperty("count")
    val count: Int,
    @field:JsonProperty("currentPage")
    val currentPage: Int,
    @field:JsonProperty("perPage")
    val offset: Int,
    @field:JsonProperty("totalPages")
    val total: Int
)