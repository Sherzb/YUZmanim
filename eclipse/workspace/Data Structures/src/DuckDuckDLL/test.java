package DuckDuckDLL;

public class test extends CircularSentinelDLL {
	public static void main(String[] args) {
		CircularSentinelDLL blarg = new CircularSentinelDLL();
		blarg.insert("hello");
		blarg.insert("goodbye");
		
		CSDLLIterator it = (CSDLLIterator) blarg.iterator();
		while(it.hasNext()) {
			it.next();
		}
	}
}
