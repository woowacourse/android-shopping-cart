package woowacourse.shopping.ui.productdetail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import woowacourse.ProductDetailSource
import woowacourse.shopping.AppContainer.cartRepository
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import woowacourse.shopping.ui.productdetail.component.MintButton
import woowacourse.shopping.ui.productdetail.component.ProductDetail
import woowacourse.shopping.ui.productdetail.component.ProductDetailTopAppBar
import woowacourse.shopping.ui.productdetail.component.RecentlyViewedProductCard
import woowacourse.shopping.ui.productdetail.viewmodel.ProductDetailViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    product: Product?,
    source: ProductDetailSource,
    onProductClick: (Uuid) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val addToCartText = stringResource(R.string.add_to_the_shopping_cart)
    val addToCartSnackbarText = stringResource(R.string.add_to_the_shopping_cart_snackbar_text)
    var quantity by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = { ProductDetailTopAppBar(onClose) },
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (product != null) {
                MintButton(
                    onClick = {
                        scope.launch {
                            cartRepository.addProduct(
                                product = product,
                                quantityToAdd = quantity,
                            )
                            snackbarHostState.showSnackbar(addToCartSnackbarText)
                        }
                    },
                    text = addToCartText,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (product != null) {
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    ProductDetail(
                        product = product,
                        quantity = quantity,
                        increaseQuantity = { quantity++ },
                        decreaseQuantity = { quantity-- },
                    )
                    val lastViewedProduct = viewModel.lastViewedProduct
                    if (source == ProductDetailSource.PRODUCT_LIST && lastViewedProduct != null) {
                        Box(modifier = Modifier.padding(horizontal = 18.dp)) {
                            RecentlyViewedProductCard(
                                viewModel = viewModel,
                                onClick = { onProductClick(lastViewedProduct.productId) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductDetailScreenPreview() {
    val packageName = LocalContext.current.packageName
    val allProducts = ProductFixture.productList(packageName)
    val currentProduct = allProducts.last()

    val viewModel = remember {
        ProductDetailViewModel(
            recentViewedProductsRepository =
                object : RecentlyViewedProductsRepository {
                    override suspend fun saveViewedProduct(productId: Uuid) = Unit

                    override fun getRecentlyViewedProducts(): Flow<Products> =
                        flowOf(Products(allProducts.take(3)))

                    override suspend fun getLastViewedProduct(): Product? =
                        allProducts.firstOrNull()
                },
            currentProductId = currentProduct.productId,
        )
    }

    ProductDetailScreen(
        viewModel = viewModel,
        product = currentProduct,
        source = ProductDetailSource.PRODUCT_LIST,
        onProductClick = {},
        onClose = {},
    )
}
