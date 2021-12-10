package ru.donspb.arithmometer20

import android.os.Bundle
import android.view.View

interface IMainPresenter {
    fun attachView(view: IMainView)
    fun detachView(view: IMainView)

    fun actionPressed(operation: Int)
    fun numberPressed(number: String)
}