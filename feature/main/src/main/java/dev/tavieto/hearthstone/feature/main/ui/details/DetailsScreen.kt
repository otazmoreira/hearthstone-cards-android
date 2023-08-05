package dev.tavieto.hearthstone.feature.main.ui.details

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import dev.tavieto.hearthstone.feature.main.R
import dev.tavieto.hearthstone.feature.main.model.CardModel
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsViewAction.Navigate
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsViewAction.Set
import dev.tavieto.hearthstone.feature.main.util.HtmlUtils

@Composable
fun DetailsScreen(
    card: CardModel?,
    viewModel: DetailsViewModel
) {
    val state by viewModel.state.collectAsState()
    val action = viewModel::dispatchAction

    Content(state = state, action = viewModel::dispatchAction)

    LaunchedEffect(Unit) {
        action(Set.Card(card))
    }
}

@Composable
private fun Content(
    state: DetailsViewState,
    action: (DetailsViewAction) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { action(Navigate.PopBackStack) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    ) { padding ->
        Crossfade(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            targetState = state.card != null,
            label = "ContentCrossfade",
            content = { if (it) ContentCard(state) else ContentNullCard() }
        )
    }
}

@Composable
private fun ContentNullCard() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            text = "Selecione um card para ver seus detalhes!".uppercase(),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ContentCard(state: DetailsViewState) {
    require(value = state.card != null, lazyMessage = { "DetailsViewState.card can not be null" })

    val card by remember(state.card) { mutableStateOf(state.card) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(card) {
            if (img.isNotBlank()) {
                GlideImage(
                    modifier = Modifier.width(300.dp),
                    imageModel = img,
                    contentScale = ContentScale.Fit,
                    previewPlaceholder = R.drawable.img_card_preview
                )
            }
            Text(stringResource(R.string.name_label, name))
            Text(stringResource(R.string.flavor_label, flavor))
            Text(stringResource(R.string.attack_label, attack))
            Text(stringResource(R.string.card_set_label, cardSet))
            Text(stringResource(R.string.cost_label, cost))
            Text(stringResource(R.string.faction_label, faction))
            Text(stringResource(R.string.health_label, health))
            Text(stringResource(R.string.race_label, race))
            Text(stringResource(R.string.rarity_label, rarity))
            Text(stringResource(R.string.type_label, type))
            Text(
                text = stringResource(R.string.description_label),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = HtmlUtils.simpleHtmlToAnnotatedString(
                    text = text,
                    style = MaterialTheme.typography.body1
                )
            )
        }
    }
}

@Preview
@Composable
fun ContentPreview() {
    Content(state = DetailsViewState(card = CardModel.mock), action = {})
}

@Preview
@Composable
fun ContentNullCardPreview() {
    Content(state = DetailsViewState(), action = {})
}
