package modelo;

import javafx.scene.control.Button;

public class Letra {

	private Button btnLetra;
	private String letra;
	
	public Letra(String letra) {
		this.btnLetra = new Button("Letra");
		this.letra = letra;
	}
	
	//Botón
	public Button getBtnLetra() {
		return btnLetra;
	}
	public void setBtnLetra(Button btnLetra) {
		this.btnLetra = btnLetra;
	}
	
	//Letra
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
}
