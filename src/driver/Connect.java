package driver;

public class Connect {
	private final String username = "root";
	private final String password = "";
	private final String database = "stellarfest";
	private final String host = "localhost:3306";
	private final String connection = String.format("jdbc:mysql://%s/%s", host, database);
	
	private Connect() {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
