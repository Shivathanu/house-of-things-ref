package pt.feup.ads.factory;

import java.io.File;

import pt.feup.ads.environment.House;

public interface HouseLoader {
	
	House build(File selectedFile);

}
