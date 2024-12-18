package uz.itteacher.contactapp103.layout

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uz.itteacher.contactapp103.R
import uz.itteacher.contactapp103.db.AppDataBase
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContactScreen(navController: NavHostController, appDataBase: AppDataBase) {
    val context = LocalContext.current
    var myContacts by remember {
        mutableStateOf(
            appDataBase.getMyContactDao().getAllContacts().sortedBy { it.name })
    }
    val filter = remember {
        myContacts.mapNotNull { it.name.firstOrNull() }
            .filter { it.isLetterOrDigit() }
            .distinct()
            .sorted()
    }

    var activeSwipeId by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedLetter by remember { mutableStateOf<Char?>(null) }



    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Kontaktlar", fontSize = 25.sp)
                Row {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate("create/0") }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            LazyColumn(state = listState, modifier = Modifier.padding(10.dp)) {
                items(myContacts, key = { it.id }) { contact ->
                    var swipeOffset by remember { mutableStateOf(0f) }
                    val maxSwipeOffset = 350f

                    Box {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                }) {

                            Row(
                                modifier = Modifier
                                    .background(Color.Blue)
                                    .padding(end = 16.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Edit tugmasi
                                IconButton(
                                    onClick = {
                                        navController.navigate("create/${contact.id}")

                                    },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = "Edit",
                                        tint = Color.White
                                    )
                                }

                                // Delete tugmasi
                                IconButton(
                                    onClick = {
                                        appDataBase.getMyContactDao().deleteContact(contact.id)
                                        myContacts =
                                            appDataBase.getMyContactDao().getAllContacts()
                                            .sortedBy { it.name }
                                    },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Edit",
                                        tint = Color.White
                                    )
                                }
                            }

                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset { IntOffset(swipeOffset.toInt(), 0) }
                                .draggable(
                                    orientation = Orientation.Horizontal,

                                    state = rememberDraggableState { delta ->
                                        if (activeSwipeId != contact.id) {
                                            activeSwipeId = contact.id
                                            swipeOffset = 0f
                                        }

                                        swipeOffset =
                                            (swipeOffset + delta).coerceIn(0f, maxSwipeOffset)
                                    },
                                    onDragStopped = {
                                        swipeOffset = if (swipeOffset > maxSwipeOffset / 2) {
                                            maxSwipeOffset
                                        } else {
                                            0f
                                        }
                                        if (swipeOffset == 0f) {
                                            activeSwipeId = 0
                                        }
                                    }
                                )
                                .clickable {
                                    activeSwipeId = 0
                                }
                                .background(Color.White)
                                .padding(horizontal = 16.dp)
                        ) {
                            Card(
                                Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                            ) {
                                Row(
                                    modifier = Modifier.padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.person),
                                        contentDescription = "Person",
                                        modifier = Modifier
                                            .size(70.dp)
                                            .padding(18.dp)
                                    )
                                    Text(text = contact.name, fontSize = 20.sp)
                                }
                            }

                            LaunchedEffect(activeSwipeId) {
                                if (activeSwipeId != contact.id && swipeOffset != 0f) {
                                    swipeOffset = 0f
                                }
                            }
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(Color(0x60CECECE), RoundedCornerShape(5.dp))
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(filter) { letter ->
                Text(
                    text = letter.toString(),
                    fontSize = 14.sp,
                    color = if (selectedLetter == letter) Color.Blue else Color.Black,
                    modifier = Modifier
                        .clickable {
                            selectedLetter = letter
                            coroutineScope.launch {
                                val scrollIndex =
                                    myContacts.indexOfFirst { it.name.startsWith(letter, true) }
                                if (scrollIndex != -1) {
                                    listState.scrollToItem(scrollIndex)
                                }
                            }
                        }
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}


