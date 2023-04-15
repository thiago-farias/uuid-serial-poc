package com.example

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.http.MediaType



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@ActiveProfiles("integration-test")
class RegisterUseCaseIntegrationTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val repository: ExemploRepository) {

    @BeforeEach
    fun setup() {
        repository.deleteAll()
    }

    @Test
    fun `registrationWorksThroughAllLayers`() {
        val ex = Exemplo(description = "inside test")

        mockMvc.post("/exemplo") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(ex)
        }
        .andExpect {
            status { isOk() }
        }

        val exEntity = repository.findByDescription("inside test")
        assertNotNull(exEntity.id)
    }
}
