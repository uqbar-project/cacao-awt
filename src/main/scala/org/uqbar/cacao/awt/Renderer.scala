package org.uqbar.cacao.awt

import org.uqbar.math.vectors._
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
		new AWTRenderer(canvas, graphics.create(from.x, from.y, size.x, size.y).asInstanceOf[Graphics2D])

	def scaled(ratio: Vector): AWTRenderer = {
		val g = graphics.create.asInstanceOf[Graphics2D]
		g.scale(ratio.x, ratio.y)
		new AWTRenderer(canvas, g)
	}
	def translated(translation: Vector): AWTRenderer = {
		val g = graphics.create.asInstanceOf[Graphics2D]
		g.translate(translation.x, translation.y)
		new AWTRenderer(canvas, g)
	}

	def draw(image: Image)(position: Vector = Origin) =
		graphics.drawImage(image.asInstanceOf[AWTImage].innerImage, position.x.toInt, position.y.toInt, null)

	def draw(string: String)(position: Vector) {
		graphics.setColor(color)
		graphics.setFont(font.asInstanceOf[AWTFont].innerFont)
		graphics.drawString(string, position.x, position.y)
	}
	def draw(shapes: Shape*) {
		graphics.setColor(color)
		for (shape <- shapes) shape match {
			case Line(from, to) => graphics.drawLine(from.x, from.y, to.x, to.y)
			case Rectangle(from, size) => graphics.drawRect(from.x, from.y, size.x, size.y)
			case Circle(at, radius) => graphics.drawOval(at.x - radius, at.y - radius, radius * 2, radius * 2)
		}
	}
	def fill(shapes: Shape*) {
		graphics.setColor(color)
		for (shape <- shapes) shape match {
			case Line(from, to) => graphics.drawLine(from.x, from.y, to.x, to.y)
			case Rectangle(from, size) => graphics.fillRect(from.x, from.y, size.x, size.y)
			case Circle(at, radius) => graphics.fillOval(at.x - radius, at.y - radius, radius * 2, radius * 2)
		}
	}

	def clear(from: Vector, size: Vector) = graphics.clearRect(from.x, from.y, size.x, size.y)
}