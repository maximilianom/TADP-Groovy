package dsl

import static dsl.DSL.*
import utn.frba.tadp.firewall.api.Action
import utn.frba.tadp.firewall.api.Filter
import utn.frba.tadp.firewall.impl.filters.ports.FiltroConjuntoDePuertos
import utn.frba.tadp.firewall.impl.model.Regla

class FilterBuilder {

    def static puertos = "puertos"
    def static excepcion = "excepcion"
    
    def puertoList = []
    def word
    def firewall
    
    FilterBuilder(firewall, word) {
        this.firewall = firewall 
        this.word = word
    }
    
    def los(ports) { this }

    def a(ex) { this }
    
    def del(int[] ports) {
        for (port in ports) {this.puertoList << port}
        this
    }
	
	def de(bloque) {
		def lista = bloque()
		lista.each {this.puertoList << it }
		Filter filter = new FiltroConjuntoDePuertos(this.puertoList)
		Regla regla = new Regla(new ArrayList<Action>(), new ArrayList<Action>(), filter )
		firewall.addRule(regla)
		regla
		
	}
    
    def y(port) {
        this.del(port) 
        if (word.equals(todos)) new FiltroConjuntoDePuertos(puertoList)
    }

}