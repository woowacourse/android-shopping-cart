package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProductListScaffold(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Scaffold(
        topBar = {
            ProductListTopAppBar(
                onClick = onClick,
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            content()
        }
    }
}

@Preview
@Composable
fun ProductListScaffoldPreview() {
    ProductListScaffold(
        onClick = {},
        content = {},
    )
}
