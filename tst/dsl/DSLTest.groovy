package dsl;

import static org.junit.Assert.*
import junit.framework.Assert

import org.junit.Before
import org.junit.Test

import utn.frba.tadp.firewall.api.Filter
import utn.frba.tadp.firewall.impl.Firewall
import utn.frba.tadp.firewall.impl.model.Request

import static dsl.DSL.*
import static dsl.FilterBuilder.*
import static dsl.BlockIPBuilder.*;

class DSLTest {

    Firewall firewall
    
    @Before
    void 'set up'() {
        firewall = new Firewall()
    }
    
    @Test
    public void 'Redirigir todo los paquetes con destino a 192.168.1.185 a la IP 192.168.1.73'() {
    }
	
	@Test
	public void 'Bloquear envios desde 192.168.1.1 a 192.168.1.2 por el puerto 80'(){
		Filter filtro = firewall.bloquear envios desde "192.168.1.1" a "192.168.1.2" 
		
		Assert.assertTrue(filtro.accepts(new Request(80, "192.168.1.1", "192.168.1.100")))
		Assert.assertTrue(filtro.accepts(new Request(5702, "192.168.1.1", "127.0.0.1")))
		Assert.assertFalse(filtro.accepts(new Request(8080, "192.168.1.1", "192.168.1.2")))
	}
    
    @Test
    public void 'Filtrar todos los puertos salvo el 80, 443 y 8080'() {
        Filter filtro = firewall.filtrar todos los puertos a excepcion del 80, 443 y 8080
        
        Assert.assertTrue (filtro.accepts(new Request(1, "", "")));
        Assert.assertFalse(filtro.accepts(new Request(80, "", "")));
        Assert.assertFalse(filtro.accepts(new Request(443, "", "")));
        Assert.assertFalse(filtro.accepts(new Request(8080, "", "")));
    }
}
