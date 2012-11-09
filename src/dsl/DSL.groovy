package dsl

import utn.frba.tadp.firewall.impl.Firewall

class DSL {
    
    def static todos = "todos"
	def static envios = "envios"

    static {
        Firewall.metaClass {
            filtrar = { word -> new FilterBuilder(delegate, word) }
			bloquear = { word -> new BlockIPBuilder(delegate)}
        }
    }

}