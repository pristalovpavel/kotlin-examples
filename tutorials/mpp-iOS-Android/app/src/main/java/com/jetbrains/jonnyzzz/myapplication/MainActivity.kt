package com.jetbrains.jonnyzzz.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import org.kotlin.mpp.mobile.GameEngine
import org.kotlin.mpp.mobile.GameEngineCallbacks
import org.kotlin.mpp.mobile.createApplicationScreenMessage

class MainActivity : AppCompatActivity(), GameEngineCallbacks {

  override fun clearUIField() {
    listOf(cell_1_1, cell_1_2, cell_1_3, cell_2_1, cell_2_2, cell_2_3, cell_3_1, cell_3_2, cell_3_3).forEach{
      it.text = ""
    }
    winner.text = ""
  }

  override fun showZero(i: Int, j: Int) {
    val list = listOf(listOf(cell_1_1, cell_1_2, cell_1_3),
            listOf(cell_2_1, cell_2_2, cell_2_3),
            listOf(cell_3_1, cell_3_2, cell_3_3))
    if(i in 0..2 && j in 0..2)
      list[i][j].text = "o"
  }

  override fun showWinner(message: String) {
    winner.text = message
  }

  private val engine = GameEngine(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    main_text.text = createApplicationScreenMessage()

    new_game.setOnClickListener { engine.startNewGame() }

    listOf(cell_1_1, cell_1_2, cell_1_3, cell_2_1, cell_2_2, cell_2_3, cell_3_1, cell_3_2, cell_3_3).forEach{
      it.setOnClickListener{ v->
        onButtonPressed(v)
      }
    }
  }

  private fun onButtonPressed(v: View) {
    if(v is Button)
      v.text = "x"

    when(v.id) {
      R.id.cell_1_1 -> engine.fieldPressed(0,0)
      R.id.cell_1_2 -> engine.fieldPressed(0,1)
      R.id.cell_1_3 -> engine.fieldPressed(0,2)
      R.id.cell_2_1 -> engine.fieldPressed(1,0)
      R.id.cell_2_2 -> engine.fieldPressed(1,1)
      R.id.cell_2_3 -> engine.fieldPressed(1,2)
      R.id.cell_3_1 -> engine.fieldPressed(2,0)
      R.id.cell_3_2 -> engine.fieldPressed(2,1)
      R.id.cell_3_3 -> engine.fieldPressed(2,2)
      else -> {}
    }
  }
}
