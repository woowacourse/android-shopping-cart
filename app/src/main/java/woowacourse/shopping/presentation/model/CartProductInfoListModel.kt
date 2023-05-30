package woowacourse.shopping.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartProductInfoListModel(val items: List<CartProductInfoModel>) : Parcelable
