package ru.donspb.arithmometer20

import java.lang.ArithmeticException
import java.lang.RuntimeException

class MainPresenter : IMainPresenter {

    private val calCore: CalcCore = CalcCore()

    private var actionSignEnter: Boolean = false
    private var resultShowing = false
    private var topString: String? = null
    private var botString: String? = null
    private var currentView: IMainView? = null

    init {
        initCore()
    }

    fun initCore() {
        calCore.clearCore()
        setScreenStrings("", calCore.getCurrentNumber())
        resultShowing = false
        actionSignEnter = false
    }

    override fun attachView(view: IMainView) {
        if (view != currentView) currentView = view
        setScreenStrings(topString, botString)
    }



    override fun detachView(view: IMainView) {
        if (view == currentView) currentView = null
    }

    override fun actionPressed(operation: Int) {
        when (operation) {
            Consts.ACTION_CLEAR -> {
                calCore.clearCore()
                currentView?.clearScreen()
                setScreenStrings("", calCore.getCurrentNumber())
                resultShowing = false
                actionSignEnter = false
            }
            Consts.ACTION_BACK -> {
                if (!actionSignEnter) calCore.deleteDigit()
                setScreenStrings("", calCore.getCurrentNumber())
            }
            Consts.ACTION_PLUS, Consts.ACTION_MINUS, Consts.ACTION_DIV, Consts.ACTION_MULT ->
                mathOperationProcessor(operation)
            Consts.ACTION_EQUALS -> if (!resultShowing) calculationsEnd()
            else -> throw RuntimeException("Unknown button")
        }
    }

    override fun numberPressed(number: String) {
        actionSignEnter = false
        if (number.equals(".") && !resultShowing) calCore.addDot()
        else {
            if (resultShowing) currentView?.clearScreen()
            calCore.addDigit(number, resultShowing)
            resultShowing = false
        }
        currentView?.showOnScreen("", calCore.getCurrentNumber())
    }

    private fun mathOperationProcessor(operation: Int) {
        var currentTopString: String?
        var currentBotString: String?

        val operationSymbol: String =
            when (operation) {
                Consts.ACTION_DIV -> "÷"
                Consts.ACTION_MULT -> "x"
                Consts.ACTION_MINUS -> "-"
                Consts.ACTION_PLUS -> "+"
                else -> " "
            }

        if (!actionSignEnter) {
            if (calCore.getPreviousNumber().isNullOrEmpty()) currentView?.clearScreen()
            currentTopString = calCore.getCurrentNumber() + " " + operationSymbol + " "
            actionSignEnter = true
            try {
                calCore.addOperation(operation)
            } catch (e: ArithmeticException) {
                e.message
            }.also { currentBotString = it }
            setScreenStrings(currentTopString, currentBotString)
        } else {
            val topSubstring = topString?.length?.minus(2)?.let { topString?.substring(0, it) }
            calCore.setOperation(operation)
            currentView?.clearScreen()
            setScreenStrings("$topSubstring $operationSymbol ", calCore.getCurrentNumber())
        }
        resultShowing = false
    }

    private fun calculationsEnd() {
        if (calCore.getPreviousNumber().isNullOrEmpty())
            setScreenStrings("", calCore.getCurrentNumber())
        else {
            topString = calCore.getCurrentNumber()
            botString = try {
                calCore.getResult()
            } catch (e: ArithmeticException) {
                e.message
            }
            setScreenStrings(topString, botString)
        }
        resultShowing = true
        actionSignEnter = false
    }

    private fun setScreenStrings(top: String?, bot: String?) {
        topString = top
        botString = bot
        currentView?.showOnScreen(topString, botString)
    }

}