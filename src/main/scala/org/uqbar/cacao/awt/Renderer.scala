package org.uqbar.cacao.awt

import org.uqbar.math.spaces.R2._
import org.uqbar.cacao._
import java.awt.Graphics2D
import java.awt.image.BufferStrategy
import java.awt.{ Color => AWTColor }
import java.awt.Canvas

class AWTRenderer(canvas: Canvas, var graphics: Graphics2D = null) extends Renderer {

	implicit private def Double_to_Int(d: Double): Int = d.toInt
	implicit private def Color_to_Color(c: Color) = new AWTColor(c.red, c.green, c.blue, c.alpha)

	protected def bufferStrategy = canvas.getBufferStrategy

	def beforeRendering = graphics = bufferStrategy.getDrawGraphics.asInstanceOf[Graphics2D]

	def afterRendering {
		graphics.dispose
		bufferStrategy.show
	}

	def cropped(from: Vector = (0, 0))(size: Vector): AWTRenderer =
		new AWTRenderer(canvas, graphics.create(from(X), from(Y), size(X), size(Y)).asInstanceOf[Graphics2D])

	def scaled(ratio: Vector): AWTRenderer = {
		val g = graphics.create.asInstanceOf[Graphics2D]
		g.scale(ratio(X), ratio(Y))
		new AWTRenderer(canvas, g)
	}
	def translated(translation: Vector): AWTRenderer = {
		val g = graphics.create.asInstanceOf[Graphics2D]
		g.translate(translation(X), translation(Y))
		new AWTRenderer(canvas, g)
	}

	def draw(image: Image)(position: Vector = Origin) =
		graphics.drawImage(image.asInstanceOf[AWTImage].innerImage, position(X).toInt, position(Y).toInt, null)

	def draw(string: String)(position: Vector) {
		graphics.setColor(color)
		graphics.setFont(font.asInstanceOf[AWTFont].innerFont)
		graphics.drawString(string, position(X), position(Y))
	}
	def draw(shapes: Shape*) {
		graphics.setColor(color)
		for (shape <- shapes) shape match {
			case Line(from, to) => graphics.drawLine(from(X), from(Y), to(X), to(Y))
			case Rectangle(from, size) => graphics.drawRect(from(X), from(Y), size(X), size(Y))
			case Circle(at, radius) => graphics.drawOval(at(X) - radius, at(Y) - radius, radius * 2, radius * 2)
		}
	}
	def fill(shapes: Shape*) {
		graphics.setColor(color)
		for (shape <- shapes) shape match {
			case Line(from, to) => graphics.drawLine(from(X), from(Y), to(X), to(Y))
			case Rectangle(from, size) => graphics.fillRect(from(X), from(Y), size(X), size(Y))
			case Circle(at, radius) => graphics.fillOval(at(X) - radius, at(Y) - radius, radius * 2, radius * 2)
		}
	}

	def clear(from: Vector, size: Vector) = graphics.clearRect(from(X), from(Y), size(X), size(Y))
}