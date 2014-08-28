package echonest.v4;

import echonest.v4.WebDocument;

import java.util.Map;

public class Image extends WebDocument {
	
	Image(Map map) {
		super("image", map);
	}
	
    /**
     * Gets the URL of the item
     * @return the URL
     */
    public String getURL() {
        return getString("url");
    }

    /**
     * Gets the license for the image
     * @return the image license
     */
    public String getLicenseType() {
    	return getString("license.type");
    }
    
    /**
     * Gets the image license attribution
     * @return the image license attribution
     */
    public String getLicenseAttribution() {
    	return getString("license.attribution");
    }
}
