package com.dex.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void isNull_Null() {
		assertTrue(StringUtil.isNull(null));
	}

	@Test
	public void isNull_Empty() {
		assertFalse(StringUtil.isNull(""));
	}

	@Test
	public void isNullOrEmpty_Null() {
		assertTrue(StringUtil.isNullOrEmpty(null));
	}
	
	@Test
	public void isNullOrEmpty_Empty() {
		assertTrue(StringUtil.isNullOrEmpty(""));
	}
	
	@Test
	public void isNullOrEmpty_NonEmpty() {
		assertFalse(StringUtil.isNullOrEmpty("Test"));
	}
	
	@Test
	public void isNullOrEmpty_Spaces() {
		assertFalse(StringUtil.isNullOrEmpty("  "));
	}
	
	@Test
	public void isNullOrEmptyTrim_Null() {
		assertTrue(StringUtil.isNullOrEmptyTrim(null));
	}
	
	@Test
	public void isNullOrEmptyTrim_Spaces() {
		assertTrue(StringUtil.isNullOrEmptyTrim("  "));
	}
	
	@Test
	public void isNullOrEmptyTrim_NonSpaces() {
		assertFalse(StringUtil.isNullOrEmptyTrim("Test"));
	}
	
	@Test
	public void substring_Good() {
		assertThat( StringUtil.substring("Testvalue", 0, 4), is("Test"));
	}
	
	@Test
	public void substring_Null() {
		assertNull(StringUtil.substring(null, 0, 4));
	}
	
	@Test(expected=StringIndexOutOfBoundsException.class)
	public void substring_StartOutOfBounds() {
		StringUtil.substring("Test", 5, 4);
		fail("A StringIndexOutOfBoundsException should have been thrown");
	}
	
	@Test(expected=StringIndexOutOfBoundsException.class)
	public void substring_CountOutOfBounds() {
		StringUtil.substring("Test", 2, 4);
		fail("A StringIndexOutOfBoundsException should have been thrown");
	}
}