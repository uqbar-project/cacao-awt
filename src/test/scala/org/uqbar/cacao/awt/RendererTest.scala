package org.uqbar.cacao.awt

import org.scalatest.FreeSpec
import org.scalatest.Matchers
import javax.swing._
import java.awt.Toolkit
import java.awt.{ Color => AWTColor }
import org.uqbar.math.vectors._
import org.uqbar.cacao.Circle
import java.awt.Canvas
import java.awt.Dimension
import org.uqbar.cacao.Color
import java.awt.Graphics2D

class RendererTest extends FreeSpec with Matchers {

	//*********************************************************************************************
	// TEST CASES
	//*********************************************************************************************

	def mkWindow = new JFrame {
		val c = new Canvas {
			setPreferredSize(new Dimension(100, 100))
			setMinimumSize(new Dimension(100, 100))
			setMaximumSize(new Dimension(100, 100))
			setIgnoreRepaint(true)
		}
		add(c)

		setLocationRelativeTo(null)
		setLocation((Toolkit.getDefaultToolkit.getScreenSize - (getSize: Vector)) / 2)

		setVisible(true)
		setResizable(false)
		pack
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
	}

	"Renderer" - {
		"?" in {
			val w = mkWindow
			w.c.createBufferStrategy(2)
			val bs = w.c.getBufferStrategy
			val renderer = new AWTRenderer(w.c)

			renderer.beforeRendering
			renderer.color = Color.Red
			renderer.fill(Circle((50, 50), 50))
			renderer.afterRendering
		}
	}
}