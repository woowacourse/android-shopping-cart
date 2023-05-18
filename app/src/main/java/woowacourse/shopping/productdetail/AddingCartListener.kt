package woowacourse.shopping.productdetail

import java.io.Serializable

interface AddingCartListener : Serializable {

    fun onAdded()
}
