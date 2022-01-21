package modelo;

public class Album {

	private String nombreImagen;
	private String tituloAlbum;
	
	public Album(String nombreImagen, String tituloAlbum) {
		this.nombreImagen = nombreImagen;
		this.tituloAlbum = tituloAlbum;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public String getTituloAlbum() {
		return tituloAlbum;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public void setTituloAlbum(String tituloAlbum) {
		this.tituloAlbum = tituloAlbum;
	}
	
}
