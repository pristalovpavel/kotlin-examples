package org.kotlin.mpp.mobile

import platform.UIKit.UIDevice

actual fun platformName(): String {

  return UIDevice.currentDevice.systemName() +
          " " +
          UIDevice.currentDevice.systemVersion
}

actual fun GameEngine.clearUIField() {

}

actual fun GameEngine.showZero(i: Int, int: Int) {

}

actual fun GameEngine.showWinner(message: String) {

}