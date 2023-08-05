package dev.tavieto.hearthstone.feature.main.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tavieto.hearthstone.core.uikit.components.LazyColumnPaging
import dev.tavieto.hearthstone.feature.main.model.CardModel
import dev.tavieto.hearthstone.feature.main.ui.home.HomeViewAction.Get
import dev.tavieto.hearthstone.feature.main.ui.home.HomeViewAction.Navigate

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()

    Content(state = state, action = viewModel::dispatchAction)
}

@Composable
private fun Content(
    state: HomeViewState,
    action: (HomeViewAction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumnPaging(
            modifier = Modifier.weight(1f),
            items = state.cards,
            requestNewPage = { action(Get.Page.Next) },
            itemContent = {
                HearthstoneCard(it) { action(Navigate.Details(it)) }
            }
        )
    }
}

@Composable
private fun HearthstoneCard(
    data: CardModel,
    onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = data.name,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier.weight(1f),
                text = data.cardSet,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
fun HearthstoneCardPreview() {
    HearthstoneCard(data = CardModel.mock, onClick = {})
}
