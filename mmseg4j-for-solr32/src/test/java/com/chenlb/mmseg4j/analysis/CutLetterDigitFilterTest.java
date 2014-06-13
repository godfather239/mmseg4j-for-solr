package com.chenlb.mmseg4j.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.junit.Assert;
import org.junit.Test;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;

/**
 * 
 * 
 * @author chenlb 2014年3月30日 下午7:19:57
 */
public class CutLetterDigitFilterTest {

	@Test
	public void testCutLeeterDigitFilter() {
		String myTxt = "mb991ch cq40-519tx mmseg4j ";
		List<String> words = AnalyzerTest.toWords(myTxt, new Analyzer() {

			@Override
			public TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
				TokenStream ts = super.reusableTokenStream(fieldName, reader);
				return new CutLetterDigitFilter(ts);
			}

			@Override
			public TokenStream tokenStream(String fieldName, Reader reader) {
				TokenStream ts = new MMSegTokenizer(new MaxWordSeg(Dictionary.getInstance()), reader);
				return new CutLetterDigitFilter(ts);
			}

		});

		Assert.assertArrayEquals("CutLeeterDigitFilter fail", words.toArray(new String[words.size()]),
				"mb 991 ch cq 40 519 tx mmseg 4 j".split(" "));
	}
}
