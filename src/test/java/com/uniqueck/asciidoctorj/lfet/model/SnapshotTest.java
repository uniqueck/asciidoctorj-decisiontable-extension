package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnapshotTest extends AbstractLfdtTest<Snapshot> {
	private Snapshot createUnderTest() {
		return new Snapshot("crDat", "rSeq", "scm", "cars", "focR", "focCA");
	}
	
	private String createExpectedXml() {
		return "<Snapshot crDat='crDat' rSeq='rSeq' scm='scm' cars='cars' focR='focR' focCA='focCA'/>".replaceAll("'", "\"");
	}

	@Test
	public void testSnapshot() throws Exception {
		Snapshot snapshot = createUnderTest();
		assertSnapshot(snapshot, "crDat", "rSeq", "scm", "cars", "focR", "focCA");
	}

	@Test
	public void testPersistSnapshot() throws Exception {
		Snapshot snapshot = createUnderTest();
		String xml = persist(snapshot);
		assertEquals(createExpectedXml(), xml);
	}

	@Test
	public void testConvertTitleXML() throws Exception {
		Snapshot snapshot = convertToModel(createExpectedXml());
		assertSnapshot(snapshot, "crDat", "rSeq", "scm", "cars", "focR", "focCA");
	}
}
