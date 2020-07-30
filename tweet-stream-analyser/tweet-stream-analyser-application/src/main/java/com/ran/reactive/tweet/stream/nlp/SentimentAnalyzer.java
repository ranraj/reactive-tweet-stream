package com.ran.reactive.tweet.stream.nlp;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.ran.reactive.tweet.stream.model.Sentiment;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

@Component
public class SentimentAnalyzer {

	private StanfordCoreNLP pipeline;

	@PostConstruct
	private void setProperties() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(props);
	}

	public Sentiment getSentiment(String text) {
		Sentiment sentiment = new Sentiment();
		
		if (text != null && text.length() > 0) {
			Annotation annotation = pipeline.process(text);
			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				sentiment.setScore(RNNCoreAnnotations.getPredictedClass(tree));
				sentiment.setType(sentimentType);
			}

		}
		return sentiment;
	}

}
