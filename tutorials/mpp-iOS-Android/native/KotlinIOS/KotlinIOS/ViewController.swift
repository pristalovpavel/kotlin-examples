import UIKit
import main

@objc class ViewController: UIViewController, GameEngineCallbacks {
    
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBOutlet weak var b11: UIButton!
    @IBOutlet weak var b12: UIButton!
    @IBOutlet weak var b13: UIButton!
    @IBOutlet weak var b21: UIButton!
    @IBOutlet weak var b22: UIButton!
    @IBOutlet weak var b23: UIButton!
    @IBOutlet weak var b31: UIButton!
    @IBOutlet weak var b32: UIButton!
    @IBOutlet weak var b33: UIButton!
    
    @IBOutlet weak var winnerLabel: UILabel!
    
    @IBOutlet weak var newGame: UIButton!
    
    func clearUIField() {
        // buttons
        let arr = [b11, b12, b13, b21, b22, b23, b31, b32, b33]
        for elem in arr {
            elem?.setTitle("_", for: .normal)
        }
        winnerLabel.text = ""
    }
    
    func showZero(i: Int32, j: Int32) {
        // buttons
        let arr = [[b11, b12, b13], [b21, b22, b23], [b31, b32, b33]]
        arr[Int(i)][Int(j)]?.setTitle("o", for: .normal)
    }
    
    func showWinner(message: String) {
        winnerLabel.text = message
    }
    private var engine: GameEngine?

    // Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        engine = GameEngine(callbacks: self)

        titleLabel.text = CommonKt.createApplicationScreenMessage()
        engine?.startNewGame()
    }

    // Actions
    @IBAction func onNewGameClick(_ sender: Any) {
        engine?.startNewGame()
    }

    @IBAction func onB11Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 0, j: 0)
    }

    @IBAction func onB12Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 0, j: 1)
    }

    @IBAction func onB13Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 0, j: 2)
    }

    @IBAction func onB21Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 1, j: 0)
    }

    @IBAction func onB22Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 1, j: 1)
    }

    @IBAction func onB23Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 1, j: 2)
    }

    @IBAction func onB31Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 2, j: 0)
    }

    @IBAction func onB32Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 2, j: 1)
    }

    @IBAction func onB33Click(_ sender: UIButton) {
        sender.setTitle("x", for: .normal)
        engine?.fieldPressed(i: 2, j: 2)
    }
}

