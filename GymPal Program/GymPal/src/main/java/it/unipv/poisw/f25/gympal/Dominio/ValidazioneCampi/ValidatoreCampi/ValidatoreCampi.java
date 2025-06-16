package it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.ValidatoreCampi;

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public class ValidatoreCampi implements IValidatoreCampi{
	
	private List<ICampoValidabile> campi = new ArrayList<>();
	
	//----------------------------------------------------------------
	
	@Override
	public void registra(ICampoValidabile campo) {
		
		campi.add(campo);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public boolean tuttiValidi() {
		
		return campi.stream().allMatch(ICampoValidabile::isValido);
		
		
		/*La sinstassi sopra utilizzata è un modo conciso per scrivere:
		
		boolean tuttiValidi = true;
		for (ICampoValidabile campo : campi) {
		    if (!campo.isValido()) {
		        tuttiValidi = false;
		        break;
		    }
		}
		
		* La lista di campi è trasformata in un flusso di elementi, a cui è possibile
		* applicare operazioni funzionali a catena ("isValido", in questo caso).
		* 
		* La "allMatch" è un metodo del flusso che prende un predicato (una funzione che
		* restituisce 'true' oppure 'false', niente di più, niente di meno), e restituisce
		* 'true' soltanto se tutti gli elementi del flusso soddisfano il predicato.
		*/
		
	}
	
	//----------------------------------------------------------------

}
