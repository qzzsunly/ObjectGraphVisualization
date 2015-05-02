package ch.hsr.ogv.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adrian Rieser
 *
 */
public class ModelObject extends ModelBox {

	private Map<Attribute, String> attributeValues = new HashMap<Attribute, String>();
	private ModelClass modelClass;

	public static volatile AtomicInteger modelObjectCounter = new AtomicInteger(0);

	// for un/marshaling only
	public ModelObject() {
	}
	
	public ModelObject(String name, ModelClass modelClass, Point3D coordinates, double width, double heigth, Color color) {
		super(name, coordinates, width, heigth, color);
		this.modelClass = modelClass;
	}
	
	public Map<Attribute, String> getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(Map<Attribute, String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public void changeAttributeName(Attribute attribute, String name) {
		attribute.setName(name);
		setChanged();
		notifyObservers(attribute);
	}

	public void changeAttributeValue(Attribute attribute, String value) {
		String oldValue = this.attributeValues.put(attribute, value);
		if(oldValue != null) {
			setChanged();
			notifyObservers(attribute);
		}
	}

	public boolean addAttributeValue(Attribute attribute, String attributeValue) {
		if (attributeValues.containsKey(attribute)) {
			return false;
		}
		attributeValues.put(attribute, attributeValue);
		setChanged();
		notifyObservers(attribute);
		return true;
	}

	public String deleteAttributeValue(Attribute attribute) {
		String deleted = attributeValues.remove(attribute);
		if(deleted != null) {
			setChanged();
			notifyObservers(attribute);
		}
		return deleted;
	}

	@XmlTransient
	public ModelClass getModelClass() {
		return modelClass;
	}

	public void setModelClass(ModelClass modelClass) {
		this.modelClass = modelClass;
	}

	public void updateAttribute(Attribute attribute, String value) {
		attributeValues.replace(attribute, value);
	}

}