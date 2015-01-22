package org.uqbar.cacao.awt

import scala.language.implicitConversions
import java.awt.{ Point => AWTPoint, Dimension => AWTDimension }

import org.uqbar.math.spaces.R2._

package object implicits {
  implicit def Point_to_Vector(p: AWTPoint): Vector = (p(X), p(Y))
  implicit def Vector_to_Point(v: Vector): AWTPoint = new AWTPoint(v(X).toInt, v(Y).toInt)

  implicit def Dimension_to_Vector(d: AWTDimension): Vector = (d.width, d.height)
  implicit def Vector_to_Dimension(v: Vector): AWTDimension = new AWTDimension(v(X).toInt, v(Y).toInt)
}