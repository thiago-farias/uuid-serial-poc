// Importações necessárias
package com.example

import org.hibernate.annotations.Generated
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.GenerationTime
import org.hibernate.annotations.GenericGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.*

// Definição da entidade Exemplo
@Entity
@Table(name = "exemplo")
data class Exemplo(
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
        generator = "pg-uuid"
    )
    @GenericGenerator(
        name = "pg-uuid",
        strategy = "uuid2",
        parameters = [Parameter(
            name = "uuid_gen_strategy_class",
            value = "com.example.PostgreSQLUUIDGenerationStrategy"
        )]
    )
    val id: UUID? = null,

    @Column(name = "externalid", columnDefinition = "serial")
    @Generated(GenerationTime.INSERT)
    val externalId: Long? = null,
    val description: String = ""
)

// Interface do repositório JPA
@Repository
interface ExemploRepository : JpaRepository<Exemplo, UUID>

// Controlador REST
@RestController
@RequestMapping("/exemplo")
class ExemploController(private val repository: ExemploRepository) {

    // Operação GET /exemplo
    @GetMapping
    fun findAll() = repository.findAll()

    // Operação GET /exemplo/{uuid}
    @GetMapping("/{uuid}")
    fun findById(@PathVariable uuid: UUID) =
        repository.findById(uuid).orElseThrow { NoSuchElementException() }

    // Operação POST /exemplo
    @PostMapping
    fun create(@RequestBody exemplo: Exemplo) =
        repository.save(exemplo)

    // Operação PUT /exemplo/{uuid}
    @PutMapping("/{uuid}")
    fun update(@PathVariable uuid: UUID, @RequestBody exemplo: Exemplo) =
        repository.findById(uuid).map { existing ->
            repository.save(existing.copy(
                id = exemplo.id,
            ))
        }.orElseThrow { NoSuchElementException() }

    // Operação DELETE /exemplo/{uuid}
    @DeleteMapping("/{uuid}")
    fun delete(@PathVariable uuid: UUID) =
        repository.deleteById(uuid)
}

// Classe principal da aplicação
@SpringBootApplication
open class Application

// Função de entrada da aplicação
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
