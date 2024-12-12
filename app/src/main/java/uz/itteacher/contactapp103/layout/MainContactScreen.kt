package uz.itteacher.contactapp103.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uz.itteacher.contactapp103.R
import uz.itteacher.contactapp103.db.AppDataBase

@Composable
fun MainContactScreen(navController: NavHostController, appDataBase: AppDataBase) {
    val myContacts = appDataBase.getMyContactDao().getAllContacts().sortedBy { it.name }
    val filter = remember {
        myContacts.mapNotNull { it.name.firstOrNull() }
            .filter { it.isLetterOrDigit() }
            .distinct()
            .sorted()
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedLetter by remember { mutableStateOf<Char?>(null) }
    val displayedContacts = remember {
        myContacts
    }

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
                        Icon(Icons.Default.Search, contentDescription = "Search", modifier = Modifier.size(30.dp))
                    }
                    IconButton(onClick = { navController.navigate("create") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(30.dp))
                    }
                }
            }

            LazyColumn(state = listState, modifier = Modifier.padding(10.dp)) {
                items(displayedContacts) { contact ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                                val scrollIndex = myContacts.indexOfFirst { it.name.startsWith(letter, true) }
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