package com.eleks.cah.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ImagesRepository(
    private val client: HttpClient = HttpClient()
) {
    
    suspend fun getImageByPrompt(prompt: String): String {
        val response = client.request {
            headers {
                set("Authorization", "Bearer $OPEN_API_TOKEN")
            }

            setBody(ImageRequest(prompt, 1, "256x256"))
        }
        return response.body<ImageResponseBody>()
            .data
            .first()
            .url
    }

    companion object {
        private const val OPEN_API_TOKEN = "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"
    }

    data class ImageRequest(
        val prompt: String,
        val n: Int,
        val size: String
    )

    data class ImageResponseBody(
        val data: List<ImageResponse>
    )

    data class ImageResponse(
        val url: String
    )
}