package dsl;

import static dsl.ActionBuilder.*
import static dsl.BlockIPBuilder.*
import static dsl.DSL.*
import static dsl.FilterBuilder.*
import static org.junit.Assert.*
import junit.framework.Assert

import org.junit.Before
import org.junit.Test

import utn.frba.tadp.firewall.impl.Firewall
import utn.frba.tadp.firewall.impl.model.Regla
import utn.frba.tadp.firewall.impl.model.Request
import utn.frba.tadp.firewall.mock.*;

class DSLTest {

    Firewall firewall
    
    @Before
    void 'set up'() {
        firewall = new Firewall()
    }
	
	@Test
	public void 'Bloquear envios desde 192.168.1.1 a 192.168.1.2'(){
		Regla regla = firewall.bloquear envios desde "192.168.1.1" a "192.168.1.2" si falla entonces logea
		
		List<Regla> reglas = firewall.getRules();
		Assert.assertEquals(regla, reglas.get(0));
		
		Assert.assertTrue(regla.getFilter().accepts(new Request(80, "192.168.1.1", "192.168.1.100")))
		Assert.assertTrue(regla.getFilter().accepts(new Request(5702, "192.168.1.1", "127.0.0.1")))
		Assert.assertFalse(regla.getFilter().accepts(new Request(8080, "192.168.1.1", "192.168.1.2")))
	}
    
    @Test
    public void 'Bloquear los puertos 80, 443 y 8080'() {
        Regla regla = firewall.bloquear los puertos {[80, 443, 8080]} si falla entonces informa	
					
			
		RequestListenerMock listenerMock = new RequestListenerMock()
		firewall.setDefaultRequestListener(listenerMock)
        
        Assert.assertTrue (regla.getFilter().accepts(new Request(1, "", "")));
        Assert.assertFalse(regla.getFilter().accepts(new Request(80, "", "")));
        Assert.assertFalse(regla.getFilter().accepts(new Request(443, "", "")));
        Assert.assertFalse(regla.getFilter().accepts(new Request(8080, "", "")));
		
		Request r = new Request(80, "127.0.0.1", "192.168.1.1")
		firewall.evaluate(r);
		
		Assert.assertTrue(listenerMock.wasBlocked(r));
    }
}
