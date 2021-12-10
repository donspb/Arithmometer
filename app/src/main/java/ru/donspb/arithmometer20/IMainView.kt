package ru.donspb.arithmometer20

import android.view.View

interface IMainView{
    fun showOnScreen(topScreenData: String?, botScreenData: String?)
    fun clearScreen()
}