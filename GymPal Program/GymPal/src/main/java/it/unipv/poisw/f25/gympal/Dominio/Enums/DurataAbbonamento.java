package it.unipv.poisw.f25.gympal.Dominio.Enums;

public enum DurataAbbonamento {
	
	/*Le "enum" modellano concetti di business, non visuali(GUI), e potrebbero essere
	 *riutilizzate per altri scopi, non per forza legati alla GUI (che deve riflettere
	 *il dominio, non viceversa).
	 *
	 *In tal modo è rafforzata la separazione fra strato di dominio e strato GUI, ed in
	 *più, la logica di dominio è isolata da quella di visualizzazione.*/
	TRIMESTRALE, SEMESTRALE, ANNUALE, NESSUNO

}
