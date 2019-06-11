package com.jetbrains.jonnyzzz.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.cell_1_1
import kotlinx.android.synthetic.main.activity_main.cell_1_2
import kotlinx.android.synthetic.main.activity_main.cell_1_3
import kotlinx.android.synthetic.main.activity_main.cell_2_1
import kotlinx.android.synthetic.main.activity_main.cell_2_2
import kotlinx.android.synthetic.main.activity_main.cell_2_3
import kotlinx.android.synthetic.main.activity_main.cell_3_1
import kotlinx.android.synthetic.main.activity_main.cell_3_2
import kotlinx.android.synthetic.main.activity_main.cell_3_3
import kotlinx.android.synthetic.main.activity_main.main_text
import kotlinx.android.synthetic.main.activity_main.new_game
import org.kotlin.mpp.mobile.GameEngine
import org.kotlin.mpp.mobile.createApplicationScreenMessage

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val engine = GameEngine()

    main_text.text = createApplicationScreenMessage()

    new_game.setOnClickListener { engine.startNewGame() }

    listOf(cell_1_1, cell_1_2, cell_1_3, cell_2_1, cell_2_2, cell_2_3, cell_3_1, cell_3_2, cell_3_3).forEach{
      it.setOnClickListener{ v->
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
  }
}
