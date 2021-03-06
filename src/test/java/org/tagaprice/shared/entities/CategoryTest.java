package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.categorymanagement.Category;

public class CategoryTest {

	Category emptyCategory;
	Category newCategory;
	Category updateCategory;
	Category setterCategory;
	
	User testUser = new User("testUID", "testRev", "Test User");

	@Before
	public void setUp() throws Exception {
		emptyCategory = new Category();
		newCategory = new Category(testUser, "title", new Category(testUser, "parent", null));
		updateCategory = new Category(testUser, "1", "2", "update", new Category(testUser, "updateparent", null));
		setterCategory = new Category();
	}

	@Test
	public void testCategory() {
		assertNull(emptyCategory.getParent());
		assertNull(emptyCategory.getTitle());
		assertNull(emptyCategory.getId());
		assertNull(emptyCategory.getRevision());
	}

	@Test
	public void testCategoryStringCategory() {
		assertEquals(newCategory.getTitle(), "title");
		assertNotNull(newCategory.getParent());
		assertEquals(newCategory.getParent().getTitle(), "parent");
		assertNull(newCategory.getId());
		assertNull(newCategory.getRevision());
	}


	@Test
	public void testGetParentCategory() {
		assertNotNull(updateCategory.getParent());
		assertEquals(updateCategory.getParent().getTitle(), "updateparent");
	}

	@Test
	public void testSetParentCategory() {
		setterCategory.setParent(new Category(testUser, "parent", null));
		assertNotNull(setterCategory.getParent());
		assertEquals(setterCategory.getParent().getTitle(), "parent");
		assertNull(setterCategory.getParent().getParent());

	}

	@Test
	public void testGetTitle() {
		assertEquals(updateCategory.getTitle(), "update");
	}

	@Test
	public void testSetTitle() {
		setterCategory.setTitle("title");
		assertEquals(setterCategory.getTitle(), "title");
	}

	@Test
	public void testGetRevision() {
		assertEquals(updateCategory.getRevision(),"2");
	}

	@Test
	public void testSetRevision() {
		setterCategory.setRevision("15");
		assertEquals(setterCategory.getRevision(), "15");
	}

	@Test
	public void testGetId() {
		assertEquals(updateCategory.getId(),"1");
	}

	@Test
	public void testSetId() {
		setterCategory.setId("9");
		assertEquals(setterCategory.getId(), "9");
	}

}
