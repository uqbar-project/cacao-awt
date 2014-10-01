package org.uqbar.cacao.awt

import org.uqbar.cacao._
import org.uqbar.cacao.FontModifier._
import java.awt.image.BufferStrategy
import java.awt._
import java.awt.{ Font => JavaFont }
import javax.imageio.ImageIO

class AWTResourceLoader() extends ResourceLoader {
	def font(name: Symbol, size: Int, modifiers: FontModifier*): AWTFont = {
		val nameCode = name.toString.replace('_', ' ')
		val modifierCode = modifiers.toList match {
			case Bold :: Italic :: _ => "BOLDITALIC"
			case Bold :: _ => "BOLD"
			case Italic :: _ => "ITALIC"
			case _ => "PLAIN"
		}
		new AWTFont(JavaFont.decode(s"$nameCode-$modifierCode-$size"))
	}
	def image(fileName: String) = try {
		new AWTImage(ImageIO.read(getClass.getResource(fileName)))
	} catch {
		case _: Exception ⇒ throw new RuntimeException("The resource '" + fileName + "' was not found");
	}
}