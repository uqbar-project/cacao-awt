package org.uqbar.cacao.awt

import org.uqbar.math.spaces.R2._
import org.uqbar.cacao.Image
import org.uqbar.cacao.Color
import java.awt.image.BufferedImage
import java.awt.{ Color => AWTColor }
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import javax.imageio.ImageIO
import java.io.File
import sun.java2d.loops.DrawLine

class AWTImage(val innerImage: BufferedImage) extends Image {
	implicit private def Double_to_Int(d: Double): Int = d.toInt

	val size: Vector = (innerImage.getWidth, innerImage.getHeight)

	def apply(position: Vector): Color = {
		val c = new AWTColor(innerImage.getRGB(position(X).toInt, position(Y).toInt), true)
		Color(c.getRed, c.getBlue, c.getGreen, c.getAlpha)
	}

	def writeTo(fileName: String) = ImageIO.write(innerImage, "png", new File(fileName))

	def scaled(ratio: Vector) = new AWTImage(
		transformedImage(innerImage)(AffineTransform.getScaleInstance(ratio(X), ratio(Y)))
	)

	def rotated(radians: Double) = {
		val newImage = new BufferedImage(size(X), size(Y), innerImage.getType)
		val graphics = newImage.createGraphics
		graphics.rotate(radians, size(X) / 2, size(Y) / 2)
		graphics.drawImage(innerImage, null, 0, 0)
		graphics.dispose

		new AWTImage(newImage)
	}

	def cropped(from: Vector = (0, 0))(size: Vector) = new AWTImage(
		innerImage.getSubimage(from(X), from(Y), size(X), size(Y))
	)

	def repeated(repetitions: Vector): AWTImage = {
		val horizontalIterations: Int = repetitions(X).ceil
		val verticalIterations: Int = repetitions(Y).ceil
		val newImage = new BufferedImage(size(X) * repetitions(X), size(Y) * repetitions(Y), innerImage.getType)

		val graphics = newImage.createGraphics

		for {
			i ← 0 until horizontalIterations
			j ← 0 until verticalIterations
		} graphics.drawImage(innerImage, i * size(X), j * size(Y), null)

		graphics.dispose

		new AWTImage(newImage)
	}

	protected def transformedImage(image: BufferedImage)(transformation: AffineTransform) =
		new AffineTransformOp(transformation, AffineTransformOp.TYPE_BICUBIC).filter(image, new BufferedImage(
			size(X) * transformation.getScaleX.abs,
			size(Y) * transformation.getScaleY.abs,
			image.getType
		))
}