import io.github.joshlha.partnershipproject.Main
import org.junit.jupiter.api.BeforeEach
import java.awt.Component
import javax.swing.JButton
import javax.swing.JLabel
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest {

    private lateinit var calculatorPanel: Main

    @BeforeEach
    fun setup() {
        calculatorPanel = Main()
    }

    @Test
    fun `test simple addition`() {
        // 1 + 2 = 3
        clickButton("1")
        clickButton("+")
        clickButton("2")
        clickButton("=")

        assertEquals("3.0", getDisplayValue())
    }

    @Test
    fun `test simple subtraction`() {
        // 5 - 3 = 2
        clickButton("5")
        clickButton("-")
        clickButton("3")
        clickButton("=")

        assertEquals("2.0", getDisplayValue())
    }

    @Test
    fun `test decimal logic`() {
        // 1 . 5 + 0 . 5 = 2
        clickButton("1")
        clickButton(".")
        clickButton("5")
        clickButton("+")
        clickButton("0")
        clickButton(".")
        clickButton("5")
        clickButton("=")

        assertEquals("2.0", getDisplayValue())
    }

    @Test
    fun `test clear button resets value`() {
        clickButton("5")
        clickButton("C")

        // Should reset to 0.0
        assertEquals("0.0", getDisplayValue())
    }

    @Test
    fun `test delete button removes last digit`() {
        // Type 123
        clickButton("1")
        clickButton("2")
        clickButton("3")

        // Delete twice
        clickButton("del")
        clickButton("del")

        // Should remain "1"
        assertEquals("1", getDisplayValue())
    }

    @Test
    fun `test delete button on single digit returns to zero`() {
        clickButton("7")
        clickButton("del")

        assertEquals("0.0", getDisplayValue())
    }

    @Test
    fun `test chained operations`() {
        // 2 * 3 = 6, then + 4 = 10
        clickButton("2")
        clickButton("*")
        clickButton("3")
        clickButton("=")

        // Result is now 6.0
        assertEquals("6.0", getDisplayValue())

        clickButton("+")
        clickButton("4")
        clickButton("=")

        assertEquals("10.0", getDisplayValue())
    }

    @Test
    fun `test division by zero`() {
        clickButton("5")
        clickButton("/")
        clickButton("0")
        clickButton("=")

        assertEquals("Infinity", getDisplayValue())
    }


    /**
     * Finds a button by its text inside the panel and simulates a click.
     */
    private fun clickButton(text: String) {
        val button = findComponent<JButton> { it.text == text }
        if (button != null) {
            button.doClick()
        } else {
            throw IllegalArgumentException("Button with text '$text' not found")
        }
    }

    /**
     * Finds the main display label and returns its text.
     */
    private fun getDisplayValue(): String {
        val label = findComponent<JLabel> { true }
        return label?.text ?: "Error: Label not found"
    }

    /**
     * A generic helper to recursively find a Swing component inside the JPanel.
     */
    private inline fun <reified T : Component> findComponent(predicate: (T) -> Boolean): T? {
        val components = calculatorPanel.components
        for (comp in components) {
            if (comp is T && predicate(comp)) {
                return comp
            }
        }
        return null
    }
}