package dsl

import utn.frba.tadp.firewall.impl.Firewall
import utn.frba.tadp.firewall.impl.model.Regla

class DSL {
    
    def static los = "los"
	def static envios = "envios"

    static {
        Firewall.metaClass {
			bloquear = 
				{ word -> 
					if(word.equals(los)) return new FilterBuilder(delegate, word)
					else new BlockIPBuilder(delegate)
				}
        }
		
		/*Regla.metaClass {
			si = { word -> new ActionBuilder(delegate, word) }
		}*/
    }

}