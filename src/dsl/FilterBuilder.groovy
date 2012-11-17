package dsl

import static dsl.DSL.*
import utn.frba.tadp.firewall.api.Action
import utn.frba.tadp.firewall.api.Filter
import utn.frba.tadp.firewall.impl.filters.ports.FiltroConjuntoDePuertos
import utn.frba.tadp.firewall.impl.model.Regla

class FilterBuilder {
    
    def puertoList = []
    def word
    def firewall
    
    FilterBuilder(firewall, word) {
        this.firewall = firewall 
        this.word = word
    }

    def puertos(bloque){
		def lista = bloque()
		lista.each {this.puertoList << it }
		Filter filter = new FiltroConjuntoDePuertos(this.puertoList)
		Regla regla = new Regla(new ArrayList<Action>(), new ArrayList<Action>(), filter )
		firewall.addRule(regla)
		
		return new ActionBuilder(regla)
	}
}