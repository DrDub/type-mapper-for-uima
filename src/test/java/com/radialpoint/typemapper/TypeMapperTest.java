//package com.radialpoint.nlp.TypeMapper;
//
//import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
//import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
//import static org.apache.uima.fit.factory.ResourceCreationSpecifierFactory.createResourceCreationSpecifier;
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.uima.UIMAException;
//import org.apache.uima.analysis_engine.AnalysisEngineDescription;
//import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
//import org.apache.uima.cas.CAS;
//import org.apache.uima.cas.CASException;
//import org.apache.uima.cas.CASRuntimeException;
//import org.apache.uima.collection.CollectionReader;
//import org.apache.uima.fit.component.CasAnnotator_ImplBase;
//import org.apache.uima.fit.descriptor.SofaCapability;
//import org.apache.uima.fit.descriptor.TypeCapability;
//import org.apache.uima.fit.factory.AggregateBuilder;
//import org.apache.uima.fit.factory.FlowControllerFactory;
//import org.apache.uima.fit.pipeline.SimplePipeline;
//import org.apache.uima.util.XMLInputSource;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.radialpoint.nlp.flow.SkippingDocumentsFlowController;
//import com.radialpoint.nlp.readers.BinaryCollectionReader;
//import com.radialpoint.nlp.types.RadialpointDocumentAnnotation;
//
//public class TypeMapperTest {
//  private AnalysisEngineDescription engine;
//
//  // private static List<Type> idTextPairs = new ArrayList<>();
//
//  @SofaCapability(inputSofas = { ViewsConstants.INITIAL_VIEW, ViewsConstants.TEXT_VIEW })
//  @TypeCapability(inputs = "com.radialpoint.nlp.types.RadialpointDocumentAnnotation")
//  public static class TestTypeMapping extends CasAnnotator_ImplBase {
//
//    @Override
//    public void process(CAS cas) throws AnalysisEngineProcessException {
//      RadialpointDocumentAnnotation docAnn;
//      try {
//        docAnn = GlobalMetada.getRadialpointDocumentAnnotation(cas);
//      } catch (CASRuntimeException | CASException e) {
//        throw new AnalysisEngineProcessException(e);
//      }
//
//      System.out.println("TestDebug: Inside TestTypeMapping.process, de type = " + cas.getBeginFeature());
//      System.out.println("TestDebug: Inside TestTypeMapping.process, rp type = "
//              + cas.getTypeSystem().getType("com.radialpoint.nlp.types.Token").getFeatureByBaseName("begin"));
//      // cas Types = " + cas.getTypeSystem().toString());
//
//    }
//  }
//
//  @Before
//  public void setUp() throws Exception {
//    AnalysisEngineDescription detector = (AnalysisEngineDescription) createResourceCreationSpecifier(
//            new XMLInputSource(this.getClass().getClassLoader()
//                    .getResourceAsStream("com/radialpoint/nlp/aggregates/TypeDetectionAggregate.xml"), new File(".")),
//            new Object[0]);
//    AnalysisEngineDescription extractor = (AnalysisEngineDescription) createResourceCreationSpecifier(
//            new XMLInputSource(this.getClass().getClassLoader()
//                    .getResourceAsStream("com/radialpoint/nlp/aggregates/TextExtractionAggregate.xml"), new File(".")),
//            new Object[0]);
//    createEngineDescription(detector);
//    createEngineDescription(extractor);
//    AnalysisEngineDescription reporter = createEngineDescription(TestTypeMapping.class);
//
//    final AggregateBuilder builder = new AggregateBuilder();
//    builder.add(detector);
//    builder.add(extractor);
//    builder.add(reporter, "_InitialView", "_InitialView", "text", "text");
//    builder.setFlowControllerDescription(FlowControllerFactory
//            .createFlowControllerDescription(SkippingDocumentsFlowController.class));
//    this.engine = builder.createAggregateDescription();
//  }
//
//  @After
//  public void tearDown() throws Exception {
//    engine = null;
//  }
//
//  @Test
//  public void testProcessCAS() throws UIMAException, IOException {
//
//    CollectionReader reader = createReader(BinaryCollectionReader.class, BinaryCollectionReader.PARAM_LANGUAGE,
//            "en", //
//            BinaryCollectionReader.PARAM_DIRECTORY,
//            "./src/test/resources/com/radialpoint/nlp/annotators/detection/tika");
//
//    System.out.println("TestDebug: Before simplePiple call ");
//
//    SimplePipeline.runPipeline(reader, engine);
//
//    System.out.println("TestDebug: After simplePiple call ");
//
//    assertEquals(3, 3);
//
//  }
//
//}
