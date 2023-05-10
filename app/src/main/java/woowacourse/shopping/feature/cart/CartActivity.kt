package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.CartProduct
import woowacourse.shopping.data.CartDbHandler
import woowacourse.shopping.data.CartDbHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductListAdapter
import woowacourse.shopping.feature.list.item.CartProductListItem
import woowacourse.shopping.feature.list.item.ProductListItem
import woowacourse.shopping.feature.model.mapper.toItem

class CartActivity : AppCompatActivity() {
    private var _binding: ActivityCartBinding? = null
    private val binding: ActivityCartBinding
        get() = _binding!!

    private val dbHandler: CartDbHandler by lazy {
        CartDbHandler(CartDbHelper(this).writableDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        val mockkCart = listOf(
//            CartProductListItem(
//                1, "", "마스터볼", 2000
//            )
//        )

        val carts: List<CartProductListItem> = dbHandler.getCartProducts().map(CartProduct::toItem)

        val adapter = CartProductListAdapter(
            carts,
            onXClick = { listItem ->
                when (listItem) {
                    is ProductListItem -> {
                        Log.d("otter", "엑스 클릭")
                    }
                }
            }
        )
        binding.cartProductRv.adapter = adapter
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
