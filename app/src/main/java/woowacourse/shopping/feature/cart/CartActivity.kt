package woowacourse.shopping.feature.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.component.ShoppingAppBar
import woowacourse.shopping.core.data.CartRepository
import woowacourse.shopping.core.model.Product
import woowacourse.shopping.feature.cart.model.CartItem
import woowacourse.shopping.feature.cart.ui.CartCard
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                val cartItems = CartRepository.getCart().items
                CartScreen(
                    onDeleteItem = { CartRepository.deleteItem(it) },
                    cartItems = cartItems.toImmutableList(),
                )
            }
        }
    }
}

@Composable
fun CartScreen(
    onDeleteItem: (Product) -> Unit,
    cartItems: ImmutableList<CartItem>,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable {
                                    activity?.finish()
                                },
                    )
                    Spacer(modifier = Modifier.width(21.dp))
                    Text(
                        text = "Cart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                    )
                },
            )
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        CartContent(
            onDeleteItem = onDeleteItem,
            cartItems = cartItems,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        )
    }
}

@Composable
private fun CartContent(
    onDeleteItem: (Product) -> Unit,
    cartItems: ImmutableList<CartItem>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(
            items = cartItems,
            key = { it.product.id },
        ) { item ->
            val product = item.product
            CartCard(
                productName = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
                onDeleteItem = {
                    onDeleteItem(product)
                },
            )
        }
    }
}
