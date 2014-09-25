package org.uqbar._cacao

import org.uqbar.math.vectors._

package object awt {

	def setUp {
		org.uqbar.Cacao = new CacaoAWT
	}

	class CacaoAWT extends Cacao with ImageAWTDef with FontAWTDef with RendererAWTDef
}