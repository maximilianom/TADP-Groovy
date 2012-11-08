package dsl

import utn.frba.tadp.firewall.impl.Firewall

class DSL {
    
    def static todos = "todos"

    static {
        Firewall.metaClass {
            filtrar = { word -> new FilterBuilder(delegate, word) }
        }
    }

}