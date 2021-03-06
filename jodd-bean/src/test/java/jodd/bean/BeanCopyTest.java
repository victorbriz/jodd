// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.bean;

import jodd.bean.data.FooBean;
import jodd.bean.data.FooBeanString;
import jodd.util.Wildcard;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BeanCopyTest {

	@Test
	public void testCopy() {
		FooBean fb = createFooBean();
		FooBean dest = new FooBean();
		BeanCopy.beans(fb, dest).copy();

		Integer v = (Integer) BeanUtil.getProperty(dest, "fooInteger");
		assertEquals(201, v.intValue());
		v = (Integer) BeanUtil.getProperty(dest, "fooint");
		assertEquals(202, v.intValue());
		Long vl = (Long) BeanUtil.getProperty(dest, "fooLong");
		assertEquals(203, vl.longValue());
		vl = (Long) BeanUtil.getProperty(dest, "foolong");
		assertEquals(204, vl.longValue());
		Byte vb = (Byte) BeanUtil.getProperty(dest, "fooByte");
		assertEquals(95, vb.intValue());
		vb = (Byte) BeanUtil.getProperty(dest, "foobyte");
		assertEquals(96, vb.intValue());
		Character c = (Character) BeanUtil.getProperty(dest, "fooCharacter");
		assertEquals('7', c.charValue());
		c = (Character) BeanUtil.getProperty(dest, "foochar");
		assertEquals('8', c.charValue());
		Boolean b = (Boolean) BeanUtil.getProperty(dest, "fooBoolean");
		assertTrue(b.booleanValue());
		b = (Boolean) BeanUtil.getProperty(dest, "fooboolean");
		assertFalse(b.booleanValue());
		Float f = (Float) BeanUtil.getProperty(dest, "fooFloat");
		assertEquals(209.0, f.floatValue(), 0.005);
		f = (Float) BeanUtil.getProperty(dest, "foofloat");
		assertEquals(210.0, f.floatValue(), 0.005);
		Double d = (Double) BeanUtil.getProperty(dest, "fooDouble");
		assertEquals(211.0, d.doubleValue(), 0.005);
		d = (Double) BeanUtil.getProperty(dest, "foodouble");
		assertEquals(212.0, d.doubleValue(), 0.005);
		String s = (String) BeanUtil.getProperty(dest, "fooString");
		assertEquals("213", s);
		String[] sa = (String[]) BeanUtil.getProperty(dest, "fooStringA");
		assertEquals(2, sa.length);
		assertEquals("214", sa[0]);
		assertEquals("215", sa[1]);
		assertSame(dest.getFooStringA(), sa);


		FooBean empty = new FooBean();
		BeanCopy.beans(empty, dest).copy();

		v = (Integer) BeanUtil.getProperty(dest, "fooInteger");
		assertNull(v);
		v = (Integer) BeanUtil.getProperty(dest, "fooint");
		assertEquals(0, v.intValue());
		vl = (Long) BeanUtil.getProperty(dest, "fooLong");
		assertNull(vl);
		vl = (Long) BeanUtil.getProperty(dest, "foolong");
		assertEquals(0, vl.longValue());
		vb = (Byte) BeanUtil.getProperty(dest, "fooByte");
		assertNull(vb);
		vb = (Byte) BeanUtil.getProperty(dest, "foobyte");
		assertEquals(0, vb.byteValue());
		c = (Character) BeanUtil.getProperty(dest, "fooCharacter");
		assertNull(c);
		c = (Character) BeanUtil.getProperty(dest, "foochar");
		assertEquals(0, c.charValue());
		b = (Boolean) BeanUtil.getProperty(dest, "fooBoolean");
		assertNull(b);
		b = (Boolean) BeanUtil.getProperty(dest, "fooboolean");
		assertFalse(b);
		f = (Float) BeanUtil.getProperty(dest, "fooFloat");
		assertNull(f);
		f = (Float) BeanUtil.getProperty(dest, "foofloat");
		assertEquals(0, f, 0.005);
		d = (Double) BeanUtil.getProperty(dest, "fooDouble");
		assertNull(d);
		d = (Double) BeanUtil.getProperty(dest, "foodouble");
		assertEquals(0, d, 0.005);
		s = (String) BeanUtil.getProperty(dest, "fooString");
		assertNull(s);
		sa = (String[]) BeanUtil.getProperty(dest, "fooStringA");
		assertNull(sa);
	}

	@Test
	public void testCopyIncludes() {
		FooBean fb = createFooBean();
		FooBean dest = new FooBean();
		BeanCopy.beans(fb, dest).excludeAll().include("fooInteger", "fooLong").copy();

		Integer v = (Integer) BeanUtil.getProperty(dest, "fooInteger");
		assertEquals(201, v.intValue());
		Long vl = (Long) BeanUtil.getProperty(dest, "fooLong");
		assertEquals(203, vl.longValue());
		v = (Integer) BeanUtil.getProperty(dest, "fooint");
		assertEquals(0, v.intValue());
		vl = (Long) BeanUtil.getProperty(dest, "foolong");
		assertEquals(0, vl.longValue());
		Byte vb = (Byte) BeanUtil.getProperty(dest, "fooByte");
		assertNull(vb);
		vb = (Byte) BeanUtil.getProperty(dest, "foobyte");
		assertEquals(0, vb.byteValue());
		Character c = (Character) BeanUtil.getProperty(dest, "fooCharacter");
		assertNull(c);
		c = (Character) BeanUtil.getProperty(dest, "foochar");
		assertEquals(0, c.charValue());
		Boolean b = (Boolean) BeanUtil.getProperty(dest, "fooBoolean");
		assertNull(b);
		b = (Boolean) BeanUtil.getProperty(dest, "fooboolean");
		assertFalse(b);
		Float f = (Float) BeanUtil.getProperty(dest, "fooFloat");
		assertNull(f);
		f = (Float) BeanUtil.getProperty(dest, "foofloat");
		assertEquals(0, f, 0.005);
		Double d = (Double) BeanUtil.getProperty(dest, "fooDouble");
		assertNull(d);
		d = (Double) BeanUtil.getProperty(dest, "foodouble");
		assertEquals(0, d, 0.005);
		String s = (String) BeanUtil.getProperty(dest, "fooString");
		assertNull(s);
		String[] sa = (String[]) BeanUtil.getProperty(dest, "fooStringA");
		assertNull(sa);
	}

	@Test
	public void testCopyExcludes() {
		FooBean fb = createFooBean();
		FooBean dest = new FooBean();
		BeanCopy.beans(fb, dest).exclude("fooInteger", "fooLong").copy();

		Integer v = (Integer) BeanUtil.getProperty(dest, "fooInteger");
		assertNull(v);
		v = (Integer) BeanUtil.getProperty(dest, "fooint");
		assertEquals(202, v.intValue());
		Long vl = (Long) BeanUtil.getProperty(dest, "fooLong");
		assertNull(vl);
		vl = (Long) BeanUtil.getProperty(dest, "foolong");
		assertEquals(204, vl.longValue());
		Byte vb = (Byte) BeanUtil.getProperty(dest, "fooByte");
		assertEquals(95, vb.intValue());
		vb = (Byte) BeanUtil.getProperty(dest, "foobyte");
		assertEquals(96, vb.intValue());
		Character c = (Character) BeanUtil.getProperty(dest, "fooCharacter");
		assertEquals('7', c.charValue());
		c = (Character) BeanUtil.getProperty(dest, "foochar");
		assertEquals('8', c.charValue());
		Boolean b = (Boolean) BeanUtil.getProperty(dest, "fooBoolean");
		assertTrue(b.booleanValue());
		b = (Boolean) BeanUtil.getProperty(dest, "fooboolean");
		assertFalse(b.booleanValue());
		Float f = (Float) BeanUtil.getProperty(dest, "fooFloat");
		assertEquals(209.0, f.floatValue(), 0.005);
		f = (Float) BeanUtil.getProperty(dest, "foofloat");
		assertEquals(210.0, f.floatValue(), 0.005);
		Double d = (Double) BeanUtil.getProperty(dest, "fooDouble");
		assertEquals(211.0, d.doubleValue(), 0.005);
		d = (Double) BeanUtil.getProperty(dest, "foodouble");
		assertEquals(212.0, d.doubleValue(), 0.005);
		String s = (String) BeanUtil.getProperty(dest, "fooString");
		assertEquals("213", s);
		String[] sa = (String[]) BeanUtil.getProperty(dest, "fooStringA");
		assertEquals(2, sa.length);
		assertEquals("214", sa[0]);
		assertEquals("215", sa[1]);
		assertSame(dest.getFooStringA(), sa);
	}

	@Test
	public void testCopyTemplate() {
		FooBean fooBean = createFooBean();
		FooBean dest = new FooBean();

		BeanCopy.beans(fooBean, dest).includeAs(FooBeanString.class).copy();

		Integer v = (Integer) BeanUtil.getProperty(dest, "fooInteger");
		assertNull(v);
		v = (Integer) BeanUtil.getProperty(dest, "fooint");
		assertEquals(0, v.intValue());
		Long vl = (Long) BeanUtil.getProperty(dest, "fooLong");
		assertNull(vl);
		vl = (Long) BeanUtil.getProperty(dest, "foolong");
		assertEquals(0, vl.longValue());
		Byte vb = (Byte) BeanUtil.getProperty(dest, "fooByte");
		assertNull(vb);
		vb = (Byte) BeanUtil.getProperty(dest, "foobyte");
		assertEquals(0, vb.byteValue());
		Character c = (Character) BeanUtil.getProperty(dest, "fooCharacter");
		assertNull(c);
		c = (Character) BeanUtil.getProperty(dest, "foochar");
		assertEquals(0, c.charValue());
		Boolean b = (Boolean) BeanUtil.getProperty(dest, "fooBoolean");
		assertNull(b);
		b = (Boolean) BeanUtil.getProperty(dest, "fooboolean");
		assertFalse(b.booleanValue());
		Float f = (Float) BeanUtil.getProperty(dest, "fooFloat");
		assertNull(f);
		f = (Float) BeanUtil.getProperty(dest, "foofloat");
		assertEquals(0, f.floatValue(), 0.005);
		Double d = (Double) BeanUtil.getProperty(dest, "fooDouble");
		assertNull(d);
		d = (Double) BeanUtil.getProperty(dest, "foodouble");
		assertEquals(0, d.doubleValue(), 0.005);
		String s = (String) BeanUtil.getProperty(dest, "fooString");
		assertEquals("213", s);
		String[] sa = (String[]) BeanUtil.getProperty(dest, "fooStringA");
		assertNull(sa);
	}

	@Test
	public void testCopyProperties() {
		Properties properties = new Properties();

		properties.put("fooInteger", Integer.valueOf(1));
		properties.put("fooint", Integer.valueOf(2));

		FooBean fooBean = new FooBean();

		assertEquals(0, fooBean.getFooint());

		// copy to

		BeanCopy.fromMap(properties).toBean(fooBean).copy();

		assertEquals(1, fooBean.getFooInteger().intValue());
		assertEquals(2, fooBean.getFooint());


		// copy back

		properties.clear();

		BeanCopy.fromBean(fooBean).toMap(properties).copy();

		assertEquals(1, properties.get("fooInteger"));
		assertEquals(2, properties.get("fooint"));
	}




	static class Less {
		String data;

		Integer number;

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}
	}

	static class More {
		String data;

		String number;

		String boo;

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}


		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getBoo() {
			return boo;
		}

		public void setBoo(String boo) {
			this.boo = boo;
		}
	}

	@Test
	public void testLessToMore() {
		Less less = new Less();
		less.data = "data";
		less.number = new Integer(2);
		More more = new More();
		BeanCopy.beans(less, more).declared(true).copy();
		assertEquals("data", more.data);
		assertEquals("2", more.number);

		more.data = "tij";
		more.number = "17";
		BeanCopy.beans(more, less).declared(true).copy();
		assertEquals("tij", less.data);
		assertEquals(17, less.number.intValue());
	}

	@Test
	public void testCopyMap() {
		Map map = new HashMap();
		map.put("fooint", Integer.valueOf(102));
		map.put("fooString", "mao");

		FooBean dest = new FooBean();
		BeanCopy.beans(map, dest).copy();
		assertEquals(102, dest.getFooint());
		assertEquals("mao", dest.getFooString());

		Map destMap = new HashMap();
		BeanCopy.beans(map, destMap).copy();
		assertEquals(2, destMap.size());
		assertEquals(Integer.valueOf(102), destMap.get("fooint"));
		assertEquals("mao", destMap.get("fooString"));
	}

	@Test
	public void testCopyIgnoreNulls() {
		FooBean fb = createFooBean();
		FooBean dest = new FooBean();

		dest.setFooInteger(Integer.valueOf(999));
		fb.setFooInteger(null);
		BeanCopy.beans(fb, dest).ignoreNulls(true).copy();

		Integer v = (Integer) BeanUtil.getProperty(dest, "fooInteger");
		assertEquals(999, v.intValue());
		v = (Integer) BeanUtil.getProperty(dest, "fooint");
		assertEquals(202, v.intValue());
		Long vl = (Long) BeanUtil.getProperty(dest, "fooLong");
		assertEquals(203, vl.longValue());
		vl = (Long) BeanUtil.getProperty(dest, "foolong");
		assertEquals(204, vl.longValue());
		Byte vb = (Byte) BeanUtil.getProperty(dest, "fooByte");
		assertEquals(95, vb.intValue());
		vb = (Byte) BeanUtil.getProperty(dest, "foobyte");
		assertEquals(96, vb.intValue());
		Character c = (Character) BeanUtil.getProperty(dest, "fooCharacter");
		assertEquals('7', c.charValue());
		c = (Character) BeanUtil.getProperty(dest, "foochar");
		assertEquals('8', c.charValue());
		Boolean b = (Boolean) BeanUtil.getProperty(dest, "fooBoolean");
		assertTrue(b.booleanValue());
		b = (Boolean) BeanUtil.getProperty(dest, "fooboolean");
		assertFalse(b.booleanValue());
		Float f = (Float) BeanUtil.getProperty(dest, "fooFloat");
		assertEquals(209.0, f.floatValue(), 0.005);
		f = (Float) BeanUtil.getProperty(dest, "foofloat");
		assertEquals(210.0, f.floatValue(), 0.005);
		Double d = (Double) BeanUtil.getProperty(dest, "fooDouble");
		assertEquals(211.0, d.doubleValue(), 0.005);
		d = (Double) BeanUtil.getProperty(dest, "foodouble");
		assertEquals(212.0, d.doubleValue(), 0.005);
		String s = (String) BeanUtil.getProperty(dest, "fooString");
		assertEquals("213", s);
		String[] sa = (String[]) BeanUtil.getProperty(dest, "fooStringA");
		assertEquals(2, sa.length);
		assertEquals("214", sa[0]);
		assertEquals("215", sa[1]);
		assertSame(dest.getFooStringA(), sa);
	}

	private FooBean createFooBean() {
		FooBean fb = new FooBean();
		fb.setFooInteger(new Integer(201));
		fb.setFooint(202);
		fb.setFooLong(new Long(203));
		fb.setFoolong(204);
		fb.setFooByte(new Byte((byte) 95));
		fb.setFoobyte((byte) 96);
		fb.setFooCharacter(new Character('7'));
		fb.setFoochar('8');
		fb.setFooBoolean(Boolean.TRUE);
		fb.setFooboolean(false);
		fb.setFooFloat(new Float(209.0));
		fb.setFoofloat((float) 210.0);
		fb.setFooDouble(new Double(211.0));
		fb.setFoodouble(212.0);
		fb.setFooString("213");
		fb.setFooStringA(new String[]{"214", "215"});
		return fb;
	}

	public static class Source {
		public String pub = "a1";

		private int priv = 2;

		public long getGetter() {
			return 5;
		}

		private int _prop = 8;

		public int getProp() {
			return _prop;
		}

		protected String getMoo() {
			return moo;
		}

		protected String moo = "wof";
	}

	@Test
	public void testCopyWithFields() {
		Source source = new Source();
		HashMap dest = new HashMap();

		BeanCopy.beans(source, dest).copy();

		assertEquals(2, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());

		dest.clear();
		BeanCopy.beans(source, dest).declared(true).copy();

		assertEquals(3, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());
		assertEquals("wof", dest.get("moo").toString());


		dest.clear();
		BeanCopy.beans(source, dest).declared(false).includeFields(true).copy();

		assertEquals(3, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());
		assertEquals("a1", dest.get("pub").toString());


		dest.clear();
		BeanCopy.beans(source, dest).declared(true).includeFields(true).copy();

		assertEquals(6, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());
		assertEquals("a1", dest.get("pub").toString());
		assertEquals("2", dest.get("priv").toString());
		assertEquals("8", dest.get("_prop").toString());
		assertEquals("wof", dest.get("moo").toString());
	}

	public static class Moo {

		private String name = "cow";
		private Integer value = Integer.valueOf(7);
		private long nick = 100;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public long getNick() {
			return nick;
		}

		public void setNick(long nick) {
			this.nick = nick;
		}
	}

	@Test
	public void testIncludeExclude() {
		Moo moo = new Moo();
		HashMap map = new HashMap();

		BeanCopy beanCopy = new BeanCopy(moo, map) {
			@Override
			public boolean accept(String propertyName, String pattern, boolean included) {
				return Wildcard.match(propertyName, pattern);
			}
		};

		// + exclude all
		beanCopy.rules.reset();
		map.clear();
		beanCopy.exclude("*");
		beanCopy.copy();

		assertEquals(0, map.size());

		// + exclude all but one
		beanCopy.rules.reset();
		map.clear();
		beanCopy.exclude("*");
		beanCopy.include("name");
		beanCopy.copy();

		assertEquals(1, map.size());
		assertEquals("cow", map.get("name"));

		// + include all but one
		beanCopy.rules.reset();
		map.clear();
		beanCopy.exclude("nick");
		beanCopy.copy();

		assertEquals(2, map.size());
		assertEquals("cow", map.get("name"));
		assertEquals("7", map.get("value").toString());

		// + include all
		beanCopy.rules.reset();
		map.clear();
		beanCopy.copy();

		assertEquals(3, map.size());
		assertEquals("cow", map.get("name"));
		assertEquals("7", map.get("value").toString());
		assertEquals("100", map.get("nick").toString());
	}

	// ---------------------------------------------------------------- special test

	public static class PropertyBean {
		public int number;
		public PropertyBean child;
	}

	@Test
	public void testFromMapToBean() throws Exception {
		Properties propsSource = new Properties();

		propsSource.put("number", 42);
		propsSource.put("child.number", 43);
		propsSource.put("nonExistantNumber", 142);
		propsSource.put("nonExistantChild.number", 143);

		PropertyBean beanDest = new PropertyBean();

		BeanCopy.fromMap(propsSource).toBean(beanDest).forced(true).copy();

		assertThat(beanDest.number, is(42));
		assertThat(beanDest.child.number, is(43));
	}

	@Test
	public void testFromMapToMap() throws Exception {
		Properties propsSource = new Properties();

		propsSource.put("number", 42);
		propsSource.put("child.number", 43);
		propsSource.put("nonExistantNumber", 142);
		propsSource.put("nonExistantChild.number", 143);

		Properties propsDest = new Properties();

		BeanCopy.fromMap(propsSource).toMap(propsDest).copy();

		assertEquals(propsSource, propsDest);
	}

	@Test
	public void testFromBeanToMap() throws Exception {
		PropertyBean beanSource = new PropertyBean();

		beanSource.number = 42;
		beanSource.child = new PropertyBean();
		beanSource.child.number = 43;

		Properties propsDest = new Properties();

		BeanCopy
			.fromBean(beanSource)
			.toMap(propsDest)
			.includeFields(true)
			.copy();


		assertThat(propsDest.size(), is(2));
		assertThat((Integer) propsDest.get("number"), is(42));
		assertThat((Integer) BeanUtil.getProperty(propsDest, "child.number"), is(43));
	}

	@Test
	public void testFromBeanToBean() throws Exception {
		PropertyBean beanSource = new PropertyBean();

		beanSource.number = 42;
		beanSource.child = new PropertyBean();
		beanSource.child.number = 43;

		PropertyBean beanDest = new PropertyBean();

		BeanCopy.fromBean(beanSource).toBean(beanDest).includeFields(true).copy();

		assertThat(beanDest.number, is(42));
		assertThat(beanDest.child.number, is(43));
	}

}
