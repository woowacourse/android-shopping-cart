package woowacourse.shopping.model.uimodel

import java.io.Serializable

class ProductUIModel(
    val id: Int,
    val name: String,
    val url: String,
    val price: Int,
) : Serializable
