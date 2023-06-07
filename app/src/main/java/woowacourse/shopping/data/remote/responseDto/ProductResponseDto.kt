package woowacourse.shopping.data.remote.responseDto

import com.google.gson.annotations.SerializedName

data class ProductResponseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("itemImage")
    val itemImage: String,

)
