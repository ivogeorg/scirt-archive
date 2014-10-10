package hu.u_szeged.rgai.bio.uima.tagger;

import java.util.List;
import java.util.logging.Logger;

import martin.common.ArgParser;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import uk.ac.man.entitytagger.EntityTagger;
import uk.ac.man.entitytagger.Mention;
import uk.ac.man.entitytagger.matching.Matcher;

public class RgaiLinnaeusWrapper extends JCasAnnotator_ImplBase {

	private Matcher matcher;
	private Logger logger;

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		String configFile = (String) aContext.getConfigParameterValue("ConfigFile");
		if (configFile == null) {
			logger.warning("Parameter ConfigFile is NULL using \"./properties.conf\"");
			configFile = "./properties.conf";
		}
		ArgParser ap = new ArgParser(new String[] { "--properties", configFile });

		this.matcher = EntityTagger.getMatcher(ap, logger);
		super.initialize(aContext);
	}

	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {

		String text = cas.getDocumentText();
		List<Mention> mentions = matcher.match(text);
		for (Mention mention : mentions) {

			String mostProbableID = mention.getMostProbableID();
			String idsToString = mention.getIdsToString();

			LinnaeusSpecies species = new LinnaeusSpecies(cas);
			species.setBegin(mention.getStart());
			species.setEnd(mention.getEnd());
			species.setMostProbableSpeciesId(mostProbableID);
			species.setAllIdsString(idsToString);
			species.setAmbigous(mention.isAmbigous());
			species.addToIndexes();

		}
	}

	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException {
		this.matcher = null;
		super.collectionProcessComplete();
	}
}
