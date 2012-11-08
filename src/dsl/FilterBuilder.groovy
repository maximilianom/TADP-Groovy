package dsl

import utn.frba.tadp.firewall.impl.filters.ports.FiltroConjuntoDePuertos
import static dsl.DSL.*

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
    
    def y(port) {
        this.del(port) 
        if (word.equals(todos)) new FiltroConjuntoDePuertos(puertoList)
    }

}