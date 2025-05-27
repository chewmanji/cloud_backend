package pl.edu.pwr.chat.controller

import org.springframework.web.bind.annotation.*
import pl.edu.pwr.chat.dto.MessageRequestTO
import pl.edu.pwr.chat.service.ChatServiceImpl
import pl.edu.pwr.chat.service.S3Service

@RestController
@RequestMapping("/api/chat/files")
class FileController(private val s3Service: S3Service, private val chatServiceImpl: ChatServiceImpl) {

    @PostMapping("/presign")
    fun getPresignedUrl(@RequestBody request: PresignRequest): String {
        val url = s3Service.generatePresignedUploadUrl(
            key = "uploads/${request.filename}",
            expirationMinutes = 60
        )
        println("Generated url: $url")
        chatServiceImpl.createLiveEvent(MessageRequestTO(request.username, request.filename, true))
        return url
    }

    @GetMapping("/{filename}")
    fun generateDownloadUrl(@PathVariable filename: String): String {
        val presignedUrl = s3Service.generatePresignedDownloadUrl("uploads/${filename}", 60)
        return presignedUrl
    }
}

data class PresignRequest(
    val username: String,
    val filename: String,
    val contentType: String
)