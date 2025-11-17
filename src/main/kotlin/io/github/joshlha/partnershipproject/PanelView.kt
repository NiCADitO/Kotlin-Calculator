package io.github.joshlha.partnershipproject

import net.miginfocom.swing.MigLayout
import org.slf4j.LoggerFactory
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

/*
    "Main" is a subclass of JPanel. It gives JPanel's constructor a new MigLayout instance.
    This tells the JPanel to use MigLayout for laying out the components.
    There are many other layouts available in swing, but Mig is most used.
 */


// TODO only allow single decimal at a time --- DONE
// TODO create a del and a reset button --- DONE
// TODO make a variable for the label value --- DONE
class Main : JPanel(MigLayout("ins 4, fill", "[fill, sg g]5", "")) {

    private var nextOperator: String? = null
    private var operatorFlag: Boolean = false

    private var firstOperand = "0.0"
    private var secondOperand = "0.0"   // 1 + 2
        set(value) {
            field = value
            valueLabel.text = value
        }

    private fun digitButtonStyle(text: String) = JButton(text).apply {
            horizontalAlignment = JButton.CENTER
            border = BorderFactory.createLineBorder(Color.GRAY)
            font = font.deriveFont(24f)

        addActionListener {

            if (operatorFlag) {
                // TODO set current value to previous value, and clear current value
                firstOperand = secondOperand
                operatorFlag = false
                secondOperand = text
            } else {
                if (secondOperand == "0.0") {
                    secondOperand = text
                }
                else if(!secondOperand.contains(".") || text != ".") {
                    secondOperand = secondOperand + text
                }
            }



        }
    }

    private fun operatorButtonStyle(text: String) = JButton(text).apply {
        background = Color.ORANGE
        foreground = Color.BLACK
        font = font.deriveFont(24f)

        addActionListener {
            // run calculation
            if (nextOperator == null) {
                nextOperator = text
                operatorFlag = true
            //
            } else if(!operatorFlag) {
                if (text != "=") {
                    nextOperator = text
                }
                runCalculation()
                operatorFlag = true
            } else {
                nextOperator = text
            }

//            when (text) {
//                "C" -> nextOperator = null
//                "del" -> nextOperator = null
//                "=" -> {
//
//                    // TODO calculate new value
//
//                    nextOperator = null
//                }
//                else -> nextOperator = text
//
//            }
            // if operator is null then do nothing if pressing =
            // otherwise if there is an operator queued
        }

    }

    fun runCalculation() {
        var calSum: Double = 0.0

        when (nextOperator) {
            "+" -> calSum = firstOperand.toDouble() + secondOperand.toDouble()
            "-" -> calSum = firstOperand.toDouble() - secondOperand.toDouble()
            "*" -> calSum = firstOperand.toDouble() * secondOperand.toDouble()
            "/" -> calSum = firstOperand.toDouble() / secondOperand.toDouble()  //TODO check for / by zero

        }
        secondOperand = calSum.toString()
        valueLabel.text = calSum.toString()
        firstOperand = "0.0"
    }


    private val valueLabel = JLabel("0.0").apply {
        border = BorderFactory.createLineBorder(Color.WHITE)
        horizontalAlignment = JButton.LEFT
        background = Color.WHITE
        font = font.deriveFont(24f)
        horizontalAlignment = JLabel.RIGHT

    }

    init {
        // Add the components to the layout, providing MigLayout constraints for their position
        // There is and implied "this." added before the "add" function call
        add(valueLabel, "span 5, grow, wrap")


        add(digitButtonStyle("7"), "grow")
        add(digitButtonStyle("8"), "grow")
        add(digitButtonStyle("9"), "grow")

        add(operatorButtonStyle("del").apply {
            addActionListener {
                secondOperand = secondOperand.dropLast(1)
                if (secondOperand == "") {
                    secondOperand = "0.0"
                }
            }
        }, "span 2, grow, wrap")

        add(digitButtonStyle("4"), "grow")
        add(digitButtonStyle("5"), "grow")
        add(digitButtonStyle("6"), "grow")
        add(operatorButtonStyle("*"), "grow")
        add(operatorButtonStyle("/"), "grow, wrap")

        add(digitButtonStyle("1"), "grow") //w 10:200:300
        add(digitButtonStyle("2"), "grow")
        add(digitButtonStyle("3"), "grow")
        add(operatorButtonStyle("-"), "grow")
        add(operatorButtonStyle("="), "span 1 2, grow, wrap")

        add(operatorButtonStyle("C").apply {
            addActionListener {
                secondOperand = "0.0"
            }
        }, "grow")

        add(digitButtonStyle("0"), "grow")
        add(digitButtonStyle("."), "grow")
        add(operatorButtonStyle("+"), "grow")
//        add(operatorButtonStyle("del"), "grow, wrap")
    }

    // A singleton instance object called "Main" because it is the companion to the Main class.
    companion object {
        public val logger = LoggerFactory.getLogger(Main::class.java)


    }
}


