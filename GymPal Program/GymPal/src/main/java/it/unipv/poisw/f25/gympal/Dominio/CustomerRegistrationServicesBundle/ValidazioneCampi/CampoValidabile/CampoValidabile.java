package it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabile;

import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;

public class CampoValidabile implements ICampoValidabile{
	
	private JTextField field;
    private String regex;
    private IRegexCheck checker;
    
	//----------------------------------------------------------------

    public CampoValidabile(JTextField field, String regex, IRegexCheck checker) {
        this.field = field;
        this.regex = regex;
        this.checker = checker;
    }
    
	//----------------------------------------------------------------

    @Override
    public boolean isValido() {
        String testo = field.getText().trim();
        return checker.check(testo, regex);
    }
    
	//----------------------------------------------------------------

    @Override
    public JTextField getField() {
        return field;
    }
    
	//----------------------------------------------------------------

}
