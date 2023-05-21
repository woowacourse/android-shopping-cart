package woowacourse.shopping.shoppingcart.adapter

interface CartProductCountPickerListener {

    fun onPlus(id: Int)

    fun onMinus(id: Int)
}
