package woowacourse.shopping.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.feature.cart.component.CartItem
import woowacourse.shopping.feature.cart.component.CartPageButton
import woowacourse.shopping.feature.cart.component.CartTopAppBar
import woowacourse.shopping.feature.cart.model.CartInfo

@Composable
fun CartScreen(
    cartItems: ImmutableList<CartInfo>,
    isLastPage: Boolean,
    isFirstPage: Boolean,
    pageCount: Int,
    isShowControls: Boolean,
    onBackClick: () -> Unit,
    onCartDeleteClick: (id: String) -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CartTopAppBar(onClick = onBackClick)

        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 18.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            cartItems.forEach { cartInfo ->
                CartItem(
                    productName = cartInfo.productName,
                    productUrl = cartInfo.productImageUrl,
                    price = cartInfo.price,
                    onClick = { onCartDeleteClick(cartInfo.id) }
                )
            }
        }

        Spacer(Modifier.height(36.dp))

        if (isShowControls) {
            CartPageButton(
                onNextClick = onNextClick,
                onPreviousClick = onPreviousClick,
                pageText = pageCount.toString(),
                isPreviousEnabled = !isFirstPage,
                isNextEnabled = !isLastPage
            )
            Spacer(Modifier.height(36.dp))
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        cartItems = List(5) { CartInfo.PREVIEW }.toImmutableList(),
        onBackClick = {},
        onCartDeleteClick = {},
        isLastPage = false,
        isFirstPage = true,
        pageCount = 1,
        isShowControls = true,
        onNextClick = {},
        onPreviousClick = {},
    )
}
