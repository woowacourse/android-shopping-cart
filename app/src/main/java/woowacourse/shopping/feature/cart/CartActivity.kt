package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductsAdapter
import woowacourse.shopping.feature.list.item.CartProductItem
import woowacourse.shopping.feature.list.item.ListItem
import kotlin.properties.Delegates

class CartActivity : AppCompatActivity(), CartActivityContract.View {
    private lateinit var presenter: CartActivityContract.Presenter
    private var _binding: ActivityCartBinding? = null
    private val binding: ActivityCartBinding
        get() = _binding!!

    private val db: CartDbHandler by lazy {
        CartDbHandler(CartDbHelper(this).writableDatabase)
    }

    private var page: Int by Delegates.observable(1) { _, _, new ->
        presenter.setUpData(new)
        binding.pageNumberTv.text = page.toString()
    }

    private lateinit var adapter: CartProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CartActivityPresenter(this, db)
        presenter.setUpData(OPEN_PAGE)
        presenter.setUpButton()
        setUpView()
    }

    override fun setUpRecyclerView(cartItems: List<CartProductItem>) {
        adapter = CartProductsAdapter(
            onXClick = { listItem -> itemXClickEvent(listItem) },
        )
        binding.cartProductRv.adapter = adapter
        adapter.setItems(cartItems)
    }

    override fun setButtonListener(maxPage: Int) {
        binding.pageAfterTv.setOnClickListener {
            if (page < maxPage) {
                ++page
            }
        }

        binding.pageBeforeTv.setOnClickListener {
            if (page > 1) {
                --page
            }
        }
    }

    override fun updateAdapterData(cartItems: List<CartProductItem>) {
        adapter.setItems(cartItems)
    }

    private fun setUpView() {
        binding.pageNumberTv.text = OPEN_PAGE.toString()
        binding.pageBeforeTv.setBackgroundColor(getColor(R.color.gray))
    }

    private fun itemXClickEvent(listItem: ListItem) {
        when (listItem) {
            is CartProductItem -> {
                presenter.deleteData(page, listItem)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val OPEN_PAGE = 1

        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
