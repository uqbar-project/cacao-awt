package org.uqbar._cacao.awt

import org.uqbar._cacao.RendererDef
import org.uqbar.math.vectors._

trait RendererAWTDef extends RendererDef {
	object Renderer extends RendererCompanion {
		def apply = new RendererAWT
	}

	class RendererAWT extends Renderer {
		def cropped(from: Vector = (0, 0))(size: Vector): Renderer = ???
		def scaled(ratio: Vector): Renderer = ???
		def translated(translation: Vector): Renderer = ???

		def draw(image: Image)(position: Vector = Origin) = ???
		def draw(string: String)(position: Vector) = ???
		def draw(shape: Shape) = ???
		def fill(shape: Shape) = ???
		def clear(from: Vector, size: Vector) = ???

		def set(setting: RenderSetting): Renderer = ???
	}

}