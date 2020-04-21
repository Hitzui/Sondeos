package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.dao.DaoSuelos;
import com.dysconcsa.sondeos.model.*;
import com.dysconcsa.sondeos.util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@FXMLController("/view/FormValores.fxml")
public class FormValoresController {

    private File fileExcel;
    private static String lastVisitedDirectory = System.getProperty("user.dir");
    private ProfundidadSondeo profundidadSondeo;
    private final double tabWidth = 90.0;
    private final ObservableList<String> trepanoList = FXCollections.observableArrayList();
    private static int lastSelectedTabIndex = 0;
    private final ObservableList<TrepanoProperty> trepanoProperties = FXCollections.observableArrayList();
    private final ObservableList<SuelosProperty> suelosProperties = FXCollections.observableArrayList();
    private final ObservableList<IndexedColors> itemsColorPoperties = FXCollections.observableArrayList();
    private final ObservableList<FillPatternType> patternTypesProperties = FXCollections.observableArrayList();
    private final ObservableList<DatosCampoProperty> datosCampoProperties = FXCollections.observableArrayList();
    private final ObservableList<ClasificacionSucsProperty> clasificacionSucsProperties = FXCollections.observableArrayList();
    private final ObservableList<HumedadProperty> humedadProperties = FXCollections.observableArrayList();
    private final ObservableList<AdemeProperty> ademeProperties = FXCollections.observableArrayList();

    @FXML
    private JFXTabPane tabContainer;

    @FXML
    private Tab tabValores;

    @FXML
    private Tab tabEstratigrafia;

    @FXML
    public Tab tabCliente;

    @FXML
    private Tab tabGuardar;

    @FXML
    private Tab tabGenerar;

    @FXML
    private Tab tabTipoSuelos;

    @FXML
    private AnchorPane anchorPaneCliente;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane anchorPaneTipoSuelos;

    @FXML
    private TableView<DatosCampoProperty> tableDatos;

    @FXML
    private TableColumn<DatosCampoProperty, Double> colProfundidadInicial;

    @FXML
    private TableColumn<DatosCampoProperty, Double> colProfundidadFinal;

    @FXML
    private TableColumn<DatosCampoProperty, Integer> colRecobro;

    @FXML
    private TableColumn<DatosCampoProperty, Integer> colGolpe1;

    @FXML
    private TableColumn<DatosCampoProperty, Integer> colGolpe2;

    @FXML
    private TableColumn<DatosCampoProperty, Integer> colGolpe3;

    @FXML
    private TableView<ClasificacionSucsProperty> tableClasificacion;

    @FXML
    private TableColumn<ClasificacionSucsProperty, Double> colProfundidadSucs;

    @FXML
    private TableColumn<ClasificacionSucsProperty, Integer> colLimiteLiquido;

    @FXML
    private TableColumn<ClasificacionSucsProperty, Integer> colIndicePlasticidad;

    @FXML
    private TableColumn<ClasificacionSucsProperty, SuelosProperty> colTipoSuelo;

    @FXML
    private TableColumn<ClasificacionSucsProperty, String> colDescipcion;
    @FXML
    private TableColumn<ClasificacionSucsProperty, IndexedColors> colColor;
    @FXML
    private TableColumn<ClasificacionSucsProperty, FillPatternType> colPattern;

    @FXML
    private TableView<HumedadProperty> tableHumedad;

    @FXML
    private TableColumn<HumedadProperty, Double> colProfundidadInicialHumedad;

    @FXML
    private TableColumn<HumedadProperty, Double> colProfundidadFinalHumedad;

    @FXML
    private TableColumn<HumedadProperty, Double> colContenidoHumedad;

    @FXML
    private TableView<AdemeProperty> tableAdeme;

    @FXML
    private TableColumn<AdemeProperty, Double> colProfundidadAdeme;

    @FXML
    private TableColumn<AdemeProperty, String> colAdemeDescripion;

    @FXML
    private TableView<TrepanoProperty> tableTrepano;

    @FXML
    private TableColumn<TrepanoProperty, Double> colProfundidadTrepano;

    @FXML
    private TableColumn<TrepanoProperty, String> colTrepano;

    @FXML
    @ActionTrigger("action_btnEliminar")
    private JFXButton btnEliminar;

    @FXML
    @ActionTrigger("action_btnLimpiarDatos")
    private JFXButton btnLimpiarDatos;

    @FXML
    @ActionTrigger("action_btnCargarDatos")
    private JFXButton btnCargarDatos;

    @FXML
    @ActionTrigger("action_btnInsertar")
    private JFXButton btnInsertar;

    @FXML
    @ActionTrigger("action_btnConfig")
    private JFXButton btnConfig;

    @FXML
    @ActionTrigger("action_btnDatosSondeos")
    private JFXButton btnDatosSondeos;
    public static File file = null;

    @ActionMethod("action_btnConfig")
    public void action_btnConfig() {
        // Load the fxml file and create a new stage for the popup dialog.
        Flow flow = new Flow(ConfigurationController.class);
        FlowHandler flowHandler = flow.createHandler();
        try {
            StackPane pane = flowHandler.start();
            Utility utility = new Utility();
            Stage dialogStage = utility.showDialogStage((Stage) btnConfig.getScene().getWindow(), pane, "Establecer configuracion");
            ConfigurationController configurationController = (ConfigurationController) flowHandler.getCurrentView().getViewContext().getController();
            configurationController.setStage(dialogStage);
            dialogStage.showAndWait();
            /*if (!configurationController.isCancel) {
                imagenEmpresa.setImage(new Image(String.valueOf(new File(configurationController.getPathImagen()).toURI())));
            }*/
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    @ActionMethod("action_btnDatosSondeos")
    public void action_btnDatosSondeos() {
        if (showDatosSondeosDialog(profundidadSondeo)) {
            //System.out.println("Sondeo numero: " + profundidadSondeo.getSondeoNumero());
        }
    }

    @ActionMethod("action_btnEliminar")
    public void action_btnEliminar() {
        if (datosCampoProperties.size() <= 1) {
            return;
        }
        int index = tableDatos.getSelectionModel().getSelectedIndex();
        DatosCampoProperty datosCampoProperty = tableDatos.getSelectionModel().getSelectedItem();
        datosCampoProperties.remove(datosCampoProperty);
        /*for (int i = index; i < datosCampoProperties.size(); i++) {
            DatosCampoProperty datos = datosCampoProperties.get(i);
            datos.setProfundidadInicial(datos.getProfundidadInicial() - 1.5);
            datos.setProfundidadFinal(datos.getProfundidadFinal() - 1.5);
        }*/
    }

    @ActionMethod("action_btnInsertar")
    public void action_btnInsertar() {
        DatosCampoProperty datosCampoProperty = tableDatos.getSelectionModel().getSelectedItem();
        int index = tableDatos.getSelectionModel().getSelectedIndex();
        index += 1;
        Double profInicial = datosCampoProperty.getProfundidadInicial() + 1.5;
        Double profFinal = datosCampoProperty.getProfundidadFinal() + 1.5;
        DatosCampoProperty addDatos = new DatosCampoProperty(profInicial, profFinal,
                datosCampoProperty.getRecobro(), datosCampoProperty.getGolpe1(), datosCampoProperty.getGolpe2(),
                datosCampoProperty.getGolpe3());
        for (int i = index; i < datosCampoProperties.size(); i++) {
            DatosCampoProperty datos = datosCampoProperties.get(i);
            datos.setProfundidadInicial(datos.getProfundidadInicial() + 1.5);
            datos.setProfundidadFinal(datos.getProfundidadFinal() + 1.5);
        }
        tableDatos.getItems().add(index, addDatos);
    }

    @ActionMethod("action_btnLimpiarDatos")
    public void action_btnLimpiarDatos() {
        try {
            trepanoList.addAll("AW-N EX", "AX", "BX", "NX", "T", "D", "Do", "CP", "CN", "PD");
            datosCampoProperties.clear();
            datosCampoProperties.add(new DatosCampoProperty());
            clasificacionSucsProperties.clear();
            if (suelosProperties.size() <= 0) {
                clasificacionSucsProperties.add(new ClasificacionSucsProperty(0.0, 0, 0, 0, "", IndexedColors.WHITE, FillPatternType.NO_FILL));
            } else {
                clasificacionSucsProperties.add(new ClasificacionSucsProperty(0.0, 0, 0, suelosProperties.get(0).getID(), suelosProperties.get(0).getNombre(), IndexedColors.WHITE, FillPatternType.NO_FILL));
            }
            humedadProperties.clear();
            humedadProperties.add(new HumedadProperty());
            ademeProperties.clear();
            ademeProperties.add(new AdemeProperty());
            trepanoProperties.clear();
            trepanoProperties.add(new TrepanoProperty());
            file = null;
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    @ActionMethod("action_btnCargarDatos")
    public void action_btnCargarDatos() {
        try {
            //ArchivoXml archivoXml = new ArchivoXml();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cargar daatos");
            fileChooser.setInitialDirectory(new File(lastVisitedDirectory));
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sondeos files (*.sxml)", "*.sxml");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                loadSxml(file);
                ((Stage) btnCargarDatos.getScene().getWindow()).setTitle("Aplicacion de Sondeos - " + file.getName());
                lastVisitedDirectory = (file != null) ? file.getParent() : System.getProperty("user.home");
            }
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    @FXML
    public void initialize() {
        try {
            loadSxml(file);
            loadColors();
            loadPattern();
            listSuelosProperties();
            if (trepanoProperties.size() <= 0) {
                trepanoProperties.add(new TrepanoProperty(0.0, ""));
            }
            if (datosCampoProperties.size() <= 0) {
                datosCampoProperties.add(new DatosCampoProperty(0d, 1.5, 0, 0, 0, 0));
            }
            if (clasificacionSucsProperties.size() <= 0) {
                if (suelosProperties.size() <= 0) {
                    clasificacionSucsProperties.add(new ClasificacionSucsProperty(0.0, 0, 0, 0, "", IndexedColors.WHITE, FillPatternType.NO_FILL));
                } else {
                    clasificacionSucsProperties.add(new ClasificacionSucsProperty(0.0, 0, 0, suelosProperties.get(0).getID(), suelosProperties.get(0).getNombre(), IndexedColors.WHITE, FillPatternType.NO_FILL));
                }
            }
            if (humedadProperties.size() <= 0) {
                humedadProperties.add(new HumedadProperty(0.0, 1.5, 0.0));
            }
            if (ademeProperties.size() <= 0) {
                ademeProperties.add(new AdemeProperty(0.0, "Se Ademo hasta: 0.0"));
            }
            tableDatos.setItems(datosCampoProperties);
            tableClasificacion.setItems(clasificacionSucsProperties);
            tableHumedad.setItems(humedadProperties);
            tableAdeme.setItems(ademeProperties);
            tableTrepano.setItems(trepanoProperties);
            setTableEditTrepano();
            setupProfundidadColumn();
            setTableEditableDatos();
            setTableEditableHumedad();
            setTableEditableAdeme();
            setupTrepanoColumn();
            setupGolpesColumn();
            setupHumedadColumn();
            setupClasificacionColumn();
            setupAdemeColumn();
            configureView();

        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    private void prepararDatos() {
        double prof = 0.0;
        ObservableList<DatosCampoProperty> agregar = FXCollections.observableArrayList();
        ObservableList<DatosCampoProperty> remover = FXCollections.observableArrayList();
        for (DatosCampoProperty datos : datosCampoProperties) {
            if (prof != 0) {
                if (prof != datos.getProfundidadFinal()) {
                    double aux = datos.getProfundidadInicial();
                    while (aux < datos.getProfundidadFinal()) {
                        agregar.add(new DatosCampoProperty(aux, aux + 1.5, datos.getRecobro(), datos.getGolpe1(), datos.getGolpe2(), datos.getGolpe3()));
                        aux += 1.5;
                    }
                    remover.add(datos);
                }
            }
            prof = datos.getProfundidadFinal() + 1.5;
        }
        datosCampoProperties.removeAll(remover);
        datosCampoProperties.addAll(agregar);
        datosCampoProperties.sort((x, y) -> {
            final Integer value = (int) (x.getProfundidadInicial() + 1.5) * 2;
            final Integer value2 = (int) (y.getProfundidadFinal()) * 2;
            return value.compareTo(value2);
        });
        tableDatos.refresh();
    }

    private void configureView() {
        tabContainer.setTabMinWidth(tabWidth);
        tabContainer.setTabMaxWidth(tabWidth);
        tabContainer.setTabMinHeight(tabWidth);
        tabContainer.setTabMaxHeight(tabWidth);
        tabContainer.setRotateGraphic(true);

        EventHandler<Event> replaceBackgroundColorHandler = event -> {
            lastSelectedTabIndex = tabContainer.getSelectionModel().getSelectedIndex();
            Tab currentTab = (Tab) event.getTarget();
            if (currentTab.isSelected()) {
                currentTab.setStyle("-fx-background-color: -fx-focus-color;");
            } else {
                currentTab.setStyle("-fx-background-color: -fx-accent;");
            }
        };
        EventHandler<Event> saveFile = event -> {
            Tab currentTab = (Tab) event.getTarget();
            if (currentTab.isSelected()) {
                tabContainer.getSelectionModel().select(lastSelectedTabIndex);
                try {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sondeos Files (*.sxml)",
                            "*.sxml");
                    fileChooser.getExtensionFilters().add(extFilter);
                    Stage stage = (Stage) anchorPane.getScene().getWindow();
                    // Show save file dialog
                    if (file == null) {
                        file = fileChooser.showSaveDialog(stage);
                    }
                    if (file != null) {
                        ArchivoXml archivoXml = new ArchivoXml();
                        archivoXml.setDocument();
                        archivoXml.prepararElementosDatos(datosCampoProperties, anchorPane);
                        archivoXml.prepararElementosClasificacion(clasificacionSucsProperties, anchorPane);
                        archivoXml.prepararElementHumedad(humedadProperties, anchorPane);
                        archivoXml.prepararElementosProfundidad(profundidadSondeo);
                        archivoXml.prepararElementosAdeme(ademeProperties, anchorPane);
                        archivoXml.prepararElementosTrepano(trepanoProperties, anchorPane);
                        archivoXml.guardarArchivoXml(file);
                        Utility.dialog("Guardar", "Guardar Archivo sondeo", "Se ha guardado el archivo de forma correcta.");
                    }
                } catch (NumberFormatException ex) {
                    AlertError.showAlert(ex);
                }
            }
        };
        EventHandler<Event> generarGrafico = event -> {
            Tab currentTab = (Tab) event.getTarget();
            if (currentTab.isSelected()) {
                tabContainer.getSelectionModel().select(lastSelectedTabIndex);
                try {
                    if (Utility.idcliente == 0) {
                        Utility.dialog("Error", "", "No se ha especificado un cliente, por favor especifique un cliente para continuar.");
                        tabContainer.getSelectionModel().select(tabCliente);
                        return;
                    }
                    ArchivoExcel archivoExcel = new ArchivoExcel();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Generar Gráfico");
                    fileChooser.setInitialDirectory(new File(lastVisitedDirectory));
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
                    fileChooser.getExtensionFilters().addAll(extFilter);
                    Stage stage = (Stage) anchorPane.getScene().getWindow();
                    // Show save file dialog
                    fileExcel = fileChooser.showSaveDialog(stage);
                    if (fileExcel != null) {
                        prepararDatos();
                        archivoExcel.setClasificacionSucsProperties(clasificacionSucsProperties);
                        archivoExcel.setDatosCampoProperties(datosCampoProperties);
                        archivoExcel.setHumedadProperties(humedadProperties);
                        List<ProfundidadSondeo> profundidadSondeos = new ArrayList<>();
                        profundidadSondeos.add(profundidadSondeo);
                        archivoExcel.setProfundidadSondeos(profundidadSondeos);
                        archivoExcel.setTrepanoProperties(trepanoProperties);
                        archivoExcel.setTabPane(tabContainer);
                        archivoExcel.setTabToUse(tabCliente);
                        archivoExcel.crearArchivo(anchorPane, fileExcel, Utility.idcliente, profundidadSondeo, ademeProperties);
                    }
                    lastVisitedDirectory = (file != null) ? fileExcel.getParent() : System.getProperty("user.home");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //Utility.dialog("Error", "Error al generar", "No se pudo generar el archivo de sondeo, intente nuevamente." + ex.getMessage());
                }
            }
        };
        configureTab(tabValores, "Valores de\nCampo", "/image/valores.png", replaceBackgroundColorHandler);
        configureTab(tabEstratigrafia, "Estratigrafia", "/image/estratigrafia.png", replaceBackgroundColorHandler);
        Flow flowCliente = new Flow(ClienteController.class);
        try {
            FlowHandler flowHandler = flowCliente.createHandler();
            StackPane stackPane = flowHandler.start();
            ClienteController clienteController = (ClienteController) flowHandler.getCurrentView().getViewContext()
                    .getController();
            clienteController.habilitarBtnSeleccionar();
            AnchorPane.setTopAnchor(stackPane, 0.0);
            AnchorPane.setBottomAnchor(stackPane, 0.0);
            AnchorPane.setRightAnchor(stackPane, 0.0);
            AnchorPane.setLeftAnchor(stackPane, 0.0);
            anchorPaneCliente.getChildren().clear();
            anchorPaneCliente.getChildren().add(stackPane);
            configureTab(tabCliente, "Cliente", "/image/cliente.png", replaceBackgroundColorHandler);
        } catch (FlowException e) {
            e.printStackTrace();
        }
        Flow flowTipoSuelos = new Flow(TipoSueloController.class);
        try {
            FlowHandler flowHandler = flowTipoSuelos.createHandler();
            StackPane stackPane = flowHandler.start();
            AnchorPane.setTopAnchor(stackPane, 0.0);
            AnchorPane.setBottomAnchor(stackPane, 0.0);
            AnchorPane.setRightAnchor(stackPane, 0.0);
            AnchorPane.setLeftAnchor(stackPane, 0.0);
            anchorPaneTipoSuelos.getChildren().clear();
            anchorPaneTipoSuelos.getChildren().add(stackPane);
            configureTab(tabTipoSuelos, "Tipos de Suelos", "/image/suelo.png", replaceBackgroundColorHandler);
        } catch (FlowException ex) {
            ex.printStackTrace();
        }
        configureTab(tabGuardar, "Guardar", "/image/save01.png", saveFile);
        configureTab(tabGenerar, "Generar\nArchivo", "/image/generar.png", generarGrafico);
    }

    private void configureTab(Tab tab, String title, String iconPath,
                              EventHandler<Event> onSelectionChangedEvent) {
        double imageWidth = 40.0;

        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(imageWidth);
        imageView.setFitWidth(imageWidth);

        Label label = new Label(title);
        label.setMaxWidth(tabWidth - 20);
        label.setPadding(new Insets(5, 0, 0, 0));
        label.setStyle("-fx-text-fill: White; -fx-font-size: 9pt; -fx-font-weight: normal;");
        label.setTextAlignment(TextAlignment.CENTER);

        BorderPane tabPane = new BorderPane();
        tabPane.setRotate(90.0);
        tabPane.setMaxWidth(tabWidth);
        tabPane.setCenter(imageView);
        tabPane.setBottom(label);

        tab.setText("");
        tab.setGraphic(tabPane);

        tab.setOnSelectionChanged(onSelectionChangedEvent);
    }

    private void listSuelosProperties() {
        DaoSuelos daoSuelos = new DaoSuelos();
        suelosProperties.clear();
        try {
            suelosProperties.addAll(daoSuelos.findAll());
        } catch (SQLException e) {
            AlertError.showAlert(e);
        }
    }

    private void setupAdemeColumn() {
        colProfundidadAdeme.setCellValueFactory(value -> value.getValue().profundidadProperty().asObject());
        colProfundidadAdeme.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colProfundidadAdeme.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProfundidad(value);
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setDescripcion("Se ademo hasta: " + value);
        });
        colAdemeDescripion.setCellValueFactory(value -> value.getValue().descripcionProperty());
    }

    private void setupClasificacionColumn() {
        colProfundidadSucs.setCellValueFactory(value -> value.getValue().profundidadProperty().asObject());
        colProfundidadSucs.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colProfundidadSucs.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProfundidad(value);
            tableClasificacion.getSelectionModel().select(event.getTablePosition().getRow(), colLimiteLiquido);
            event.consume();
            //tableClasificacion.refresh();
        });
        colLimiteLiquido.setCellValueFactory(value -> value.getValue().limiteLiquidoProperty().asObject());
        colLimiteLiquido.setCellFactory(EditCell.forTableColumn(new IntegerStringConverter()));
        colLimiteLiquido.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLimiteLiquido(value);
            tableClasificacion.getSelectionModel().select(event.getTablePosition().getRow(), colIndicePlasticidad);
            event.consume();
        });
        colIndicePlasticidad.setCellValueFactory(value -> value.getValue().indicePlasticidadProperty().asObject());
        colIndicePlasticidad.setCellFactory(EditCell.forTableColumn(new IntegerStringConverter()));
        colIndicePlasticidad.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIndicePlasticidad(value);
            tableClasificacion.getSelectionModel().select(event.getTablePosition().getRow(), colTipoSuelo);
            event.consume();
        });
        colTipoSuelo.setCellFactory(param -> {
            ComboBoxTableCell<ClasificacionSucsProperty, SuelosProperty> comboBoxTableCell = new ComboBoxTableCell<>();
            comboBoxTableCell.getItems().addAll(suelosProperties);
            comboBoxTableCell.setComboBoxEditable(true);
            AutoCompleteComboBoxListener.autoCompleteComboBoxPlus(comboBoxTableCell.getComboBox(), (typedText, itemToCompare) -> itemToCompare.getNombre().toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.getNombre().equals(typedText));
            comboBoxTableCell.updateSelected(true);
            return comboBoxTableCell;
        });
        colTipoSuelo.setCellValueFactory(value -> {
            DaoSuelos daoSuelos = new DaoSuelos();
            SuelosProperty suelosProperty = daoSuelos.findById(value.getValue().getTipoSuelo());
            return new SimpleObjectProperty<>(suelosProperty);
        });
        colTipoSuelo.setOnEditCommit(event -> {
            DaoSuelos daoSuelos = new DaoSuelos();
            ClasificacionSucsProperty clasificacionSucsProperty = event.getRowValue();
            SuelosProperty suelos = daoSuelos.findBYSimbolo(String.valueOf(event.getNewValue()));
            //TablePosition<ClasificacionSucsProperty, ?> pos = tableClasificacion.getFocusModel().getFocusedCell();
            //clasificacionSucsProperties.get(pos.getRow()).setDescripcion(suelos.getNombre().toUpperCase());
            clasificacionSucsProperty.setTipoSuelo(suelos.getID());
            event.consume();
        });
        colDescipcion.setCellValueFactory(value -> value.getValue().descripcionProperty());
        colDescipcion.setCellFactory(EditCell.forTableColumn(new DefaultStringConverter()));
        colDescipcion.setOnEditCommit(event -> {
            final String value = !event.getNewValue().isEmpty() ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDescripcion(value.toUpperCase());
            event.consume();
        });
        colColor.setCellFactory(param -> comboBoxColors());
        colColor.setCellValueFactory(value -> value.getValue().colorProperty());
        colPattern.setCellFactory(param -> comboBoxPattern());
        colPattern.setCellValueFactory(value -> value.getValue().patternProperty());
        setTableEditableClasificacionSucs();
    }

    private void setupProfundidadColumn() {
        colProfundidadInicial.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colProfundidadInicial.setCellValueFactory(value -> value.getValue().profundidadInicialProperty().asObject());
        colProfundidadInicial.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProfundidadInicial(value);
            tableDatos.getSelectionModel().select(event.getTablePosition().getRow(), colProfundidadFinal);
            event.consume();
        });
        colProfundidadFinal.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colProfundidadFinal.setCellValueFactory(value -> value.getValue().profundidadFinalProperty().asObject());
        colProfundidadFinal.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProfundidadFinal(value);
            event.consume();
        });
    }

    private void setupGolpesColumn() {
        colRecobro.setCellFactory(EditCell.forTableColumn(new IntegerStringConverter()));
        colRecobro.setCellValueFactory(value -> value.getValue().recobroProperty().asObject());
        colRecobro.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setRecobro(value);
            tableDatos.getSelectionModel().select(event.getTablePosition().getRow(), colGolpe1);
            event.consume();
        });

        colGolpe1.setCellFactory(EditCell.forTableColumn(new IntegerStringConverter()));
        colGolpe1.setCellValueFactory(value -> value.getValue().golpe1Property().asObject());
        colGolpe1.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setGolpe1(value);
            tableDatos.getSelectionModel().select(event.getTablePosition().getRow(), colGolpe2);
            event.consume();
        });
        colGolpe2.setCellFactory(EditCell.forTableColumn(new IntegerStringConverter()));
        colGolpe2.setCellValueFactory(value -> value.getValue().golpe2Property().asObject());
        colGolpe2.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setGolpe2(value);
            tableDatos.getSelectionModel().select(event.getTablePosition().getRow(), colGolpe3);
            event.consume();
        });
        colGolpe3.setCellFactory(EditCell.forTableColumn(new IntegerStringConverter()));
        colGolpe3.setCellValueFactory(value -> value.getValue().golpe3Property().asObject());
        colGolpe3.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setGolpe3(value);
            event.consume();
        });
    }

    private void setupHumedadColumn() {
        colProfundidadInicialHumedad.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colProfundidadInicialHumedad
                .setCellValueFactory(value -> value.getValue().profundidadInicialProperty().asObject());
        colProfundidadInicialHumedad.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProfundidadInicial(value);
            tableHumedad.getSelectionModel().select(event.getTablePosition().getRow(), colProfundidadFinalHumedad);
            event.consume();
        });
        colProfundidadFinalHumedad.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colProfundidadFinalHumedad.setCellValueFactory(value -> value.getValue().profundidadFinalProperty().asObject());
        colProfundidadFinalHumedad.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProfundidadFinal(value);
            tableHumedad.getSelectionModel().select(event.getTablePosition().getRow(), colContenidoHumedad);
            event.consume();
        });
        colContenidoHumedad.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colContenidoHumedad.setCellValueFactory(value -> value.getValue().humedadProperty().asObject());
        colContenidoHumedad.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setHumedad(value);
            event.consume();
        });
    }

    private void setupTrepanoColumn() {
        ObservableList<String> trepanoList = FXCollections.observableArrayList();
        trepanoList.addAll("AW - Nw Ex", "AX", "BX", "NX", "T", "D", "Do", "CP", "CN", "PD");
        colProfundidadTrepano.setCellFactory(EditCell.forTableColumn(new DoubleStringConverter()));
        colProfundidadTrepano.setCellValueFactory(value -> value.getValue().profundidadProperty().asObject());
        colProfundidadTrepano.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProfundidad(value);
            tableTrepano.getSelectionModel().select(event.getTablePosition().getRow(), colTrepano);
            event.consume();
        });
        colTrepano.setCellFactory(cell -> {
            ComboBoxTableCell<TrepanoProperty, String> comboBoxTableCell = new ComboBoxTableCell<>();
            comboBoxTableCell.getItems().addAll(trepanoList);
            comboBoxTableCell.setComboBoxEditable(true);
            AutoCompleteComboBoxListener.autoCompleteComboBoxPlus(comboBoxTableCell.getComboBox(), (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.equals(typedText));
            return comboBoxTableCell;
        });
        colTrepano.setCellValueFactory(value -> value.getValue().trepanoProperty());
        colTrepano.setOnEditCommit(event -> {
            try {
                final String value = !event.getNewValue().equals(event.getOldValue()) ? event.getNewValue() : event.getOldValue();
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setTrepano(value.toUpperCase());
                event.consume();
            } catch (Exception ex) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setTrepano(event.getOldValue().toUpperCase());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setTableEditTrepano() {
        tableTrepano.setEditable(true);
        tableTrepano.getSelectionModel().cellSelectionEnabledProperty().set(true);
        tableTrepano.setOnKeyPressed(event -> {
            TablePosition<TrepanoProperty, ?> pos = tableTrepano.getFocusModel().getFocusedCell();
            if (event.getCode().isDigitKey() || event.getCode().isLetterKey()) {
                editFocuedCellTrepano();
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                if (pos.getColumn() == 1) {
                    if (pos.getRow() == (tableTrepano.getItems().size() - 1)) {
                        trepanoProperties.add(new TrepanoProperty(0.0, ""));
                        event.consume();
                    }
                    tableTrepano.getSelectionModel().selectNext();
                } else {
                    tableTrepano.getSelectionModel().selectRightCell();
                }
            } else if (event.getCode() == KeyCode.DELETE) {
                if (tableTrepano.getItems().size() > 1) {
                    trepanoProperties.remove(tableTrepano.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setTableEditableAdeme() {
        tableAdeme.setEditable(true);
        tableAdeme.getSelectionModel().cellSelectionEnabledProperty().set(true);
        tableAdeme.setOnKeyPressed(event -> {
            TablePosition<AdemeProperty, ?> pos = tableAdeme.getFocusModel().getFocusedCell();
            if (event.getCode().isDigitKey()) {
                editFocuedCellAdeme();
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                if (pos.getColumn() == 1) {
                    if (pos.getRow() == (tableAdeme.getItems().size() - 1)) {
                        ademeProperties.add(new AdemeProperty(0.0, "Se ademo hasta 0.0"));
                        event.consume();
                    }
                    tableAdeme.getSelectionModel().selectNext();
                } else {
                    tableAdeme.getSelectionModel().selectNext();
                }
            } else if (event.getCode() == KeyCode.DELETE) {
                if (tableAdeme.getItems().size() > 1) {
                    ademeProperties.remove(tableAdeme.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setTableEditableHumedad() {
        tableHumedad.setEditable(true);
        tableHumedad.getSelectionModel().cellSelectionEnabledProperty().set(true);
        tableHumedad.setOnKeyPressed(event -> {
            TablePosition<HumedadProperty, ?> pos = tableHumedad.getFocusModel().getFocusedCell();
            if (event.getCode().isDigitKey()) {
                editFocuedCellHumedad();
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                if (pos.getColumn() == 2) {
                    if (pos.getRow() == (tableHumedad.getItems().size() - 1)) {
                        humedadProperties.add(new HumedadProperty(0.0, 0.0, 0.0));
                        event.consume();
                    }
                    tableHumedad.getSelectionModel().selectNext();
                } else {
                    tableHumedad.getSelectionModel().selectNext();
                }
            } else if (event.getCode() == KeyCode.DELETE) {
                if (tableHumedad.getItems().size() > 1) {
                    humedadProperties.remove(tableHumedad.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setTableEditableClasificacionSucs() {
        tableClasificacion.setEditable(true);
        tableClasificacion.getSelectionModel().cellSelectionEnabledProperty().set(true);
        tableClasificacion.setOnKeyPressed(event -> {
            TablePosition<ClasificacionSucsProperty, ?> pos = tableClasificacion.getFocusModel().getFocusedCell();
            if (event.getCode().isDigitKey()) {
                editFocuedCellClasificacion();
            } else if (event.getCode() == KeyCode.DELETE) {
                if (clasificacionSucsProperties.size() > 1) {
                    clasificacionSucsProperties.remove(tableClasificacion.getSelectionModel().getSelectedItem());
                }
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                if (pos.getColumn() == 4) {
                    if (pos.getRow() == (tableClasificacion.getItems().size() - 1)) {
                        clasificacionSucsProperties.add(new ClasificacionSucsProperty(0.0, 0, 0,
                                suelosProperties.get(0).getID(), suelosProperties.get(0).getNombre(), IndexedColors.WHITE, FillPatternType.NO_FILL));
                        tableClasificacion.getSelectionModel().select(pos.getRow() + 1, colProfundidadSucs);
                        event.consume();
                    } else {
                        tableClasificacion.getSelectionModel().selectNext();
                        event.consume();
                    }
                } else {
                    tableClasificacion.getSelectionModel().selectNext();
                    event.consume();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setTableEditableDatos() {
        tableDatos.setEditable(true);
        tableDatos.getSelectionModel().cellSelectionEnabledProperty().set(true);
        tableDatos.setOnKeyPressed(event -> {
            TablePosition<DatosCampoProperty, ?> pos = tableDatos.getFocusModel().getFocusedCell();
            if (event.getCode().isDigitKey()) {
                editFocusedCellDatos();
            } else if (event.getCode() == KeyCode.DELETE) {
                action_btnEliminar();
            } else if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER) {
                if (pos.getColumn() == 5) {
                    if (pos.getRow() == (tableDatos.getItems().size() - 1)) {
                        DatosCampoProperty datosCampoProperty = tableDatos.getItems().get(pos.getRow());
                        Double profIni = datosCampoProperty.getProfundidadFinal();
                        Double profFinal = datosCampoProperty.getProfundidadFinal() + 1.5;
                        datosCampoProperties.add(new DatosCampoProperty(profIni, profFinal, 0, 0, 0, 0));
                        tableDatos.getSelectionModel().select(pos.getRow() + 1, colRecobro);
                        event.consume();
                    } else {
                        tableDatos.getSelectionModel().selectNext();
                    }
                } else {
                    tableDatos.getSelectionModel().selectNext();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void editFocuedCellTrepano() {
        final TablePosition<TrepanoProperty, ?> focusedCell = tableTrepano.focusModelProperty().get().focusedCellProperty()
                .get();
        tableTrepano.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private void editFocuedCellAdeme() {
        final TablePosition<AdemeProperty, ?> focusedCell = tableAdeme.focusModelProperty().get().focusedCellProperty()
                .get();
        tableAdeme.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private void editFocuedCellHumedad() {
        final TablePosition<HumedadProperty, ?> focusedCell = tableHumedad.focusModelProperty().get()
                .focusedCellProperty().get();
        tableHumedad.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private void editFocuedCellClasificacion() {
        final TablePosition<ClasificacionSucsProperty, ?> focusedCell = tableClasificacion.focusModelProperty().get()
                .focusedCellProperty().get();
        tableClasificacion.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private void editFocusedCellDatos() {
        final TablePosition<DatosCampoProperty, ?> focusedCell = tableDatos.focusModelProperty().get()
                .focusedCellProperty().get();
        tableDatos.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    private void loadSxml(File file) {
        if (file != null) {
            ArchivoXml archivoXml = new ArchivoXml();
            datosCampoProperties.clear();
            datosCampoProperties.addAll(archivoXml.cargarDatosCampo(file));
            clasificacionSucsProperties.clear();
            clasificacionSucsProperties.addAll(archivoXml.cargarDatosClasificacion(file));
            humedadProperties.clear();
            humedadProperties.addAll(archivoXml.cargarDatosHumedad(file));
            ademeProperties.clear();
            ademeProperties.addAll(archivoXml.cargarDatosAdeme(file));
            trepanoProperties.clear();
            ObservableList<TrepanoProperty> datos = archivoXml.cargarDatosTrepano(file);
            if (datos.size() > 0) {
                trepanoProperties.addAll(datos);
            }
            List<ProfundidadSondeo> list = archivoXml.cargarDatosIniciales(file);
            profundidadSondeo = list.get(0);
        }
    }

    public boolean showDatosSondeosDialog(ProfundidadSondeo profundidadSondeo) {
        try {
            Flow flow = new Flow(DatosSondeosController.class);
            FlowHandler flowHandler = flow.createHandler();
            // Create the dialog Stage.
            StackPane stackPane = flowHandler.start(new DefaultFlowContainer());
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.getIcons().add(new Image("/image/Sondeos.png"));
            //dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initOwner(btnCargarDatos.getScene().getWindow());
            dialogStage.setScene(new Scene(stackPane));
            // Set the person into the controller.
            DatosSondeosController datosSondeosController = (DatosSondeosController) flowHandler.getCurrentView().getViewContext().getController();
            datosSondeosController.setDialogStage(dialogStage);
            datosSondeosController.setProfundidadSondeo(profundidadSondeo);
            //Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            this.profundidadSondeo = datosSondeosController.getProfundidadSondeo();
            return datosSondeosController.isOkClicked();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void loadColors() {
        Utility utility = new Utility();
        utility.loadColors(itemsColorPoperties);
    }

    private void loadPattern() {
        patternTypesProperties.addAll(FillPatternType.values());
    }

    private ComboBoxTableCell<ClasificacionSucsProperty,IndexedColors> comboBoxColors() {
        Utility utility = new Utility();
        return utility.comboBoxColors(itemsColorPoperties);
    }

    private ComboBoxTableCell<ClasificacionSucsProperty,FillPatternType> comboBoxPattern() {
        Utility utility = new Utility();
        return utility.comboBoxPattern(patternTypesProperties);
    }
}
