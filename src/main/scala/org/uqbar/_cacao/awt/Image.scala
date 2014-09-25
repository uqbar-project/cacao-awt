package org.uqbar._cacao.awt

import org.uqbar._cacao.ImageDef
import org.uqbar.math.vectors.Vector

trait ImageAWTDef extends ImageDef {

	object Image extends ImageCompanion {
		def apply(fileName: String): ImageAWT = ???
	}

	class ImageAWT extends Image {
		def size: Vector = ???

		def apply(position: Vector): Color = ???

		def writeTo(fileName: String) = ???

		def scaled(ratio: Vector): Image = ???
		def rotated(radians: Double): Image = ???
		def cropped(from: Vector = (0, 0))(size: Vector): Image = ???
		def repeated(repetitions: Vector): Image = ???
	}
}