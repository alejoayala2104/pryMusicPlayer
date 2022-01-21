package modelo;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;

public class Cancion {	
	
	private ObjectProperty<CheckBox> check = new SimpleObjectProperty<>();
	private ObjectProperty<Album> album = new SimpleObjectProperty<>();
	private StringProperty titulo = new SimpleStringProperty();
	private StringProperty artista = new SimpleStringProperty();
	private StringProperty genero = new SimpleStringProperty();
	private ObjectProperty<Hyperlink> iTunes = new SimpleObjectProperty<>();
	private ObjectProperty<Hyperlink> spotify = new SimpleObjectProperty<>();
	private IntegerProperty rating = new SimpleIntegerProperty();
	private ObjectProperty<Letra> objLetra = new SimpleObjectProperty<>();
	
	public Cancion(String nombreImagen, String tituloAlbum, String titulo,
			String artista, String genero, String linkiTunes, String linkSpotify,
			int rating, String letra) {
		
		this.setCheck(new CheckBox());
		this.setAlbum(new Album(nombreImagen,tituloAlbum));
		this.setTitulo(titulo);
		this.setArtista(artista);
		this.setGenero(genero);
		
		//Creación del hiperlink para iTunes
		Hyperlink iTunes = new Hyperlink();
		iTunes.setText("iTunes");

		iTunes.setOnAction(e -> {
		    if(Desktop.isDesktopSupported())
		    {
		        try {
		            Desktop.getDesktop().browse(new URI(linkiTunes));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }
		});		
		this.setITunes(iTunes);
		
		//Creación del hiperlink para Spotify
		Hyperlink spotify = new Hyperlink();
		spotify.setText("Spotify");

		spotify.setOnAction(e -> {
		    if(Desktop.isDesktopSupported())
		    {
		        try {
		            Desktop.getDesktop().browse(new URI(linkSpotify));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }
		});		
		this.setSpotify(spotify);
		
		this.setRating(rating);
		this.setObjLetra(new Letra(letra));
	}
	
	
	//Check	
	public void setCheck(CheckBox chk) {
		this.check.set(chk);
	}
	public CheckBox getCheck() {
		return this.check.get();
	}
	public ObjectProperty<CheckBox> checkProperty(){
		return this.check;
	}
	
	//Album
	public void setAlbum(Album alb){
	  this.album.set(alb); 
    }
    public Album getAlbum(){
        return this.album.get();
    }
    public ObjectProperty<Album> albumProperty(){
        return this.album;
    }
    
    //Titulo    
    public void setTitulo(String tit){
    	this.titulo.set(tit); 
	}
	public String getTitulo(){
		return this.titulo.get();
	}
	public StringProperty tituloProperty(){
		return this.titulo;
	}
	
	//Artista
	public void setArtista(String art) {
		this.artista.set(art);
	}
	public String getArtista() {
		return this.artista.get();
	}    
	public StringProperty artistaProperty() {
		return this.artista;
	}
	
	//Género
	public void setGenero(String gen) {
		this.genero.set(gen);
	}
	public String getGenero() {
		return this.genero.get();
	}
	public StringProperty generoProperty() {
		return this.genero;
	}
	
	//Hipervículo de iTunes
	public void setITunes(Hyperlink itunes) {
		this.iTunes.set(itunes);
	}
	public Hyperlink getITunes() {
		return this.iTunes.get();
	}
	public ObjectProperty<Hyperlink> iTunesProperty() {
		return this.iTunes;		
	}
	
	//Hipervículo de Spotify
	public void setSpotify(Hyperlink spot) {
		this.spotify.set(spot);
	}
	public Hyperlink getSpotify() {
		return this.spotify.get();
	}
	public ObjectProperty<Hyperlink> spotifyProperty() {
		return this.spotify;		
	}	
	
	//Rating
	public void setRating(int rat) {
		this.rating.set(rat);
	}
	public int getRating() {
		return this.rating.get();
	}
	public IntegerProperty ratingProperty() {
		return this.rating;
	}
	
	//Botón letra
	public void setObjLetra(Letra objLet) {
		this.objLetra.set(objLet);
	}
	public Letra getObjLetra() {
		return this.objLetra.get();
	}
	public ObjectProperty<Letra> objLetraProperty() {
		return this.objLetra;
	}
}
