package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductModel(val id: Int, val name: String, val imageUrl: String, val price: Int) : Parcelable
