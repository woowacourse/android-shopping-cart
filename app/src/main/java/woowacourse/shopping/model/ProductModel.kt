package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(val id: Int, val name: String, val imageUrl: String, val price: Int, var count: Int) : Parcelable
