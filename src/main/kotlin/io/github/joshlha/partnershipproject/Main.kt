package io.github.joshlha.partnershipproject

import com.formdev.flatlaf.FlatDarkLaf
import io.github.joshlha.partnershipproject.Main.Companion.logger
import net.miginfocom.swing.MigLayout
import org.slf4j.LoggerFactory
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel


fun main(args: Array<String>) {
    FlatDarkLaf.setup() // Setup the Look-and-feel. This gives us a dark mode, modern UI
    logger.info("Showing JFrame!")

    // Create an instance of JFrame and set it visible to show it.
    // A JFrame is effectively just a window.
    // Set the "contentPane" to an instance of the "Main" class, which is a customized JPanel.
    JFrame("Partnership Program").apply {
        contentPane = Main()
        setSize(800, 600)
        setLocationRelativeTo(null) // Centers the JFrame on the screen.
        isVisible = true // false by default.
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        // Disposing one frame removes it and its children from memory.
        // Disposing all frames ends the program.
    }
}