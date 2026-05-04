package woowacourse.shopping.presentation.shopping.ui

import android.content.Intent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.R
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.common.ShoppingAppBar
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.detail.DetailActivity

@Composable
fun ShoppingScreen(modifier: Modifier = Modifier) {
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()
    var products by rememberSaveable { mutableStateOf(emptyList<ProductUiModel>()) }

    val state =
        remember {
            ShoppingStateHolder(
                scope = scope,
                getCurrentProducts = { products },
                onProductsChanged = { newList -> products = newList },
            )
        }

    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = stringResource(R.string.shopping_cart),
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clickable {
                                    val intent = Intent(activity, CartActivity::class.java)
                                    activity?.startActivity(intent)
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
                        activity?.startActivity(DetailActivity.newIntent(activity, product.id))
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
