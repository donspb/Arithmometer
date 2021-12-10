package ru.donspb.arithmometer20

import java.lang.ArithmeticException
import java.lang.RuntimeException
import java.lang.StringBuilder

class CalcCore {
    private val NUMBER_INIT_VALUE = "0"
    private val DELIMITER = "."
    private val MAX_NUMBER_LEN = 7

    private var mPreviousNumber: String? = null
    private var mCurrentNumber: StringBuilder? = null
    private var mOperation: Int? = null
    private var mIsFloat = false

    fun clearCore() {
        mPreviousNumber = ""
        mCurrentNumber = StringBuilder(NUMBER_INIT_VALUE)
        mOperation = null
        mIsFloat = false
    }

    fun setCore(previous: String?, current: String, operation: Int?) {
        mPreviousNumber = previous
        mCurrentNumber = StringBuilder(current)
        mOperation = operation
        if (current.contains(DELIMITER)) mIsFloat = false
    }

    private fun checkCurrentEmpty(): Boolean {
        return mCurrentNumber.toString() == NUMBER_INIT_VALUE
    }

    private fun getEquationResult(): String {
        val a = mPreviousNumber!!.toDouble()
        val b = mCurrentNumber.toString().toDouble()
        val res: Double
        res = when (mOperation) {
            Consts.ACTION_PLUS -> a + b
            Consts.ACTION_MINUS -> a - b
            Consts.ACTION_MULT -> a * b
            Consts.ACTION_DIV -> if (b != 0.0) a / b else throw ArithmeticException("ERR: DIV by ZERO")
            else -> throw RuntimeException("Unknownn action")
        }
        if (java.lang.Double.isInfinite(res)) throw ArithmeticException("ERR: TOO BIG NUMBER")
        return java.lang.Double.toString(res)
    }

    fun getPreviousNumber(): String? {
        return mPreviousNumber
    }

    fun getCurrentNumber(): String? {
        return mCurrentNumber.toString()
    }

    fun getOperation(): Int? {
        return mOperation
    }

    @Throws(ArithmeticException::class)
    fun getResult(): String? {
        var resultForReturn: String?
        var badResult = false
        try {
            resultForReturn = getEquationResult()
        } catch (e: ArithmeticException) {
            badResult = true
            resultForReturn = e.message
        }
        mPreviousNumber = ""
        mOperation = null
        if (!badResult) mCurrentNumber!!.replace(0, mCurrentNumber!!.length, resultForReturn) else {
            clearCurrentNumber()
            throw ArithmeticException(resultForReturn)
        }
        return resultForReturn
    }


    fun setOperation(operation: Int?) {
        mOperation = operation
    }

    @Throws(ArithmeticException::class)
    fun addOperation(operation: Int?): String {
        val msgEquation: String
        mIsFloat = false
        if (mPreviousNumber!!.isEmpty()) {
            mPreviousNumber = mCurrentNumber.toString()
            clearCurrentNumber()
            mOperation = operation
            msgEquation = mCurrentNumber.toString()
        } else {
            try {
                msgEquation = getEquationResult()
                mOperation = operation
                clearCurrentNumber()
                mPreviousNumber = msgEquation
            } catch (e: ArithmeticException) {
                mOperation = null
                mPreviousNumber = ""
                clearCurrentNumber()
                throw ArithmeticException(e.message)
            }
        }
        return msgEquation
    }

    fun addDigit(digit: String?, clear: Boolean) {
        if (clear) {
            clearCurrentNumber()
            mIsFloat = false
        }
        if (checkCurrentEmpty()) mCurrentNumber!!.replace(
            0,
            mCurrentNumber!!.length,
            digit
        ) else if (mCurrentNumber!!.length < MAX_NUMBER_LEN) mCurrentNumber!!.append(digit)
    }

    fun deleteDigit() {
        if (mCurrentNumber!!.length > 1) mCurrentNumber!!.delete(
            mCurrentNumber!!.length - 1,
            mCurrentNumber!!.length
        ) else clearCurrentNumber()
    }

    fun addDot() {
        if (!mIsFloat) {
            mCurrentNumber!!.append(DELIMITER)
            mIsFloat = true
        }
    }

    fun clearCurrentNumber() {
        mCurrentNumber = mCurrentNumber!!.replace(0, mCurrentNumber!!.length, NUMBER_INIT_VALUE)
    }
}