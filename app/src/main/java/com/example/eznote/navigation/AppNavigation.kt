package com.example.eznote.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eznote.screens.NoteDetailScreen
import com.example.eznote.screens.NoteScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EzNoteScreen.HomeScreen.route, modifier = modifier) {
        composable(EzNoteScreen.HomeScreen.route) {
            NoteScreen(
                snackbarHostState = snackbarHostState,
                onNoteClick = { clickedNoteId ->
                    navController.navigate("${EzNoteScreen.DetailScreen.route}/$clickedNoteId")
                }
            )
        }
        composable(
            route = EzNoteScreen.DetailScreen.routeWithArgs,
            arguments = listOf(navArgument(EzNoteScreen.DetailScreen.argumentKey) {
                type = NavType.StringType
            })
            ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString(EzNoteScreen.DetailScreen.argumentKey)
            NoteDetailScreen(
                noteId = noteId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}