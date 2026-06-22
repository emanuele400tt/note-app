package com.example.eznote.navigation

sealed class EzNoteScreen (val route: String){
    object HomeScreen: EzNoteScreen("home_screen")

    object DetailScreen: EzNoteScreen("detail_screen") {
        const val routeWithArgs = "detail_screen/{noteId}"
        const val argumentKey = "noteId"
    }
}