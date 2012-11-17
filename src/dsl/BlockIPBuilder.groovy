package dsl

import utn.frba.tadp.firewall.api.Action
import utn.frba.tadp.firewall.api.Filter
import utn.frba.tadp.firewall.impl.actions.ForwardAction
import utn.frba.tadp.firewall.impl.filters.ip.FiltroIpIndividual;
import utn.frba.tadp.firewall.impl.model.RangoIP
import utn.frba.tadp.firewall.impl.model.Regla

class BlockIPBuilder {
	def ipOrigen = ""
	def firewall
	
	
	BlockIPBuilder(firewall){
		this.firewall = firewall
	}
	
	def desde(ip) {
		this.ipOrigen = ip
		this
	}
	
	def a(ip) {
		FiltroIpIndividual filtro = new FiltroIpIndividual(this.ipOrigen, ip)
		Regla r = new Regla(new ArrayList<Action>(), new ArrayList<Action>(), filtro)
		this.firewall.addRule(r)
		
		return new ActionBuilder(r)
	}
}
