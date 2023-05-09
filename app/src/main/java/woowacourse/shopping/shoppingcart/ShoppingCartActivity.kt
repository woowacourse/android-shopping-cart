package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.productdetail.ProductUiModel

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var binding: ActivityShoppingCartBinding
    val example = ProductUiModel(
        imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002487]_20210426091745467.jpg",
        name = "아메리카노",
        price = 5000
    )

    val exampleList = List(10) { example }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)
        binding.recyclerViewCart.adapter = ShoppingCartRecyclerAdapter(exampleList)
    }

    companion object {
        private const val CART_PRODUCT_KEY = "cart_product"

        fun getIntent(context: Context, product: ProductUiModel): Intent {
            val intent = Intent(context, ShoppingCartActivity::class.java).apply {
                putExtra(CART_PRODUCT_KEY, product)
            }

            return intent
        }
    }
}
