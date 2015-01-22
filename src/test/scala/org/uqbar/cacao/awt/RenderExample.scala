package org.uqbar.cacao.awt

import java.awt.Canvas
import java.awt.{Color => AWTColor}
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Toolkit

import scala.language.implicitConversions

import org.scalatest.FreeSpec
import org.uqbar.cacao.Circle
import org.uqbar.cacao.Color
import org.uqbar.math.spaces.R2._
import org.uqbar.cacao.awt.implicits._

import javax.swing._

object RenderExample extends App {
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

	val w = mkWindow
	w.c.createBufferStrategy(2)
	val bs = w.c.getBufferStrategy
	val renderer = new AWTRenderer(w.c)

	//*********************************************************************************************
	// RENDERS
	//*********************************************************************************************

	renderer.beforeRendering
	renderer.color = Color.Red
	renderer.fill(Circle((50, 50), 50))
	renderer.afterRendering
}