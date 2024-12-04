package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import application.storelib.Product;
import application.storelib.Store;

public class DataHandler {
	static File file = new File("Store.txt");

	public static void saveData(Store store) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(store);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Store loadData(){
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			Store s = (Store)ois.readObject();
			ois.close();

			return s;
		} catch (IOException | ClassNotFoundException e) {
			//commenting to avoid red lines on console
			// e.printStackTrace();
		}
		return new Store("UapBazar");
	}
}
