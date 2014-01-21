package fr.miage.orleans.campark.testneuroph.model.neuralnetworks;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.data.DataSet;
import org.neuroph.imgrec.ColorMode;
import org.neuroph.imgrec.ImageRecognitionHelper;
import org.neuroph.imgrec.image.Dimension;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author Steve Cancès <steve.cances@gmail.com>
 */
public class CustomNeuralNetwork {

    //Default configuration
    public static final ColorMode DEFAULT_COLOR_MODE = ColorMode.FULL_COLOR;

    public static final TransferFunctionType DEFAULT_TRANSFER_FUNCTION_TYPE = TransferFunctionType.SIGMOID;

    public static final Double DEFAULT_LEARNING_RATE = 0.1d;

    public static final Double DEFAULT_MOMENTUM = 0.25d;

    public static final Integer DEFAULT_SAMPLING_RESOLUTION = 20;

    //Parameters
    protected ColorMode colorMode = DEFAULT_COLOR_MODE;

    protected TransferFunctionType transferFunctionType = DEFAULT_TRANSFER_FUNCTION_TYPE;

    protected List<Integer> hiddenLayers;

    protected double learningRate = DEFAULT_LEARNING_RATE;

    protected double momentum = DEFAULT_MOMENTUM;

    Dimension samplingResolution = new Dimension(DEFAULT_SAMPLING_RESOLUTION, DEFAULT_SAMPLING_RESOLUTION);

    // Attributs
    protected String label;

    protected MultiLayerPerceptron neuralNetwork;

    protected MomentumBackpropagation learningRule;

    protected Set<String> imageClasses;

    protected boolean initialized = false;

    public CustomNeuralNetwork(String label) {
	this.label = label;
	this.hiddenLayers = new ArrayList<>();
	this.imageClasses = new HashSet<>();
    }

    public void initialize() {
	List imageClassesList = new ArrayList(this.imageClasses);
	Collections.sort(imageClassesList);
	this.neuralNetwork = (MultiLayerPerceptron) ImageRecognitionHelper.createNewNeuralNetwork(
		this.label,
		this.samplingResolution,
		this.colorMode,
		imageClassesList,
		this.hiddenLayers,
		this.transferFunctionType);
	this.learningRule = (MomentumBackpropagation) this.neuralNetwork.getLearningRule();
	this.learningRule.setLearningRate(this.learningRate);
	this.learningRule.setMomentum(this.momentum);
	this.learningRule.addListener(new LearningEventListener() {

	    @Override
	    public void handleLearningEvent(LearningEvent le) {
		System.out.println(le.toString());
	    }
	});
	this.initialized = true;
    }

    public int imageClassesSize() {
	return imageClasses.size();
    }

    public boolean addImageClasse(String imageClasse) {
	return imageClasses.add(imageClasse);
    }

    public boolean removeImageClasse(String imageClasse) {
	return imageClasses.remove(imageClasse);
    }

    public void clearImageClasses() {
	imageClasses.clear();
    }

    public boolean isInitialized() {
	return initialized;
    }

    public boolean addHiddenLayer(Integer e) {
	return hiddenLayers.add(e);
    }

    public void clearHiddenLayers() {
	hiddenLayers.clear();
    }

    public List<Integer> getHiddenLayers() {
	return hiddenLayers;
    }

    public void setHiddenLayers(List<Integer> hiddenLayers) {
	this.hiddenLayers = hiddenLayers;
    }

    public ColorMode getColorMode() {
	return colorMode;
    }

    public void setColorMode(ColorMode colorMode) {
	this.colorMode = colorMode;
    }

    public TransferFunctionType getTransferFunctionType() {
	return transferFunctionType;
    }

    public void setTransferFunctionType(TransferFunctionType transferFunctionType) {
	this.transferFunctionType = transferFunctionType;
    }

    public Double getLearningRate() {
	return learningRate;
    }

    public void setLearningRate(double learningRate) {
	this.learningRate = learningRate;
    }

    public Double getMomentum() {
	return momentum;
    }

    public void setMomentum(double momentum) {
	this.momentum = momentum;
    }

    public Dimension getSamplingResolution() {
	return samplingResolution;
    }

    public void setSamplingResolution(Dimension samplingResolution) {
	this.samplingResolution = samplingResolution;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public void learn(DataSet dataSet) {
	if (this.initialized) {
	    this.neuralNetwork.learn(dataSet);
	}
    }
}
