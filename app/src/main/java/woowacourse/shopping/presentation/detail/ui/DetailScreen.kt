package woowacourse.shopping.presentation.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.presentation.common.QuantityCounter
import woowacourse.shopping.presentation.common.ShoppingAppBar
import woowacourse.shopping.presentation.detail.viewmodel.DetailViewModel
import woowacourse.shopping.ui.theme.Gray40
import woowacourse.shopping.ui.theme.Green40
import woowacourse.shopping.util.formattedPrice

@Composable
fun DetailScreen(
    id: Long,
    isFromLastSeen: Boolean = false,
    onNavigateToCart: (AddItemResult) -> Unit,
    onClickLastProductCard: (Long) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val product = uiState.product

    LaunchedEffect(Unit) {
        viewModel.loadProduct(id, isFromLastSeen)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            ShoppingAppBar(
                contents = {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable { onBack() },
                    )
                },
            )
        },
        bottomBar = {
            Box(
                modifier =
                    Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Green40)
                        .clickable {
                            scope.launch {
                                val result =
                                    viewModel.addToCart(
                                        id = id,
                                        quantity = uiState.quantity,
                                    )
                                onNavigateToCart(result)
                            }
                        },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.add_product_to_cart),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        },
        modifier =
            modifier
                .statusBarsPadding(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            DetailContent(
                imageUrl = product.imageUrl,
                productName = product.name,
                price = uiState.price,
                quantity = uiState.quantity,
                onIncrease = {
                    scope.launch { viewModel.increase() }
                },
                onDecrease = {
                    scope.launch { viewModel.decrease() }
                },
                modifier = Modifier.padding(innerPadding),
            )
            if (uiState.showLastSeenProductCard) {
                LastSeenProductCard(
                    name = uiState.lastSeenProduct!!.name,
                    onClick = { onClickLastProductCard(uiState.lastSeenProduct!!.id) },
                    modifier = Modifier.padding(horizontal = 18.dp),
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    imageUrl: String,
    productName: String,
    price: Long,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = productName,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
        )
        Text(
            text = productName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 18.dp),
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Gray40)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = formattedPrice(price),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 26.sp,
                color = Color.Black,
                modifier =
                    Modifier
                        .weight(.5f),
            )
            QuantityCounter(
                quantity = quantity,
                onIncrease = onIncrease,
                onDecrease = onDecrease,
                modifier =
                    Modifier
                        .weight(.5f),
            )
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        id = 1L,
        onNavigateToCart = {},
        onClickLastProductCard = {},
        onBack = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreview() {
    DetailContent(
        imageUrl = "",
        productName = "Test",
        price = 10000,
        onDecrease = {},
        onIncrease = {},
        quantity = 3,
    )
}
