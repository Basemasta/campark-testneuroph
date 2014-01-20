package fr.miage.orleans.campark.testneuroph.model.neuralnetworks;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.neuroph.core.learning.DataSet;
import org.neuroph.imgrec.ColorMode;
import org.neuroph.imgrec.FractionRgbData;
import org.neuroph.imgrec.ImageRecognitionHelper;
import org.neuroph.imgrec.image.Dimension;
import org.neuroph.util.TransferFunctionType;
import testneuroph.model.utils.ResizeImage;

/**
 *
 * @author Steve Cancès <steve.cances@gmail.com>
 */
public class Parameters {

    protected ArrayList<Set<?>> parametersList;

    protected Map<Set<?>, Comparator<CustomNeuralNetwork>> parametersComparators;

    protected Set<ColorMode> colorModes;

    protected Set<TransferFunctionType> transferFunctionTypes;

    protected Set<Double> learningRates;

    protected Set<Double> momentums;

    protected Set<List<Integer>> hiddenLayers;

    protected Set<Integer> resizeMaxDimensions;

    protected List<CustomNeuralNetwork> neuralNetworksList;

    public Parameters() {
	this.parametersList = new ArrayList<>();
	this.parametersComparators = new HashMap<>();

	this.colorModes = new HashSet<>();
	this.parametersList.add(this.colorModes);
	this.parametersComparators.put(this.colorModes, new ComparatorColorMode());

	this.transferFunctionTypes = new HashSet<>();
	this.parametersList.add(this.transferFunctionTypes);
	this.parametersComparators.put(this.transferFunctionTypes, new ComparatorTransferFunctionType());

	this.learningRates = new HashSet<>();
	this.parametersList.add(this.learningRates);
	this.parametersComparators.put(this.learningRates, new ComparatorLearningRate());

	this.momentums = new HashSet<>();
	this.parametersList.add(this.momentums);
	this.parametersComparators.put(this.momentums, new ComparatorMomentum());

	this.hiddenLayers = new HashSet<>();
	this.parametersList.add(this.hiddenLayers);
	this.parametersComparators.put(this.hiddenLayers, new ComparatorHiddenLayer());

	this.resizeMaxDimensions = new HashSet<>();
	this.parametersList.add(this.resizeMaxDimensions);
	this.parametersComparators.put(this.resizeMaxDimensions, new ComparatorResizeMaxDimension());

	this.neuralNetworksList = new ArrayList<>();
    }

    public Set<ColorMode> getColorModes() {
	return colorModes;
    }

    public Set<TransferFunctionType> getTransferFunctionTypes() {
	return transferFunctionTypes;
    }

    public Set<Double> getLearningRates() {
	return learningRates;
    }

    public Set<Double> getMomentums() {
	return momentums;
    }

    public Set<List<Integer>> getHiddenLayers() {
	return hiddenLayers;
    }

    public Set<Integer> getResizeMaxDimensions() {
	return resizeMaxDimensions;
    }

    public List<CustomNeuralNetwork> getNeuralNetworksList() {
	return neuralNetworksList;
    }

    public boolean isReady() {
	for (Set<?> set : this.parametersList) {
	    if (set.isEmpty()) {
		return false;
	    }
	}
	return true;
    }

    public void sortNeuralNetworks() {
	//for (Set<?> set : this.parametersList) {
	List<Set<?>> parametersListReversed = (List<Set<?>>) this.parametersList.clone();
	for (Set<?> set : parametersListReversed) {
	    Collections.sort(this.neuralNetworksList, this.parametersComparators.get(set));
	}
    }

    public void generateNeuralNetworks(Map<String, BufferedImage> dataMap) {
	if (!dataMap.isEmpty() && this.isReady()) {

	    // Les classes
	    List<String> imageClasses = new ArrayList<>();
	    imageClasses.add("0");
	    imageClasses.add("1");

	    for (Integer dim : this.resizeMaxDimensions) {

		// La map de labelImage , Images
		Map<String, FractionRgbData> rgbDataMap = new HashMap<>();
		for (Entry<String, BufferedImage> entry : dataMap.entrySet()) {
		    BufferedImage bufferedImage = ResizeImage.resizeImageWithMaxWidthMaxHeight(entry.getValue(), dim, dim);
		    FractionRgbData fractionRgbData = new FractionRgbData(bufferedImage);
		    // ajout de l'image FractionRgbData dans la map
		    rgbDataMap.put(entry.getKey(), fractionRgbData);
		}
		// Récupération d'une image de la map
		BufferedImage firstBufferedImage = dataMap.entrySet().iterator().next().getValue();
		// Récupération des dimensions de l'image
		Dimension samplingResolution = new Dimension(firstBufferedImage.getWidth(), firstBufferedImage.getHeight());
		System.out.println("dim : " + dim);
		System.out.println("samplingResolution : " + samplingResolution.getWidth() + " " + samplingResolution.getHeight());

		// Création du dataSet
		DataSet dataSet = ImageRecognitionHelper.createTrainingSet(imageClasses, rgbDataMap);
		dataSet.setLabel("dataSet");
		for (ColorMode colorMode : this.colorModes) {
		    for (TransferFunctionType tft : this.transferFunctionTypes) {
			for (Double learningRate : this.learningRates) {
			    for (Double momentum : this.momentums) {
				for (List<Integer> hls : this.hiddenLayers) {
				    CustomNeuralNetwork cnn = new CustomNeuralNetwork("");
				    cnn.setColorMode(colorMode);
				    cnn.setTransferFunctionType(tft);
				    cnn.setLearningRate(learningRate);
				    cnn.setMomentum(momentum);
				    for (Integer hl : hls) {
					cnn.addHiddenLayer(hl);
				    }
				    cnn.setSamplingResolution(samplingResolution);
				    cnn.initialize();
				    this.neuralNetworksList.add(cnn);
				    cnn.learn(dataSet);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private class ComparatorColorMode implements Comparator<CustomNeuralNetwork> {

	@Override
	public int compare(CustomNeuralNetwork cnn1, CustomNeuralNetwork cnn2) {
	    return cnn1.getColorMode().compareTo(cnn2.getColorMode());
	}
    }

    private class ComparatorTransferFunctionType implements Comparator<CustomNeuralNetwork> {

	@Override
	public int compare(CustomNeuralNetwork cnn1, CustomNeuralNetwork cnn2) {
	    return cnn1.getTransferFunctionType().compareTo(cnn2.getTransferFunctionType());
	}
    }

    private class ComparatorLearningRate implements Comparator<CustomNeuralNetwork> {

	@Override
	public int compare(CustomNeuralNetwork cnn1, CustomNeuralNetwork cnn2) {
	    return cnn1.getLearningRate().compareTo(cnn2.getLearningRate());
	}
    }

    private class ComparatorMomentum implements Comparator<CustomNeuralNetwork> {

	@Override
	public int compare(CustomNeuralNetwork cnn1, CustomNeuralNetwork cnn2) {
	    return cnn1.getMomentum().compareTo(cnn2.getMomentum());
	}
    }

    private class ComparatorHiddenLayer implements Comparator<CustomNeuralNetwork> {

	@Override
	public int compare(CustomNeuralNetwork cnn1, CustomNeuralNetwork cnn2) {
	    return (cnn1.getHiddenLayers().toString().compareTo(cnn2.getHiddenLayers().toString()));
	}
    }

    private class ComparatorResizeMaxDimension implements Comparator<CustomNeuralNetwork> {

	@Override
	public int compare(CustomNeuralNetwork cnn1, CustomNeuralNetwork cnn2) {
	    Integer dim1 = cnn1.getSamplingResolution().getWidth() * cnn1.getSamplingResolution().getHeight();
	    Integer dim2 = cnn2.getSamplingResolution().getWidth() * cnn2.getSamplingResolution().getHeight();
	    return dim1.compareTo(dim2);
	}
    }

}
