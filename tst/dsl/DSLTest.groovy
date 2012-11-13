package dsl;

import static dsl.BlockIPBuilder.*
import static dsl.DSL.*
import static dsl.FilterBuilder.*
import static dsl.ActionBuilder.*
import static org.junit.Assert.*
import junit.framework.Assert

import org.junit.Before
import org.junit.Test

import utn.frba.tadp.firewall.api.Filter
import utn.frba.tadp.firewall.impl.Firewall
import utn.frba.tadp.firewall.impl.model.Regla
import utn.frba.tadp.firewall.impl.model.Request

class DSLTest {

    Firewall firewall
    
    @Before
    void 'set up'() {
        firewall = new Firewall()
    }
	
	@Test
	public void 'Bloquear envios desde 192.168.1.1 a 192.168.1.2 por el puerto 80'(){
		Regla regla = firewall.bloquear envios desde "192.168.1.1" a "192.168.1.2"
		
		List<Regla> reglas = firewall.getRules();
		Assert.assertEquals(regla, reglas.get(0));
		
		Assert.assertTrue(regla.getFilter().accepts(new Request(80, "192.168.1.1", "192.168.1.100")))
		Assert.assertTrue(regla.getFilter().accepts(new Request(5702, "192.168.1.1", "127.0.0.1")))
		Assert.assertFalse(regla.getFilter().accepts(new Request(8080, "192.168.1.1", "192.168.1.2")))
	}
    
    @Test
    public void 'Filtrar todos los puertos salvo el 80, 443 y 8080'() {
        Regla regla = firewall.filtrar todos los puertos a excepcion de {[80, 443, 8080]}
		
		firewall.filtrar todos los puertos a excepcion de {[80, 443, 8080]}
			si falla entonces loguea
			si acepta entonces bleh
        
        Assert.assertTrue (regla.getFilter().accepts(new Request(1, "", "")));
        Assert.assertFalse(regla.getFilter().accepts(new Request(80, "", "")));
        Assert.assertFalse(regla.getFilter().accepts(new Request(443, "", "")));
        Assert.assertFalse(regla.getFilter().accepts(new Request(8080, "", "")));
    }
	
	@Test
	public void 'Logear mensajes bloqueados'() {
		Regla regla = firewall.filtrar todos los puertos a excepcion de {[80]}
		regla.si falla entonces logea
	}
}
