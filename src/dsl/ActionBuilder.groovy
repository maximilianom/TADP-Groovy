package dsl

import utn.frba.tadp.firewall.impl.actions.InformarBloqueoAction
import utn.frba.tadp.firewall.impl.actions.LoggearPaqueteAction
import utn.frba.tadp.firewall.impl.model.Regla

class ActionBuilder {
	def static falla = "falla"
	def static acepta = "acepta"
	
	def static logea = new LoggearPaqueteAction()
	def static informa = new InformarBloqueoAction()
	
	def Regla regla
	
	def builder
	
	ActionBuilder(regla, word) {
		this.regla = regla
		if(word.equals(falla)) {
			 builder = new BlockingActionBuilder(regla)
		} else {
			builder = new PublicActionBuilder(regla)
		}
	}
	
	def entonces(accion) {
		builder.entonces(accion)
	}
}

class BlockingActionBuilder {
	def Regla regla
	
	BlockingActionBuilder(regla) {
		this.regla = regla
	}
	
	def entonces(accion) {
		regla.addBlockAction(accion)
		regla
	}
}

class PublicActionBuilder {
	def Regla regla
	
	PublicActionBuilder(regla) {
		this.regla = regla
	}
	
	def entonces(accion) {
		regla.addPublicAction(accion)
		regla
	}
}
