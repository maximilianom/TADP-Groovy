package dsl

import utn.frba.tadp.firewall.impl.Firewall
import utn.frba.tadp.firewall.impl.model.Regla

class DSL {
    
    def static todos = "todos"
	def static envios = "envios"

    static {
        Firewall.metaClass {
            filtrar = { word -> new FilterBuilder(delegate, word) }
			bloquear = { word -> new BlockIPBuilder(delegate)}
        }
		
		Regla.metaClass {
			si = { word -> new ActionBuilder(delegate, word) }
		}
    }

}