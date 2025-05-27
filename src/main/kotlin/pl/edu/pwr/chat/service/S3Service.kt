package pl.edu.pwr.chat.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import java.time.Duration


@Service
class S3Service(@Value("\${aws.s3.bucket-name}") val bucketName: String) {
    fun generatePresignedUploadUrl(key: String, expirationMinutes: Int): String {
        val presigner = S3Presigner.create()
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        return presigner.presignPutObject {
            it
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(expirationMinutes.toLong()))
        }.url().toString()
    }

    fun generatePresignedDownloadUrl(key: String, expirationMinutes: Int): String {
        val presigner = S3Presigner.create()
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        return presigner.presignGetObject { builder ->
            builder.getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(expirationMinutes.toLong()))
        }.url().toString()
    }
}