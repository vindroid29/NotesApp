package com.globant.notesapp.presentation.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globant.notesapp.core.common.CommonLoading
import com.globant.notesapp.core.common.UiState
import com.globant.notesapp.theme.LightRed
import kotlinx.coroutines.delay
import notesapp.composeapp.generated.resources.Res
import notesapp.composeapp.generated.resources.baseline_notes_24
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, val title: String, val content: String
)

val LocalVehicle = compositionLocalOf<Vehicle> {
    Vehicle("Meteor",350,27.0)
}

data class Vehicle(val name: String, val engineCC: Long, val torque: Double)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen() {
    val noteListViewModel = koinViewModel<NoteListViewModel>()
    val noteState by noteListViewModel.notesListState.collectAsStateWithLifecycle()

    val showBottomSheet by noteListViewModel.addNoteDialogState.collectAsStateWithLifecycle()
    val bottomsheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false) { sheetValue ->
            println("sheetValue ${sheetValue.ordinal}")
            showBottomSheet
        }

    val listState = rememberLazyListState()
    val showFab by remember {
        derivedStateOf {
            /*val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (visibleItemsInfo.isEmpty()) {
                true
            } else {
                val lastVisibleItem = visibleItemsInfo.last()
                layoutInfo.viewportEndOffset >= lastVisibleItem.offset + lastVisibleItem.size
            }*/
            true
        }
    }
    Scaffold(floatingActionButton = {
        AnimatedVisibility(
            visible = showFab, enter = fadeIn() + scaleIn(), exit = fadeOut() + scaleOut()
        ) {
            AddFloatingActionButton {
                noteListViewModel.showAddNoteBottomsheet()
            }
        }
    }) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            when (val state = noteState) {
                is UiState.Loading -> {
                    CommonLoading()
                }

                is UiState.Success -> {
                    if (state.data.isEmpty()) ShowEmptyView() else LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState
                    ) {
                        items(items = state.data, key = { it.id }) { note ->
                            NoteCardView(note = note) {
                                noteListViewModel.deleteNote(note)
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    ShowEmptyView()
                }
            }
            if (showBottomSheet) {
                CompositionLocalProvider(LocalVehicle provides Vehicle("Super Meteor",650,52.3)){
                    AddNoteScreen(bottomsheetState) {
                        noteListViewModel.dismissAddNoteBottomsheet()
                    }
                }
            }
        }
    }
}

@Composable
fun ShowEmptyView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(180.dp),
            painter = painterResource(Res.drawable.baseline_notes_24),
            contentDescription = "empty list"
        )
        Text(
            text = "No items found. \nClick + to add new note",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick.invoke() },
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "Add new item"
        )
    }


}

@Composable
fun NoteCardView(note: Note, onDeleteClick: (Note) -> Unit) {
    SwipeToDeleteContainer(
        item = note, onDelete = { onDeleteClick.invoke(note) }) {
        Card(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors()
        ) {
            Column {
                Text(
                    text = note.title,
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = note.content,
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun <T> SwipeToDeleteContainer(
    item: T, onDelete: (T) -> Unit, animationDuration: Int = 1000, content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val density = LocalDensity.current
    val state = rememberSwipeToDismissBoxState(confirmValueChange = { value ->
        if (value == SwipeToDismissBoxValue.EndToStart) {
            isRemoved = true
            true
        } else {
            false
        }
    }, positionalThreshold = {
        with(density) { 80.dp.toPx() }
    })
    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }
    AnimatedVisibility(
        visible = !isRemoved, exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration), shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state, backgroundContent = {
            DeleteBackground(swipeDismissState = state)
        }, onDismiss = { SwipeToDismissBoxValue.EndToStart }, enableDismissFromEndToStart = true
        ) { content(item) }
    }
}

@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        LightRed
    } else Color.Transparent

    Card(
        modifier = Modifier.padding(4.dp), colors = CardDefaults.cardColors(color)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(end = 20.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White
            )
        }
    }
}
