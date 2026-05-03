package woowacourse.shopping.ui.shopping

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.component.ShoppingAppBar
import woowacourse.shopping.ui.detail.DetailActivity
import woowacourse.shopping.ui.model.ProductUiModel

@Composable
fun ShoppingScreen(modifier: Modifier = Modifier) {
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()
    var savedProducts by rememberSaveable {
        mutableStateOf(arrayListOf<ProductUiModel>())
    }

    var savedCanLoadMore by rememberSaveable {
        mutableStateOf(true)
    }

    var savedOffset by rememberSaveable {
        mutableIntStateOf(0)
    }

    val state =
        remember {
            ShoppingStateHolder(
                scope = scope,
                initialProducts = savedProducts,
                initialCanLoadMore = savedCanLoadMore,
                initialOffset = savedOffset,
                onProductsChanged = { products ->
                    savedProducts = ArrayList(products)
                },
                onCanLoadMoreChanged = { canLoadMore ->
                    savedCanLoadMore = canLoadMore
                },
                onOffsetChanged = { offset ->
                    savedOffset = offset
                },
            )
        }
    val products = state.currentProducts

    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Text(
                        text = "Shopping",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "쇼핑 카트",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clickable {
                                    activity?.startActivity(CartActivity.getIntent(activity))
                                },
                    )
                },
                modifier = modifier.fillMaxWidth(),
            )
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        ShoppingContents(
            products = products.toImmutableList(),
            modifier = Modifier.padding(innerPadding),
            onLoad = { state.loadMore() },
            isCanLoadMore = state.canLoadMore,
        )
    }
}

@Composable
private fun ShoppingContents(
    products: ImmutableList<ProductUiModel>,
    onLoad: () -> Unit,
    isCanLoadMore: Boolean,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 20.dp),
        ) {
            items(
                items = products,
                key = { product -> product.id },
            ) { product ->
                ProductCard(
                    onClick = {
                        activity?.startActivity(
                            DetailActivity.getIntent(
                                context = activity,
                                id = product.id,
                            ),
                        )
                    },
                    imageUrl = product.imageUrl,
                    productName = product.name,
                    price = product.price,
                )
            }
            if (isCanLoadMore) {
                item(
                    span = { GridItemSpan(2) },
                ) {
                    LoadButton(
                        onClick = onLoad,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen()
}
