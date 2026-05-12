package woowacourse.shopping.feature.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.R
import woowacourse.shopping.feature.common.ProductQuantitySelector
import woowacourse.shopping.feature.common.state.ProductUiModel
import woowacourse.shopping.feature.format.DecimalPriceFormatter
import woowacourse.shopping.feature.productdetail.viewmodel.ProductDetailLoadingState
import woowacourse.shopping.feature.productdetail.viewmodel.ProductDetailViewModel
import woowacourse.shopping.feature.productlist.LoadingIndicator
import woowacourse.shopping.feature.productlist.PreviewableAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    id: String,
    activityFinish: () -> Unit,
    onClickRecentButton: (String) -> Unit,
    modifier: Modifier = Modifier,
    recentProductId: String? = null,
    viewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModel.Factory),
) {
    LaunchedEffect(Unit) {
        viewModel.initialLoading(
            productId = id,
            recentProductId = recentProductId,
        )
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val productState = uiState.productState
    val recentState = uiState.recentProductState

    when {
        productState is ProductDetailLoadingState.Loading ||
            recentState is ProductDetailLoadingState.Loading -> LoadingIndicator()

        productState is ProductDetailLoadingState.Error ||
            productState is ProductDetailLoadingState.None -> ProductDetailErrorScreen(activityFinish)

        productState is ProductDetailLoadingState.Success -> ProductDetailContent(
            ProductUiModel = productState.product,
            recentProductLoadingState = recentState,
            quantity = uiState.quantity,
            activityFinish = activityFinish,
            increase = viewModel::increase,
            decrease = viewModel::decrease,
            onAddCartButton = {
                viewModel.addToCart()
                activityFinish()
            },
            onClickRecentButton = onClickRecentButton,
            modifier = modifier,
        )
    }
}

@Composable
fun ProductDetailContent(
    ProductUiModel: ProductUiModel,
    recentProductLoadingState: ProductDetailLoadingState,
    quantity: Int,
    activityFinish: () -> Unit,
    increase: () -> Unit,
    decrease: () -> Unit,
    onAddCartButton: () -> Unit,
    onClickRecentButton: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = Color.White,
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductAppBar(
                onCloseClick = activityFinish,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column {
                PreviewableAsyncImage(
                    imageUrl = ProductUiModel.imageUrl,
                    description = ProductUiModel.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                )
                Text(
                    text = ProductUiModel.name,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 18.dp),
                )
                HorizontalDivider()
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 18.dp),
                ) {
                    Text(
                        text = DecimalPriceFormatter().format(ProductUiModel.price * quantity),
                        fontWeight = FontWeight.W400,
                        fontSize = 20.sp,
                    )
                    ProductQuantitySelector(
                        decreaseEnabled = quantity > 1,
                        quantity = quantity,
                        onIncrease = increase,
                        onDecrease = decrease,
                        modifier = Modifier.size(
                            width = 126.dp,
                            height = 42.dp,
                        ),
                    )
                }
                RecentProductLetter(
                    loadingState = recentProductLoadingState,
                    onClickRecentProduct = onClickRecentButton,
                )
            }

            CartPutButton(
                onClick = onAddCartButton,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun CartPutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xff04c09e))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
    ) {
        Text(
            stringResource(R.string.product_detail_select),
            fontWeight = FontWeight.W700,
            fontSize = 24.sp,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailErrorScreen(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = Color.White,
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductAppBar(
                onCloseClick = onCloseClick,
            )
        },
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.product_detail_error_description),
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(64.dp),
            )
            Text(
                text = stringResource(R.string.product_detail_entry_error),
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}

@Preview
@Composable
private fun ProductScreenPreview() {
    ProductDetailContent(
        activityFinish = {},
        ProductUiModel = ProductUiModel(
            name = "asd",
            price = 2000,
            imageUrl = "",
            id = "1",
            quantity = 5,
        ),
        quantity = 5,
        increase = { },
        decrease = { },
        onAddCartButton = { },
        onClickRecentButton = {},
        recentProductLoadingState = ProductDetailLoadingState.Error("asd"),
    )
}

@Preview
@Composable
private fun ProductErrorScreenPreview() {
    ProductDetailErrorScreen(onCloseClick = {})
}

@Preview
@Composable
private fun CartPutButtonPreview() {
    CartPutButton(onClick = {})
}
