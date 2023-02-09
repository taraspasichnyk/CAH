package com.eleks.cah.data

import com.eleks.cah.BuildKonfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ImagesRepository(
    private val client: HttpClient = HttpClient()
) {

    suspend fun getImageByPrompt(prompt: String): String {
        val response = client.request {
            headers {
                set("Authorization", "Bearer ${BuildKonfig.OPEN_API_KEY}")
            }

            setBody(ImageRequest(prompt, 1, DEFAULT_IMAGE_SIZE))
        }
        return response.body<ImageResponseBody>()
            .data
            .first()
            .url
    }

    companion object {
        private const val DEFAULT_IMAGE_SIZE = "256x256"
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