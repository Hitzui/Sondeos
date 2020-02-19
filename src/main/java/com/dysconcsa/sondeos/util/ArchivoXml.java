package com.dysconcsa.sondeos.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.dysconcsa.sondeos.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

public class ArchivoXml {

    private Integer linea;
    private Document document;
    private Element rootElement;

    public ArchivoXml() {
    }

    public void setDocument() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            AlertError.showAlert(e);
        }
        if (dBuilder != null) {
            this.document = dBuilder.newDocument();
            this.rootElement = this.document.createElement("elementos");
            document.appendChild(rootElement);
        }
    }

    public void prepararElementosDatos(ObservableList<DatosCampoProperty> datosCampoProperties, AnchorPane anchorPane) {
        try {
            Utility utility = new Utility();
            JFXButton buttonAceptar = new JFXButton("Aceptar");
            JFXDialog dialog = utility.showDialog(anchorPane, "Datos", "Debe especificar los datos de campo para poder continuar", buttonAceptar);
            buttonAceptar.setOnAction(e -> dialog.close());
            if (datosCampoProperties.size() <= 0) {
                dialog.show();
                return;
            }
            linea = 1;
            Element datosElement = document.createElement("datos");
            rootElement.appendChild(datosElement);
            for (DatosCampoProperty datos : datosCampoProperties) {
                Element valores = document.createElement("valores");
                datosElement.appendChild(valores);
                valores.setAttribute("linea", String.valueOf(linea));
                Element profundidadInicial = document.createElement("profundidadInicial");
                profundidadInicial.appendChild(document.createTextNode(Double.toString(datos.getProfundidadInicial())));
                valores.appendChild(profundidadInicial);
                Element profundidadFinal = document.createElement("profundidadFinal");
                profundidadFinal.appendChild(document.createTextNode(Double.toString(datos.getProfundidadFinal())));
                valores.appendChild(profundidadFinal);
                Element recobro = document.createElement("recobro");
                recobro.appendChild(document.createTextNode(Integer.toString(datos.getRecobro())));
                valores.appendChild(recobro);
                Element golpe1 = document.createElement("golpe1");
                golpe1.appendChild(document.createTextNode(Integer.toString(datos.getGolpe1())));
                valores.appendChild(golpe1);
                Element golpe2 = document.createElement("golpe2");
                golpe2.appendChild(document.createTextNode(Integer.toString(datos.getGolpe2())));
                valores.appendChild(golpe2);
                Element golpe3 = document.createElement("golpe3");
                golpe3.appendChild(document.createTextNode(Integer.toString(datos.getGolpe3())));
                valores.appendChild(golpe3);
                linea += 1;
            }
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    public void prepararElementosProfundidad(String sondeoNumero,Double profundidadMinima, Double profundidadMaxima, Double elevacion) {
        try {
            Element valores = document.createElement("valores");
            Element rootProfundidad = document.createElement("estrato");
            rootElement.appendChild(rootProfundidad);
            rootProfundidad.appendChild(valores);
            Element elemntSondeoNumero = document.createElement("sondeoNumero");
            elemntSondeoNumero.appendChild(document.createTextNode(sondeoNumero));
            valores.appendChild(elemntSondeoNumero);
            Element elementProfundidadMinima = document.createElement("profundidadMinima");
            elementProfundidadMinima.appendChild(document.createTextNode(Double.toString(profundidadMinima)));
            valores.appendChild(elementProfundidadMinima);
            Element elementProfundidadMaxima = document.createElement("profundidadMaxima");
            elementProfundidadMaxima.appendChild(document.createTextNode(Double.toString(profundidadMaxima)));
            valores.appendChild(elementProfundidadMaxima);
            Element elementElevacion = document.createElement("elevacion");
            elementElevacion.appendChild(document.createTextNode(Double.toString(elevacion)));
            valores.appendChild(elementElevacion);
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    public void prepararElementosClasificacion(ObservableList<ClasificacionSucsProperty> clasificacionSucsProperties, AnchorPane anchorPane) {
        try {
            Utility utility = new Utility();
            JFXButton buttonAceptar = new JFXButton("Aceptar");
            JFXDialog dialog = utility.showDialog(anchorPane, "Datos", "Debe especificar los datos de campo para poder continuar", buttonAceptar);
            buttonAceptar.setOnAction(e -> dialog.close());
            if (clasificacionSucsProperties.size() <= 0) {
                dialog.show();
                return;
            }
            linea = 1;
            Element elementClasificacion = document.createElement("clasificacion");
            rootElement.appendChild(elementClasificacion);
            for (ClasificacionSucsProperty datos : clasificacionSucsProperties) {
                Element valores = document.createElement("valores");
                elementClasificacion.appendChild(valores);
                valores.setAttribute("linea", String.valueOf(linea));
                Element profundidad = document.createElement("profundidad");
                profundidad.appendChild(document.createTextNode(Double.toString(datos.getProfundidad())));
                valores.appendChild(profundidad);
                Element limiteLiquido = document.createElement("limiteLiquido");
                limiteLiquido.appendChild(document.createTextNode(Integer.toString(datos.getLimiteLiquido())));
                valores.appendChild(limiteLiquido);
                Element indicePlasticidad = document.createElement("indicePlasticidad");
                indicePlasticidad.appendChild(document.createTextNode(Integer.toString(datos.getIndicePlasticidad())));
                valores.appendChild(indicePlasticidad);
                Element tipoSuelo = document.createElement("tipoSuelo");
                tipoSuelo.appendChild(document.createTextNode(Integer.toString(datos.getTipoSuelo())));
                valores.appendChild(tipoSuelo);
                Element descripcion = document.createElement("descripcion");
                descripcion.appendChild(document.createTextNode(datos.getDescripcion().toUpperCase()));
                valores.appendChild(descripcion);
                linea += 1;
            }
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    public void prepararElementHumedad(ObservableList<HumedadProperty> humedadProperties, AnchorPane anchorPane) {
        Utility utility = new Utility();
        JFXButton buttonAceptar = new JFXButton("Aceptar");
        JFXDialog dialog = utility.showDialog(anchorPane, "Datos", "Debe especificar los datos de campo para poder continuar", buttonAceptar);
        buttonAceptar.setOnAction(e -> dialog.close());
        if (humedadProperties.size() <= 0) {
            dialog.show();
            return;
        }
        linea = 1;
        Element elementHumedad = document.createElement("humedad");
        rootElement.appendChild(elementHumedad);
        for (HumedadProperty datos : humedadProperties) {
            Element valores = document.createElement("valores");
            elementHumedad.appendChild(valores);
            valores.setAttribute("linea", String.valueOf(linea));
            Element profundidadInicial = document.createElement("profundidadInicial");
            profundidadInicial.appendChild(document.createTextNode(Double.toString(datos.getProfundidadInicial())));
            valores.appendChild(profundidadInicial);
            Element profundidadFinal = document.createElement("profundidadFinal");
            profundidadFinal.appendChild(document.createTextNode(Double.toString(datos.getProfundidadFinal())));
            valores.appendChild(profundidadFinal);
            Element humedad = document.createElement("humedad");
            humedad.appendChild(document.createTextNode(Double.toString(datos.getHumedad())));
            valores.appendChild(humedad);
            linea += 1;
        }
    }

    public void prepararElementosAdeme(ObservableList<AdemeProperty> ademeProperties, AnchorPane anchorPane) {
        Utility utility = new Utility();
        JFXButton buttonAceptar = new JFXButton("Aceptar");
        JFXDialog dialog = utility.showDialog(anchorPane, "Datos", "Debe especificar los datos de campo para poder continuar", buttonAceptar);
        buttonAceptar.setOnAction(e -> dialog.close());
        if (ademeProperties.size() <= 0) {
            dialog.show();
            return;
        }
        linea = 1;
        Element elementAdeme = document.createElement("ademe");
        rootElement.appendChild(elementAdeme);
        for (AdemeProperty datos : ademeProperties) {
            Element valores = document.createElement("valores");
            elementAdeme.appendChild(valores);
            valores.setAttribute("linea", String.valueOf(linea));
            Element profundidad = document.createElement("profundidad");
            profundidad.appendChild(document.createTextNode(String.valueOf(datos.getProfundidad())));
            valores.appendChild(profundidad);
            Element descripcion = document.createElement("descripcion");
            descripcion.appendChild(document.createTextNode(datos.getDescripcion()));
            valores.appendChild(descripcion);
            linea += 1;
        }
    }

    public void prepararElementosTrepano(ObservableList<TrepanoProperty> trepanoProperties, AnchorPane anchorPane) {
        Utility utility = new Utility();
        JFXButton buttonAceptar = new JFXButton("Aceptar");
        JFXDialog dialog = utility.showDialog(anchorPane, "Datos", "Debe especificar los datos de campo para poder continuar", buttonAceptar);
        buttonAceptar.setOnAction(e -> dialog.close());
        if (trepanoProperties.size() <= 0) {
            dialog.show();
            return;
        }
        linea = 1;
        Element elementTrepano = document.createElement("trepano");
        rootElement.appendChild(elementTrepano);
        for (TrepanoProperty dato : trepanoProperties) {
            Element valores = document.createElement("valores");
            elementTrepano.appendChild(valores);
            valores.setAttribute("linea", String.valueOf(linea));
            Element profundidad = document.createElement("profundidad");
            profundidad.appendChild(document.createTextNode(String.valueOf(dato.getProfundidad())));
            valores.appendChild(profundidad);
            Element trepano = document.createElement("trepano");
            trepano.appendChild(document.createTextNode(dato.getTrepano().toUpperCase()));
            valores.appendChild(trepano);
            linea += 1;
        }
    }

    /**
     * <p>Guardamos el archivo xml despues de haber generado los datos en el TableView</p>
     *
     * @param file Lugar donde se guardar el archivo XML
     */
    public void guardarArchivoXml(File file) {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
            // Output to console for testing
            /*StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);*/
        } catch (TransformerException ex) {
            AlertError.showAlert(ex);
        }
    }

    public ObservableList<DatosCampoProperty> cargarDatosCampo(File file) {
        try {
            if (file == null) {
                return FXCollections.observableArrayList();
            }
            ObservableList<DatosCampoProperty> datos = FXCollections.observableArrayList();
            Element element = this.getNodeList(file, "datos");
            NodeList nodeList = element.getElementsByTagName("valores");
            for (int j = 0; j < nodeList.getLength(); j++) {
                Double profundidadInicial = Double.parseDouble(element.getElementsByTagName("profundidadInicial").item(j).getTextContent());
                Double profundidadFinal = Double.parseDouble(element.getElementsByTagName("profundidadFinal").item(j).getTextContent());
                Integer recobro = Integer.parseInt(element.getElementsByTagName("recobro").item(j).getTextContent());
                Integer golpe1 = Integer.parseInt(element.getElementsByTagName("golpe1").item(j).getTextContent());
                Integer golpe2 = Integer.parseInt(element.getElementsByTagName("golpe2").item(j).getTextContent());
                Integer golpe3 = Integer.parseInt(element.getElementsByTagName("golpe3").item(j).getTextContent());
                datos.add(new DatosCampoProperty(profundidadInicial, profundidadFinal, recobro, golpe1, golpe2, golpe3));
            }
            return datos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    public ObservableList<ClasificacionSucsProperty> cargarDatosClasificacion(File file) {
        try {
            ObservableList<ClasificacionSucsProperty> datos = FXCollections.observableArrayList();
            Element element = this.getNodeList(file, "clasificacion");
            NodeList nodeList = element.getElementsByTagName("valores");
            for (int j = 0; j < nodeList.getLength(); j++) {
                Double profundidad = Double.parseDouble(element.getElementsByTagName("profundidad").item(j).getTextContent());
                Integer limiteLiquido = Integer.parseInt(element.getElementsByTagName("limiteLiquido").item(j).getTextContent());
                Integer indicePlasticidad = Integer.parseInt(element.getElementsByTagName("indicePlasticidad").item(j).getTextContent());
                Integer tipoSuelo = Integer.parseInt(element.getElementsByTagName("tipoSuelo").item(j).getTextContent());
                String descripcion = element.getElementsByTagName("descripcion").item(j).getTextContent();
                datos.add(new ClasificacionSucsProperty(profundidad, limiteLiquido, indicePlasticidad, tipoSuelo, descripcion));
            }
            return datos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    public ObservableList<HumedadProperty> cargarDatosHumedad(File file) {
        try {
            ObservableList<HumedadProperty> datos = FXCollections.observableArrayList();
            Element element = this.getNodeList(file, "humedad");
            NodeList nodeList = element.getElementsByTagName("valores");
            for (int j = 0; j < nodeList.getLength(); j++) {
                Double profundidadInicial = Double.parseDouble(element.getElementsByTagName("profundidadInicial").item(j).getTextContent());
                Double profundidadFinal = Double.parseDouble(element.getElementsByTagName("profundidadFinal").item(j).getTextContent());
                Double humedad = Double.parseDouble(element.getElementsByTagName("humedad").item(j).getTextContent());
                datos.add(new HumedadProperty(profundidadInicial, profundidadFinal, humedad));
            }
            return datos;
        } catch (Exception ex) {
            AlertError.showAlert(ex);
            return FXCollections.observableArrayList();
        }
    }

    public List<ProfundidadSondeo> cargarDatosIniciales(File file) {
        List<ProfundidadSondeo> datos = new ArrayList<>();
        try {
            Element element = this.getNodeList(file, "estrato");
            NodeList nodeList = element.getElementsByTagName("valores");
            for (int j = 0; j < nodeList.getLength(); j++) {
                String sondeoNumero = element.getElementsByTagName("sondeoNumero").item(j).getTextContent();
                Double profundidadMinima = Double.parseDouble(element.getElementsByTagName("profundidadMinima").item(j).getTextContent());
                Double profundidadMaxima = Double.parseDouble(element.getElementsByTagName("profundidadMaxima").item(j).getTextContent());
                Double elevacion = Double.parseDouble(element.getElementsByTagName("elevacion").item(j).getTextContent());
                System.out.println(profundidadMinima);
                datos.add(new ProfundidadSondeo(sondeoNumero,profundidadMinima, profundidadMaxima, elevacion));
            }
            return datos;
        } catch (Exception ex) {
            AlertError.showAlert(ex);
            return null;
        }
    }

    public ObservableList<AdemeProperty> cargarDatosAdeme(File file) {
        try {
            ObservableList<AdemeProperty> datos = FXCollections.observableArrayList();
            Element element = this.getNodeList(file, "ademe");
            NodeList nodeList = element.getElementsByTagName("valores");
            for (int j = 0; j < nodeList.getLength(); j++) {
                Double profundidad = Double.parseDouble(element.getElementsByTagName("profundidad").item(j).getTextContent());
                String descripcion = element.getElementsByTagName("descripcion").item(j).getTextContent();
                datos.add(new AdemeProperty(profundidad, descripcion));
            }
            return datos;
        } catch (Exception ex) {
            return FXCollections.observableArrayList();
        }
    }

    public ObservableList<TrepanoProperty> cargarDatosTrepano(File file) {
        try {
            ObservableList<TrepanoProperty> datos = FXCollections.observableArrayList();
            Element element = this.getNodeList(file, "trepano");
            NodeList nodeList = element.getElementsByTagName("valores");
            for (int j = 0; j < nodeList.getLength(); j++) {
                Double profundidad = Double.parseDouble(element.getElementsByTagName("profundidad").item(j).getTextContent());
                String descripcion = element.getElementsByTagName("trepano").item(j).getTextContent();
                datos.add(new TrepanoProperty(profundidad, descripcion));
            }
            return datos;
        } catch (Exception ex) {
            return FXCollections.observableArrayList();
        }
    }

    private Element getNodeList(File file, String node) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Element _ele = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("elementos");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList _datos = eElement.getElementsByTagName(node);
                    Node _node = _datos.item(0);
                    if (_node.getNodeType() == Node.ELEMENT_NODE) {
                        _ele = (Element) _node;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _ele;
    }

}

