package org.uqbar._cacao.awt

import org.uqbar._cacao.FontDef
import org.uqbar.math.vectors.Vector

trait FontAWTDef extends FontDef {

	object Font extends FontCompanion {
		def apply(name: Symbol, size: Int, modifiers: FontModifier*): FontAWT = ???
	}

	class FontAWT extends Font {
		def sizeOf(target: Char): Vector = ???
	}
}