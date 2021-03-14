package ru.gb.androidone.donspb.arithmometer;

public class Calcore {

    private static final String NUMBER_INIT_VALUE = "0";
    private static final String DELIMITER = ".";
    private static final int MAX_NUMBER_LEN = 7;

    private String mPreviousNumber = "";
    private StringBuilder mCurrentNumber = new StringBuilder(NUMBER_INIT_VALUE);
    private String mOperation = "";
    private boolean mIsFloat = false;

    private boolean checkCurrentEmpty() {
        return mCurrentNumber.toString().equals(NUMBER_INIT_VALUE);
    }

    private String getEquationResult() {
        double a = Double.parseDouble(mPreviousNumber);
        double b = Double.parseDouble(mCurrentNumber.toString());
        double res;
        switch (mOperation) {
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "x":
                res = a * b;
                break;
            case "÷":
                if (b != 0) res = a / b;
                else throw new ArithmeticException("ERR: DIV by ZERO");
                break;
            default:
                throw new RuntimeException("Unknownn action");
        }
        if (Double.isInfinite(res)) throw new ArithmeticException("ERR: TOO BIG NUMBER");
        return Double.toString(res);
    }

    public String getPreviousNumber() {
        return mPreviousNumber;
    }

    public String getCurrentNumber() {
        return mCurrentNumber.toString();
    }

    public String getOperation() {
        return mOperation;
    }

    public String getResult() throws ArithmeticException {
        String resultForReturn;
        boolean badResult = false;

        try {
            resultForReturn = getEquationResult();
        } catch (ArithmeticException e) {
                badResult = true;
                resultForReturn = e.getMessage();
        }

        mPreviousNumber = "";
        mOperation = "";
        if (!badResult) mCurrentNumber.replace(0, mCurrentNumber.length(), resultForReturn);
        else {
            clearCurrentNumber();
            throw new ArithmeticException(resultForReturn);
        }
        return resultForReturn;
    }


    public void setOperation(String operation) {
        this.mOperation = operation;
    }

    public String addOperation(String operation) throws ArithmeticException {
        String msgEquation;
        mIsFloat = false;

        if (mPreviousNumber.isEmpty()) {
            mPreviousNumber = mCurrentNumber.toString();
            clearCurrentNumber();
            mOperation = operation;
            msgEquation = mCurrentNumber.toString();
        } else {
            try {
                msgEquation = getEquationResult();
                mOperation = operation;
                clearCurrentNumber();
                mPreviousNumber = msgEquation;
            } catch (ArithmeticException e) {
                mOperation = "";
                mPreviousNumber = "";
                clearCurrentNumber();
                throw new ArithmeticException(e.getMessage());
            }
        }
        return msgEquation;
    }

    public void addDigit(String digit, boolean clear) {
        if (clear) {
            clearCurrentNumber();
            mIsFloat = false;
        }

        if (checkCurrentEmpty()) mCurrentNumber.replace(0,mCurrentNumber.length(), digit);
        else if (mCurrentNumber.length() < MAX_NUMBER_LEN) mCurrentNumber.append(digit);
    }

    public void deleteDigit() {
        if (mCurrentNumber.length() > 1)
            mCurrentNumber.delete(mCurrentNumber.length() - 1, mCurrentNumber.length());
        else clearCurrentNumber();
    }

    public void addDot() {
        if (!mIsFloat) {
            mCurrentNumber.append(DELIMITER);
            mIsFloat = true;
        }
    }

    public void clearCurrentNumber() {
        mCurrentNumber = mCurrentNumber.replace(0, mCurrentNumber.length(), NUMBER_INIT_VALUE);
    }

}
