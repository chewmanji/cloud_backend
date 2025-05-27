package pl.edu.pwr.chat.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class ChatMessage(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val username: String,
    val message: String,
    val isFile: Boolean = false,
    val timestamp: LocalDateTime
)
