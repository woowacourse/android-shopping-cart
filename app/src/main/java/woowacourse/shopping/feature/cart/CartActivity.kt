package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.CartProduct
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductListAdapter
import woowacourse.shopping.feature.list.item.CartProductListItem
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toItem

class CartActivity : AppCompatActivity() {
    private var _binding: ActivityCartBinding? = null
    private val binding: ActivityCartBinding
        get() = _binding!!

    private val dbHandler: CartDbHandler by lazy {
        CartDbHandler(CartDbHelper(this).writableDatabase)
    }

    lateinit var adapter: CartProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carts: List<CartProductListItem> = dbHandler.getCartProducts().map(CartProduct::toItem)

        adapter = CartProductListAdapter(
            carts,
            onXClick = { listItem -> itemXClickEvent(listItem) }
        )
        binding.cartProductRv.adapter = adapter
    }

    private fun itemXClickEvent(listItem: ListItem) {
        when (listItem) {
            is CartProductListItem -> {
                dbHandler.deleteColumn(listItem.toDomain())
                val carts: List<CartProductListItem> =
                    dbHandler.getCartProducts().map(CartProduct::toItem)
                adapter.setItems(carts)
            }
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
