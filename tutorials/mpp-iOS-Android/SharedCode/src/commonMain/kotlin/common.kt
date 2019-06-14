package org.kotlin.mpp.mobile

import kotlin.random.Random

expect fun platformName(): String

fun createApplicationScreenMessage(): String {
  return "Kotlin Rocks on ${platformName()}"
}

interface GameEngineCallbacks {
  // метод очищает игровое поле
  fun clearUIField()

  // метод говорит UI отрисовать нолик в поле i, j
  fun showZero(i: Int, j: Int)

  // метод отображает сообщение о выигрыше
  fun showWinner(message: String)
}

class GameEngine (private var callbacks: GameEngineCallbacks) {

  private var table: MutableList<MutableList<Int>> = MutableList(3) { MutableList(3) { 0 } }
  private val computerValue = 2
  private val playerValue = 1

  // метод начинает новую игру
  fun startNewGame() {
    startGame()
  }

  // метод обработки нажатия на игровое поле живым игроком
  fun fieldPressed(i: Int, j: Int) {
    table[i][j] = playerValue

    if(!showWinner()) {
      ai(nonZeroValues())
      showWinner()
    }
  }

  // начинаем новую игру
  private fun startGame() {
    // чистим игровое поле
    callbacks.clearUIField()
    clearTable()
  }

  // метод печатает победителя
  private fun showWinner() : Boolean {
    val winner = check()
    when (winner) {
      computerValue -> {
        callbacks.showWinner("AI won!")
        return true
      }
      playerValue -> {
        callbacks.showWinner("You won!")
        return true
      }
    }
    return false
  }

  // подсчитывает количество заполненных клеток
  private fun nonZeroValues(): Int {
    var count = 0

    for(i in 0 until 3) {
      for(j in 0 until 3) {
        if (table[i][j] != 0) count++
      }
    }

    return count
  }

  // очищает внутреннюю таблицу
  private fun clearTable() {
    table = MutableList(3) { MutableList(3) { 0 } }
  }

  // проверяет выигрыш одной из сторон
  private fun check() : Int {
    for (i in 0..2)
      if (table[i][0] != 0 && table[i][0] == table[i][1] && table[i][1] == table[i][2]
        || table[0][i] != 0 && table[0][i] == table[1][i] && table[1][i] == table[2][i])
        return table[i][0]

    return if (table[0][0] != 0 && table[0][0] == table[1][1] && table[1][1] == table[2][2]) table[0][0]
    else if (table[0][2] != 0 && table[0][2] == table[1][1] && table[1][1] == table[2][0]) table[0][2]
    else 0
  }

  private fun defend(c: Int, p: Int, b: Int): Int {
    if (b < 3)
      return 0
    else if (table[0][0] + table[1][1] + table[2][2] == 2 * c && table[0][0] != p && table[1][1] != p && table[2][2] != p) {
      for (i in 0..2) {
        if (table[i][i] == 0) {
          table[i][i] = computerValue
          callbacks.showZero(i, i)
          return 1
        }
      }
    } else if (table[0][2] + table[1][1] + table[2][0] == 2 * c && table[0][2] != p && table[1][1] != p && table[2][0] != p) {
      for (i in 0..2) {
        if (table[i][2 - i] == 0) {
          table[i][2 - i] = computerValue
          callbacks.showZero(i, 2 - i)
          return 1
        }
      }
    } else {
      var i = 0
      var j: Int
      while (i < 3) {
        if (table[i][0] + table[i][1] + table[i][2] == 2 * c && table[i][0] != p && table[i][1] != p && table[i][2] != p) {
          j = 0
          while (j < 3) {
            if (table[i][j] == 0) {
              table[i][j] = computerValue
              callbacks.showZero(i, j)
              return 1
            }
            j++
          }
        } else if (table[0][i] + table[1][i] + table[2][i] == 2 * c && table[0][i] != p && table[1][i] != p && table[2][i] != p) {
          j = 0
          while (j < 3) {
            if (table[j][i] == 0) {
              table[j][i] = computerValue
              callbacks.showZero(j, i)
              return 1
            }
            j++
          }
        }
        i++
      }
    }
    return 0
  }

  // сделать ход, чтобы в будущем выиграть
  private fun attack(): Int {
    var i: Int
    var j: Int
    if (table[0][0] + table[0][2] + table[2][0] + table[2][2] == playerValue || table[0][0] + table[0][2] + table[2][0] + table[2][2] == 2 * playerValue) {
      i = 0
      while (i < 3) {
        if (table[i][0] + table[i][1] + table[i][2] == computerValue && (table[i][0] == computerValue || table[i][1] == computerValue || table[i][2] == computerValue)) {
          if (i == 1) {
            j = 0
            while (j < 3) {
              if (table[i][j] == 0) {
                table[i][j] = computerValue
                callbacks.showZero(i, j)
                return 1
              }
              j++
            }
          } else {
            j = 2
            while (j >= 0) {
              if (table[i][j] == 0) {
                table[i][j] = computerValue
                callbacks.showZero(i, j)
                return 1
              }
              j--
            }
          }
        }
        if (table[0][i] + table[1][i] + table[2][i] == computerValue && (table[0][i] == computerValue || table[1][i] == computerValue || table[2][i] == computerValue)) {
          if (i == 1) {
            j = 0
            while (j < 3) {
              if (table[j][i] == 0) {
                table[j][i] = computerValue
                callbacks.showZero(j, i)
                return 1
              }
              j++
            }
          } else {
            j = 2
            while (j >= 0) {
              if (table[j][i] == 0) {
                table[j][i] = computerValue
                callbacks.showZero(j, i)
                return 1
              }
              j--
            }
          }

        }
        i++
      }

      if (table[0][0] + table[1][1] + table[2][2] == computerValue && (table[0][0] == computerValue || table[1][1] == computerValue || table[2][2] == computerValue)) {
        i = 2
        while (i >= 0) {
          if (table[i][i] == 0 && (table[i][0] + table[i][1] + table[i][2] == computerValue && (table[i][0] == computerValue || table[i][1] == computerValue || table[i][2] == computerValue) || table[0][i] + table[1][i] + table[2][i] == computerValue && (table[0][i] == computerValue || table[1][i] == computerValue || table[2][i] == computerValue))) {
            table[i][i] = computerValue
            callbacks.showZero(i, i)
            return 1
          }
          i--
        }
        i = 2
        while (i >= 0) {
          if (table[i][i] == 0) {
            if (table[i][0] + table[i][1] + table[i][2] == playerValue && (table[i][0] == playerValue || table[i][1] == playerValue || table[i][2] == playerValue) && table[0][i] + table[1][i] + table[2][i] == playerValue && (table[0][i] == playerValue || table[1][i] == playerValue || table[2][i] == playerValue)) {
              table[i][i] = computerValue
              callbacks.showZero(i, i)
              return 1
            }
          }
          i--
        }
        i = 2
        while (i >= 0) {
          if (table[i][i] == 0) {
            table[i][i] = computerValue
            callbacks.showZero(i, i)
            return 1
          }
          i--
        }
      } else if (table[0][2] + table[1][1] + table[2][0] == computerValue && (table[0][2] == computerValue || table[1][1] == computerValue || table[2][0] == computerValue)) {
        i = 2
        while (i >= 0) {
          if (table[i][2 - i] == 0 && (table[i][0] + table[i][1] + table[i][2] == computerValue && (table[i][0] == computerValue || table[i][1] == computerValue || table[i][2] == computerValue) || table[0][2 - i] + table[1][2 - i] + table[2][2 - i] == computerValue && (table[0][2 - i] == computerValue || table[1][2 - i] == computerValue || table[2][2 - i] == computerValue))) {
            table[i][2 - i] = computerValue
            callbacks.showZero(i, 2 - i)
            return 1
          }
          i--
        }
        i = 2
        while (i >= 0) {
          if (table[i][2 - i] == 0) {
            if (table[i][0] + table[i][1] + table[i][2] == playerValue && (table[i][0] == playerValue || table[i][1] == playerValue || table[i][2] == playerValue) && table[0][2 - i] + table[1][2 - i] + table[2][2 - i] == playerValue && (table[0][2 - i] == playerValue || table[1][2 - i] == playerValue || table[2][2 - i] == playerValue)) {
              table[i][2 - i] = computerValue
              callbacks.showZero(i, 2 - i)
              return 1
            }
          }
          i--
        }
        i = 2
        while (i >= 0) {
          if (table[i][2 - i] == 0) {
            table[i][2 - i] = computerValue
            callbacks.showZero(i, 2 - i)
            return 1
          }
          i--
        }
      }
    } else {
      if (table[0][0] + table[1][1] + table[2][2] == computerValue && (table[0][0] == computerValue || table[1][1] == computerValue || table[2][2] == computerValue)) {
        i = 2
        while (i >= 0) {
          if (table[i][i] == 0 && (table[i][0] + table[i][1] + table[i][2] == computerValue && (table[i][0] == computerValue || table[i][1] == computerValue || table[i][2] == computerValue) || table[0][i] + table[1][i] + table[2][i] == computerValue && (table[0][i] == computerValue || table[1][i] == computerValue || table[2][i] == computerValue))) {
            table[i][i] = computerValue
            callbacks.showZero(i, i)
            return 1
          }
          i--
        }
        i = 2
        while (i >= 0) {
          if (table[i][i] == 0) {
            if (table[i][0] + table[i][1] + table[i][2] == playerValue && (table[i][0] == playerValue || table[i][1] == playerValue || table[i][2] == playerValue) && table[0][i] + table[1][i] + table[2][i] == playerValue && (table[0][i] == playerValue || table[1][i] == playerValue || table[2][i] == playerValue)) {
              table[i][i] = computerValue
              callbacks.showZero(i, i)
              return 1
            }
          }
          i--
        }
        i = 2
        while (i >= 0) {
          if (table[i][i] == 0) {
            table[i][i] = computerValue
            callbacks.showZero(i, i)
            return 1
          }
          i--
        }
      } else if (table[0][2] + table[1][1] + table[2][0] == computerValue && (table[0][2] == computerValue || table[1][1] == computerValue || table[2][0] == computerValue)) {
        i = 2
        while (i >= 0) {
          if (table[i][2 - i] == 0 && (table[i][0] + table[i][1] + table[i][2] == computerValue && (table[i][0] == computerValue || table[i][1] == computerValue || table[i][2] == computerValue) || table[0][2 - i] + table[1][2 - i] + table[2][2 - i] == computerValue && (table[0][2 - i] == computerValue || table[1][2 - i] == computerValue || table[2][2 - i] == computerValue))) {
            table[i][2 - i] = computerValue
            callbacks.showZero(i, 2 - i)
            return 1
          }
          i--
        }
        i = 2
        while (i >= 0) {
          if (table[i][2 - i] == 0) {
            if (table[i][0] + table[i][1] + table[i][2] == playerValue && (table[i][0] == playerValue || table[i][1] == playerValue || table[i][2] == playerValue) && table[0][2 - i] + table[1][2 - i] + table[2][2 - i] == playerValue && (table[0][2 - i] == playerValue || table[1][2 - i] == playerValue || table[2][2 - i] == playerValue)) {
              table[i][2 - i] = computerValue
              callbacks.showZero(i, 2 - i)
              return 1
            }
          }
          i--
        }

        i = 2
        while (i >= 0) {
          if (table[i][2 - i] == 0) {
            table[i][2 - i] = computerValue
            callbacks.showZero(i, 2 - i)
            return 1
          }
          i--
        }
      } else {
        i = 0
        while (i < 3) {
          if (table[i][0] + table[i][1] + table[i][2] == computerValue && (table[i][0] == computerValue || table[i][1] == computerValue || table[i][2] == computerValue)) {
            if (i == 1) {
              for (j in 0..2) {
                if (table[i][j] == 0) {
                  table[i][j] = computerValue
                  callbacks.showZero(i, j)
                  return 1
                }
              }
            } else {
              j = 2
              while (j >= 0) {
                if (table[i][j] == 0) {
                  table[i][j] = computerValue
                  callbacks.showZero(i, j)
                  return 1
                }
                j--
              }
            }
          } else if (table[0][i] + table[1][i] + table[2][i] == computerValue && (table[0][i] == computerValue || table[1][i] == computerValue || table[2][i] == computerValue)) {
            if (i == 1) {
              j = 0
              while (j < 3) {
                if (table[j][i] == 0) {
                  table[j][i] = computerValue
                  callbacks.showZero(j, i)
                  return 1
                }
                j++
              }
            } else {
              j = 2
              while (j >= 0) {
                if (table[j][i] == 0) {
                  table[j][i] = computerValue
                  callbacks.showZero(j, i)
                  return 1
                }
                j--
              }
            }
          }
          i++
        }
      }
    }
    return 0
  }

  // управление ходом компьютера
  private fun ai(b: Int) {
    if(defend(computerValue, playerValue, b) == 0)
    {
      if(defend(playerValue, computerValue, b) == 0)
      {
        if((table[0][0] + table[0][2] + table[2][0] + table[2][2] == playerValue + computerValue
                  || table[0][0] + table[0][2] + table[2][0] + table[2][2] == playerValue + 2 * computerValue)
          && table[1][1] == 0)
        {
          for(i in 0 until 3 step 2)
          {
            for(j in 0 until 3 step 2)
            if(table[i][j]==0)
            {
              table[i][j] = computerValue
              callbacks.showZero(i,j)
              return
            }
          }

        }
        else if(b == 2 && table[1][1] == 0)
        {
          table[1][1] = computerValue
          callbacks.showZero(1,1)
        }
        if(attack() == 0)
        {
          if(b == 0)
          {
            table[Random(7654321).nextInt(0,1) * 2][Random(1234567).nextInt(0,1) * 2] = computerValue
            for(i in 0 until 3 step 2)
            {
              for(j in 0 until 3 step 2)
              if(table[i][j] == computerValue)
                callbacks.showZero(i, j)
            }
          }
          else if(table[1][1] == 0)
          {
            table[1][1]=computerValue
            callbacks.showZero(1,1)
          }
          else
          {
            for(i in 0 until 3)
            {
              for(j in 0 until 3)
              {
                if(table[i][j] == 0)
                {
                  table[i][j]=computerValue
                  callbacks.showZero(i, j)
                  return
                }
              }
            }
          }
        }
      }
    }
  }
}
