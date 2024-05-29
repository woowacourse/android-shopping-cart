package woowacourse.shopping.productlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangedItemsId(val ids: Set<Long>) : Parcelable {
    companion object {
        const val KEY_CHANGED_ITEMS = "changedItems"
    }
}
