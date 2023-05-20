package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductsAdapter
import woowacourse.shopping.feature.list.item.CartProductItem
import kotlin.properties.Delegates

class CartActivity : AppCompatActivity(), CartActivityContract.View {
    private lateinit var presenter: CartActivityContract.Presenter
    private lateinit var binding: ActivityCartBinding

    private var page: Int by Delegates.observable(1) { _, _, new ->
        presenter.setUpData(new)
        binding.pageNumberTv.text = page.toString()
    }

    private lateinit var adapter: CartProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()

        val db = CartDbHandler(CartDbHelper(this).writableDatabase)
        presenter = CartActivityPresenter(this, db)
        presenter.setUpData(OPEN_PAGE)
        presenter.setUpButton()
    }

    override fun setUpRecyclerView(cartItems: List<CartProductItem>) {
        adapter = CartProductsAdapter(
            onDeleteItem = { listItem -> onDeleteItem(listItem) },
        )
        binding.cartProductRv.adapter = adapter
        adapter.setItems(cartItems)
    }

    override fun setButtonListener(maxPage: Int) {
        updateButtonsEnabledState(maxPage)
        binding.pageAfterTv.setOnClickListener {
            if (page < maxPage) {
                ++page
            }
            updateButtonsEnabledState(maxPage)
        }

        binding.pageBeforeTv.setOnClickListener {
            if (page > 1) {
                --page
            }
            updateButtonsEnabledState(maxPage)
        }
    }

    private fun updateButtonsEnabledState(maxPage: Int) {
        binding.pageBeforeTv.isEnabled = page > 1
        binding.pageAfterTv.isEnabled = page < maxPage
    }

    override fun updateAdapterData(cartItems: List<CartProductItem>) {
        adapter.setItems(cartItems)
    }

    private fun setUpView() {
        binding.pageNumberTv.text = "$page"
    }

    private fun onDeleteItem(productView: CartProductItem) {
        presenter.deleteData(page, productView)
    }

    companion object {
        private const val OPEN_PAGE = 1

        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
