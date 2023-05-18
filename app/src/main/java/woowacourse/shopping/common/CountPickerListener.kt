package woowacourse.shopping.common

import java.io.Serializable

interface CountPickerListener : Serializable {

    fun onPlus()

    fun onMinus()
}
