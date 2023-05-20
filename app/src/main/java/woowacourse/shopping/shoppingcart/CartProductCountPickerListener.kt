package woowacourse.shopping.shoppingcart

interface CartProductCountPickerListener {

    fun onPlus(id: Int)

    fun onMinus(id: Int)
}
