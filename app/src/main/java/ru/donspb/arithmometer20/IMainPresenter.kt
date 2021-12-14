package ru.donspb.arithmometer20

interface IMainPresenter {
    fun attachView(view: IMainView)
    fun detachView(view: IMainView)

    fun actionPressed(operation: Int)
    fun numberPressed(number: String)
}