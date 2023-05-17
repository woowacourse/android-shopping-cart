package woowacourse.shopping.productdetail

import java.io.Serializable

interface ProductCountPickerListener : Serializable {

    fun onCompleted()

    fun onPlus(count: Int)

    fun onMinus(count: Int)
}
