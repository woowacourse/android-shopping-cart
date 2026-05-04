package woowacourse.shopping.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.source.CartDataSourceImpl
import woowacourse.shopping.ui.component.topbar.NavigateUpTopBar

@Composable
fun CartScreen(
    cartStateHolder: CartStateHolder = rememberSaveable(
        saver = Saver(
            save = { it.curPage },
            restore = {
                CartStateHolder(
                    cartRepository = CartRepositoryImpl(CartDataSourceImpl),
                    initialPage = it,
                )
            },
        ),
    ) { CartStateHolder(cartRepository = CartRepositoryImpl(CartDataSourceImpl)) },
    onNavigateUp: () -> Unit,
) {
    val cartItems = cartStateHolder.cartItems
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        cartStateHolder.initCartItems()
    }

    Scaffold(
        topBar = {
            NavigateUpTopBar(
                title = "Cart",
                onNavigateUp = onNavigateUp,
            )
        },
        modifier = Modifier.systemBarsPadding(),
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 24.dp, horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(
                items = cartItems,
                key = { it.product.id },
            ) {
                CartItemCard(
                    imageUrl = it.product.imageUrl,
                    name = it.product.name,
                    price = it.product.price,
                    onDelete = {
                        scope.launch {
                            cartStateHolder.deleteCartItem(it.product.id)
                        }
                    },
                )
            }

            if (cartStateHolder.curPage != 1 || !cartStateHolder.isLast) {
                item {
                    CartPagination(
                        curPage = cartStateHolder.curPage,
                        isLastPage = cartStateHolder.isLast,
                        onPrevClick = {
                            scope.launch {
                                cartStateHolder.getPrevPage()
                            }
                        },
                        onNextClick = {
                            scope.launch {
                                cartStateHolder.getNextPage()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 50.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CartPagination(
    curPage: Int,
    isLastPage: Boolean,
    modifier: Modifier = Modifier,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PaginationButton(
            enabled = curPage != 1,
            onClick = onPrevClick,
        ) {
            Text(text = "<", fontSize = 22.sp)
        }

        Text(text = curPage.toString(), color = Color.Black, fontSize = 22.sp)

        PaginationButton(
            enabled = !isLastPage,
            onClick = onNextClick,
        ) {
            Text(text = ">", fontSize = 22.sp)
        }
    }
}

@Composable
private fun PaginationButton(
    enabled: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(0xff04C09E),
            disabledContainerColor = Color(0xffAAAAAA),
            disabledContentColor = Color.White,
        ),
    ) {
        content()
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        onNavigateUp = { },
    )
}
