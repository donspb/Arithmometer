package ru.donspb.arithmometer20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import ru.donspb.arithmometer20.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity(), IMainView {

    private val KEY_FIRST_NUMBER = "FIRST_NUMBER"
    private val KEY_OPERATION = "OPERATION"
    private val KEY_OP_ENTER = "OPER_ENTER"
    private val KEY_RES_SHOWING = "RES_SHOWING"
    private val KEY_TOP_SCREEN = "TOP_SCREEN"
    private val KEY_BOT_SCREEN = "BOT_SCREEN"
    private val KEY_CURRENT_NUMBER = "CURRENT"
    private val settings = "settings.xml"
    private val darkSet = "Dark"

    private val actionSignEnter = false
    private val resOnScreen = false

    lateinit var presenter: IMainPresenter
    private var binding: ActivityMainBinding? = null

    fun createPresenter(): IMainPresenter {
        return MainPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        presenter.detachView(this)
        super.onStop()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        binding = ActivityMainBinding.inflate(layoutInflater)
        themeSetter()
        setContentView(binding?.root)
        controlsInit()
    }

    fun themeSetter() {
        val isDark: Boolean = getSharedPreferences(settings, MODE_PRIVATE)
            .getBoolean(darkSet, false)
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun controlsInit() {
        binding?.button0?.setOnClickListener{
            presenter.numberPressed(binding?.button0?.text.toString())
        }
        binding?.button1?.setOnClickListener{
            presenter.numberPressed(binding?.button1?.text.toString())
        }
        binding?.button2?.setOnClickListener{
            presenter.numberPressed(binding?.button2?.text.toString())
        }
        binding?.button3?.setOnClickListener{
            presenter.numberPressed(binding?.button3?.text.toString())
        }
        binding?.button4?.setOnClickListener{
            presenter.numberPressed(binding?.button4?.text.toString())
        }
        binding?.button5?.setOnClickListener{
            presenter.numberPressed(binding?.button5?.text.toString())
        }
        binding?.button6?.setOnClickListener{
            presenter.numberPressed(binding?.button6?.text.toString())
        }
        binding?.button7?.setOnClickListener{
            presenter.numberPressed(binding?.button7?.text.toString())
        }
        binding?.button8?.setOnClickListener{
            presenter.numberPressed(binding?.button8?.text.toString())
        }
        binding?.button9?.setOnClickListener{
            presenter.numberPressed(binding?.button9?.text.toString())
        }
        binding?.buttonDot?.setOnClickListener{
            presenter.numberPressed(binding?.buttonDot?.text.toString())
        }
        binding?.buttonCe?.setOnClickListener{presenter.actionPressed(Consts.ACTION_CLEAR) }
        binding?.buttonBack?.setOnClickListener{presenter.actionPressed(Consts.ACTION_BACK)}
        binding?.buttonDiv?.setOnClickListener{presenter.actionPressed(Consts.ACTION_DIV)}
        binding?.buttonMult?.setOnClickListener{presenter.actionPressed(Consts.ACTION_MULT)}
        binding?.buttonMinus?.setOnClickListener{presenter.actionPressed(Consts.ACTION_MINUS)}
        binding?.buttonAdd?.setOnClickListener{presenter.actionPressed(Consts.ACTION_PLUS)}
        binding?.buttonEqual?.setOnClickListener{presenter.actionPressed(Consts.ACTION_EQUALS)}
    }


    override fun showOnScreen(topScreenData: String?, botScreenData: String?) {
        val newTopText: String = binding?.topScreen?.text.toString() + topScreenData
        binding?.topScreen?.text = newTopText
        binding?.botScreen?.text = botScreenData
    }

    override fun clearScreen() {
        binding?.topScreen?.text = ""
        binding?.botScreen?.text = ""
    }
}

