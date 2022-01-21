package controlador;

import java.io.File;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Album;
import modelo.Cancion;
import modelo.Letra;


public class TableMusicControlador {

	@FXML
    private VBox vbxRoot;
    @FXML
    private Label lblBlblioMusica;
    @FXML
    private TableView<Cancion> tblCanciones;
    @FXML
    private TableColumn<Cancion, CheckBox> tblColCheck;
    @FXML
    private TableColumn<Cancion, Album> tblColAlbum;
    @FXML
    private TableColumn<Cancion, String> tblColTitulo;
    @FXML
    private TableColumn<Cancion, String> tblColArtista;
    @FXML
    private TableColumn<Cancion, String> tblColGenero;
    @FXML
    private TableColumn<Cancion, Hyperlink> tblColiTunes;
    @FXML
    private TableColumn<Cancion, Hyperlink> tblColSpotify;
    @FXML
    private TableColumn<Cancion, Integer> tblColRating;
    @FXML
    private TableColumn<Cancion, Letra> tblColLetra;
    @FXML
    private TextField txfAlbum;
    @FXML
    private TextField txfTitulo;
    @FXML
    private TextField txfArtista;
    @FXML
    private TextField txfGenero;
    @FXML
    private TextField txfiTunes;
    @FXML
    private TextField txfSpotify;
    @FXML
    private ComboBox<Integer> cbxRating;
    @FXML
    private TextArea txaLetra;
    @FXML
    private TextField txfBuscar;
    
    private ObservableList<Cancion> listaCanciones;
    
    private final FileChooser explorador = new FileChooser();

    //Método que configura todo lo perteneciente al Table View de canciones.
    public void configurarTblCanciones() {
    	
    	    	
    	//Configuración de los formatos de celda.
    	this.tblColCheck.setCellValueFactory(new PropertyValueFactory<Cancion, CheckBox>("check"));
    	
    	//Para la columna de álbum, se configura un formato de celda personalizado.
    	this.tblColAlbum.setCellValueFactory(new PropertyValueFactory<Cancion,Album>("album"));
    	this.tblColAlbum.setCellFactory(new Callback<TableColumn<Cancion,Album>, TableCell<Cancion,Album>>(){
    		
    		@Override
    		public TableCell<Cancion,Album> call(TableColumn<Cancion,Album>param){
    			TableCell<Cancion,Album> cell = new TableCell<Cancion,Album>(){
    				@Override
    				public void updateItem(Album item, boolean empty) {
    					
    					//Configuración primordial para sustituir el método updateItem personalizado
    					//de manera correcta. Dado que se hace el llamado a la superclase, se sobrescribe 
    					//el método de renderizado de la celda con todas sus propiedades.    					
    					super.updateItem(item, empty);
    					
    					//Si está vacía, o si la Cell es nula.
    					//Esta verificación se hace para eliminar problemas de renderizado.    					
    					if(empty || item==null) {
    						setGraphic(null);
    					}else {
    						HBox hbxAlbum = new HBox(10);
    						
    						ImageView albumCover = new ImageView();
    						albumCover.setFitHeight(50);
    						albumCover.setFitWidth(50);
    						albumCover.setImage(new Image("imagenes/"+item.getNombreImagen()));
    						
    						
    						albumCover.setOnMousePressed(event ->{
    							configurarExploradorArchivos();
    							try {
	    							//Se obtiene el archivo seleccionado. Se hace un cast para obtener el Stage de la ventana abierta.
									File imagenCover = explorador.showOpenDialog((Stage) albumCover.getScene().getWindow());
									if(imagenCover.exists()) {
										albumCover.setImage(new Image("imagenes/"+imagenCover.getName()));
									}
    							}catch(Exception e) {
    								mostrarAlerta(AlertType.INFORMATION, "Selección de imagen", "Se cerró el explorador de archivos", null);
    							}
    						});    						
    						
    						hbxAlbum.getChildren().add(albumCover);
    						hbxAlbum.getChildren().add(new Label(item.getTituloAlbum()));
    						setGraphic(hbxAlbum);
    					}
    				}
    			};
    			return cell;
    		}
    	});
    	
    	//Configuración para hacer que titulo sea editable como un TextField
    	this.tblColTitulo.setCellValueFactory(new PropertyValueFactory<Cancion, String>("titulo"));
    	this.tblColTitulo.setCellFactory(TextFieldTableCell.forTableColumn());
    	this.tblColTitulo.setOnEditCommit(
    		    new EventHandler<CellEditEvent<Cancion, String>>() {
    		        @Override
    		        public void handle(CellEditEvent<Cancion, String> t) {
    		            ((Cancion) t.getTableView().getItems().get(
    		                t.getTablePosition().getRow())
    		                ).setTitulo(t.getNewValue());
    		        }
    		    }
    	);
    	
    	//Hace que la tabla pueda ser editable en los campos que tengan el formato.
    	this.tblCanciones.setEditable(true);
    	
    	this.tblColArtista.setCellValueFactory(new PropertyValueFactory<Cancion, String>("artista"));
    	this.tblColGenero.setCellValueFactory(new PropertyValueFactory<Cancion, String>("genero"));
    	this.tblColiTunes.setCellValueFactory(new PropertyValueFactory<Cancion, Hyperlink>("iTunes"));
    	this.tblColSpotify.setCellValueFactory(new PropertyValueFactory<Cancion, Hyperlink>("spotify"));
    	this.tblColRating.setCellValueFactory(new PropertyValueFactory<Cancion, Integer>("rating"));
    	
    	//Configuración personalizada del formato de celda de la columna letra.
    	this.tblColLetra.setCellValueFactory(new PropertyValueFactory<Cancion, Letra>("objLetra"));
    	this.tblColLetra.setCellFactory(new Callback<TableColumn<Cancion,Letra>, TableCell<Cancion,Letra>>(){
    		
    		@Override
    		public TableCell<Cancion,Letra> call(TableColumn<Cancion,Letra>param){
    			TableCell<Cancion,Letra> cell = new TableCell<Cancion,Letra>(){
    				@Override
    				public void updateItem(Letra item, boolean empty) {
    					
    					super.updateItem(item, empty);
    					
    					if(empty || item==null) {    						
    						setGraphic(null);
    					}
    					else {
    						Button btnLetra = new Button("Letra");
    						btnLetra.setOnAction(event -> {
    							TextArea txaLetra = new TextArea();
    							txaLetra.setText(item.getLetra());
    							Scene scene = new Scene(txaLetra);
    							Stage ventana = new Stage();
    							ventana.setScene(scene);
    							ventana.setTitle("Letra de la canción");
    							ventana.show();
    						});
    						setGraphic(btnLetra);
    					}
    				}
    			};
    			return cell;
    		}
    	});
    	    	    	
    	//Configurando los items(objetos) a mostrar en cada celda del TableView
    	this.tblCanciones.setItems(this.listaCanciones);
    }

    @FXML
    public void agregarCancion(ActionEvent event) {

    	//Validación selección de rating
    	if(this.cbxRating.getValue()==null) {
    		this.mostrarAlerta(AlertType.ERROR,"ERROR:Rating inválido","Rating inválido","Por favor dé un rating a la canción");
    		return;
    	}
    	
    	//Validación campos vacíos
    	if(this.txfAlbum.getText().isEmpty() || this.txfTitulo.getText().isEmpty() ||
    			this.txfArtista.getText().isEmpty() || this.txfGenero.getText().isEmpty() ||
    			this.txfiTunes.getText().isEmpty() || this.txfSpotify.getText().isEmpty() ||
    			this.txaLetra.getText().isEmpty()) {
    		this.mostrarAlerta(AlertType.ERROR,"ERROR:Campos vacíos","Campos vacíos","Por favor rellene todos los campos");
    		return;
    	}
    	
    	Cancion nuevaCancion = new Cancion("default.png",this.txfAlbum.getText(), this.txfTitulo.getText(),
    			this.txfArtista.getText(), this.txfGenero.getText(), this.txfiTunes.getText(),
    			this.txfSpotify.getText(), this.cbxRating.getValue().intValue(), txaLetra.getText());
    	    	
    	this.listaCanciones.add(nuevaCancion); 	
    }

    @FXML
    public void eliminarCancion(ActionEvent event) {

    	if(tblCanciones.getSelectionModel().getSelectedItem()==null) {
    		this.mostrarAlerta(AlertType.ERROR, "ERROR: Cancion a eliminar", "Canción no seleccionada", "Por favor seleccione una canción de la tabla para eliminarla");
    	}
    	Cancion cancionEliminar = this.tblCanciones.getSelectionModel().getSelectedItem();
    	this.listaCanciones.remove(cancionEliminar);    	
    }

    @FXML
    public void eliminarSeleccionadas(ActionEvent event) {

    	Collection<Cancion> cancionesAEliminar = FXCollections.observableArrayList();
    	for(Cancion cancionEliminar: this.listaCanciones) {
    		if(cancionEliminar.getCheck().isSelected())
    			cancionesAEliminar.add(cancionEliminar);
    	}   	
    	
    	this.listaCanciones.removeAll(cancionesAEliminar);    	 	
    }    
    
    public void buscarCancion() {
    	//Envuelve la ObservableList en una FilteredList (inicialmente muestra todos los datos)
    	FilteredList<Cancion> filterdata = new FilteredList<>(listaCanciones, predicate-> true);
    	
    	this.txfBuscar.textProperty().addListener((observable, oldValue,  newValue)->{
    	//TextProperty obtiene El contenido textual de este TextInputControl.
    		filterdata.setPredicate(cancion -> {
    		//un Predicado define una condición que un objeto determinado debe cumplir 
            //Descripción de propiedad:
            //El predicado que coincidirá con los elementos que estarán en esta FilteredList. 
            //Los elementos que no coincidan con el predicado se filtrarán. 
            //El predicado nulo significa predicado "siempre verdadero", todos los elementos coincidirán.
    			
    			// Si el texto de filtro está vacío, muesta todas las canciones
    			if (newValue == null || newValue.isEmpty()) {
    				 return true;
    			}    			
    		
    		String lowerCaseFilter = newValue.toLowerCase();
    		
    		//validacion para  comparar los titulos de la columna titulo con el contenido del txfBuscar
    		if (cancion.getTitulo().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
    			//si El filtro coincide con el titulo.
    			return true;
    			
    		}
    		//validacion para comparar los artista de la columna artista con el contenido del txfBuscar
    		else if (cancion.getArtista().toLowerCase().indexOf(lowerCaseFilter) != -1) {
    			//si El filtro coincide con el artista.
    			return true;
    		}
    		//validacion para comparar el genero de la columna genero con el contenido del txfBuscar
    		else if(cancion.getGenero().toLowerCase().indexOf(lowerCaseFilter) != -1){
    			//si El filtro coincide con el genero.
    			return true;
    		}
    		return false;
    	
    		});
    	});
    	
    	/*
         SortedList: Envuelve una ObservableList y clasifica su contenido. 
         Todos los cambios en la ObservableList se propagan inmediatamente a la SortedList.
    	 */
    	
    	SortedList<Cancion> sortedata = new SortedList<>(filterdata);
    	
    	// 4. Vincula el comparador SortedList al comparador TableView.
    	sortedata.comparatorProperty().bind(tblCanciones.comparatorProperty());
    	//comparatorProperty: El comparador que denota el orden de esta SortedList.
    	
    	
    	// Agregua los datos ordenados (y filtrados) a la tabla. 
    	tblCanciones.setItems(sortedata);
    }
    
	public void mostrarAlerta(AlertType tipoAlerta,String tituloVentana,String tituloMensaje,String mensaje) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(tituloVentana);
		alerta.setHeaderText(tituloMensaje);
		alerta.setContentText(mensaje);
		alerta.showAndWait();		
	}
	
	public void configurarExploradorArchivos() {
		//Titulo de la ventana
		this.explorador.setTitle("Selección del cover del álbum");
		
		//Se crea un archivo para que se referencie la carpeta imagenes como directorio inicial
		File directorioFotos = new File(System.getProperty("user.dir"), "/src/imagenes");	
		this.explorador.setInitialDirectory(directorioFotos);
		
		//Se agregan los filtros para que la imagen sea únicamente JPG o PNG.
		this.explorador.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Archivos de imagen", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg*"),
				new FileChooser.ExtensionFilter("PNG", "*.png*")					
				);
	}

    @FXML
    public void initialize() {
    	
    	//Añadir los ratings al ComboBox
    	this.cbxRating.setItems(FXCollections.observableArrayList(1,2,3,4,5));

    	//Se pre añaden algunas canciones.
    	this.listaCanciones = FXCollections.observableArrayList(
    			new Cancion("metallicacover.jpg","Metallica","Sad but true","Metallica","Metal","https://music.apple.com/us/album/enter-sandman/579372950",
    	    			"https://open.spotify.com/album/2Kh43m04B1UkVcpcRa1Zug?highlight=spotify:track:1PhLYngBKbeDtdmDzCg3Pb",5,"Hey I'm your life\r\n" + 
    	    					"I'm the one who takes you there\r\n" + 
    	    					"Hey I'm your life\r\n" + 
    	    					"I'm the one who cares\r\n" + 
    	    					"They, they betray\r\n" + 
    	    					"I'm your only true friend now\r\n" + 
    	    					"They they'll betray\r\n" + 
    	    					"I'm forever there\r\n" + 
    	    					"I'm your dream, make you real\r\n" + 
    	    					"I'm your eyes when you must steal\r\n" + 
    	    					"I'm your pain when you can't feel\r\n" + 
    	    					"Sad but true\r\n" + 
    	    					"I'm your dream, mind astray\r\n" + 
    	    					"I'm your eyes while you're away\r\n" + 
    	    					"I'm your pain while you repay\r\n" + 
    	    					"You know it's sad but true\r\n" + 
    	    					"Sad but true\r\n" + 
    	    					"You you're my mask\r\n" + 
    	    					"You're my cover, my shelter\r\n" + 
    	    					"You you're my mask\r\n" + 
    	    					"You're the one who's blamed\r\n" + 
    	    					"Do do my work\r\n" + 
    	    					"Do my dirty work, scapegoat\r\n" + 
    	    					"Do do my deeds\r\n" + 
    	    					"For you're the one who's shamed\r\n" + 
    	    					"I'm your dream, make you real\r\n" + 
    	    					"I'm your eyes when you must steal\r\n" + 
    	    					"I'm your pain when you can't feel\r\n" + 
    	    					"Sad but true\r\n" + 
    	    					"I'm your dream, mind astray\r\n" + 
    	    					"I'm your eyes while you're away\r\n" + 
    	    					"I'm your pain while you repay\r\n" + 
    	    					"You know it's sad but true\r\n" + 
    	    					"Sad but true\r\n" + 
    	    					"I'm your dream, I'm your eyes\r\n" + 
    	    					"I'm your pain\r\n" + 
    	    					"I'm your dream, I'm your eyes\r\n" + 
    	    					"I'm your pain\r\n" + 
    	    					"You know is sad but true\r\n" + 
    	    					"Hate I'm your hate\r\n" + 
    	    					"I'm your hate when you want love\r\n" + 
    	    					"Pay Pay the price\r\n" + 
    	    					"Pay, for nothing's fair\r\n" + 
    	    					"Hey I'm your life\r\n" + 
    	    					"I'm the one who took you here\r\n" + 
    	    					"Hey I'm your life\r\n" + 
    	    					"And I no longer care\r\n" + 
    	    					"I'm your dream, make you real\r\n" + 
    	    					"I'm your eyes when you must steal\r\n" + 
    	    					"I'm your pain when you can't feel\r\n" + 
    	    					"Sad but true\r\n" + 
    	    					"I'm your truth, telling lies\r\n" + 
    	    					"I'm your reasoned alibis\r\n" + 
    	    					"I'm inside open your eyes\r\n" + 
    	    					"I'm you\r\n" + 
    	    					"Sad but true")    			
    			,new Cancion("rompecover.jpg","Tormeta Tropical","Rompe","Daddy Yankee","Reggaeton","https://music.apple.com/pe/music-video/rompe/162913564",
    	    			"https://open.spotify.com/track/4Xtlw8oXkIOvzV7crUBKeZ",4,"Nananana nananana nananana na\r\n" + 
    	    					"Nananana nananana nananana na\r\n" + 
    	    					"Nananana nananana nananana na\r\n" + 
    	    					"(You know)\r\n" + 
    	    					"Los capos están ready\r\n" + 
    	    					"Las mami's están ready\r\n" + 
    	    					"Y en la calle 'tamos ready\r\n" + 
    	    					"Yeah, yeah, andamos ready\r\n" + 
    	    					"Los barrios están ready\r\n" + 
    	    					"One, two, get ready\r\n" + 
    	    					"(Come on)\r\n" + 
    	    					"(Oh, oh, oh)\r\n" + 
    	    					"Rompe, rompe, rompe (bien guilla'o)\r\n" + 
    	    					"Rompe, rompe, rompe (ese cuerpo ella lo)\r\n" + 
    	    					"Rompe, rompe, rompe (bien guilla'o) (Are you ready?)\r\n" + 
    	    					"Rompe, rompe, rompe (break it down)\r\n" + 
    	    					"Rompe, rompe, rompe (the way she moves ella lo)\r\n" + 
    	    					"Rompe, rompe, rompe (break it down)\r\n" + 
    	    					"(Let's go)\r\n" + 
    	    					"My boo no se limita a la hora de romper su pum-pum\r\n" + 
    	    					"Con curva' más caliente' que el sur, right thru\r\n" + 
    	    					"Enséñame si tienes la actitud, mami\r\n" + 
    	    					"Dale, go, dale, go, dale, go-go\r\n" + 
    	    					"Tiempo llego el momento, baby, de perder el control\r\n" + 
    	    					"Trabájame ese cuerpo ma' que un shot de Winstrol\r\n" + 
    	    					"Sube ese temperamento, dame movimiento lento, lento ella lo\r\n" + 
    	    					"Rompe, rompe, rompe (bien guilla'o)\r\n" + 
    	    					"Rompe, rompe, rompe (ese cuerpo ella lo)\r\n" + 
    	    					"Rompe, rompe, rompe (bien guilla'o) (Are you ready?)\r\n" + 
    	    					"Rompe, rompe, rompe (break it down)\r\n" + 
    	    					"Rompe, rompe, rompe (the way she moves ella lo)\r\n" + 
    	    					"Rompe, rompe, rompe (break it down)\r\n" + 
    	    					"(Go, go, go, go)\r\n" + 
    	    					"Voy chillin', tranquilo that's right (go)\r\n" + 
    	    					"Buscando una gata que cae (go, go)\r\n" + 
    	    					"No escondas todo eso que traes\r\n" + 
    	    					"Yo baby, qué es la que hay\r\n" + 
    	    					"Voy chillin', tranquilo that's right (go)\r\n" + 
    	    					"Buscando una gata que cae (go, go)\r\n" + 
    	    					"No escondas todo eso que traes (go)\r\n" + 
    	    					"Qué pasa socio, qué es la que hay (what, what, what, what)\r\n" + 
    	    					"Pinche wey, pensaste que esto era un mamey\r\n" + 
    	    					"No vo'a dar break, deja ese guille de Scarface\r\n" + 
    	    					"Get out my way, usted no vende ni en eBay\r\n" + 
    	    					"No das pa' na', conmigo ta' Frito-Lay\r\n" + 
    	    					"Chequea el swing\r\n" + 
    	    					"Que se le pega a to'a las nenas mas que un g-string\r\n" + 
    	    					"Yo soy la pesadilla de todos los dream team\r\n" + 
    	    					"Ya se te acabo el magazine\r\n" + 
    	    					"Conmigo no te guilles, pa', de listerín (Daddy te)\r\n" + 
    	    					"Rompe, rompe, rompe (bien guilla'o)\r\n" + 
    	    					"Rompe, rompe, rompe (ese cuerpo ella lo)\r\n" + 
    	    					"Rompe, rompe, rompe (bien guilla'o) (Are you ready?)\r\n" + 
    	    					"Rompe, rompe, rompe (break it down)\r\n" + 
    	    					"Rompe, rompe, rompe (the way she moves ella lo)\r\n" + 
    	    					"Rompe, rompe, rompe (break it down)\r\n" + 
    	    					"(Let's go)\r\n" + 
    	    					"Nananana nananana nananana na\r\n" + 
    	    					"Nananana nananana nananana na\r\n" + 
    	    					"Nananana nananana nananana na\r\n" + 
    	    					"(You know)\r\n" + 
    	    					"Oh, oh, oh, en directo\r\n" + 
    	    					"Oh, oh, oh, Daddy Yankee yo\r\n" + 
    	    					"Con lo' Jedys, Monserrate y Dj Urba\r\n" + 
    	    					"Offic- (ha ha) it's official\r\n" + 
    	    					"Daddy Yankee, Cartel Records, En Directo\r\n" + 
    	    					"Who's This?")
    			,new Cancion("dualipacover.jpg","Dua Lipa Album","Physical","Dua Lipa","Pop","https://music.apple.com/us/album/physical/1495799403?i=1495799737&l=es",
    	    			"https://open.spotify.com/track/3AzjcOeAmA57TIOr9zF1ZW?si=GJYCUgcmSOyhtj16YbfByA",3,"Lorem ipsum")
    			,new Cancion("jamesbrowncover.jpg","Out of Sight","I Feel Good","James Brown","Funk","https://music.apple.com/us/album/i-got-you-i-feel-good/1469584155?i=1469584226",
    	    			"https://open.spotify.com/track/5haXbSJqjjM0TCJ5XkfEaC",5,"Lorem ipsum")
    			,new Cancion("hectorlavoecover.jpg","Comedia","El Cantante","Héctor Lavoe","Salsa","https://music.apple.com/us/album/el-cantante/1466317393?i=1466317542",
    	    			"https://open.spotify.com/track/5Uve0jm1RgxKWzdSvncBDO",5,"Lorem ipsum")
    			,new Cancion("babysharkcover.jpg","Pinkfong Animal Songs","Baby Shark","Pinkfong","Música infantil","https://music.apple.com/us/album/baby-shark/1264976423?i=1264976429",
    	    			"https://open.spotify.com/track/5ygDXis42ncn6kYG14lEVG",1,"Odio esta canción")    			
    			);
    	
    	//Se configura la TableView al inicializar el Controlador.
    	this.configurarTblCanciones();    	
    	
    	this.lblBlblioMusica.setId("titulo");
    	this.vbxRoot.setId("root");
    	
    	this.buscarCancion();
    }
}
