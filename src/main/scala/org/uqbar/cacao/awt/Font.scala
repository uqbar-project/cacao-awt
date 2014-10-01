package org.uqbar.cacao.awt

import org.uqbar.math.vectors.Vector
import org.uqbar.cacao.FontModifier
import org.uqbar.cacao.Font
import java.awt.{ Font => JavaFont }
import java.awt.Canvas

class AWTFont(val innerFont: JavaFont) extends Font {
	protected lazy val metrics = new Canvas().getFontMetrics(innerFont)

	def sizeOf(target: Char): Vector = (metrics.charWidth(target), innerFont.getSize2D * 1.05)
}