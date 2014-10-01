package org.uqbar.cacao.awt

import org.uqbar.math.vectors.Vector
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
		val c = new AWTColor(innerImage.getRGB(position.x.toInt, position.y.toInt), true)
		Color(c.getRed, c.getBlue, c.getGreen, c.getAlpha)
	}

	def writeTo(fileName: String) = ImageIO.write(innerImage, "png", new File(fileName))

	def scaled(ratio: Vector) = new AWTImage(
		transformedImage(innerImage)(AffineTransform.getScaleInstance(ratio.x, ratio.y))
	)

	def rotated(radians: Double) = {
		val newImage = new BufferedImage(size.x, size.y, innerImage.getType)
		val graphics = newImage.createGraphics
		graphics.rotate(radians, size.x / 2, size.y / 2)
		graphics.drawImage(innerImage, null, 0, 0)
		graphics.dispose

		new AWTImage(newImage)
	}

	def cropped(from: Vector = (0, 0))(size: Vector) = new AWTImage(
		innerImage.getSubimage(from.x, from.y, size.x, size.y)
	)

	def repeated(repetitions: Vector): AWTImage = {
		val horizontalIterations: Int = repetitions.x.ceil
		val verticalIterations: Int = repetitions.y.ceil
		val newImage = new BufferedImage(size.x * repetitions.x, size.y * repetitions.y, innerImage.getType)

		val graphics = newImage.createGraphics

		for {
			i ← 0 until horizontalIterations
			j ← 0 until verticalIterations
		} graphics.drawImage(innerImage, i * size.x, j * size.y, null)

		graphics.dispose

		new AWTImage(newImage)
	}

	protected def transformedImage(image: BufferedImage)(transformation: AffineTransform) =
		new AffineTransformOp(transformation, AffineTransformOp.TYPE_BICUBIC).filter(image, new BufferedImage(
			size.x * transformation.getScaleX.abs,
			size.y * transformation.getScaleY.abs,
			image.getType
		))
}