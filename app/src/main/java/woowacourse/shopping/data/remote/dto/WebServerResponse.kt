package woowacourse.shopping.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WebServerResponse(
    val id: String,
    val name: String,
    val price: Int,
    @SerialName("imageUrl")
    val imageUri: String,
)
