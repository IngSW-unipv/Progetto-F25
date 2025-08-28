package it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.Codecs;

import java.util.List;

public interface IListsToStringCodec {
	
	public String condensaAbbonamento(List<String> componentiAbbonamento,
	  		 						  List<String> corsiSelezionati);
	
    //----------------------------------------------------------------
	
	public void espandiAbbonamento(String composizioneAbbonamento,
			   					   List<String> componentiAbbonamento,
			   					   List<String> corsiSelezionati);
	
    //----------------------------------------------------------------

}
