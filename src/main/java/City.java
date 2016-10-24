

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class City {
	public String CityName;
	public String FatherCity;

	public City(String CityName, String FatherCity) {
		this.CityName = CityName;
		this.FatherCity = FatherCity;

	}

	@Override
	public String toString() {
		return CityName + "->" + FatherCity;

	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		String filePath = "C:/Users/wangsy/Desktop/biding/forCity.txt";
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath)), "utf-8"));
			String line;
			String pro = null;
			String city = null;
			int proInt = 0, cityInt = 0, countyInt = 0;
			while ((line = input.readLine()) != null) {
				if (line.startsWith("2")) {
					pro = line.substring(1);
					System.out.println(pro);
					SARJDBC.insertIntoLocations("Province", proInt++, pro, null);
				} else if (line.startsWith("1")) {
					city = line.substring(1);
					if (pro == null) {

						SARJDBC.insertIntoLocations("City", cityInt++, city, city);
						SARJDBC.insertIntoLocations("Province", cityInt++, city, null);
						System.out.println("--->" + city);
					} else {
						SARJDBC.insertIntoLocations("City", cityInt++, city, pro);
						System.out.println("--->" + city);
					}
				} else {
					String[] counties = line.split(" ");
					for (String county : counties) {

						SARJDBC.insertIntoLocations("County", countyInt++, county, city);
						System.out.println("------->" + county);
					}
				}

			}
			
			input.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}
