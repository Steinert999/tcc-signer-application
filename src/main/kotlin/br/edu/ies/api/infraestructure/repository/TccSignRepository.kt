package br.edu.ies.api.infraestructure.repository

import br.edu.ies.api.infraestructure.repository.document.TccSignDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TccSignRepository : MongoRepository<TccSignDocument, UUID> {

    @Query("{ 'status':  'WAITING_SIGNATURES'}")
    fun findAllWaitingSignatureDocuments(): List<TccSignDocument>

    @Query("{ 'status': 'CREATED' }")
    fun findAllCreatedDocuments(): List<TccSignDocument>

    fun findByDocumensoEnvelopeId(documensoEnvelopeId: String): TccSignDocument?
}