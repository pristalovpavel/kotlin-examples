package org.kotlin.mpp.mobile

expect fun platformName(): String

fun createApplicationScreenMessage(): String {
  return "Kotlin Rocks on ${platformName()}"
}

class GameEngine {
  var table: MutableList<MutableList<Int>> = MutableList(3) { MutableList(3) { 0 } }

  // начинаем новую игру
  fun startGame() {
    // чистим игровое поле
    clearField()
    clearTable()
  }

  fun clearTable() {
    table = MutableList(3) { MutableList(3) { 0 } }
  }

  fun gameProcess() {
  }
}

// метод очищает игровое поле
expect fun GameEngine.clearField()

