package woowacourse.shopping.presentation.cart.ui

import android.widget.Toast
import androidx.activity.compose.LocalActivity
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.RemoveItemResult
import woowacourse.shopping.presentation.cart.model.CartItemUiModel
import woowacourse.shopping.presentation.common.ShoppingAppBar
import woowacourse.shopping.presentation.common.model.ProductUiModel

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val page = rememberSaveable { mutableStateOf(0) }
    val state = remember { CartStateHolder(initialPage = page.value) }

    LaunchedEffect(Unit) {
        state.loadCartItems()
    }

    LaunchedEffect(state.page) {
        page.value = state.page
    }

    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
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
        bottomBar = {
            if (state.totalCartSize > 5) {
                CartPageSection(
                    page = state.page + 1,
                    onNext = { scope.launch { state.nextPage() } },
                    onPrevious = { scope.launch { state.previousPage() } },
                    isCanMoveNext = state.isCanMoveNext,
                )
            }
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        CartContent(
            onDeleteItem = {
                scope.launch {
                    val result = state.deleteItem(it)
                    when (result) {
                        is RemoveItemResult.Success -> {
                            Toast
                                .makeText(
                                    context,
                                    R.string.delete_item_success,
                                    Toast.LENGTH_SHORT,
                                ).show()
                        }
                        is RemoveItemResult.NotFoundItem -> {
                            Toast
                                .makeText(context, R.string.not_found_item, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            },
            cartItems = state.currentCartItems.toImmutableList(),
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        )
    }
}

@Composable
private fun CartContent(
    onDeleteItem: (String) -> Unit,
    cartItems: ImmutableList<CartItemUiModel>,
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
                    onDeleteItem(product.id)
                },
            )
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen()
}

@Preview(showBackground = true)
@Composable
private fun CartContentPreview() {
    CartContent(
        onDeleteItem = {},
        cartItems =
            listOf(
                CartItemUiModel(
                    product =
                        ProductUiModel(
                            id = "1",
                            name = "커피",
                            imageUrl = "",
                            price = 1000,
                        ),
                    quantity = 1,
                    totalPrice = 1000,
                ),
                CartItemUiModel(
                    product =
                        ProductUiModel(
                            id = "2",
                            name = "커피",
                            imageUrl = "",
                            price = 1000,
                        ),
                    quantity = 1,
                    totalPrice = 1000,
                ),
            ).toImmutableList(),
    )
}
