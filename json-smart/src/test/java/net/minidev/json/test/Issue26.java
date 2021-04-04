package net.minidev.json.test;

import junit.framework.TestCase;
import net.minidev.json.JSONValue;


public class Issue26 extends TestCase {
	public class AppInDb {
		protected int softid;
	    protected transient String uuid;
	    protected String softname;
	    protected String infourl;

	    public int getSoftid() {
			return softid;
		}
		public void setSoftid(int softid) {
			this.softid = softid;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getSoftname() {
			return softname;
		}
		public void setSoftname(String softname) {
			this.softname = softname;
		}
		public String getInfourl() {
			return infourl;
		}
		public void setInfourl(String infourl) {
			this.infourl = infourl;
		}
	}

	public class App extends AppInDb {
	    public boolean isFree() {
			return free;
		}
		public void setFree(boolean free) {
			this.free = free;
		}
		public boolean isPlugin() {
			return plugin;
		}
		public void setPlugin(boolean plugin) {
			this.plugin = plugin;
		}
		protected boolean free;
	    protected boolean plugin;
	}

	public void testIssue26() {
		App dbApp = new App();
		dbApp.setSoftname("sssssssssss");
		assertTrue(JSONValue.toJSONString(dbApp).contains("sssssssssss"));
	}

}
